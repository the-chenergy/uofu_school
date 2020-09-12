package demos.algs;

import java.util.Arrays;

/**
 * Explores how Merge Sort with pre-allocated temporary array works.
 * 
 * @author Qianlang Chen
 * @version February 8, 2019
 */
public class MergeSortWithPreAllocatedSpaceDemo
{
	/**
	 * Performs a Merge Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 */
	public static void mergeSort(int[] array)
	{
		mergeSort(array, new int[array.length], 0, array.length - 1);
	}
	
	/**
	 * Performs a Merge Sort on a part of an integer array.
	 * 
	 * @param array     The array to sort.
	 * @param tempArray The array to store temporary data.
	 * @param low       The lower bound to sort (inclusive).
	 * @param high      The upper bound to sort (inclusive).
	 * 
	 * @private
	 */
	private static void mergeSort(int[] array, int[] tempArray, int low, int high)
	{
		// Base case.
		
		if (low >= high)
			return;
		
		// Divide the array into two halves.
		
		int mid = (low + high) / 2;
		
		mergeSort(array, tempArray, low, mid);
		mergeSort(array, tempArray, mid + 1, high);
		
		// Make a copy of the data in the array.
		
		int tarIndex;
		
		for (tarIndex = low; tarIndex <= high; tarIndex++)
			tempArray[tarIndex] = array[tarIndex];
		
		// Merge the two halves and sort the data.
		
		int i = low, j = mid + 1;
		
		tarIndex = low;
		
		while (i <= mid && j <= high)
		{
			if (tempArray[i] < tempArray[j])
				array[tarIndex++] = tempArray[i++];
			else
				array[tarIndex++] = tempArray[j++];
		}
		
		// Merge the rest of elements (that are not compared), if there are any.
		
		while (i <= mid)
			array[tarIndex++] = tempArray[i++];
		while (j <= high)
			array[tarIndex++] = tempArray[j++];
	}
	
	/** ... **/
	public static void main(String[] args)
	{
		int[] array0 = {3, 6, 9, 0, 2, 5, 8, 11, 1, 4, 7, 10};
		int[] array1 = {2, 23, 47, 5, 3, 19, 37, 13, 7, 29, 53, 31, 11, 43, 41, 17};
		int[] array2 = {4, 4, 3, 1, 2, 3, 2, 1};
		int[] array3 = {};
		
		mergeSort(array0);
		mergeSort(array1);
		mergeSort(array2);
		mergeSort(array3);
		
		System.out.println(Arrays.toString(array0));
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
		System.out.println(Arrays.toString(array3));
	}
}
