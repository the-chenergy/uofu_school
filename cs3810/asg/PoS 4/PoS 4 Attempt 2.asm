##############################################################################
# Proof-of-Study #4 
# 
# This program (when complete) will sort an array of numbers and print them
# out.  Your task is to hand-compile the code for the function that sorts
# the numbers.  You MUST follow the code outline - do not rearrange or
# optimize the algorithm.  Just convert each line of the algorithm
# into a small group of assembly statements.
#
# Several test cases are given below - feel free to adjust the code in 
# 'main' to test one of the other arrays.
#
# P.S.  I've always loved assembly language coding for breaking tasks into
# small, simpler steps.  It was great fun to write the solution, and I hope
# you find a bit of elegance or joy in your coding.  -- PJ
##############################################################################

# Data memory below this point.				
	.data
	
TestArray1:  # 3 elements
	.word 68, 4, 19	
	
TestArray2:  # 9 elements
	.word 9, 3, 7, 5, 1, 2, 4, 6, 8
	
TestArray3:  # 20 elements
        .word 20, 41, 38, 15, 96, 23, 13, 92, 91, 25, 17, 14, 40, 79, 51, 11, 15, 53, 64, 81 
        
Message1:
	.asciiz "*** Program has started. ***\n" 	
	
Message2:
	.asciiz "Unsorted data: " 	
	
Message3:
	.asciiz "Sorted data: " 	
	
Message4:
     	.asciiz "The number 42 from the stack, printed twice: "	
	
Message5:
	.asciiz "*** Program has ended. ***\n" 	

# Students should place additional asciiz data (and labels) below this line.

#------ Used by the sort() function ------

sort_enter_message:
	.asciiz	"Entering sort: "

sort_leave_message:
	.asciiz	"Leaving sort: "
			
# Program memory below this point.				
	.text
	
Main:
	# Caution - Remember - You can change the code in Main for testing but
	# we will replace this code with our own code for official tests.  Do
	# NOT do anything here that your sort function will depend on.

	# Before doing anything else, print a message to the screen.
	#   Normally you wouldn't do this, but for testing it is nice to know
	#   that your program is running.
	
	la   $a0, Message1
	li   $v0, 4         # Print string syscall code
	syscall

	# Give our 'main' function a stack frame.  We won't use it, but we
	#   make sure to have one.  For testing, I'll create a frame large
	#   enough to hold ten words, and I'll put a 42 in the first position
	#   just for fun.  Also, because I use $s0 and $s1 below, I'll preserve them,
	#   and I'll also preserve the old frame pointer (whatever it was).
	
	li   $t0, 42        # I am doing this just for testing, no good reason.
	
	addi $sp, $sp, -40  # Create my stack frame.  (Really, just keep track of a new, smaller address.)
	sw   $fp, 12($sp)   # Preserve $fp by storing it in our stack frame.
	sw   $s1,  8($sp)   # Preserve $s1 by storing it in our stack frame.
	sw   $s0,  4($sp)   # Preserve $s0 by storing it in our stack frame.
	sw   $t0,  0($sp)   # Put a 42 in our frame (just to show you it can be done).
	addi $fp, $sp, 36   # The frame pointer address is now the address of 
	                    #   the top word (the tenth word, word 9) of our stack frame).
	                    
	# Set up for testing:
	#   $s0 - contains the base address of an array.
	#   $s1 - contains the length of an array.
	
	la   $s0, TestArray1
	la   $s1, 3
	  
	# Print out the unsorted array.
	
	la   $a0, Message2
	li   $v0, 4         # Print string syscall code
	syscall

        move $t0, $s0       # Copy the array base into $t0
        move $t1, $s1       # Copy the array length into $t1
                            #   $t1 represents the number of values we need to print        
Loop1Top:
        beq  $t1, $zero, Loop1End # If we have zero items left to print, exit the loop.
        
        lw   $a0, 0($t0)    # Load an integer from the array
        li   $v0, 1         # Print integer syscall code
	syscall
	
        li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	addi $t1, $t1, -1   # We now have one less item to print.
	addi $t0, $t0, 4    # Advance the address to the address of the next item.
	j    Loop1Top       # Go back and loop again.

Loop1End:

        li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# Next, go sort the array.  (Students will need to write this function
	#   or else the code will stop working at this point.  Students should not
	#   change the way this function is called.  Students should also not add
	#   code to "Main" to preserve anything else on the stack.)
	
	move $a0, $s0       # Copy the array address into the first parameter.
	li   $a1, 0         # Set the low index to 0 (the index of the first array element)
	addi $a2, $s1, -1   # Set the high index to length-1 (the index of the last array element)
	jal  Sort           # Sort the array from positions lowIndex to highIndex (inclusive)

	# Print out the sorted array.
	
	la   $a0, Message3
	li   $v0, 4         # Print string syscall code
	syscall

        move $t0, $s0       # Copy the array base into $t0
        move $t1, $s1       # Copy the array length into $t1
                            #   $t1 represents the number of values we need to print        
Loop2Top:
        beq  $t1, $zero, Loop2End # If we have zero items left to print, exit the loop.
        
        lw   $a0, 0($t0)    # Load an integer from the array
        li   $v0, 1         # Print integer syscall code
	syscall
	
        li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	addi $t1, $t1, -1   # We now have one less item to print.
	addi $t0, $t0, 4    # Advance the address to the address of the next item.
	j    Loop2Top       # Go back and loop again.

Loop2End:

        li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# I stored a number 42 into the stack earlier.  Make sure it is still there, and
	#   use both the stack pointer and the frame pointer to access it.
	#   (This way, we make sure that the student has correctly restored the stack
	#   and that the student has not accidentally overwritten our data.)
	
	la   $a0, Message4
	li   $v0, 4         # Print string syscall code
	syscall
	
	lw   $a0, 0($sp)    # Load the 42 from our frame, use the stack pointer base address.
	li   $v0, 1         # Print integer syscall code
	syscall
	
	li   $a0, 32        # ASCII code for a space character
	li   $v0, 11        # Print character syscall code
	syscall
	
	lw   $a0, -36($fp)  # Load the 42 from our frame, use the frame pointer base address.
	li   $v0, 1         # Print integer syscall code
	syscall
	
	li   $a0, 10        # ASCII code for a newline character
	li   $v0, 11        # Print character syscall code
	syscall
	
	# Our 'Main' function is done.  Restore saved values from the stack frame and
	#   'remove' the stack frame.  We just adjust the $sp address, but this
	#   is how we keep track of stack frames, so the frame is 'removed'.  Since
	#   we grew the stack by 40 bytes above, we'll shrink it by 40 here.  (Remember,
	#   the stack 'grows' down, lower memory addresses = larger stack.)
	#
	# Important:  Don't 'remove' the frame until we have restored the data from it.
	
	lw   $fp, 12($sp)   # Preserve $fp by storing it in our stack frame.
	lw   $s1,  8($sp)   # Preserve $s1 by storing it in our stack frame.
	lw   $s0,  4($sp)   # Preserve $s0 by storing it in our stack frame.
	addi $sp, $sp, 40   # Remove my stack frame.  (Really, just keep track of a new, larger address.)
	                    
	# Now that the program has finished, print a message to the screen.
	#   Normally you wouldn't do this, but for testing it is nice to know
	#   that your program has completed normally.
	
	la   $a0, Message5
	li   $v0, 4         # Print string syscall code
	syscall
	
	# Exit gracefully.

	li   $v0, 10         # Terminate program syscall code
	syscall
        # Simulator will now stop executing statements.

##############################################################################

# Student code for the sort function should go here.  I deleted my solution
#   code below, but I left my comments behind.  (Some comments I replaced with ???
#   because they gave away exactly what to do.)

# void sort (int[] data, int lowIndex, int highIndex)
Sort:
	# Create a stack frame for the current method call.
	addi	$sp,	$sp,	-20
	sw	$fp,	0($sp)
	sw	$s1,	4($sp)
	sw	$s2,	8($sp)
	sw	$s3,	12($sp)
	sw	$ra,	16($sp)
	addi	$fp,	$sp,	16
	
	# (Move arguments into their registers)
	move	$s1,	$a0
	move	$s2,	$a1
	move	$s3,	$a2
		
	# print ("Entering sort: ")
	li	$v0,	4			# syscall 4: print ascii string
	la	$a0,	sort_enter_message
	syscall
        
        # print (lowIndex)
        li	$v0,	1			# syscall #1: print integer
        move	$a0,	$s2
        syscall
	
        # print (" ")
        li	$v0,	11			# syscall #11: print character
        li	$a0,	' '
        syscall
	
        # print (highIndex)
        li	$v0,	1			# syscall #1: print integer
        move	$a0,	$s3
        syscall
        	
        # print ("\n")
        li	$v0,	11			# syscall #11: print character
        li	$a0,	'\n'
        syscall
	
        # if (lowIndex  >=  highIndex)
	slt	$t7,	$s2,	$s3		# t7 = (low < high)
	beq	$t7,	$zero,	sort_return	# if (!t7) return
        
    	# centerIndex = lowIndex
    	move	$t2,	$s2			# t2 = center = low

    	# centerValue = data[highIndex]
    	sll	$t4,	$s3,	2
    	add	$t4,	$t4,	$s1		# t4 = &data[high]
    	lw	$t3,	0($t4)			# t3 = centerValue = data[high]
    	
    	# tempIndex   = centerIndex
    	move	$t0,	$t2			# t0 = temp = center
    	
    	# while (tempIndex < highIndex)    	
sort_loop:
	slt	$t7,	$t0,	$s3		# t7 = (temp < high)
	beq	$t7,	$zero,	sort_loop_end	# if (!t7) break
    	        
    	#    tempValue   = data[tempIndex]
    	sll	$t7,	$t0,	2
    	add	$t7,	$t7,	$s1		# t7 = &data[temp]
    	lw	$t1,	0($t7)			# t1 = tempValue = data[temp]
    	
   	#    if (tempValue  < centerValue)
   	slt	$t6,	$t1,	$t3		# t6 = (tempValue < centerValue)
   	beq	$t6,	$zero,	sort_if_t1_ge_t3# if (!t6) goto
   	
   	#        data[tempIndex]   = data[centerIndex]
   	sll	$t6,	$t2,	2
   	add	$t6,	$t6,	$s1		# t6 = &data[center]
   	lw	$t5,	0($t6)			# t5 = data[center]
   	
   	sw	$t5,	0($t7)			# data[temp] = data[center]
    	
   	#        data[centerIndex] = tempValue 
   	sw	$t1,	0($t6)			# data[center] = tempValue
    	
   	#        centerIndex       = centerIndex + 1
   	addi	$t2,	$t2,	1
   	
   	# (end if)
sort_if_t1_ge_t3:
   	
   	#    tempIndex = tempIndex + 1
   	addi	$t0,	$t0,	1
   	
        # While loop ends.
        j	sort_loop
sort_loop_end:
        
   	# data[highIndex]   = data[centerIndex]
   	sll	$t6,	$t2,	2
   	add	$t6,	$t6,	$s1
   	lw	$t5,	0($t6)			# t5 = data[center]
   	sw	$t5,	0($t4)			# data[high] = t5
        
        # data[centerIndex] = centerValue
        sll	$t4,	$t2,	2
        add	$t4,	$t4,	$s1
        sw	$t3,	0($t4)
        
   	# Expand the stack to store the current center index (t2).
   	addi	$sp,	$sp,	-4
   	sw	$t2,	0($sp)
   	
        # sort (data, centerIndex+1, highIndex)
        move	$a0,	$s1
        addi	$a1,	$t2,	1
        move	$a2,	$s3
        jal	Sort
	
	# Restore the stack (and the center index)
   	lw	$t2,	0($sp)
   	addi	$sp,	$sp,	4
   	
   	# (t0 is not needed anymore so we don't store it for the next function call.)
        # sort (data, lowIndex, centerIndex-1)
        move	$a0,	$s1
        move	$a1,	$s2
        addi	$a2,	$t2,	-1
        jal	Sort
        
sort_return:
	# print ("Leaving sort: ")
	li	$v0,	4			# syscall 4: print ascii string
	la	$a0,	sort_leave_message
	syscall
        
        # print (lowIndex)
        li	$v0,	1			# syscall #1: print integer
        move	$a0,	$s2
        syscall
	
        # print (" ")
        li	$v0,	11			# syscall #11: print character
        li	$a0,	' '
        syscall
	
        # print (highIndex)
        li	$v0,	1			# syscall #1: print integer
        move	$a0,	$s3
        syscall
        	
        # print ("\n")
        li	$v0,	11			# syscall #11: print character
        li	$a0,	'\n'
        syscall
	
	# Restore the stack frame.
	lw	$fp,	0($sp)
	lw	$s1,	4($sp)
	lw	$s2,	8($sp)
	lw	$s3,	12($sp)
	lw	$ra,	16($sp)
	addi	$sp,	$sp,	20
	
    	# return

    	jr $ra     # I put this in so I could test my main function.
