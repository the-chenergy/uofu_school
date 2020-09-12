/**
 * This is a BETTER program to compute the surface area
 * of a rectangular solid.
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Sat, Sept 1, 2018
 * 
 */

#include <iostream>
#include <cmath>

int main()
{
  // ask for the inputs of the length, width, and depth of
  //   the solid and reject invalid inputs.
  double width;
  std::cout << "Hi! What's the width (in meters) of the rectangular solid?" << std::endl;
  std::cin >> width;
  if (width <= 0) {
    std::cout << "Invalid width! It should be a positive number!" << std::endl;
    return 0;
  }

  double length;
  std::cout << "What about the length (in meters)?\n";
  std::cin >> length;
  if (length <= 0) {
    std::cout << "Invalid length! It should be a positive number!" << std::endl;
    return 0;
  }

  double depth;
  std::cout << "What about the depth (in meters)?\n";
  std::cin >> depth;
  if (depth <= 0) {
    std::cout << "Invalid depth! It should be a positive number!" << std::endl;
    return 0;
  }

  // calculate the surface area of the given rectangular solid
  //   and display it.

  std::cout << "The surface area of your solid is "
	    << (2 * (length * width + length * depth + width * depth))
	    << " square meters!" << std::endl;
}
