package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Represents a binary search tree (BST) that stores some type of data.
 * 
 * @author Qianlang Chen and Kevin Song
 * @version March 20, 2019
 *
 * @param <E> - The type of data stored in this BST.
 */
public class BinarySearchTree<E extends Comparable<? super E>> implements SortedSet<E>
{
	/** The root node of this tree. **/
	private Node<E> root;
	
	/** The number of items in this tree. **/
	private int size;
	
	/** Creates a new <code>BinarySearchTree</code> instance. **/
	public BinarySearchTree()
	{
	}
	
	/**
	 * Ensures that this BST contains the specified item.
	 * 
	 * @param item The item whose presence is ensured in this BST.
	 * @return <code>true</code> if this BST changed as a result of this method call (that is, if
	 *         the input item was actually inserted); otherwise <code>false</code>.
	 */
	@Override
	public boolean add(E item)
	{
		if (isEmpty())
		{
			root = new Node<E>(item);
			size++;
			
			return true;
		}
		
		return root.add(item);
	}
	
	/**
	 * Ensures that this BST contains all items in the specified collection.
	 * 
	 * @param items The collection of items whose presence is ensured in this BST.
	 * @return <code>true</code> if this BST changed as a result of this method call (that is, if
	 *         any item in the input collection was actually inserted); otherwise
	 *         <code>false</code>.
	 */
	@Override
	public boolean addAll(Collection<? extends E> items)
	{
		boolean hasChanged = false;
		
		for (E item : items)
			hasChanged |= add(item);
		
		return hasChanged;
	}
	
	/** Removes all items from this BST. The BST will be empty after this method call. **/
	@Override
	public void clear()
	{
		root = null;
		size = 0;
	}
	
	/**
	 * Determines if there is an item in this BST that is equal to the specified item.
	 * 
	 * @param item The item sought in this BST.
	 * @return <code>true</code> if there is an item in this BST that is equal to the input item;
	 *         otherwise <code>false</code>.
	 */
	@Override
	public boolean contains(E element)
	{
		if (isEmpty())
			return false;
		
		return root.contains(element);
	}
	
	/**
	 * Determines if for each item in the specified collection, there is an item in this BST that is
	 * equal to it.
	 * 
	 * @param items The collection of items sought in this BST.
	 * @return <code>true</code> if for each item in the specified collection, there is an item in
	 *         this BST that is equal to it; otherwise <code>false</code>.
	 */
	@Override
	public boolean containsAll(Collection<? extends E> items)
	{
		for (E item : items)
			if (!contains(item))
				return false;
			
		return true;
	}
	
	/**
	 * Returns the first (i.e., smallest) item in this BST.
	 * 
	 * @return The first item in this BST.
	 * @throws NoSuchElementException If the BST is empty.
	 */
	@Override
	public E first() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException("There is no item in this binary search tree.");
		
		return root.first();
	}
	
	/**
	 * Returns the last (i.e., largest) item in this BST.
	 * 
	 * @return The last item in this BST.
	 * @throws NoSuchElementException if the BST is empty.
	 */
	@Override
	public E last() throws NoSuchElementException
	{
		if (isEmpty())
			throw new NoSuchElementException("There is no item in this binary search tree.");
		
		return root.last();
	}
	
	/**
	 * Ensures that this BST does not contain the specified item.
	 * 
	 * @param item The item whose absence is ensured in this BST.
	 * @return <code>true</code> if this BST changed as a result of this method call (that is, if
	 *         the input item was actually removed); otherwise <code>false</code>.
	 */
	@Override
	public boolean remove(E element)
	{
		if (isEmpty())
			return false;
		
		return root.remove(element, null);
	}
	
	/**
	 * Ensures that this BST does not contain any of the items in the specified collection.
	 * 
	 * @param items The collection of items whose absence is ensured in this BST.
	 * @return <code>true</code> if this BST changed as a result of this method call (that is, if
	 *         any item in the input collection was actually removed); otherwise <code>false</code>.
	 */
	@Override
	public boolean removeAll(Collection<? extends E> items)
	{
		boolean hasChanged = false;
		
		for (E item : items)
			hasChanged |= remove(item);
		
		return hasChanged;
	}
	
	/**
	 * Returns the number of items in this BST.
	 * 
	 * @return The number of items in this BST.
	 */
	@Override
	public int size()
	{
		return size;
	}
	
	/**
	 * Returns <code>true</code> if this BST contains no items.
	 * 
	 * @return <code>true</code> if this BST is empty; otherwise <code>false</code>.
	 */
	@Override
	public boolean isEmpty()
	{
		return root == null;
	}
	
	/**
	 * Returns an <code>ArrayList</code> containing all of the items in this BST, in sorted order.
	 * 
	 * @return The <code>ArrayList</code> containing the sorted items in this BST.
	 */
	@Override
	public ArrayList<E> toArrayList()
	{
		return toArrayList(root, new ArrayList<E>());
	}
	
	/**
	 * Adds the left and right nodes of a given node to a list In-Order.
	 * 
	 * @param curr      The current node to add.
	 * @param arrayList The list to add the elements in nodes.
	 * @return The list with the node itself, its left node, and its right node added.
	 */
	private ArrayList<E> toArrayList(Node<E> curr, ArrayList<E> list)
	{
		if (curr != null)
		{
			toArrayList(curr.left, list);
			list.add(curr.element);
			toArrayList(curr.right, list);
		}
		
		return list;
	}
	
	/**
	 * Returns the DOT script of this <code>BinarySearchTree</code>.
	 * 
	 * @return A <code>String</code> containing the DOT script of this BST.
	 */
	public String toDot()
	{
		StringBuilder dot = new StringBuilder("digraph D {\n");
		Stack<Node<E>> stack = new Stack<Node<E>>();
		
		if (!isEmpty())
			stack.push(root);
		
		while (!stack.isEmpty())
		{
			Node<E> curr = stack.pop();
			
			if (curr.left != null)
			{
				dot.append(String.format("  \"%s\"%s -> \"%s\"\n", curr.element, (curr.right == null ? ":sw" : ""),
					curr.left.element));
				stack.push(curr.left);
			}
			
			if (curr.right != null)
			{
				dot.append(String.format("  \"%s\"%s -> \"%s\"\n", curr.element, (curr.left == null ? ":se" : ""),
					curr.right.element));
				stack.push(curr.right);
			}
		}
		
		dot.append("}");
		
		return dot.toString();
	}
	
	/**
	 * Returns the string representation of this BST.
	 * 
	 * @return The string containing the string representation of each item in this BST In-Order.
	 */
	@Override
	public String toString()
	{
		return toArrayList().toString();
	}
	
	/**
	 * Represents a node for the binary search tree.
	 *
	 * @param <T> The generic type.
	 */
	private class Node<T extends Comparable<? super T>>
	{
		/** The value stored in this node. **/
		public T element;
		
		/** The left child node. **/
		public Node<T> left;
		
		/** The right child node. **/
		public Node<T> right;
		
		/**
		 * Creates a new <code>Node</code> instance.
		 * 
		 * @param element The element stored in this node.
		 */
		public Node(T element)
		{
			this.element = element;
		}
		
		/**
		 * Returns the first (smallest) element in this sub-tree.
		 * 
		 * @return The first element in this sub-tree.
		 */
		public T first()
		{
			if (left == null)
				return element;
			
			return left.first();
		}
		
		/**
		 * Returns the last (greatest) element in this sub-tree.
		 * 
		 * @return The last element in this sub-tree.
		 */
		public T last()
		{
			if (right == null)
				return element;
			
			return right.last();
		}
		
		/**
		 * Adds a new element, if it does not exist, into this sub-tree as a new node.
		 * 
		 * @param element The element to add.
		 * @return <code>true</code> if <code>element</code> is added into this sub-tree; otherwise
		 *         <code>false</code>.
		 */
		public boolean add(T element)
		{
			if (element.equals(this.element))
				return false;
			
			if (element.compareTo(this.element) < 0)
			{
				if (left != null)
					return left.add(element);
				
				left = new Node<T>(element);
			}
			else // if (value > this.value)
			{
				if (right != null)
					return right.add(element);
				
				right = new Node<T>(element);
			}
			
			size++;
			
			return true;
		}
		
		/**
		 * Checks and returns <code>true</code> if an element exists in this sub-tree.
		 * 
		 * @param element The element to check.
		 * @return <code>true</code> if <code>element</code> exists in this sub-tree; otherwise
		 *         <code>false</code>.
		 */
		public boolean contains(T element)
		{
			if (element.equals(this.element))
				return true;
			
			if (element.compareTo(this.element) < 0)
				return left != null && left.contains(element);
			
			return right != null && right.contains(element);
		}
		
		/**
		 * Removes an <code>element</code> from this sub-tree, if it exists.
		 * 
		 * @param element The element to remove.
		 * @param parent  The node whose left or right child is this node.
		 * @return <code>true</code> if <code>element</code> exists in this sub-tree and is removed;
		 *         otherwise <code>false</code>.
		 */
		public boolean remove(T element, Node<T> parent)
		{
			// search for the node to remove
			if (!element.equals(this.element))
			{
				if (element.compareTo(this.element) < 0)
					return left != null && left.remove(element, this);
				
				return right != null && right.remove(element, this);
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
					this.element = left.removeRight(this);
				else
					this.element = right.removeLeft(this);
			}
			
			size--;
			
			return true;
		}
		
		/**
		 * Removes the left-most child node of this node and returns the value it is holding.
		 * 
		 * @param parent The node whose left or right child is this node.
		 * @return The value that the left-most child node holds.
		 */
		private T removeLeft(Node<T> parent)
		{
			if (left != null)
				return left.removeLeft(this);
			
			if (this == parent.right) // this node is the direct (and only) child of the node to remove
				parent.right = right;
			else
				parent.left = right;
			
			return element;
		}
		
		/**
		 * Removes the right-most child node of this node and returns the value it is holding.
		 * 
		 * @param parent The node whose left or right child is this node.
		 * @return The value that the right-most child node holds.
		 */
		private T removeRight(Node<T> parent)
		{
			if (right != null)
				return right.removeRight(this);
			
			if (this == parent.left)
				parent.left = left;
			else
				parent.right = left;
			
			return element;
		}
	}
}
