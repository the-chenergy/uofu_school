#include <iostream>
#include <cmath>

using namespace std;

double fibonacci(int n) {  
  double a = 0, b = 1, c = 0;
  while (n--) {
    a = b;
    b = c;
    c = a + b;
  }
  return c;
}

const double SQRT_5 = sqrt(5);
const double GOLDEN_RATIO = .5 * (SQRT_5 + 1);

double fibonacci2(int n) {
  return round((pow(GOLDEN_RATIO, n) - pow(1 - GOLDEN_RATIO, n)) / SQRT_5);
}

double fibonacci_old(int n) {
  return n < 2 ? n : (fibonacci_old(n - 1) + fibonacci_old(n - 2));
}

int main() {
  cout << SQRT_5 << ", " << GOLDEN_RATIO << endl;
  for (int i = 0; i < 1200; i++) {
    //cout << "[" << i << "]: " <<  fibonacci_old(i) << endl;
    //cout << "[" << i << "]: " <<  fibonacci(i) << endl;
    cout << "[" << i << "]: " <<  fibonacci2(i) << endl;
  }
}
