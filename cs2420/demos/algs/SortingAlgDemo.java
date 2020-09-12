package demos.algs;

import java.util.Arrays;
import java.util.Random;

/**
 * An exploration of how each sorting algorithm works.
 * 
 * @author Qianlang Chen
 * @version 02/06/19
 */
public class SortingAlgDemo
{
	/**
	 * Performs a Selection Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] selectionSort(int[] array)
	{
		// Selection Sort breaks the array into a sorted and an unsorted portion,
		// finds and swaps the smallest element with the first element in the unsorted
		// portion, making the smallest element sorted. This process is then repeated.
		//
		// Example: ("//" separates the sorted and unsorted portions of the array.)
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {1 // 6, 9, 2, 5, 8, 3, 4, 7} (3 is swapped with 1 since its the smallest)
		// {1, 2 // 9, 6, 5, 8, 3, 4, 7}
		// {1, 2, 3 // 6, 5, 8, 9, 4, 7}
		// {1, 2, 3, 4 // 5, 8, 9, 6, 7}
		// {1, 2, 3, 4, 5 // 8, 9, 6, 7} (5 is swapped with itself)
		// {1, 2, 3, 4, 5, 6 // 9, 8, 7}
		// {1, 2, 3, 4, 5, 6, 7 // 8, 9}
		// {1, 2, 3, 4, 5, 6, 7, 8 // 9}
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}
		
		for (int i = 0; i < array.length; i++) // locate the sorted portion of the array
		{
			// Find the smallest element in the unsorted portion.
			
			int tarIndex = i; // the index of the smallest element
			
			for (int j = i + 1; j < array.length; j++)
				if (array[j] < array[tarIndex])
					tarIndex = j;
				
			// Swap the smallest element with the first element.
			
			int temp = array[i];
			array[i] = array[tarIndex];
			array[tarIndex] = temp;
		}
		
		return array;
	}
	
	/**
	 * Performs a Bubble Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] bubbleSort(int[] array)
	{
		// Bubble Sort breaks the array into a sorted and an unsorted portion and
		// swaps each pair of elements in the unsorted portion (ordered back to front),
		// bringing the smallest element to the front in the process and making it
		// sorted. This process is then repeated.
		//
		// Example: ("//" separates the sorted and unsorted portions of the array.)
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {1 // 3, 6, 9, 2, 5, 8, 4, 7} (1 is the bubble and is carried all the way to the front)
		// {1, 2 // 3, 6, 9, 5, 8, 4, 7}
		// {1, 2, 3 // 6, 9, 5, 8, 4, 7}
		// {1, 2, 3, 4 // 6, 9, 5, 8, 7}
		// {1, 2, 3, 4, 5 // 6, 9, 8, 7}
		// {1, 2, 3, 4, 5, 6 // 9, 8, 7}
		// {1, 2, 3, 4, 5, 6, 7 // 9, 8}
		// {1, 2, 3, 4, 5, 6, 7, 8 // 9}
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}
		
		for (int i = 0; i < array.length; i++) // locate the sorted portion of the array
		{
			// Performed from back to front, swap every pair of adjacent elements when needed.
			
			for (int j = array.length - 1; j > i; j--)
			{
				if (array[j] < array[j - 1])
				{
					// Swap the order of the pair.
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		
		return array;
	}
	
	/**
	 * Performs an Insertion Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] insertionSort(int[] array)
	{
		// Insertion Sort breaks the array into a sorted and an unsorted portion,
		// inserts the first element in the unsorted portion into its correct spot
		// in the sorted portion, and repeats this process.
		//
		// Example: ("//" separates the sorted and unsorted portions of the array.)
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {3 // 6, 9, 2, 5, 8, 1, 4, 7}
		// {3, 6 // 9, 2, 5, 8, 1, 4, 7} (6 is taken and sorted relatively with 3)
		// {3, 6, 9 // 2, 5, 8, 1, 4, 7}
		// {2, 3, 6, 9 // 5, 8, 1, 4, 7} (2 is inserted to its correct spot)
		// {2, 3, 5, 6, 9 // 8, 1, 4, 7}
		// {2, 3, 5, 6, 8, 9 // 1, 4, 7}
		// {1, 2, 3, 5, 6, 8, 9 // 4, 7}
		// {1, 2, 3, 4, 5, 6, 8, 9 // 7}
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}
		
		for (int i = 1; i < array.length; i++) // locate the sorted portion of the array;
		// we can assume the first element is sorted
		{
			// Find the appropriate index in the sorted portion for the first item in the
			// unsorted portion.
			
			int target = array[i]; // the first item in the unsorted portion
			int tarIndex = i; // the target index to insert the target
			
			while (tarIndex > 0 && target < array[tarIndex - 1]) // find the correct target index
				array[tarIndex] = array[--tarIndex]; // shift everything over to give target a spot
				
			// Insert the target to its appropriate index, making it sorted.
			
			array[tarIndex] = target;
		}
		
		return array;
	}
	
	/**
	 * Performs a Shellsort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] shellSort(int[] array)
	{
		// As long as our sorting algorithms only compare and swap elements in the array that
		// are next to each other, we can never do better than N^2. The Shellsort comes and
		// fixes this.
		//
		// The Shellsort compares and swaps elements far part (using insertion sort),
		// then it shrinks the gap between the elements it is swapping so that it tends toward
		// a basic insertion sort as the sort progresses. The gap-sequence determines the gap
		// size the sort is going to use in each iteration.
		//
		// Example: (with a gap-sequence of 4, 2, and 1)
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {3, 6, 9, 2, 5, 8, 1, 4, 7} (3, 5, and 7 are separated with gaps sized 4 and are sorted)
		// {3, 6, 9, 2, 5, 8, 1, 4, 7} (6 and 8 are also already sorted)
		// {3, 6, 1, 2, 5, 8, 9, 4, 7} (9 and 1 are sorted)
		// {3, 6, 1, 2, 5, 8, 9, 4, 7} (2 and 4 are sorted)
		// {1, 6, 3, 2, 5, 8, 7, 4, 9} (with a gap size of two, 1, 3, 5, 7, and 9 are sorted.
		// {1, 2, 3, 4, 5, 6, 7, 8, 9} (2, 4, 6, and 8 are then sorted)
		// {1, 2, 3, 4, 5, 6, 7, 8, 9} (finally, the basic Insertion Sort becomes much more efficient
		// because the elements are almost sorted; in this case, it happens to be already perfectly
		// sorted)
		
		for (int gap = array.length / 2; gap > 0; gap /= 2) // determines the gap sequence we are using:
		// length/2, length/4, ..., 4, 2, 1.
		{
			// A basic Insertion Sort, except it is performed on elements far apart.
			
			for (int i = gap; i < array.length; i++) // elements before gap can be assumed to be already sorted.
			{
				int target = array[i];
				int tarIndex = i;
				
				while (tarIndex >= gap && target < array[tarIndex - gap])
					array[tarIndex] = array[tarIndex -= gap];
				
				array[tarIndex] = target;
			}
		}
		
		return array;
	}
	
	/**
	 * Performs a Merge Sort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] mergeSort(int[] array)
	{
		// Merge Sort breaks the array into two halves, sorts each of the two sub-arrays, and
		// merges the two sorted sub-arrays back into one. This process is repeated for
		// each smaller-sized sub-array until there is only one element in the sub-array
		// which we do not need to sort.
		//
		// The sorting process is done during the merging period, and since each sub-array we
		// are merging are also sorted, we can take advantage of that to make a linear-merge
		// which has an excellent performance.
		//
		// Example:
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {3, 6, 9, 2, 5} / {8, 1, 4, 7} (divide in half)
		// {3, 6, 9} / {2, 5} // {8, 1} / {4, 7} (divide in half)
		// {3, 6} / {9} // {2} / {5} /// {8} / {1} // {4} / {7} (some already reached 1-element)
		// {3} / {6} // {9} /// {2} // {5} //// {8} // {1} /// {4} // {7} (now start merging)
		// {3, 6} / {9} // {2} / {5} /// {8} / {1} // {4} / {7} (3 and 6 are merged and sorted)
		// {3, 6, 9} / {2, 5} // {1, 8} / {4, 7} (each sub-array is merged and sorted)
		// {2, 3, 5, 6, 9} / {1, 4, 7, 8} (each of the original 2 halves is merged and sorted)
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}
		
		mergeSort(array, 0, array.length - 1);
		
		return array;
	}
	
	/**
	 * Merge-sorts a portion of an integer array.
	 * 
	 * @param array The array to sort.
	 * @param low   The start index of the portion in the sub-array.
	 * @param high  The end index of the portion in the sub-array.
	 * 
	 * @private
	 */
	private static void mergeSort(int[] array, int low, int high)
	{
		if (low >= high) // base case: only one element in the array
			return;
		
		// Divide the array into halves.
		
		int mid = (low + high) / 2;
		
		mergeSort(array, low, mid);
		mergeSort(array, mid + 1, high);
		
		// Merge and sort the two halves.
		
		// make a copy of the array, since we are merging them so some data will be overridden
		int[] tempLow = Arrays.copyOfRange(array, low, mid + 1);
		int[] tempHigh = Arrays.copyOfRange(array, mid + 1, high + 1);
		
		// go through two sub-arrays simultaneously and insert the next smallest element into
		// the original array.
		int i = 0, j = 0, tarIndex = low;
		while (i < tempLow.length && j < tempHigh.length)
		{
			// pick the smaller one of the two candidates
			if (tempLow[i] < tempHigh[j])
				array[tarIndex++] = tempLow[i++];
			else
				array[tarIndex++] = tempHigh[j++];
		}
		
		// put in the rest of the elements in either of the sub-array, if there are any.
		while (i < tempLow.length)
			array[tarIndex++] = tempLow[i++];
		
		while (j < tempHigh.length)
			array[tarIndex++] = tempHigh[j++];
	}
	
	/**
	 * Performs a Quicksort on an integer array.
	 * 
	 * @param array The array to sort.
	 * @return The sorted array (smallest to greatest).
	 */
	public static int[] quickSort(int[] array)
	{
		// Quicksort takes a guess of where the median (the middle of the sorted array) is,
		// moves all of the elements smaller than the guess of median (called a pivot) to the
		// left side of the pivot and those that are greater than the pivot to the right side,
		// making the pivot sorted. This process is then repeated on both sides of the pivot
		// until every element in the array has been a pivot and sorted.
		//
		// After reading the above, we know clearly that the efficiency if a Quicksort highly
		// depends on the quality of the pivot we pick. Picking a bad pivot every time could
		// even lead to a quadratic runtime behavior to Quicksort. In this example, we use
		// the median-of-3 method to choose our pivot, and this can lower the chance of us
		// picking a bad pivot.
		//
		// Example: (for more examples see below in the code)
		//
		// {3, 6, 9, 2, 5, 8, 1, 4, 7}
		// {3, 1, 4, 2, 5, 8, 6, 9, 7} // 5 is the pivot and is sorted
		// {1, 2, 4, 3} 5 {8, 6, 9, 7} // 2 is the pivot and is sorted
		// {1} 2 {3, 4} 5 {8, 6, 9, 7} // 3 is sorted
		// {1} 2 {3, 4} 5 {6, 7, 9, 8} // 7 is sorted
		// {1} 2 {3, 4} 5 {6} 7 {8, 9} // 8 is sorted
		
		quickSort(array, 0, array.length - 1);
		
		return array;
	}
	
	/**
	 * Quick-sorts a portion of an integer array.
	 * 
	 * @param array The array to sort.
	 * @param low   The start index of the portion in the sub-array.
	 * @param high  The end index of the portion in the sub-array.
	 * 
	 * @private
	 */
	private static void quickSort(int[] array, int low, int high)
	{
		if (low >= high) // base case: only one element in the array.
			return;
			
		// Pick a pivot using the Median-of-3 method (let pivot be the median out of the first
		// element, the middle element, and the last element).
		
		int mid = (low + high) / 2;
		// Example:
		// {7, 4, 1, 8, 5, 2, 9, 6, 3}
		// .L...........M...........H
		
		// sort the elements at low, mid, and high; the middle element will be the mo3
		// by the end of this process.
		if (array[mid] < array[low])
			swapElements(array, low, mid);
		if (array[high] < array[low])
			swapElements(array, low, high);
		if (array[high] < array[mid])
			swapElements(array, mid, high);
		// {3, 4, 1, 8, 5, 2, 9, 6, 7} (the elements at L, M, and H are now sorted relatively)
		// .L...........M...........H (the middle is now the mo3 and is chosen for pivot)
		
		// move the pivot out of the way for partitioning.
		swapElements(array, mid, high - 1);
		// {3, 4, 1, 8, 6, 2, 9, 5, 7} (5 is swapped with 6)
		// .L...........M...........H
		
		int pivot = array[high - 1];
		// {3, 4, 1, 8, 6, 2, 9, 5, 7}
		// ......................P... (P: pivot)
		
		// Partition the array by moving all elements smaller than the pivot to the left
		// and those greater than the pivot to the right.
		
		int i = low, j = high - 1;
		while (i < j)
		{
			while (array[++i] < pivot);
			while (array[--j] > pivot);
			
			if (i < j)
				swapElements(array, i, j);
		}
		// Example:
		// {3, 4, 1, 8, 6, 2, 9, 5, 7}
		// ....I..............J..P...
		// .......I...........J..P... (I keeps searching for an element on the wrong side of pivot)
		// ..........I........J..P... (bingo, 8 should go to the other side of pivot because 8>5;
		// now J searches for an element to swap with 8)
		// {3, 4, 1, 8, 6, 2, 9, 5, 7}
		// ..........I.....J.....P... (bingo, 2 should go to the other side because 2<5)
		// {3, 4, 1, 2, 6, 8, 9, 5, 7}
		// ..........I.....J.....P... (2 swaps with 8; they are now sorted relative to the pivot)
		// {3, 4, 1, 2, 6, 8, 9, 5, 7}
		// .............I..J.....P... (I and J keep going until they meet
		// .............IJ.......P... (I and J now meet; the search is over)
		
		// the position where they meet locates the final position of the pivot,
		// so we swap them.
		swapElements(array, i, high - 1);
		// {3, 4, 1, 2, 5, 8, 9, 6, 7}
		// .............I............ (now partitioning is done: all elements less than the
		// pivot(5) are to the left of pivot and vice versa)
		
		// Repeat this process to get the sub-arrays on both sides of the pivot partitioned.
		// The pivot is now brought to its correct position, so we ignore it for the rest of
		// the sorting process.
		
		quickSort(array, low, i - 1);
		quickSort(array, i + 1, high);
	}
	
	/**
	 * Swaps the elements at two specific indices in an integer array.
	 * 
	 * @param array    The array to swap elements in.
	 * @param index    The index to swap.
	 * @param tarIndex The target index to swap.
	 * 
	 * @private
	 */
	private static void swapElements(int[] array, int index, int tarIndex)
	{
		int temp = array[index];
		
		array[index] = array[tarIndex];
		array[tarIndex] = temp;
	}
	
	/** Application entry point. **/
	public static void main(String[] args)
	{
		// TIMING / BEHAVIORS
		//
		// .............Best.......Average....Worst
		// Selection....N^2........N^2........N^2
		// Bubble.......N^2........N^2........N^2
		// Insertion....N..........N^2........N^2
		// Shell........N..........N^(3/2)....N^2
		// Merge........NlogN......NlogN......NlogN
		// Quick........NlogN......NlogN......N^2
		
		int[] array = {3, 6, 9, 2, 5, 8, 1, 4, 7};
		
		System.out.println("TestCase1:\t" + Arrays.toString(array));
		System.out.println();
		
		System.out.println("Selection:\t" + Arrays.toString(selectionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Bubble:\t\t" + Arrays.toString(bubbleSort(Arrays.copyOf(array, array.length))));
		System.out.println("Insertion:\t" + Arrays.toString(insertionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Shell:\t\t" + Arrays.toString(shellSort(Arrays.copyOf(array, array.length))));
		System.out.println("Merge:\t\t" + Arrays.toString(mergeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Quick:\t\t" + Arrays.toString(quickSort(Arrays.copyOf(array, array.length))));
		System.out.println();
		
		array = new int[] {2, 23, 47, 5, 3, 19, 37, 13, 7, 29, 53, 31, 11, 43, 41, 17};
		
		System.out.println("TestCase2:\t" + Arrays.toString(array));
		System.out.println();
		
		System.out.println("Selection:\t" + Arrays.toString(selectionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Bubble:\t\t" + Arrays.toString(bubbleSort(Arrays.copyOf(array, array.length))));
		System.out.println("Insertion:\t" + Arrays.toString(insertionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Shell:\t\t" + Arrays.toString(shellSort(Arrays.copyOf(array, array.length))));
		System.out.println("Merge:\t\t" + Arrays.toString(mergeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Quick:\t\t" + Arrays.toString(quickSort(Arrays.copyOf(array, array.length))));
		System.out.println();
		
		array = new int[] {2, 3, 1, 3, 2, 1, 4, 4};
		
		System.out.println("TestCase3:\t" + Arrays.toString(array));
		System.out.println();
		
		System.out.println("Selection:\t" + Arrays.toString(selectionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Bubble:\t\t" + Arrays.toString(bubbleSort(Arrays.copyOf(array, array.length))));
		System.out.println("Insertion:\t" + Arrays.toString(insertionSort(Arrays.copyOf(array, array.length))));
		System.out.println("Shell:\t\t" + Arrays.toString(shellSort(Arrays.copyOf(array, array.length))));
		System.out.println("Merge:\t\t" + Arrays.toString(mergeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Quick:\t\t" + Arrays.toString(quickSort(Arrays.copyOf(array, array.length))));
		System.out.println();
		
		Random random = new Random();
		
		int max = 2_000, step = 200; // feel free to change these numbers
		int numLoops = 1_000; // my computer is slow so I did not choose very big numbers
		
		System.out.println("Average of " + numLoops);
		System.out.println();
		
		System.out.println("Sorted cases");
		System.out.println("N\t\tSelection\tBubble\t\tInsertion\tShell\t\tMerge\t\tQuick");
		
		for (int n = step; n <= max; n += step)
		{
			int[] temp = new int[n];
			for (int i = 0; i < n; i++)
				temp[i] = i; // Best case: sorted order
				
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				selectionSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long selectionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				bubbleSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long bubbleSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				insertionSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long insertionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				shellSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long shellSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				mergeSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long mergeSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				quickSort(temp);
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			long quickSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			System.out
				.println(n + "\t\t" + selectionSortAverage + "\t\t" + bubbleSortAverage + "\t\t" + insertionSortAverage
					+ "\t\t" + shellSortAverage + "\t\t" + mergeSortAverage + "\t\t" + quickSortAverage);
		}
		
		System.out.println();
		
		System.out.println("Random cases");
		System.out.println("N\t\tSelection\tBubble\t\tInsertion\tShell\t\tMerge\t\tQuick");
		
		for (int n = step; n <= max; n += step)
		{
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n); // Average case: random order
					
				selectionSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long selectionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
				
				bubbleSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long bubbleSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
				
				insertionSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long insertionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
				
				shellSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long shellSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
				
				mergeSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long mergeSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
				
				quickSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = random.nextInt(n);
			}
			
			long quickSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			System.out
				.println(n + "\t\t" + selectionSortAverage + "\t\t" + bubbleSortAverage + "\t\t" + insertionSortAverage
					+ "\t\t" + shellSortAverage + "\t\t" + mergeSortAverage + "\t\t" + quickSortAverage);
		}
		
		System.out.println();
		
		System.out.println("Reverse-sorted cases");
		System.out.println("N\t\tSelection\tBubble\t\tInsertion\tShell\t\tMerge\t\tQuick");
		
		for (int n = step; n <= max; n += step)
		{
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j; // Worst case: reversed order
					
				selectionSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long selectionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
				
				bubbleSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long bubbleSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
				
				insertionSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long insertionSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
				
				shellSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long shellSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
				
				mergeSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long mergeSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
				
				quickSort(temp);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int[] temp = new int[n];
				for (int j = 0; j < n; j++)
					temp[j] = n - j;
			}
			
			long quickSortAverage = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			System.out
				.println(n + "\t\t" + selectionSortAverage + "\t\t" + bubbleSortAverage + "\t\t" + insertionSortAverage
					+ "\t\t" + shellSortAverage + "\t\t" + mergeSortAverage + "\t\t" + quickSortAverage);
		}
	}
}
