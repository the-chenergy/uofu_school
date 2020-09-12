package demos.others;

@SuppressWarnings("unused")
public class Test
{
	public static void main(String[] args)
	{
		// System.err.println("\t+...........");
		// for (int i = -32; i <= 32; i++)
		// {
		// if (i % 8 == 0)
		// System.out.println("\t+...........");
		//
		// printBinary(i);
		// }
		
		System.out.println("Value\tSign\tExp\tMantissa");
		for (int i = -32; i <= 128; i++)
			// printIEEE754(i);
			printIEEE754BaseTen(i);
		
		System.out.println("--------------------------");
		printIEEE754BaseTen(0.5);
		printIEEE754BaseTen(0.25);
		printIEEE754BaseTen(0.75);
		printIEEE754BaseTen(0.0625);
		printIEEE754BaseTen(-0.6875);
	}
	
	private static void printBinary(double n)
	{
		System.out.print(n + "\t");
		
		printBinary(Double.doubleToLongBits(n), 64);
		
		System.out.println();
	}
	
	private static void printBinary(long t, int places)
	{
		for (int i = places - 1; i >= 0; i--)
			System.out.print(t >> i & 1);
	}
	
	private static void printIEEE754(double n)
	{
		System.out.print(n + "\t");
		
		long t = Double.doubleToLongBits(n);
		
		System.out.print((t >> 63 & 1) + "\t");
		printBinary(t >> 52 & (long)(Math.pow(2, 11) - 1), 11);
		System.out.print("\t");
		printBinary(t & (long)(Math.pow(2, 52) - 1), 52);
		
		System.out.println();
	}
	
	private static void printIEEE754BaseTen(double n)
	{
		System.out.print(n + "\t");
		
		long t = Double.doubleToLongBits(n);
		
		System.out.print(((t >> 63 & 1) == 0 ? '+' : '-') + "\t");
		System.out.print((t >> 52 & (long)(Math.pow(2, 11) - 1)) - 1023 + "\t");
		
		t &= (long)(Math.pow(2, 52) - 1);
		double mantissa = 1;
		for (int i = 51; i >= 0; i--)
			mantissa += (t >> i & 1) * Math.pow(2, i - 52);
		System.out.println(mantissa);
	}
}
