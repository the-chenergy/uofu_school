#include <bits/stdc++.h>
using namespace std;

int lis(vector<int>& arr, int max_index, int start_index)
{
  if (start_index == arr.size())
    return 0;

  int len_if_use_it = 0;
  if (max_index == -1 || arr[start_index] > arr[max_index])
    len_if_use_it = lis(arr, start_index, start_index + 1) + 1;
  int len_if_lose_it = lis(arr, max_index, start_index + 1);

  return max(len_if_use_it, len_if_lose_it);
}

int lis(vector<int>& arr)
{
  return lis(arr, -1, 0);
}

int main()
{
  vector<int> arr = {3, 1, 4, 1, 5, 9};
  cout << lis(arr);
}