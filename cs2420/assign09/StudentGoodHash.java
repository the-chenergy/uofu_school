package assign09;

import java.text.DecimalFormat;

/**
 * This class provides a simple representation for a University of Utah student. Object's
 * hashCode method overridden with a correct hash function for this object, and one that does a
 * good job of distributing students in a hash table.
 * 
 * @author Erin Parker, Kevin Song, and Qianlang Chen
 * @version F 03/22/19
 */
public class StudentGoodHash
{
	/** The UID of this student. **/
	private int uid;
	
	/** This student's first name. **/
	private String firstName;
	
	/** This student's last name **/
	private String lastName;
	
	/**
	 * Creates a new student with the specified UID, firstName, and lastName.
	 * 
	 * @param uid       This student's UID.
	 * @param firstName This student's first name.
	 * @param lastName  This Student's last name.
	 */
	public StudentGoodHash(int uid, String firstName, String lastName)
	{
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Returns this student's UID.
	 * 
	 * @return This student's UID.
	 */
	public long getUid()
	{
		return this.uid;
	}
	
	/**
	 * Returns this student's first name.
	 * 
	 * @return This student's first name.
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	/**
	 * Returns this student's last name.
	 * 
	 * @return This student's last name.
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	
	/**
	 * Check if this student is equal to another student.
	 * 
	 * @return <code>true</code> if this student and <code>other</code> have the same UID, first
	 *         name, and last name; otherwise <code>false</code>.
	 */
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof StudentGoodHash))
			return false;
		
		StudentGoodHash rhs = (StudentGoodHash)other;
		
		return this.uid == rhs.uid && this.firstName.equals(rhs.firstName) && this.lastName.equals(rhs.lastName);
	}
	
	/**
	 * Returns the string representation of this student.
	 * 
	 * @return A string containing this student's full name, followed by their UID.
	 */
	@Override
	public String toString()
	{
		DecimalFormat formatter = new DecimalFormat("0000000");
		
		return firstName + " " + lastName + " (u" + formatter.format(uid) + ")";
	}
	
	/**
	 * Returns a hash code value for this <code>StudentGoodHash</code> object.
	 * 
	 * @return A hash code value for this <code>StudentGoodHash</code> object.
	 */
	@Override
	public int hashCode()
	{
		int hashValue = 0;
		String fullName = firstName + lastName;
		
		for (int i = 0; i < fullName.length(); i++)
			hashValue = hashValue * 128 + fullName.charAt(i);
		
		return hashValue + uid;
	}
}
