package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class can be used to generate random phrases from a grammar file.
 * 
 * @author         Brandon Walton and Naren Anandh
 * @version        4/17/19
 * @param   args[] - args[0] is the directory of the grammar file, and args[1] is the number of sentences to generate from the grammar file.
 */
public class RandomPhraseGenerator
{
    public static void main(String args[]) throws FileNotFoundException
    {
	HashTable table = new HashTable();
	int timesToIterate = Integer.valueOf(args[1]);
	StringBuilder data = new StringBuilder();
	Scanner scanner = new Scanner(new File(args[0]));
	while (scanner.hasNextLine())
	    {
		data.append(scanner.nextLine());
		data.append("\n");
	    }
	scanner.close();
	boolean curlyExists = false, firstLine = false, secondLine = false;
	StringBuilder current = new StringBuilder();
	Link heading = null;
	for (int i = 0; i < data.length(); i++)
	    {
		switch (data.charAt(i))
		    {
		    case '{':
			curlyExists = true;
			firstLine = true;
			break;
		    case '}':
			curlyExists = false;
			table.add(heading);
			heading = null;
			break;
		    case '\n':
			if (firstLine)
			    {
				firstLine = false;
				secondLine = true;
			    }
			else if (secondLine)
			    {
				secondLine = false;
				heading = new Link(current.toString());
			    }
			else if (curlyExists)
			    heading.add(current.toString());
			current.setLength(0);
			break;
		    default:
			if (curlyExists)
			    current.append(data.charAt(i));
			break;
		    }
	    }
	for (int i = 0; i < timesToIterate; i++)
	    {
		System.out.println(generateMessage(table, "<start>"));
	    }
    }
    
    /**
     * Recursively generates a segment of a message from the given table
     * 
     * @param   table - the HashTable to get a link from
     * @param   start - the key of the link to generate from
     * @returns       the message segment from the given starting point
     */
    public static String generateMessage(HashTable table, String start)
    {
	Link header = table.get(start);
	StringBuilder string = new StringBuilder();
	StringBuilder altHeader = new StringBuilder();
	boolean inBracket = false;
	String line = header.getRandom();
	for (int i = 0; i < line.length(); i++)
	    {
		switch (line.charAt(i))
		    {
		    case '<':
			inBracket = true;
			altHeader.append(line.charAt(i));
			break;
		    case '>':
			inBracket = false;
			altHeader.append(line.charAt(i));
			string.append(generateMessage(table, altHeader.toString()));
			altHeader = new StringBuilder();
			break;
		    default:
			if (inBracket)
			    altHeader.append(line.charAt(i));
			else
			    string.append(line.charAt(i));
			break;
		    }
	    }
	return string.toString();
    }
}
