/**
 * 
 */
package assign02;

import java.util.GregorianCalendar;

/**
 * @author Brandon Walton and Qianlang Chen
 *
 */
public class LibraryBook extends Book
{

	private GregorianCalendar dueDate;
	private String holder;
	
	public LibraryBook(long isbn, String author, String title)
	{
		super(isbn, author, title);
		this.checkIn();
	}
	
	public String getHolder()
	{
		return this.holder;
	}
	
	public GregorianCalendar getDueDate()
	{
		return this.dueDate;
	}
	
	public void checkOut(String holder, GregorianCalendar dueDate)
	{
		this.holder = holder;
		this.dueDate = dueDate;
	}
	
	public void checkIn()
	{
		this.holder = null;
		this.dueDate = null;
	}
}
