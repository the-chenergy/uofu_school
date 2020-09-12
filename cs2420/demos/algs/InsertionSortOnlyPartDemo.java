package demos.algs;

import java.util.Arrays;

public class InsertionSortOnlyPartDemo
{
	/**
	 * Performs an Insertion Sort on a portion of an integer array.
	 * 
	 * @param array The array to sort.
	 * @param low   The lower bound of the part to sort (inclusive).
	 * @param high  The upper bound of the part to sort (inclusive).
	 */
	public static void insertionSort(int[] array, int low, int high)
	{
		for (int i = low + 1; i <= high; i++)
		{
			int target = array[i];
			int tarIndex = i;
			
			while (tarIndex > low && target < array[tarIndex - 1])
				array[tarIndex] = array[--tarIndex];
			
			array[tarIndex] = target;
		}
	}
	
	/** ... **/
	public static void main(String[] args)
	{
		int[] array0 = {3, 6, 9, 0, 2, 5, 8, 11, 1, 4, 7, 10};
		int[] array1 = {2, 23, 47, 5, 3, 19, 37, 13, 7, 29, 53, 31, 11, 43, 41, 17};
		int[] array2 = {4, 4, 3, 1, 2, 3, 2, 1};
		int[] array3 = {};
		
		insertionSort(array0, 3, 11);
		insertionSort(array1, 4, 10);
		insertionSort(array2, 2, 6);
		insertionSort(array3, 0, 0);
		
		System.out.println(Arrays.toString(array0));
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
		System.out.println(Arrays.toString(array3));
	}
}
