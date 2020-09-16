#include <bits/stdc++.h>
using namespace std;
typedef long long ll;
const int lim = 1001;
ll a[lim], c[lim];
int main()
{
  int n;
  cin >> n;
  if (n == 0)
  {
    cout << 0;
    return 0;
  }
  cin >> a[0];
  c[0] = 0;
  for (int i = 1; i <= n; i++)
  {
    cin >> a[i];
    c[i] = 1e18;
    for (int j = 0; j < i; j++)
    {
      ll d = abs(a[i] - a[j] - 400);
      c[i] = min(c[i], c[j] + d * d);
    }
  }
  cout << c[n];
}