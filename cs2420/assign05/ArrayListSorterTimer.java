package assign05;

import java.util.ArrayList;

/**
 * Times the <code>ArrayListSorter</code> class.
 * 
 * @author Qianlang Chen
 * @version H 02/14/19
 */
public class ArrayListSorterTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int numLoops = 1000;
		int nStep = 1_000, nMax = 20_000;
		
		System.out.println("Average of " + numLoops);
		System.out.println();
		System.out.println("N\tMerge Sort\tQuicksort");
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			ArrayList<Integer> list = ArrayListSorter.generateDescending(n);
		
			long start = System.nanoTime();
			
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			
			for (int i = 0; i < numLoops; i++)
				ArrayListSorter.mergesort(new ArrayList<Integer>(list));
			
			long end = System.nanoTime();
			
			for (int i = 0; i < numLoops; i++)
				new ArrayList<Integer>(list);
			
			double mergesortAverage = ((end - start) - (System.nanoTime() - end)) / (double)numLoops;
			
			start = System.nanoTime();
			
			for (int i = 0; i < numLoops; i++)
				ArrayListSorter.quicksort(new ArrayList<Integer>(list));
			
			end = System.nanoTime();
			
			for (int i = 0; i < numLoops; i++)
				new ArrayList<Integer>(list);
			
			double quicksortAverage = ((end - start) - (System.nanoTime() - end)) / (double)numLoops;
			
			System.out.printf(n + "\t%d\t%d\n", (long)mergesortAverage, (long)quicksortAverage);
			// System.out.printf(n + "\t\t%6.1e\t\t\t%6.1e\n", mergesortAverage, quicksortAverage);
		}
	}
}
