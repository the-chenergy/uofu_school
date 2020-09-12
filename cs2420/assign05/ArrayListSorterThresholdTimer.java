package assign05;

import java.util.ArrayList;

/**
 * Times and experiments different sizes of threshold used in <code>ArrayListSorter</code> class
 * for Merge Sort switching to Insertion Sort.
 * 
 * @author Qianlang Chen
 * @version H 02/14/19
 */
public class ArrayListSorterThresholdTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int numLoops = 1_000;
		
		System.out.printf("Average of %d\n", numLoops);
		
		int n = 16_384;
		ArrayList<Integer> list = ArrayListSorter.generatePermuted(n);
		
		System.out.printf("Data size %d\n\n", n);
		
		int tStep = 20, tMax = 160;
		
		System.out.printf("Threshold\tRuntime\n");
		
		for (int threshold = tStep; threshold <= tMax; threshold += tStep)
		{
			ArrayListSorter.THRESHOLD = threshold;
			
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				ArrayListSorter.mergesort(new ArrayList<Integer>(list));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				new ArrayList<Integer>(list);
			
			System.out.printf("%d\t\t%d\n", threshold, ((end - start) - (System.nanoTime() - end)) / numLoops);
		}
	}
}
