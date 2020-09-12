/**
 * 
 * This program simply calculates and outputs the first 15
 * rows of the Pascal's triangle.
 * 
 * ========
 * 
 * Qianlang Chen
 * u1172983
 * Mon, Sept 16, 2018
 * 
 */

//////// INCLUDES ////////

#include <iostream>
#include <string>
#include <sstream>

using namespace std;

//////// FORWARD DECLARATIONS ////////

int pascal(int row, int column);

//////// MAIN FUNCTION ////////

/**
 * The main function of the program.
 *
 * Returns:
 *   int -- the exit code of the program.
 */
int main() {
  int total_rows = 15;
  cout << pascal(5, 2) << endl;
  ostringstream stream; // a string stream to store the output strings.
  
  // Calculate the length of the last line of the Pascal's Triangle.
  
  for (int column = 0; column <= total_rows; column++)
    stream << pascal(total_rows, column) << " ";
  
  int max_length = stream.str().length(); // the length of the text -
  // of the last line of the triangle.
  
  // For each row and columns
  for (int row = 0; row < total_rows; row++) {
    stream.str(""); // clear the previous strings in the string stream.
    
    // Write everything into a stream to calculate the length of the line.
    for (int column = 0; column <= row; column++)
      stream << pascal(row, column) << " ";
    
    string line = stream.str();
    int length = line.length();
    int space_length = (max_length - length) / 2;
    
    // Spacing...
    for (int space = 0; space < space_length; space++)
      cout << " ";
    
    // Output the line.
    cout << line << endl;
  }
  
  return 0;
}

//////// FUNCTIONS ////////

/**
 * Calculates the number at a certain location
 * in the Pascal's triangle.
 * 
 * Params:
 *   int row -- the row of the location to be
 *     calculated.
 *   int column -- the column of the location
 *     to be calculated.
 * 
 * Returns:
 *   int -- the number at the location in the
 *     Pascal's triangle.
 */
int pascal(int row, int column) {
  if (column == 0 || column == row)
    return 1;
  
  return pascal(row - 1, column) + pascal(row - 1, column - 1);
}
