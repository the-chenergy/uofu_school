package demos.others;

/**
 * Explores how <code>String.format()</code> works in Java.
 * 
 * @author Qianlang Chen
 * @version A 03/02/19
 */
public class StringFormatDemo
{
	/** ... **/
	public static void main(String[] args)
	{
		String model = "This %c# code is %.1f %s big and has %,d lines!";
		
		System.out.println(String.format(model, 'C', 8.75, "kilobytes", 1728));
		
		System.out.println("---- ---- ----");
		
		String s = String.format("%d%%", 100);
		
		System.out.println(s);
	}
}
