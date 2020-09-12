package demos.structures;

import java.util.NoSuchElementException;

/**
 * Explores how a <code>LinkedList</code> works.
 * 
 * @author Qianlang Chen
 * @version M 02/18/19
 */
public class LinkedListDemo
{
	/** ... **/
	public static void main(String[] args)
	{
		SimpleLinkedList<String> list = new SimpleLinkedList<>();
		
		System.out.println(list.size() + ", " + list);
		
		list.unshift("Refactor");
		
		System.out.println(list.size() + ", " + list);
		
		list.unshift("Edit");
		
		System.out.println(list.size() + ", " + list);
		
		list.push("Navigate");
		
		System.out.println(list.size() + ", " + list);
		
		list.insert(1, "Source");
		
		System.out.println(list.size() + ", " + list);
		
		list.insert(0, "File");
		
		System.out.println(list.size() + ", " + list);
		
		System.out.println(list.pop());
		
		System.out.println(list.shift());
		
		System.out.println(list.remove(3));
		
		System.out.println(list.size() + ", " + list);
	}
	
	/**
	 * Represents a singly-linked list.
	 *
	 * @param <E> The type of data stored in this linked list.
	 */
	private static class SimpleLinkedList<E>
	{
		/** Creates a new <code>SimpleLinkedList</code> instance. **/
		public SimpleLinkedList()
		{
		}
		
		/** The head node of this linked list. **/
		private Node<E> head;
		
		/** The number of items in this linked list. **/
		private int numElements;
		
		/**
		 * Returns the number of items in this linked list.
		 * 
		 * @return The size of this linked list.
		 */
		public int size()
		{
			return numElements;
		}
		
		/**
		 * Inserts an item at the beginning of the linked list.
		 * 
		 * @param item The item to insert.
		 */
		public void unshift(E item)
		{
			if (numElements == 0)
				head = new Node<E>(item, null);
			else
				head = new Node<E>(item, head.next);
			
			numElements++;
		}
		
		/**
		 * Removes the first item in the linked list and returns that item.
		 * 
		 * @return The first item in this linked list.
		 * @throws NoSuchElementException When this linked list is empty.
		 */
		public E shift() throws NoSuchElementException
		{
			if (numElements == 0)
				throw new NoSuchElementException("This linked list is empty.");
			
			E temp = head.data;
			head = head.next;
			
			numElements--;
			
			return temp;
		}
		
		/**
		 * Inserts an item at a specific index in this linked list.
		 * 
		 * @param index The index to insert.
		 * @param item  The item to insert.
		 * @throws IndexOutOfBoundsException When the index to insert is invalid.
		 */
		public void insert(int index, E item) throws IndexOutOfBoundsException
		{
			if (index == 0)
			{
				unshift(item);
				
				return;
			}
			
			Node<E> temp = getPrevNode(index);
			System.out.println(index + "!!!");
			temp.next = new Node<E>(item, temp.next);
			
			numElements++;
		}
		
		/**
		 * Adds an item at the end of this linked list.
		 * 
		 * @param item The item to add.
		 */
		public void push(E item)
		{
			insert(numElements, item);
		}
		
		/**
		 * Removes the last item in this linked list and returns that item.
		 * 
		 * @return The last item in this linked list.
		 * @throws NoSuchElementException When this linked list is empty.
		 */
		public E pop() throws NoSuchElementException
		{
			if (numElements == 0)
				throw new NoSuchElementException("This linked list is empty");
			
			return remove(numElements - 1);
		}
		
		/**
		 * Removes an item at a specific index and returns that item.
		 * 
		 * @param index The index to remove.
		 * @return The item at that index.
		 * @throws IndexOutOfBoundsException When the index to remove is invalid.
		 */
		public E remove(int index) throws IndexOutOfBoundsException
		{
			if (index == 0 && numElements > 0)
				return shift();
			
			Node<E> temp = getPrevNode(index);
			
			temp.next = temp.next.next;
			
			numElements--;
			
			return temp.data;
		}
		
		/**
		 * Returns the node before index.
		 * 
		 * @param index The index to get.
		 * @return The node before the index.
		 */
		private Node<E> getPrevNode(int index) throws IndexOutOfBoundsException
		{
			if (index <= 0 || index > numElements)
				throw new IndexOutOfBoundsException("The index is invalid.");
			
			Node<E> temp = head;
			
			while (--index > 1)
				temp = temp.next;
			
			return temp;
		}
		
		/**
		 * Returns the String representation of this linked list.
		 * 
		 * @return The String representation of this linked list.
		 */
		public String toString()
		{
			if (numElements == 0)
				return "{}";
			
			StringBuilder string = new StringBuilder();
			Node<E> temp = head;
			
			while (temp != null)
			{
				string.append(temp.data + ", ");
				
				temp = temp.next;
			}
			
			return "{" + string.substring(0, string.length() - 2) + "}";
		}
		
		/**
		 * Represents a node in the linked list.
		 *
		 * @param <T> The type of data stored in the node.
		 */
		private class Node<T>
		{
			/**
			 * Creates a new <code>Node</code> instance.
			 * 
			 * @param data The data to store in this node.
			 * @param next The next node.
			 */
			public Node(T data, Node<T> next)
			{
				this.data = data;
				this.next = next;
			}
			
			/** The data stored in this node. **/
			public T data;
			
			/** The next node that this node is connected to. **/
			public Node<T> next;
		}
	}
}
