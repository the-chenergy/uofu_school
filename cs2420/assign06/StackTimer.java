package assign06;

import demos.others.TimerDemo;

/**
 * Times and compares the methods of stack implemented by array and linked list.
 * 
 * @author Qianlang Chen
 * @version M 02/25/19
 */
public class StackTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int numLoops = 10_000, nStep = 10_000, nMax = 120_000;
		
		System.out.printf("(Average of %,d)\n", numLoops);
		System.out.println("N\tArray\tLinkedList");
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			ArrayStack<Integer> arr = new ArrayStack<>();
			LinkedListStack<Integer> ll = new LinkedListStack<>();
			
			for (int i = 0; i < n * 2; i++)
			{
				arr.push(i); // ensure that arr has enough capacity.
				ll.push(i);
			}
			
			for (int i = 0; i < n; i++)
			{
				arr.pop();
				ll.pop();
			}
			
			long start, end;
			
			// Array
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int x = arr.peek();
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
			}
			
			System.out.printf("\t%,.3g", ((end - start) - (System.nanoTime() - end)) / (double)numLoops);
			
			// LinkedList
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				int x = ll.peek();
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
			}
			
			System.out.printf("\t%,.3g", ((end - start) - (System.nanoTime() - end)) / (double)numLoops);
			
			System.out.println();
		}
	}
}
