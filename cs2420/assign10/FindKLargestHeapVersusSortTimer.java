package assign10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Times and analyzes our <code>FindKLargest</code> class.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version April 9, 2019
 */
public class FindKLargestHeapVersusSortTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int nStep = 500, nMax = 12_000, timesToLoop = 1_000;
		
		System.out.printf("(Average of %,d)\n\n\tHeap\tSort\n", timesToLoop);
		
		for (int n = nStep, a = 0; n <= nMax; n += nStep * a, a = 1)
		{
			System.out.printf("%,d", n);
			
			int kToFind = n;
			
			long startTime, endTime;
			
			/* findKLargestHeap() */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				FindKLargest.findKLargestHeap(generateIntegerList(n), kToFind);
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				generateIntegerList(n);
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* findKLargestSort() */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				FindKLargest.findKLargestSort(generateIntegerList(n), kToFind);
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				generateIntegerList(n);
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			System.out.println();
		}
	}
	
	/** Generates a list with random integers ranged 0 to <i>size</i> - 1. **/
	private static List<Integer> generateIntegerList(int size)
	{
		List<Integer> list = new ArrayList<>();
		Random random = new Random();
		
		for (int i = 0; i < size; i++)
			list.add(random.nextInt(size));
		
		return list;
	}
}
