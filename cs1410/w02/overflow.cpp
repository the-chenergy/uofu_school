#include <iostream>
#include <cmath>

/**
 * An exploration of integer overflow.
 * 
 * (2,147,483,647 is the maximum positive 32-bit integer
 * a computer can hold.)
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * 
 */

int main()
{
  int i;

  // additions
  i = 2147483646 + 1;
  std::cout << "2147483646 + 1 = " << i << "\n"; // 2147483647
  // in the example above, the integer has not yet overflowed,
  //   the result of addition is correct.

  i = 2147483647 + 1;
  std::cout << "2147483647 + 1 = " << i << "\n"; // -2147483648
  // in the example above, the integer has overflowed
  //  and gives us a negative number instead.

  // multiplications
  i = 20000 * 20000;
  std::cout << "20000 * 20000 = "  << i << "\n";

  i = 200000 * 200000;
  std::cout << "200000 * 200000 = " << i << "\n";

  // multiplying two very large integers may also result in overflow,
  //   but it may be hard to detect. we can still detect it by 
  //   estimating the result. we don't necessarily have to estimate the 
  //   correct result, we can tell from just looking at the numbers
  //   sometimes. in the example above, we're multiplying two
  //   integers ending with a bunch of zeros, and by estimating
  //   roughly we should expect that the result should also end in
  //   a string of zeros. however, that was not the case, so an
  //   integer overflow must have occured.
}
