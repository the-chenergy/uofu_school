/*******************************************
 * Solutions for the CS:APP Performance Lab
 ********************************************/

#include <stdio.h>
#include <stdlib.h>

#include "defs.h"

student_t student = {
    "Qianlang Chen",          /* Full name */
    "qianlangchen@gmail.com", /* Email address */
};

/***************
 * COMPLEX KERNEL
 ***************/

/******************************************************
 * Your different versions of the complex kernel go here
 ******************************************************/

/*
 * naive_complex - The naive baseline version of complex
 */
char naive_complex_descr[] = "naive_complex: Naive baseline implementation";
void naive_complex(int dim, pixel* src, pixel* dest) {
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++) {
      dest[RIDX(dim - j - 1, dim - i - 1, dim)].red =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].green =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].blue =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;
    }
}

/*
 * complex - Your current working version of complex
 * IMPORTANT: This is the version you will be graded on
 */
char complex_descr[] = "complex: Current working version";
void complex(int dim, pixel* src, pixel* dst) {
  /* Strategy: go row-wise with blocking */
  const int block_dim = 8;

  for (int block_y = 0; block_y < dim; block_y += block_dim) {
    for (int block_x = 0; block_x < dim; block_x += block_dim) {
      /* Process block starting at (block_y, block_x) */
      for (int y = block_y; y < block_y + block_dim; y++) {
        for (int x = block_x; x < block_x + block_dim; x++) {
          pixel p = src[RIDX(y, x, dim)];
          unsigned short gray = ((unsigned int)p.red + p.green + p.blue) / 3;
          p.red = p.green = p.blue = gray;
          dst[RIDX(dim - 1 - x, dim - 1 - y, dim)] = p;
        }
      }
    }
  }
}

/*********************************************************************
 * register_complex_functions - Register all of your different versions
 *     of the complex kernel with the driver by calling the
 *     add_complex_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_complex_functions() {
  add_complex_function(&complex, complex_descr);
  add_complex_function(&naive_complex, naive_complex_descr);
}

/***************
 * MOTION KERNEL
 **************/

/***************************************************************
 * Various helper functions for the motion kernel
 * You may modify these or add new ones any way you like.
 **************************************************************/

/*
 * weighted_combo - Returns new pixel value at (i,j)
 */
static pixel weighted_combo(int dim, int i, int j, pixel* src) {
  int ii, jj;
  pixel current_pixel;

  int red, green, blue;
  red = green = blue = 0;

  int num_neighbors = 0;
  for (ii = 0; ii < 3; ii++)
    for (jj = 0; jj < 3; jj++)
      if ((i + ii < dim) && (j + jj < dim)) {
        num_neighbors++;
        red += (int)src[RIDX(i + ii, j + jj, dim)].red;
        green += (int)src[RIDX(i + ii, j + jj, dim)].green;
        blue += (int)src[RIDX(i + ii, j + jj, dim)].blue;
      }

  current_pixel.red = (unsigned short)(red / num_neighbors);
  current_pixel.green = (unsigned short)(green / num_neighbors);
  current_pixel.blue = (unsigned short)(blue / num_neighbors);

  return current_pixel;
}

/* Adds to (when mult=1) or subtracts from (when mult=-1) the running sums a region of RGB
 * values starting at (y, x) with size dy*dx */
#define READ(mult, y, x, dy, dx)                                                       \
  for (int sy = y; sy < y + dy; sy++) {                                                \
    for (int sx = x; sx < x + dx; sx++) {                                              \
      pixel p = src[sy * dim + sx];                                                    \
      red_sum += mult * p.red, green_sum += mult * p.green, blue_sum += mult * p.blue; \
    }                                                                                  \
  }

/* Checks and prints whether the red value at (y, x) matches the naive solution. Exits the
 * program if they don't match. */
#define CHECK(log, y, x)                                                     \
  unsigned short expected = weighted_combo(dim, y, x, src).red,              \
                 actual = dst[RIDX(y, x, dim)].red;                          \
  if (actual != expected) {                                                  \
    printf("## FAIL %s @ (%i, %i) %i != %i\n", log, y, x, actual, expected); \
    exit(1);                                                                 \
  }                                                                          \
  printf("## pass %s @ (%i, %i)\n", log, y, x);

/* Divides the running sums by count and writes the results to dst at (y, x) */
#define WRITE(count, y, x)                                                               \
  pixel p;                                                                               \
  p.red = red_sum / (count), p.green = green_sum / (count), p.blue = blue_sum / (count); \
  dst[RIDX(y, x, dim)] = p;

/******************************************************
 * Your different versions of the motion kernel go here
 ******************************************************/

/*
 * naive_motion - The naive baseline version of motion
 */
char naive_motion_descr[] = "naive_motion: Naive baseline implementation";
void naive_motion(int dim, pixel* src, pixel* dst) {
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++) dst[RIDX(i, j, dim)] = weighted_combo(dim, i, j, src);
}

/*
 * motion - Your current working version of motion.
 * IMPORTANT: This is the version you will be graded on
 */
char motion_descr[] = "motion: Current working version";
void motion(int dim, pixel* src, pixel* dst) {
  /* Strategy: go row-wise with running sums */

  /* Prepare for the top row by summing the first 2*3 window */
  unsigned int red_sum = 0, green_sum = 0, blue_sum = 0;
  READ(1, 0, 0, 2, 3);

  /* Handle down to the bottom 2 rows */
  for (int y = 0; y < dim - 2; y++) {
    /* Handle the leftmost column by expanding the window bottom */
    READ(1, y + 2, 0, 1, 3);
    WRITE(9, y, 0);
    unsigned int red_sum_top = red_sum, green_sum_top = green_sum, blue_sum_top = blue_sum;

    /* Handle the rest of columns by sliding the window right; take extra care of the 2
     * rightmost columns */
    for (int x = 1; x < dim - 2; x++) {
      READ(-1, y, x - 1, 3, 1);
      READ(1, y, x + 2, 3, 1);
      WRITE(9, y, x);
    }
    for (int dx = 2; dx > 0; dx--) {
      READ(-1, y, dim - dx - 1, 3, 1);
      WRITE(dx * 3, y, dim - dx);
    }

    /* Prepare for moving down by shrinking the window top */
    red_sum = red_sum_top, green_sum = green_sum_top, blue_sum = blue_sum_top;
    READ(-1, y, 0, 1, 3);
  }

  /* Handle the bottom two rows */
  for (int dy = 2; dy > 0; dy--) {
    /* Handle the leftmost column (withot expanding bottom) */
    WRITE(3 * dy, dim - dy, 0);
    unsigned int red_sum_top = red_sum, green_sum_top = green_sum, blue_sum_top = blue_sum;

    /* Handle the rest of columns by sliding the window right; take extra care of the 2
     * rightmost columns */
    for (int x = 0 + 1; x < dim - 2; x++) {
      READ(-1, dim - dy, x - 1, dy, 1);
      READ(1, dim - dy, x + 2, dy, 1);
      WRITE(dy * 3, dim - dy, x);
    }
    for (int dx = 2; dx > 0; dx--) {
      READ(-1, dim - dy, dim - dx - 1, dy, 1);
      WRITE(dy * dx, dim - dy, dim - dx);
    }

    /* Prepare for moving down by shrinking the window top */
    red_sum = red_sum_top, green_sum = green_sum_top, blue_sum = blue_sum_top;
    READ(-1, dim - dy, 0, 1, 3);
  }
}

/*********************************************************************
 * register_motion_functions - Register all of your different versions
 *     of the motion kernel with the driver by calling the
 *     add_motion_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_motion_functions() {
  add_motion_function(&motion, motion_descr);
  add_motion_function(&naive_motion, naive_motion_descr);
}
