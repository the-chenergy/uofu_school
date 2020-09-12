package demos.others;

import java.util.ArrayList;

import assign05.ArrayListSorter;

/**
 * Times a piece of code.
 * 
 * @author  Qianlang Chen
 * @version A 02/23/19
 */
public class TimerDemo
{
	/** ... **/
	private static ArrayList<Integer> list;
	
	/** ... **/
	public static void main(String[] args)
	{
		time("Merge\t\tQuick", 100, 5_000, 60_000, false, (i, n) -> list = ArrayListSorter.generatePermuted(n), null,
			() -> ArrayListSorter.mergesort(new ArrayList<>(list)), () -> new ArrayList<>(list),
			() -> ArrayListSorter.quicksort(new ArrayList<>(list)), () -> new ArrayList<>(list)
		);
	}
	
	/**
	 * Times and compares a piece of code using different settings.
	 * 
	 * @param header       The header of the table.
	 * @param numLoops     The number of times to loop a piece of code and take the average of.
	 * @param nStep        The increment of data size N.
	 * @param nMax         The maximum data size N.
	 * @param loopForSetup Indicates if the <code>setup()</code> function should be called once
	 *                     (<code>false</code>) or N (data size) times (<code>true</code>).
	 * @param setup        The function to run before timing for each data size N.
	 * @param cleanup      The function to run after timing for each data size N.
	 * @param args         The start method and the end method (must come in pairs).
	 */
	public static void time(String header, int numLoops, int nStep, int nMax, boolean loopForSetup,
		FunctorWithArgs setup, Functor cleanup, Functor ...args)
	{
		if (args.length == 0 || args.length % 2 > 0) // the args don't come in pairs
			throw new IllegalArgumentException("Arguments must come in pairs.");
		
		System.out.printf("(Average of %,d)\nN\t%s\n", numLoops, header);
		
		// Run the codes for each data size N.
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			// Setup.
			
			if (loopForSetup)
				for (int i = 0; i < n; i++)
					setup.f(i, n);
			else
				setup.f(-1, n);
			
			long start, end;
			double mean, mag;
			
			// Run and time each piece of code.
			
			System.out.printf("%,d", n);
			
			for (int a = 0; a < args.length; a += 2)
			{
				start = System.nanoTime();
				while (System.nanoTime() - start < 1_000_000_000);
				
				start = System.nanoTime();
				for (int i = 0; i < numLoops; i++)
					args[a].f();
				
				end = System.nanoTime();
				for (int i = 0; i < numLoops; i++)
					args[a + 1].f();
				
				mean = ((end - start) - (System.nanoTime() - end)) / (double)numLoops;
				
				if (Math.abs(mean) < 1_000)
					System.out.printf("\t%.3g", mean);
				else
					System.out.printf("\t%,.0f", mean);
			}
			
			System.out.println();
			
			if (cleanup != null)
				cleanup.f();
		}
		
		System.out.println("------------------------------------------------------------");
	}
	
	/** Represents a function with no arguments. **/
	public interface Functor
	{
		/** ... **/
		void f();
	}
	
	/** Represents a function with 2 integer arguments. **/
	public interface FunctorWithArgs
	{
		/** ... **/
		void f(int i, int n);
	}
}
