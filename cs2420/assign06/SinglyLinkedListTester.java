package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * A series of tests for the SinglyLinkedList class.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version 2/26/19
 */
class SinglyLinkedListTester
{
	@Test
	void addFirstTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		assertEquals(1, list.getFirst().intValue());
		
		list.addFirst(2);
		assertEquals(2, list.getFirst().intValue());
	}
	
	@Test
	void addTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(2);
		
		list.add(2, 7);
		assertEquals(7, list.get(2).intValue());
		
		list.add(1, 13);
		assertEquals(13, list.get(1).intValue());
		
		list.add(0, 123456789);
		assertEquals(123456789, list.getFirst().intValue());
		
		assertEquals(7, list.get(4).intValue());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(123456789, 123456789));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-123456789, 123456789));
	}
	
	@Test
	void getFirstTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> list.getFirst());
		
		list.addFirst(1);
		assertEquals(1, list.getFirst().intValue());
		
		list.addFirst(2);
		assertEquals(2, list.getFirst().intValue());
	}
	
	@Test
	void getTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(2);
		list.addFirst(3);
		list.addFirst(4);
		list.addFirst(5);
		list.addFirst(13);
		
		Integer[] control = {13, 5, 4, 3, 2, 1};
		
		for (int i = 0; i < list.size(); i++)
			assertEquals(control[i], list.get(i));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(6));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-6));
	}
	
	@Test
	void removeFirstTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(2);
		list.addFirst(3);
		list.addFirst(4);
		list.addFirst(5);
		list.addFirst(13);
		
		assertEquals(13, list.removeFirst().intValue());
		assertEquals(5, list.removeFirst().intValue());
		assertEquals(4, list.removeFirst().intValue());
		assertEquals(3, list.removeFirst().intValue());
		assertEquals(2, list.removeFirst().intValue());
		assertEquals(1, list.removeFirst().intValue());
		
		assertThrows(NoSuchElementException.class, () -> list.removeFirst());
	}
	
	@Test
	void removeTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(2);
		list.addFirst(3);
		list.addFirst(4);
		list.addFirst(5);
		list.addFirst(13);
		
		assertEquals(13, list.remove(0).intValue());
		assertEquals(5, list.remove(0).intValue());
		assertEquals(3, list.remove(1).intValue());
		assertEquals(4, list.removeFirst().intValue());
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(6));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-6));
	}
	
	@Test
	void indexOfTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(2);
		list.addFirst(3);
		list.addFirst(4);
		list.addFirst(5);
		list.addFirst(13);
		
		assertEquals(0, list.indexOf(13));
		assertEquals(1, list.indexOf(5));
		assertEquals(2, list.indexOf(4));
		assertEquals(3, list.indexOf(3));
		assertEquals(4, list.indexOf(2));
		assertEquals(5, list.indexOf(1));
		assertEquals(-1, list.indexOf(52));
	}
	
	@Test
	void sizeTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		assertEquals(0, list.size());
		
		list.addFirst(1);
		list.addFirst(2);
		list.add(1, 3);
		assertEquals(3, list.size());
		
		list.removeFirst();
		assertEquals(2, list.size());
		
		list.remove(1);
		assertEquals(1, list.size());
		
		list.removeFirst();
		assertEquals(0, list.size());
	}
	
	@Test
	void isEmptyTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		assertTrue(list.isEmpty());
		
		list.addFirst(1);
		assertFalse(list.isEmpty());
		
		list.removeFirst();
		assertTrue(list.isEmpty());
	}
	
	@Test
	void clearTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(7);
		list.clear();
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void toArrayTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(1);
		list.addFirst(7);
		list.addFirst(13);
		
		Object[] temp = list.toArray();
		Integer[] control = {13, 7, 1};
		
		assertTrue(Arrays.deepEquals(temp, control));
	}
	
	@Test
	void iteratorTest()
	{
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		list.addFirst(52);
		list.addFirst(13);
		list.addFirst(7);
		list.addFirst(1);
		
		Iterator<Integer> iterator = list.iterator();
		
		assertThrows(IllegalStateException.class, () -> iterator.remove()); // no removing before next is called
		
		assertEquals(1, iterator.next().intValue());
		iterator.remove(); // remove 1
		assertThrows(IllegalStateException.class, () -> iterator.remove()); // no removing twice in a row
		assertEquals(7, iterator.next().intValue());
		iterator.remove(); // remove 7
		assertThrows(IllegalStateException.class, () -> iterator.remove());
		assertEquals(13, iterator.next().intValue());
		assertEquals(52, iterator.next().intValue());
		assertThrows(NoSuchElementException.class, () -> iterator.next()); // the end is reached
		iterator.remove(); // remove 52
		assertThrows(IllegalStateException.class, () -> iterator.remove());
		
		assertEquals(13, list.getFirst().intValue()); // 13 should be the only one left in the list
		assertEquals(1, list.size());
	}
}
