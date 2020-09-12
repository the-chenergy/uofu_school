package assign09;

/**
 * Times and experiments how different threshold of when to expand the backing array in our
 * <code>HashTable</code> class affects the runtime performance of the class.
 * 
 * @author Qianlang Chen
 * @version T 03/26/19
 */
public class HashTableThresholdTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int nStep = 250, nMax = 2_000, thresholdStep = 1, thresholdMax = 10, timesToLoop = 100_000;
		
		System.out.printf("(Average of %,d)\n\n\t(Threshold size)\nN", timesToLoop);
		
		for (int i = thresholdStep; i <= thresholdMax; i += thresholdStep)
			System.out.printf("\t(%,d)", i);
		
		System.out.println();
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			for (int threshold = thresholdStep; threshold <= thresholdMax; threshold += thresholdStep)
			{
				// SETUP
				
				HashTable<String, Integer> table = new HashTable<>();
				for (int i = 0; i < n; i++)
					table.put("String " + i, i);
				table.setThreshold(threshold);
				
				// WARM-UP
				
				long startTime = System.nanoTime(), endTime;
				while (System.nanoTime() - startTime < 1_000_000_000);
				
				// TIME
				
				startTime = System.nanoTime();
				for (int i = 0; i < timesToLoop; i++)
					table.containsKey("String " + i);
				
				endTime = System.nanoTime();
				for (int i = 0; i < timesToLoop; i++);
				
				// OUTPUT
				
				System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			}
			
			System.out.println();
		}
	}
}
