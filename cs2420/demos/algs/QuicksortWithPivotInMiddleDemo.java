package demos.algs;

import java.util.Arrays;

/**
 * Explores how Quicksort is going to work without using the Middle-Of-3 method, or more
 * specifically, how it is going to work without getting the last element in the array sorted.
 * 
 * @author Qianlang Chen
 * @version F 02/08/19
 */
public class QuicksortWithPivotInMiddleDemo
{
	/**
	 * Performs a Quicksort on an integer array.
	 * 
	 * @param array The array to sort.
	 */
	public static void quicksort(int[] array)
	{
		quicksort(array, 0, array.length - 1);
	}
	
	/**
	 * Performs a Quicksort on a portion of an integer array.
	 * 
	 * @param array The array to sort.
	 * @param low   The lower bound of the portion to sort (inclusive).
	 * @param high  The upper bound of the portion to sort (inclusive).
	 */
	private static void quicksort(int[] array, int low, int high)
	{
		if (low >= high)
			return;
		
		swapElements(array, getPivot(array, low, high), high);
		
		int pivot = array[high];
		
		int i = low, j = high - 1;
		while (i <= j)
		{
			for (; i <= j && array[i] < pivot; i++);
			for (; i <= j && array[j] > pivot; j--);
			
			if (i <= j)
				swapElements(array, i++, j--);
		}
		
		swapElements(array, i, high);
		
		quicksort(array, low, i - 1);
		quicksort(array, i + 1, high);
	}
	
	/**
	 * Returns index of the pivot for a Quicksort.
	 * 
	 * @param array The array to sort.
	 * @param low   The lower bound of the portion to sort (inclusive).
	 * @param high  The upper bound of the portion to sort (inclusive).
	 * @return The index of the pivot.
	 */
	private static int getPivot(int[] array, int low, int high)
	{
		return (int)(Math.random() * (high - low + 1) + low);
	}
	
	/**
	 * Swaps two elements in an integer array.
	 * 
	 * @param array    The array to swap elements in.
	 * @param index    The element to swap.
	 * @param tarIndex The target element to swap.
	 */
	private static void swapElements(int[] array, int index, int tarIndex)
	{
		int temp = array[index];
		
		array[index] = array[tarIndex];
		array[tarIndex] = temp;
	}
	
	/** ... **/
	public static void main(String[] args)
	{
		int[] array0 = {3, 6, 9, 0, 2, 5, 8, 11, 1, 4, 7, 10};
		int[] array1 = {2, 23, 47, 5, 3, 19, 37, 13, 7, 29, 53, 31, 11, 43, 41, 17};
		int[] array2 = {4, 4, 3, 1, 2, 3, 2, 1};
		int[] array3 = {};
		
		quicksort(array0);
		quicksort(array1);
		quicksort(array2);
		quicksort(array3);
		
		System.out.println(Arrays.toString(array0));
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
		System.out.println(Arrays.toString(array3));
	}
}
