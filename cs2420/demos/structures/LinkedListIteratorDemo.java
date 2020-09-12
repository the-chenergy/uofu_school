package demos.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Explores how an iterator would work for a linked list.
 * 
 * @author  Qianlang Chen
 * @version S 02/24/19
 */
public class LinkedListIteratorDemo
{
	/** .. **/
	public static void main(String[] args)
	{
		System.out.println(new LL<>());
		System.out.println(new LL<>(2));
		System.out.println(new LL<>(2, 3, 5, 7, 11, 13, 17, 19));
		System.out.println("------------");
		
		LL<Integer> ll = new LL<>(0, 1, 2, 3, 4, 5);
		Iterator<Integer> lli = ll.iterator();
		
		System.out.println(ll); // .012345
		lli.remove(); // fails
		System.out.println(lli.next()); // 0.12345
		lli.remove(); // removes 0
		System.out.println(ll); // .12345
		System.out.println(lli.next()); // 1.2345
		System.out.println(lli.next()); // 12.345
		lli.remove(); // removes 2
		System.out.println(ll); // 1.345
		System.out.println(lli.next()); // 13.45
		lli.remove(); // removes 3
		System.out.println(ll); // 125.
		lli.remove(); // removes 4
		System.out.println(ll); // 12 .
		lli.remove();
		System.out.println(ll);
	}
	
	private static class LL<E> implements Iterable<E>
	{
		public LL(E ...args)
		{
			for (int i = args.length - 1; i >= 0; i--)
				head = new Node<E>(args[i], head);
			
			size = args.length;
		}
		
		private Node<E> head;
		
		private int size;
		
		@Override
		public String toString()
		{
			if (head == null)
				return "[0]{}";
			
			StringBuilder string = new StringBuilder();
			
			for (E element : this)
				string.append(element.toString() + ", ");
			
			return "[" + size + "]{" + string.substring(0, string.length() - 2) + "}";
		}
		
		@Override
		public Iterator<E> iterator()
		{
			return new LLIterator<>();
		}
		
		private class Node<T>
		{
			public Node(T data, Node<T> next)
			{
				this.data = data;
				this.next = next;
			}
			
			private T data;
			
			private Node<T> next;
			
			@Override
			public String toString()
			{
				return data.toString();
			}
		}
		
		private class LLIterator<T> implements Iterator<T>
		{
			private Node<T> curr = (Node<T>)head, prev;
			
			@Override
			public boolean hasNext()
			{
				return curr != null;
			}
			
			@Override
			public T next()
			{
				if (!hasNext())
				{
					System.out.println("NO NEXT!!");
					
					return null;
				}
				
				if (prev == null && curr != head)
					prev = (Node<T>)head;
				else if (prev != null)
					prev = prev.next;
				
				try
				{
					return curr.data;
				}
				finally
				{
					curr = curr.next;
				}
			}
			
			public void remove()
			{
				if (curr == head)
				{
					System.out.println("CANNOT REMOVE!!");
					
					return;
				}
				
				if (prev == null)
					head = head.next;
				else
				{
					prev.next = prev.next.next;
					curr = prev;
				}
				
				size--;
			}
		}
	}
}
