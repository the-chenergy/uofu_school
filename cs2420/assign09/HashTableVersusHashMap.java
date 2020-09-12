package assign09;

import java.util.HashMap;

/**
 * Times and compares how our <code>HashTable</code> class performs with Java's native
 * <code>HashMap</code> class does.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version T 03/26/19
 */
public class HashTableVersusHashMap
{
	/** ... **/
	public static void main(String[] args)
	{
		int nStep = 100, nMax = 2_000, timesToLoop = 1_000;
		
		System.out.printf("(Average of %,d)\n\nN\tHashTable\tHashMap\n", timesToLoop);
		
		HashTable<String, Integer> table = new HashTable<>();
		HashMap<String, Integer> map = new HashMap<>();
		
		// ensure that the tables do not need to expand during timing
		for (int i = 0; i < nMax; i++)
		{
			table.put("", 0);
			map.put("", 0);
		}
		table.clear();
		map.clear();
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			long startTime, endTime;
			
			/* HashTable */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					table.put("String " + j, 0);
				
				table.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					("String " + j).hashCode();
				
				table.clear();
			}
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* HashMap */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					map.put("String " + j, 0);
				
				map.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					("String " + j).hashCode();
				
				map.clear();
			}
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			System.out.println();
		}
	}
}
