#include <bits/stdc++.h>
using namespace std;
typedef pair<double, double> pd;
const int lim = 1e6;
pd a[lim];
double d;
pd null_pd = make_pair(-1, -1);
bool dist(pd p, pd q)
{
  return sqrt(pow(p.first - q.first, 2) + pow(p.second - q.second, 2)) <= d;
}
pd count(pd p, bool has_q, pd q, int l, int r)
{
  int pc = 0, qc = 0;
  for (int i = l; i < r; i++)
  {
    if (dist(p, a[i]))
      pc++;
    if (has_q && dist(q, a[i]))
      qc++;
  }
  int n = (r - l) / 2;
  if (pc > n)
    return p;
  if (qc > n)
    return q;
  return null_pd;
}
pd solve(int l, int r)
{
  if (l == r)
    return null_pd;
  if (l == r - 1)
    return a[l];
  int m = (l + r + 1) / 2;
  pd lr = solve(l, m), rr = solve(m, r);
  if (lr != null_pd)
  {
    if (rr != null_pd)
      return count(lr, true, rr, l, r);
    return count(lr, false, null_pd, l, r);
  }
  if (rr != null_pd)
    return count(rr, false, null_pd, l, r);
  return null_pd;
}
int main()
{
  ios::sync_with_stdio(0);
  cin.tie(0);
  int k;
  cin >> d >> k;
  for (int i = 0; i < k; i++)
  {
    double x, y;
    cin >> x >> y;
    a[i] = make_pair(x, y);
  }
  pd p = solve(0, k);
  if (p == null_pd)
  {
    cout << "NO";
    return 0;
  }
  int ans = 0;
  for (int i = 0; i < k; i++)
    if (dist(p, a[i]))
      ans++;
  cout << ans;
}
