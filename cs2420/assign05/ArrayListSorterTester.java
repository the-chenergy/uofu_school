package assign05;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for <code>ArrayListSorter</code> class.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version February 9, 2019
 */
class ArrayListSorterTester
{
	@Test
	void testGenerateAscending()
	{
		ArrayList<Integer> result = ArrayListSorter.generateAscending(52);
		
		for (int i = 0; i < 52; i++)
			assertEquals(new Integer(i + 1), result.get(i));
	}
	
	@Test
	void testGenerateAscendingEmptyList()
	{
		ArrayList<Integer> result = ArrayListSorter.generateAscending(0); // check if any errors are thrown
		
		assertEquals(0, result.size());
	}
	
	@Test
	void testGeneratePermuted()
	{
		ArrayList<Integer> result = ArrayListSorter.generatePermuted(52);
		
		// The elements in the sorted version of a permuted list must be in ascending order.
		
		result.sort(null); // the native sort function is guaranteed to work
		
		for (int i = 0; i < 52; i++)
			assertEquals(new Integer(i + 1), result.get(i));
	}
	
	@Test
	void testGeneratePermutedEmptyList()
	{
		ArrayList<Integer> result = ArrayListSorter.generatePermuted(0);
		
		assertEquals(0, result.size());
	}
	
	@Test
	void testGenerateDescending()
	{
		ArrayList<Integer> result = ArrayListSorter.generateDescending(52);
		
		for (int i = 0; i < 52; i++)
			assertEquals(new Integer(52 - i), result.get(i));
	}
	
	@Test
	void testGenerateDescendingEmptyList()
	{
		ArrayList<Integer> result = ArrayListSorter.generateDescending(0);
		
		assertEquals(0, result.size());
	}
	
	@Test
	void testMergesort()
	{
		ArrayList<Integer> list = ArrayListSorter.generatePermuted(52);
		
		ArrayListSorter.mergesort(list);
		
		for (int i = 0; i < 52; i++)
			assertEquals(new Integer(i + 1), list.get(i));
	}
	
	@Test
	void testMergesortEmptyList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		ArrayListSorter.mergesort(list); // check if any errors are thrown
		
		assertEquals(0, list.size());
	}
	
	@Test
	void testMergesortSingleElementList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(52);
		
		ArrayListSorter.mergesort(list); // check if any errors are thrown
		
		assertEquals(1, list.size());
		assertEquals(new Integer(52), list.get(0));
	}
	
	@Test
	void testMergesortListWithDuplicates()
	{
		ArrayList<Integer> list = new ArrayList<Integer>(
			Arrays.asList(52, 13, 8, 42, 52, 13, 8, 54, 7)
		);
		
		ArrayListSorter.mergesort(list);
		
		ArrayList<Integer> control = new ArrayList<Integer>(
			Arrays.asList(7, 8, 8, 13, 13, 42, 52, 52, 54)
		);
		
		assertEquals(control, list); // Java implemented ArrayList.equals() method to compare each element
	}
	
	@Test
	void testMergesortStringList()
	{
		ArrayList<String> list = new ArrayList<String>(
			Arrays.asList("Spade", "Heart", "Club", "Diamond", "Ace", "Jack", "Queen", "King")
		);
		
		ArrayListSorter.mergesort(list);
		
		ArrayList<String> control = new ArrayList<String>(
			Arrays.asList("Ace", "Club", "Diamond", "Heart", "Jack", "King", "Queen", "Spade")
		);
		
		assertEquals(control, list);
	}
	
	@Test
	void testQuicksort()
	{
		ArrayList<Integer> list = ArrayListSorter.generatePermuted(52);
		
		ArrayListSorter.quicksort(list);
		
		for (int i = 0; i < 52; i++)
			assertEquals(new Integer(i + 1), list.get(i));
	}
	
	@Test
	void testQuicksortEmptyList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		ArrayListSorter.quicksort(list); // check if any errors are thrown
		
		assertEquals(0, list.size());
	}
	
	@Test
	void testQuicksortSingleElementList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(52);
		
		ArrayListSorter.quicksort(list); // check if any errors are thrown
		
		assertEquals(1, list.size());
		assertEquals(new Integer(52), list.get(0));
	}
	
	@Test
	void testQuicksortListWithDuplicates()
	{
		ArrayList<Integer> list = new ArrayList<Integer>(
			Arrays.asList(52, 13, 8, 42, 52, 13, 8, 54, 7)
		);
		
		ArrayListSorter.quicksort(list);
		
		ArrayList<Integer> control = new ArrayList<Integer>(
			Arrays.asList(7, 8, 8, 13, 13, 42, 52, 52, 54)
		);
		
		assertEquals(control, list);
	}
	
	@Test
	void testQuicksortStringList()
	{
		ArrayList<String> list = new ArrayList<String>(
			Arrays.asList("Spade", "Heart", "Club", "Diamond", "Ace", "Jack", "Queen", "King")
		);
		
		ArrayListSorter.quicksort(list);
		
		ArrayList<String> control = new ArrayList<String>(
			Arrays.asList("Ace", "Club", "Diamond", "Heart", "Jack", "King", "Queen", "Spade")
		);
		
		assertEquals(control, list);
	}
}
