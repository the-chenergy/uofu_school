package assign08;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>BinarySearchTree</code> class.
 * 
 * @author Qianlang Chen
 * @version March 15, 2019
 */
class BinarySearchTreeTester
{
	@Test
	void testAdd()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		int size = tree.size();
		
		assertTrue(tree.add(6));
		assertEquals(++size, tree.size());
		assertTrue(tree.contains(6));
		
		assertTrue(tree.add(13));
		assertEquals(++size, tree.size());
		assertTrue(tree.contains(13));
		
		assertTrue(tree.add(18));
		assertEquals(++size, tree.size());
		assertTrue(tree.contains(18));
		
		assertTrue(tree.add(23));
		assertEquals(++size, tree.size());
		assertTrue(tree.contains(23));
		
		assertFalse(tree.add(12));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add(16));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add(21));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add(23));
		assertEquals(size, tree.size());
	}
	
	@Test
	void testAddStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		int size = tree.size();
		
		assertTrue(tree.add("Ale"));
		assertEquals(++size, tree.size());
		
		assertTrue(tree.add("Cameron"));
		assertEquals(++size, tree.size());
		
		assertTrue(tree.add("Peter"));
		assertEquals(++size, tree.size());
		
		assertTrue(tree.add("Thomas"));
		assertEquals(++size, tree.size());
		
		assertFalse(tree.add("Aaron"));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add("Michael"));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add("Peter"));
		assertEquals(size, tree.size());
		
		assertFalse(tree.add("Thomas"));
		assertEquals(size, tree.size());
	}
	
	@Test
	void testAddAll()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		int size = tree.size();
		
		// all elements exist
		assertFalse(tree.addAll(Arrays.asList(8, 10, 11, 12)));
		assertEquals(size, tree.size());
		
		// some elements exist
		assertTrue(tree.addAll(Arrays.asList(8, 10, 11, -314159)));
		assertEquals(++size, tree.size());
		
		assertTrue(tree.addAll(Arrays.asList(13, 13, 13, 13)));
		assertEquals(++size, tree.size());
		
		// all new elements
		assertTrue(tree.addAll(Arrays.asList(271828, 459045, 314159, 265358)));
		assertEquals(size += 4, tree.size());
		System.out.println(tree.toDot());
	}
	
	@Test
	void testAddAllStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		int size = tree.size();
		
		// all elements exist
		assertFalse(tree.addAll(Arrays.asList("Bill", "Gio", "Matt")));
		assertEquals(size, tree.size());
		
		// some elements exist
		assertTrue(tree.addAll(Arrays.asList("Chase", "Drew", "Ryan")));
		assertEquals(++size, tree.size());
		
		// all new elements
		assertTrue(tree.addAll(Arrays.asList("", "Alf", "CK", "Parker")));
		assertEquals(size += 4, tree.size());
	}
	
	@Test
	void testClear()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		
		tree.clear();
		
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
	}
	
	@Test
	void testContains()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		
		assertTrue(tree.contains(11));
		assertTrue(tree.contains(12));
		assertTrue(tree.contains(16));
		assertTrue(tree.contains(21));
		
		assertFalse(tree.contains(-11));
		assertFalse(tree.contains(0));
		assertFalse(tree.contains(9));
		assertFalse(tree.contains(23));
	}
	
	@Test
	void testContainsStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		
		assertTrue(tree.contains("Aaron"));
		assertTrue(tree.contains("Brandon"));
		assertTrue(tree.contains("Joe"));
		assertTrue(tree.contains("Ryan"));
		
		assertFalse(tree.contains(""));
		assertFalse(tree.contains("Ale"));
		assertFalse(tree.contains("Matthew"));
	}
	
	@Test
	void testContainsWithEmptyTree()
	{
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		// check if any error is thrown
		assertFalse(tree.contains(0));
	}
	
	@Test
	void testContainsAll()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		
		// all elements exist
		assertTrue(tree.containsAll(Arrays.asList(8, 10, 11, 12)));
		
		// some elements exist
		assertFalse(tree.containsAll(Arrays.asList(8, 10, 11, -314159)));
		
		// all elements do not exist
		assertFalse(tree.containsAll(Arrays.asList(13, 13, 13, 13)));
		assertFalse(tree.containsAll(Arrays.asList(271828, 459045, 314159, 265358)));
	}
	
	@Test
	void testContainsAllStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		
		// all elements exist
		assertTrue(tree.containsAll(Arrays.asList("Bill", "Gio", "Matt")));
		
		// some elements exist
		assertFalse(tree.containsAll(Arrays.asList("Chase", "Drew", "Ryan")));
		
		// all elements do not exist
		assertFalse(tree.containsAll(Arrays.asList("", "Alf", "CK", "Parker")));
	}
	
	@Test
	void testFirst()
	{
		BinarySearchTree<Integer> tree;
		
		tree = generateHalfTree();
		assertEquals(8, tree.first().intValue());
		tree.add(6);
		assertEquals(6, tree.first().intValue());
		
		tree = generateLeftBiasedTree();
		assertEquals(1, tree.first().intValue());
		tree.add(0);
		assertEquals(0, tree.first().intValue());
		
		tree = generateRightBiasedTree();
		assertEquals(16, tree.first().intValue());
		tree.add(314159);
		assertEquals(16, tree.first().intValue());
	}
	
	@Test
	void testFirstStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		
		assertEquals("Aaron", tree.first());
		
		tree.add("");
		assertEquals("", tree.first());
		
		tree.add("i love chocolate pies");
		assertEquals("", tree.first());
		
		tree.remove("");
		assertEquals("Aaron", tree.first());
	}
	
	@Test
	void testFirstWithEmptyTree()
	{
		assertThrows(NoSuchElementException.class, () -> new BinarySearchTree<>().first());
	}
	
	@Test
	void testLast()
	{
		BinarySearchTree<Integer> tree;
		
		tree = generateHalfTree();
		assertEquals(24, tree.last().intValue());
		tree.add(28);
		assertEquals(28, tree.last().intValue());
		
		tree = generateLeftBiasedTree();
		assertEquals(16, tree.last().intValue());
		tree.add(0);
		assertEquals(16, tree.last().intValue());
		
		tree = generateRightBiasedTree();
		assertEquals(31, tree.last().intValue());
		tree.add(314159);
		assertEquals(314159, tree.last().intValue());
	}
	
	@Test
	void testLastStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		
		assertEquals("Will", tree.last());
		
		tree.remove("Will");
		assertEquals("Taylor", tree.last());
		
		tree.add("i love chocolate pies");
		assertEquals("i love chocolate pies", tree.last());
		
		tree.remove("i love chocolate pies");
		assertEquals("Taylor", tree.last());
	}
	
	@Test
	void testLastWithEmptyTree()
	{
		assertThrows(NoSuchElementException.class, () -> new BinarySearchTree<>().last());
	}
	
	@Test
	void testRemove()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		int size = tree.size();
		
		assertTrue(tree.remove(12));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains(12));
		
		assertTrue(tree.remove(16));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains(16));
		
		assertTrue(tree.remove(20));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains(20));
		
		assertTrue(tree.remove(21));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains(21));
		
		assertFalse(tree.remove(0));
		assertEquals(size, tree.size());
		
		assertFalse(tree.remove(12));
		assertEquals(size, tree.size());
		
		assertFalse(tree.remove(21));
		assertEquals(size, tree.size());
		
		assertFalse(tree.remove(314159));
		assertEquals(size, tree.size());
	}
	
	@Test
	void testRemoveStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		int size = tree.size();
		
		assertTrue(tree.remove("Alex"));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains("Alex"));
		
		assertTrue(tree.remove("Gio"));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains("Gio"));
		
		assertTrue(tree.remove("Sam"));
		assertEquals(--size, tree.size());
		assertFalse(tree.contains("Sam"));
		
		assertFalse(tree.remove(""));
		assertEquals(size, tree.size());
		
		assertFalse(tree.remove("Ale"));
		assertEquals(size, tree.size());
		
		assertFalse(tree.remove("Parker"));
		assertEquals(size, tree.size());
	}
	
	@Test
	void testRemoveRoots()
	{
		BinarySearchTree<Integer> tree;
		int size;
		
		tree = generateHalfTree();
		size = tree.size() - 1;
		assertTrue(tree.remove(16));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateFullTree();
		size = tree.size() - 1;
		assertTrue(tree.remove(16));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateLeftBiasedTree();
		size = tree.size() - 1;
		assertTrue(tree.remove(16));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateRightBiasedTree();
		size = tree.size() - 1;
		assertTrue(tree.remove(16));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
	}
	
	@Test
	void testRemoveLeaves()
	{
		BinarySearchTree<Integer> tree;
		int size;
		
		tree = generateHalfTree();
		size = tree.size() - 4;
		assertTrue(tree.remove(11));
		assertTrue(tree.remove(21));
		assertTrue(tree.remove(10));
		assertTrue(tree.remove(22));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateFullTree();
		size = tree.size() - 4;
		assertTrue(tree.remove(1));
		assertTrue(tree.remove(27));
		assertTrue(tree.remove(3));
		assertTrue(tree.remove(2));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateLeftBiasedTree();
		size = tree.size() - 2;
		assertTrue(tree.remove(1));
		assertTrue(tree.remove(2));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		tree = generateRightBiasedTree();
		size = tree.size() - 2;
		assertTrue(tree.remove(31));
		assertTrue(tree.remove(30));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
	}
	
	@Test
	void testRemoveWithSingleElementTree()
	{
		BinarySearchTree<Integer> tree = buildTree(1);
		
		// check if any error is thrown
		assertTrue(tree.remove(1));
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
		assertEquals(0, tree.toArrayList().size());
	}
	
	@Test
	void testRemoveWithEmptyTree()
	{
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		// check if any error is thrown
		assertFalse(tree.remove(314159));
	}
	
	@Test
	void testRemoveAll()
	{
		BinarySearchTree<Integer> tree = generateHalfTree();
		int size = tree.size();
		
		// all elements exist
		assertTrue(tree.removeAll(Arrays.asList(8, 10, 11, 12)));
		assertEquals(size -= 4, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		// some elements exist
		assertTrue(tree.removeAll(Arrays.asList(8, 10, 21, 23)));
		assertEquals(--size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		// all elements do not exist
		assertFalse(tree.removeAll(Arrays.asList(0, 8, 314159)));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
	}
	
	@Test
	void testRemoveAllStrings()
	{
		BinarySearchTree<String> tree = generateStringTree();
		int size = tree.size();
		
		// all elements exist
		assertTrue(tree.removeAll(Arrays.asList("Bill", "Aaron", "Taylor")));
		assertEquals(size -= 3, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		// some elements exist
		assertTrue(tree.removeAll(Arrays.asList("Jackson", "Joe", "Taylor")));
		assertEquals(--size, tree.size());
		assertEquals(size, tree.toArrayList().size());
		
		// all elements do not exist
		assertFalse(tree.removeAll(Arrays.asList("", "Joe", "Peter")));
		assertEquals(size, tree.size());
		assertEquals(size, tree.toArrayList().size());
	}
	
	@Test
	void testSize()
	{
		assertEquals(9, generateHalfTree().size());
		assertEquals(31, generateFullTree().size());
		assertEquals(5, generateLeftBiasedTree().size());
		assertEquals(5, generateRightBiasedTree().size());
		assertEquals(0, new BinarySearchTree<>().size());
	}
	
	@Test
	void testIsEmpty()
	{
		assertFalse(generateHalfTree().isEmpty());
		assertTrue(new BinarySearchTree<>().isEmpty());
	}
	
	@Test
	void testToArrayList()
	{
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 7, 2, 11, 13));
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		
		tree.addAll(list);
		list.sort(null);
		
		assertEquals(list, tree.toArrayList());
		
		// check if any error is thrown
		assertEquals(new ArrayList<>(), new BinarySearchTree<>().toArrayList());
	}
	
	/**
	 * Generates a <code>BinarySearchTree</code> where each node has only one child, except for the
	 * root.
	 */
	private BinarySearchTree<Integer> generateHalfTree()
	{
		return buildTree(16, 8, 24, 12, 20, 10, 22, 11, 21);
	}
	
	/** Generates a full <code>BinarySearchTree</code>. **/
	private BinarySearchTree<Integer> generateFullTree()
	{
		return buildTree(16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21,
			23, 25, 27, 29, 31);
	}
	
	/** Generates a <code>BinarySearchTree</code> that holds strings. **/
	private BinarySearchTree<String> generateStringTree()
	{
		return buildTree("Joe", "David", "Kevin", "Bill", "Gio", "Sam", "Alex", "Brandon", "Michael", "Will", "Aaron",
			"Chase", "Matt", "Ryan", "Taylor");
	}
	
	/** Generates a <code>BinarySearchTree</code> where every node has only the left child. **/
	private BinarySearchTree<Integer> generateLeftBiasedTree()
	{
		return buildTree(16, 8, 4, 2, 1);
	}
	
	/** Generates a <code>BinarySearchTree</code> where every node has only the right child. **/
	private BinarySearchTree<Integer> generateRightBiasedTree()
	{
		return buildTree(16, 24, 28, 30, 31);
	}
	
	/** Builds a <code>BinarySearchTree</code> with the given data as the elements. **/
	private <T extends Comparable<? super T>> BinarySearchTree<T> buildTree(T ...args)
	{
		BinarySearchTree<T> tree = new BinarySearchTree<>();
		
		tree.addAll(Arrays.asList(args));
		
		return tree;
	}
}
