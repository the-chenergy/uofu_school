package assign10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains generic static methods for finding the <i>k</i> largest items in a list.
 * 
 * @author Erin Parker, Kevin Song, and Qianlang Chen
 * @version April 4, 2019
 */
public class FindKLargest
{
	/**
	 * Determines the <i>k</i> largest items in the given list, using a binary max heap and the natural ordering of the items.
	 * 
	 * @param items The given list.
	 * @param k     The number of largest items to find in the given list.
	 * @return A list of the <code>k</code> largest items, in descending order.
	 * @throws IllegalArgumentException If <code>k<code> is negative or larger than the size of the given list.
	 */
	public static <T extends Comparable<? super T>> List<T> findKLargestHeap(List<T> items, int k) throws IllegalArgumentException
	{
		return findKLargestHeap(items, k, null);
	}
	
	/**
	 * Determines the <i>k</i> largest items in the given list, using a binary max heap.
	 * 
	 * @param items      The given list.
	 * @param k          The number of largest items to find in the given list.
	 * @param comparator The comparator defining how to compare items.
	 * @return A list of the <code>k</code> largest items, in descending order.
	 * @throws IllegalArgumentException If <code>k</code> is negative or larger than the size of the given list.
	 */
	public static <T> List<T> findKLargestHeap(List<T> items, int k, Comparator<? super T> comparator) throws IllegalArgumentException
	{
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException("The number k is invalid.");
		
		BinaryMaxHeap<T> heap = new BinaryMaxHeap<>(items, comparator);
		
		List<T> list = new ArrayList<>();
		for (int i = 0; i < k; i++)
			list.add(heap.extractMax());
		
		return list;
	}
	
	/**
	 * Determines the <i>k</i> largest items in the given list, using Java's sort routine and the natural ordering of the items.
	 * 
	 * @param items The given list.
	 * @param k     The number of largest items to find in the given list.
	 * @return A list of the <code>k</code> largest items, in descending order.
	 * @throws IllegalArgumentException If <code>k</code> is negative or larger than the size of the given list.
	 */
	public static <T extends Comparable<? super T>> List<T> findKLargestSort(List<T> items, int k) throws IllegalArgumentException
	{
		return findKLargestSort(items, k, null);
	}
	
	/**
	 * Determines the <i>k</i> largest items in the given list, using Java's sort routine.
	 * 
	 * @param items      The given list.
	 * @param k          The number of largest items to find in the given list.
	 * @param comparator The comparator defining how to compare items.
	 * @return A list of the <code>k</code> largest items, in descending order.
	 * @throws IllegalArgumentException If <code>k</code> is negative or larger than the size of the given list.
	 */
	public static <T> List<T> findKLargestSort(List<T> items, int k, Comparator<? super T> comparator) throws IllegalArgumentException
	{
		if (k < 0 || k > items.size())
			throw new IllegalArgumentException("The number k is invalid.");
		
		items.sort(comparator);
		
		List<T> list = new ArrayList<>();
		for (int i = 0; i < k; i++)
			list.add(items.get(items.size() - 1 - i));
		
		return list;
	}
}
