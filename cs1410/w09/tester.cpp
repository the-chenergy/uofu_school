/**
 * This project tests the Fraction class via creating some Fraction object
 * and doing some fun calculations with them.
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

int main()
{
  cout << "Hello, working..." << endl;
  
  // Create a few fraction objects
  
  Fraction a(1, 2);
  Fraction b(1, 6);
  
  Fraction e(1, 3);
  Fraction f(4, 5);
  Fraction g(1, 4);
  Fraction h(7, 8);
  Fraction j(2, 3);
  Fraction k;
  
  // Create a fraction as the result of computation
  
  int x = 7, y = x + 1;
  Fraction c(x, y);
  
  // Print them out
  
  cout << "Fraction A is: " << a.to_string() << endl
       << "Fraction B is: " << b.to_string() << endl
       << "Fraction C is: " << c.to_string() << endl;
  
  // Perform a bit of computation with fractions
  
  c = a.add(b);
  
  cout << a.to_string() << " plus " << b.to_string()
       << " equals " << c.to_string() << endl;
  
  c = a.multiply(b);
  
  cout << a.to_string() << " multiplied by "
       << b.to_string() << " equals " << c.to_string()
       << endl;
  
  // -------------------
  // Dividing test
  
  k = e.divide(f);
  
  cout << e.to_string() << " divided by " << f.to_string()
       << " equals " << k.to_string() << endl;
  
  k = f.divide(e);
  
  cout << f.to_string() << " divided by " << e.to_string()
       << " equals " << k.to_string() << endl;
  
  // Subtracting test
  
  k = g.subtract(h);
  
  cout << g.to_string() << " subtract " << h.to_string()
       << " equals " << k.to_string() << endl;
  
  k = h.subtract(g);
  
  cout << h.to_string() << " subtract " << g.to_string()
       << " equals " << k.to_string() << endl;
  
  // Power test
  
  int p = 3, q = 0, r = -2;
  
  k = j.power(p);
  
  cout << j.to_string() << " to the power of " << p
       << " equals " << k.to_string() << endl;
  
  k = j.power(q);
  
  cout << j.to_string() << " to the power of " << q
       << " equals " << k.to_string() << endl;
  
  k = j.power(r);
  
  cout << j.to_string() << " to the power of " << r
       << " equals " << k.to_string() << endl;
  
  // Convert a fraction to a double
  
  double d = c.to_double();
  
  cout << "The real number closest to " << c.to_string()
       << " is " << d << endl;
  
  // Done.
}
