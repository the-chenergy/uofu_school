#include <iostream>
#include <cmath>

/**
 * This is a program that will help the users to solve
 * quadratic polynomial equations. The users must have
 * their equations written as "ax^2+bx+c=0". This
 * program will let the users input the a, b, and c
 * values and compute the solution of x (if one exists).
 * 
 * (WARNING: if the equation input has no solution
 * i.e. delta < 0,
 * this program is going to display NaN and NaN as the
 * results.)
 * 
 * PS: Why don't expect me to write the extra code for 
 * solving the error? It's easy and fun, and also it
 * should be part of the program anyways!! ;)
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Tue, Aug 28, 2018
 * 
 */

int main()
{
  // ask for a, b, and c values.
  double a, b, c;

  std::cout << "Hi! You should have your quadratic equation written in the format of \"ax^2+bx+c=0\"! What's the value of a?\n";
  std::cin >> a;

  std::cout << "What about b?\n";
  std::cin >> b;

  std::cout << "What about c?\n";
  std::cin >> c;

  // calculate the solutions of the given equation
  //   using the quadratic formula.
  double delta = std::sqrt(b * b - 4 * a * c); // this may return NaN
  double x0 = (-b - delta) / (2 * a);
  double x1 = (-b + delta) / (2 * a);

  // display the solutions with friendly comments.
  if (x0 == x1) // when the two roots are equal
  {
    std::cout << "Your equation has two same solutions: "
	      << x0 << ".\n";
  }
  else // when the two roots are not equal
  {
    std::cout << "Your equation has two solutions: "
	      << x0 << " and " << x1 << ".\n";
  }
}
