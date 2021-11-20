/**
 * A memory allocator that provides the `malloc` and `free` functions for general memory-
 * management usages.
 *
 * The allocator's implementation incorporates a segregated explicit free list to help find the
 * best-fit free block more efficiently. To reduce the number of calls to `mem_map`, the
 * allocator only calls `mem_map` when there exists no free block that fits a requested size,
 * where it maps a new chunk that can fit at least 64 times the requested size. Coalescence of
 * adjacent blocks is performed whenever and wherever possible upon a `mm_free` call. Chunks are
 * also unmapped when its blocks are all freed up.
 *
 * Qianlang Chen
 * F 11/19/21
 */

#include "mm.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "memlib.h"

/** Returns `floor( log2(x) )`. */
static int lg(size_t x) { return 31 - __builtin_clz((int)x); }

/** Returns `ceil(x / boundary) * boundary`. */
static size_t align(size_t x, size_t boundary) {
  return (x + (boundary - 1)) & ~(boundary - 1);
}

/** Sets the header and footer data for a block. */
static void set_block_metadata(char* block, size_t size, char is_allocated) {
  *(size_t*)(block - 8) = *(size_t*)(block + size) = size | is_allocated;
}

static size_t get_block_size(char* block) { return *(size_t*)(block - 8) & ~1; }

static char is_block_allocated(char* block) { return *(size_t*)(block - 8) & 1; }

static size_t get_prev_block_size(char* block) { return *(size_t*)(block - 16) & ~1; }

static char is_prev_block_allocated(char* block) { return *(size_t*)(block - 16) & 1; }

/** Creates a new block with at least the requested `size` by mapping a new chunk. */
static char* create_block(size_t size) {
  size = align(size + 32, mem_pagesize());
  char* chunk = mem_map(size);
  /* Chunk start and end markers: size 0, allocated */
  *(size_t*)chunk = *(size_t*)(chunk + size - 8) = 0 | 1;

  char* block = chunk + 16;
  set_block_metadata(block, size - 32, 0);
  return block;
}

/**
 * Splits a free block into two, where the first part has size `size_to_allocate` and will be
 * marked as allocated. Returns a pointer to the second (free) part, or null if no free part is
 * produced, where the entire `block` becomes allocated.
 */
static char* split_block(char* block, size_t size_to_allocate) {
  size_t size = get_block_size(block);
  if (size < size_to_allocate + 32) {
    set_block_metadata(block, size, 1); /* no meaningful size to spare; just mark allocated */
    return NULL;
  }

  set_block_metadata(block, size_to_allocate, 1);
  char* block_to_spare = block + size_to_allocate + 16;
  set_block_metadata(block_to_spare, size - size_to_allocate - 16, 0);
  return block_to_spare;
}

/**
 * An array of linked lists, where free_list[i] is the address of the first free block with size
 * between `2**i` and `2**(i+1) - 1`.
 */
static char** free_list = NULL;

static void add_block_to_free_list(char* block) {
  size_t size = get_block_size(block);
  char** free_list_start = &free_list[lg(size)];
  *(char**)block = *free_list_start;                             /* block.next = head.next */
  *(char**)(block + 8) = (char*)free_list_start;                 /* block.prev = head */
  if (*free_list_start) *(char**)(*free_list_start + 8) = block; /* head.next.prev = block */
  *free_list_start = block;                                      /* head.next = block */
}

/** Returns the smallest free block that fits `size`, or null if there isn't one. */
static char* find_best_fit_block(size_t size) {
  char* best_fit_block = NULL;
  size_t best_fit_size = 1ul << 32;
  for (int i = lg(size); !best_fit_block && i < 32; i++) {
    char* curr_block = free_list[i];
    while (curr_block) {
      size_t curr_block_size = get_block_size(curr_block);
      if (curr_block_size >= size && curr_block_size < best_fit_size) {
        best_fit_block = curr_block;
        best_fit_size = curr_block_size;
      }
      curr_block = *(char**)curr_block; /* curr = curr.next */
    }
  }
  return best_fit_block;
}

static void remove_block_from_free_list(char* block) {
  if (*(char**)block)                                     /* if (block.next) */
    *(char**)(*(char**)block + 8) = *(char**)(block + 8); /*   block.next.prev = block.prev */
  **(char***)(block + 8) = *(char**)block;                /* block.prev.next = block.next */
}

/**
 * Initializes the memory allocator. This function must be called and returned before any call
 * to `mm_malloc` or `mm_free`.
 */
int mm_init(void) {
  mem_reset();

  /* Create an initial block of memory whose start will used as the free list */
  char* block = create_block(1 << 16);

  /* Initialize and zero out the free list with 32 entries (allocation sizes go up to 2**32) */
  free_list = (char**)block;
  size_t free_list_size = 8 * 32;
  memset(free_list, 0, free_list_size);

  /* The rest of the initial block can be used as free space */
  char* block_to_spare = split_block(block, free_list_size);
  add_block_to_free_list(block_to_spare);

  return 0;
}

/**
 * Allocates `size` bytes of contiguous memory and returns a pointer to the start of the
 * allocated memory block.
 */
void* mm_malloc(size_t size) {
  size = align(size, 16);

  /* Find the best-fit block from free list and create new block if necessary */
  char* best_fit_block = find_best_fit_block(size);
  if (best_fit_block)
    remove_block_from_free_list(best_fit_block);
  else
    best_fit_block = create_block(size * 64); /* 64x to reduce `mem_map` usage as it's slow */

  /* The rest of the block (if any) can be used as free space */
  char* block_to_spare = split_block(best_fit_block, size);
  if (block_to_spare) add_block_to_free_list(block_to_spare);

  return (void*)best_fit_block;
}

/** Frees the allocated block of memory starting at `ptr`. */
void mm_free(void* ptr) {
  char* block = ptr;
  size_t size = get_block_size(block);

  /* Coalesce with previous block if it's free */
  if (!is_prev_block_allocated(block)) {
    size += get_prev_block_size(block) + 16;
    block -= get_prev_block_size(block) + 16;
    remove_block_from_free_list(block);
  }

  /* Coalesce with next block if it's free */
  char* next_block = block + size + 16;
  if (!is_block_allocated(next_block)) {
    size += get_block_size(next_block) + 16;
    remove_block_from_free_list(next_block);
  }

  next_block = block + size + 16;
  if (!get_block_size(next_block) && !get_prev_block_size(block)) {
    /* Reached both start and end marks where block size is zero; unmap chunk */
    mem_unmap(block - 16, size + 32);
    return;
  }

  /* Mark block as free */
  set_block_metadata(block, size, 0);
  add_block_to_free_list(block);
}
