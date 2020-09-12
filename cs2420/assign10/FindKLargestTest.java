package assign10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>FindKLargest</code> class.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version April 6, 2019
 */
class FindKLargestTest
{
	@Test
	void testFindKLargestHeap()
	{
		List<Integer> toFind = generateIntegerList();
		List<Integer> control = new ArrayList<Integer>(toFind);
		control.sort(Collections.reverseOrder());
		
		for (int k = 0; k <= toFind.size(); k++)
			assertEquals(control.subList(0, k), FindKLargest.findKLargestHeap(toFind, k));
	}
	
	@Test
	void testFindKLargestHeapWithComparator()
	{
		List<String> toFind = generateStringList();
		List<String> control = new ArrayList<String>(toFind);
		control.sort(Collections.reverseOrder(STRING_COMPARATOR));
		
		for (int k = 0; k <= toFind.size(); k++)
		{
			List<String> expected = control.subList(0, k);
			List<String> actual = FindKLargest.findKLargestHeap(toFind, k, STRING_COMPARATOR);
			assertEquals(k, actual.size());
			
			for (int i = 0; i < k; i++)
				assertEquals(expected.get(i).length(), actual.get(i).length());
		}
	}
	
	@Test
	void testFindKLargestSort()
	{
		List<Integer> toFind = generateIntegerList();
		List<Integer> control = new ArrayList<Integer>(toFind);
		control.sort(Collections.reverseOrder());
		
		for (int k = 0; k <= toFind.size(); k++)
			assertEquals(control.subList(0, k), FindKLargest.findKLargestSort(toFind, k));
	}
	
	@Test
	void testFindKLargestSortWithComparator()
	{
		List<String> toFind = generateStringList();
		List<String> control = new ArrayList<String>(toFind);
		control.sort(Collections.reverseOrder(STRING_COMPARATOR));
		
		for (int k = 0; k <= toFind.size(); k++)
		{
			List<String> expected = control.subList(0, k);
			List<String> actual = FindKLargest.findKLargestSort(toFind, k, STRING_COMPARATOR);
			assertEquals(k, actual.size());
			
			for (int i = 0; i < k; i++)
				assertEquals(expected.get(i).length(), actual.get(i).length());
		}
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
