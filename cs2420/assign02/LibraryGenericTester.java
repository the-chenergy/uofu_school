package assign02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This class contains tests for LibraryGeneric.
 * 
 * @author  Erin Parker, Brandon Walton, and Qianlang Chen
 * @version January 16, 2019
 */
public class LibraryGenericTester
{
	
	private LibraryGeneric<String> nameLib; // library that uses names to identify patrons (holders)
	private LibraryGeneric<PhoneNumber> phoneLib; // library that uses phone numbers to identify patrons
	
	@BeforeEach
	void setUp() throws Exception
	{
		nameLib = new LibraryGeneric<String>();
		
		nameLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		nameLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		nameLib.add(9780446580342L, "David Baldacci", "Simple Genius");
		
		phoneLib = new LibraryGeneric<PhoneNumber>();
		
		phoneLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		phoneLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		phoneLib.add(9780446580342L, "David Baldacci", "Simple Genius");
	}
	
	@Test
	public void testNameLibCheckout()
	{
		String patron = "Jane Doe";
		
		assertTrue(nameLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(nameLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}
	
	@Test
	public void testNameLibLookup()
	{
		String patron = "Jane Doe";
		
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut = nameLib.lookup(patron);
		
		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}
	
	@Test
	public void testNameLibCheckin()
	{
		String patron = "Jane Doe";
		
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		assertTrue(nameLib.checkin(patron));
	}
	
	@Test
	public void testNameLibInvalidCheckin()
	{
		String patron = "Jane Doe";
		
		assertFalse(nameLib.checkin(patron));
	}
	
	@Test
	public void testNameLibInvalidCheckout()
	{
		String patron = "Jane Doe";
		
		assertFalse(nameLib.checkout(9999999999999L, patron, 1, 1, 2008));
	}
	
	@Test
	public void testNameLibCheckinISBN()
	{
		String patron = "Jane Doe";
		
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		assertTrue(nameLib.checkin(9780374292799L));
		assertTrue(nameLib.checkin(9780330351690L));
	}
	
	@Test
	public void testPhoneLibCheckout()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		assertTrue(phoneLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(phoneLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}
	
	@Test
	public void testNameLibInvalidISBN()
	{
		assertFalse(nameLib.checkin(9999999999999L));
	}
	
	@Test
	public void testPhoneLibLookup()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut = phoneLib.lookup(patron);
		
		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}
	
	@Test
	public void testPhoneLibCheckin()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		assertTrue(phoneLib.checkin(patron));
	}
	
	@Test
	public void testPhoneLibCheckinISBN()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		
		assertTrue(phoneLib.checkin(9780330351690L));
		assertTrue(phoneLib.checkin(9780374292799L));
	}
	
	@Test
	public void testPhoneLibInvalidCheckin()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		assertFalse(phoneLib.checkin(patron));
	}
	
	@Test
	public void testPhoneLibInvalidISBN()
	{
		assertFalse(phoneLib.checkin(9999999999999L));
	}
	
	@Test
	public void testPhoneLibInvalidCheckout()
	{
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		
		assertFalse(phoneLib.checkout(9999999999999L, patron, 1, 1, 2008));
	}
	
	@Test
	public void testNameLibSortByISBN()
	{
		ArrayList<LibraryBookGeneric<String>> bookList = nameLib.getInventoryList();
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780374292799L);
		assertEquals(bookList.get(2).getIsbn(), 9780446580342L);
	}
	
	@Test
	public void testPhoneLibSortByISBN()
	{
		ArrayList<LibraryBookGeneric<PhoneNumber>> bookList = phoneLib.getInventoryList();
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780374292799L);
		assertEquals(bookList.get(2).getIsbn(), 9780446580342L);
	}
	
	@Test
	public void testNameLibSortByDueDate()
	{
		nameLib.checkout(9780374292799L, "Brandon Walton", 1, 18, 2008);
		nameLib.checkout(9780330351690L, "Brandon Walton", 1, 18, 2007);
		
		ArrayList<LibraryBookGeneric<String>> bookList = nameLib.getOverdueList(1, 18, 2019);
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780374292799L);
		
		nameLib.checkin("Brandon Walton");
	}
	
	@Test
	public void testPhoneLibSortByDueDate()
	{
		PhoneNumber patron = new PhoneNumber("801.957.6669");
		
		phoneLib.checkout(9780374292799L, patron, 1, 18, 2008);
		phoneLib.checkout(9780330351690L, patron, 1, 18, 2007);
		
		ArrayList<LibraryBookGeneric<PhoneNumber>> bookList = phoneLib.getOverdueList(1, 18, 2019);
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780374292799L);
		
		phoneLib.checkin(patron);
	}
	
	@Test
	public void testPhoneLibSortByTitle()
	{
		ArrayList<LibraryBookGeneric<PhoneNumber>> bookList = phoneLib.getOrderedByTitle();
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780446580342L);
		assertEquals(bookList.get(2).getIsbn(), 9780374292799L);
	}
	
	@Test
	public void testNameLibSortByTitle()
	{
		ArrayList<LibraryBookGeneric<String>> bookList = nameLib.getOrderedByTitle();
		
		assertEquals(bookList.get(0).getIsbn(), 9780330351690L);
		assertEquals(bookList.get(1).getIsbn(), 9780446580342L);
		assertEquals(bookList.get(2).getIsbn(), 9780374292799L);
	}
}
