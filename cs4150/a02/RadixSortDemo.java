import java.util.*;

public class RadixSortDemo
{
	public static void main(String[] args)
	{
		System.out.println("n\tmySort\tArrays.sort");
		for (int n = 100_000; n <= 1_000_000; n += 1e5)
		{
			System.out.print(n + "\t");

			int[] arr1 = new int[n], arr2 = new int[n];
			for (int i = 0; i < n; i++)
				arr1[i] = arr2[i] = (int)(Math.random() * 1e6);

			long t = System.nanoTime();
			while (System.nanoTime() - t < 2e9)
			{
			}

			t = System.nanoTime();
			for (int i = 0; i < 1e3; i++)
				mySort(arr1);
			System.out.print((System.nanoTime() - t) / 1e3 + "\t");

			t = System.nanoTime();
			for (int i = 0; i < 1e3; i++)
				Arrays.sort(arr2);
			System.out.println((System.nanoTime() - t) / 1e3);
		}

		// Scanner in = new Scanner(System.in);
		// int n = in.nextInt();

		// int[] arr = new int[n];
		// for (int i = 0; i < n; i++)
		// {
		//     arr[i] = in.nextInt();
		// }

		// mySort(arr);
		// System.out.println(Arrays.toString(arr));
	}

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