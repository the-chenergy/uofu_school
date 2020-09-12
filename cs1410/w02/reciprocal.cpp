#include <iostream>
#include <cmath>

/**
 * This is a cute little program that will ask the users
 * for a numeric input and output the reciprocal of the
 * input value.
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Tue, Aug 26, 2018
 * 
 */

int main()
{
  // ask the user for a numeric input.
  double input;
  std::cout << "Hi! Give me a numeric value!\n";
  std::cin >> input;

  // calculate the reciprocal of the given value,
  //   then display it with friendly texts.
  std::cout << "The reciprocal of the given value is "
	    << (1 / input)
	    << "!\n";
}
