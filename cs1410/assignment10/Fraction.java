package assignment10;

public class Fraction {

	// ------ CONSTRUCTORS --------------------------------------------------------------------------

	/**
	 * Creates a new Fraction instance with a value of 0.
	 */
	public Fraction() {
		// Set this object's variables to represent 0.

		numerator = 0;
		denominator = 1;
	}

	/**
	 * Creates a new Fraction instance to store the specified values.
	 * 
	 * @param _numerator   The numerator of this Fraction.
	 * @param _denominator The denominator of this Fraction.
	 */
	public Fraction(long _numerator, long _denominator) {
		// Copy the parameter values into this object's variables.

		numerator = _numerator;
		denominator = _denominator;

		// Ensure the denominator is not negative.

		// (Students will add statements here for the assignment)

		// Reduce the fraction (as required by the class contract).
		// We'll just use our private function to help us.

		reduce();
	}

	// ------ PRIVATE FIELDS ------------------------------------------------------------------------

	/**
	 * The numerator of this Fraction.
	 * 
	 * @private
	 */
	private long numerator;

	/**
	 * The denominator of this Fraction.
	 * 
	 * @private
	 */
	private long denominator;

	// ------ PUBLIC FIELDS -------------------------------------------------------------------------

	// ------ PRIVATE METHODS -----------------------------------------------------------------------

	/**
	 * Reduces this Fraction to its simplest form.
	 * 
	 * @private
	 */
	private void reduce() {
		// Compute the greatest common divisor using a well-known algorithm.

		long gcd = numerator;
		long remainder = denominator;

		while (remainder != 0) {
			long temp = remainder;
			remainder = gcd % remainder;
			gcd = temp;
		}

		// Divide both the numerator and denominator by the greatest common divisor.

		numerator /= gcd;
		denominator /= gcd;
		
		// Make sure that only the numerator may be negative.
		
		if (denominator < 0)
		{
			numerator *= -1;
			denominator *= -1;
		}
	}

	// ------ PUBLIC METHODS ------------------------------------------------------------------------

	/**
	 * Returns the string representation of this object.
	 * 
	 * @return The string representation of this object.
	 */
	public String toString() {
		return numerator + "/" + denominator;
	}

	/**
	 * Returns the double that approximates this Fraction.
	 * 
	 * @return The double approximation of this object.
	 */
	public double toDouble() {
		return numerator / (double) denominator;
	}

	/**
	 * Adds this Fraction object to the provided Fraction object (without changing
	 * this object).
	 * 
	 * @param right Some other Fraction object.
	 * @return The Fraction representing the value of this object + right object.
	 */
	public Fraction add(Fraction right) {
		// Compute the sum

		long sum_numerator, sum_denominator;

		sum_numerator = numerator * right.denominator + right.numerator * denominator;

		sum_denominator = denominator * right.denominator;

		// Create the resulting fraction.

		Fraction result = new Fraction(sum_numerator, sum_denominator);

		// Return it.

		return result;
	}

	/**
	 * Subtracts the provided Fraction object from this Fraction object (without
	 * changing this object).
	 * 
	 * @param right Some other Fraction object.
	 * @return The Fraction representing the value of this object - right object.
	 */
	public Fraction subtract(Fraction right) {
		// Compute the difference.

		long diff_numerator = numerator * right.denominator - right.numerator * denominator;

		long diff_denominator = denominator * right.denominator;

		// Create the resulting fraction.

		Fraction result = new Fraction(diff_numerator, diff_denominator);

		// Return the result.

		return result;
	}

	/**
	 * Multiplies the provided Fraction object by this Fraction object (without
	 * changing this object).
	 * 
	 * @param right Some other Fraction object.
	 * @return The Fraction representing the value of this object * right object.
	 */
	public Fraction multiply(Fraction right) {
		// Compute the product

		long product_numerator, product_denominator;

		product_numerator = numerator * right.numerator;
		product_denominator = denominator * right.denominator;

		// Create the resulting fraction.

		Fraction result = new Fraction(product_numerator, product_denominator);

		// Return it.

		return result;
	}

	/**
	 * Divides this Fraction object by the provided Fraction object (without
	 * changing this object).
	 * 
	 * @param right Some other Fraction object.
	 * @return The Fraction representing the value of this object / right object.
	 */
	public Fraction divide(Fraction right) {
		// Compute the quotient.

		long quotient_numerator = numerator * right.denominator;
		long quotient_denominator = denominator * right.numerator;

		// Create the resulting fraction.

		Fraction result = new Fraction(quotient_numerator, quotient_denominator);

		// Return the result.

		return result;
	}

	/**
	 * Raises this Fraction object to some integer power.
	 * 
	 * @param x	The power to raise this Fraction to.
	 * @return	The Fraction representing the value of this object ^ power.
	 */
	public Fraction power(int x) {
		// Check if power is 0.

		if (x == 0)
			return new Fraction(1, 1);

		// Create a fraction to record the result.

		Fraction result = new Fraction(numerator, denominator);

		// Compute the result.

		int exp = Math.abs(x); // the number of multiplications needed.
		Fraction temp = new Fraction(numerator, denominator); // keep a copy of this object.

		while (--exp > 0)
			result = result.multiply(temp);

		// Return the result.

		if (x < 0)
			return new Fraction(1, 1).divide(result); // the recipical of the resulting fraction.

		return result;
	}

}
