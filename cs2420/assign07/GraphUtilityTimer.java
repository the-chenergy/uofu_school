package assign07;

import java.util.ArrayList;
import java.util.List;

/**
 * Experiments and times our <code>GraphUtility</code> class.
 * 
 * @author Apple
 * @version M 03/04/19
 */
public class GraphUtilityTimer
{
	/** ... **/
	public static void main(String[] args)
	{
		List<Integer> sources = new ArrayList<>();
		List<Integer> dests = new ArrayList<>();
		
		int numLoops = 1_000, nStep = 100, nMax = 1_200;
		int mult = 2; // |E| = mult * |V|
		
		System.out.printf("(Average of %,d)\nN\tRuntime\n", numLoops);
		
		for (int n = nStep; n <= nMax; n += nStep)
		{
			System.out.printf("%,d", n);
			
			long start, end;
			
			start = System.nanoTime();
			while (System.nanoTime() - start < 1_000_000_000);
			
			start = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				generateAcyclic(n, mult, sources, dests);
				
				boolean x = GraphUtility.isCyclic(sources, dests);
				// boolean x = GraphUtility.areConnected(sources, dests, 1, n);
				// List<Integer> x = GraphUtility.sort(sources, dests);
			}
			
			end = System.nanoTime();
			for (int i = 0; i < numLoops; i++)
			{
				generateAcyclic(n, mult, sources, dests);
			}
			
			System.out.printf("\t%,.0f", ((end - start) / (double)numLoops));
			
			System.out.println();
		}
	}
	
	/** ... **/
	public static void generateAcyclic(int size, int mult, List<Integer> sources, List<Integer> dests)
	{
		sources.clear();
		dests.clear();
		
		for (int i = 1; i < size; i++)
		{
			for (int j = 0; j < mult; j++)
			{
				sources.add(i);
				dests.add(randomInt(i, size));
			}
		}
	}
	
	public static int randomInt(int min, int max)
	{
		return (int)(Math.floor(Math.random() * (max - min) + 1)) + min;
	}
	
	/**
	 * Builds and returns the DOT script of a graph defined by a list of edges.
	 * 
	 * @param sources The sources of the edges in the graph.
	 * @param dests   The destinations of the edges in the graph.
	 * @return The DOT script of the given graph.
	 */
	public static <T> String toDot(List<T> sources, List<T> dests)
	{
		StringBuilder dot = new StringBuilder("digraph D {\n");
		
		for (int i = 0; i < sources.size(); i++)
			dot.append(String.format("  \"%s\" -> \"%s\"\n", sources.get(i), dests.get(i)));
		
		dot.append("}");
		
		return dot.toString();
	}
}
