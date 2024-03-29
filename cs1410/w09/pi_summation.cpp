/**
 * This program calculates Pi by a good old way, using the Fraction
 * class.
 * 
 * ============
 * 
 * Qianlang Chen
 * u1172983
 * 10/20/18 A
 */

#include <iostream>
#include "Fraction.h"

using namespace std;

/** Application entry point. **/
int main() {
  // First loop: Calculate Pi as a Fraction.
  
  int k = 0; // the "index" of the summing process.
  Fraction frac_pi; // the fraction to record Pi as the calculation goes.
  
  while (true) {
    // Create some fractions necessary for the calculation.
    
    Fraction a(1, 16);
    Fraction b(4, 8 * k + 1);
    Fraction c(2, 8 * k + 4);
    Fraction d(1, 8 * k + 5);
    Fraction e(1, 8 * k + 6);
    
    // Sum up the results using a magic formula.
    
    Fraction new_frac = frac_pi.add(a.power(k).multiply(
      b.subtract(c).subtract(d).subtract(e))
    );
    
    // cout << k << "\t" << new_frac.to_double() << endl;
    
    // Check if overflow occured in the calculation
    //   (if the value is unreasonable such as too much off).
    
    // if the new value of Pi is not between 3 and 4.
    if (new_frac.to_double() < 3 || new_frac.to_double() > 4) {
      k--; // restore the value of k since the new one has
      // caused it to overflow.
      break;
    }
    
    // Update the fraction storing the value of Pi.
    
    frac_pi = new_frac;
    k++;
  }
  
  // Output the results from the first loop.
  
  cout << "---- First Loop ----\n"
       << "Pi as a fraction: " << frac_pi.to_string() << "\n"
       << "Pi as a double from the fraction: " << frac_pi.to_double() << "\n"
       << "Value of k: " << k << endl;
  
  // Second loop: Calculate but sum up the double values.
  
  k = 0; // reset the "index".
  double pi = 0; // the result of summing.
  
  while (true) {
    // Create some fractions for calculations.
    
    Fraction a(1, 16);
    Fraction b(4, 8 * k + 1);
    Fraction c(2, 8 * k + 4);
    Fraction d(1, 8 * k + 5);
    Fraction e(1, 8 * k + 6);
    
    // Calculate the new Pi.
    
    double new_pi = pi + a.power(k).multiply(
      b.subtract(c).subtract(d).subtract(e)
    ).to_double();
    
    // cout << k << "\t" << new_pi << endl;
    
    // Check if the new Pi is within a reasonable range.
    if (new_pi < 3 || new_pi > 4) {
      k--;
      
      break;
    }
    
    // Update the value of Pi.
    
    pi = new_pi;
    k++;
  }
  
  // Output the results from the second loop.
  
  cout << "---- Second Loop ----\n"
       << "Pi as a double: " << pi << "\n"
       << "Value of k: " << k << endl;
  
  // Done!
  
  return 0;
}
