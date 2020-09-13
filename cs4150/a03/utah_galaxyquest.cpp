#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
// const int lim = 2e6;
// typedef pair<ll, ll> pl;
// stack<pl> s;
// pl p[lim];
// int p[lim];
ll dd;
bool dist(int x, int y, int xx, int yy)
{
  ll dx = (ll)xx - (ll)x, dy = (ll)yy - (ll)y;
  return dx * dx + dy * dy <= dd;
}
int main()
{
  ios::sync_with_stdio(0);
  cin.tie(0);
  int d, k;
  scanf("%d %d", &d, &k);
  dd = (ll)d * (ll)d;
  vector<int> p;
  p.reserve(2 * k);
  int s = 0;
  int sx, sy;
  for (int i = 0; i < k; i++)
  {
    ll x, y;
    scanf("%lld %lld", &x, &y);
    if (s == 0)
    {
      sx = x;
      sy = y;
      s = 1;
    }
    else if (dist(sx, sy, x, y))
    {
      s++;
    }
    else
    {
      s--;
    }
    p.push_back(x);
    p.push_back(y);
  }
  if (s == 0)
  {
    printf("NO");
  }
  else
  {
    s = 0;
    for (int ii = 0; ii < 2 * k; ii += 2)
    {
      if (dist(sx, sy, p[ii], p[ii + 1]))
      {
        s++;
      }
    }
    if (s > k / 2)
    {
      printf("%d", s);
    }
    else
    {
      printf("NO");
    }
  }
}
/* int main()
{
  ios::sync_with_stdio(0);
  cin.tie(0);
  int k;
  cin >> d >> k;
  d *= d;
  for (int i = 0; i < k; i++)
  {
    ll x, y;
    cin >> x >> y;
    p[i] = make_pair(x, y);
    if (s.empty())
    {
      s.push(p[i]);
    }
    else
    {
      pl t = s.top();
      if (dist(t.first, t.second, p[i].first, p[i].second))
      {
        s.push(p[i]);
      }
      else
      {
        s.pop();
      }
    }
  }
  if (s.empty())
  {
    cout << "NO";
    return 0;
  }
  pl t = s.top();
  int count = 0;
  for (int i = 0; i < k; i++)
  {
    if (dist(t.first, t.second, p[i].first, p[i].second))
    {
      count++;
    }
  }
  if (count > k / 2)
  {
    cout << count;
  }
  else
  {
    cout << "NO";
  }
} */
/*
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
 */