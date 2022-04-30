# This program computes and prints out a given number of rows of the Pascal's Triangle.
# 
# Qianlang Chen
# U1172983
# (Extra Credit Version)
# 
# v1.2	H 09/12/19
#   - Refined the logic in get_array function.
# 
# v1.1	M 09/09/19
#   - Initial version.

#################################### DATA SEGMENT ####################################

.data

anr_welcome_text:
	.asciiz	"Hello there, welcome to the magic of the Pascal's Triangle!\n"

anr_ask_text:
	.asciiz	"How many rows of the Pascal's Triangle do you want to see?\n"

anr_error_text:
	.asciiz	"Whoops, the number of rows must be positive! "

anr_thank_text:
	.asciiz	"Thank you! Here is the Pascal's Triangle for you:\n"

cr_overflow_text:
	.asciiz	"Man, the numbers went way to big that they overflowed! :(\n"

#################################### TEXT SEGMENT ####################################

.text

# Program's entry point.
main:
	# Ask the user for the number of rows to display.
	jal	ask_num_rows
	# Request the system for the memory needed to store that many rows.
	jal	get_array
	# Print the first row of the triangle.
	jal	print_row
	# Initialize the row counter.
	addi	$t1,	$t0,	-1	# subtract one because the first row is already computed
	
	# For each next row...
	# [ for ($t1 = $t0 - 1; $t1 > 0; $t1--) ]
	m_loop_rows:
		# Update the loop counter. [ $t1-- ]
		addi	$t1,	$t1,	-1
		# Compute and print out the next row.
		jal	compute_row
		jal	print_row
		# Back to loop if needed.
		bne	$t1,	$zero,	m_loop_rows
	
	# Exit the program
	li	$v0,	10	# 10: dispose function code
	syscall

#################################### FUNCTIONS ####################################

# Asks the user for how many rows of the triangle they want to see.
# 
# Returns:
# $t0	The number (an integer value) the user answered.
ask_num_rows:
	# Print out the welcome text.
	li	$v0,	4	# 4: print string function code
	la	$a0,	anr_welcome_text
	syscall
	
	anr_loop_ask:
		# Print out the question.
		li	$v0,	4
		la	$a0,	anr_ask_text
		syscall
		# Get the result from the user.
		li	$v0,	5	# 5: get integer input function code
		syscall
		# Save the result.
		add	$t0,	$v0,	$zero
		
		# Check if the result is positive; break the loop if it is.
		slt	$t7,	$zero,	$t0	# [ $t7 = (0 < $t0) ]
		bne	$t7,	$zero,	anr_end_loop_ask	# [ if ($t7) break  ]
		
		# Since it's not positive, throw the user an error message, loop back and ask again.
		li	$v0,	4
		la	$a0,	anr_error_text
		syscall
		j	anr_loop_ask
		
		anr_end_loop_ask:
	
	# Thank the user for taking this program easy.
	li	$v0,	4
	la	$a0,	anr_thank_text
	syscall
	# Return. The number should already be stored in $t0.
	jr	$ra
	

# Requests the system for the space needed to store a given number of integer values.
# 
# Args:
# $t0	The number of integer values to store.
# 
# Returns:
# $s0	The address pointed to the first cell of the requested array.
get_array:
	# Make a copy of the number requested and calculate the bytes needed to store that many integers.
	add	$t7,	$t0,	$t0
	add	$t7,	$t7,	$t7	# 4 bytes per integer
	# Request the system for the memory.
	li	$v0,	9	# 9: request memory function code
	add	$a0,	$t7,	$zero	# make a copy for later to access the last element
	syscall
	# Store the return value ($v0)
	add	$s0,	$v0,	$zero
	# Store a 1 in the last cell of the array by looping.
	add	$s7,	$s0,	$t7
	addi	$s7,	$s7,	-4	# the last cell starts at (total bytes - 4)
	li	$t6,	1
	sw	$t6,	0($s7)
	# Return. The address should already be stored in $s0.
	jr	$ra

# Prints all non-zero values in the current row of the triangle.
# 
# Args:
# $s0	The address pointed to the first cell of the row array.
print_row:
	# Initialize the loop counter and the address of the array.
	add	$t7,	$t0,	$zero
	add	$s7,	$s0,	$zero
	
	# For each cell in the row array...
	# [ for ($t7 = $t0; $t7 > 0; $t7--) ]
	pr_loop_row:
		# Update the loop counter. [ $t7-- ]
		addi	$t7,	$t7,	-1
		# Load the value stored in the cell.
		lw	$t6,	0($s7)
		
		# If the fetched value is not zero, print it out.
		# [ if (value != 0) ]
		beq	$t6,	$zero,	pr_if_t6_0	# skip if value is zero
			li	$v0,	1	# 1: print integer function code
			add	$a0,	$t6,	$zero
			syscall
			
			# Print a tab character to make it look nicer (only when it is necessary).
			# [ if ($t7 != 0) ] indicating there are more rows left
			beq	$t7,	$zero,	pr_if_t7_0
				li	$v0,	11	# 11: print character function code
				la	$a0,	'\t'
				syscall
				pr_if_t7_0:	# end if
			
			pr_if_t6_0:	# end if
		
		# Move on to the next cell.
		addi	$s7,	$s7,	4
		# Back to loop if needed.
		bne	$t7,	$zero,	pr_loop_row
	
	# Print a line-break.
	li	$v0,	11
	la	$a0,	'\n'
	syscall
	# Return.
	jr	$ra

# Computes the values for the next row of the triangle. (Makes it move on to the next row.)
# 
# Args:
# $s0	The address pointed to the first cell of the row array.
compute_row:
	# Initialize the loop counter and make a copy of the address of the array.
	addi	$t7,	$t0,	-1	# subtract one because the last row is always 1.
	add	$s7,	$s0,	$zero
	
	# For each cell in the row array (except for the last cell)...
	# [ for ($t7 = $t0; $t7 > 0; -$t7--) ]
	cr_loop_row:
		# Update the loop counter. [ $t7-- ]
		addi	$t7,	$t7,	-1
		# Load the values stored in the current and the next cell.
		lw	$t6,	0($s7)
		lw	$t5,	4($s7)	# a 4-byte offset should make it load the next cell
		# Perform the addition.
		addu	$t6,	$t6,	$t5
		
		# Check if overflow occurred. (Overflow should make the result negative.)
		slt	$t5,	$t6,	$zero	# [ $t5 = ($t6 < 0) ]
		beq	$t5,	$zero,	cr_if_t5_0	# [ if (!$t5) ]
			# Print out overflow error message.
			li	$v0,	4
			la	$a0,	cr_overflow_text
			syscall
			# Exit the program straight away
			li	$v0,	10
			syscall
			cr_if_t5_0:
		
		# Store the result back to the row array.
		sw	$t6,	0($s7)
		# Move on to the next cell.
		addi	$s7,	$s7,	4
		# Back to loop if needed.
		bne	$t7,	$zero,	cr_loop_row
	
	# Return.
	jr	$ra
