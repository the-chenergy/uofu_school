package assign05;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides different sorting methods to sort <code>ArrayList</code>.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version February 13, 2019
 */
public class ArrayListSorter
{
	/**
	 * Generates and returns an <code>ArrayList</code> of integers 1 to <code>size</code> in
	 * ascending order.
	 * 
	 * @param size The size of the <code>ArrayList</code> to generate.
	 * @return An <code>ArrayList</code> containing integers 1 to <code>size</code> in ascending
	 *         order.
	 */
	public static ArrayList<Integer> generateAscending(int size)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int i = 1; i <= size; i++)
			result.add(i);
		
		return result;
	}
	
	/**
	 * Generates and returns an <code>ArrayList</code> of integers 1 to <code>size</code> in
	 * permuted order (i,e., randomly ordered).
	 * 
	 * @param size The size of the <code>ArrayList</code> to generate.
	 * @return An <code>ArrayList</code> containing integers 1 to <code>size</code> in random order.
	 */
	public static ArrayList<Integer> generatePermuted(int size)
	{
		ArrayList<Integer> result = generateAscending(size);
		
		Collections.shuffle(result);
		
		return result;
	}
	
	/**
	 * Generates and returns an <code>ArrayList</code> of integers 1 to <code>size</code> in
	 * descending order.
	 * 
	 * @param size The size of the <code>ArrayList</code> to generate.
	 * @return An <code>ArrayList</code> containing integers 1 to <code>size</code> in descending
	 *         order.
	 */
	public static ArrayList<Integer> generateDescending(int size)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		while (size >= 1)
			result.add(size--);
		
		return result;
	}
	
	/**
	 * The threshold to switch to an Insertion Sort.
	 * 
	 * @private
	 */
	// private static final int THRESHOLD = 12;
	
	public static int THRESHOLD = 12; // for experimenting only
	
	/**
	 * Performs a merge sort on the generic <code>ArrayList</code> given as input.
	 * 
	 * @param list The <code>ArrayList</code> to sort.
	 */
	public static <T extends Comparable<? super T>> void mergesort(ArrayList<T> list)
	{
		mergesort(list, new ArrayList<T>(list), 0, list.size() - 1);
	}
	
	/**
	 * Performs a merge sort on a portion of a generic <code>ArrayList</code>.
	 * 
	 * @param list     The <code>ArrayList</code> to sort.
	 * @param tempList The <code>ArrayList</code> to store temporary data.
	 * @param left     The left index of the portion to sort.
	 * @param right    The right index of the portion to sort.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> void mergesort(ArrayList<T> list, ArrayList<T> tempList, int left,
		int right)
	{
		// Base case: switch to an Insertion Sort for small lists.
		
		if (right - left < THRESHOLD)
		{
			insertionSort(list, left, right);
			
			return;
		}
		
		// Divide the list into two halves and repeat until the list is in the simplest pieces.
		
		int mid = (left + right) / 2;
		
		mergesort(list, tempList, left, mid);
		mergesort(list, tempList, mid + 1, right);
		
		// Merge the two halves of the list.
		
		merge(list, tempList, left, mid, right);
	}
	
	/**
	 * Performs an insertion sort on a portion of a generic <code>ArrayList</code>.
	 * 
	 * @param list  The <code>ArrayList</code> to sort.
	 * @param left  The left index of the portion to sort.
	 * @param right The right index of the portion to sort.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> void insertionSort(ArrayList<T> list, int left, int right)
	{
		for (int i = left + 1; i <= right; i++)
		{
			T target = list.get(i);
			int targetIndex = i;
			
			while (targetIndex > left && target.compareTo(list.get(targetIndex - 1)) < 0)
				list.set(targetIndex, list.get(--targetIndex));
			
			list.set(targetIndex, target);
		}
	}
	
	/**
	 * Merges two halves of an <code>ArrayList</code> back into one in a merge sort.
	 * 
	 * @param list     The <code>ArrayList</code> to merge.
	 * @param tempList The <code>ArrayList</code> to store temporary data.
	 * @param left     The left index of the portion to sort.
	 * @param mid      The middle index of the portion to sort.
	 * @param right    The right index of the portion to sort.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> void merge(ArrayList<T> list, ArrayList<T> tempList, int left,
		int mid, int right)
	{
		// Make a copy of the data in the list.
		
		int targetIndex;
		
		for (targetIndex = left; targetIndex <= right; targetIndex++)
			tempList.set(targetIndex, list.get(targetIndex));
		
		// Merge the two halves and sort the data.
		
		int i = left, j = mid + 1;
		
		targetIndex = left;
		
		while (i <= mid && j <= right)
		{
			if (tempList.get(i).compareTo(tempList.get(j)) < 0)
				list.set(targetIndex++, tempList.get(i++));
			else
				list.set(targetIndex++, tempList.get(j++));
		}
		
		// Merge the rest of elements (that are not compared), if there are any.
		
		while (i <= mid)
			list.set(targetIndex++, tempList.get(i++));
		while (j <= right)
			list.set(targetIndex++, tempList.get(j++));
	}
	
	/**
	 * Performs a Quicksort on the generic <code>ArrayList</code> given as input.
	 * 
	 * @param list The <code>ArrayList</code> to sort.
	 */
	public static <T extends Comparable<? super T>> void quicksort(ArrayList<T> list)
	{
		quicksort(list, 0, list.size() - 1);
	}
	
	/**
	 * Performs a Quicksort on a portion of a generic <code>ArrayList</code>.
	 * 
	 * @param list  The <code>ArrayList</code> to sort.
	 * @param left  The left index of the portion to sort.
	 * @param right The right index of the portion to sort.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> void quicksort(ArrayList<T> list, int left, int right)
	{
		// Base case: only 1 or 0 element in the list.
		
		if (left >= right)
			return;
		
		// Partition the list and get the index of the pivot.
		
		int pivotIndex = partition(list, left, right);
		
		// Repeat this process to get the sub-lists on both sides of the pivot partitioned.
		// The pivot is now brought to its correct position, so we ignore it for the rest of
		// the sorting process.
		
		quicksort(list, left, pivotIndex - 1);
		quicksort(list, pivotIndex + 1, right);
	}
	
	/**
	 * Partitions the two portions of an <code>ArrayList</code> in a Quicksort.
	 * 
	 * @param list  The <code>ArrayList</code> to partition.
	 * @param left  The left index of the portion to sort.
	 * @param right The right index of the portion to sort.
	 * @return The final index of the pivot (as an indication of the middle of the two portions).
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> int partition(ArrayList<T> list, int left, int right)
	{
		// Pick a pivot and get it to the edge of the list.
		
		swapElements(list, getPivot(list, left, right), right);
		
		T pivot = list.get(right);
		
		// Partition the list by moving all elements smaller than the pivot to the left
		// and those greater than the pivot to the right.
		
		int i = left, j = right - 1;
		
		while (true)
		{
			while (i <= j && list.get(i).compareTo(pivot) < 0)
				i++;
			while (i <= j && list.get(j).compareTo(pivot) > 0)
				j--;
			
			if (i > j)
				break;
			
			swapElements(list, i++, j--);
		}
		
		swapElements(list, i, right); // get the pivot back to the middle
		
		return i;
	}
	
	/**
	 * Picks the Median-of-3 as the pivot for Quicksort.
	 * 
	 * @private
	 */
	private static final int MO3 = 0;
	
	/**
	 * Picks a random element as the pivot for Quicksort.
	 * 
	 * @private
	 */
	private static final int RANDOM = 1;
	
	/**
	 * Picks the last element as the pivot for Quicksort.
	 * 
	 * @private
	 */
	private static final int LAST = 2;
	
	/**
	 * The strategy used in <code>getPivot()</code> method to pick the pivot for Quicksort.
	 * 
	 * @private
	 */
	// private static int getPivotStrategy = MO3;
	
	public static int getPivotStrategy = MO3; // for experimenting only
	
	/**
	 * Returns the index of the pivot in a portion of a generic <code>ArrayList</code>.
	 * 
	 * @param list  The <code>ArrayList</code> to sort.
	 * @param left  The left index of the portion to sort.
	 * @param right The right index of the portion to sort.
	 * @return The index of the pivot.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> int getPivot(ArrayList<T> list, int left, int right)
	{
		switch (getPivotStrategy)
		{
			case MO3:
				int mid = (left + right) / 2;
				
				// Find the median out of the first element, the middle element, and the last element.
				
				if (list.get(right).compareTo(list.get(left)) < 0)
					swapElements(list, left, right);
				
				if (list.get(mid).compareTo(list.get(left)) < 0)
					return left;
				
				if (list.get(right).compareTo(list.get(mid)) < 0)
					return right;
				
				return mid;
			
			case RANDOM:
				// generate a random integer between left and right, inclusive
				return (int)(Math.random() * (right - left + 1) + left);
			
			case LAST:
				return right;
			
			default: // invalid pivot-picking strategy
				return -1;
		}
	}
	
	/**
	 * Swaps the elements at two specific indices in a generic <code>ArrayList</code>.
	 * 
	 * @param list        The <code>ArrayList</code> to swap elements in.
	 * @param index       The index to swap.
	 * @param targetIndex The target index to swap.
	 * 
	 * @private
	 */
	private static <T extends Comparable<? super T>> void swapElements(ArrayList<T> list, int index, int targetIndex)
	{
		T temp = list.get(index);
		
		list.set(index, list.get(targetIndex));
		list.set(targetIndex, temp);
	}
}
