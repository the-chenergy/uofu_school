package lab05;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import assign03.PriorityQueue;

/**
 * A priority queue that supports access of the minimum element only.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version January 28, 2019
 * 
 * @param <E> - the type of elements contained in this priority queue
 */
public class SimplePriorityQueue<E> implements PriorityQueue<E>, Iterable<E>
{
	/**
	 * The elements in this priority queue.
	 */
	private E[] elements;
	
	/**
	 * The number of elements in the priority queue.
	 */
	private int numElements;
	
	/**
	 * The comparator used to sort the elements.
	 */
	private Comparator<? super E> comparator;
	
	/**
	 * Creates a new <code>SimplePriorityQueue</code> instance containing elements that are
	 * <code>Comparable</code>.
	 */
	public SimplePriorityQueue()
	{
		clear();
	}
	
	/**
	 * Creates a new <code>SimplePriorityQueue</code> instance sorted with a provided
	 * <code>comparator</code>.
	 * 
	 * @param comparator - the comparator to sort the elements
	 */
	public SimplePriorityQueue(Comparator<? super E> comparator)
	{
		clear();
		
		this.comparator = comparator;
	}
	
	/**
	 * Retrieves, but does not remove, the minimum element in this priority queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	@Override
	public E findMin() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException("The queue is empty.");
		
		return elements[numElements - 1];
	}
	
	/**
	 * Retrieves and removes the minimum element in this priority queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	@Override
	public E deleteMin() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException("The queue is empty.");
		
		return elements[--numElements];
	}
	
	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param item - the element to insert
	 */
	@Override
	public void insert(E item)
	{
		if (isEmpty())
		{
			elements[numElements++] = item;
			
			return;
		}
		
		// Use binary-search to find the appropriate spot for the element to insert.
		// (The element array is sorted greatest to smallest.)
		
		int low = 0, high = size() - 1;
		int targetIndex = -1;
		
		while (true)
		{
			targetIndex = (low + high) / 2;
			
			int diff;
			if (comparator == null) // we assume the elements are Comparable
				diff = ((Comparable<? super E>)item).compareTo(elements[targetIndex]);
			else
				diff = comparator.compare(item, elements[targetIndex]);
			
			if (diff == 0)
				break;
			
			if (diff < 0)
				low = targetIndex + 1;
			else
				high = targetIndex - 1;
			
			if (low > high) // insert the item at where they overlap
			{
				targetIndex = low;
				
				break;
			}
		}
		
		// Insert the new item at its target index and shift the around elements
		// accordingly. If the element array runs out of space, create a new one
		// with twice the capacity of the old array.
		
		if (++numElements > elements.length)
			elements = Arrays.copyOf(elements, elements.length * 2);
		
		for (int i = numElements - 1; i > targetIndex; i--)
			elements[i] = elements[i - 1];
		
		elements[targetIndex] = item;
	}
	
	/**
	 * Inserts the specified elements into this priority queue.
	 * 
	 * @param coll - the collection of elements to insert
	 */
	@Override
	public void insertAll(Collection<? extends E> coll)
	{
		for (E element : coll)
			insert(element);
	}
	
	/**
	 * Returns the number of elements in this priority queue.
	 * 
	 * @return the number of elements in this priority queue
	 */
	@Override
	public int size()
	{
		return numElements;
	}
	
	/**
	 * Returns true if this priority queue contains no elements.
	 * 
	 * @return <code>true</code> if this priority queue contains no elements, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean isEmpty()
	{
		return numElements == 0;
	}
	
	/**
	 * Removes all of the elements from this priority queue. The queue will be empty when this call
	 * returns.
	 */
	@Override
	public void clear()
	{
		elements = (E[])new Object[1];
		
		numElements = 0;
	}
	
	/* ----------------------------------------- */
	/* ----------- INTERATOR RELATED ----------- */
	/* ----------------------------------------- */
	
	@Override
	public Iterator<E> iterator()
	{
		return new SimplePriorityQueueIterator<E>();
	}
	
	private class SimplePriorityQueueIterator<E> implements Iterator<E>
	{
		/**
		 * Creates a new <code>SimplePriorityQueueIterator</code> instance.
		 * 
		 * @param elements The elements to iterate.
		 */
		public SimplePriorityQueueIterator()
		{
			index = 0;
			canRemove = false;
		}
		
		/**
		 * The next index.
		 * 
		 * @private
		 */
		private int index;
		
		/**
		 * Whether this iterator can remove an element.
		 * 
		 * @private
		 */
		private boolean canRemove;
		
		/**
		 * Returns true if this iterator has a next element.
		 * 
		 * @return
		 */
		@Override
		public boolean hasNext()
		{
			return index < numElements;
		}
		
		/**
		 * Returns the next element.
		 * 
		 * @return
		 */
		@Override
		public E next()
		{
			if (!hasNext())
				throw new NoSuchElementException("No next element found.");
			
			canRemove = true;
			
			return (E)elements[index++];
		}
		
		/**
		 * Removes the next element.
		 */
		public void remove()
		{
			if (!canRemove)
				throw new IllegalStateException("Cannot remove element.");
			
			numElements--;
			index--;
			
			for (int i = index; i < numElements; i++)
				elements[i] = elements[i + 1];
		}
	}
}
