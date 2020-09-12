package assign06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols in an input
 * file are correctly matched.
 * 
 * @author Erin Parker, Brandon Walton, and Qianlang Chen
 * @version 2/27/19
 */
public class BalancedSymbolChecker
{
	/**
	 * Generates a message indicating whether the input file has unmatched symbols. (Use the helper
	 * methods below for constructing messages.)
	 * 
	 * @param filename - name of the input file to check
	 * @return a message indicating whether the input file has unmatched symbols
	 * @throws FileNotFoundException if the file does not exist
	 */
	public static String checkFile(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(filename));
		
		ArrayStack<Character> stack = new ArrayStack<>();
		int lineNum = 1;
		char open = 0; // records which symbol is opened (", ' and * for string, char and comment)
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			
			char curr = 0, prev = 0;
			
			for (int i = 0; i < line.length(); i++, prev = curr)
			{
				curr = line.charAt(i);
				
				if (curr == '\\' && (open == '"' || open == '\'')) // escape char (\)
				{
					i++;
					
					continue;
				}
				
				if (curr == '"' || curr == '\'') // string/char (" or ')
				{
					if (open == 0)
						open = curr; // open " or '
					else if (open == curr)
						open = 0; // close " or '
						
					continue;
				}
				
				if (prev == '*' && curr == '/' && open == '*') // close block comment (*/)
				{
					open = 0;
					
					continue;
				}
				
				if (open != 0) // something (comment/string/char) is open
					continue;
				
				if (prev == '/' && curr == '/') // line comment (//)
					break;
				
				if (prev == '/' && curr == '*') // open block comment (/*)
				{
					open = '*';
					
					continue;
				}
				
				if (curr == ')' || curr == ']' || curr == '}') // close symbols
				{
					if (stack.isEmpty()) // close before opening
					{
						scanner.close();
						return unmatchedSymbol(lineNum, i + 1, curr, ' ');
					}
					
					char expected = stack.pop();
					
					if (curr != expected) // unmatched symbol
					{
						scanner.close();
						return unmatchedSymbol(lineNum, i + 1, curr, expected);
					}
					
					continue;
				}
				
				if (curr == '(') // open symbols
					stack.push(')');
				else if (curr == '[')
					stack.push(']');
				else if (curr == '{')
					stack.push('}');
			}
			
			lineNum++;
		}
		
		scanner.close();
		
		if (open != 0)
			return unfinishedComment();
		
		if (!stack.isEmpty())
			return unmatchedSymbolAtEOF(stack.peek());
		
		return allSymbolsMatch();
	}
	
	/**
	 * Use this error message in the case of an unmatched symbol.
	 * 
	 * @param lineNumber     - the line number of the input file where the matching symbol was
	 *                       expected
	 * @param colNumber      - the column number of the input file where the matching symbol was
	 *                       expected
	 * @param symbolRead     - the symbol read that did not match
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber, char symbolRead, char symbolExpected)
	{
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column " + colNumber + ". Expected "
			+ symbolExpected + ", but read " + symbolRead + " instead.";
	}
	
	/**
	 * Use this error message in the case of an unmatched symbol at the end of the file.
	 * 
	 * @param symbolExpected - the matching symbol expected
	 * @return the error message
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected)
	{
		return "ERROR: Unmatched symbol at the end of file. Expected " + symbolExpected + ".";
	}
	
	/**
	 * Use this error message in the case of an unfinished comment (i.e., a file that ends with an
	 * open /* comment).
	 * 
	 * @return the error message
	 */
	private static String unfinishedComment()
	{
		return "ERROR: File ended before closing comment.";
	}
	
	/**
	 * Use this message when no unmatched symbol errors are found in the entire file.
	 * 
	 * @return the success message
	 */
	private static String allSymbolsMatch()
	{
		return "No errors found. All symbols match.";
	}
}
