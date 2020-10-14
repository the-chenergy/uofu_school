#include <bits/stdc++.h>
using namespace std;
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  int n, l;
  cin >> n >> l;
  priority_queue<vector<int>> q;
  for (int i = 0; i < n; i++) {
    int c, w;
    cin >> c >> w;
    q.push({c, w});
  }
  int ans = 0, a = l + 1;
  vector<bool> u(l);
  while (q.size() && a > 0) {
    int c = q.top()[0], w = q.top()[1];
    q.pop();
    for (int t = w; t >= 0; t--) {
      if (!u[t]) {
        u[t] = true;
        ans += c;
        a--;
        break;
      }
    }
  }
  cout << ans;
}