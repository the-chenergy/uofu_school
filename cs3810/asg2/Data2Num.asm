# This piece of assembly code concats the digits at 3 different places in an array
# into an integer.
# Get the address of the Data variable and load in the three digits.
	la	$t0,	Data
	lw	$t1,	44($t0)		# 11th place * 4 bytes
	lw	$t2,	48($t0)		# 12 * 4
	lw	$t3,	52($t0)		# 13 * 4
# Now the three digits should be stored in t1, t2, and t3, respectively.
# Perform "t1 *= 100" by splitting it up into "t1 * 10 * 10".
	add	$t4,	$t1,	$t1
	add	$t4,	$t4,	$t1
	add	$t4,	$t4,	$t1
	add	$t4,	$t4,	$t1	# t1 * 5 so far
	add	$t4,	$t4,	$t4	# Now t4 should hold "t1 * 10"
	add	$t1,	$t4,	$t4
	add	$t1,	$t1,	$t4
	add	$t1,	$t1,	$t4
	add	$t1,	$t1,	$t4	# t4 * 5 so far
	add	$t1,	$t1,	$t1	# Now t1 should hold "t4 * 10"
# Perform "t2 *= 10" using similar ideas.
	add	$t4,	$t2,	$t2
	add	$t4,	$t4,	$t2
	add	$t4,	$t4,	$t2
	add	$t4,	$t4,	$t2	# t2 * 5 so far
	add	$t2,	$t4,	$t4	# Now t2 should hold "t2 * 10"
# Perform "t1 += t2 + t3".
	add	$t2,	$t1,	$t2
	add	$t1,	$t2,	$t3
# Load the address of the Num variable (and override the old Data's address)
# and store the rusult from t1.
	la	$t0,	Num
	sw	$t1,	0($t0)		# w/o offset
# Complete.