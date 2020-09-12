#include <iostream>
#include <cmath>

/**
 * This is a program that will convert a given 
 * temperature in Fahrenheit to Celsius.
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
  // ask for an interger input for the fahrenheit temp.
  int input;
  std::cout << "Hi! What's the temperature degrees in Fahrenheit?\n";
  std::cin >> input;

  // calculate and display the converted temp in celcius.
  std::cout << "It's "
	    << (input - 32) * 5 / 9
	    << " degrees in Celcius!\n";
}
