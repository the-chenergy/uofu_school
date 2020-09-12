package assign05;

import java.util.ArrayList;

/**
 * Times and experiments different pivot-picking strategies used in <code>ArrayListSorter</code>
 * class for Quicksort.
 * 
 * @author Qianlang Chen
 * @version H 02/14/19
 */
public class ArrayListSorterPivotTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int numLoops = 1_000;
		
		System.out.printf("Average of %d\n", numLoops);
		
		int n = 10_000;
		ArrayList<Integer> list = ArrayListSorter.generatePermuted(n);
		
		System.out.printf("Data size %d\n\n", n);
		
		System.out.printf("Strategy\tRuntime\n");
		
		for (int strategy = 0; strategy < 3 * 3; strategy++)
		{
			ArrayListSorter.getPivotStrategy = strategy / 3;
			
			long start = System.nanoTime(), end;
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				ArrayListSorter.quicksort(new ArrayList<Integer>(list));
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				new ArrayList<Integer>(list);
			
			System.out.printf("%d\t\t%d\n", strategy / 3, ((end - start) - (System.nanoTime() - end)) / numLoops);
		}
	}
}
