import java.util.*;

public class PlantingTrees
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();

		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		mySort(arr);
		for (int i = n - 2, d = 1; i >= 0; i--, d++)
			arr[i] = Math.max(arr[i] + d, arr[i + 1]);

		System.out.println(arr[0] + 2);
	}

	// Quick-sort with the median-of-3 strategy.
	// Time complexity: O(n^2) worst case / O(nlg(n)) average case
	private static void mySort(int[] arr)
	{
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int low, int high)
	{
		if (low >= high)
			return;

		int mid = (low + high) / 2;
		if (arr[mid] < arr[low])
			swapElements(arr, low, mid);
		if (arr[high] < arr[low])
			swapElements(arr, low, high);
		if (arr[high] < arr[mid])
			swapElements(arr, mid, high);

		swapElements(arr, mid, high - 1);

		int pivot = arr[high - 1];
		int i = low, j = high - 1;
		while (i < j)
		{
			while (arr[++i] < pivot)
			{
			}
			while (arr[--j] > pivot)
			{
			}

			if (i < j)
				swapElements(arr, i, j);
		}
		swapElements(arr, i, high - 1);

		quickSort(arr, low, i - 1);
		quickSort(arr, i + 1, high);
	}

	private static void swapElements(int[] arr, int index, int tarIndex)
	{
		int temp = arr[index];

		arr[index] = arr[tarIndex];
		arr[tarIndex] = temp;
	}
}