/**
 * Assigning each letter a numeric value (A being 1, B being 2, etc.),
 * this program finds the words where all letters sum up to a certain value.
 * 
 * By Qianlang Chen
 * u1172983
 * S 12/02/18
 * v1.0.0
 */

////// INCLUDES ///////////////////////////////////////////////////////////////

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cstdlib>
#include <cstdarg>

using namespace std;

////// FORWARD DECLARATIONS ///////////////////////////////////////////////////

/**
 * A structure that holds a pair of English word and its letter value.
 */
struct word {
  string text;
  int sum;
};

vector<word> get_words();
int get_sum(string);
int get_positive_int_input(string, string);
string get_random_word(int, ...);

////// METHODS ////////////////////////////////////////////////////////////////

/** Application entry point. **/
int main() {
  // Read in the word list this program will use.
  
  vector<word> words = get_words();
  
  // Display some introductions.
  
  cout << "  Welcome!\n"
       << "  Assigning each English letter a numeric value,\n"
       << "with the letter A being 1, letter B being 2, and so on,\n"
       << "each word or phrase sums up to some number.\n"
       << "  For example, the word \"knowledge\" and \"hardwork\"\n"
       << "sum up to 96 and 98 respectively, while the word\n"
       << "\"attitude\" sums up to 100, meaning that one's\n"
       << "attitude is the most important out of all.\n"
       << "  Isn't that interesting? Give me an expected sum,\n"
       << "and I'll list you the words that sum up to it,\n"
       << "so you can make Memes out of it!"
       << endl;
  
  // Perform the task repeatedly (ask for input, calculate output).
  
  srand(time(NULL)); // for later to generate a random word for ending display.
  
  for (;;) {
    // Ask the user for a sum.
    
    int expected_sum = get_positive_int_input("Oops, wrong input! Give me a positive integer.", "exit");
    
    if (expected_sum == 0) { // exit
      cout << "Okay, I hope you enjoyed it! Have a nice day!" << endl;
      
      return 0;
    }
    
    // Search and display the word list for the words that sum up to the
    // given integer.
    
    vector<string> result;
    
    for (int i = 0, l = words.size(); i < l; i++)
      if (words[i].sum == expected_sum)
	result.push_back(words[i].text);

    if (result.size() == 0) {
      cout << "Sorry, I couldn't find any word that sum up to this number!\n"
	   << "Give me another sum to try again, or enter \"exit\" "
	   << "to exit." << endl;
      
      continue;
    }
    
    cout << "Here are the words I found that sum up to "
	 << expected_sum << ":" << endl;
    
    for (int i = 0, l = result.size(); i < l; i++)
      cout << result[i] << endl;
    
    // Ask for the user for another input.
    
    cout << get_random_word(6, "Yay", "Here ya go", "Impressive", "Boom", "Wow", "Isn't that cool")
	 << "! Give me another sum to find again, or enter \"exit\" "
	 << "to exit." << endl;
  }
  
  return 0;
}

/**
 * Reads the list of words that this program will be using from a text file
 * named "words.txt" and stores the word list into a vector object.
 * 
 * Calculates the sum (letter value) of each word.
 * 
 * Returns the vector that contains the word list read in.
 */
vector<word> get_words() {
  ifstream file("words.txt");
  vector<word> output; // use a vector since we don't know the length
  
  string line;
  while (getline(file, line)) { // read in each line instead of each word
    word current_word;
    
    current_word.text = line;
    current_word.sum = get_sum(line);
    
    if (current_word.sum == -1) // get rid of the invalid words
      continue;
    
    output.push_back(current_word);
  }
  
  return output;
}

/**
 * Assigning each English letter a numeric value (A being 1, B being 2, etc.),
 * calculates and returns the numeric value of a string.
 * 
 * Spaces have a numeric value of zero.
 * 
 * Returns -1 if the string contains invalid characters (characters other than
 * upper-case, lower-case, and spaces).
 * 
 * Params:
 *   string text - The string to calculate its value.
 */
int get_sum(string text) {
  int sum = 0;
  
  for (int i = 0, l = text.size(); i < l; i++) {
    char ch = text[i];
    
    // Filter out spaces (they have no value).
    
    if (ch == ' ')
      continue;
    
    // Calculate the value of an upper-case letter.
    
    if (ch >= 'A' && ch <= 'Z') {
      sum += ch - 'A' + 1;
      
      continue;
    }
    
    // Calculate the value of an lower-case letter.
    
    if (ch >= 'a' && ch <= 'z') {
      sum += ch - 'a' + 1;
      
      continue;
    }
    
    // The text in considered invalid by containing characters
    // other than those.
    
    return -1;
  }
  
  return sum;
}

/**
 * Gets a positive integer input from the user. If user's input is not valid,
 * shows them a message and gets another integer input. If the user enters
 * a permitted word, this function will still accept it.
 * 
 * Returns the positive integer input from the user, or 0 if the user enters
 * the permitted word.
 * 
 * Params:
 *   string message - The message to show to the user when they enter an
 * invalid input.
 *   string permitted_word - The word the user can enter and make this
 * function return 0.
 * 
 */
int get_positive_int_input(string message, string permitted_word) {
  string input;
  cin >> input;
  
  istringstream input_stream(input);
  
  if (input_stream.str() == permitted_word) // the user gives the permitted
    // word
    return 0;
  
  double value;
  input_stream >> value;
  
  if (value > 0 && (int)value == value) // the user gives a valid input
    return (int)value;
  
  cout << message << endl; // display the error message
  
  return get_positive_int_input(message, permitted_word);
}

/**
 * Returns a random word from a word list.
 * 
 * Params:
 *   int count - The number of word to randomly choose one word from.
 *   ... - The list of words to get a random word from.
 */
string get_random_word(int count, ...) {
  va_list args;
  
  int rand_index = rand() % count;
  
  va_start(args, count);
  for (int i = 0; i < count; i++) {
    char* current_word = va_arg(args, char*);
    
    if (i == rand_index) {
      va_end(args);
      
      return string(current_word);
    }
  }
  va_end(args);
  
  return "";
}
