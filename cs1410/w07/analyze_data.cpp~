/**
 *   This program reads the high and low temperatures of each day of a
 * year from "./data.txt", analyzes the data, and outputs some useful
 * information of the data.
 * 
 * ================
 * 
 * Qianlang Chen
 * u1172983
 * F 09/28/18
 */

#include <iostream>
#include <fstream>

using namespace std;

//////////////// FORWARD DECLARATIONS ////////////////

string pluralize(int, string, string);

//////////////////////////////////////////////////////

/** Program entry point. **/
int main() {
  // Display some "copyright" texts.
  
  cout << "Assignment #6\n"
       << "CS 1410-030\n"
       << "Qianlang Chen\n"
       << endl;
  
  // Create some arrays to store related data.
  
  int length = 365; // the data should have exactly 365 lines.
  
  string dates[length];
  int high_temps[length];
  int low_temps[length];
  
  // Read and store the data from file "data.txt", meanwhile, 
  //   collect some useful information for later to analyze.
  
  ifstream file("data.txt");
  
  int highest = high_temps[0];
  int highest_index = 0;
  int lowest = low_temps[0];
  int lowest_index = 0;
  int num_perfect_days = 0;
  int num_cold_fronts = 0;
  int high_temp_length = 151; // assuming the range of high temperatures
  // is 0..150, inclusive.
  int high_temp_counts[high_temp_length]; // the array to record the
  // occurence of each high temperatures.
  
  // Initialize the high_temp_count array.
  for (int i = 0; i < high_temp_length; i++)
    high_temp_counts[i] = 0;
  
  // Go over the data, find the related statistics, and record them.
  for (int i = 0; i < length; i++) {
    int high_temp, low_temp; // keep a copy of the temperature data
    // read in for statistical calculations below.
    
    file >> dates[i] >> high_temp >> low_temp;
    
    high_temps[i] = high_temp;
    low_temps[i] = low_temp;
    
    // Update the count of the high temperature.
    high_temp_counts[high_temp]++;
    
    // Check if it is a perfect day.
    if (high_temp < 82 && low_temp >= 48)
      num_perfect_days++;
    
    // Skip the rest of the tests for the first day.
    if (i == 0)
      continue;
    
    // Check if it is a cold front.
    if (high_temp - high_temps[i - 1] >= 15
	|| low_temp - low_temps[i - 1] >= 15)
      num_cold_fronts++;
    
    // Update the highest/lowest temperatures.
    
    if (high_temp > highest) {
      highest = high_temp;
      highest_index = i;
    }
    
    if (low_temp < lowest) {
      lowest = low_temp;
      lowest_index = i;
    }
  }
  
  file.close();
  
  // Determine the most common high temperature.
  
  int most_common_high_temp = 0;
  int most_common_count = high_temp_counts[0];
  
  for (int i = 1; i < high_temp_length; i++) {
    // Update the highest count. The below is designed so that when two
    //   counts tie, the one with the higher temperature will be kept.
    if (high_temp_counts[i] >= most_common_count) {
      most_common_count = high_temp_counts[i];
      most_common_high_temp = i; // the high temp is i, since we used the
      // temperature to map it with its count.
    }
  }
  
  // Output all statistics.
  
  cout << "The maximum high temperature was " << highest
       << " on " << dates[highest_index] << ".\n"
       
       << "The minimum low temperature was " << lowest
       << " on " << dates[lowest_index] << ".\n"
       
       << "There were " << num_perfect_days
       << " perfect " << pluralize(num_perfect_days, "day", "days")
       << " in this year.\n"
       
       << "There were " << num_cold_fronts
       << " significant cold " << pluralize(num_cold_fronts, "front", "fronts")
       <<" in this year.\n"
       
       << "The most common daily high temperature was "
       << most_common_high_temp << " this year."
       
       << endl;
  
  return 0;
}

/**
 * Pluralize a word according to its count.
 * 
 * Params:
 *   int count -- the count of the word.
 *   string word -- the word to be pluralized.
 *   string plural -- the plural of the word.
 * 
 * Returns:
 *   The plural of the word if its count is not one, or the word itself
 * otherwise.
 */
string pluralize(int count, string word, string plural) {
  return count == 1 ? word : plural;
}
