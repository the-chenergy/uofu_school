package assign10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>BinaryMaxHeap</code> class.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version April 6, 2019
 */
class BinaryMaxHeapTest
{
	@Test
	void testConstructor()
	{
		List<Integer> toAdd = generateIntegerList();
		
		// check if buildHeap() works properly
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>(toAdd);
		assertEquals(toAdd.size(), heap.size());
		
		toAdd.sort(null);
		for (int i = toAdd.size() - 1; i >= 0; i--)
			assertEquals(toAdd.get(i), heap.extractMax());
	}
	
	@Test
	void testConstructorWithComparator()
	{
		List<String> toAdd = generateStringList();
		
		// check if buildHeap() works properly
		BinaryMaxHeap<String> heap = new BinaryMaxHeap<>(toAdd, STRING_COMPARATOR);
		assertEquals(toAdd.size(), heap.size());
		
		toAdd.sort(STRING_COMPARATOR);
		for (int i = toAdd.size() - 1; i >= 0; i--)
			assertEquals(toAdd.get(i).length(), heap.extractMax().length());
	}
	
	@Test
	void testAdd()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
		List<Integer> toAdd = generateIntegerList();
		
		for (int i = 0; i < toAdd.size(); i++)
		{
			heap.add(toAdd.get(i));
			assertEquals(i + 1, heap.size());
			
			// check if the heap actually contains the newly added item
			assertTrue(Arrays.asList(heap.toArray()).contains(toAdd.get(i)));
		}
	}
	
	@Test
	void testPeek()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
		List<Integer> toAdd = generateIntegerList();
		List<Integer> added = new ArrayList<>();
		
		for (int i = 0; i < toAdd.size(); i++)
		{
			heap.add(toAdd.get(i));
			
			added.add(toAdd.get(i));
			added.sort(null);
			assertEquals(added.get(i), heap.peek());
		}
	}
	
	@Test
	void testPeekWithComparator()
	{
		BinaryMaxHeap<String> heap = new BinaryMaxHeap<>(STRING_COMPARATOR);
		List<String> toAdd = generateStringList();
		List<String> added = new ArrayList<>();
		
		for (int i = 0; i < toAdd.size(); i++)
		{
			heap.add(toAdd.get(i));
			
			added.add(toAdd.get(i));
			added.sort(STRING_COMPARATOR);
			assertEquals(added.get(i).length(), heap.peek().length());
		}
	}
	
	@Test
	void testPeekWithEmptyHeap()
	{
		assertThrows(NoSuchElementException.class, () -> new BinaryMaxHeap<>().peek());
	}
	
	@Test
	void testExtractMax()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>();
		List<Integer> toAdd = generateIntegerList();
		
		for (Integer i : toAdd)
			heap.add(i);
		
		toAdd.sort(null);
		for (int i = toAdd.size() - 1; i >= 0; i--)
		{
			assertEquals(toAdd.get(i), heap.extractMax());
			assertEquals(i, heap.size());
		}
	}
	
	@Test
	void testExtractMaxWithComparator()
	{
		BinaryMaxHeap<String> heap = new BinaryMaxHeap<>(STRING_COMPARATOR);
		List<String> toAdd = generateStringList();
		
		for (String string : toAdd)
			heap.add(string);
		
		toAdd.sort(STRING_COMPARATOR);
		for (int i = toAdd.size() - 1; i >= 0; i--)
		{
			assertEquals(toAdd.get(i).length(), heap.extractMax().length());
			assertEquals(i, heap.size());
		}
	}
	
	@Test
	void testExtractMaxWithEmptyHeap()
	{
		assertThrows(NoSuchElementException.class, () -> new BinaryMaxHeap<>().extractMax());
	}
	
	@Test
	void testClear()
	{
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>(generateIntegerList());
		heap.clear();
		assertTrue(heap.isEmpty());
		assertEquals(0, heap.size());
		
		// check if the heap still works properly after clearing
		List<Integer> toAdd = generateIntegerList();
		List<Integer> added = new ArrayList<>();
		for (int i = 0; i < toAdd.size(); i++)
		{
			heap.add(toAdd.get(i));
			assertEquals(i + 1, heap.size());
			
			added.add(toAdd.get(i));
			added.sort(null);
			assertEquals(added.get(i), heap.peek());
		}
	}
	
	@Test
	void testSize()
	{
		assertEquals(0, new BinaryMaxHeap<>().size());
		assertEquals(generateIntegerList().size(), new BinaryMaxHeap<>(generateIntegerList()).size());
		assertEquals(generateStringList().size(), new BinaryMaxHeap<>(generateStringList(), STRING_COMPARATOR).size());
	}
	
	@Test
	void testIsEmpty()
	{
		assertTrue(new BinaryMaxHeap<>().isEmpty());
		assertFalse(new BinaryMaxHeap<>(generateIntegerList()).isEmpty());
		assertFalse(new BinaryMaxHeap<>(generateStringList(), STRING_COMPARATOR).isEmpty());
	}
	
	@Test
	void testToArray()
	{
		List<Integer> toAdd = generateIntegerList();
		BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>(toAdd);
		
		assertEquals(toAdd.size(), heap.size());
		for (Object i : heap.toArray())
			assertTrue(toAdd.contains(i));
	}
	
	@Test
	void testToArrayWithComparator()
	{
		List<String> toAdd = generateStringList();
		BinaryMaxHeap<String> heap = new BinaryMaxHeap<>(toAdd, STRING_COMPARATOR);
		
		assertEquals(toAdd.size(), heap.size());
		for (Object string : heap.toArray())
			assertTrue(toAdd.contains(string));
	}
	
	@Test
	void testToArrayWithEmptyHeap()
	{
		assertEquals(0, new BinaryMaxHeap<>().toArray().length);
	}
	
	/** The comparator used to order strings by their lengths. **/
	private final Comparator<String> STRING_COMPARATOR = (s1, s2) -> s1.length() - s2.length();
	
	/** Generates a list with a lot of random integers. **/
	private List<Integer> generateIntegerList()
	{
		List<Integer> list = new ArrayList<>();
		
		int size = 1_000;
		for (int i = 0; i < size; i++)
			list.add((int)(Math.random() * size));
		
		return list;
	}
	
	/** Generates a list with a lot of random strings with various lengths. **/
	private List<String> generateStringList()
	{
		List<String> list = new ArrayList<>();
		
		int size = 1_000;
		for (int i = 0; i < size; i++)
		{
			StringBuilder string = new StringBuilder();
			for (int length = (int)(Math.random() * size); length > 0; length--)
				string.append((char)(Math.random() * 26 + 65)); // a random upper-case letter
				
			list.add(string.toString());
		}
		
		return list;
	}
}
