/**
 * 
 */

#include <iostream>

using namespace std;

////// FORWARD DECLEARATIONS //////

void swap_integers(int a, int b);

////// MAIN FUNCTION //////

int main() {
  int a = 0, b = 1;
  
  cout << "Before: " << a << ", " << b << endl;
  
  //swap_integers(a, b); // this line does nothing
  
  int temp = a;
  a = b;
  b = temp;
  
  cout << "After: " << a << ", " << b << endl;
}

////// FUNCTIONS //////

/**
 * 
 * This function swaps the values in its parameters.
 * 
 * Parameters:
 *   int a -- an integer
 *   int b -- an integer
 *
 */
void swap_integers(int a, int b) {
  int temp = a;
  a = b;
  b = temp;
}
