#include <iostream>
#include <string>

/**
 * 
 * Given a string (without spaces), this program will output
 * the ASCII code of each charactor in the string, as well
 * as the total number of vowels the string contains.
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
  // Ask the user for a string input.
  
  string input;
  cout << "Enter a string (without spaces):" << endl;
  cin >> input;
  
  // Calculate and output the ASCII code of each character
  //   in the string. Also, count the number of vowels
  //   in the string.
  
  cout << "ASCII codes:" << endl;
  
  int vowels = 0;
  
  for (int i = 0, l = input.length(); i < l; i++) {
    char ch = input[i]; // The i-th character in the string.
    
    cout << "  " << ch << "  " << (int)ch;
    
    // Update the count of total vowels.
    if (ch == 'a' || ch == 'e' || ch == 'i'
	|| ch == 'o' || ch == 'u' || ch == 'A'
	|| ch == 'E' || ch == 'I' || ch == 'O'
	|| ch == 'U') {
      vowels++;
      
      // Write a friendly comment behind each vowel.
      cout << " (vowel)";
    }
    
    cout << endl;
  }
  
  // Output the total number of vowels in the string.
  
  cout << "The input string contains " << vowels
       << " vowel(s)!" << endl;
  
  return 0;
}
