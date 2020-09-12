/**
 * This program prompts the user for a filename, reads the bits from the
 * file, decodes them using the BitMessage class, and outputs the resulting
 * message to the console.
 * 
 * ============
 * 
 * Qianlang Chen
 * u1172983
 * 10/27/18 A
 */

#include <iostream>
#include <fstream>

#include "BitMessage.h"

using namespace std;

/** Application entry point. **/
int main() {
  // Ask the user for the input file name.
  
  string file_name;
  
  cout << "Hi, what's the input file name?" << endl;
  cin >> file_name;
  
  // Read the bits from the file. Meanwhile, pass the bits to the message
  // for decoding.
  
  ifstream file(file_name.c_str()); // for some reason this won't work
  // without calling the c_str() method.
  BitMessage message; // the object used to hold the secret msg.
  
  while (true) {
    int bit;
    file >> bit; // assuming the bits are separated by whitespaces.
    
    // stop looping when the end of file is reached.
    if (file.fail())
      break;
    
    message.append(bit);
  }
  
  file.close();
  
  // Display the decoded message to the user.
  
  cout << "Your decoded message:\n"
       << message.getMessage() << endl;
  
  // Done!
  
  return 0;
}
