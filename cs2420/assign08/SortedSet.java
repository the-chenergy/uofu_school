package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * An interface for representing a sorted set of generically-typed items. By definition, a set
 * contains no duplicate items. The items are ordered using their natural ordering (i.e., each
 * item must be Comparable). Note that this interface is much like Java's SortedSet, but
 * simpler.
 * 
 * @author Erin Parker
 * @version March 6, 2019
 */
public interface SortedSet<E extends Comparable<? super E>>
{
	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if the input item
	 *         was actually inserted); otherwise, returns false
	 */
	public boolean add(E item);
	
	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items - the collection of items whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if any item in the
	 *         input collection was actually inserted); otherwise, returns false
	 */
	public boolean addAll(Collection<? extends E> items);
	
	/**
	 * Removes all items from this set. The set will be empty after this method call.
	 */
	public void clear();
	
	/**
	 * Determines if there is an item in this set that is equal to the specified item.
	 * 
	 * @param item - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input item; otherwise,
	 *         returns false
	 */
	public boolean contains(E item);
	
	/**
	 * Determines if for each item in the specified collection, there is an item in this set that is
	 * equal to it.
	 * 
	 * @param items - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an item in this set that
	 *         is equal to it; otherwise, returns false
	 */
	public boolean containsAll(Collection<? extends E> items);
	
	/**
	 * Returns the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public E first() throws NoSuchElementException;
	
	/**
	 * Returns true if this set contains no items.
	 */
	public boolean isEmpty();
	
	/**
	 * Returns the last (i.e., largest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public E last() throws NoSuchElementException;
	
	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if the input item
	 *         was actually removed); otherwise, returns false
	 */
	public boolean remove(E item);
	
	/**
	 * Ensures that this set does not contain any of the items in the specified collection.
	 * 
	 * @param items - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if any item in the
	 *         input collection was actually removed); otherwise, returns false
	 */
	public boolean removeAll(Collection<? extends E> items);
	
	/**
	 * Returns the number of items in this set.
	 */
	public int size();
	
	/**
	 * Returns an ArrayList containing all of the items in this set, in sorted order.
	 */
	public ArrayList<E> toArrayList();
}
