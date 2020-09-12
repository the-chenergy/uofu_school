/**
 * Provides method/operator definitions and descriptions for the pntvec class.
 *
 * Qianlang Chen
 * H 01/23/20
 */

#include "pntvec.h"

#include <cmath>
#include <iostream>

////// PUBLIC CONSTRUCTORS /////////////////////////////////////////////////////

/** Creates a new point vector representing the origin (0, 0, 0). **/
pntvec::pntvec() : x(0), y(0), z(0)
{
}

/**
 * Creates a new point vector representing a particular 3-dimensional point.
 *
 * double x - the X coordinate of the point vector.
 * double y - the Y coordinate of the point vector.
 * double z - the Z coordinate of the point vector.
 */
pntvec::pntvec(double x, double y, double z) : x(x), y(y), z(z)
{
}

/**
 * Creates a new point vector as a copy of another point vector.
 *
 * pntvec &p - the point vector to copy.
 */
pntvec::pntvec(const pntvec &p) : x(p.x), y(p.y), z(p.z)
{
}

////// PUBLIC METHODS //////////////////////////////////////////////////////////

/** Returns the X coordinate of this point vector. **/
double pntvec::get_x() const
{
  return x;
}

/** Returns the Y coordinate of this point vector. **/
double pntvec::get_y() const
{
  return y;
}

/** Returns the Z coordinate of this point vector. **/
double pntvec::get_z() const
{
  return z;
}

/**
 * Returns the straight-line distance from this point to another point.
 *
 * pntvec &p - the point to calculate distance to.
 */
double pntvec::distance_to(const pntvec &p) const
{
  return sqrt(pow(p.x - x, 2) + pow(p.y - y, 2) + pow(p.z - z, 2));
}

////// PUBLIC OPERATORS ////////////////////////////////////////////////////////

/**
 * Sets the coordinates of this point vector to be equal to another point
 * vector's.
 *
 * pntvec &rhs - the point vector to copy the coordinates from.
 */
const pntvec &pntvec::operator=(const pntvec &rhs)
{
  x = rhs.x;
  y = rhs.y;
  z = rhs.z;

  return *this;
}

/**
 * Performs vector addition between this and another point vector object, and
 * returns the resulting vector.
 *
 * pntvec &rhs - the other point vector.
 */
const pntvec pntvec::operator+(const pntvec &rhs) const
{
  return pntvec(x + rhs.x, y + rhs.y, z + rhs.z);
}

/** Returns the negation of this point vector (-x, -y, -z). **/
const pntvec pntvec::operator-() const
{
  return pntvec(-x, -y, -z);
}

/**
 * Performs a vector subtraction between this and another point vector object,
 * and returns the resulting vector.
 *
 * pntvec &rhs - the other point vector.
 */
const pntvec pntvec::operator-(const pntvec &rhs) const
{
  return pntvec(x - rhs.x, y - rhs.y, z - rhs.z);
}

/**
 * Performs a vector scale-multiplication between this point vector and a
 * scalar, and returns the resulting vector.
 *
 * double &rhs - the scalar.
 */
const pntvec pntvec::operator*(const double &rhs) const
{
  return pntvec(x * rhs, y * rhs, z * rhs);
}

////// PUBLIC FRIEND OPERATORS /////////////////////////////////////////////////

/**
 * Writes the string-representation of a point vector into an output stream.
 *
 * std::ostream &lhs - the output stream to write to.
 * pntvec &rhs - the point vector to write.
 */
std::ostream &operator<<(std::ostream &lhs, const pntvec &rhs)
{
  // formatted as "(x, y, z)"
  lhs << "(" << rhs.x << ", " << rhs.y << ", " << rhs.z << ")";

  return lhs;
}

/**
 * Reads and parses the next 3 tokens from an input stream as the X, Y, Z
 * coordinates of a point vector object.
 *
 * std::istream &lhs - the input stream to read from.
 * pntvec &rhs - the point vector to store the read-in coordinates to.
 */
std::istream &operator>>(std::istream &lhs, pntvec &rhs)
{
  double x, y, z;
  lhs >> x >> y >> z;

  pntvec temp(x, y, z);
  rhs = temp;

  return lhs;
}
