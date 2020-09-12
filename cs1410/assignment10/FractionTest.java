package assignment10;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class FractionTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testFraction() {
		// Constructor (without arguments) test

		Fraction a = new Fraction();

		assertEquals("0/1", a.toString());
	}

	@Test
	void testFractionLongLong() {
		// Constructor (with arguments) test

		Fraction a = new Fraction(1, 6); // normal
		Fraction b = new Fraction(3, 2); // greater than 1
		Fraction c = new Fraction(-1, 12); // negative
		Fraction d = new Fraction(12, -48); // not in simplest form, negative denominator

		assertEquals("1/6", a.toString());
		assertEquals("3/2", b.toString());
		assertEquals("-1/12", c.toString());
		assertEquals("-1/4", d.toString());
	}

	@Test
	void testToString() {
		// Fraction.toString() test

		Fraction a = new Fraction(1, 2);

		assertEquals("1/2", a.toString());
	}

	@Test
	void testToDouble() {
		// Fraction.toDouble() test

		Fraction a = new Fraction(1, 2); // normal
		Fraction b = new Fraction(-1, 12); // unending
		Fraction c = new Fraction(3, -4); // negative denominator
		Fraction d = new Fraction(-3, -4); // negative both n/d

		assertEquals(0.5, a.toDouble(), 1e-12);
		assertEquals(-1. / 12, b.toDouble(), 1e-12);
		assertEquals(-0.75, c.toDouble(), 1e-12);
		assertEquals(0.75, d.toDouble(), 1e-12);
	}

	@Test
	void testAdd() {
		// Fraction.add() test

		Fraction a = new Fraction(1, 4).add(new Fraction(1, 2)); // 1/4 + 1/2
		Fraction b = new Fraction(1, 4).add(new Fraction(-1, 2)); // 1/4 + 1/-2
		Fraction c = new Fraction(4, 3).add(new Fraction()); // 4/3 + 0

		assertEquals("3/4", a.toString());
		assertEquals("-1/4", b.toString());
		assertEquals("4/3", c.toString());
	}

	@Test
	void testSubtract() {
		// Fraction.subtract() test

		Fraction a = new Fraction(1, 4).subtract(new Fraction(1, 2)); // 1/4 - 1/2
		Fraction b = new Fraction(1, 4).subtract(new Fraction(-1, 2)); // 1/4 - 1/-2
		Fraction c = new Fraction(4, 3).subtract(new Fraction()); // 4/3 - 0

		assertEquals("-1/4", a.toString());
		assertEquals("3/4", b.toString());
		assertEquals("4/3", c.toString());
	}

	@Test
	void testMultiply() {
		// Fraction.multiply() test

		Fraction a = new Fraction(1, 4).multiply(new Fraction(1, 2)); // (1/4) * (1/2)
		Fraction b = new Fraction(1, 4).multiply(new Fraction(-1, 2)); // (1/4) * (1/-2)
		Fraction c = new Fraction(4, 3).multiply(new Fraction()); // (4/3) * 0

		assertEquals("1/8", a.toString());
		assertEquals("-1/8", b.toString());
		assertEquals("0/1", c.toString());
	}

	@Test
	void testDivide() {
		// Fraction.divide() test

		Fraction a = new Fraction(1, 4).divide(new Fraction(1, 2)); // (1/4) / (1/2)
		Fraction b = new Fraction(1, 4).divide(new Fraction(-1, 2)); // (1/4) / (1/-2)
		Fraction c = new Fraction(4, 3).divide(new Fraction(1, 1)); // (4/3) / 1

		assertEquals("1/2", a.toString());
		assertEquals("-1/2", b.toString());
		assertEquals("4/3", c.toString());
	}

	@Test
	void testPower() {
		// Fraction.power() test

		Fraction a = new Fraction(3, 4).power(2); // (1/4) ^ -2
		Fraction b = new Fraction(3, 4).power(-2); // (1/4) ^ -2
		Fraction c = new Fraction(3, 4).power(0); // (1/4) ^ 0

		assertEquals("9/16", a.toString());
		assertEquals("16/9", b.toString());
		assertEquals("1/1", c.toString());
	}

}
