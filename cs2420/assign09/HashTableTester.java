package assign09;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>HashTableTest</code> class.
 * 
 * @author Kevin Song and Qianlang Chen
 * @version F 03/22/19
 */
class HashTableTester
{
	@Test
	void testIsEmpty()
	{
		assertTrue(new HashTable<>().isEmpty());
		assertFalse(buildSmallTable().isEmpty());
	}
	
	@Test
	void testSize()
	{
		assertEquals(0, new HashTable<>().size());
		assertEquals(2, buildSmallTable().size());
		assertEquals(5, buildObjectTable().size());
		assertEquals(144, buildLargeTable().size());
	}
	
	@Test
	void testClear()
	{
		HashTable<Integer, String> table = buildLargeTable();
		
		table.clear();
		
		assertTrue(table.isEmpty());
		assertEquals(0, table.size());
	}
	
	@Test
	void testContainsKey()
	{
		HashTable<Integer, String> table = buildLargeTable();
		
		assertTrue(table.containsKey(1210001));
		assertTrue(table.containsKey(1210013));
		assertTrue(table.containsKey(1210052));
		
		assertFalse(table.containsKey(0));
		assertFalse(table.containsKey(-1210001));
		assertFalse(table.containsKey(3141592));
	}
	
	@Test
	void testContainsKeyEmptyTable()
	{
		// check if any error is thrown
		assertFalse(new HashTable<>().containsKey(3141592));
	}
	
	@Test
	void testContainsObjectTable()
	{
		HashTable<String, StudentGoodHash> table = buildObjectTable();
		
		assertTrue(table.containsKey("Asianboii"));
		assertTrue(table.containsKey("Sorcerer"));
		
		assertFalse(table.containsKey(""));
		assertFalse(table.containsKey("Mr. Pi"));
	}
	
	@Test
	void testContainsValue()
	{
		HashTable<Integer, String> table = buildSmallTable();
		
		assertTrue(table.containsValue("Qianlang Chen"));
		assertTrue(table.containsValue("Kevin Song"));
		
		assertFalse(table.containsValue(""));
		assertFalse(table.containsValue("i love chocolate pie"));
	}
	
	@Test
	void testContainsValueEmptyTable()
	{
		assertFalse(new HashTable<>().containsValue("i love chocolate pie"));
	}
	
	@Test
	void testContainsValueObjectTable()
	{
		HashTable<String, StudentGoodHash> table = buildObjectTable();
		
		assertTrue(table.containsValue(new StudentGoodHash(1172983, "Qianlang", "Chen")));
		assertTrue(table.containsValue(new StudentGoodHash(1211977, "Kevin", "Song")));
		
		assertFalse(table.containsValue(new StudentGoodHash(3141592, "Mr.", "Pi")));
	}
	
	@Test
	void testEntries()
	{
		List<MapEntry<Integer, String>> entries = buildLargeTable().entries();
		
		assertEquals(144, entries.size());
		
		for (int i = 1; i <= 144; i++)
			assertTrue(entries.contains(new MapEntry<>(1210000 + i, "Student " + i)));
	}
	
	@Test
	void testEntriesEmptyTable()
	{
		assertTrue(new HashTable<>().entries().isEmpty());
	}
	
	@Test
	void testGet()
	{
		HashTable<Integer, String> table = buildSmallTable();
		
		assertEquals("Qianlang Chen", table.get(1172983));
		assertEquals("Kevin Song", table.get(1211977));
		
		assertNull(table.get(0));
		assertNull(table.get(3141592));
	}
	
	@Test
	void testGetObjectTable()
	{
		HashTable<String, StudentGoodHash> table = buildObjectTable();
		
		assertEquals(new StudentGoodHash(1172983, "Qianlang", "Chen"), table.get("Asianboii"));
		assertEquals(new StudentGoodHash(1211977, "Kevin", "Song"), table.get("ProGamer"));
		
		assertNull(table.get(""));
		assertNull(table.get("Mr. Pi"));
	}
	
	@Test
	void testPut()
	{
		HashTable<Integer, String> table = buildLargeTable();
		
		assertNull(table.put(1172983, "Qianlang Chen"));
		assertEquals("Qianlang Chen", table.get(1172983));
		assertEquals(145, table.size());
		
		assertNull(table.put(1211977, "Kevin Song"));
		assertEquals("Kevin Song", table.get(1211977));
		assertEquals(146, table.size());
		
		assertEquals("Student 1", table.put(1210001, "Student One"));
		assertEquals("Student One", table.get(1210001));
		assertEquals(146, table.size());
		
		assertEquals("Student 52", table.put(1210052, "Student Fifty-Two"));
		assertEquals("Student Fifty-Two", table.get(1210052));
		assertEquals(146, table.size());
	}
	
	@Test
	void testPutObjectTable()
	{
		HashTable<String, StudentGoodHash> table = buildObjectTable();
		
		assertNull(table.put("Mr. Pi", new StudentGoodHash(3141592, "Mr.", "Pi")));
		assertEquals(new StudentGoodHash(3141592, "Mr.", "Pi"), table.get("Mr. Pi"));
		assertEquals(6, table.size());
		
		assertEquals(new StudentGoodHash(3141592, "Mr.", "Pi"),
			table.put("Mr. Pi", new StudentGoodHash(6535897, "i love", "chocolate pie")));
		assertEquals(new StudentGoodHash(6535897, "i love", "chocolate pie"), table.get("Mr. Pi"));
		assertEquals(6, table.size());
	}
	
	@Test
	void testRemove()
	{
		HashTable<Integer, String> table = buildLargeTable();
		
		assertEquals("Student 1", table.remove(1210001));
		assertFalse(table.containsKey(1210001));
		assertEquals(143, table.size());
		
		assertEquals("Student 52", table.remove(1210052));
		assertFalse(table.containsKey(1210052));
		assertEquals(142, table.size());
		
		assertNull(table.remove(0));
		assertNull(table.remove(3141592));
		assertEquals(142, table.size());
	}
	
	@Test
	void testRemoveEmptyTable()
	{
		assertNull(new HashTable<>().remove(3141592));
	}
	
	@Test
	void testRemoveObjectTable()
	{
		HashTable<String, StudentGoodHash> table = buildObjectTable();
		
		assertEquals(new StudentGoodHash(1172983, "Qianlang", "Chen"), table.remove("Asianboii"));
		assertFalse(table.containsKey("Asianboii"));
		assertEquals(4, table.size());
		
		assertEquals(new StudentGoodHash(1211977, "Kevin", "Song"), table.remove("ProGamer"));
		assertFalse(table.containsKey("ProGamer"));
		assertEquals(3, table.size());
		
		assertNull(table.remove(""));
		assertEquals(3, table.size());
		
		assertNull(table.remove("Mr. Pi"));
		assertEquals(3, table.size());
	}
	
	/**
	 * Builds a small <code>HashTable</code> that uses <code>Integer</code> and <code>String</code>
	 * as the types of keys and values.
	 */
	private HashTable<Integer, String> buildSmallTable()
	{
		return buildTable(1172983, "Qianlang Chen", 1211977, "Kevin Song");
	}
	
	/**
	 * Builds a <code>HashTable</code> that uses <code>String</code> and
	 * <code>StudentGoodHash</code> as the types of keys and values.
	 */
	private HashTable<String, StudentGoodHash> buildObjectTable()
	{
		return buildTable("Asianboii", new StudentGoodHash(1172983, "Qianlang", "Chen"), "ProGamer",
			new StudentGoodHash(1211977, "Kevin", "Song"), "Sorcerer",
			new StudentGoodHash(1231234, "Brandon", "Walton"), "Mathy", new StudentGoodHash(1212121, "Naren", "Anandh"),
			"ProGamer2", new StudentGoodHash(1232123, "Joe", "Liao"));
	}
	
	/**
	 * Builds a large <code>HashTable</code> that uses <code>Integer</code> and <code>String</code>
	 * as the types of keys and values.
	 */
	private HashTable<Integer, String> buildLargeTable()
	{
		HashTable<Integer, String> table = new HashTable<>();
		
		for (int i = 1; i <= 144; i++)
			table.put(1210000 + i, "Student " + i);
		
		return table;
	}
	
	/**
	 * Builds a <code>HashTable</code> to store the given keys and values (formatted as "key1, val1,
	 * key2, val2, ..., keyN, valN").
	 */
	@SuppressWarnings("unchecked")
	private <K, V> HashTable<K, V> buildTable(K firstKey, V firstValue, Object ...rest)
	{
		HashTable<K, V> table = new HashTable<>();
		
		table.put(firstKey, firstValue);
		
		for (int i = 0; i < rest.length / 2; i++)
			table.put((K)rest[i], (V)rest[i + 1]);
		
		return table;
	}
}
