package demos.others;

public class StringTraverseDemo
{
	public static void main(String[] args)
	{
		String line = "some <object><aaa> <bbb> .<ccc>";
		
		int start = 0;
		for (int i = line.indexOf('<'); i != -1; i = line.indexOf('<', start + 1))
		{
			if (i > start) // only add a "terminal" when there is one
				System.out.println(line.substring(start, i));
			
			start = line.indexOf('>', i + 1) + 1;
			System.out.println(line.substring(i, start));
		}
		
		if (start < line.length())
			System.out.println(line.substring(start));
	}
}
