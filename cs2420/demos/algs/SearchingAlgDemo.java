package demos.algs;

import java.util.Random;

/**
 * An exploration of how each searching algorithm works.
 * 
 * @author Qianlang Chen
 * @version 02/03/19
 */
public class SearchingAlgDemo
{
	/**
	 * Performs a sequential search on an integer array.
	 * 
	 * @param array  The integer array to search in.
	 * @param target The target to search for.
	 * @return The index of the target in the array, or <code>-1</code> if the target does not exist
	 *         in the array.
	 */
	public static int sequentialSearch(int[] array, int target)
	{
		// Sequential search goes over each element in the array exactly once from left to right
		// and returns the index whenever it meets it the target.
		//
		// This searching algorithm does not require the array to be pre-sorted.
		//
		// Example: Search for 17. ("^" represents the current searching position.)
		//
		// {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53}
		// .^2
		// ....^3
		// .......^5
		// ..........^7
		// .............^11
		// .................^13
		// .....................^17 (found it)
		
		for (int i = 0; i < array.length; i++)
			if (array[i] == target)
				return i;
			
		return -1;
	}
	
	/**
	 * Performs a binary search on an integer array.
	 * 
	 * @param array  The integer array to search in.
	 * @param target The target to search for.
	 * @return The index of the target in the array, or <code>-1</code> if the target does not exist
	 *         in the array.
	 */
	public static int binarySearch(int[] array, int target)
	{
		// Binary search looks at the element in the middle of the array and moves to search
		// in the left or right half of the array depending on the middle is too high or too low.
		//
		// This process is then repeated for each sub-array until the target is found or the
		// most promising sub-array (the closest to the target) is searched.
		//
		// A good example of people using this sort of thinking is guessing a number. When a
		// person is asked to guess the number another person having in mind (assuming ranged
		// between 0 and 100), the guesser will often choose to guess 50. Depending on knowing
		// if the guess is too small or too big, the guesser will move on to guessing 25 or
		// 75, and the process will repeat. Rather than guessing the number from 0 to 100 in
		// numerical order, this method is obviously more efficient, because we can get clues
		// of what to guess next as we guess.
		//
		// Because of this feature, this searching algorithm requires the array to be pre-sorted,
		// or we would have no clue where to move on during the search.
		//
		// Example: Search for 17.
		//
		// {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53}
		// .........................^19 (too big, therefore move and search to the left)
		// ..........^7 (too small, move right)
		// .................^13 (too small, move right)
		// .....................^17 (found it)
		
		// The lower and upper bounds of the sub-array to search for.
		int low = 0, high = array.length - 1;
		
		while (low <= high)
		{
			// The middle of the sub-array. The search will continue to the left or right
			// depending or the relationship between the target and the median.
			int i = (high + low) / 2;
			
			if (array[i] == target)
				return i;
			
			// Change the low or high bound accordingly to move on to another median.
			if (array[i] < target)
				low = i + 1;
			else
				high = i - 1;
				
			// The search ends when the low-bound and high-bound meet, meaning that all of
			// the promising sub-arrays are reached and searched.
		}
		
		return -1;
	}
	
	/** Application entry point. **/
	public static void main(String[] args)
	{
		int[] array = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
		// ............0..1..2..3...4...5...6...7...8...9..10..11..12..13..14..15
		
		System.out.println(sequentialSearch(array, 13)); // 5
		System.out.println(sequentialSearch(array, 24)); // -1
		System.out.println();
		System.out.println(binarySearch(array, 13)); // 5
		System.out.println(binarySearch(array, 24)); // -1
		System.out.println();
		
		// TIMING / BEHAVIORS
		//
		// ..............Pre-sorting Required....Worst case
		// Sequential....No......................N
		// Binary........Yes.....................logN
		
		Random random = new Random();
		
		int max = 2_400_000, step = 100_000;
		int numLoops = 1_000;
		
		System.out.println("Average of " + numLoops);
		System.out.println();
		System.out.println("N\t\tSequential\tBinary");
		
		for (int n = step; n <= max; n += step)
		{
			int[] temp = new int[n];
			for (int i = 0; i < n; i++)
				temp[i] = i;
			
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				sequentialSearch(temp, random.nextInt(n));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				random.nextInt(n);
			
			double sequentialSearchAverage = ((end - start) - (System.nanoTime() - end)) / (double)numLoops;
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				binarySearch(temp, random.nextInt(n));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				random.nextInt(n);
			
			double binarySearchAverage = ((end - start) - (System.nanoTime() - end)) / (double)numLoops;
			
			System.out.println(n + "\t\t" + sequentialSearchAverage + "\t" + binarySearchAverage);
		}
	}
}
