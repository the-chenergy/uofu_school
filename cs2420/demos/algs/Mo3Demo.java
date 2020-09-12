package demos.algs;

/**
 * Finds the median of 3 numbers (A demo of how to quickly find the index of the median-of-3
 * during the partitioning process of a Quicksort).
 * 
 * @author Qianlang Chen
 * @version A 02/09/19
 */
public class Mo3Demo
{
	/**
	 * Returns the median of three integers.
	 * 
	 * @param a The first integer.
	 * @param b The second integer.
	 * @param c The third integer.
	 * @return The median out of the three integers.
	 */
	public static int mo3(int a, int b, int c)
	{
		if (a > c)
		{
			int temp = a;
			a = c;
			c = temp;
		}
		
		if (b < a)
			return a;
		
		if (c < b)
			return c;
		
		return b;
	}
	
	/** ... **/
	public static void main(String[] args)
	{
		for (int i = 1; i <= 3; i++)
			for (int j = 1; j <= 3; j++)
				for (int k = 1; k <= 3; k++)
					System.out.println(i + ", " + j + ", " + k + ": " + mo3(i, j, k));
	}
}
