#include <algorithm>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <vector>

// int num_tests = 1, max_arr_len = 8, max_arr_element = 12;
int num_tests = 20736, max_arr_len = 144, max_arr_element = 288;
bool always_print_results = false, print_calls = false;

using namespace std;
int kth(int k, vector<int>& a, vector<int>& b, int al, int ar, int bl, int br) {
  if (print_calls)
    cout << ">>> called " << k << ' ' << al << ' ' << ar << ' ' << bl << ' '
         << br << ' ' << '\n';
  if (al == ar) return b[bl + k];
  if (bl == br) return a[al + k];
  if (k == 0) return min(a[al], b[bl]);
  int m = k / 2;
  if (al + m >= ar) {
    if (a[ar - 1] < b[bl + m]) return b[bl + k - (ar - al)];
    return kth(m, a, b, al, ar, bl + k - m, br);
  }
  if (bl + m >= br) {
    if (b[br - 1] < a[al + m]) return a[al + k - (br - bl)];
    return kth(m, a, b, al + k - m, ar, bl, br);
  }
  if (a[al + m] < b[bl + m])
    return kth(m, a, b, al + k - m, ar, bl, bl + k - m);
  else if (a[al + m] > b[bl + m])
    return kth(m, a, b, al, al + k - m, bl + k - m, br);
  return a[al + m];
}
int kth(int k, vector<int>& a, vector<int>& b) {
  return kth(k, a, b, 0, a.size(), 0, b.size());
}
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  srand(time(nullptr));
  bool passed = true;
  for (int t = 0; t < num_tests; t++) {
    int an = rand() % max_arr_len + 1, bn = rand() % max_arr_len + 1;
    vector<int> a, b, s;
    a.reserve(an);
    b.reserve(bn);
    s.reserve(an + bn);
    for (int i = 0; i < an; i++) {
      int x = rand() % max_arr_element;
      a.push_back(x);
      s.push_back(x);
    }
    for (int i = 0; i < bn; i++) {
      int x = rand() % max_arr_element;
      b.push_back(x);
      s.push_back(x);
    }
    sort(a.begin(), a.end());
    sort(b.begin(), b.end());
    sort(s.begin(), s.end());
    bool arr_printed = false;
    for (int i = 0; i < an + bn; i++) {
      int expected = s[i];
      int actual = kth(i, a, b);
      if (always_print_results || expected != actual) {
        if (!arr_printed) {
          cout << "a:";
          for (auto it : a) cout << ' ' << it;
          cout << "\nb:";
          for (auto it : b) cout << ' ' << it;
          cout << '\n';
        }
        cout << "k=" << i << ", expected " << expected << ", got " << actual
             << "\n";
        arr_printed = true;
        passed = false;
      }
    }
    if (arr_printed) cout << '\n';
  }
  if (passed) cout << "all " << num_tests << " tests passed\n";
}
