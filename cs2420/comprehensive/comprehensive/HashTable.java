package comprehensive;

import java.util.ArrayList;

/**
 * This class represents a hash table of links, composed from a grammar file by the RandomPhraseGenerator class.
 * 
 * @author  Brandon Walton and Naren Anandh
 * @version 4/17/19
 *
 */
public class HashTable
{
    private ArrayList<ArrayList<Link>> array;
    
    int capacity;
    
    int size;
    
    /**
     * Generates a new HashTable with the default capacity of 32
     */
    public HashTable()
    {
	capacity = 32;
	size = 0;
	array = new ArrayList<ArrayList<Link>>(capacity);
	for (int i = 0; i < capacity; i++)
	    {
		array.add(new ArrayList<Link>());
	    }
    }
    
    /**
     * Generates a new HashTable with the given capacity
     * 
     * @param capacity - the capacity of the HashTable
     */
    public HashTable(int capacity)
    {
	this.capacity = capacity;
	size = 0;
	array = new ArrayList<ArrayList<Link>>(capacity);
    }
    
    /**
     * Adds the given Link to the HashTable
     * 
     * @param l - the Link to be added
     */
    public void add(Link l)
    {
	if (size >= 9 * capacity)
	    resize();
	array.get(Math.abs(l.getNonTerminal().hashCode()) % capacity).add(l);
	size++;
    }
    
    /**
     * Returns the Link using the provided string as a key
     * 
     * @param   s - The key of the Link to search for
     * @returns   The Link with the provided key, or null if no such Link exists
     */
    public Link get(String s)
    {
	for (Link item : array.get(Math.abs(s.hashCode() % capacity)))
	    {
		if (item.getNonTerminal().equals(s))
		    {
			return item;
		    }
	    }
	return null;
    }
    
    /**
     * Resizes this HashTable with double the capacity. This method is called from add if the table is getting too big.
     */
    public void resize()
    {
	int newCapacity = capacity * 2;
	HashTable newHash = new HashTable(newCapacity);
	for (ArrayList<Link> list : array)
	    {
		for (Link item : list)
		    {
			newHash.add(item);
		    }
	    }
	capacity = newCapacity;
	array = newHash.getBackingArray();
    }
    
    /**
     * This method returns the backing ArrayList for this HashTable. This is primarily used by the resize() method.
     * 
     * @return This HashTable's backing array
     */
    public ArrayList<ArrayList<Link>> getBackingArray()
    {
	return array;
    }
}
