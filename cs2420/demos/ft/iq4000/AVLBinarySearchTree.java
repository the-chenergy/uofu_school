package demos.ft.iq4000;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class represents a self-balancing binary search tree using the AVL method.
 * 
 * @author Eric O'Donoghue
 * @version June 17, 2019
 */
public class AVLBinarySearchTree<Type extends Comparable<? super Type>>
{
    /** Root node in the tree */
    private AVLNode root;

    /**
     * Creates a AVL binary search tree with no nodes
     */
    public AVLBinarySearchTree() {
        root = null;
    }
    
    /**
     * Adds a new node into the tree.
     * 
     * @param item element of the node being added
     * @return true if the item is added, otherwise returns false
     */
    public boolean add(Type item) {
        int temp = size(root);
        root = addRecursion(root, item);
        
        // Checks if the size of the tree has changed
        return size(root) - temp != 0;
    }

    /**
     * Recursive helper method for add. 
     * 
     * @param node node being tested for placement 
     * @param item element of the node being added
     * @return the node to be set as the node passed into the method
     */
    private AVLNode addRecursion (AVLNode node, Type item) {
        // Returns a new node to be set as the node passed into the method
        if (node == null) 
            return new AVLNode(item, 0, 1);
        
        // Recursively iterates through the tree until the correct place is found
        if (item.compareTo(node.element) < 0)
            node.left = addRecursion(node.left, item);
        else if (item.compareTo(node.element) > 0)
            node.right = addRecursion(node.right, item);
        else
            return node;
        
        // Updates the height and size of the current node
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;
        
        return balanceTree(node);
   }
   
    /**
     * Returns true if the tree contains the specified item, otherwise returns false.
     * 
     * @param item element whose presence is to be tested for
     * @return true if the element is present, otherwise returns false
     */
   public boolean contains(Type item) {
       return containsRecur(root, item);
   }
   
   /**
    * Recursive helper method for contains
    * 
    * @param node node to be tested for the element
    * @param item element whose presence is to be tested for
    * @return true of the element is present, otherwise false
    */
   private boolean containsRecur(AVLNode node, Type item) {
       // Iterates through the tree until the element is found or a null node is reached
       if (node == null)
           return false;
       else {           
           if (item.compareTo(node.element) < 0)
               return containsRecur(node.left, item);
           else if (item.compareTo(node.element) > 0)
               return containsRecur(node.right, item);
           else
               return true;
       }      
   }
   
   /**
    * Removes the node containing the given element 
    * 
    * @param item element in node to be removed
    */
   public boolean remove(Type item) {
       if (root == null)
           throw new NoSuchElementException("ERROR: BinarySearchTree is empty.");
       int temp = size(root);
       root = removeRecursion(root, item);
       return size(root) - temp != 0;
   }
   
   /**
    * Recursive helper method for remove.
    * 
    * @param node node to be tested for removal
    * @param item element of node being removed
    * @return the node to be set as the node passed into the method
    */
   private AVLNode removeRecursion(AVLNode node, Type item) {
       if (item.compareTo(node.element) < 0) 
           node.left = removeRecursion(node.left, item);       
       else if (item.compareTo(node.element) > 0) 
           node.right = removeRecursion(node.right, item);       
       else {
           if (node.left == null) 
               return node.right;           
           else if (node.right == null) 
               return node.left;           
           else {
               AVLNode temp = node;
               node = minimum(temp.right);
               node.right = removeMinimum(temp.right);
               node.left = temp.left;
           }
       }
       
       node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
       node.size = size(node.left) + size(node.right) + 1; 
       return balanceTree(node);
   }
   
   /**
    * Returns the smallest element in the tree.
    * 
    * @return the smallest element in the tree
    */
   public Type minimum() {
       return minimum(root).element;
   }
   
   /**
    * Returns the smallest element in the subtree of the given node.
    * 
    * @param node the subtree
    * @return the smallest element in the subtree of the given node
    */
   private AVLNode minimum(AVLNode node) {
       if (root == null)
           throw new NoSuchElementException("ERROR: BinarySearchTree is empty.");
       
       // Iterates to the left most node 
       while (node.left != null) 
           node = node.left;
     
       return node;
   }
   
   /**
    * Removes the smallest element in the subtree of the given node.
    * 
    * @param node the subtree
    * @return the smallest node in the subtree of the given node
    */
   private AVLNode removeMinimum(AVLNode node) {
       if (node.left == null)
           return node.right;
       else
           node.left = removeMinimum(node.left);
       
       node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
       return balanceTree(node);
   }
   
   /**
    * Returns the largest element in the tree.
    * 
    * @param node the subtree
    * @return the largest element in the subtree of the given node
    */
   public Type maximum() {
       return maximum(root).element;
   }
   
   /**
    * Returns the largest element in the subtree of the given node.
    * 
    * @param node the subtree
    * @return the largest element in the subtree of the given node
    */
   private AVLNode maximum(AVLNode node) {
       if (root == null)
           throw new NoSuchElementException("ERROR: BinarySearchTree is empty.");
       
       while (node.right != null) 
           node = node.right;
     
       return node;
   }
   
   /**
    * Checks if the tree is balanced based on AVL standards.
    * 
    * @return true if the tree is balanced, otherwise returns false
    */
   public boolean isBalanced() {      
       return isBalancedRecursion(root);
   }
   
   /**
    * Checks if the subtree of the given node is balanced based on AVL standards.
    * 
    * @param node the subtree
    * @return true if the subtree is balanced, otherwise returns false;
    */
   private boolean isBalancedRecursion(AVLNode node) {
       if (node == null)
           return true;
       else if (getBalanceFactor(node) > 1 || getBalanceFactor(node) < -1) 
           return false;
       else 
           return isBalancedRecursion(node.left) && isBalancedRecursion(node.right);
       
   }
   
   /**
    * Clears the tree.
    */
   public void clear() {
       root = null;
   }
   
   /**
    * Checks if the tree is empty.
    * 
    * @return true if the tree is empty, otherwise returns false
    */
   public boolean isEmpty() {
       return root == null;
   }
   
   /**
    * Returns the size of the tree.
    * 
    * @return the size of the tree
    */
   public int size() {
       return size(root);
   }
   
   /**
    * Returns the size of the subtree of the given node.
    * 
    * @param node the subtree
    * @return the size of the subtree of the given node.
    */
   private int size(AVLNode node) {
       if (node == null) 
           return 0;
       else
           return node.size;
   }
   
   /**
    * Returns an ArrayList containing all of the items in this set, in sorted order.
    * 
    * @return the in-order ArrayList
    */
   public ArrayList<Type> toArrayList() {    
       ArrayList<Type> arrList = new ArrayList<>();

       toArrayListRecur(root, arrList);
       return arrList;
   }

   /**
    * Recursive helper method that adds all the nodes to an ArrayList using
    * in-order traversal.
    */
   private void toArrayListRecur(AVLNode node, ArrayList<Type> arrList) {
       if (node == null)
           return;
       toArrayListRecur(node.left, arrList);
       arrList.add(node.element);
       toArrayListRecur(node.right, arrList);
   }
   
   /**
    * Balances the subtree of the given node according to AVL standards.
    * 
    * @param node the subtree
    * @return the node to be set as the node passed into the method
    */
   private AVLNode balanceTree(AVLNode node) {
        if (getBalanceFactor(node) > 1) {
            if (getBalanceFactor(node.left) < 0)
                node.left = leftRotate(node.left);
            node = rightRotate(node);
        }
        
        else if (getBalanceFactor(node) < -1) {
            if (getBalanceFactor(node.right) > 0)
                node.right = rightRotate(node.right);
            node = leftRotate(node);
        }
        
        return node;
    }
    
    private AVLNode leftRotate (AVLNode node) {
        AVLNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;        
        temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
        temp.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        
        return temp;
    }
    
    private AVLNode rightRotate (AVLNode node) {
        AVLNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;        
        temp.height = Math.max(getHeight(temp.left), getHeight(temp.right)) + 1;
        temp.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        
        return temp;
    }
    
    /**
     * Returns the height of the subtree of the given node.
     * 
     * @param node the subtree
     * @return the height of the subtree of the given node
     */
    private int getHeight(AVLNode node) {
        if(node == null)
            return -1;
        
        return node.height;
    }
    
    /**
     * Checks if the subtree of the given node is balanced according the AVL standard
     * 
     * @param node the subtree
     * @return
     */
    private int getBalanceFactor(AVLNode node) {
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Represents a node based on the AVL requirements.
     * 
     * @author Eric O'Donoghue
     * @version June 17, 2019
     */
    private class AVLNode {
        
        private Type element;
        private int height;
        private int size;
        private AVLNode left;
        private AVLNode right;
        
        /**
         * Creates a AVLNode.
         * 
         * @param element to be contained in the node
         * @param height the longest path from the node to a leaf 
         * @param size the number of nodes in the subtree
         */
        public AVLNode (Type element, int height, int size) {
            this.element = element;
            this.height = height;
            this.size = size;
        }
    }
    
    

    
}
