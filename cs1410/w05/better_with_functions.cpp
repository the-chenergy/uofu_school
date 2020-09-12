/**
 * 
 * This is a EVEN better program to compute the surface area of a rectangular solid.
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Fri, Sept 14, 2018
 * 
 */

//////// INCLUDES ////////

#include <iostream>

using namespace std;

//////// FORWARD DECLARATIONS ////////

double get_positive_number(string msg, string error_msg);
double surface_area_of(double length, double width, double depth);

//////// MAIN FUNCTION ////////

/**
 * The main function of the program.
 *
 * Returns:
 *   int - the exit code of the program.
 */
int main() {
  double length, width, depth;
  
  // Get the inputs from the user. Meanwhile, filter invalid user inputs.
  
  length = get_positive_number("What's the length of the solid?", "Invalid length!");
  if (length == -1) return 0;
  
  width = get_positive_number("What about the width?", "Invalid width!");
  if (width == -1) return 0;
  
  depth = get_positive_number("What about the depth?", "Invalid depth!");
  if (depth == -1) return 0;
  
  // Calculates and outputs the surface area of the rectangular solid.
  
  cout << "The surface area of your solid is "
       << surface_area_of(length, width, depth) << " squared units!" << endl;
  
  //
  
  return 0;
}

//////// FUNCTIONS ////////

/**
 * Shows a text message and gets a positive number input from the user. Shows an error
 *   message if user gives an invalid input.
 * 
 * Params:
 *   string msg -- the message to show before user inputs.
 *   string error_msg -- the message to show when the user gives invalid input.
 * 
 * Returns:
 *   double -- a positive user-input, or -1 if the user gives an non-positive input.
 */
double get_positive_number(string msg, string error_msg) {
  double input;
  cout << msg << endl;
  cin >> input;
  
  // Filter invalid user input.
  if (input <= 0) {
    cout << error_msg << endl;
    
    return -1;
  }
  
  return input;
}

/**
 * Calculates the surface area of a rectangular solid.
 * 
 * Params:
 *   double length -- the length of the solid.
 *   double width -- the width of the solid.
 *   double depth -- the depth of the solid.
 * 
 * Returns:
 *   double -- the surface area of the rectangular solid.
 */
double surface_area_of(double length, double width, double depth) {
  return (length * width + length * depth + width * depth) * 2;
}
