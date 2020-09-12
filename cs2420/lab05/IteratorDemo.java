package lab05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import assign01.MathVector;

/**
 * This class demonstrates how to use iterators, for Lab 5.
 * 
 * @author Ryan Sargent & Erin Parker
 * @version February 8, 2019
 */
public class IteratorDemo
{
	public static void main(String[] args)
	{
		SortedSet<Integer> ss = new TreeSet<>();
		
		// How we like to see this
		for (int element : ss)
		{
			System.out.println(element);
		}
		
		// But this is what it is really doing
		Iterator<Integer> iter = ss.iterator();
		while (iter.hasNext())
		{
			int element = iter.next();
			System.out.println(element);
		}
		
		// Same thing, different loop
		for (Iterator<Integer> it = ss.iterator(); it.hasNext();)
		{
			int element = it.next();
			System.out.println(element);
		}
		
		// this works for ANY collection:
		Collection<Integer> collection = new ArrayList<>();
		collection = new LinkedList<Integer>();
		collection = new HashSet<>();
		collection = new Vector<>();
		collection = new PriorityQueue<>();
		
		Queue<Integer> q = new LinkedList<>();
		q = new PriorityQueue<>();
		
		Stack<Integer> stack = new Stack<>();
		
		Iterator<Integer> iterator;
		iterator = collection.iterator();
		iterator = q.iterator();
		iterator = stack.iterator();
		
		for (int whatever : collection)
		{
			System.out.println(whatever);
		}
		
		for (int something : q)
		{
			System.out.println(q);
		}
		
		for (int nothing : stack)
		{
			System.out.println(stack);
		}
		
		/* ################################### */
		
		ArrayList<MathVector> mathVectors = new ArrayList<MathVector>();
		mathVectors.add(new MathVector(new double[][] {{2, 3, 5, 7, 11}}));
		mathVectors.add(new MathVector(new double[][] {{2}, {3}, {5}, {7}, {11}}));
		mathVectors.add(new MathVector(new double[][] {{1}}));
		mathVectors.add(new MathVector(new double[][] {{1, 1}}));
		mathVectors.add(new MathVector(new double[][] {{1}, {1}}));
		
		for (MathVector mathVector : mathVectors)
			System.out.println(mathVector + "\n--------");
		
		for (MathVector mathVector : mathVectors)
			mathVector.transpose();
		
		System.out.println("============================");
		
		for (MathVector mathVector : mathVectors)
			System.out.println(mathVector + "\n--------");
		
		/* ################################### */
		
		
	}
}
