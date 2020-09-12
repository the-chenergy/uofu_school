/**
 * Provides tests for the pntvec class.
 *
 * Qianlang Chen
 * H 01/23/20
 */

#include "pntvec.h"

#include <cmath>
#include <iostream>
#include <sstream>

////// HELPER FUNCTIONS ////////////////////////////////////////////////////////

/**
 * Checks and returns true if a double value does NOT approximately equal an
 * expected double value.
 *
 * double actual - the double value to check.
 * double expected - the expected double value.
 */
bool check_double(double actual, double expected)
{
  // allow each double value to have a margin of error (10^-9)
  return std::fabs(actual - expected) > 1e-9;
}

/**
 * Checks and returns true if a point vector object does NOT store the expected
 * coordinates.
 *
 * pntvec &p - the point vector object to check.
 * double x - the expected X coordinate.
 * double y - the expected Y coordinate.
 * double z - the expected Z coordinate.
 */
bool check_pntvec(const pntvec &p, double x, double y, double z)
{
  return check_double(p.get_x(), x) || check_double(p.get_y(), y) ||
         check_double(p.get_z(), z);
}

/**
 * Prints an error message of failing a test case and returns the error code -1.
 *
 * string func_name - the name of pntvec's function that failed.
 */
int fail(const std::string &func_name)
{
  std::cout << "Error in <" << func_name << ">." << std::endl;
  return -1;
}

////// TEST CASES //////////////////////////////////////////////////////////////

/** Application entry point. **/
int main()
{
  /* test cases for the pntvec class...
   *
   * inside each pair of braces {} below contains a test case for one of
   * pntvec's methods or operators, named by the comment above each test case.
   */

  // pntvec()
  {
    pntvec p;

    if (check_pntvec(p, 0, 0, 0))
      return fail("pntvec");
  }

  // pntvec(double, double, double)
  {
    pntvec p(0, 0, 0);
    pntvec q(1.414, 2.718, -3.142);

    if (check_pntvec(p, 0, 0, 0) || check_pntvec(q, 1.414, 2.718, -3.142))
      return fail("pntvec");
  }

  // pntvec(pntvec)
  {
    pntvec p;
    pntvec q(1.414, 2.718, -3.142);

    pntvec r(p);
    pntvec s(q);

    if (check_pntvec(r, 0, 0, 0) || check_pntvec(s, 1.414, 2.718, -3.142))
      return fail("pntvec");
  }

  // get_x()
  {
    pntvec p;
    pntvec q(-1.414, -2.718, -3.142);
    pntvec r(1.414, 2.718, 3.142);

    if (check_double(p.get_x(), 0) || check_double(q.get_x(), -1.414) ||
        check_double(r.get_x(), 1.414))
      return fail("get_x");
  }

  // get_y()
  {
    pntvec p;
    pntvec q(-1.414, -2.718, -3.142);
    pntvec r(1.414, 2.718, 3.142);

    if (check_double(p.get_y(), 0) || check_double(q.get_y(), -2.718) ||
        check_double(r.get_y(), 2.718))
      return fail("get_y");
  }

  // get_z()
  {
    pntvec p;
    pntvec q(-1.414, -2.718, -3.142);
    pntvec r(1.414, 2.718, 3.142);

    if (check_double(p.get_z(), 0) || check_double(q.get_z(), -3.142) ||
        check_double(r.get_z(), 3.142))
      return fail("get_z");
  }

  // distance_to(pntvec)
  {
    pntvec p;
    pntvec q(-2, 3, 6);

    if (check_double(p.distance_to(q), 7) || check_double(q.distance_to(p), 7))
      return fail("distance_to");
  }

  // operator=(pntvec)
  {
    pntvec p;
    pntvec q(-2, 3, 6);
    pntvec r(1.414, 2.718, -3.142);

    p = q = r;

    if (check_pntvec(p, 1.414, 2.718, -3.142) ||
        check_pntvec(q, 1.414, 2.718, -3.142))
      return fail("operator=");
  }

  // operator+(pntvec)
  {
    pntvec p(-2, 3, 6);
    pntvec q(1.414, 2.718, -3.142);

    pntvec r = p + q;

    if (check_pntvec(r, -2 + 1.414, 3 + 2.718, 6 - 3.142))
      return fail("operator+");
  }

  // operator-()
  {
    pntvec p;
    pntvec q(1.414, 2.718, -3.142);

    pntvec r = -p;
    pntvec s = -q;

    if (check_pntvec(r, 0, 0, 0) || check_pntvec(s, -1.414, -2.718, 3.142))
      return fail("operator-");
  }

  // operator-(pntvec)
  {
    pntvec p(-2, 3, 6);
    pntvec q(1.414, 2.718, -3.142);

    pntvec r = p - q;

    if (check_pntvec(r, -2 - 1.414, 3 - 2.718, 6 + 3.142))
      return fail("operator-");
  }

  // operator*(double)
  {
    pntvec p;
    pntvec q(1.414, 2.718, -3.142);

    pntvec r = p * 2;
    pntvec s = q * 2;
    pntvec t = q * -2;
    pntvec u = q * 0;

    if (check_pntvec(r, 0, 0, 0) ||
        check_pntvec(s, 1.414 * 2, 2.718 * 2, -3.142 * 2) ||
        check_pntvec(t, 1.414 * -2, 2.718 * -2, -3.142 * -2) ||
        check_pntvec(u, 0, 0, 0))
      return fail("operator*");
  }

  // operator<<(ostream, pntvec)
  {
    pntvec p;
    pntvec q(-2, 3, 6);

    std::ostringstream out;
    out << p << q;

    if (out.str() != "(0, 0, 0)(-2, 3, 6)")
      return fail("operator<<");
  }

  // operator>>(istream, pntvec)
  {
    // the input stream contains (0, 0, 0) and (1.414, 2.718, -3.142)
    std::istringstream in("0\t0 0\r\n1.414   2.718\t\t-3.142");

    pntvec p, q;
    in >> p >> q;

    if (check_pntvec(p, 0, 0, 0) || check_pntvec(q, 1.414, 2.718, -3.142))
      return fail("operator>>");
  }

  // all tests have passed at this point
  std::cout << "No error." << std::endl;

  return 0;
}
