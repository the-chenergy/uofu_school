package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Generates and displays random phrases/sentences from a set of grammar rules.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version W 04/10/19
 */
public class RandomPhraseGenerator10
{
	/**
	 * Application entry point.<br/>
	 * <br/>
	 * Two arguments must be passed: the first argument is the URL to the grammar definition text-file, and the second argument must be a
	 * positive integer indicating the number of random phrases/sentences to generate and display from the given grammar rules.
	 * 
	 * @param args The list with the 2 arguments to pass into this program.
	 * @throws FileNotFoundException If the URL to the grammar definition file cannot be found or accessed.
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		//////////// TESTING AREA ////////////
		
		// args = new String[] {"src/comprehensive/poetic_sentence.g", "8"};
		// args = new String[] {"src/comprehensive/mathematical_expression.g", "8"};
		// args = new String[] {"src/comprehensive/assignment_extension_request.g", "8"};
		
		//////////////////////////////////////
		
		readGrammar(new Scanner(new File(args[0])));
		printPhrases(Integer.parseInt(args[1]));
	}
	
	/**
	 * The dictionary used to store the set of grammar rules read in.<br/>
	 * <br/>
	 * Key: The signature of the "non-terminal" to define, such as <i>"&lt;object&gt;"</i>.<br/>
	 * Value: The list of production rules of such "non-terminal". An example production rule may be <i>"a smart &lt;noun&gt;"</i>.
	 */
	private static Hashtable<String, ArrayList<LinkedList<Text>>> ruleDict = new Hashtable<>();
	
	/** The random number generator used to choose a production rule for a "non-terminal" when there are multiple. **/
	private static Random random = new Random();
	
	/**
	 * Reads the grammar rules with a given scanner.
	 * 
	 * @param scanner The scanner used to read in the grammar rules.
	 */
	private static void readGrammar(Scanner scanner)
	{
		boolean isInComments = true; // whether the current line is a comment and should be ignored (i.e., outside of any blocks)
		ArrayList<LinkedList<Text>> rules = null; // records the rule list for the current signature (block)
		
		while (scanner.hasNextLine()) // read the grammar file line-by-line
		{
			String line = scanner.nextLine();
			
			if (isInComments)
			{
				if (line.contentEquals("{")) // comment area ends
				{
					isInComments = false;
					
					// Read in the signature of the "non-terminal" whose rules are being defined in the current block
					
					rules = new ArrayList<>();
					ruleDict.put(scanner.nextLine(), rules); // create a rule list for that signature in our rule-dictionary
					// since the signature is always on the very next line of the opening brace ('{'), we can safely do this
				}
				
				continue;
			}
			
			if (line.contentEquals("}")) // comment area starts
			{
				isInComments = true;
				
				continue;
			}
			
			// Each line at this point is a production rule of a "non-terminal".
			
			LinkedList<Text> rule = new LinkedList<>();
			rules.add(rule); // create a rule in the rule list for the current signature
			
			// Format the production rule into a list of Texts ("terminal" and "non-terminal" objects)
			
			int start = 0;
			for (int i = line.indexOf('<'); i != -1; i = line.indexOf('<', start)) // keep searching for the next opening angle-bracket ('<')
			{
				if (i > start) // only add a "terminal" when there is one
					rule.add(new Terminal(line.substring(start, i))); // normal strings represent "terminals"
				// NOTE that our solution treats multiple words -- including spaces and punctuation marks in-between if there are any --
				// as a single "terminal"; for example, the production rule "The <noun> <verb> tonight." will be treated and formatted as:
				// { Terminal("The "), Non-terminal("<noun>"), Terminal(" "), Non-terminal("<verb>"), Terminal(" tonight.") }
				
				start = line.indexOf('>', i + 1) + 1;
				rule.add(new NonTerminal(line.substring(i, start)));
			}
			
			if (start < line.length()) // there is a "terminal" right before the line ends
				rule.add(new Terminal(line.substring(start)));
		}
		
		scanner.close();
	}
	
	/**
	 * Prints out a number of random phrases generated by the given grammar rules.
	 * 
	 * @param n The number of random phrases to generate and display.
	 */
	private static void printPhrases(int n)
	{
		NonTerminal start = new NonTerminal("<start>");
		StringBuilder buffer = new StringBuilder();
		
		while (n-- > 0)
		{
			start.getText(buffer);
			buffer.append("\n"); // new-lines separate each random phrase/sentence generated
		}
		
		//System.out.print(buffer.toString()); // sysout is so slow that we avoid calling it many times
	}
	
	/** Represents a piece of text in a phrase/sentence, which may be either a "non-terminal" or "terminal". **/
	private static interface Text
	{
		/**
		 * Generates a string this text can represent and puts it at the end of a given <code>StringBuilder</code>.
		 * 
		 * @param buffer The buffer to put this text in.
		 */
		public void getText(StringBuilder buffer);
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
		public void getText(StringBuilder buffer)
		{
			buffer.append(text); // just append the text in this "terminal" to the end of the buffer
		}
	}
	
	/** Represents a "non-terminal" in a phrase/sentence. **/
	private static class NonTerminal implements Text
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
			ArrayList<LinkedList<Text>> rules = ruleDict.get(signature); // retrieve the production rules for this specific "non-terminal"
			
			for (Text text : rules.get(random.nextInt(rules.size())))
				text.getText(buffer); // this may be called recursively since "text" may also be a "non-terminal"
		}
	}
}
