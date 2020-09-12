package lab08;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a vertex (AKA node) in a directed graph. The vertex is not generic,
 * assumes that a string name is stored there.
 * 
 * @author Erin Parker
 * @version March 1, 2019
 */
public class Vertex implements Comparable<Vertex>
{
	// used to id the Vertex
	private String name;
	
	// adjacency list
	private LinkedList<Edge> adj;
	
	private boolean isVisited;
	
	private double distance;
	
	private Vertex prev;
	
	public Vertex(String name)
	{
		this.name = name;
		this.adj = new LinkedList<Edge>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addEdge(Vertex otherVertex, double weight)
	{
		adj.add(new Edge(otherVertex, weight));
	}
	
	public Iterator<Edge> edges()
	{
		return adj.iterator();
	}
	
	public double getDistanceFromStart()
	{
		return distance;
	}
	
	public void setDistanceFromStart(double value)
	{
		distance = value;
	}
	
	public Vertex getPrevious()
	{
		return prev;
	}
	
	public void setPrevious(Vertex value)
	{
		prev = value;
	}
	
	public boolean getVisited()
	{
		return isVisited;
	}
	
	public void setVisited(boolean value)
	{
		isVisited = value;
	}
	
	public ArrayList<Edge> getEdges()
	{
		return new ArrayList<Edge>(adj);
	}
	
	public String toString()
	{
		String s = "Vertex " + name + " adjacent to ";
		Iterator<Edge> itr = adj.iterator();
		while (itr.hasNext())
			s += itr.next() + "  ";
		return s;
	}
	
	@Override
	public int compareTo(Vertex o)
	{
		return (int)(getDistanceFromStart() - o.getDistanceFromStart());
	}
}
