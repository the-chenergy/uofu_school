package comprehensive.ft.iq4000;

import java.io.IOException;

/**
 * @author Eric O'Donoghue & Daniel Reyna
 */
public class RandomPhraseGenerator
{
	/**
	 * Generates N random phrases from a given grammar file.
	 * 
	 * @param  args[0]     is the file name of the grammar to construct the phrase from, args[1] is
	 *                     desired number of phrases to generate.
	 * @throws IOException if the file does not exist.
	 */
	public static void main(String[] args) throws IOException
	{
		PhraseHashTable phraseTable = new PhraseHashTable();
		phraseTable.constructPhraseTable(args[0]);
		for (int i = 0; i < Integer.parseInt(args[1]); i++)
			System.out.println(phraseTable.buildPhrase());
	}
	
	/**
	 * Used in order to time the generation of random phrases.
	 */
	public static void generateRandomPhrase(String file, String phrases) throws IOException
	{
		
		PhraseHashTable phraseTable = new PhraseHashTable();
		phraseTable.constructPhraseTable(file);
		for (int i = 0; i < Integer.parseInt(phrases); i++)
			phraseTable.buildPhrase();
	}
}
