package comprehensive.ft.iq4000;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PhraseHashTable
{
	/** Represents a PhraseHashTable */
	private HashMap<String, ArrayList<Text[]>> phraseMap;
	
	/** Stores the phrase as its being generated */
	private StringBuilder phrase;
	
	public PhraseHashTable()
	{
		phraseMap = new HashMap<>();
		phrase = new StringBuilder();
	}
	
	/**
	 * Driver method for the recursive function that generates a random phrase.
	 * 
	 * @return the randomly generated phrase.
	 */
	public String buildPhrase()
	{
		new NonTerminal("<start>").getText(phrase);
		String result = phrase.toString();
		phrase = new StringBuilder();
		return result;
	}
	
	/**
	 * Recursive method for randomly generating a phrase.
	 * 
	 * @param arrList ArrayList to iterate through and either append to phrase or call the recursive method on.
	 */
	// private void buildPhraseRecur(Text[] arr)
	// {
	// for (Text section : arr)
	// {
	// if (section.charAt(0) != '<')
	// phrase.append(section);
	// else
	// buildPhraseRecur(getRandom(section));
	// }
	// }
	
	/**
	 * Reads through the given grammar file and constructs a PhraseHashTable.
	 * 
	 * @param  filename    grammar file
	 * @throws IOException if the grammar file does not exist
	 */
	public void constructPhraseTable(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String curr = "";
		boolean inBracket = false;
		
		while (true)
		{
			String nonTerminal = "";
			ArrayList<Text[]> arrList = new ArrayList<>();
			curr = reader.readLine();
			if (curr == null)
				break;
			
			if (curr.equals("{"))
			{
				inBracket = true;
				nonTerminal = reader.readLine();
			}
			while (inBracket)
			{
				curr = reader.readLine();
				if (curr.equals("}"))
				{
					phraseMap.put(nonTerminal, arrList);
					inBracket = false;
				}
				else
				{
					arrList.add(splitProductionRule(curr));
				}
			}
		}
		
		reader.close();
	}
	
	/**
	 * Gets a random entry from the ArrayList mapped to the given key
	 * 
	 * @param  key key to retrieve value from in the HashMap
	 * @return     the random ArrayList from the ArrayList mapped to the given key
	 */
	public Text[] getRandom(String key)
	{
		Random rng = new Random();
		ArrayList<Text[]> resultArr = phraseMap.get(key);
		Text[] result = resultArr.get(rng.nextInt(resultArr.size()));
		return result;
	}
	
	/**
	 * splits a production rule into terminals and non-terminals and inserts them into an ArrayList
	 * 
	 * @param  s production rule to be split
	 * @return   an ArrayList containing the production rule split into terminals and non-terminals
	 */
	public Text[] splitProductionRule(String s)
	{
		ArrayList<Text> result = new ArrayList<>();
		
		int i, j;
		for (i = 0, j = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == '<')
			{
				if (j != i)
					result.add(new Terminal(s.substring(j, i)));
				j = i;
			}
			else if (s.charAt(i) == '>')
			{
				result.add(new NonTerminal(s.substring(j, i + 1)));
				j = i + 1;
			}
		}
		if (j < i)
		{
			result.add(new Terminal(s.substring(j)));
		}
		
		Text[] arr = new Text[result.size()];
		for (i = 0; i < result.size(); i++)
			arr[i] = result.get(i);
		
		return arr;
	}
	
	/**
	 * Represents a piece of text in a phrase/sentence, which may be either a "non-terminal" or "terminal".
	 **/
	private interface Text
	{
		/**
		 * Generates a string this text can represent and puts it at the end of a given <code>StringBuilder</code>.
		 * 
		 * @param buffer The buffer to put this text in.
		 */
		public void getText(StringBuilder buffer);
	}
	
	/** Represents a "terminal" in a phrase/sentence. **/
	private class Terminal implements Text
	{
		/**
		 * Creates a new "terminal" with the given text.
		 * 
		 * @param text The content of this "terminal".
		 */
		public Terminal(String text)
		{
			this.text = text;
		}
		
		/** The content of this "terminal". **/
		public String text;
		
		@Override
		public void getText(StringBuilder buffer)
		{
			buffer.append(text); // just append the text in this "terminal" to the end of the buffer
		}
	}
	
	/** Represents a "non-terminal" in a phrase/sentence. **/
	private class NonTerminal implements Text
	{
		/**
		 * Creates a new "non-terminal" with the given signature.
		 * 
		 * @param signature The signature (name) of this "non-terminal", such as <i>"&lt;object&gt;"</i>.
		 */
		public NonTerminal(String signature)
		{
			this.signature = signature;
		}
		
		/** The signature of this "non-terminal". **/
		public String signature;
		
		@Override
		public void getText(StringBuilder buffer)
		{
			ArrayList<Text[]> rules = phraseMap.get(signature); // retrieve the production rules for this specific
																// "non-terminal"
			
			for (Text text : rules.get((int)(Math.random() * rules.size())))
				text.getText(buffer); // this may be called recursively since "text" may also be a "non-terminal"
		}
	}
}
