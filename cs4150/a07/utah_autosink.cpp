#include <bits/stdc++.h>
using namespace std;
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  int n;
  cin >> n;
  unordered_map<string, int> c;
  int t[2000];
  for (int i = 0; i < n; i++) {
    string s;
    int j;
    cin >> s >> j;
    c[s] = i;
    t[i] = j;
  }
  int h;
  cin >> h;
  vector<vector<array<int, 2>>> g(n);
  for (int i = 0; i < h; i++) {
    string ss, es;
    cin >> ss >> es;
    int s = c[ss], e = c[es];
    g[s].push_back({e, t[e]});
  }
  int q;
  cin >> q;
  vector<vector<int>> d(n);
  vector<vector<bool>> v(n);
  while (q--) {
    string ss, es;
    cin >> ss >> es;
    int s = c[ss], e = c[es];
    if (s == e) {
      cout << "0\n";
      continue;
    }
    if (d[s].empty()) {
      d[s].resize(n, 2e9);
      v[s].resize(n, false);
      priority_queue<array<int, 2>> pq;
      pq.push({0, s});
      while (pq.size()) {
        int w = -pq.top()[0], x = pq.top()[1];
        pq.pop();
        if (v[s][x]) continue;
        v[s][x] = true;
        for (auto y : g[x]) {
          if (v[s][y[0]]) continue;
          int nw = w + y[1];
          if (nw < d[s][y[0]]) {
            d[s][y[0]] = nw;
            pq.push({-nw, y[0]});
          }
        }
      }
    }
    if (d[s][e] == 2e9)
      cout << "NO\n";
    else
      cout << d[s][e] << '\n';
  }
}