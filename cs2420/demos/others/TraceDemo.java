package demos.others;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * See if <code>tr.a.ce()</code> works.
 * 
 * @author Qianlang Chen
 * @version T 03/05/19
 */
public class TraceDemo
{
	/** ... **/
	public static void main(String[] args)
	{
		tr.a.ce("`n");
		tr.a.ce("`s", "My name is", 'Q', 'C', "and I am", 18, "years old!");
		tr.a.ce("`fdc", "%d%% %s", 100, new Word("accurate").toString(), 2019, new String[] {"33", "EI\r"});
		tr.a.ce("`d", Arrays.asList(2, 4, 6));
		tr.a.ce("`dmp", new int[0], new byte[1], new double[] {Math.PI, Math.E}, new char[] {'a', '\t', '\n', '\\'});
		tr.a.ce("`qm", "Some text!!\n\t\\\"", 'a');
		
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("Asianboii", 18);
		hashMap.put("Kevin", 23);
		hashMap.put("Joe", 21);
		tr.a.ce(hashMap);
		
		tr.a.ce(new Point(255, 511));
		
		WordBank wordBank = new WordBank("MyWordBank", "file", "edit", "source", "refactor", "servers", "expressions", "run");
		tr.a.ce(wordBank);
		tr.a.ce(wordBank.dict);
		
		tr.a.ce("`k", "trace demo main give me stack!");
	}
	
	private static class Word
	{
		private String s;
		
		public Word(String s)
		{
			this.s = s;
		}
		
		@Override
		public String toString()
		{
			return s;
		}
	}
	
	private static class WordBank
	{
		private String name;
		
		public HashMap<Character, List<Word>> dict;
		
		public WordBank(String name, String ...args)
		{
			this.name = name;
			
			dict = new HashMap<>();
			for (String word : args)
			{
				char ch = word.charAt(0);
				
				if (dict.containsKey(ch))
				{
					dict.get(ch).add(new Word(word));
					
					continue;
				}
				
				ArrayList<Word> words = new ArrayList<>();
				words.add(new Word(word));
				
				dict.put(ch, words);
			}
		}
	}
}
