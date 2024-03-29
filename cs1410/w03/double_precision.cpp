/**
 * This is a program to explore the problems of precisions of the double data type.
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Sat, Sept 5, 2018
 * 
 */

#include <iostream>
#include <cmath>

using namespace std;

int main() {
  // print out some useful descriptions first
  
  cout << "This program demonstrates the round-off problems\n"
       << "that double numbers have by simply calculating\n"
       << "some numbers and comparing the results with an\n"
       << "expected result.\n" << endl;
  
  // declear some variables
  
  double n, expected_result, actual_result;
  
  // first test: computing and storing small numbers
  
  n = .56785678;
  actual_result = n * 9 + n;
  expected_result = 5.6785678;
  
  cout << ".56785678 * 10 = " << actual_result << endl;
  
  if (actual_result == expected_result) {
    cout << "The above answer is correct. No round-off problems\n"
	 << "have occurred.\n" << endl;
  } else {
    cout << "The above answer is not correct. Some round-off\n"
	 << "problem occurred, causing an error of\n"
	 << actual_result - expected_result << ".\n" << endl; 
  }
  
  // second test: adding a large number by 1
  
  n = pow(2, 53);
  actual_result = n + 1. + 1.;
  expected_result = pow(2, 53) + 2.;
  
  cout << "2^53 + 2 = " << actual_result << endl;
  
  if (actual_result == expected_result) {
    cout << "The above answer is correct. No round-off problems\n"
	 << "have occurred.\n" << endl;
  } else {
    cout << "The above answer is not correct. Some precision was\n"
	 << "lost during storing a large number, causing an\n"
	 << "error of " << actual_result - expected_result << ".\n"
	 << endl;
  }
  
  // third test: dealing with things like square-roots
  
  n = 100.00001;
  actual_result = pow(sqrt(n), 2);
  expected_result = 100.00001;
  
  cout << "sqrt(100.00001)^2 = " << actual_result << endl;
  
  if (actual_result == expected_result) {
    cout << "The above answer is correct. No round-off problems\n"
	 << "have occurred.\n" << endl;
  } else {
    cout << "The above answer is not correct. Some round-off\n"
	 << "problems occurred, causing an error of\n"
	 << actual_result - expected_result << ".\n" << endl;
  }
}
