	.data
	
# The "nums" variables for later testing
# Since it contains ten integers (in a row), it can be treated and used as an array
nums:
	.word	42
	.word	142
	.word	242
	.word	342
	.word	442
	.word	542
	.word	642
	.word	742
	.word	842
	.word	942
	
text:
	.asciiz	"It works!!"
			
	.text
	
main:
	li	$s0,	10			# $s0 is now a loop counter
	
# Load the "nums" variable "array" into $t1
	la	$t1,	nums
	
# Loop the "nums" array and increment each "cell"
loop_print_text:
	li	$v0,	4			# print string
	la	$a0,	text
	syscall
	
# Increment the current "cell" in the "nums" array
	lw	$t2,	($t1)
	addi	$t2,	$t2,	1
	sw	$t2,	($t1)
	
# print out the content of the "current cell"
	li	$v0,	1			# print integer
	move	$a0,	$t2
	syscall
	
# Progresses the current "cell" (by offsetting the pointer by 4 bytes)
	addi	$t1,	$t1,	4
	
# Make a line-break
	jal	print_newline
	
	addi	$s0,	$s0,	-1		# update the loop counter
	bne	$s0,	$zero,	loop_print_text	# go back to loop if not finished
	
	li	$v0,	10			# dispose program
	syscall

######################## FUNCTIONS ########################

# Prints a line-break character into the console.
print_newline:
	li	$v0,	11			# print character
	li	$a0,	'\n'
	syscall
	
	jr	$ra				# return the function (back to whoever called it)
