/* This node class is used to build drop lists for the
 * string_set class.
 *
 * Peter Jensen and Qianlang Chen
 * January 30, 2020
 */

#ifndef NODE_H
#define NODE_H

#include <string>
#include <vector>

namespace cs3505
{
  // We're in a namespace - declarations will be within this CS3505 namespace.
  //   (There are no definitions here - see node.cpp.)

  /* Node class for holding elements. */

  /* Note:  Do not alter the next line of code.  My tester will re-write
     the 'class node'  to 'class node : private auditor' so that it inherits
     from my auditing class (harmlessly). */

  class node
  {
    friend class string_set; // This allows functions in string_set to access
                             //   private data (and constructor) within this
                             //   class.

  private:
    // constructors
    node(int next_width, std::string data = "");

    // fields
    int next_width;
    std::string data;
    std::vector<node*> next;
  };
} // namespace cs3505

#endif
