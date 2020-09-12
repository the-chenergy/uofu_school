/**
 * 
 */
package assign02;

import java.util.GregorianCalendar;

/**
 * @author  Brandon Walton and Qianlang Chen
 * @version January 18, 2016
 */
public class LibraryBookGeneric<Type> extends Book
{
	
	private GregorianCalendar dueDate;
	private Type holder;
	
	public LibraryBookGeneric(long isbn, String author, String title)
	{
		super(isbn, author, title);
	}
	
	public Type getHolder()
	{
		return holder;
	}
	
	public GregorianCalendar getDueDate()
	{
		return dueDate;
	}
	
	public void checkOut(Type holder, GregorianCalendar dueDate)
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
