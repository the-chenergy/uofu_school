/**
 * Generates and displays random phrases according to a set of grammar rules.
 *
 * ------------
 * Qianlang Chen
 * H 04/18/19
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>
#include <cstdlib>

using namespace std;

/** Represents a piece of text in a production rule of a non-terminal. **/
struct text {
  /** Indicates whether this text is a terminal. **/
  bool is_terminal;

  /** The content of this terminal or the name of this non-terminal. **/
  string content;

  /**
   * Creates a new text instance.
   *
   * Params:
   *   _is_terminal - Whether this text is a terminal.
   *   _content     - The content of this text.
   */
  text(bool _is_terminal, string _content) {
    is_terminal = _is_terminal;
    content = _content;
  }
};

/** The map used to store the production rules for each non-terminal. **/
map<string, vector< vector<text> > > rule_map;

/**
 * Returns a piece of text a non-terminal can represent according to the
 * grammar rules read in.
 *
 * Param:
 *   non_terminal - The name of the non-terminal to get text of.
 */
string get_text(string non_terminal) {
  vector< vector<text> > rule_list = rule_map[non_terminal];
  vector<text> rule = rule_list[rand() % rule_list.size()];
  string res;

  // for each piece of text in the production rule
  for (int i = 0; i < rule.size(); i++) {
    text text = rule[i];

    if (text.is_terminal)
      res += text.content;
    else
      res += get_text(text.content);
  }

  return res;
}

/** Application entry point. **/
int main() {
  string url; // the url to the grammar definition file
  cout << "Grammar file name: ";
  cin >> url;

  int num_phrases; // the number of phrases to generate
  cout << "Number of phrases to generate: ";
  cin >> num_phrases;

  // READ IN THE GRAMMAR DEFINITION FILE

  ifstream fin(url.c_str());
  string line;

  // for each line in the file
  while (getline(fin, line)) {
    if (line != "{") // comment area
      continue;

    getline(fin, line); // the non-terminal currently being defined
    string non_terminal = line;
    vector< vector<text> > rule_list; // create a new non-terminal in the map

    // for each line of production rule of a non-terminal
    while (getline(fin, line)) {
      if (line == "}") // comment area starts
	break;

      vector<text> rule; // create a new rule for the current non-terminal

      int t = 0, n = 0;
      for (int i = 0, l = line.size(); i <= l; i++) {
	if (i == l || line[i] == '<') { // terminal ends
	  n = i;
	  if (n > t) { // only add a terminal when there is one
	    text text(true, line.substr(t, n - t));
	    rule.push_back(text); // append the rule with the terminal
	  }
	} else if (line[i] == '>') { // non-terminal ends
	  t = i + 1;
	  text text(false, line.substr(n, t - n));
	  rule.push_back(text); // append the rule with the non-terminal
	}
      }

      rule_list.push_back(rule); // put the rule into the rule list
    }

    rule_map[non_terminal] = rule_list; // put the rules in for the curr nt
  }

  // GENERATE THE NUMBER OF RANDOM PHRASES REQUESTED

  srand(time(NULL)); // seed the random number generator used to pick
  // a production rule for a non-terminal

  while (num_phrases--)
    cout << get_text("<start>") << endl;
  // "<start>" defines the starting non-terminal (the one to expand)

  return 0;
}
