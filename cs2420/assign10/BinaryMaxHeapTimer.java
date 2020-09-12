package assign10;

/**
 * Times and analyzes our <code>BinaryMaxHeap</code> class.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version April 8, 2019
 */
public class BinaryMaxHeapTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int nStep = 100_000, nMax = 2_000_000, timesToLoop = 10_000;
		
		System.out.printf("(Average of %,d)\n\n\tAverage\t\t\tWorst\nN\tadd()\tpeek()\textract()\tadd()\n", timesToLoop);
		
		for (int n = nStep, a = 0; n <= nMax; n += nStep * a, a = 1)
		{
			System.out.printf("%,d", n);
			
			BinaryMaxHeap<Integer> heap;
			
			long startTime, endTime;
			
			/* AVERAGE add() */
			
			heap = generateIntegerHeap(n);
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				heap.add(n / 2);
				heap.extractMax();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				heap.extractMax();
			
			System.out.printf("\t%,.3f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* AVERAGE peek() */
			
			heap = generateIntegerHeap(n);
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				heap.peek();
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++);
			
			System.out.printf("\t%1.3e", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* AVERAGE extractMax() */
			
			heap = generateIntegerHeap(n);
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				heap.extractMax();
			
			System.out.printf("\t%,.0f", (System.nanoTime() - startTime) / (double)timesToLoop);
			heap = generateIntegerHeap(n);
			
			/* WORST add() */
			
			heap = generateIntegerHeap(n);
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				heap.add(n);
				heap.extractMax();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
				heap.extractMax();
			
			System.out.printf("\t%,.1f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			System.out.println();
		}
	}
	
	/** Generates a <code>BinaryMaxHeap</code> with strictly integers 0 to <i>size</i> - 1. **/
	private static BinaryMaxHeap<Integer> generateIntegerHeap(int size)
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
		
		for (int i = 0; i < size; i++)
			heap.add(i);
		
		return heap;
	}
}
