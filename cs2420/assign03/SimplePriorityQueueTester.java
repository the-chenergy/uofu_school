package assign03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A test case for the <code>SimplePriorityQueue</code> class.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version January 25, 2019
 */
class SimplePriorityQueueTester
{
	/**
	 * A <code>SimplePriorityQueue</code> of integers.
	 */
	private SimplePriorityQueue<Integer> intQueue;
	
	/**
	 * A <code>SimplePriorityQueue</code> of integers with a customized comparator that sorts the
	 * integers from greatest to smallest.
	 */
	private SimplePriorityQueue<Integer> intQueueWithComparator;
	
	/**
	 * A <code>SimplePriorityQueue</code> of strings.
	 */
	private SimplePriorityQueue<String> stringQueue;
	
	/**
	 * A <code>SimplePriorityQueue</code> of strings with a customized comparator that sorts the
	 * strings by their lengths, smallest to greatest.
	 */
	private SimplePriorityQueue<String> stringQueueWithComparator;
	
	/**
	 * An empty <code>SimplePriorityQueue</code> of strings.
	 */
	private SimplePriorityQueue<String> emptyStringQueue;
	
	@BeforeEach
	void setUp()
	{
		// Initialize our integer queues with the first 16 primes in random order.
		
		intQueue = new SimplePriorityQueue<Integer>();
		intQueueWithComparator = new SimplePriorityQueue<Integer>(
			(int1, int2) -> (int2 - int1) // compare the integers in reversed order
		);
		
		for (Integer i : new Integer[] {2, 23, 47, 5, 3, 19, 37, 13, 7, 29, 53, 31, 11, 43, 41, 17})
		{
			intQueue.insert(i);
			intQueueWithComparator.insert(i);
		}
		
		// Initialize our string queues with 7 words in random order.
		
		stringQueue = new SimplePriorityQueue<String>();
		stringQueueWithComparator = new SimplePriorityQueue<String>(
			(string1, string2) -> (string1.length() - string2.length()) // compare the lengths
		);
		
		for (String string : new String[] {"Eclipse", "File", "Edit", "Source", "Navigate", "Project", "Run"})
		{
			stringQueue.insert(string);
			stringQueueWithComparator.insert(string);
		}
		
		// Initialize our empty string queue with nothing.
		
		emptyStringQueue = new SimplePriorityQueue<String>();
	}
	
	/**
	 * Ensures that removing from an empty queue throws an exception
	 */
	@Test
	public void emptyQueueException()
	{
		assertThrows(NoSuchElementException.class, () -> emptyStringQueue.deleteMin());
	}
	
	/**
	 * Ensures that removing an integer works properly under normal circumstances
	 */
	@Test
	public void removeInt()
	{
		int size1 = intQueue.size();
		int placeHolder = intQueue.deleteMin();
		int size2 = intQueue.size();
		assertEquals(size1 - 1, size2);
		// To keep the queue the same for future tests
		intQueue.insert(placeHolder);
		size2 = intQueue.size();
		assertEquals(size1, size2);
		assertEquals(2, placeHolder);
	}
	
	/**
	 * Ensures that insert works properly under normal circumstances The test does this by adding a
	 * bunch of numbers, then deleting one at a time to check the order Note that a failure here may
	 * explain possible failures in other tests
	 */
	@Test
	public void insertInt()
	{
		SimplePriorityQueue<Integer> queue = new SimplePriorityQueue<Integer>();
		queue.insert(3);
		queue.insert(7);
		queue.insert(23);
		queue.insert(9);
		queue.insert(3);
		queue.insert(5);
		queue.insert(84);
		queue.insert(4);
		queue.insert(5);
		queue.insert(64);
		int i = queue.deleteMin();
		assertEquals(3, i);
		i = queue.deleteMin();
		assertEquals(3, i);
		i = queue.deleteMin();
		assertEquals(4, i);
		i = queue.deleteMin();
		assertEquals(5, i);
		i = queue.deleteMin();
		assertEquals(5, i);
		i = queue.deleteMin();
		assertEquals(7, i);
		i = queue.deleteMin();
		assertEquals(9, i);
		i = queue.deleteMin();
		assertEquals(23, i);
		i = queue.deleteMin();
		assertEquals(64, i);
		i = queue.deleteMin();
		assertEquals(84, i);
	}
	
	/**
	 * Ensures that removing an integer with a comparator works properly
	 */
	@Test
	public void removeIntComparator()
	{
		int size1 = intQueueWithComparator.size();
		int placeHolder = intQueueWithComparator.deleteMin();
		int size2 = intQueueWithComparator.size();
		assertEquals(size1 - 1, size2);
		// To keep the queue the same for future tests
		intQueueWithComparator.insert(placeHolder);
		size2 = intQueueWithComparator.size();
		assertEquals(size1, size2);
		assertEquals(53, placeHolder);
	}
	
	/**
	 * Ensures that removing a string with a comparator works properly
	 */
	@Test
	public void removeString()
	{
		int size1 = stringQueue.size();
		String placeHolder = stringQueue.deleteMin();
		int size2 = stringQueue.size();
		assertEquals(size1 - 1, size2);
		// To keep the queue the same for future tests
		stringQueue.insert(placeHolder);
		size2 = stringQueue.size();
		assertEquals(size1, size2);
		assertEquals("Eclipse", placeHolder);
	}
	
	/**
	 * Ensures that removing a string with a comparator works properly
	 */
	@Test
	public void removeStringWithComparator()
	{
		int size1 = stringQueueWithComparator.size();
		String placeHolder = stringQueueWithComparator.deleteMin();
		int size2 = stringQueueWithComparator.size();
		assertEquals(size1 - 1, size2);
		// To keep the queue the same for future tests
		stringQueueWithComparator.insert(placeHolder);
		size2 = stringQueueWithComparator.size();
		assertEquals(size1, size2);
		assertEquals("Run", placeHolder);
	}
	
	/**
	 * Ensures that findMin works properly with integers
	 */
	@Test
	public void findInt()
	{
		int i = intQueue.findMin();
		assertEquals(2, i);
	}
	
	/**
	 * Ensures that findMin works properly with integers and a comparator
	 */
	@Test
	public void findIntComp()
	{
		int i = intQueueWithComparator.findMin();
		assertEquals(53, i);
	}
	
	/**
	 * Ensures that findMin works properly with integers
	 */
	@Test
	public void findString()
	{
		String i = stringQueue.findMin();
		assertEquals("Eclipse", i);
	}
	
	/**
	 * Ensures that findMin works properly with integers
	 */
	@Test
	public void findStringComp()
	{
		String i = stringQueueWithComparator.findMin();
		assertEquals("Run", i);
	}
	
	/**
	 * Ensures that isEmpty behaves properly
	 */
	@Test
	public void isEmpty()
	{
		assertTrue(emptyStringQueue.isEmpty());
		assertFalse(intQueue.isEmpty());
		assertFalse(stringQueue.isEmpty());
	}
	
	/**
	 * Ensures that clear behaves properly
	 */
	@Test
	public void clear()
	{
		emptyStringQueue.insert("a");
		emptyStringQueue.insert("c");
		emptyStringQueue.insert("b");
		emptyStringQueue.clear();
		assertThrows(NoSuchElementException.class, () -> emptyStringQueue.deleteMin());
	}
	
	/**
	 * Ensures that insertAll behaves properly, using an arraylist as an example collection
	 */
	@Test
	public void insertAll()
	{
		ArrayList<Integer> queue = new ArrayList<Integer>();
		queue.add(7);
		queue.add(8);
		queue.add(4);
		queue.add(35);
		SimplePriorityQueue<Integer> temp = new SimplePriorityQueue<Integer>();
		temp.insertAll(queue);
		int i = temp.deleteMin();
		assertEquals(4, i);
		i = temp.deleteMin();
		assertEquals(7, i);
		i = temp.deleteMin();
		assertEquals(8, i);
		i = temp.deleteMin();
		assertEquals(35, i);
		assertThrows(NoSuchElementException.class, () -> temp.deleteMin());
	}
}
