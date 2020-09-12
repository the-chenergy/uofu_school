/**
 * Converts a time in seconds into hours, minutes, and seconds.
 *
 * Qianlang Chen
 * u1172983
 * M 01/06/20
 */

#include <cmath>
#include <iostream>

using namespace std;

/**
 * Adds a leading zero in front of an integer (if needed) and prints it.
 *
 * n - the integer to format and print.
 */
void format(int n)
{
  if (n < 10)
    cout << "0";
  cout << n;
}

/** application entry point. **/
int main()
{
  // input
  double decimal_time;
  cout << "Enter a time in seconds: ";
  cin >> decimal_time;

  // calculate
  int time = round(decimal_time);
  int hours = time / 3600;
  int minutes = time % 3600 / 60;
  int seconds = time % 60;

  // output
  cout << time << " seconds --> ";
  format(hours);
  cout << ":";
  format(minutes);
  cout << ":";
  format(seconds);
  cout << endl;

  return 0;
}
