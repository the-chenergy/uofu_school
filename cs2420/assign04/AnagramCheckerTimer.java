package assign04;

import java.util.Random;

/**
 * Tests and times <code>AnagramChecker.areAnagrams()</code> and
 * <code>AnagramChecker.getLargestAnagramGroup()</code> methods for different sized queues.
 * 
 * @author Qianlang Chen
 * @version February 7, 2019
 */
public class AnagramCheckerTimer
{
	/** Application entry point. **/
	public static void main(String[] args)
	{
		int nStep, nMax, numLoops;
		
		// Time areAnagrams()
		
		nStep = 500;
		nMax = 8_000;
		numLoops = 800;
		
		System.out.println("Average of " + numLoops);
		System.out.println();
		System.out.println("N\t\tareAnagrams()");
		
		for (int n = nStep; false && n <= nMax; n += nStep)
		{
			long start = System.nanoTime();
			
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				boolean b = AnagramChecker.areAnagrams(generateString(n), generateString(n));
			}
			
			long end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				String s = generateString(n);
				String t = generateString(n);
			}
			
			long average = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			System.out.println(n + "\t\t" + average);
		}
		
		// Time getLAG()
		
		nStep = 100;
		nMax = 3_000;
		numLoops = 500;
		
		System.out.println();
		System.out.println("Average of " + numLoops);
		System.out.println();
		System.out.println("N\t\tgetLAG()");
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			long start = System.nanoTime();
			
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				String[] s = AnagramChecker.getLargestAnagramGroup(generateStrings(n));
			}
			
			long end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				String[] s = generateStrings(n);
			}
			
			long average = ((end - start) - (System.nanoTime() - end)) / numLoops;
			
			System.out.println(n + "\t\t" + average);
		}
	}
	
	/**
	 * The String containing characters used to generate random Strings.
	 */
	private static final String chars = "0123456789?!PYFGCRLAOEIUDHTNS:QJKXBMWVZ,.pyfgcrlaoeiudhtns;qjkxbmwvz";
	
	/**
	 * The number of characters in <code>chars</code>.
	 */
	private static final int numChars = chars.length();
	
	/**
	 * Generates a String containing random characters.
	 * 
	 * @param length The length of the String to generate.
	 * @return A random String.
	 */
	private static String generateString(int length)
	{
		String string = "";
		Random random = new Random();
		
		while (length-- > 0)
			string += chars.charAt(random.nextInt(numChars));
		
		return string;
	}
	
	/**
	 * Generates an array containing Strings of 12 random characters.
	 * 
	 * @param length The length of the String array.
	 * @return A random String array.
	 */
	private static String[] generateStrings(int length)
	{
		String[] strings = new String[length];
		
		while (--length >= 0)
			strings[length] = generateString(12);
		
		return strings;
	}
}
