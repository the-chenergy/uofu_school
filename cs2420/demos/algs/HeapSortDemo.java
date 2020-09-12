package demos.algs;

import java.util.Arrays;

/**
 * Explores and demonstrates how the Heap Sort works for integer arrays.
 * 
 * @author Qianlang Chen
 * @version W 03/27/19
 */
public class HeapSortDemo
{
	/**
	 * Performs a Heap Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array.
	 */
	public static int[] heapSort(int[] array)
	{
		// view the array as already, structure-wise, a binary max heap
		// percolate elements down backwards, ignoring the last row, to order the heap
		// (moving the max element to the root)
		for (int i = array.length / 2 - 1; i >= 0; i--)
			heapify(array, array.length, i);
			
		// after the order is satisfied, the max element should be at the root
		// take each max element out and put it at the end of the array, making it sorted
		for (int i = array.length - 1; i >= 0; i--)
		{
			// swap the root with the last element in the heap,
			// putting it into the sorted portion and keeping the heap's structure
			swapElements(array, 0, i);
			
			// order the heap again to move the next max element to the root
			heapify(array, i, 0);
		}
		
		return array;
	}
	
	/**
	 * Heapifies a sub-tree in a binary max heap, moving its maximum element to the root.
	 * 
	 * @param array The array containing the binary heap to heapify.
	 * @param size  The length of the part of the array that the heap uses.
	 * @param index The index of the element to percolate down.
	 */
	private static void heapify(int[] array, int size, int index)
	{
		// when the current element is on the last row (no need to heapify the last row)
		if (index >= size / 2)
			return;
		
		// the indices to the left and right children of the current index
		int left = 2 * index + 1, right = 2 * index + 2;
		
		// find the higher of the two children
		int max = (right < size && array[right] > array[left] ? right : left);
		
		// when the root is already the highest in its sub-tree
		if (array[index] >= array[max])
			return;
		
		// percolate the current element down since one of its children is higher
		swapElements(array, index, max);
		
		// since the sub-tree is modified, heapify it to ensure it is ordered
		heapify(array, size, max);
	}
	
	/**
	 * Swaps the elements at two specific indices in an integer array.
	 * 
	 * @param array       The array to swap elements in.
	 * @param index       The index to swap.
	 * @param targetIndex The target index to swap the element with.
	 */
	private static void swapElements(int[] array, int index, int targetIndex)
	{
		int temp = array[index];
		
		array[index] = array[targetIndex];
		array[targetIndex] = temp;
	}
	
	/** ... **/
	public static void main(String[] args)
	{
		// ------------ TEST ------------
		
		System.out.println(Arrays.toString(heapSort(new int[] {3, 6, 9, 2, 5, 8, 1, 4, 7})));
		System.out.println(Arrays.toString(heapSort(new int[] {2, 3, 1, 3, 2, 1, 4, 4})));
		
		// ------------ TIMING ------------
		
		int numLoops = 10_000, step = 200, max = 2_400;
		
		System.out.printf("\n(Average of %,d)\n\nN\tHeap Sort\tJava's Merge Sort\n", numLoops);
		
		for (int n = step; n <= max; n += step)
		{
			System.out.printf("%,d", n);
			
			long start, end;
			
			// time our heap sort
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				heapSort(generateArray(n));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				generateArray(n);
			
			System.out.printf("\t%,.0f", ((end - start) - (System.nanoTime() - end)) / (double)numLoops);
			
			// time java's merge sort
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				Arrays.sort(generateArray(n));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				generateArray(n);
			
			System.out.printf("\t%,.0f\n", ((end - start) - (System.nanoTime() - end)) / (double)numLoops);
		}
	}
	
	/** Generates an integer array with random integers ranged from 0 to n-1. **/
	private static int[] generateArray(int size)
	{
		int[] array = new int[size];
		
		for (int i = 0; i < size; i++)
			array[i] = (int)(Math.random() * size);
		
		return array;
	}
}
