#include <bits/stdc++.h>
using namespace std;
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  int n;
  cin >> n;
  unordered_map<string, int> si(n);
  vector<string> is(n);
  for (int i = 0; i < n; i++) {
    string s;
    cin >> s;
    si[s] = i;
    is[i] = s;
  }
  int f;
  cin >> f;
  vector<vector<int>> g(n);
  for (int i = 0; i < f; i++) {
    string s, t;
    cin >> s >> t;
    g[si[s]].push_back(si[t]);
    g[si[t]].push_back(si[s]);
  }
  int r;
  cin >> r;
  while (r--) {
    string s;
    cin >> s;
    cout << s;
    queue<array<int, 2>> q;
    q.push({si[s], 0});
    vector<bool> v(n);
    v[si[s]] = true;
    priority_queue<string, vector<string>, greater<string>> p;
    int cd = 0;
    while (q.size()) {
      int x = q.front()[0], d = q.front()[1];
      q.pop();
      if (d != cd) {
        while (p.size()) {
          cout << ' ' << p.top();
          p.pop();
        }
        cd = d;
      }
      for (int y : g[x]) {
        if (v[y]) continue;
        v[y] = true;
        q.push({y, d + 1});
        p.push(is[y]);
      }
    }
    while (p.size()) {
      cout << ' ' << p.top();
      p.pop();
    }
    for (int i = 0; i < n; i++) {
      if (!v[i]) p.push(is[i]);
    }
    while (p.size()) {
      cout << ' ' << p.top();
      p.pop();
    }
    cout << '\n';
  }
}