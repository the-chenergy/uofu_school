package others.ft.bladethetitan.assignment2;

import static org.junit.Assert.*;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

public class SetOnArrayListTest
{
	/**
	 * Creates and returns a Set under test, i.e. a set with dynamic type SetOnArrayList, populated with the given words.
	 * 
	 * @param words Strings to populate the set with
	 * @return generated set with the proper dynamic type
	 */
	private Set<String> createFromArgsUUT(String ...words)
	{
		Set<String> s = new SetOnArrayList<>();
		for (String w : words)
		{
			if (!s.contains(w))
			{
				s.add(w);
			}
		}
		return s;
	}
	
	/**
	 * Creates and returns a Set for reference, i.e. a set with dynamic type Set1L, populated with the given words.
	 * 
	 * @param words Strings to populate the set with
	 * @return generated set with the proper dynamic type
	 */
	private Set<String> createFromArgsRef(String ...words)
	{
		Set<String> s = new Set1L<>();
		for (String w : words)
		{
			if (!s.contains(w))
			{
				s.add(w);
			}
		}
		return s;
	}
	
	@Test
	public final void testAddToEmpty()
	{
		// Creates and empty set s (SetOnArrayList)
		Set<String> s = createFromArgsUUT();
		// Adds the string "red" to the empty set s
		s.add("red");
		// Creates a Set1L with a string "red" in it
		Set<String> sExpected = createFromArgsRef("red");
		// Checks if the two sets are equal
		assertEquals(s, sExpected);
	}
	
	@Test
	public void Test1()
	{
		SetOnArrayList<String> set = new SetOnArrayList<String>();
		set.add("echo");
		
		assertEquals(false, set.contains("pineapple"));
		
	}
	
	@Test
	public void Test2()
	{
		SetOnArrayList<Integer> set = new SetOnArrayList<Integer>();
		set.add(23);
		
		assertEquals(true, set.contains(23));
		
	}
	
	@Test
	public void Test3()
	{
		SetOnArrayList<String> set = new SetOnArrayList<String>();
		set.add("echo");
		set.add("spicy");
		set.add("hot");
		set.add("exalt");
		set.add("twenty-one");
		
		assertEquals(true, set.contains("exalt"));
		
	}
	
	@Test
	public void Test4()
	{
		SetOnArrayList<Integer> set = new SetOnArrayList<Integer>();
		set.add(46);
		set.add(67);
		set.add(42);
		set.add(38);
		set.add(9);
		
		assertEquals(false, set.contains(0));
		
	}
	
	@Test
	public void Test5()
	{
		SetOnArrayList<String> set = new SetOnArrayList<String>();
		set.add("");
		
		assertEquals(false, set.contains("true"));
		
	}
	
}
