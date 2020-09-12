#include <iostream>

/**
 * 
 * This program computes and outputs a hailstone sequence which
 * is related to the Collatz conjecture.
 * Here is how this sequence works out:
 * Starting with a positive integer N,
 *   - If N is even, the next number will be N/2.
 *   - Otherwise, the next number will be 3N+1.
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
  // Ask the user for an integer input N.
  
  int n;
  cout << "Enter a positive integer:" << endl;
  cin >> n;
  
  // Check if the input N is positive.
  if (n <= 0) {
    cout << "The input is not a positive integer!!"
	 << endl;
    return 0;
  }
  
  // Compute and output the related hailstone starting with N.
  
  cout << "Here is the hailstone sequence starting with your input:"
       << endl << n << " ";
  
  while (n != 1) {
    // Update the value of N according to its parity.
    if (n % 2 == 0)
      n /= 2;
    else
      n = n * 3 + 1;
    
    cout << n << " ";
  }
  
  cout << endl;
  
  return 0;
}
