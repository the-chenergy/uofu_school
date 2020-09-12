package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a list of items of one specific type.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version February 27, 2019
 * 
 * @param <E> The type of data stored in this list.
 */
public class SinglyLinkedList<E> implements List<E>
{
	/**
	 * The head node of this list.
	 */
	private Node<E> head;
	
	/**
	 * The number of nodes in this list.
	 */
	private int count;
	
	/**
	 * Creates a new <code>SinglyLinkedList</code> instance.
	 */
	public SinglyLinkedList()
	{
	}
	
	/**
	 * Inserts an element at the beginning of the list. O(1) for a singly-linked list.
	 * 
	 * @param element - the element to add
	 */
	@Override
	public void addFirst(E element)
	{
		head = new Node<E>(element, head);
		count++;
	}
	
	/**
	 * Inserts an element at a specific position in the list. O(N) for a singly-linked list.
	 * 
	 * @param index   - the specified position
	 * @param element - the element to add
	 * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
	 */
	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException
	{
		if (index == 0)
		{
			addFirst(element);
			
			return;
		}
		
		Node<E> temp = getPrevNode(index, true);
		
		temp.next = new Node<E>(element, temp.next);
		count++;
	}
	
	/**
	 * Gets the first element in the list. O(1) for a singly-linked list.
	 * 
	 * @return the first element in the list
	 * @throws NoSuchElementException if the list is empty
	 */
	@Override
	public E getFirst() throws NoSuchElementException
	{
		if (head == null)
			throw new NoSuchElementException("No elements in this list");
		
		return head.element;
	}
	
	/**
	 * Gets the element at a specific position in the list. O(N) for a singly-linked list.
	 * 
	 * @param index - the specified position
	 * @return the element at the position
	 * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index == 0 && head != null)
			return head.element;
		
		Node<E> temp = getPrevNode(index, false);
		
		return temp.next.element;
	}
	
	/**
	 * Removes and returns the first element from the list. O(1) for a singly-linked list.
	 * 
	 * @return the first element
	 * @throws NoSuchElementException if the list is empty
	 */
	@Override
	public E removeFirst() throws NoSuchElementException
	{
		if (head == null)
			throw new NoSuchElementException("No elements in this list");
		
		E temp = head.element;
		
		head = head.next;
		count--;
		
		return temp;
	}
	
	/**
	 * Removes and returns the element at a specific position in the list. O(N) for a singly-linked
	 * list.
	 * 
	 * @param index - the specified position
	 * @return the element at the position
	 * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index == 0 && head != null)
			return removeFirst();
		
		Node<E> temp = getPrevNode(index, false);
		E item = temp.next.element;
		
		temp.next = temp.next.next;
		count--;
		
		return item;
	}
	
	/**
	 * Returns the Node before the specified index
	 * 
	 * @param index     the index to find the Node before
	 * @param allowSize whether it allows the <code>size</code> to be a valid index
	 * @return the node before the specified index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	private Node<E> getPrevNode(int index, boolean allowSize) throws IndexOutOfBoundsException
	{
		if (index <= 0 || index > count || !allowSize && index == count)
			throw new IndexOutOfBoundsException("The index is invalid");
		
		Node<E> temp = head;
		
		while (--index > 0)
			temp = temp.next;
		
		return temp;
	}
	
	/**
	 * Determines the index of the first occurrence of the specified element in the list, or -1 if
	 * this list does not contain the element. O(N) for a singly-linked list.
	 * 
	 * @param element - the element to search for
	 * @return the index of the first occurrence; -1 if the element is not found
	 */
	@Override
	public int indexOf(E element)
	{
		int i = 0;
		
		for (E e : this)
		{
			if (e == element)
				return i;
			
			i++;
		}
		
		return -1;
	}
	
	/**
	 * O(1) for a singly-linked list.
	 * 
	 * @return the number of elements in this list
	 */
	@Override
	public int size()
	{
		return count;
	}
	
	/**
	 * O(1) for a singly-linked list.
	 * 
	 * @return true if this collection contains no elements; false, otherwise
	 */
	@Override
	public boolean isEmpty()
	{
		return count == 0;
	}
	
	/**
	 * Removes all of the elements from this list. O(1) for a singly-linked list.
	 */
	@Override
	public void clear()
	{
		head = null;
		count = 0;
	}
	
	/**
	 * Generates an array containing all of the elements in this list in proper sequence (from first
	 * element to last element). O(N) for a singly-linked list.
	 * 
	 * @return an array containing all of the elements in this list, in order
	 */
	@Override
	public Object[] toArray()
	{
		int i = 0;
		Object[] temp = new Object[count];
		
		for (E e : this)
		{
			temp[i] = e;
			i++;
		}
		
		return temp;
	}
	
	/**
	 * @return an iterator over the elements in this list in proper sequence (from first element to
	 *         last element)
	 */
	@Override
	public Iterator<E> iterator()
	{
		return new SinglyLinkedListIterator<E>();
	}
	
	/**
	 * Represents a Node within a SinglyLinkedList. Each node contains an element and a pointer to
	 * the next node.
	 *
	 * @param <T> The data stored
	 */
	private class Node<T>
	{
		/**
		 * The data stored in this node
		 */
		public T element;
		
		/**
		 * The next node
		 */
		public Node<T> next;
		
		/**
		 * Creates a new Node instance
		 * 
		 * @param element The data stored in this node
		 * @param next    The next node
		 */
		public Node(T element, Node<T> next)
		{
			this.element = element;
			this.next = next;
		}
	}
	
	/**
	 * The <code>Iterator</code> used to iterate the <code>SinglyLinkedList</code> class.
	 *
	 * @param <T> The type of data this iterator iterates.
	 */
	private class SinglyLinkedListIterator<T> implements Iterator<T>
	{
		/**
		 * The current node.
		 */
		private Node<T> curr;
		
		/**
		 * The previous node to the current node.
		 */
		private Node<T> prev;
		
		/**
		 * Creates a new <code>SinglyLinkedListIterator</code> instance.
		 */
		public SinglyLinkedListIterator()
		{
			curr = (Node<T>)head;
		}
		
		/**
		 * Returns whether there is a next element.
		 * 
		 * @return <code>true</code> if there is a next element, <code>false</code> otherwise
		 */
		@Override
		public boolean hasNext()
		{
			return curr != null;
		}
		
		/**
		 * Returns the next element.
		 * 
		 * @return The next element
		 * @throws NoSuchElementException if there is no next element
		 */
		@Override
		public T next() throws NoSuchElementException
		{
			if (!this.hasNext())
				throw new NoSuchElementException("No next element");
			
			if (prev == null)
			{
				if (curr != head)
					prev = (Node<T>)head;
			}
			else if (prev.next != curr)
			{
				prev = prev.next;
			}
			
			T element = curr.element;
			curr = curr.next;
			
			return element;
		}
		
		/**
		 * Removes the last seen node from the linked list.
		 * 
		 * @throws IllegalStateException if there is no current element to remove
		 */
		@Override
		public void remove() throws IllegalStateException
		{
			if (curr == head || prev != null && curr == prev.next)
				throw new IllegalStateException("Cannot remove element");
			
			if (prev == null)
				curr = (Node<T>)(head = head.next);
			else
				curr = prev.next = prev.next.next;
			
			count--;
		}
	}
}
