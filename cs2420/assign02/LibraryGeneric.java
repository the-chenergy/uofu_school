package assign02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * This class represents a library, which is a collection of library books.
 * 
 * @author  Erin Parker, Brandon Walton, and Qianlang Chen
 * @version January 16, 2019
 */
public class LibraryGeneric<Type>
{
	
	private ArrayList<LibraryBookGeneric<Type>> library;
	
	/**
	 * Creates an empty library.
	 */
	public LibraryGeneric()
	{
		library = new ArrayList<LibraryBookGeneric<Type>>();
	}
	
	/**
	 * Adds to the library the book, specified by the given ISBN, author, and title. Assumes there
	 * is no possibility of duplicate library books.
	 * 
	 * @param isbn   - ISBN of the book to be added
	 * @param author - author of the book to be added
	 * @param title  - title of the book to be added
	 */
	public void add(long isbn, String author, String title)
	{
		library.add(new LibraryBookGeneric<Type>(isbn, author, title));
	}
	
	/**
	 * Add the list of library books to the library. Assumes there is no possibility of duplicate
	 * library books.
	 * 
	 * @param list - list of library books to be added
	 */
	public void addAll(ArrayList<LibraryBookGeneric<Type>> list)
	{
		library.addAll(list);
	}
	
	/**
	 * Adds to the library the books specified by the input file. Assumes the input files specifies
	 * one book per line with ISBN, author, and title separated by tabs.
	 * 
	 * If file does not exist or format is violated, prints an error message and does not change the
	 * library.
	 * 
	 * @param filename
	 */
	public void addAll(String filename)
	{
		ArrayList<LibraryBookGeneric<Type>> toBeAdded = new ArrayList<LibraryBookGeneric<Type>>();
		
		try
		{
			Scanner fileIn = new Scanner(new File(filename));
			int lineNum = 1;
			
			while (fileIn.hasNextLine())
			{
				String line = fileIn.nextLine();
				
				Scanner lineIn = new Scanner(line);
				lineIn.useDelimiter("\\t");
				
				if (!lineIn.hasNextLong())
				{
					lineIn.close();
					throw new ParseException("ISBN", lineNum);
				}
				long isbn = lineIn.nextLong();
				
				if (!lineIn.hasNext())
				{
					lineIn.close();
					throw new ParseException("Author", lineNum);
				}
				String author = lineIn.next();
				
				if (!lineIn.hasNext())
				{
					lineIn.close();
					throw new ParseException("Title", lineNum);
				}
				String title = lineIn.next();
				
				toBeAdded.add(new LibraryBookGeneric<Type>(isbn, author, title));
				
				lineNum++;
				lineIn.close();
			}
			fileIn.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage() + " Nothing added to the library.");
			
			return;
		}
		catch (ParseException e)
		{
			System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
				+ ". Nothing added to the library.");
			
			return;
		}
		
		library.addAll(toBeAdded);
	}
	
	/**
	 * Returns the holder of the library book with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns null.
	 * 
	 * @param isbn - ISBN of the book to be looked up
	 */
	public Type lookup(long isbn)
	{
		for (LibraryBookGeneric<Type> book : library)
			if (book.getIsbn() == isbn)
				return book.getHolder();
			
		return null;
	}
	
	/**
	 * Returns the list of library books checked out to the specified holder.
	 * 
	 * If the specified holder has no books checked out, returns an empty list.
	 * 
	 * @param holder - holder whose checked out books are returned
	 */
	public ArrayList<LibraryBookGeneric<Type>> lookup(Type holder)
	{
		ArrayList<LibraryBookGeneric<Type>> bookList = new ArrayList<LibraryBookGeneric<Type>>();
		
		for (LibraryBookGeneric<Type> book : library)
			if (holder.equals(book.getHolder()))
				bookList.add(book);
			
		return bookList;
	}
	
	/**
	 * Sets the holder and due date of the library book with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked out, returns false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn   - ISBN of the library book to be checked out
	 * @param holder - new holder of the library book
	 * @param month  - month of the new due date of the library book
	 * @param day    - day of the new due date of the library book
	 * @param year   - year of the new due date of the library book
	 */
	public boolean checkout(long isbn, Type holder, int month, int day, int year)
	{
		for (LibraryBookGeneric<Type> book : library)
		{
			if (book.getIsbn() == isbn)
			{
				if (book.getHolder() != null) // the book has already been checked out
					return false;
				
				GregorianCalendar c = new GregorianCalendar(year, month, day);
				book.checkOut(holder, c);
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Unsets the holder and due date of the library book.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked in, returns false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn - ISBN of the library book to be checked in
	 */
	public boolean checkin(long isbn)
	{
		for (LibraryBookGeneric<Type> book : library)
		{
			if (book.getIsbn() == isbn)
			{
				if (book.getHolder() == null) // The book has not been checked out
					return false;
				
				book.checkIn();
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Unsets the holder and due date for all library books checked out by the specified holder.
	 * 
	 * If no books with the specified holder are in the library, returns false;
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param holder - holder of the library books to be checked in
	 */
	public boolean checkin(Type holder)
	{
		ArrayList<LibraryBookGeneric<Type>> bookList = lookup(holder);
		
		if (bookList.isEmpty()) // This holder does not owe any books
			return false;
		
		for (LibraryBookGeneric<Type> book : bookList)
			book.checkIn();
		
		return true;
	}
	
	/**
	 * Returns the list of library books, sorted by ISBN (smallest ISBN first).
	 */
	public ArrayList<LibraryBookGeneric<Type>> getInventoryList()
	{
		// Create a copy of the library to avoid modifying the original order of books.
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		libraryCopy.addAll(library);
		
		OrderByIsbn comparator = new OrderByIsbn();
		
		sort(libraryCopy, comparator);
		
		return libraryCopy;
	}
	
	/**
	 * Returns the list of library books whose due date is older than the input date. The list is
	 * sorted by date (oldest first).
	 *
	 * If no library books are overdue, returns an empty list.
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOverdueList(int month, int day, int year)
	{
		ArrayList<LibraryBookGeneric<Type>> bookList = new ArrayList<LibraryBookGeneric<Type>>();
		
		long time = new GregorianCalendar(year, month, day).getTimeInMillis();
		
		for (LibraryBookGeneric<Type> book : this.library)
			if (book.getDueDate() != null)
				if (book.getDueDate().getTimeInMillis() < time)
					bookList.add(book);
				
		OrderByDueDate comparator = new OrderByDueDate();
		
		sort(bookList, comparator);
		
		return bookList;
	}
	
	/**
	 * Returns the list of library books, sorted by title
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOrderedByTitle()
	{
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		
		libraryCopy.addAll(library);
		
		sort(libraryCopy,
			(LibraryBookGeneric<Type> o1,
				LibraryBookGeneric<Type> o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()) > 0 ? 1
					: (o1.getTitle().compareToIgnoreCase(o2.getTitle()) < 0 ? -1 : 0));
		
		return libraryCopy;
	}
	
	/**
	 * Performs a SELECTION SORT on the input ArrayList.
	 * 
	 * 1. Finds the smallest item in the list. 2. Swaps the smallest item with the first item in the
	 * list. 3. Reconsiders the list be the remaining unsorted portion (second item to Nth item) and
	 * repeats steps 1, 2, and 3.
	 */
	private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c)
	{
		for (int i = 0; i < list.size() - 1; i++)
		{
			int j, minIndex;
			
			for (j = i + 1, minIndex = i; j < list.size(); j++)
				if (c.compare(list.get(j), list.get(minIndex)) < 0)
					minIndex = j;
				
			ListType temp = list.get(i);
			
			list.set(i, list.get(minIndex));
			list.set(minIndex, temp);
		}
	}
	
	/**
	 * Comparator that defines an ordering among library books using the ISBN.
	 */
	protected class OrderByIsbn implements Comparator<LibraryBookGeneric<Type>>
	{
		/**
		 * Returns a negative value if lhs is smaller than rhs. Returns a positive value if lhs is
		 * larger than rhs. Returns 0 if lhs and rhs are equal.
		 */
		public int compare(LibraryBookGeneric<Type> lhs, LibraryBookGeneric<Type> rhs)
		{
			return lhs.getIsbn() > rhs.getIsbn() ? 1 : (lhs.getIsbn() < rhs.getIsbn() ? -1 : 0);
		}
	}
	
	/**
	 * Comparator that defines an ordering among library books using the due date.
	 */
	protected class OrderByDueDate implements Comparator<LibraryBookGeneric<Type>>
	{
		@Override
		public int compare(LibraryBookGeneric<Type> o1, LibraryBookGeneric<Type> o2)
		{
			long time1 = o1.getDueDate().getTimeInMillis();
			long time2 = o2.getDueDate().getTimeInMillis();
			
			return time1 > time2 ? 1 : (time1 < time2 ? -1 : 0);
		}
	}
}
