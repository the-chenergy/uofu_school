#include <iostream>
#include <cmath>

using namespace std;

int main() {
  char ch = 'P';
  int i = 21, j = 4;
  long l = 6;
  double n = 25;
  cout << i + j << ", " << n + (int)j << ", " << (int)n + j << ", "
       << i % l << ", " << ch + j << ", " << (char)(ch + j) << ", "
       << 1 / 2 * n << endl;
  
  int x = 10, y = 5;
  bool a = true, b = false, c = false;
  cout << (x < 5) << ", " << (y > 5) << ", " << (y < 100 && x * y == 50)
       << ", " << !(a || b) << ", " << (a && !(b || c)) << endl;
}
