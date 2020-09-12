package lab03;

/**
 * This class contains code for completing the Lab 3: Debugging activities.
 * 
 * @author  CS 2420 course staff
 * @version January 25, 2019
 */
public class Part1
{
	
	public static boolean binarySearch(int[] arr, int goal)
	{
		int low = arr[0], high = arr[arr.length - 1], mid = 0;
		while (low <= high)
		{
			mid = (low + high) / 2;
			if (goal == arr[mid])
				return true;
			else if (goal < arr[mid])
				high = arr[mid - 1];
			else
				low = arr[mid + 1];
		}
		return false;
	}
}
