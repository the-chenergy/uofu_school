/* This node class is used to build linked lists for the
 * string_set class.
 *
 * Peter Jensen and Qianlang Chen
 * February 10, 2020
 */

#include "node.h"

namespace cs3505
{
  ////// PRIVATE CONSTRUCTORS /////////////////////////////////////////////

  /**
   * Creates a new node instance.
   *
   * next_width - The number of next nodes this node can contain.
   * data - The string data to hold. (default = "")
   */
  node::node(int next_width, std::string data)
      : next_width(next_width), data(data)
  {
    next.resize(next_width, NULL); // fill with nulls
  }
} // namespace cs3505
