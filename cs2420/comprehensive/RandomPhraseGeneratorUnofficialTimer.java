package comprehensive;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Times, experiments, and compares each versions of our <code>RandomPhraseGenerator</code> class.
 * 
 * @author  Qianlang Chen
 * @version H 04/11/19
 */
public class RandomPhraseGeneratorUnofficialTimer
{
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, SecurityException,
		IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		int numLoops = 2_000, step = 50, max = 600, versionStarts = 12, versionsToSkip = 0;
		boolean timeCurrentVersion = true;
		
		System.out.printf("(Average of %,d, microseconds)%n%nN", numLoops);
		
		List<String> versions = new ArrayList<>();
		for (int versionEnds = versionStarts; versionEnds < versionStarts + 10; versionEnds++)
		{
			String version = String.format("%02d", versionEnds);
			
			try
			{
				Class.forName("comprehensive.RandomPhraseGenerator" + version);
			}
			catch (ClassNotFoundException ex)
			{
				continue;
			}
			
			versions.add(version);
		}
		versions = versions.subList(0, versions.size() - versionsToSkip);
		for (String version : versions)
			System.out.print("\t04/" + version);
		if (timeCurrentVersion)
		{
			versions.add("");
			System.out.print("\tCurrent");
		}
		System.err.println();
		
		for (int n = step, a = 0; n <= max; n += step * a, a = 1)
		{
			if (a > 0)
				System.out.printf("%,d", n);
			
			double time, last = -1;
			for (String version : versions)
			{
				String[] mainArgs = {"src/comprehensive/assignment_extension_request.g", Integer.toString(n)};
				Method main = Class.forName("comprehensive.RandomPhraseGenerator" + version).getMethod("main", mainArgs.getClass());
				
				long start, end;
				
				start = System.nanoTime();
				while (System.nanoTime() - start < 1_000_000_000);
				
				start = System.nanoTime();
				for (int i = 0; i < numLoops; i++)
					main.invoke(null, (Object)mainArgs);
				
				end = System.nanoTime();
				for (int i = 0; i < numLoops; i++);
				
				time = ((end - start) - (System.nanoTime() - end)) / (double)numLoops * .001;
				
				if (a > 0)
					System.out.printf("\t%,.0f", time);
				
				if (version.isEmpty() && a > 0) // the latest version
				{
					double percent = Math.round((time - last) / last * 100);
					if (percent >= 0)
						System.err.printf(" (%+,.0f%%)", percent);
					else
						System.out.printf(" (%+,.0f%%)", percent);
				}
				
				last = time;
			}
			
			if (a > 0)
				System.out.println();
		}
	}
}
