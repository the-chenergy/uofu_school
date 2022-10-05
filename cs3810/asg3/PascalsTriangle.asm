# This program computes and prints out the first eleven rows of the Pascal's Triangle.
# 
# Qianlang Chen
# U1172983
# v1.0	M 09/09/19

#################################### DATA SEGMENT ####################################

.data

# Initialize the backing array with eleven integers.
# This backing array will represent a row in the triangle.
row:
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	0
	.word	1

#################################### TEXT SEGMENT ####################################

.text

# Program's entry point.
main:
	# Print the first row of the triangle.
	jal	print_row
	# Initialize the row counter.
	li	$t1,	10
	
	# For each next row...
	# [ for ($t1 = 10; $t1 > 0; $t1--) ]
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

# Prints all non-zero values in the current row of the triangle.
print_row:
	# Initialize the loop counter and load the address of the first "cell" in the 'row' array.
	li	$t7,	11
	la	$s7,	row
	
	# For each "cell" in the 'row' array...
	# [ for ($t7 = 11; $t7 > 0; $t7--) ]
	pr_loop_row:
		# Update the loop counter. [ $t7-- ]
		addi	$t7,	$t7,	-1
		# Load the value stored in the "cell".
		lw	$t6,	0($s7)
		
		# If the fetched value is not zero, print it out.
		# [ if (value != 0) ]
		beq	$t6,	$zero,	pr_if_t6_0	# skip if value is zero
			li	$v0,	1	# 1: print integer function code
			add	$a0,	$t6,	$zero
			syscall
			
			# Print a tab character to make it look nicer (only when it is necessary).
			# [ if ($t7 != 0) ] indicating there are more row left
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
compute_row:
	# Initialize the loop counter and load the address of the first "cell" in the 'row' array.
	li	$t7,	10
	la	$s7,	row
	
	# For each "cell" in the 'row' array (except for the last "cell")...
	# [ for ($t7 = 10; $t7 > 0; $t7--) ]
	cr_loop_row:
		# Update the loop counter. [ $t7-- ]
		addi	$t7,	$t7,	-1
		# Load the values stored in the current and the next "cell".
		lw	$t6,	0($s7)
		lw	$t5,	4($s7)	# a 4-byte offset should make it load the next "cell"
		# Perform the addition and store the result back.
		add	$t6,	$t6,	$t5
		sw	$t6,	0($s7)
		# Move on to the next cell.
		addi	$s7,	$s7,	4
		# Back to loop if needed.
		bne	$t7,	$zero,	cr_loop_row
	
	# Return.
	jr	$ra
