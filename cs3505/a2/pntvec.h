/**
 * Represents a point vector in a 3-dimensional space and provides methods to
 * perform basic arithmetic operations between point vectors.
 *
 * Qianlang Chen
 * W 01/22/20
 */

// allow the class to be #included multiple times
#ifndef PNTVEC_H
#define PNTVEC_H

#include <iostream>

class pntvec
{
private:
  // private fields
  double x;
  double y;
  double z;

public:
  // public constructors
  pntvec();
  pntvec(double x, double y, double z);
  pntvec(const pntvec &p);

  // public methods
  double get_x() const;
  double get_y() const;
  double get_z() const;
  double distance_to(const pntvec &p) const;

  // public operators
  const pntvec &operator=(const pntvec &rhs);
  const pntvec operator+(const pntvec &rhs) const;
  const pntvec operator-() const;
  const pntvec operator-(const pntvec &rhs) const;
  const pntvec operator*(const double &rhs) const;

  // public friend operators
  friend std::ostream &operator<<(std::ostream &lhs, const pntvec &rhs);
  friend std::istream &operator>>(std::istream &lhs, pntvec &rhs);
};

#endif // PNTVEC_H
