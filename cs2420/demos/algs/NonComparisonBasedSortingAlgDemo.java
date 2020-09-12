package demos.algs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Demonstrates how each of the four non-comparison based sorting algorithm covered in our lecture works.
 * 
 * @author Qianlang Chen
 * @version F 04/26/19
 */
public class NonComparisonBasedSortingAlgDemo
{
	private static KeyValuePair[] bucketSort(KeyValuePair[] array)
	{
		// Example: {31, 41, 59, 26, 53, 58, 97, 93, 23, 8}
		//
		// Initialize 10 buckets for each of the 10 starting digits:
		// [0]: {}
		// [1]: {}
		// [2]: {}
		// [3]: {}
		// [4]: {}
		// [5]: {}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {}
		//
		// Put each item into its corresponding bucket (according to its first digit):
		// [0]: {8} ("8" is treated as "08" since the maximum number "97" has 2 digits)
		// [1]: {}
		// [2]: {26, 23}
		// [3]: {31}
		// [4]: {41}
		// [5]: {59, 53, 58}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {97, 93}
		//
		// Sort each bucket using Java's Merge Sort (which is a stable sort):
		// [0]: {8}
		// [1]: {}
		// [2]: {23, 26}
		// [3]: {31}
		// [4]: {41}
		// [5]: {53, 58, 59}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {93, 97}
		//
		// Add each item back to the original array, in order (bucket-by-bucket):
		// {8, 23, 26, 31, 41, 53, 58, 59, 93, 97}
		//
		// ------------------------------------
		// Space Complexity: O(N*b)
		// Runtime Complexity: O(N+b) on average
		// with b = number of buckets
		// ------------------------------------
		
		// Initialize our buckets. Assuming an integer having the first digit of N would belong to bucket N.
		
		ArrayList<LinkedList<KeyValuePair>> buckets = new ArrayList<>(10);
		for (int i = 0; i < 10; i++)
			buckets.add(new LinkedList<>());
		
		// Allocates each key-value-pair into the bucket that its key belongs to (according to its first digit).
		
		// find the magnitude (number of digits) of the maximum item in the array to define what the "first digit" means for other items.
		// for example, if the maximum item was 965, then its magnitude would be 100 (965 = 9.65*10^2), so we divide other items by 100
		// to get their first digits, for example, 365 / 100 = 3 which is the first digit of 365.
		int mag = (int)Math.pow(10, Math.floor(Math.log10(getMax(array).key)));
		
		for (KeyValuePair item : array)
			buckets.get(item.key / mag).add(item);
		
		// Sort each of the buckets using Java's Merge Sort.
		
		for (LinkedList<KeyValuePair> bucket : buckets)
			bucket.sort((item1, item2) -> item1.key - item2.key);
		
		// Put all items back to the original array, in order.
		
		int curr = 0;
		for (LinkedList<KeyValuePair> bucket : buckets)
			for (KeyValuePair item : bucket)
				array[curr++] = item;
			
		return array;
	}
	
	private static KeyValuePair[] pigeonholeSort(KeyValuePair[] array)
	{
		// Example: {3A, 1A, 4A, 1B, 5A, 9A, 2A, 6A, 5B, 3B, 5C, 8A}
		//
		// Initialize 9 "pigeonholes" since the range of the array to sort is 9 (1..9):
		// [1]: {}
		// [2]: {}
		// [3]: {}
		// [4]: {}
		// [5]: {}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {}
		//
		// Put each item into its corresponding pigeonhole, in order:
		// [1]: {1A, 1B}
		// [2]: {2A}
		// [3]: {3A, 3B}
		// [4]: {4A}
		// [5]: {5A, 5B, 5C}
		// [6]: {6A}
		// [7]: {}
		// [8]: {8A}
		// [9]: {9A}
		//
		// Add each item back to the original array, in order:
		// {1A, 1B, 2A, 3A, 3B, 4A, 5A, 5B, 5C, 6A, 8A, 9A}
		//
		// ------------------------------------
		// Space Complexity: O(N*r)
		// Runtime Complexities:
		// O(N) when r <= N
		// O(r) when r > N
		// with r = range of the array to sort
		// ------------------------------------
		
		int min = getMin(array).key, max = getMax(array).key;
		int range = max - min + 1;
		
		// Initialize our list of pigeonholes, one pigeonhole per key of an item.
		
		ArrayList<LinkedList<KeyValuePair>> pigeonholes = new ArrayList<>(range);
		for (int i = 0; i < range; i++)
			pigeonholes.add(new LinkedList<>());
		
		// Allocates each item into its corresponding pigeonhole.
		
		for (KeyValuePair item : array)
			pigeonholes.get(item.key - min).add(item);
		
		// Put all items back from the pigeonholes, in order.
		
		int curr = 0;
		for (LinkedList<KeyValuePair> pigeonhole : pigeonholes)
			for (KeyValuePair item : pigeonhole)
				array[curr++] = item;
			
		return array;
	}
	
	private static KeyValuePair[] countingSort(KeyValuePair[] array)
	{
		// Example: {3A, 1A, 4A, 1B, 5A, 9A, 2A, 6A, 5B, 3B, 5C, 8A}
		//
		// Initialize an array to store the count of each unique item in the array to sort:
		// [1]: 2
		// [2]: 1
		// [3]: 2
		// [4]: 1
		// [5]: 3
		// [6]: 1
		// [7]: 0
		// [8]: 1
		// [9]: 1
		//
		// Calculate the "accumulative" counts:
		// [1]: 2
		// [2]: 3 (2+1)
		// [3]: 5 (3+2)
		// [4]: 6 (5+1)
		// [5]: 9
		// [6]: 10
		// [7]: 10
		// [8]: 11
		// [9]: 12
		//
		// Loop through the original array backwards, and add the item to the index indicated in the array of counts:
		// .0...1...2...3...4...5...6...7...8...9...10..11
		// {__, __, __, __, __, __, __, __, __, __, __, __}
		// {__, __, __, __, __, __, __, __, __, __, 8A, __} (count of 8 is 11, 11-1 = index 10)
		// {__, __, __, __, __, __, __, __, 5C, __, 8A, __} (count of 5 is 9, 9-1 = index 8)
		// {__, __, __, __, 3B, __, __, __, 5C, __, 8A, __} (count of 3 is 5, 5-1 = index 4)
		// {__, __, __, __, 3B, __, __, 5B, 5C, __, 8A, __} (count of 5 is 8, 8-1 = index 7)
		// ...
		// {1A, 1B, 2A, 3A, 3B, 4A, 5A, 5B, 5C, 6A, 8A, 9A}
		//
		// ------------------------------------
		// Space Complexity: O(r)
		// Runtime Complexity: O(N+r)
		// with r = range of the array to sort
		// ------------------------------------
		
		int min = getMin(array).key, max = getMax(array).key;
		int range = max - min + 1;
		
		// Initialize an array to store the count of each unique item in the array to sort.
		
		int[] counts = new int[range];
		for (KeyValuePair item : array)
			counts[item.key - min]++;
		
		// Calculate the accumulative counts.
		
		for (int i = 1; i < counts.length; i++)
			counts[i] += counts[i - 1];
		
		// Put the items in the array back according to its accumulative count.
		
		KeyValuePair[] temp = Arrays.copyOf(array, array.length);
		for (int i = temp.length - 1; i >= 0; i--)
			array[--counts[temp[i].key - min]] = temp[i];
		
		return array;
	}
	
	private static KeyValuePair[] radixSort(KeyValuePair[] array)
	{
		// Example: {314, 159, 26, 535, 89, 7, 932, 384}
		//
		// Initialize 10 buckets for each of the 10 starting digits:
		// [0]: {}
		// [1]: {}
		// [2]: {}
		// [3]: {}
		// [4]: {}
		// [5]: {}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {}
		//
		// Run bucket sort for the least significant digit (ones):
		// [0]: {}
		// [1]: {}
		// [2]: {932}
		// [3]: {}
		// [4]: {314, 384}
		// [5]: {535}
		// [6]: {26}
		// [7]: {7}
		// [8]: {}
		// [9]: {159, 89}
		//
		// Put the items back, in order:
		// {932, 314, 384, 535, 26, 7, 159, 89}
		//
		// Run bucket sort for the second least significant digit (tens):
		// [0]: {7} (7 is treated as 007)
		// [1]: {314}
		// [2]: {26}
		// [3]: {932, 535}
		// [4]: {}
		// [5]: {159}
		// [6]: {}
		// [7]: {}
		// [8]: {384, 89}
		// [9]: {}
		//
		// Put the items back, in order:
		// {7, 314, 26, 932, 535, 159, 384, 89}
		//
		// Run bucket sort for the third least significant digit (hundreds):
		// [0]: {7, 26, 89} (26 and 89 are treated as 026 and 089, respectively)
		// [1]: {159}
		// [2]: {}
		// [3]: {314, 384}
		// [4]: {}
		// [5]: {535}
		// [6]: {}
		// [7]: {}
		// [8]: {}
		// [9]: {932}
		//
		// Put the items back, in order:
		// {7, 26, 89, 159, 314, 384, 535, 932}
		//
		// Since there are no more digits in the maximum item, the sort is over.
		//
		// ------------------------------------
		// Space Complexity: O(N+d)
		// Runtime Complexity: O(N*d)
		// with d = number of digits in the maximum item
		// ------------------------------------
		
		// Record the maximum item in the array to sort.
		
		int max = getMax(array).key;
		
		// Run Bucket Sort for each digit of the items in the array.
		
		for (int mag = 1; mag < max; mag *= 10)
		{
			// Create 10 buckets to hold each digit.
			
			ArrayList<LinkedList<KeyValuePair>> digits = new ArrayList<>();
			for (int i = 0; i < 10; i++)
				digits.add(new LinkedList<>());
			
			// Allocate each item into its corresponding bucket according to its N-th digit.
			
			for (KeyValuePair item : array)
				digits.get(item.key / mag % 10).add(item);
			
			// Put everything from the bucket back to the original array, in order.
			
			int curr = 0;
			for (LinkedList<KeyValuePair> digit : digits)
				for (KeyValuePair item : digit)
					array[curr++] = item;
		}
		
		return array;
	}
	
	/** Returns the minimum item in an array. **/
	private static KeyValuePair getMin(KeyValuePair[] array)
	{
		KeyValuePair min = array[0];
		for (int i = 1; i < array.length; i++)
			if (array[i].key < min.key)
				min = array[i];
			
		return min;
	}
	
	/** Returns the maximum item in an array. **/
	private static KeyValuePair getMax(KeyValuePair[] array)
	{
		KeyValuePair max = array[0];
		for (int i = 1; i < array.length; i++)
			if (array[i].key > max.key)
				max = array[i];
			
		return max;
	}
	
	public static void main(String[] args)
	{
		KeyValuePair[] array = {new KeyValuePair(3, "A"), new KeyValuePair(1, "A"), new KeyValuePair(4, "A"), new KeyValuePair(1, "B"),
			new KeyValuePair(5, "A"), new KeyValuePair(9, "A"), new KeyValuePair(2, "A"), new KeyValuePair(6, "A"), new KeyValuePair(5, "B"),
			new KeyValuePair(3, "B"), new KeyValuePair(5, "C"), new KeyValuePair(9, "B")};
		
		System.out.println("Original Array:");
		System.out.println(Arrays.deepToString(Arrays.copyOf(array, array.length)));
		System.out.println("Bucket Sort:");
		System.out.println(Arrays.deepToString(bucketSort(Arrays.copyOf(array, array.length))));
		System.out.println("Pigeonhole Sort:");
		System.out.println(Arrays.deepToString(pigeonholeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Counting Sort:");
		System.out.println(Arrays.deepToString(countingSort(Arrays.copyOf(array, array.length))));
		System.out.println("Radix Sort:");
		System.out.println(Arrays.deepToString(radixSort(Arrays.copyOf(array, array.length))));
		
		System.out.println();
		
		array = new KeyValuePair[] {new KeyValuePair(31, "A"), new KeyValuePair(41, "A"), new KeyValuePair(59, "A"), new KeyValuePair(26, "A"),
			new KeyValuePair(53, "A"), new KeyValuePair(58, "A"), new KeyValuePair(97, "A"), new KeyValuePair(93, "A"),
			new KeyValuePair(23, "A"), new KeyValuePair(8, "A")};
		
		System.out.println("Original Array:");
		System.out.println(Arrays.deepToString(Arrays.copyOf(array, array.length)));
		System.out.println("Bucket Sort:");
		System.out.println(Arrays.deepToString(bucketSort(Arrays.copyOf(array, array.length))));
		System.out.println("Pigeonhole Sort:");
		System.out.println(Arrays.deepToString(pigeonholeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Counting Sort:");
		System.out.println(Arrays.deepToString(countingSort(Arrays.copyOf(array, array.length))));
		System.out.println("Radix Sort:");
		System.out.println(Arrays.deepToString(radixSort(Arrays.copyOf(array, array.length))));
		
		System.out.println();
		
		array = new KeyValuePair[] {new KeyValuePair(314, "A"), new KeyValuePair(159, "A"), new KeyValuePair(26, "A"),
			new KeyValuePair(535, "A"), new KeyValuePair(89, "A"), new KeyValuePair(7, "A"), new KeyValuePair(932, "A"),
			new KeyValuePair(384, "A")};
		
		System.out.println("Original Array:");
		System.out.println(Arrays.deepToString(Arrays.copyOf(array, array.length)));
		System.out.println("Bucket Sort:");
		System.out.println(Arrays.deepToString(bucketSort(Arrays.copyOf(array, array.length))));
		System.out.println("Pigeonhole Sort:");
		System.out.println(Arrays.deepToString(pigeonholeSort(Arrays.copyOf(array, array.length))));
		System.out.println("Counting Sort:");
		System.out.println(Arrays.deepToString(countingSort(Arrays.copyOf(array, array.length))));
		System.out.println("Radix Sort:");
		System.out.println(Arrays.deepToString(radixSort(Arrays.copyOf(array, array.length))));
	}
	
	/** Represents a key-value-pair (a.k.a. map entry) which has an integer key and a string value. **/
	private static class KeyValuePair
	{
		public KeyValuePair(int key, String value)
		{
			this.key = key;
			this.value = value;
		}
		
		public int key;
		
		public String value;
		
		@Override
		public String toString()
		{
			return key + value;
		}
	}
}

