package comprehensive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Generates and displays random phrases/sentences from a set of grammar rules.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version M 04/15/19
 */
public class RandomPhraseGenerator
{
	/**
	 * Application entry point.<br/>
	 * <br/>
	 * Two arguments must be passed: the first argument is the URL to the grammar definition text-file, and the second argument must be a
	 * positive integer indicating the number of random phrases/sentences to generate and display from the given grammar rules.
	 * 
	 * @param args The list with the 2 arguments to pass into this program.
	 * @throws IOException If an I/O error occurs during reading the grammar file or during writing the generated phrase(s) into console.
	 */
	public static void main(String[] args) throws IOException
	{
		//////////// TESTING AREA ////////////
		
		// args = new String[] {"src/comprehensive/poetic_sentence.g", "8"};
		// args = new String[] {"src/comprehensive/mathematical_expression.g", "8"};
		// args = new String[] {"src/comprehensive/assignment_extension_request.g", "8"};
		// args = new String[] {"src/comprehensive/stupid.g", "1"};
		
		//////////////////////////////////////
		
		readGrammar(args[0]);
		printPhrases(Integer.parseInt(args[1]));
	}
	
	/**
	 * The dictionary used to store the set of grammar rules read in for each "non-terminal".<br/>
	 * <br/>
	 * Key: The name of the "non-terminal" to define, such as <i>"&lt;object&gt;"</i>.<br/>
	 * Value: The corresponding "non-terminal" instance which contains its own grammar rules.
	 */
	private static HashMap<String, NonTerminal> nonTerminals;
	
	/** The random number generator used to choose a production rule for a "non-terminal" when there are multiple. **/
	private static Random random = new Random();
	
	/**
	 * Reads the grammar rules from a text file.
	 * 
	 * @param url The name of the text file containing the grammar rules to read in.
	 * @throws IOException If an I/O error occurs during the reading process.
	 */
	private static void readGrammar(String url) throws IOException
	{
		nonTerminals = new HashMap<>(); // initialize it here to avoid unnecessary expansions when "main" method is run multiple times
		
		BufferedReader in = new BufferedReader(new FileReader(url));
		
		boolean isInComments = true; // whether the current line is a comment and should be ignored (i.e., outside of any blocks)
		ArrayList<ArrayList<Text>> ruleList = null; // a reference to the rule-list of the current "non-terminal" being defined
		
		while (true)
		{
			String line = in.readLine(); // read the grammar file line-by-line
			if (line == null)
				break;
			
			// Filter out comments and prepare for the "non-terminal" being defined.
			
			if (isInComments)
			{
				if (line.contentEquals("{")) // definitions start / comments end
				{
					isInComments = false;
					
					ruleList = getNonTerminal(in.readLine()).rules; // get a reference to the rule-list of the current "non-terminal"
					// since the name is always on the very next line of the opening brace ('{'), we can safely do this
				}
				
				continue;
			}
			
			if (line.contentEquals("}")) // definitions end / comments start
			{
				isInComments = true;
				
				continue;
			}
			
			// Each line at this point is a production rule of a "non-terminal".
			
			ArrayList<Text> rule = new ArrayList<>();
			ruleList.add(rule); // create a rule in the rule-list of the "non-terminal" currently being defined
			
			// Format the production rule into a list of Texts ("terminal" and "non-terminal" objects).
			
			int start = 0;
			for (int i = line.indexOf('<'); i != -1; i = line.indexOf('<', start)) // keep searching for the next opening angle-bracket ('<')
			{
				if (i > start) // only add a "terminal" when there is one
					rule.add(new Terminal(line.substring(start, i)));
					
				// NOTE that our solution treats multiple words -- including spaces and punctuation marks in-between if there are any --
				// as a single "terminal"; for example, the production rule "The <noun> <verb> tonight." will be treated and formatted as:
				// { Terminal("The "), Non-terminal("<noun>"), Terminal(" "), Non-terminal("<verb>"), Terminal(" tonight.") }
				
				start = line.indexOf('>', i + 1) + 1;
				rule.add(getNonTerminal(line.substring(i, start)));
			}
			
			if (start < line.length()) // there is a "terminal" right before the line ends
				rule.add(new Terminal(line.substring(start)));
		}
		
		in.close();
	}
	
	/**
	 * Prints out a number of random phrases generated by the given grammar rules.
	 * 
	 * @param n The number of random phrases to generate and display.
	 * @throws IOException If an I/O error occurs during the writing process.
	 */
	private static void printPhrases(int n) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 100 * n); // avoid using sysout altogether
		
		NonTerminal start = getNonTerminal("<start>");
		String newLine = System.lineSeparator(); // more compatible to different os than using just '\n'
		
		while (n-- > 0)
		{
			start.getText(out);
			out.write(newLine); // new-lines separate each random phrase/sentence generated
		}
		
		out.flush(); // leave the sysout stream unclosed in case it is used elsewhere
	}
	
	/**
	 * Returns a reference to the instance of a "non-terminal" with a particular name.
	 * 
	 * @param name The name of the "non-terminal" to get.
	 * @return A reference to the "non-terminal" with the specified name.
	 */
	private static NonTerminal getNonTerminal(String name)
	{
		NonTerminal temp = nonTerminals.get(name);
		
		if (temp == null) // if the dictionary does not yet contain the one being queried
		{
			temp = new NonTerminal();
			nonTerminals.put(name, temp);
		}
		
		return temp;
	}
	
	/** Represents a piece of text in a phrase/sentence, which may be either a "non-terminal" or "terminal". **/
	private static interface Text
	{
		/**
		 * Generates a string this text can represent and writes it into a given output buffer.
		 * 
		 * @param out The output buffer to write the generated string in.
		 * @throws IOException If an I/O error occurs during the writing process.
		 */
		public void getText(BufferedWriter out) throws IOException;
	}
	
	/** Represents a "terminal" in a phrase/sentence. **/
	private static class Terminal implements Text
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
		public void getText(BufferedWriter out) throws IOException
		{
			out.write(text); // just append the text in this "terminal" to the end of the buffer
		}
	}
	
	/** Represents a "non-terminal" in a phrase/sentence. **/
	private static class NonTerminal implements Text
	{
		/** Creates a new "non-terminal". **/
		public NonTerminal()
		{
		}
		
		/** The list of grammar rules to generating a phrase/sentence with this "non-terminal". **/
		public ArrayList<ArrayList<Text>> rules = new ArrayList<>();
		
		@Override
		public void getText(BufferedWriter out) throws IOException
		{
			for (Text text : rules.get(random.nextInt(rules.size())))
				text.getText(out); // this may be called recursively since "text" may also be a "non-terminal"
		}
	}
}
