package demos.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Experiments and explores how the methods of a binary search tree can be done recursively and
 * how better it would do over methods being done iteratively.
 * 
 * @author Qianlang Chen
 * @version T 03/19/19
 */
public class RecursiveBinarySearchTreeDemo
{
	/** ... **/
	public static void main(String[] args)
	{
		// ---- TESTS ----
		//
		// This is the tree used in testing:
		//
		// .....16
		// ..../..\
		// .../....\
		// ../......\
		// .8........24
		// ..\....../
		// ...12..20
		// ../......\
		// 10........22
		// ..\....../
		// ...11..21
		
		IBST ibst = new IBST(16, 8, 24, 12, 20, 10, 22, 11, 21);
		
		System.out.println(ibst);
		System.out.println("add(314159): " + ibst.add(314159));
		System.out.println("add(314159): " + ibst.add(314159));
		System.out.println(ibst);
		System.out.println("contains(314159): " + ibst.contains(314159));
		System.out.println("contains(0): " + ibst.contains(0));
		System.out.println("remove(16): " + ibst.remove(16));
		System.out.println(ibst);
		System.out.println("remove(20): " + ibst.remove(20));
		System.out.println(ibst);
		System.out.println("size: " + ibst.size);
		
		System.out.println("\n================================================\n");
		
		RBST rbst = new RBST(16, 8, 24, 12, 20, 10, 22, 11, 21);
		
		System.out.println(rbst);
		System.out.println("add(314159): " + rbst.add(314159));
		System.out.println("add(314159): " + rbst.add(314159));
		System.out.println(rbst);
		System.out.println("contains(314159): " + rbst.contains(314159));
		System.out.println("contains(0): " + rbst.contains(0));
		System.out.println("remove(16): " + rbst.remove(16));
		System.out.println(rbst);
		System.out.println("remove(20): " + rbst.remove(20));
		System.out.println(rbst);
		System.out.println("size: " + rbst.size);
	}
	
	/** Represents an iterative binary search tree (that stores integers only). **/
	private static class IBST
	{
		/** Creates a new <code>IBST</code> to store some initial values. **/
		public IBST(int ...args)
		{
			for (int i : args)
				add(i);
		}
		
		/**  **/
		public int size;
		
		/**  **/
		private Node root;
		
		/** Adds a value to this tree (if the value does not exist). **/
		public boolean add(int value)
		{
			if (root == null)
			{
				root = new Node(value);
				size++;
				
				return true;
			}
			
			Node curr = root;
			
			// search for the appropriate spot to add the node
			while (true)
			{
				if (curr.value == value) // duplicates are not allowed
					return false;
				
				if (curr.value > value)
				{
					if (curr.left == null)
					{
						curr.left = new Node(value);
						
						break;
					}
					
					curr = curr.left;
				}
				else // if (curr.value < value)
				{
					if (curr.right == null)
					{
						curr.right = new Node(value);
						
						break;
					}
					
					curr = curr.right;
				}
			}
			
			size++;
			
			return true;
		}
		
		/** Checks if this tree contains some value. **/
		public boolean contains(int value)
		{
			if (root == null)
				return false;
			
			Node curr = root;
			
			while (curr != null)
			{
				if (curr.value == value)
					return true;
				
				if (curr.value > value)
					curr = curr.left;
				else
					curr = curr.right;
			}
			
			return false;
		}
		
		/** Removes a value from this tree if it exists. **/
		public boolean remove(int value)
		{
			if (root == null)
				return false;
			
			Node curr = root, prev = null;
			
			// search for the node to remove
			while (curr.value != value)
			{
				prev = curr;
				
				if (curr.value > value)
					curr = curr.left;
				else
					curr = curr.right;
				
				if (curr == null)
					return false;
			}
			
			// when the node is a leaf
			if (curr.left == null && curr.right == null)
			{
				if (prev == null)
					root = null;
				else if (prev.left == curr)
					prev.left = null;
				else
					prev.right = null;
			}
			else
			{
				curr.value = remove(curr);
			}
			
			size--;
			
			return true;
		}
		
		/** Finds the successor of a node going to be removed. **/
		private int remove(Node curr)
		{
			boolean goLeft;
			
			// curr has to have at least one child
			if (curr.left == null)
			{
				goLeft = true;
				curr = curr.right;
			}
			else
			{
				goLeft = false;
				curr = curr.left;
			}
			
			Node prev = null;
			
			// search for the extreme value in either tree
			while ((goLeft ? curr.left : curr.right) != null)
			{
				prev = curr;
				
				if (goLeft)
					curr = curr.left;
				else
					curr = curr.right;
			}
			
			// remove the successor we find
			if (goLeft)
				prev.left = curr.right;
			else
				prev.right = curr.left;
			
			return curr.value;
		}
		
		/**  **/
		public List<Integer> toList()
		{
			return toList(root, new ArrayList<>());
		}
		
		/**  **/
		private List<Integer> toList(Node curr, List<Integer> list)
		{
			if (curr != null)
			{
				toList(curr.left, list);
				list.add(curr.value);
				toList(curr.right, list);
			}
			
			return list;
		}
		
		@Override
		public String toString()
		{
			return toList().toString();
		}
		
		/** Represents a node in this tree. **/
		private class Node
		{
			/**  **/
			public Node(int value)
			{
				this.value = value;
			}
			
			/**  **/
			int value;
			
			/**  **/
			Node left, right;
		}
	}
	
	/*
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////
	 */
	
	/** Represents a recursive binary search tree (that stores integers only). **/
	private static class RBST
	{
		/** Creates a new <code>RBST</code> to store some initial values. **/
		public RBST(int ...args)
		{
			for (int i : args)
				add(i);
		}
		
		/**  **/
		public int size;
		
		/**  **/
		private Node root;
		
		/** Adds a value to this tree (if the value does not exist). **/
		public boolean add(int value)
		{
			if (root == null)
			{
				root = new Node(value);
				size++;
				
				return true;
			}
			
			return root.add(value);
		}
		
		/** Checks if this tree contains some value. **/
		public boolean contains(int value)
		{
			if (root == null)
				return false;
			
			return root.contains(value);
		}
		
		/** Removes a value from this tree if it exists. **/
		public boolean remove(int value)
		{
			if (root == null)
				return false;
			
			return root.remove(value, null);
		}
		
		/**  **/
		public List<Integer> toList()
		{
			return toList(root, new ArrayList<>());
		}
		
		/**  **/
		private List<Integer> toList(Node curr, List<Integer> list)
		{
			if (curr != null)
			{
				toList(curr.left, list);
				list.add(curr.value);
				toList(curr.right, list);
			}
			
			return list;
		}
		
		@Override
		public String toString()
		{
			return toList().toString();
		}
		
		/** Represents a node in this tree. **/
		private class Node
		{
			/**  **/
			public Node(int value)
			{
				this.value = value;
			}
			
			/**  **/
			int value;
			
			/**  **/
			Node left, right;
			
			/**  **/
			public boolean add(int value)
			{
				if (value == this.value)
					return false;
				
				if (value < this.value)
				{
					if (left != null)
						return left.add(value);
					
					left = new Node(value);
				}
				else // if (value > this.value)
				{
					if (right != null)
						return right.add(value);
					
					right = new Node(value);
				}
				
				size++;
				
				return true;
			}
			
			/**  **/
			public boolean contains(int value)
			{
				if (value == this.value)
					return true;
				
				if (value < this.value)
					return left != null && left.contains(value);
				
				return right != null && right.contains(value);
			}
			
			/**  **/
			public boolean remove(int value, Node parent)
			{
				// search for the node to remove
				if (value != this.value)
				{
					if (value < this.value)
						return left != null && left.remove(value, this);
					
					return right != null && right.remove(value, this);
				}
				
				// if this node is a leaf
				if (left == null && right == null)
				{
					if (parent == null)
						root = null;
					else if (parent.left == this)
						parent.left = null;
					else
						parent.right = null;
				}
				else // at least one child is not null at this point
				{
					if (left != null)
						this.value = left.removeRight(left);
					else
						this.value = right.removeLeft(right);
				}
				
				size--;
				
				return true;
			}
			
			/** Removes the left-most child node of this node. **/
			public int removeLeft(Node parent)
			{
				if (left != null)
					return left.removeLeft(this);
				
				parent.left = right;
				
				return value;
			}
			
			/** Removes the right-most child node of this node. **/
			public int removeRight(Node parent)
			{
				if (right != null)
					return right.removeRight(this);
				
				parent.right = left;
				
				return value;
			}
		}
	}
}
