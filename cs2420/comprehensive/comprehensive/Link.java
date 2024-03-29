package comprehensive;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a link in the HashTable class. A link represents a
 * nonTerminal from a grammar file and every line that can be generated from
 * that nonTerminal.
 * 
 * @author  Brandon Walton and Naren Anandh
 * @version 4/17/19
 *
 */
public class Link
{
    // The nonterminal that this link represents
    private String nonTerminal;
    // Every line that can be obtained from this link
    private ArrayList<String> contents;
    
    /**
     * Creates a new Link with the provided nonTerminal.
     * 
     * @param nonTerminal - the nonTerminal for each line found in this link.
     */
    public Link(String nonTerminal)
    {
	this.nonTerminal = nonTerminal;
	contents = new ArrayList<String>();
    }
    
    /**
     * Adds a new line to the contents of this link.
     * 
     * @param contents - The line to be added.
     */
    public void add(String contents)
    {
	this.contents.add(contents);
    }
    
    /**
     * @returns This link's nonTerminal.
     */
    public String getNonTerminal()
    {
	return nonTerminal;
    }
    
    /**
     * @returns A ranodm line that can be reached from this link's nonTerminal.
     */
    public String getRandom()
    {
	Random rng = new Random();
	return contents.get(rng.nextInt(contents.size()));
    }
}
