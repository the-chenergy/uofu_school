package assign02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This class contains tests for Library.
 * 
 * @author  Erin Parker, Brandon Walton, and Qianlang Chen
 * @version January 16, 2019
 */
public class LibraryTester
{
	
	private Library emptyLib, smallLib, mediumLib;
	
	@BeforeEach
	void setUp() throws Exception
	{
		emptyLib = new Library();
		
		smallLib = new Library();
		smallLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		smallLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		smallLib.add(9780446580342L, "David Baldacci", "Simple Genius");
		
		mediumLib = new Library();
		mediumLib.addAll("src/assign02/Mushroom_Publishing.txt");
		// FILL IN -- extend this tester to consider a medium-size library
	}
	
	@Test
	public void testEmptyLookupISBN()
	{
		assertNull(emptyLib.lookup(978037429279L));
	}
	
	@Test
	public void testEmptyLookupHolder()
	{
		ArrayList<LibraryBook> booksCheckedOut = emptyLib.lookup("Jane Doe");
		assertNotNull(booksCheckedOut);
		assertEquals(0, booksCheckedOut.size());
	}
	
	@Test
	public void testEmptyCheckout()
	{
		assertFalse(emptyLib.checkout(978037429279L, "Jane Doe", 1, 1, 2008));
	}
	
	@Test
	public void testEmptyCheckinISBN()
	{
		assertFalse(emptyLib.checkin(978037429279L));
	}
	
	@Test
	public void testEmptyCheckinHolder()
	{
		assertFalse(emptyLib.checkin("Jane Doe"));
	}
	
	@Test
	public void testSmallLibraryLookupISBN()
	{
		assertNull(smallLib.lookup(9780330351690L));
	}
	
	@Test
	public void testMediumLibraryLookupISBN()
	{
		assertNull(mediumLib.lookup(9781843190394L));
	}
	
	@Test
	public void testMediumLibraryLookupHolder()
	{
		assertTrue(mediumLib.lookup("Qianlang Chen").isEmpty());
		mediumLib.checkout(9781843190394L, "Qianlang Chen", 1, 18, 2019);
		assertFalse(mediumLib.lookup("Qianlang Chen").isEmpty());
		mediumLib.checkin("Qianlang Chen");
	}
	
	@Test
	public void testMediumLibraryCheckoutISBN()
	{
		mediumLib.checkout(9781843190394L, "Qianlang Chen", 1, 18, 2019);
		assertEquals("Qianlang Chen", mediumLib.lookup(9781843190394L));
		mediumLib.checkin("Qianlang Chen");
	}
	
	@Test
	public void testMediumLibraryCheckInISBN()
	{
		mediumLib.checkout(9781843190394L, "Qianlang Chen", 1, 18, 2019);
		mediumLib.checkin(9781843190394L);
		assertNull(mediumLib.lookup(9781843190394L));
		assertTrue(mediumLib.lookup("Qianlang Chen").isEmpty());
	}
	
	@Test
	public void testMediumLibraryCheckInHolder()
	{
		mediumLib.checkout(9781843190394L, "Qianlang Chen", 1, 18, 2019);
		mediumLib.checkin("Qianlang Chen");
		assertNull(mediumLib.lookup(9781843190394L));
		assertTrue(mediumLib.lookup("Qianlang Chen").isEmpty());
	}
	
	@Test
	public void testSmallLibraryLookupHolder()
	{
		smallLib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
		ArrayList<LibraryBook> booksCheckedOut = smallLib.lookup("Jane Doe");
		
		assertNotNull(booksCheckedOut);
		assertEquals(1, booksCheckedOut.size());
		assertEquals(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"), booksCheckedOut.get(0));
		assertEquals("Jane Doe", booksCheckedOut.get(0).getHolder());
	}
	
	@Test
	public void testSmallLibraryCheckout()
	{
		assertTrue(smallLib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008));
	}
	
	@Test
	public void testSmallLibraryCheckinISBN()
	{
		smallLib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
		assertTrue(smallLib.checkin(9780330351690L));
	}
	
	@Test
	public void testSmallLibraryCheckinHolder()
	{
		assertFalse(smallLib.checkin("Jane Doe"));
	}
	
	@Test
	public void testMediumLibraryInvalidCheckout()
	{
		assertFalse(mediumLib.checkout(9999999999999L, "Qianlang Chen", 1, 18, 2019));
	}
}
