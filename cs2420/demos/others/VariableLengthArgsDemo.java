package demos.others;

/**
 * Explores what happens when an array is passed as an argument to a function that accepts a
 * variable-length argument list in Java.
 * 
 * @author  Qianlang Chen
 * @version M 02/25/19
 */
public class VariableLengthArgsDemo
{
	/** ... **/
	public static void main(String[] args)
	{
		f("Hello", "Hi", "Goodbye", "Bye");
		
		System.out.println("------------------------");
		
		String[] strings = {"123", "456", "789"};
		
		f(strings); // AYY IT WORKS!!
	}
	
	/** Prints out the arguments, one per line. **/
	private static void f(String ...args)
	{
		System.out.println(args.length);
		
		for (String string : args)
			System.out.println(string);
	}
}
