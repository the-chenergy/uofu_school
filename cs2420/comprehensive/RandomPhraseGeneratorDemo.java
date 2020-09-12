package comprehensive;

import java.io.IOException;

/**
 * Runs the <code>RandomPhraseGenerator</code> class to ensure it works properly before submitting.
 * 
 * @author Qianlang Chen
 * @version M 04/16/19
 */
public class RandomPhraseGeneratorDemo
{
	public static void main(String[] args) throws IOException
	{
		RandomPhraseGenerator.main(new String[] {"src/comprehensive/poetic_sentence.g", "1"});
		System.out.println();
		
		RandomPhraseGenerator.main(new String[] {"src/comprehensive/mathematical_expression.g", "4"});
		System.out.println();
		
		RandomPhraseGenerator.main(new String[] {"src/comprehensive/assignment_extension_request.g", "8"});
	}
}
