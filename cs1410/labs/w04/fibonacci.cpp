/* 
 * Fibonacci code for lab #4.
 */

#include <iostream>

using namespace std;

// Forward declaration of a function.  This declares the function,
//   but does not define it.  (Notice that there is no code, just
//   a function header with a semicolon after it.

int fibonacci (int n);

////// GLOBAL VARIABLES //////

int call_count;

/* Our main function.  This is an experiment for lab #4.
 *
 * Parameters:
 *    none
 *
 * Return value:
 *    0 if we complete successfully, 1 if there was an error.
 */
int main ()
{
  string input;

  // Prints the first few numbers from the Fibonacci sequence.

  for (int i = 0; i < 42; )
  {
    // Use the student's function to compute a Fibonacci number.
    
    call_count = 0;
    
    int number = fibonacci (i);
    
    cout << call_count << endl;
    
    // This next bit of code figures out the correct
    //   suffix for displaying numbers.  1st, 2nd, 3rd,
    //   etc.  This just makes for a nice display.
    
    i++;
    
    string suffix;
    
    if (i%100/10 != 1 && i%10 == 1)
      suffix = "st";
    
    else if (i%100/10 != 1 && i%10 == 2)
      suffix = "nd";
    
    else if (i%100/10 != 1 && i%10 == 3)
      suffix = "rd";
    
    else
      suffix = "th";
    
    // Output the number.
    
    cout << "The " << i << suffix << " number is " << number << ".\n";
  }

  // Done, exit the application.

  return 0;  // No error, so we return a 0.
}

/* This function computes the nth Fibonacci number.
 *
 * Parameters:
 *    int n -- the index of the Fibonacci number to compute
 *
 * Return value:
 *    int -- the nth Fibonacci number.
 */
int fibonacci (int n)
{
  call_count++;
  
  return n < 2 ? n : fibonacci(n - 1) + fibonacci(n - 2);
}
