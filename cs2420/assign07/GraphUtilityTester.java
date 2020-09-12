package assign07;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>GraphUtility</code> class.
 * 
 * @author Qianlang Chen and Kevin Song
 * @version M 03/04/19
 */
class GraphUtilityTester
{
	@Test
	void testIsCyclicTrue()
	{
		List<String> sources = Arrays.asList("a", "b", "a", "d", "e", "f");
		List<String> dests = Arrays.asList("b", "c", "c", "e", "f", "d");
		
		assertTrue(GraphUtility.isCyclic(sources, dests));
	}
	
	@Test
	void testIsCyclicFalse()
	{
		List<String> sources = Arrays.asList("a", "a", "b", "d");
		List<String> dests = Arrays.asList("b", "c", "d", "c");
		
		assertFalse(GraphUtility.isCyclic(sources, dests));
	}
	
	@Test
	void testIsCyclicWithUnequalSizedLists()
	{
		List<String> sources = Arrays.asList("a", "b", "a", "d", "e", "f", "NULL");
		List<String> dests = Arrays.asList("b", "c", "c", "e", "f", "d");
		
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.isCyclic(sources, dests));
	}
	
	@Test
	void testIsCyclicWithEmptyLists()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		
		// A graph of nothing should be successfully considered acyclic without errors.
		assertFalse(GraphUtility.isCyclic(sources, dests));
	}
	
	@Test
	void testAreConnectedTrue()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCityLists(sources, dests);
		
		assertTrue(GraphUtility.areConnected(sources, dests, "Salt Lake City", "Washington DC"));
		assertTrue(GraphUtility.areConnected(sources, dests, "Salt Lake City", "Salt Lake City"));
		assertTrue(GraphUtility.areConnected(sources, dests, "Washington DC", "Atlanta"));
		assertTrue(GraphUtility.areConnected(sources, dests, "Nanning", "Shenzhen"));
	}
	
	@Test
	void testAreConnectedFalse()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCityLists(sources, dests);
		
		assertFalse(GraphUtility.areConnected(sources, dests, "Salt Lake City", "Shenzhen"));
		assertFalse(GraphUtility.areConnected(sources, dests, "Dallas", "Nanning"));
	}
	
	@Test
	void testAreConnectedWithUnequalSizedLists()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCityLists(sources, dests);
		
		sources.add("Moon"); // this extra element maks the list sizes unmatched
		
		assertThrows(IllegalArgumentException.class,
			() -> GraphUtility.areConnected(sources, dests, "Salt Lake City", "Shenzhen"));
	}
	
	@Test
	void testAreConnectedWithUnexistingSourceAndDest()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCityLists(sources, dests);
		
		assertThrows(IllegalArgumentException.class,
			() -> GraphUtility.areConnected(sources, dests, "Moscow", "Nanning"));
		assertThrows(IllegalArgumentException.class,
			() -> GraphUtility.areConnected(sources, dests, "Salt Lake City", "Jupiter"));
	}
	
	@Test
	void testSort()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCSCourseLists(sources, dests);
		
		List<String> sortedList = GraphUtility.sort(sources, dests);
		
		// By definition of a Topological Sort, if a vertex A points to another vertex B,
		// then A should appear before B in the sorted vertex list.
		
		for (int i = 0; i < sources.size(); i++)
		{
			int sourceIndex = sortedList.indexOf(sources.get(i));
			int destIndex = sortedList.indexOf(dests.get(i));
			
			assertTrue(sourceIndex < destIndex);
		}
	}
	
	@Test
	void testSortWithUnequalSizedLists()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCSCourseLists(sources, dests);
		
		dests.add("NoJobFoundException()"); // the extra element making the sizes unmatched
		
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(sources, dests));
	}
	
	@Test
	void testSortWithCyclicGraph()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		generateCSCourseLists(sources, dests);
		
		sources.add("CS 4500");
		dests.add("CS 1410"); // 4500 now points to 1410, making the graph cyclic
		
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(sources, dests));
	}
	
	@Test
	void testSortEmptyList()
	{
		List<String> sources = new ArrayList<String>();
		List<String> dests = new ArrayList<String>();
		
		// Check if any error is thrown (the sort should still succeed even the lists are empty).
		assertEquals(0, GraphUtility.sort(sources, dests).size());
	}
	
	/**
	 * Generates lists of sources and destinations of city names.
	 * 
	 * @param sources The list to store source cities.
	 * @param dests   The list to store destination cities.
	 */
	private void generateCityLists(List<String> sources, List<String> dests)
	{
		sources.add("Salt Lake City");
		sources.add("Salt Lake City");
		sources.add("Dallas");
		sources.add("Dallas");
		sources.add("Atlanta");
		sources.add("Atlanta");
		sources.add("Washington DC");
		sources.add("San Diego");
		sources.add("Nanning");
		
		dests.add("Dallas");
		dests.add("Atlanta");
		dests.add("Atlanta");
		dests.add("Washington DC");
		dests.add("Washington DC");
		dests.add("San Diego");
		dests.add("San Diego");
		dests.add("Salt Lake City");
		dests.add("Shenzhen");
	}
	
	/**
	 * Generates lists of sources and destinations of some CS courses.
	 * 
	 * @param sources The list to store the sources.
	 * @param dests   The list to store the destinations.
	 */
	private void generateCSCourseLists(List<String> sources, List<String> dests)
	{
		sources.add("CS 1410");
		sources.add("CS 1410");
		sources.add("CS 2100");
		sources.add("CS 2100");
		sources.add("CS 2420");
		sources.add("CS 2420");
		sources.add("CS 2420");
		sources.add("CS 2420");
		sources.add("CS 2420");
		sources.add("MATH 2250");
		sources.add("CS 3500");
		sources.add("CS 3500");
		sources.add("CS 3810");
		sources.add("CS 3505");
		
		dests.add("CS 2100");
		dests.add("CS 2420");
		dests.add("CS 3100");
		dests.add("CS 4150");
		dests.add("CS 3100");
		dests.add("CS 4150");
		dests.add("CS 3500");
		dests.add("CS 3810");
		dests.add("CS 3200");
		dests.add("CS 3200");
		dests.add("CS 3505");
		dests.add("CS 4400");
		dests.add("CS 4400");
		dests.add("CS 4500");
	}
}
