	.data
	
text:
	.asciiz	"It works!!"
	
text_newline:
	.asciiz	"\n"
			
	.text
	
main:
	li	$s0,	10			# $s0 is now a loop counter
	
loop_print_text:
	li	$v0,	4			# print string
	la	$a0,	text
	syscall
	
	li	$v0,	1			# print integer
	move	$a0,	$s0
	syscall
	
	li	$v0,	4
	la	$a0,	text_newline
	syscall
	
	addi	$s0,	$s0,	-1		# update the loop counter
	bne	$s0,	$zero,	loop_print_text	# go back to loop if not finished
	
	li	$v0,	10			# dispose program
	syscall
	
