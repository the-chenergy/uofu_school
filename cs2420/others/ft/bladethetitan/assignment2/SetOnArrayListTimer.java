package others.ft.bladethetitan.assignment2;

public class SetOnArrayListTimer
{
	public static void main(String[] args)
	{
		System.out.println("\tadd()\tremove()\tcontains()");
		
		for (int n = 2000; n <= 30000; n += 2000)
		{
			System.out.print(n + "\t");
			
			SetOnArrayList<Integer> set = new SetOnArrayList<Integer>();
			
			for (int i = 0; i < n; i++)
			{
				set.add(i);
			}
			
			long startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000)
			{
			}
			
			startTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
				set.add(n);
			}
			
			long endTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
			}
			
			long time = (endTime - startTime) - (System.nanoTime() - endTime);
			
			System.out.print((double)time / 1000 + "\t");
			
			for (int i = 0; i < n; i++)
			{
				set.add(i);
			}
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000)
			{
			}
			
			startTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
				set.remove(set.size() - 1);
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
			}
			
			time = (endTime - startTime) - (System.nanoTime() - endTime);
			
			System.out.print((double)time / 1000 + "\t");
			
			for (int i = 0; i < n; i++)
			{
				set.add(i);
			}
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000)
			{
			}
			
			startTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
				set.contains(n);
			}
			
			endTime = System.nanoTime();
			for (int i = 0; i < 1000; i++)
			{
			}
			
			time = (endTime - startTime) - (System.nanoTime() - endTime);
			
			System.out.print((double)time / 1000);
			
			System.out.println();
		}
	}
}
