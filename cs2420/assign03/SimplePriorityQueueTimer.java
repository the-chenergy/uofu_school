package assign03;

import java.util.ArrayList;
import java.util.Random;

/**
 * Tests and times <code>SimplePriorityQueue.findMin()</code> and
 * <code>SimplePriorityQueue.insert()</code> methods for different sized queues.
 * 
 * @author Qianlang Chen
 * @version January 29, 2019
 */
public class SimplePriorityQueueTimer
{
	/** Application entry point. **/
	public static void main(String[] args)
	{
		int numLoops = 25;
		
		int maxSize = 2_000_000, stepSize = 100_000;
		
		// Print the table header.
		System.out.println("Average of " + numLoops);
		System.out.println();
		System.out.println("N\t\tfindMin()\tinsert()");
		
		for (int n = stepSize; n <= maxSize; n += stepSize)
		{
			SimplePriorityQueue<Integer> queue = new SimplePriorityQueue<Integer>();
			queue.insertAll(generateIntList(n));
			
			long startTime, midTime, endTime;
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			// Time findMin()
			
			startTime = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
				queue.findMin();
			
			midTime = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			endTime = System.nanoTime();
			
			double findMinTime = ((midTime - startTime) - (endTime - midTime)) / (double)numLoops;
			
			// Time insert()
			
			startTime = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				queue.insert(2_000_000 - n);
				
				queue.deleteMin(); // undo the change to the size of the queue
			}
			
			midTime = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int x = 2_000_000 - n;
				
				queue.deleteMin();
			}
			
			endTime = System.nanoTime();
			
			double insertTime = ((midTime - startTime) - (endTime - midTime)) / (double)numLoops;
			
			// Print out the results
			
			System.out.println(n + "\t\t" + findMinTime + "\t\t" + insertTime);
		}
	}
	
	/**
	 * Generate a list of random integers ranged between 0 and the size.
	 * 
	 * @param size The size of the list of integers to generate.
	 * @return A list of random integers.
	 */
	private static ArrayList<Integer> generateIntList(int size)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++)
			list.add(generateInteger(size));
		
		return list;
	}
	
	/**
	 * Generate a random integer ranged between 0 and <code>max</code>.
	 * 
	 * @param max The maximum possible value of the integer.
	 * @return A random integer.
	 */
	private static Integer generateInteger(int max)
	{
		return new Random().nextInt(max);
	}
}
