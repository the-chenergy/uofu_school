import java.util.*;

public class PlantingTreesWithRadixSort
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();

		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		mySort(arr);
		for (int i = n - 2, d = 1; i >= 0; i--, d++)
			arr[i] = Math.max(arr[i] + d, arr[i + 1]);

		System.out.println(arr[0] + 2);
	}

	/**
	 * Sorts an int-array with Radix Sort (base 1001). Each element in the
	 * input must be non-negative and not exceed one million.
	 *
	 * (A Radix Sort sorts an array of integers based on their digits, when
	 * written in some base, starting from their least significant digits.)
	 *
	 * Time complexity: θ(n) (k is constant since all inputs < base^2)
	 * Space complexity: θ(n)
	 *
	 * @param arr The integer-array to sort.
	 */
	private static void mySort(int[] arr)
	{
		ArrayList<ArrayList<Integer>> digits = new ArrayList<>(1001);
		for (int exp = 0; exp <= 1; exp++)
		{
			if (exp == 0)
			{
				for (int i = 0; i < 1001; i++)
					digits.add(new ArrayList<>());
			}
			else
			{
				for (ArrayList<Integer> digit : digits)
					digit.clear();
			}

			int base = (int)Math.pow(1001, exp);
			for (int i : arr)
				digits.get(i / base % 1001).add(i);

			int target = 0;
			for (ArrayList<Integer> digit : digits)
			{
				for (int j : digit)
				{
					arr[target] = j;
					target++;
				}
			}
		}
	}
}