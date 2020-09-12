package demos.algs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import assign06.ArrayStack;

/**
 * Reads in a script file and checks if all brackets are balanced.<br/>
 * <br/>
 * Text inside of these characters are ignored: " ' and /*<br/>
 * These brackets are checked: ( [ and {<br/>
 * 
 * @author  Qianlang Chen
 * @version A 02/23/19
 */
public class CheckBalancedSymbolDemo
{
	/**
	 * Checks if all brackets in the given text file are balanced.
	 * 
	 * @param  filename              The name of the text file to check.
	 * @return                       <code>0</code> if success, <code>-1</code> if the file contains
	 *                               unclosed comment/string/char, <code>-2</code> if the file
	 *                               contains unmatched brackets, or <code>-3</code> if the file
	 *                               contains unclosed brackets.
	 * @throws FileNotFoundException When the file does not exist.
	 */
	private static int check(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(filename));
		
		ArrayStack<Character> stack = new ArrayStack<>();
		char open = 0;
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			
			if (line == "") // empty line
				continue;
			
			for (int i = 0; i < line.length(); i++)
			{
				char curr = line.charAt(i), prev = i > 0 ? line.charAt(i - 1) : 0;
				
				if (prev == '\\') // escape char
					continue;
				
				if (curr == '"' || curr == '\'') // string/char
				{
					if (open == 0)
						open = curr;
					else if (open == curr)
						open = 0;
					
					continue;
				}
				
				if (prev == '*' && curr == '/') // close block comment
				{
					if (open == '*')
						open = 0;
					
					continue;
				}
				
				if (open != 0) // something (comment/string/char) is open
					continue;
				
				if (curr == '/' && prev == '/') // line comment
					break;
				
				if (prev == '/' && curr == '*') // open block comment
				{
					open = '*';
					
					continue;
				}
				
				if (curr == '(')
					stack.push(')');
				else if (curr == '[')
					stack.push(']');
				else if (curr == '{')
					stack.push('}');
				else if (curr == ')' || curr == ']' || curr == '}')
				{
					if (stack.pop().charValue() != curr)
					{
						scanner.close();
						
						return -2;
					}
				}
			}
		}
		
		scanner.close();
		
		if (open != 0)
			return -1;
		
		if (!stack.isEmpty())
			return -3;
		
		return 0;
	}
	
	/** ... **/
	public static void main(String[] args) throws FileNotFoundException
	{
		System.out.println(check("src/assign06/UnfinishedComment.txt"));
		System.out.println(check("src/assign06/Unmatched0.txt"));
		System.out.println(check("src/assign06/Unmatched1.txt"));
		System.out.println(check("src/assign06/Unmatched2.txt"));
		System.out.println(check("src/assign06/UnmatchedAtEOF.txt"));
		System.out.println(check("src/assign06/EmptyFile.txt"));
		System.out.println(check("src/assign06/SinglyLinkedList.java"));
		System.out.println(check("src/assign06/LinkedListStack.java"));
	}
}
