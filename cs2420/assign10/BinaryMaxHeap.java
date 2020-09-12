package assign10;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a <i>binary max heap</i> whose removal order of the stored items is based on either the items' natural sort order or a specified
 * <code>Comparator</code>.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version April 4, 2019
 *
 * @param <E> The type of the items stored in this heap.
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E>
{
	//////////// CONSTRUCTORS //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates an empty <code>BinaryMaxHeap</code> instance. The items stored in this heap are ordered using their natural ordering (i.e., the
	 * items stored in this heap must be <code>Comparable</code>).
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap()
	{
		heap = (E[])(new Object[100]);
	}
	
	/**
	 * Creates an empty <code>BinaryMaxHeap</code> instance. The items stored in this heap are ordered using the provided
	 * <code>Comparator</code>.
	 * 
	 * @param comparator The <code>Comparator</code> used to order the items in this heap.
	 */
	public BinaryMaxHeap(Comparator<? super E> comparator)
	{
		this();
		this.comparator = comparator;
	}
	
	/**
	 * Creates a <code>BinaryMaxHeap</code> to store a list of items. The items stored in this heap are ordered using their natural ordering
	 * (i.e., the items stored in this heap must be <code>Comparable</code>).
	 * 
	 * @param items The list of items to store.
	 */
	public BinaryMaxHeap(List<? extends E> items)
	{
		this(items, null);
	}
	
	/**
	 * Creates a <code>BinaryMaxHeap</code> to store a list of items. The items stored in this heap are ordered using the provided
	 * <code>Comparator</code>.
	 * 
	 * @param items      The list of items to store.
	 * @param comparator The <code>Comparator</code> used to order the items in this heap.
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> items, Comparator<? super E> comparator)
	{
		heap = (E[])(new Object[Math.max(items.size() + 1, 100)]);
		this.comparator = comparator;
		buildHeap(items);
	}
	
	//////////// FIELDS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/** The backing array that stores the items this heap. **/
	private E[] heap;
	
	/** The <code>Comparator</code> used to order the items in this heap. **/
	private Comparator<? super E> comparator;
	
	/** The number of items stored in this heap. **/
	private int size;
	
	//////////// PUBLIC METHODS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds an item into this <code>BinaryMaxHeap</code>.
	 * 
	 * @param item The item to add.
	 */
	@Override
	public void add(E item)
	{
		if (++size == heap.length)
			expandHeap();
		
		heap[size] = item;
		percolateUp(size);
	}
	
	/**
	 * Returns, but does not remove, the maximum item in this <code>BinaryMaxHeap</code>.
	 * 
	 * @return The maximum item in this heap.
	 * @throws NoSuchElementException If this heap is empty.
	 */
	@Override
	public E peek() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException("This heap is empty.");
		
		return heap[1];
	}
	
	/**
	 * Returns and removes the maximum item in this <code>BinaryMaxHeap</code>.
	 * 
	 * @return The maximum item in this heap.
	 * @throws NoSuchElementException If this heap is empty.
	 */
	@Override
	public E extractMax() throws NoSuchElementException
	{
		E temp = peek();
		heap[1] = heap[size--];
		percolateDown(1);
		
		return temp;
	}
	
	/** Removes all items from this <code>BinaryMaxHeap</code>. **/
	@Override
	public void clear()
	{
		size = 0;
	}
	
	/**
	 * Returns the number of items stored in this <code>BinaryMaxHeap</code>.
	 * 
	 * @return The number of items stored in this heap.
	 */
	@Override
	public int size()
	{
		return size;
	}
	
	/**
	 * Checks and returns <code>true</code> if this <code>BinaryMaxHeap</code> is empty.
	 * 
	 * @return <code>true</code> if this heap is empty, otherwise <code>false</code>.
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/**
	 * Returns an array with the items stored in this <code>BinaryMaxHeap</code>, in no particular order.
	 * 
	 * @returns An array containing all items stored in this heap.
	 */
	@Override
	public Object[] toArray()
	{
		return Arrays.copyOfRange(heap, 1, size + 1);
	}
	
	//////////// PRIVATE METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/** Constructs this heap by a given list of items. **/
	private void buildHeap(List<? extends E> items)
	{
		heap[0] = null; // a placeholder
		for (int i = 0; i < items.size(); i++)
			heap[i + 1] = items.get(i);
		
		size = items.size();
		for (int i = size / 2; i >= 1; i--)
			percolateDown(i);
	}
	
	/** Compares the items at two specific indices. **/
	@SuppressWarnings("unchecked")
	private int compare(int index1, int index2)
	{
		// define the index 0 to always be the greatest
		if (index1 == 0)
			return 1;
		
		if (index2 == 0)
			return -1;
		
		if (comparator == null) // then assume the items are all Comparable
			return ((Comparable<? super E>)heap[index1]).compareTo(heap[index2]);
		
		return comparator.compare(heap[index1], heap[index2]);
	}
	
	/** Expands the backing array of this heap to twice its previous size. **/
	private void expandHeap()
	{
		heap = Arrays.copyOf(heap, heap.length * 2);
	}
	
	/** Percolates (bubbles) the item at a specific index downward. **/
	private void percolateDown(int index)
	{
		int leftIndex = index * 2;
		int rightIndex = index * 2 + 1;
		
		if (leftIndex > size)
			return;
		
		int greaterIndex = 0;
		if (rightIndex > size || compare(leftIndex, rightIndex) >= 0)
			greaterIndex = leftIndex;
		else
			greaterIndex = rightIndex;
		
		if (compare(index, greaterIndex) < 0)
		{
			swap(index, greaterIndex);
			percolateDown(greaterIndex);
		}
	}
	
	/** Percolates (bubbles) the item at a specific index upward. **/
	private void percolateUp(int index)
	{
		int parentIndex = index / 2;
		if (compare(index, parentIndex) > 0)
		{
			swap(index, parentIndex);
			percolateUp(parentIndex);
		}
	}
	
	/** Swaps the items at two specific indices. **/
	private void swap(int index1, int index2)
	{
		E temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}
}
