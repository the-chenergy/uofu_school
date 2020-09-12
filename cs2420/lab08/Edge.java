package lab08;

/**
 * This class represents an edge between a source vertex and a destination vertex in a directed
 * graph.
 * 
 * The source of this edge is the Vertex whose object has an adjacency list containing this
 * edge.
 * 
 * @author Erin Parker
 * @version March 1, 2019
 */
public class Edge
{
	// destination of this directed edge
	private Vertex dst;
	
	private double weight;
	
	public Edge(Vertex dst, double weight)
	{
		this.dst = dst;
		this.weight = weight;
	}
	
	public Vertex getOtherVertex()
	{
		return this.dst;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public String toString()
	{
		return this.dst.getName();
	}
}
