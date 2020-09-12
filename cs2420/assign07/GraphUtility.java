package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed, unweighted, sparse
 * graphs.
 * 
 * @author Erin Parker, Kevin Song, and Qianlang Chen
 * @version M 03/04/19
 */
public class GraphUtility
{
	/**
	 * Checks and returns <code>true</code> if the graph with edges defined by lists of sources and
	 * destinations is cyclic (i.e. contains a cycle).
	 * 
	 * @param sources The list of sources of the edges in the graph.
	 * @param dests   The list of destinations of the edges in the graph.
	 * @return <code>true</code> if the graph is cyclic; otherwise <code>false</code>.
	 * @throws IllegalArgumentException If the sizes of the two lists do not match.
	 */
	public static <T> boolean isCyclic(List<T> sources, List<T> dests) throws IllegalArgumentException
	{
		if (sources.size() == 0 && dests.size() == 0)
			return false; // empty graphs are always acyclic
			
		Graph<T> graph = new Graph<T>(sources, dests);
		
		return graph.isCyclic();
	}
	
	/**
	 * Checks and returns <code>true</code> if two vertices in the graph with edges defined by lists
	 * of sources and destinations are connected (i.e. have a path between them).
	 * 
	 * @param sources    The list of sources of the edges in the graph.
	 * @param dests      The list of destinations of the edges in the graph.
	 * @param sourceData The source vertex to check.
	 * @param destData   The destination vertex to check.
	 * @return <code>true</code> if <code>sourceData</code> and <code>destData</code> are connected
	 *         in the graph; otherwise <code>false</code>.
	 * @throws IllegalArgumentException If the sizes of the two lists do not match, if the lists are
	 *                                  empty, or if either of the two data arguments is null.
	 */
	public static <T> boolean areConnected(List<T> sources, List<T> dests, T sourceData, T destData)
		throws IllegalArgumentException
	{
		if (sources.size() == 0 && dests.size() == 0)
			throw new IllegalArgumentException("The lists must not be empty.");
		
		Graph<T> graph = new Graph<T>(sources, dests);
		
		return graph.areConnected(sourceData, destData);
	}
	
	/**
	 * Performs a Topological Sort on an acyclic graph with edges defined by lists of sources and
	 * destinations.
	 * 
	 * @param sources The list of sources of the edges in the graph.
	 * @param dests   The list of destinations of the edges in the graph.
	 * @return The list of data in the graph, sorted topologically.
	 * @throws IllegalArgumentException If the sizes of the two lists do not match, or if the
	 *                                  provided graph is cyclic.
	 */
	public static <T> List<T> sort(List<T> sources, List<T> dests) throws IllegalArgumentException
	{
		Graph<T> graph = new Graph<T>(sources, dests);
		
		return graph.topologicalSort();
	}
	
	/**
	 * Builds "sources" and "destinations" lists according to the edges specified in the given DOT
	 * file (e.g., "a -> b"). Assumes that the vertex data type is String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas). --accepts \\-style
	 * comments --accepts one edge per line or edges terminated with ; --does not accept attributes
	 * in [] (e.g., [label = "a label"])
	 * 
	 * @param filename     Name of the DOT file
	 * @param sources      Empty ArrayList, when method returns it is a valid "sources" list that
	 *                     can be passed to the public methods in this class
	 * @param destinations Empty ArrayList, when method returns it is a valid "destinations" list
	 *                     that can be passed to the public methods in this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations)
	{
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		scan.useDelimiter(";|\n");
		
		// Determine if graph is directed (i.e., look for "digraph id {").
		
		String line = "", edgeOp = "";
		
		while (scan.hasNext())
		{
			line = scan.next();
			
			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
			
			if (line.indexOf("digraph") >= 0)
			{
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				
				break;
			}
		}
		
		if (edgeOp.equals(""))
		{
			System.out.println("DOT graph must be directed (i.e., digraph).");
			
			scan.close();
			System.exit(0);
		}
		
		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		
		while (scan.hasNext())
		{
			String[] substring = line.split(edgeOp);
			
			for (int i = 0; i < substring.length - 1; i += 2)
			{
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				
				// if string is empty, try again
				if (vertex1.equals(""))
					continue;
				
				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				
				if (vertex2.equals(""))
					continue;
				
				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}
			
			// do until the "}" has been read
			if (substring[substring.length - 1].indexOf("}") >= 0)
				break;
			
			line = scan.next();
			
			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
		}
		
		scan.close();
	}
	
	/**
	 * Represents a collection of vertices and directed unweighted edges.
	 * 
	 * @param <T> The type of data stored by the vertices in this graph.
	 */
	private static class Graph<T>
	{
		/** The list of vertices. **/
		private HashMap<T, Vertex<T>> vertices;
		
		/**
		 * Creates a <code>Graph</code> instance.
		 * 
		 * @param sources The list of sources of the edges in the graph.
		 * @param dests   The list of destinations of the edges in the graph.
		 * @throws IllegalArgumentException If the sizes of the two lists do not match.
		 */
		public Graph(List<T> sources, List<T> dests) throws IllegalArgumentException
		{
			if (sources.size() != dests.size())
				throw new IllegalArgumentException("The sizes of the two lists must match.");
			
			vertices = new HashMap<T, Vertex<T>>();
			
			for (int i = 0; i < sources.size(); i++)
			{
				Vertex<T> source = getVertex(sources.get(i));
				Vertex<T> dest = getVertex(dests.get(i));
				
				source.adjacentVertices.put(dest.data, dest);
			}
		}
		
		/**
		 * Checks if a vertex storing some data exists in a hash-map and returns the vertex if it does;
		 * otherwise creates a new vertex to store the data and returns it.
		 * 
		 * @param vertices The hash-map to search the vertex in.
		 * @param data     The data to search for.
		 * @return The vertex storing <code>data</code>.
		 */
		private Vertex<T> getVertex(T data)
		{
			if (vertices.containsKey(data))
				return vertices.get(data);
			
			Vertex<T> v = new Vertex<T>(data);
			vertices.put(data, v);
			
			return v;
		}
		
		/**
		 * Checks and returns <code>true</code> if this graph is cyclic.
		 * 
		 * @return <code>true</code> is this graph is cyclic; otherwise <code>false</code>.
		 */
		public boolean isCyclic()
		{
			for (Vertex<T> w : vertices.values())
			{
				if (w.distance == -1)
				{
					w.distance = 0;
					
					if (isCyclic(w))
						return true;
				}
			}
			
			return false;
		}
		
		/**
		 * Uses a Depth-First Search to check if a cycle exists for a given starting vertex.
		 * 
		 * @param v The vertex to check.
		 * @return <code>true</code> if a cycle exists; otherwise <code>false</code>.
		 */
		private boolean isCyclic(Vertex<T> v)
		{
			v.isVisited = true;
			
			for (Vertex<T> w : v.adjacentVertices.values())
			{
				if (w.isVisited)
					return true;
				
				w.distance = v.distance + 1;
				
				if (isCyclic(w))
					return true;
			}
			
			v.isVisited = false;
			
			return false;
		}
		
		/**
		 * Uses a Breadth-First Search to check if two vertices are connected.
		 * 
		 * @param source The source vertex.
		 * @param dest   The destination vertex.
		 * @return <code>true</code> if the two vertices are connected; otherwise <code>false</code>.
		 */
		public boolean areConnected(T sourceData, T destData)
		{
			Vertex<T> source = vertices.get(sourceData);
			Vertex<T> dest = vertices.get(destData);
			
			if (source == null || dest == null)
				throw new IllegalArgumentException("The source and the destination must exist in the graph.");
			
			Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
			
			queue.offer(source);
			
			while (!queue.isEmpty())
			{
				Vertex<T> v = queue.poll();
				
				for (Vertex<T> w : v.adjacentVertices.values())
				{
					if (w == dest)
						return true;
					
					if (w.isVisited)
						continue;
					
					w.isVisited = true;
					
					queue.offer(w);
				}
			}
			
			return false;
		}
		
		/**
		 * Performs a Topological Sort on this graph.
		 * 
		 * @return The topologically-sorted list of the data in the vertices.
		 * @throws IllegalArgumentException If the provided graph is cyclic.
		 */
		public List<T> topologicalSort() throws IllegalArgumentException
		{
			calculateIndegrees(vertices);
			
			Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
			
			for (Vertex<T> v : vertices.values())
				if (v.indegree == 0)
					queue.offer(v);
				
			LinkedList<T> sortedList = new LinkedList<T>();
			int maxSize = vertices.values().size();
			
			while (!queue.isEmpty())
			{
				Vertex<T> v = queue.poll();
				
				sortedList.add(v.data);
				
				for (Vertex<T> w : v.adjacentVertices.values())
				{
					w.indegree--;
					
					if (w.indegree == 0)
						queue.offer(w);
				}
			}
			
			// The sorted list should contain exactly same number of vertices as the graph has, or the graph must
			// contain a cycle.
			if (sortedList.size() != maxSize)
				throw new IllegalArgumentException("The graph must be acyclic.");
			
			return sortedList;
		}
		
		/**
		 * Calculates the in-degree for each vertex in a graph. The in-degree of a vertex is equal to
		 * the number of edges that point to (not away from) the vertex.
		 * 
		 * @param vertices The list of vertices in the graph.
		 */
		private void calculateIndegrees(HashMap<T, Vertex<T>> vertices)
		{
			for (Vertex<T> v : vertices.values())
				for (Vertex<T> w : v.adjacentVertices.values())
					w.indegree++;
		}
		
		/**
		 * Builds and returns the DOT script representation of this graph.
		 * 
		 * @return A string containing the DOT script of this graph.
		 */
		@Override
		public String toString()
		{
			StringBuilder dot = new StringBuilder("digraph D {\n");
			
			for (Vertex<T> v : vertices.values())
				for (Vertex<T> w : v.adjacentVertices.values())
					dot.append(String.format("  \"%s\" -> \"%s\"\n", v.data, w.data));
				
			dot.append("}");
			
			return dot.toString();
		}
		
		/**
		 * Represents a vertex in this graph.
		 * 
		 * @param <S> The type of the data stored in this vertex.
		 */
		private class Vertex<S>
		{
			/** The data stored in this vertex. **/
			public S data;
			
			/** Indicates whether the vertex has been visited. **/
			public boolean isVisited;
			
			/** The distance to this vertex from some starting vertex. **/
			public int distance;
			
			/** The hash-map containing all adjacent vertices. **/
			public HashMap<S, Vertex<S>> adjacentVertices;
			
			/** The in-degree of this vertex (i.e. number of edges pointing to this vertex). **/
			public int indegree;
			
			/**
			 * Creates a new <code>Vertex</code> instance.
			 * 
			 * @param data The data to store in this vertex.
			 */
			public Vertex(S data)
			{
				this.data = data;
				
				distance = -1;
				indegree = 0;
				adjacentVertices = new HashMap<S, Vertex<S>>();
			}
			
			@Override
			public String toString() // for debugging purposes
			{
				return data.toString();
			}
		}
	}
}
