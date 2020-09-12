package others.ft.bladethetitan.assignment2;

import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;

import components.set.AbstractSet;

/**
 * {@code Set<E>} represented as a {@code java.util.ArrayList<E>} with implementation of primary methods.
 * 
 * @author Evan Norland
 *
 * @param <E> type of element
 */
public class SetOnArrayList<E> extends AbstractSet<E>
{
	/*
	 * Private members
	 */
	/**
	 * Underlying ArrayList representation
	 */
	private List<E> rep;
	
	
	// ----------------------------------------------------------------
	/**
	 * No-argument constructor; builds an empty set.
	 * 
	 */
	public SetOnArrayList()
	{
		rep = new ArrayList<>();
	}
	
	@Override
	public void add(E x)
	{
		assert x != null : "Violation of x is not null";
		if (!contains(x))
			rep.add(x);
	}
	
	@Override
	public boolean contains(E x)
	{
		assert x != null : "Violation of x is not null";
		
		for (int i = 0; i < rep.size(); i++)
		{
			if (x.equals(rep.get(i)))
				return true;
		}
		
		return false; // placeholder
	}
	
	@Override
	public void remove(E x)
	{
		assert x != null : "Violation of x is not null";
		assert this.contains(x) : "Violation of x is in this";
		rep.remove(x);
	}
	
	@Override
	public int size()
	{
		
		return rep.size();// placeholder
	}
	
	// ----------------------------------------------------------------
	
	/*
	 * Already implemented for you
	 */
	@Override
	public Iterator<E> iterator()
	{
		return this.rep.iterator();
	}
	
}
