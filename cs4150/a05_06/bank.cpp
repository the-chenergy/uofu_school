#include <bits/stdc++.h>
using namespace std;
int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  int n, l;
  cin >> n >> l;
  vector<vector<int>> a;
  for (int i = 0; i < n; i++) {
    int c, t;
    cin >> c >> t;
    a.push_back({t, c});
  }
  sort(a.begin(), a.end());
  vector<vector<int>> dp(n + 1, vector<int>(l + 1));
  for (int i = n - 1; i >= 0; i--) {
    for (int t = 0; t < l; t++) {
      dp[i][t] = dp[i + 1][t];
      if (a[i][0] >= t) dp[i][t] = max(dp[i][t], dp[i + 1][t + 1] + a[i][1]);
    }
  }
  cout << dp[0][0];
}