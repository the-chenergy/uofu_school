package lab12;

/**
 * This class represents a single entry in a map; i.e., a key-value pair.
 * 
 * @author Erin Parker & ??
 * @version April 5, 2019
 *
 * @param <K> - placeholder for key type
 * @param <V> - placeholder for values type
 */
public class MapEntry<K extends Comparable<? super K>, V> implements Comparable<MapEntry<K, V>>
{
	private K key;
	
	private V value;
	
	/**
	 * Creates a new MapEntry with the specified key and value.
	 * 
	 * @param key
	 * @param value
	 */
	public MapEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return the key of this MapEntry
	 */
	public K getKey()
	{
		return this.key;
	}
	
	/**
	 * @return the value of this MapEntry
	 */
	public V getValue()
	{
		return this.value;
	}
	
	/**
	 * Resets the value of this MapEntry.
	 * 
	 * @param value
	 */
	public void setValue(V value)
	{
		this.value = value;
	}
	
	/**
	 * Generates and returns a textual representation of this key-value pair.
	 */
	@Override
	public String toString()
	{
		return "(" + key + ", " + value + ")";
	}
	
	/**
	 * Considers two key-value pairs to be equal if they have the same key.
	 */
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof MapEntry<?, ?>))
			return false;
		
		MapEntry<?, ?> rhs = (MapEntry<?, ?>)other;
		
		return rhs.key.equals(key);
	}
	
	/**
	 * Compares two key-value pairs according to their keys.
	 */
	@Override
	public int compareTo(MapEntry<K, V> o)
	{
		return key.compareTo(o.key);
	}
	
	/**
	 * Uses the hash code of the key.
	 */
	@Override
	public int hashCode()
	{
		return key.hashCode();
	}
}
