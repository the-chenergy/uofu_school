package assign04;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests for the <code>AnagramChecker</code> class
 * 
 * @author Qianlang Chen and Brandon Walton
 * @version February 1, 2019
 */
class AnagramCheckerTests
{
	@Test
	void testSort()
	{
		String string = "Hello World";
		assertEquals(" HWdellloor", AnagramChecker.sort(string));
	}
	
	@Test
	void testSortEmpty()
	{
		String string = "";
		assertEquals("", AnagramChecker.sort(string));
	}
	
	@Test
	void testSortByCompTrue()
	{
		Integer[] array = {3, 6, 9, 2, 5, 8, 1, 4, 7};
		Integer[] control = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		AnagramChecker.insertionSort(array, (i1, i2) -> i1 - i2);
		assertTrue(Arrays.deepEquals(control, array));
	}
	
	@Test
	void testSortByCompFalse()
	{
		Integer[] array = {3, 6, 999, 2, 5, 8, 1, 4, 7};
		Integer[] control = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		AnagramChecker.insertionSort(array, (int1, int2) -> int1 - int2);
		assertFalse(Arrays.deepEquals(control, array));
	}
	
	@Test
	void testAreAnagrams()
	{
		assertTrue(AnagramChecker.areAnagrams("Cat", "Act"));
		assertTrue(AnagramChecker.areAnagrams("cat", "act"));
		assertFalse(AnagramChecker.areAnagrams("Brandon", "Qianlang"));
	}
	
	@Test
	void testLargestAnagramGroup()
	{
		String[] array = {"cat", "act", "DoG", "CAT", "Eat", "Ate"};
		String[] control = {"cat", "act", "CAT"};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(array)));
	}
	
	@Test
	void testLargestAnagramGroupEnd()
	{
		String[] array = {"abc", "123", "cab", "312", "acb"};
		String[] control = {"abc", "cab", "acb"};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(array)));
	}
	
	@Test
	void testLargestAnagramGroupOneElement()
	{
		String[] array = {"cat"};
		String[] control = {};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(array)));
	}
	
	@Test
	void testLargestAnagramGroupTwoElements()
	{
		String[] array = {"cat", "dog"};
		String[] control = {};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(array)));
	}
	
	@Test
	void testLargestAnagramGroupWithFile()
	{
		String filename = "src/assign04/sample_word_list.txt";
		String[] control = {"carets", "Caters", "caster", "crates", "Reacts", "recast", "traces"};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(filename)));
	}
	
	@Test
	void testLargestAnagramInvalidFile()
	{
		String filename = "thisisinvalid";
		String[] control = {};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(filename)));
	}
	
	@Test
	void testLargestAnagramEmptyFile()
	{
		String filename = "src/assign04/empty_file.txt";
		String[] control = {};
		assertTrue(Arrays.deepEquals(control, AnagramChecker.getLargestAnagramGroup(filename)));
	}
}
