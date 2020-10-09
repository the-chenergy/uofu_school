#include <iostream>
#include <set>
#include <vector>
using namespace std;
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  int t;
  cin >> t;
  while (t--) {
    int m, p;
    cin >> m >> p;
    p /= 2;
    vector<set<vector<int>>> a(101), aa(101);
    for (int i = 0; i < m; i++) {
      int b, e;
      cin >> b >> e;
      for (int j = b; j <= e; j++) {
        a[j].insert({e, -b, b, i});
        aa[j].insert({e, -b, b, i});
      }
    }
    int n = m;
    for (int i = 1; i <= 100 && n; i++) {
      if (i % 7 == 0 || i % 7 == 6) continue;
      if (a[i].empty()) continue;
      auto it = a[i].begin();
      int j = 0;
      while (j < p && it != a[i].end()) {
        vector<int> x = *it;
        for (int k = x[2]; k <= x[0]; k++) {
          if (k == i)
            it = a[i].erase(it);
          else
            a[k].erase(x);
        }
        j++;
        n--;
      }
    }
    if (!n) {
      cout << "fine\n";
      continue;
    }
    n = m;
    for (int i = 1; i <= 100 && n; i++) {
      if (aa[i].empty()) continue;
      auto it = aa[i].begin();
      int j = 0;
      while (j < p && it != aa[i].end()) {
        vector<int> x = *it;
        for (int k = x[2]; k <= x[0]; k++) {
          if (k == i)
            it = aa[i].erase(it);
          else
            aa[k].erase(x);
        }
        j++;
        n--;
      }
    }
    if (n)
      cout << "serious trouble\n";
    else
      cout << "weekend work\n";
  }
}