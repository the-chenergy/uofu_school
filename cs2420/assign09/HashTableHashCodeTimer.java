package assign09;

/**
 * Times, experiments, and compares how each of the three versions of <code>hashCode()</code>
 * method of the student class would perform.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version M 03/25/19
 */
public class HashTableHashCodeTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		int nStep = 100, nMax = 2_000, timesToLoop = 1_000;
		
		System.out.printf("(Average of %,d)\n\nN\tBad\tMedium\tGood\n", timesToLoop);
		
		StudentBadHash[] bad = new StudentBadHash[nMax];
		StudentMediumHash[] medium = new StudentMediumHash[nMax];
		StudentGoodHash[] good = new StudentGoodHash[nMax];
		
		char[] letters = ",.1234567890pyfgcrlaoeiudhtnsqjkxbmwvzPYFGCRLAOEIUDHTNSQJKXBMWVZ".toCharArray();
		for (int i = 0; i < nMax; i++)
		{
			StringBuilder firstName = new StringBuilder();
			for (int j = 0; j < 10; j++)
				firstName.append(letters[(int)(Math.random() * 64)]);
			
			bad[i] = new StudentBadHash(1170000 + i, firstName.toString(), "Smith");
			medium[i] = new StudentMediumHash(1170000 + i, firstName.toString(), "Smith");
			good[i] = new StudentGoodHash(1170000 + i, firstName.toString(), "Smith");
		}
		
		HashTable<Object, Integer> table = new HashTable<>();
		
		// ensure that the table does not need to expand during timing
		for (int i = 0; i < nMax; i++)
			table.put(0, 0);
		table.clear();
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			long startTime, endTime;
			
			/* Bad */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					table.put(bad[j], 0);
				
				table.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					bad[j].hashCode();
				
				table.clear();
			}
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* Medium */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					table.put(medium[j], 0);
				
				table.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					medium.hashCode();
				
				table.clear();
			}
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			/* Good */
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					table.put(good[j], 0);
				
				table.clear();
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++)
			{
				for (int j = 0; j < n; j++)
					good[j].hashCode();
				
				table.clear();
			}
			
			System.out.printf("\t%,.0f", ((endTime - startTime) - (System.nanoTime() - endTime)) / (double)timesToLoop);
			
			System.out.println();
		}
	}
}
