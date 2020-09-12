package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class RandomPhraseGenerator09
{
	private static Hashtable<String, ArrayList<LinkedList<Object>>> definitions = new Hashtable<>();
	
	private static Random random = new Random();
	
	public static void main(String[] args) throws FileNotFoundException
	{
		// args = new String[] {"src/comprehensive/poetic_sentence.g", "10"};
		
		Scanner s = new Scanner(new File(args[0]));
		readGrammar(s);
		
		printPhrases(Integer.parseInt(args[1]));
	}
	
	private static void readGrammar(Scanner scan)
	{
		boolean isReading = false;
		boolean isSigniture = false;
		ArrayList<LinkedList<Object>> currList = null;
		
		while (scan.hasNextLine())
		{
			String line = scan.nextLine();
			if (line.equals("{"))
			{
				isReading = true;
				isSigniture = true;
				continue;
			}
			if (!isReading)
			{
				continue;
			}
			if (line.equals("}"))
			{
				isReading = false;
				continue;
			}
			if (isSigniture)
			{
				currList = new ArrayList<>();
				definitions.put(line, currList);
				isSigniture = false;
				continue;
			}
			LinkedList<Object> production = new LinkedList<>();
			currList.add(production);
			
			int startIndex = 0;
			char[] charArray = line.toCharArray();
			for (int i = 0; i <= charArray.length; i++)
			{
				if (i == charArray.length || charArray[i] == '<')
				{
					if (i == startIndex) // no terminal in between
						continue;
					
					production.add(line.substring(startIndex, i));
					startIndex = i;
					
					continue;
				}
				
				if (charArray[i] == '>')
				{
					production.add(new NonTerminal(line.substring(startIndex, i + 1)));
					startIndex = i + 1;
				}
			}
		}
	}
	
	private static void printPhrases(int numPhrases)
	{
		NonTerminal start = new NonTerminal("<start>");
		for (int i = 0; i < numPhrases; i++)
		{
			//System.out.println(start);
			start.toString();
		}
	}
	
	private static class NonTerminal
	{
		public NonTerminal(String signature)
		{
			this.signature = signature;
		}
		
		public String signature;
		
		@Override
		public String toString()
		{
			ArrayList<LinkedList<Object>> productions = definitions.get(signature);
			LinkedList<Object> production = productions.get(random.nextInt(productions.size()));
			
			StringBuilder stringBuilder = new StringBuilder();
			for (Object obj : production)
				stringBuilder.append(obj);
			
			return stringBuilder.toString();
		}
	}
}
