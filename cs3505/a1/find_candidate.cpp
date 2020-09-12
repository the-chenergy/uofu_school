/**
 * Identifies which candidate point best approximates a cloud of 3D points.
 *
 * Qianlang Chen
 * u1172983
 * M 01/06/20
 */

#include "pntvec.h"

#include <cmath>
#include <fstream>
#include <iostream>
#include <vector>

////// forward declarations ////////////////////////

void load_points(std::string, std::vector<pntvec> &);
double score(std::vector<pntvec> &, pntvec &);
double error(pntvec &, pntvec &);

////// functions ///////////////////////////////////

/** Application entry point. **/
int main()
{
  // read inputs
  std::vector<pntvec> points, candidates;
  load_points("point_cloud.txt", points);
  load_points("candidates.txt", candidates);

  // compute
  pntvec best_candidate = candidates[0];
  double best_score = score(points, best_candidate);

  for (int i = 1; i < candidates.size(); i++)
  {
    double temp_score = score(points, candidates[i]);
    if (temp_score < best_score)
    {
      best_candidate = candidates[i];
      best_score = temp_score;
    }
  }

  // output
  std::cout << best_candidate.x << " " << best_candidate.y << " "
            << best_candidate.z << std::endl;
  std::cout << best_score << std::endl;

  return 0;
}

/**
 * Reads in a list of points from a text file.
 *
 * file_name: The name of the file to read in.
 * target: The vector object used to store the points read in.
 */
void load_points(std::string file_name, std::vector<pntvec> &target)
{
  std::ifstream fin(file_name.c_str());
  while (true)
  {
    pntvec p;
    fin >> p.x >> p.y >> p.z;

    if (fin.fail())
      break;

    target.push_back(p);
  }
  fin.close();
}

/**
 * Returns the score of a candidate point.
 *
 * points: The point cloud.
 * candidate: The candidate point whose score is to be calculated.
 */
double score(std::vector<pntvec> &points, pntvec &candidate)
{
  double res = 0;
  for (pntvec p : points)
    res += error(p, candidate);

  return res;
}

/**
 * Returns the square of the straight-line distance between two points in a 3D
 * space.
 *
 * p: The first point.
 * q: The second point.
 */
double error(pntvec &p, pntvec &q)
{
  return pow(q.x - p.x, 2) + pow(q.y - p.y, 2) + pow(q.z - p.z, 2);
}
