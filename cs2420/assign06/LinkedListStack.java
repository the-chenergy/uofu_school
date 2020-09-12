package assign06;

import java.util.NoSuchElementException;

/**
 * A stack data structure implemented with a <code>LinkedList</code>.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version 2/22/19
 * 
 * @param <E> The type of the data stored in this stack.
 */
public class LinkedListStack<E> implements Stack<E>
{
	/**
	 * The linked list to store the data in this stack.
	 */
	private SinglyLinkedList<E> stack;
	
	/**
	 * Creates a new <code>LinkedListStack</code> instance.
	 */
	public LinkedListStack()
	{
		stack = new SinglyLinkedList<E>();
	}
	
	/**
	 * Removes all of the elements from the stack.
	 */
	@Override
	public void clear()
	{
		stack.clear();
	}
	
	/**
	 * Returns whether the stack is empty.
	 * 
	 * @return true if the stack contains no elements; false, otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	/**
	 * Returns, but does not remove, the element at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	@Override
	public E peek() throws NoSuchElementException
	{
		return stack.getFirst();
	}
	
	/**
	 * Returns and removes the item at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	@Override
	public E pop() throws NoSuchElementException
	{
		return stack.removeFirst();
	}
	
	/**
	 * Adds a given element to the stack, putting it at the top of the stack.
	 * 
	 * @param element - the element to be added
	 */
	@Override
	public void push(E element)
	{
		stack.addFirst(element);
	}
	
	/**
	 * Returns the number of elements in this stack.
	 * 
	 * @return the number of elements in the stack
	 */
	@Override
	public int size()
	{
		return stack.size();
	}
}
