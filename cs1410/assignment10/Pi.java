package assignment10;

public class Pi {

	public static void main(String[] args) {
		// First loop: Calculate Pi as a Fraction.

		int k = 0; // the "index" of the summing process.
		Fraction fracPi = new Fraction(); // the fraction to record Pi as the calculation goes.

		while (true) {
			// Create some fractions necessary for the calculation.

			Fraction a = new Fraction(1, 16);
			Fraction b = new Fraction(4, 8 * k + 1);
			Fraction c = new Fraction(2, 8 * k + 4);
			Fraction d = new Fraction(1, 8 * k + 5);
			Fraction e = new Fraction(1, 8 * k + 6);

			// Sum up the results using a magic formula.

			Fraction newFracPi = fracPi.add(a.power(k).multiply(b.subtract(c).subtract(d).subtract(e)));

			// Check if overflow occurred in the calculation
			// (if the value is unreasonable such as too much off).

			// if the new value of Pi is not between 3 and 4.
			if (newFracPi.toDouble() < 3 || newFracPi.toDouble() > 4) {
				k--; // restore the value of k since the new one has caused it to overflow.

				break;
			}

			// Update the fraction storing the value of Pi.

			fracPi = newFracPi;
			k++;
		}

		// Output the results from the first loop.

		System.out.println("---- First Loop ----");
		System.out.println("Pi as a fraction: " + fracPi.toString());
		System.out.println("Pi as a double from the fraction: " + fracPi.toDouble());
		System.out.println("Value of k: " + k);

		// Second loop: Calculate but sum up the double values.

		k = 0; // reset the "index".
		double pi = 0; // the result of summing.

		while (true) {
			// Create some fractions for calculations.

			Fraction a = new Fraction(1, 16);
			Fraction b = new Fraction(4, 8 * k + 1);
			Fraction c = new Fraction(2, 8 * k + 4);
			Fraction d = new Fraction(1, 8 * k + 5);
			Fraction e = new Fraction(1, 8 * k + 6);

			// Calculate the new Pi.

			double newPi = pi + a.power(k).multiply(b.subtract(c).subtract(d).subtract(e)).toDouble();

			// Check if the new Pi is within a reasonable range.
			if (newPi < 3 || newPi > 4) {
				k--; // restore the value of k since the new one has caused it to overflow.

				break;
			}

			// Update the value of Pi.

			pi = newPi;
			k++;
		}

		// Output the results from the second loop.

		System.out.println("---- Second Loop ----");
		System.out.println("Pi as a double: " + pi);
		System.out.println("Value of k: " + k);
	}

}
