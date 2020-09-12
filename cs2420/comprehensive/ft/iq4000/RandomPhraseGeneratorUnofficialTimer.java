package comprehensive.ft.iq4000;

import java.io.IOException;

/**
 * Times, experiments, and compares each versions of our <code>RandomPhraseGenerator</code>
 * class.
 * 
 * @author  Qianlang Chen
 * @version H 04/11/19
 */
public class RandomPhraseGeneratorUnofficialTimer
{
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException
	{
		final String url = "src/comprehensive/assignment_extension_request.g";
		
		int numLoops = 200, step = 100, max = 1_000;
		
		System.out.printf("(Average of %,d, microseconds)%n%nN\tCarbon\t4000IQ%n", numLoops);
		
		for (int n = step; n <= max; n += step)
		{
			System.out.printf("%,d", n);
			
			long start, end;
			
			/* OLD */
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				comprehensive.RandomPhraseGenerator12.main(new String[] {url, Integer.toString(n)});
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				String[] x = new String[] {url, Integer.toString(n)};
			}
			
			System.out.printf("\t%,.0f", ((end - start) - (System.nanoTime() - end)) / (double)numLoops * .001);
			
			/* 2ND */
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				comprehensive.ft.iq4000.RandomPhraseGenerator.generateRandomPhrase(url, Integer.toString(n));
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++);
			
			System.out.printf("\t%,.0f", ((end - start) - (System.nanoTime() - end)) / (double)numLoops * .001);
			
			System.out.println();
		}
	}
}
