/**
 * A 'string set' is defined as a set of strings stored
 * in sorted order in a drop list.  See the class video
 * for details.
 *
 * For lists that do not exceed 2^(max_next_width elements+1)
 * elements, the add, remove, and contains functions are
 * O(lg size) on average.  The operator= and get_elements
 * functions are O(size).
 *
 * Peter Jensen and Qianlang Chen
 * February 10, 2020
 */

#include "string_set.h"

#include <cstdlib>
#include <iostream>

namespace cs3505
{
  ////// PUBLIC CONSTRUCTORS //////////////////////////////////////////////////

  /**
   * Creates a new string_set instance with a default maximum node width.
   *
   * max_next_width - The maximum number of next-nodes a node can have. (default
   * = 10)
   */
  string_set::string_set(int max_next_width)
      : max_next_width(max_next_width), size(0)
  {
    std::srand(time(NULL));

    head = new node(max_next_width);
  }

  /** Copy constructor */
  string_set::string_set(const string_set& other)
      : string_set(other.max_next_width)
  {
    node* curr = other.head->next[0];
    while (curr)
    {
      add(curr->data);
      curr = curr->next[0];
    }
  }

  ////// PUBLIC METHODS ///////////////////////////////////////////////////////

  /**
   * Ensures the string_set contains a particular string.
   *
   * target - The string whose existence is to be ensured.
   */
  void string_set::add(const std::string& target)
  {
    std::vector<node*> prev;
    get_prev(prev, target);

    node* curr = prev[0]->next[0];
    if (curr && curr->data == target) // already exists
      return;

    int next_width = get_next_width();
    node* new_node = new node(next_width, target);

    // link 'prev -/> next <-- curr' for each level
    //            -------------^

    for (int curr_level = 0; curr_level < next_width; curr_level++)
    {
      new_node->next[curr_level] = prev[curr_level]->next[curr_level];
      prev[curr_level]->next[curr_level] = new_node;
    }

    size++;
  }

  /**
   * Ensures the string_set does not contain a particular string.
   *
   * target - The string whose non-existence is to be ensured.
   */
  void string_set::remove(const std::string& target)
  {
    std::vector<node*> prev;
    get_prev(prev, target);

    node* curr = prev[0]->next[0];
    if (!curr || curr->data != target) // doesn't exist
      return;

    // link 'prev -/> curr --> next' for each level
    //            -------------^

    for (int curr_level = 0; curr_level < curr->next_width; curr_level++)
      prev[curr_level]->next[curr_level] = curr->next[curr_level];

    delete curr; // no recursive deletion here

    size--;
  }

  /**
   * Checks and returns true if the string_set contains a particular string.
   *
   * target - The string to check existence.
   */
  bool string_set::contains(const std::string& target) const
  {
    std::vector<node*> prev;
    get_prev(prev, target);

    node* curr = prev[0]->next[0];
    return curr && curr->data == target;
  }

  /** Returns the number of strings contained in this string_set. */
  int string_set::get_size() const
  {
    return size;
  }

  /** Returns a vector containing all strings in their natural ordering. */
  std::vector<std::string> string_set::get_elements() const
  {
    std::vector<std::string> res;
    res.reserve(size);

    node* curr = head;
    while (curr->next[0])
    {
      curr = curr->next[0];
      res.push_back(curr->data);
    }

    return res;
  }

  ////// PUBLIC OPERATORS /////////////////////////////////////////////////////

  /** Assignment operator */
  string_set& string_set::operator=(const string_set& rhs)
  {
    clear();

    max_next_width = rhs.max_next_width;
    head = new node(max_next_width);

    node* curr = rhs.head;
    while (curr->next[0])
    {
      curr = curr->next[0];
      add(curr->data);
    }

    return *this;
  }

  ////// PRIVATE METHODS //////////////////////////////////////////////////////

  /** Returns the next_width value for a (newly created) node. */
  int string_set::get_next_width() const
  {
    int next_width = 1;
    while (next_width < max_next_width)
    {
      if (random() % 2 == 0)
        break;

      next_width++;
    }

    return next_width;
  }

  /**
   * Retrieves a list of previous nodes to a potential node.
   *
   * prev - The vector to hold the resulting previous nodes.
   * target - The string to test against (the contents of the potential node).
   */
  void string_set::get_prev(std::vector<node*>& prev,
                            const std::string& target) const
  {
    prev.resize(max_next_width, NULL); // initialize the prev vector with nulls.

    node* curr = head;
    int curr_level = max_next_width - 1; // search high-to-low

    while (curr_level >= 0) // until first level is reached and searched
    {
      prev[curr_level] = curr;

      node* next = curr->next[curr_level];
      if (!next || next->data >= target) // dropping too far
        curr_level--;
      else
        curr = next; // continue searching in current level
    }
  }

  /** Removes all data associated with this string_set. */
  void string_set::clear()
  {
    node* curr = head;
    while (curr)
    {
      node* next = curr->next[0];

      delete curr;
      curr = next;
    }

    size = 0;
  }

  ////// DESTRUCTOR ///////////////////////////////////////////////////////////

  /** Destructor */
  string_set::~string_set()
  {
    clear();
  }
} // namespace cs3505
