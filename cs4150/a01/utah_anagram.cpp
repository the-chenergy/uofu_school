#include <bits/stdc++.h>
using namespace std;
typedef long long ll;

const ll pr[] = {1,  2,  3,  5,  7,  11, 13, 17, 19, 23, 29, 31, 37,
                 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

int main()
{
  ios::sync_with_stdio(0);
  cin.tie(0);

  int n, k;
  scanf("%d %d", &n, &k);
  unordered_map<ll, bool> m(n);
  int ans = 0;
  for (int i = 0; i < n; i++)
  {
    ll x = 1;
    for (int j = 0; j < k;)
    {
      int ch = getchar();
      if (ch >= 'a' && ch <= 'z')
      {
        x *= pr[ch - 'a'];
        j++;
      }
    }
    auto it = m.find(x);
    if (it == m.end())
    {
      m[x] = false;
      ans++;
    }
    else if (!it->second)
    {
      it->second = true;
      ans--;
    }
  }
  printf("%d", ans);
}