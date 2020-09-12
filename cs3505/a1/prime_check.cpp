/**
 * Checks if an int given as a command line arg is prime.
 *
 * Qianlang Chen
 * u1172983
 * M 01/06/20
 */

#include <cmath>
#include <cstdlib>
#include <iostream>

using namespace std;

/**
 * Returns true if a given integer is prime.
 *
 * n - the integer to check.
 */
bool is_prime(int n)
{
  if (n <= 2)
    return n == 2;

  for (int i = 2; i <= sqrt(n); i++)
    if (n % i == 0)
      return false;

  return true;
}

/** application entry point. **/
int main(int argc, char *argv[])
{
  // check if an arg was passed in (the first arg is the prog name).
  if (argc < 2)
  {
    cout << "not prime" << endl;
    return 0;
  }

  // parse the arg into an integer and check for prime.
  int value = atoi(argv[1]);
  if (is_prime(value))
    cout << "prime" << endl;
  else
    cout << "not prime" << endl;

  return 0;
}
