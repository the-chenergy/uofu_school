package assign09;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a hash table that records key-value-pairs (entries) of some types.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version F 03/22/18
 *
 * @param <K> The type of the keys of the entries.
 * @param <V> The type of the values of the entries.
 */
public class HashTable<K, V> implements Map<K, V>
{
	//////////// STATIC ////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The default limit of the average length in each <code>LinkedList</code> that the backing
	 * array expands at.
	 */
	private static final double DEFAULT_THRESHOLD = 5;
	
	/** The initial capacity of the backing array when a new <code>HashTable</code> constructs. **/
	private static final int INITIAL_CAPACITY = 64;
	
	//////////// CONSTRUCTOR ///////////////////////////////////////////////////////////////////////
	
	/** Creates a new <code>HashTable</code> instance. **/
	public HashTable()
	{
		table = new ArrayList<>();
		
		capacity = INITIAL_CAPACITY;
		initializeTable();
		
		threshold = DEFAULT_THRESHOLD;
	}
	
	//////////// FIELDS ////////////////////////////////////////////////////////////////////////////
	
	/** The backing array that stores the data in this <code>HashTable</code>. **/
	private ArrayList<LinkedList<MapEntry<K, V>>> table;
	
	/** The number of entries added into this <code>HashTable</code>. **/
	private int size;
	
	/** The capacity of the backing array of this <code>HashTable</code>. **/
	private int capacity;
	
	/** The limit of the load factor of which the backing array will expand at. **/
	private double threshold;
	
	/**
	 * [For Experimenting Use Only]</br>
	 * The number of collisions occurred since last <code>clear()</code> method call.
	 */
	private int numCollisions;
	
	
	//////////// PUBLIC METHODS ////////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if this <code>HashTable</code> contains any entries.
	 * 
	 * @return <code>true</code> if this <code>HashTable</code> is empty (i.e., does not contain any
	 *         entries); otherwise <code>false</code>.
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/**
	 * Returns the number of entries in this <code>HashTable</code>.
	 * 
	 * @return the number of entries in this <code>HashTable</code>.
	 */
	@Override
	public int size()
	{
		return size;
	}
	
	/**
	 * Removes all entries from this <code>HashTable</code>.
	 */
	@Override
	public void clear()
	{
		size = 0;
		
		for (LinkedList<MapEntry<K, V>> chain : table)
			chain.clear();
	}
	
	/**
	 * Checks if this <code>HashTable</code> contains some key.
	 * 
	 * @param key The key to check for existence.
	 * @return <code>true</code> if this <code>HashTable</code> contains the specified key;
	 *         otherwise <code>false</code>.
	 */
	@Override
	public boolean containsKey(K key)
	{
		for (MapEntry<K, V> entry : table.get(chainIndex(key)))
		{
			if (entry.getKey().equals(key))
				return true;
			
			numCollisions++; // for experimenting use only
		}
		
		return false;
	}
	
	/**
	 * Checks if this <code>HashTable</code> contains some value.
	 * 
	 * @param value The value to check for existence.
	 * @return <code>true</code> if this <code>HashTable</code> contains the specified value;
	 *         otherwise <code>false</code>.
	 */
	@Override
	public boolean containsValue(V value)
	{
		List<MapEntry<K, V>> entries = entries();
		
		for (MapEntry<K, V> entry : entries)
			if (entry.getValue().equals(value))
				return true;
			
		return false;
	}
	
	/**
	 * Returns all entries in this <code>HashTable</code>.
	 * 
	 * @return A <code>List</code> containing every entry in this <code>HashTable</code>.
	 */
	@Override
	public List<MapEntry<K, V>> entries()
	{
		List<MapEntry<K, V>> list = new ArrayList<>();
		
		for (LinkedList<MapEntry<K, V>> chain : table)
			list.addAll(chain);
		
		return list;
	}
	
	/**
	 * Returns the value in this <code>HashTable</code> that a specific key maps to.
	 * 
	 * @return The value that the specified key maps to, or <code>null</code> if the specified key
	 *         does not exist in this <code>HashTable</code>.
	 */
	@Override
	public V get(K key)
	{
		for (MapEntry<K, V> entry : table.get(chainIndex(key)))
			if (entry.getKey().equals(key))
				return entry.getValue();
			
		return null;
	}
	
	/**
	 * Puts or replaces some value that is mapped to a specific key in this <code>HashTable</code>.
	 * 
	 * @param key   The key to map the value under.
	 * @param value The value to map to the key.
	 * @return The value that is previously mapped to the key, or <code>null</code> if the key does
	 *         not map to one.
	 */
	@Override
	public V put(K key, V value)
	{
		if (loadFactor() > threshold)
			expandTable();
		
		LinkedList<MapEntry<K, V>> chain = table.get(chainIndex(key));
		
		for (MapEntry<K, V> entry : chain)
		{
			if (entry.getKey().equals(key))
			{
				try
				{
					return entry.getValue();
				}
				finally // to be done after return
				{
					entry.setValue(value);
				}
			}
			
			numCollisions++; // for experimenting use only
		}
		
		chain.add(new MapEntry<>(key, value));
		size++;
		
		return null;
	}
	
	/**
	 * Removes the mapping for a key from this <code>HashTable</code> if it is present.
	 *
	 * @param key The key to remove (if it exists in this <code>HashTable</code>).
	 * @return The value that is removed as a result, or <code>null</code> if there isn't one.
	 */
	@Override
	public V remove(K key)
	{
		LinkedList<MapEntry<K, V>> chain = table.get(chainIndex(key));
		
		for (MapEntry<K, V> entry : chain)
		{
			if (entry.getKey().equals(key))
			{
				chain.remove(entry);
				size--;
				
				return entry.getValue();
			}
			
			numCollisions++; // for experimenting use only
		}
		
		return null;
	}
	
	/**
	 * Returns the string representation of this <code>HashTable</code>.
	 * 
	 * @return A string that contains the string representation of each key-value-pair (entry) added
	 *         into this <code>HashTable</code>.
	 */
	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		
		for (MapEntry<K, V> entry : entries())
			out.append("  " + entry.getKey() + ": " + entry.getValue() + "\n");
		
		return "{\n" + out.toString() + "}";
	}
	
	//////////// PRIVATE METHODS ///////////////////////////////////////////////////////////////////
	
	/** Calculates and returns the load factor (λ) of this data structure. **/
	private double loadFactor()
	{
		return size / (double)capacity;
	}
	
	/** Ensures that the backing array has enough capacity. **/
	private void initializeTable()
	{
		table.ensureCapacity(capacity);
		
		for (int i = 0; i < capacity; i++)
			table.add(new LinkedList<>());
	}
	
	/** Double the capacity of the backing array. **/
	private void expandTable()
	{
		initializeTable();
		capacity *= 2;
		
		List<MapEntry<K, V>> entriesList = entries();
		
		clear();
		
		for (MapEntry<K, V> entry : entriesList)
			put(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Returns the "formatted" (looped) hash value of a key, that is, the index of the chain that
	 * the key is supposed to go in.
	 */
	private int chainIndex(K key)
	{
		return Math.abs(key.hashCode()) % capacity;
	}
	
	/**
	 * [For Experimenting Use Only]</br>
	 * Returns the number of collisions occurred in the <code>containsKey()</code>,
	 * <code>put()</code>, and <code>remove()</code> methods, since last time it was reset by
	 * calling the <code>resetNumCollisions()</code> method.
	 */
	int getNumCollisions()
	{
		return numCollisions;
	}
	
	/**
	 * [For Experimenting Use Only]</br>
	 * Resets the collision counter for the <code>containsKey()</code>, <code>put()</code>, and
	 * <code>remove()</code> methods.
	 */
	void resetNumCollisions()
	{
		numCollisions = 0;
	}
	
	/**
	 * [For Experimenting Use Only]</br>
	 * Sets the threshold of load factor of this <code>HashTable</code> to a new value.
	 */
	void setThreshold(double value)
	{
		threshold = value;
	}
}
