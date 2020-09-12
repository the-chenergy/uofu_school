/**
 * This is a program that will tell you the days in a given month of a given year.
 * 
 * Update: typos fixed ;)
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Sat, Sept 6, 2018
 *
 */

#include <iostream>

using namespace std;

int main() {
  // ask for the input of the year and month.
  int year, month;
  
  cout << "What year is it?" << endl;
  cin >> year;
  cout << "What month is it?" << endl;
  cin >> month;
  
  // check if the input of month is valid.
  // (do it first to avoid unnecessary calculations)
  if (month < 1 || month > 12) {
    cout << "Invalid month!" << endl;
    return 0;
  }
  
  // check if the year is a leap year.
  bool is_leap_year = year % 4 == 0;
  
  // output the days in the given month with friendly texts.
  // (not the best way to do this though XD)
  if (month == 1)
    cout << "January " << year << " has 31 days!";
  else if (month == 2) {
    cout << "February " << year;
    if (is_leap_year)
      cout << " has 29 days!";
    else
      cout << " has 28 days!";
  } else if (month == 3)
    cout << "March " << year << " has 31 days!";
  else if (month == 4)
    cout << "April " << year << " has 30 days!";
  else if (month == 5)
    cout << "May " << year << " has 31 days!";
  else if (month == 6)
    cout << "June " << year << " has 30 days!";
  else if (month == 7)
    cout << "July " << year << " has 31 days!";
  else if (month == 8)
    cout << "August " << year << " has 31 days!";
  else if (month == 9)
    cout << "September " << year << " has 30 days!";
  else if (month == 10)
    cout << "October " << year << " has 31 days!";
  else if (month == 11)
    cout << "November " << year << " has 30 days!";
  else
    cout << "December " << year << " has 31 days!";
  
  cout << endl;
}
