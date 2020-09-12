package lab07;

import java.util.Arrays;

/**
 * A better random number generator.
 * 
 * @author Qianlang Chen
 * @version F 02/22/19
 */
public class BetterRandomNumberGenerator implements RandomNumberGenerator
{
	/** The seed used to generate random integers. **/
	private long seed;
	
	/** Constant 1. **/
	private long const1;
	
	/** Constant 2 **/
	private long const2;
	
	@Override
	public int nextInt(int max)
	{
		int i = Integer.parseInt(Double.toString(Math.pow(seed, 0.3456789012)).replace(".", "").substring(2, 10));
		
		seed = Math.abs(seed + i) + 2;
		
		return i % max;
	}
	
	@Override
	public void setSeed(long seed)
	{
		this.seed = Math.abs(seed) + 2;
	}
	
	@Override
	public void setConstants(long const1, long const2)
	{
		this.const1 = const1;
		this.const2 = const2;
	}
	
	/* DANGER! */
	
	/** ... **/
	public static void main(String[] args)
	{
		int n = 26;
		long seed = 3141592653333L;
		int[] array = new int[n];
		
		BetterRandomNumberGenerator brng = new BetterRandomNumberGenerator();
		
		brng.setSeed(seed);
		
		for (int i = 0; i < n; i++)
			array[i] = brng.nextInt(10);
		
		System.out.println(Arrays.toString(array));
		
		brng.setSeed(seed);
		
		for (int i = 0; i < n; i++)
			array[i] = brng.nextInt(10);
		
		System.out.println(Arrays.toString(array));
	}
}
