/**
 * A mini rock-paper-scissors game with the computer that is TOTALLY FAIR!
 * 
 * (PS: I used to make an unfair r-p-s game as the "hello-world" project
 *      for each new language I learn. But this one is absolutely fair.
 *      Don't get me wrong :D)
 * 
 * --------
 * 
 * Qianlang Chen
 * u1172983
 * Sat, Sept 1, 2018
 *
 */

#include <iostream>
#include <cstdlib>

using namespace std;

int main() {
  // ask the user what he wants to play.
  string user_play;
  cout << "Do you wanna play \"rock\", \"paper\", or \"scissors\"?" << endl
       << "You play: ";
  cin >> user_play;
  
  // filter invalid inputs from the user.
  if (user_play != "rock" && user_play != "paper" && user_play != "scissors") {
    cout << "Invalid play!!" << endl;
    return 0;
  }
  
  // decide (RANDOMLY CHOOSE) what the computer wants to play.
  string computer_play;
  srand(time(NULL)); // sets a new seed for rand()
  int random_int = rand() % 3;
  if (random_int == 0)
    computer_play = "rock";
  else if (random_int == 1)
    computer_play = "paper";
  else
    computer_play = "scissors";
  
  // tell the user what the computer plays.
  cout << "I play: " << computer_play << endl;
  
  // judge if it's a win or draw and output a friendly message.
  string result;
  if (user_play == computer_play)
    result = "We draw";
  else if (user_play == "rock" && computer_play == "scissors"
	   || user_play == "paper" && computer_play == "rock"
	   || user_play == "scissors" && computer_play == "paper")
    result = "You win";
  else
    result = "I win";
  
  cout << result << "! Good game!" << endl;
}
