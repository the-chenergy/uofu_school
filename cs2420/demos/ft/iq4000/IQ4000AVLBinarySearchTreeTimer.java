package demos.ft.iq4000;

import java.util.TreeSet;

import assign08.BinarySearchTree;

public class IQ4000AVLBinarySearchTreeTimer
{
	public static void main(String[] args)
	{
		int nStep = 1_000, nMax = 12_000, timesToLoop = 1_000;
		
		System.out.printf("(Average of %,d)%nN\tAdd\tContains\tMin\tMax%n", timesToLoop);
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			AVLBinarySearchTree<Integer> tree = new AVLBinarySearchTree<>();
			
			long start, end;
			
			// ADD
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					tree.add(j); // worst case: items added completely in order
				tree.clear();
			}
			
			end = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				//for (int j = 0; j < n - timesToLoop; j++)
					//tree.add(j);
				tree.clear();
			}
			
			double time = ((end - start) - (System.nanoTime() - end)) / (double)timesToLoop;
			
			System.out.printf("\t%,d\t%f", (long)timesToLoop, time / (n * Math.log(n) / Math.log(2)));
			
			System.out.println();
		}
	}
}
