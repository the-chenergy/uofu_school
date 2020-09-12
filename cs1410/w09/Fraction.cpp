#include <string>
#include <sstream>
#include <cmath>
#include "Fraction.h"

/* Fraction constructor:
 *
 * This default constructor creates the fraction
 * representing the number 0.
 *
 * (Notice that no return type is specified.  The
 * function name is the class name for a constructor.)
 */
Fraction::Fraction()
{
  // Set this object's variables to represent 0.
  
  numerator = 0;
  denominator = 1;
}

/* Fraction constructor:
 *
 * This constructor creates the fraction
 * representing the number specified by the numerator
 * and denominator parameters, i.e. numerator/denominator.
 *
 * The denominator is not allowed to be 0.  (No error
 * checking is done -- you haven't learned exceptions yet.)
 *
 * (Notice that no return type is specified.  The
 * function name is the class name for a constructor.)
 *
 * Parameters:
 *   long long _numerator -- some numerator
 *   long long _denominator -- some denominator
 */
Fraction::Fraction(long long _numerator, long long _denominator)
{
  // Copy the parameter values into this object's variables.
  
  numerator = _numerator;
  denominator = _denominator;
  
  // Ensure the denominator is not negative.
  
  // (Students will add statements here for the assignment)
  
  // Reduce the fraction (as required by the class contract).
  //   We'll just use our private function to help us.
  
  reduce();
}

/* This function reduces this fraction object.  It first
 * computes the greatest common divisor of the numerator
 * and denominator.  Then, it divides both the numerator
 * and denominator by that greatest common divisor.
 */
void Fraction::reduce()
{
  // Compute the greatest common divisor using a
  //  well-known algorithm.
  
  long long gcd = numerator;
  long long remainder = denominator;
  
  while (remainder != 0)
  {
    long long temp = remainder;
    remainder = gcd % remainder;
    gcd = temp;
  }

  // Divide both the numerator and denominator by the
  //   greatest common divisor

  numerator /= gcd;
  denominator /= gcd;
}

/* Returns a string that contains text representing
 * this fraction in the form "numerator/denominator".
 *
 * Returns:
 *   a string -- the fraction converted to a string
 */
std::string Fraction::to_string()
{
  std::stringstream buffer;
  buffer << numerator << "/" << denominator;

  return buffer.str();
}

/* Returns a double value that is the approximation
 * of this fraction object.
 *
 * Returns:
 *   a double -- a floating point approximation of
 *               the number represented by this Fraction
 */
double Fraction::to_double()
{
  return numerator/(double)denominator;
}

/* Adds this fraction object to the provided fraction
 * object (without changing this object).  The sum
 * is returned in a separate fraction object.
 *
 * Parameters:
 *   Fraction right -- some other fraction object
 *
 * Returns:
 *   A Fraction object -- represents the value equal to
 *                        this object + right object.
 */
Fraction Fraction::add(Fraction right)
{
  // Compute the sum
  
  long long sum_numerator, sum_denominator;
  
  sum_numerator = numerator         * right.denominator +
    right.numerator * denominator;
  
  sum_denominator = denominator * right.denominator;
  
  // Create the resulting fraction.
  
  Fraction result(sum_numerator, sum_denominator);
  
  // Return it.
  
  return result;
}

/* Subtracts the provided fraction from this fraction object.
 * (without changing this object). The difference is returned in
 * a separate fraction object.
 * 
 * Parameters:
 *   Fraction right -- some other fraction object.
 * 
 * Returns:
 *   A fraction object representing the value equal
 * to this object - right object.
 */
Fraction Fraction::subtract(Fraction right)
{
  // Compute the difference.
  
  long long diff_numerator = numerator * right.denominator
    - right.numerator * denominator;
  
  long long diff_denominator = denominator * right.denominator;
  
  // Create the resulting fraction.
  
  Fraction result(diff_numerator, diff_denominator);
  
  // Return the result.
  
  return result;
}

/* Multiplies this fraction object with the provided fraction
 * object (without changing this object).  The product
 * is returned in a separate fraction object.
 *
 * Parameters:
 *   Fraction right -- some other fraction object
 *
 * Returns:
 *   A Fraction object -- represents the value equal to
 *                        this object * right object.
 */
Fraction Fraction::multiply(Fraction right)
{
  // Compute the product
  
  long long product_numerator, product_denominator;
  
  product_numerator   = numerator   * right.numerator;
  product_denominator = denominator * right.denominator;

  // Create the resulting fraction.
  
  Fraction result(product_numerator, product_denominator);

  // Return it.
  
  return result;
}

/* Divides this fraction object by the provided fraction object.
 * (without changing this object). The quotient is returned in
 * a separate fraction object.
 * 
 * Parameters:
 *   Fraction right -- some other fraction object.
 * 
 * Returns:
 *   A Fraction object representing the value equal to
 * this object / right object.
 */
Fraction Fraction::divide(Fraction right)
{
  // Compute the quotient.
  
  long long quotient_numerator = numerator * right.denominator;
  long long quotient_denominator = denominator * right.numerator;
  
  // Create the resulting fraction.
  
  Fraction result(quotient_numerator, quotient_denominator);
  
  // Return the result.
  
  return result;
}

/* Returns the fraction representing the result of
 * this fraction object raised to some integer power
 * (without changing this object).
 * 
 * Parameters:
 *   int x -- the power to raise this fraction to.
 * 
 * Returns:
 *   The fraction object representing the value equal to
 * this object ^ x.
 */
Fraction Fraction::power(int x)
{
  // Check if power is 0.
  
  if (x == 0)
  {
    Fraction one(1, 1);
    return one;
  }
  
  // Create a fraction to record the result.
  
  Fraction result(numerator, denominator);
  
  // Compute the result.
  
  int exp = std::abs(x); // the number of multiplications needed.
  Fraction temp(numerator, denominator); // keep a copy of this object.
  
  while (--exp)
    result = result.multiply(temp);
  
  // Return the result.
  
  if (x < 0)
  {
    Fraction one(1, 1);
    return one.divide(result); // the recipical of the resulting fraction. 
  }
  
  return result;
}
