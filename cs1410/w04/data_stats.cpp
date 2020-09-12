#include <iostream>
#include <fstream>

/**
 * 
 * This program reads all numbers in "./data.txt" and
 * outputs the count, sum, and mean of the numbers read.
 * 
 * ========
 * 
 * Qianlang Chen
 * u1172983
 * Sat, Sept 8, 2018
 * 
 */

using namespace std;

int main() {
  // Open a file for later reading.
  ifstream file("data.txt");
  
  // Todo: study the code to detect if file doesn't exist
  
  // Read all numbers from the file, meanwhile calculate
  //   the count and sum of the numbers.
  
  int count = 0;
  double sum = 0;
  
  // Use a loop to read the numbers until it fails.
  while (true) {
    double value;
    file >> value; // Store the current value.
    
    // Exit loop when reading fails.
    if (file.fail())
      break;
    
    count++;
    sum += value;
    
    // Test to see the numbers
    //cout << value << endl;
  }
  
  // Calculate and output the statistics.
  
  cout << "In \"data.txt\":\n"
       << "  Count: " << count << "\n"
       << "  Sum: " << sum << "\n"
       << "  Mean: " << sum / count << endl;
}
