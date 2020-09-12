package assign08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Times the <code>BinarySearchTree</code> class.
 * 
 * @author Qianlang Chen
 * @version H 03/21/19
 */
public class BinarySearchTreeTimer
{
	public static void main(String[] args)
	{
		/* Variables */
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		TreeSet<Integer> javaTree = new TreeSet<>();
		ArrayList<Integer> toAdd = new ArrayList<>();
		
		/* Settings */
		
		int averageOf = 30_000, step = 100, max = 2_000;
		
		/* Header */
		
		System.out.printf("Average of %,d\n\nN\tSorted\tShuffled\n", averageOf);
		
		for (int n = step, re = 0; n <= max; n += step * re, re = 1)
		{
			printf(re, "%,.0f", (double)n);
			
			/* Setup */
			
			
			for (int i = 0; i < n; i++)
			{
				toAdd.add(i);
			}
			
			long startTime, endTime;
			
			/* Test 1 */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < averageOf; i++)
			{
				Collections.shuffle(toAdd);
				javaTree.addAll(toAdd);
				// Test 1 Setup
				for (int j = 0; j < n; j++)
					javaTree.contains(j);
				javaTree.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < averageOf; i++)
			{
				Collections.shuffle(toAdd);
				javaTree.addAll(toAdd);
				// Test 1 Clear up
				for (int j = 0; j < n; j++);
				javaTree.clear();
			}
			
			printf(re, "\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)averageOf);
			
			/* Test 1 */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < averageOf; i++)
			{
				Collections.shuffle(toAdd);
				tree.addAll(toAdd);
				// Test 1 Setup
				for (int j = 0; j < n; j++)
					tree.contains(j);
				tree.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < averageOf; i++)
			{
				Collections.shuffle(toAdd);
				tree.addAll(toAdd);
				// Test 1 Clear up
				for (int j = 0; j < n; j++);
				tree.clear();
			}
			
			printf(re, "\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)averageOf);
			
			// /* Test 2 */
			//
			// startTime = System.nanoTime();
			// while (System.nanoTime() - startTime < 1_000_000_000);
			//
			// startTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 2 Setup
			// Collections.shuffle(toAdd);
			// tree.addAll(toAdd);
			// tree.add(i);
			// tree.clear();
			// }
			//
			// endTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 2 Clear up
			// Collections.shuffle(toAdd);
			// tree.addAll(toAdd);
			// tree.clear();
			// }
			//
			// printf(re, "\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) /
			// (double)averageOf);
			
			/* Test 3 */
			
			// startTime = System.nanoTime();
			// while (System.nanoTime() - startTime < 1_000_000_000);
			//
			// startTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 3 Setup
			//
			// }
			//
			// endTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 3 Clear up
			//
			// }
			//
			// printf(re, "\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) /
			// (double)averageOf);
			
			/* Test 4 */
			
			// startTime = System.nanoTime();
			// while (System.nanoTime() - startTime < 1_000_000_000);
			//
			// startTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 4 Setup
			//
			// }
			//
			// endTime = System.nanoTime();
			// for (int i = 0; i < averageOf; i++)
			// {
			// // Test 4 Clear up
			//
			// }
			//
			// printf(re, "\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) /
			// (double)averageOf);
			
			/* Clear up */
			
			toAdd.clear();
			javaTree.clear();
			tree.clear();
			// for (int i = 0; i < n; i++)
			// {
			//
			// }
			
			printf(re, "\n");
		}
	}
	
	private static void printf(int re, String toPrint, Object ...args)
	{
		if (re == 0 && args.length > 0)
			args[0] = 0.;
		
		System.out.printf(toPrint, args);
	}
}
