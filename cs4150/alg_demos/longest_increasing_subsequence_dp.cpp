#include <bits/stdc++.h>
using namespace std;

int lis(vector<int>& arr)
{
  const int n = arr.size();

  int ans[n + 1][n + 1];
  for (int max_index = 0; max_index <= n; max_index++)
    ans[max_index][n] = 0;

  for (int start_index = n - 1; start_index >= 0; start_index--)
  {
    for (int max_index = -1; max_index < start_index; max_index++)
    {
      int len_if_use_it = 0;
      if (max_index == -1 || arr[start_index] > arr[max_index])
        len_if_use_it = ans[start_index + 1][start_index + 1] + 1;
      int len_if_lose_it = ans[max_index + 1][start_index + 1];

      ans[max_index + 1][start_index] = max(len_if_use_it, len_if_lose_it);
    }
  }

  return ans[0][0];
}

int main()
{
  vector<int> arr = {3, 1, 4, 1, 5, 9};
  cout << lis(arr);
}