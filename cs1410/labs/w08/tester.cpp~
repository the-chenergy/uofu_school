#include <iostream>
#include "Fraction.h"

using namespace std;

int main()
{
  cout << "Hello, working..." << endl;
  
  // Create a few fraction objects
  
  Fraction a(1, 2);
  Fraction b(1, 6);
  
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
  
  // Convert a fraction to a double
  
  double d = c.to_double();
  
  cout << "The real number closest to " << c.to_string()
       << " is " << d << endl;
  
  // Done.
}
