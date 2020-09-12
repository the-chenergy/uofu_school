import java.util.*;
public class MergeSortDemo
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		mergeSort(arr, 0, n);
		System.out.println(Arrays.toString(arr));
	}

	private static void mergeSort(int[] arr, int low, int high)
	{
		if (low >= high - 1)
			return;

		int mid = (low + high) / 2;
		mergeSort(arr, low, mid);
		mergeSort(arr, mid, high);

		int[] temp = Arrays.copyOfRange(arr, low, high);
		int left = 0, right = mid - low;
		for (int i = low; i < high; i++)
		{
			if (right >= high - low || left < mid - low && temp[left] <= temp[right])
			{
				arr[i] = temp[left];
				left++;
			}
			else
			{
				arr[i] = temp[right];
				right++;
			}
		}
	}
}