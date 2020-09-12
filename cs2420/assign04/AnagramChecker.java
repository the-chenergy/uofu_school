package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Contains methods to determine if two words are anagrams and find the largest group of
 * anagrams in a list of words. Two words are anagrams if they contain the same letters in the
 * same frequency. For example, "alert" and "later" are anagrams.
 * 
 * @author Qianlang Chen and Brandon Walton
 * @version February 1, 2019
 */
public class AnagramChecker
{
	/**
	 * This method returns the lexicographically-sorted version of the input string.
	 * 
	 * @param string - The string to be sorted
	 * @return The sorted string
	 */
	public static String sort(String string)
	{
		char[] chars = string.toCharArray();
		
		// for (int i = 0; i < chars.length; i++)
		// {
		// char value = chars[i];
		//
		// int target = i;
		// while (target > 0 && value < chars[target - 1])
		// chars[target] = chars[--target];
		//
		// chars[target] = value;
		// }
		
		Arrays.sort(chars);
		
		return new String(chars);
	}
	
	/**
	 * This generic method sorts the input array using an insertion sort and the input Comparator
	 * object.
	 * 
	 * @param array      - The array to be sorted
	 * @param comparator - The comparator used to sort the array
	 */
	public static <T> void insertionSort(T[] array, Comparator<? super T> comparator)
	{
		for (int i = 0; i < array.length; i++)
		{
			T value = array[i];
			
			int target = i;
			while (target > 0 && comparator.compare(value, array[target - 1]) < 0)
				array[target] = array[--target];
			
			array[target] = value;
		}
	}
	
	/**
	 * This method returns true if the two input strings are anagrams of each other, otherwise
	 * returns false.
	 * 
	 * @param s1 - The first string to compare
	 * @param s2 - The second string to compare
	 * @returns Whether the two strings are anagrams
	 */
	public static boolean areAnagrams(String s1, String s2)
	{
		return s1.length() == s2.length() && sort(s1.toLowerCase()).equals(sort(s2.toLowerCase()));
	}
	
	/**
	 * This method returns the largest group of anagrams in the input array of words, in no
	 * particular order. It returns an empty array if there are no anagrams in the input array.
	 * 
	 * @param array - The array to check for anagrams in
	 * @return An array with the largest group of anagrams from the input
	 */
	public static String[] getLargestAnagramGroup(String[] array)
	{
		// insertionSort(array, (s1, s2) -> sort(s1.toLowerCase()).compareTo(sort(s2.toLowerCase())));
		
		Arrays.sort(array, (s1, s2) -> sort(s1.toLowerCase()).compareTo(sort(s2.toLowerCase())));
		
		int longestLength = 0, longestIndex = 0, startIndex = 0;
		
		for (int i = 0; i <= array.length; i++)
		{
			if (i < array.length && areAnagrams(array[startIndex], array[i]))
				continue;
			
			if (i - startIndex > longestLength) // a longest string of anagrams is found
			{
				longestIndex = startIndex;
				longestLength = i - startIndex;
			}
			
			startIndex = i;
		}
		
		if (longestLength == 1) // no anagrams are found
			return new String[0];
		
		return Arrays.copyOfRange(array, longestIndex, longestIndex + longestLength);
	}
	
	/**
	 * This method behaves the same as the previous method, but reads the list of words from the
	 * input filename. It is assumed that the file contains one word per line. If the file does not
	 * exist or is empty, the method returns an empty array because there are no anagrams.
	 * 
	 * @param filename - The file to be checked for anagrams
	 * @return An array with the largest group of anagrams from the input
	 */
	public static String[] getLargestAnagramGroup(String filename)
	{
		File file = new File(filename);
		Scanner scanner;
		
		try
		{
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException ex)
		{
			return new String[0];
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		while (scanner.hasNextLine())
			list.add(scanner.nextLine());
		
		scanner.close();
		
		return getLargestAnagramGroup(list.toArray(new String[list.size()]));
	}
}
