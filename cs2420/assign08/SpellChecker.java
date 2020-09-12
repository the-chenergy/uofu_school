package assign08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a "dictionary" of strings using a binary search tree and offers methods for
 * spell-checking documents.
 * 
 * @author Erin Parker, Kevin Song, and Qianlang Chen
 * @version March 11, 2019
 */
public class SpellChecker
{
	/** The dictionary of words. **/
	private BinarySearchTree<String> dictionary;
	
	/** Creates a new <code>SpellChecker</code> instance with an empty dictionary of words. **/
	public SpellChecker()
	{
		dictionary = new BinarySearchTree<String>();
	}
	
	/**
	 * Creates a new <code>SpellChecker</code> instance with a dictionary read from a list of words.
	 * 
	 * @param words The list of Strings used to build the dictionary.
	 */
	public SpellChecker(List<String> words)
	{
		this();
		
		buildDictionary(words);
	}
	
	/**
	 * Creates a new <code>SpellChecker</code> instance with a dictionary read in from a file.
	 * 
	 * @param dictionaryFile The <code>File</code> that contains Strings used to build the
	 *                       dictionary.
	 */
	public SpellChecker(File dictionaryFile)
	{
		this();
		
		buildDictionary(readFromFile(dictionaryFile));
	}
	
	/**
	 * Add a word to the dictionary.
	 * 
	 * @param word The String to be added to the dictionary.
	 */
	public void addToDictionary(String word)
	{
		dictionary.add(word.toLowerCase());
	}
	
	/**
	 * Remove a word from the dictionary.
	 * 
	 * @param word The String to be removed from the dictionary.
	 */
	public void removeFromDictionary(String word)
	{
		dictionary.remove(word);
	}
	
	/**
	 * Spell-checks a document against the dictionary.
	 * 
	 * @param document_file The File that contains Strings to be looked up in the dictionary.
	 * @return A list of misspelled words.
	 */
	public List<String> spellCheck(File documentFile)
	{
		List<String> words = readFromFile(documentFile);
		ArrayList<String> misspelledWords = new ArrayList<String>();
		
		for (String word : words)
			if (!dictionary.contains(word))
				misspelledWords.add(word);
			
		return misspelledWords;
	}
	
	/**
	 * Fills in the dictionary with the input list of words.
	 * 
	 * @param words The list of Strings to be added to the dictionary
	 */
	private void buildDictionary(List<String> words)
	{
		dictionary.addAll(words);
	}
	
	/**
	 * Returns a list of the words contained in the specified file. (Note that symbols, digits, and
	 * spaces are ignored.)
	 * 
	 * @param file The <code>File</code> to be read.
	 * @return A List of the Strings in the input file.
	 */
	private List<String> readFromFile(File file)
	{
		ArrayList<String> words = new ArrayList<String>();
		
		try
		{
			// Java's Scanner class is a simple lexer for Strings and primitive types (see the Java API, if
			// you are unfamiliar).
			Scanner fileInput = new Scanner(file);
			
			// The scanner can be directed how to delimit (or divide) the input. By default, it uses
			// whitespace as the delimiter. The following statement specifies anything other than alphabetic
			// characters as a delimiter (so that punctuation and such will be ignored). The string argument
			// is a regular expression that specifies "anything but an alphabetic character". You need not
			// understand any of this for the assignment.
			fileInput.useDelimiter("\\s*[^a-zA-Z]\\s*");
			
			while (fileInput.hasNext())
			{
				String s = fileInput.next();
				
				if (!s.equals(""))
					words.add(s.toLowerCase());
			}
			
			fileInput.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File " + file + " cannot be found.");
		}
		
		return words;
	}
}
