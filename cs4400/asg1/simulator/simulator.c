/*
 * Author: Daniel Kopta
 * Updated by: Erin Parker
 * CS 4400, University of Utah
 *
 * Simulator handout
 * A simple x86-like processor simulator.
 * Read in a binary file that encodes instructions to execute.
 * Simulate a processor by executing instructions one at a time and
 * appropriately updating register and memory contents.
 *
 * Some code and pseudo code has been provided as a starting point.
 *
 * Completed by: Qianlang Chen (u1172983)
 */

#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>

#include "instruction.h"

// Forward declarations for helper functions
unsigned int get_file_size(int file_descriptor);
unsigned int* load_file(int file_descriptor, unsigned int size);
instruction_t* decode_instructions(unsigned int* bytes,
                                   unsigned int num_instructions);
unsigned int execute_instruction(unsigned int program_counter,
                                 instruction_t* instructions, int* registers,
                                 unsigned char* memory);
void print_instructions(instruction_t* instructions,
                        unsigned int num_instructions);
void error_exit(const char* message);

// 17 registers
#define NUM_REGS 17
// 1024-byte stack
#define STACK_SIZE 1024

int main(int argc, char** argv) {
  // Make sure we have enough arguments
  if (argc < 2)
    error_exit("must provide an argument specifying a binary file to execute");

  // Open the binary file
  int file_descriptor = open(argv[1], O_RDONLY);
  if (file_descriptor == -1) error_exit("unable to open input file");

  // Get the size of the file
  unsigned int file_size = get_file_size(file_descriptor);
  // Make sure the file size is a multiple of 4 bytes
  // since machine code instructions are 4 bytes each
  if (file_size % 4 != 0) error_exit("invalid input file");

  // Load the file into memory
  // We use an unsigned int array to represent the raw bytes
  // We could use any 4-byte integer type
  unsigned int* instruction_bytes = load_file(file_descriptor, file_size);
  close(file_descriptor);

  unsigned int num_instructions = file_size / 4;

  /****************************************/
  /**** Begin code to modify/implement ****/
  /****************************************/

  // Allocate and decode instructions (left for you to fill in)
  instruction_t* instructions =
      decode_instructions(instruction_bytes, num_instructions);

  // Optionally print the decoded instructions for debugging
  // Will not work until you implement decode_instructions
  // Do not call this function in your submitted final version
  //  print_instructions(instructions, num_instructions);

  // Allocate and initialize registers
  int* registers = malloc(sizeof(int) * NUM_REGS);
  for (int i = 0; i < NUM_REGS; i++) registers[i] = 0;
  registers[6] = STACK_SIZE;  // %esp

  // Stack memory is byte-addressed, so it must be a 1-byte type
  unsigned char* memory = malloc(STACK_SIZE);

  // Run the simulation
  unsigned int program_counter = 0;

  // program_counter is a byte address, so we must multiply num_instructions by
  // 4 to get the address past the last instruction
  while (program_counter != num_instructions * 4) {
    program_counter =
        execute_instruction(program_counter, instructions, registers, memory);
  }

  return 0;
}

/*
 * Decodes the array of raw instruction bytes into an array of instruction_t
 * Each raw instruction is encoded as a 4-byte unsigned int
 */
instruction_t* decode_instructions(unsigned int* bytes,
                                   unsigned int num_instructions) {
  instruction_t* retval = malloc(sizeof(instruction_t) * num_instructions);

  for (int i = 0; i < num_instructions; i++) {
    unsigned int curr_bytes = bytes[i];
    instruction_t* curr_instr = retval + sizeof(instruction_t) / 4 * i;
    curr_instr->opcode = curr_bytes >> 27 & (1 << 5) - 1;
    curr_instr->first_register = curr_bytes >> 22 & (1 << 5) - 1;
    curr_instr->second_register = curr_bytes >> 17 & (1 << 5) - 1;
    curr_instr->immediate = curr_bytes & (1 << 16) - 1;
  }

  return retval;
}

/*
 * Executes a single instruction and returns the next program counter
 */
unsigned int execute_instruction(unsigned int program_counter,
                                 instruction_t* instructions, int* registers,
                                 unsigned char* memory) {
  // program_counter is a byte address, but instructions are 4 bytes each
  // divide by 4 to get the index into the instructions array
  instruction_t instr = instructions[program_counter / 4];
  double unsigned_res, signed_res;

  //  printf("pc%i op%u ra%u rb%u im%i\n", program_counter, instr.opcode,
  //  instr.first_register, instr.second_register, instr.immediate);

  switch (instr.opcode) {
    case subl:
      registers[instr.first_register] =
          registers[instr.first_register] - instr.immediate;
      break;
    case addl_reg_reg:
      registers[instr.second_register] =
          registers[instr.first_register] + registers[instr.second_register];
      break;
    case addl_imm_reg:
      registers[instr.first_register] =
          registers[instr.first_register] + instr.immediate;
      break;
    case imull:
      registers[instr.second_register] =
          registers[instr.first_register] * registers[instr.second_register];
      break;
    case shrl:
      registers[instr.first_register] =
          (unsigned int)registers[instr.first_register] >> 1;
      break;
    case movl_reg_reg:
      registers[instr.second_register] = registers[instr.first_register];
      break;
    case movl_deref_reg:
      registers[instr.second_register] =
          *(int*)(memory + registers[instr.first_register] + instr.immediate);
      break;
    case movl_reg_deref:
      *(int*)(memory + registers[instr.second_register] + instr.immediate) =
          registers[instr.first_register];
      break;
    case movl_imm_reg:
      registers[instr.first_register] = (int)instr.immediate;
      break;
    case cmpl:
      unsigned_res = (unsigned int)registers[instr.second_register] * 1. -
                     (unsigned int)registers[instr.first_register];
      signed_res = registers[instr.second_register] * 1. -
                   registers[instr.first_register];
      registers[16] &= ~(1 | 1 << 6 | 1 << 7 | 1 << 11);  // clear CZSO
      registers[16] |= unsigned_res < 0 || unsigned_res > 4294967295.;
      registers[16] |= (unsigned_res == 0) << 6;
      registers[16] |= (((unsigned int)unsigned_res & 1 << 31) > 0) << 7;
      registers[16] |= (signed_res < -2147483648. || signed_res > 2147483647.)
                       << 11;
      break;
    case je:
      if (registers[16] >> 6 & 1) return program_counter + 4 + instr.immediate;
      break;
    case jl:
      if ((registers[16] >> 7 & 1) ^ (registers[16] >> 11 & 1))
        return program_counter + 4 + instr.immediate;
      break;
    case jle:
      if ((registers[16] >> 7 & 1) ^ (registers[16] >> 11 & 1) ||
          registers[16] >> 6 & 1)
        return program_counter + 4 + instr.immediate;
      break;
    case jge:
      if (!((registers[16] >> 7 & 1) ^ (registers[16] >> 11 & 1)))
        return program_counter + 4 + instr.immediate;
      break;
    case jbe:
      if (registers[16] & 1 || registers[16] >> 6 & 1)
        return program_counter + 4 + instr.immediate;
      break;
    case jmp:
      return program_counter + 4 + instr.immediate;
      break;
    case call:
      registers[6] -= 4;
      *(int*)(memory + registers[6]) = program_counter + 4;
      return program_counter + 4 + instr.immediate;
      break;
    case ret:
      if (registers[6] == STACK_SIZE) exit(0);
      registers[6] += 4;
      return *(int*)(memory + registers[6] - 4);
      break;
    case pushl:
      registers[6] -= 4;
      *(int*)(memory + registers[6]) = registers[instr.first_register];
      break;
    case popl:
      registers[instr.first_register] = *(int*)(memory + registers[6]);
      registers[6] += 4;
      break;
    case printr:
      printf("%d (0x%x)\n", registers[instr.first_register],
             registers[instr.first_register]);
      break;
    case readr:
      scanf("%d", &(registers[instr.first_register]));
      break;
  }

  // program_counter + 4 represents the subsequent instruction
  return program_counter + 4;
}

/*********************************************/
/****  DO NOT MODIFY THE FUNCTIONS BELOW  ****/
/*********************************************/

/*
 * Returns the file size in bytes of the file referred to by the given
 * descriptor
 */
unsigned int get_file_size(int file_descriptor) {
  struct stat file_stat;
  fstat(file_descriptor, &file_stat);
  return file_stat.st_size;
}

/*
 * Loads the raw bytes of a file into an array of 4-byte units
 */
unsigned int* load_file(int file_descriptor, unsigned int size) {
  unsigned int* raw_instruction_bytes = (unsigned int*)malloc(size);
  if (raw_instruction_bytes == NULL)
    error_exit(
        "unable to allocate memory for instruction bytes (something went "
        "really wrong)");

  int num_read = read(file_descriptor, raw_instruction_bytes, size);

  if (num_read != size)
    error_exit("unable to read file (something went really wrong)");

  return raw_instruction_bytes;
}

/*
 * Prints the opcode, register IDs, and immediate of every instruction,
 * assuming they have been decoded into the instructions array
 */
void print_instructions(instruction_t* instructions,
                        unsigned int num_instructions) {
  printf("instructions: \n");
  unsigned int i;
  for (i = 0; i < num_instructions; i++) {
    printf("op: %d, reg1: %d, reg2: %d, imm: %d\n", instructions[i].opcode,
           instructions[i].first_register, instructions[i].second_register,
           instructions[i].immediate);
  }
  printf("--------------\n");
}

/*
 * Prints an error and then exits the program with status 1
 */
void error_exit(const char* message) {
  printf("Error: %s\n", message);
  exit(1);
}
