#include <iostream>
#include <cmath>

int main()
{
  // sorry, no time for typing the comments

  std::cout << "What's your name?\n";

  std::string name;

  std::cin >> name;

  std::cout << "Hi " << name  << "!\n";
  std::cout << "What's the radius of the sphere?\n";

  double radius;

  std::cin >> radius;

  double volume = pow(radius, 3) * (3.1415926535897932384626 * 4. / 3.);

  std::cout << "The volume of your sphere is " << volume << "!\n";
}
