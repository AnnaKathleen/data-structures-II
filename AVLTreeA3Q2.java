/** Assignment 2, Q2
* July 25 2018
* for AU COMP 272
* @author: Anna Rabatich 
* student no. 3314679
*/


// Java program that implements BSTs/AVL trees and can perform single rotations left or right
//to create a new BST containing the same elements as the original tree
class Node {
    int data, height;
    Node left, right;
 
    Node(int d) {
        data = d;
        height = 1;
    }
}
 
class AVLTreeA3Q2 {
 
    Node root;
 
    // A utility function to get the height of the tree
    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }
 
    // A utility function to find max of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }
 
   /** A function to perform a single right AVL rotation on the subtree rooted at y
  * @param y represents a node passed in as the root of the subtree we will rotate 
  * @return the node representing the new root of the subtree
  */
   Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
 
        // Perform rotation
        x.right = y;
        y.left = T2;
 
        // Update heights: not really necessary for a single rotate but useful for insertion function
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
 
        // Return new root
        return x;
    }
 
   /** A function to perform a single left AVL rotation on the subtree rooted at x
  * @param x represents a node passed in as the root of the subtree we will rotate 
  * @return the node representing the new root of the subtree
  */
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
 
        // rotation
        y.left = x;
        x.right = T2;
 
        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
 
        // Return new root
        return y;
    }
 
    // Get Balance factor of a node
    int getBalance(Node node) {
        if (node == null)
            return 0;
 
        return height(node.left) - height(node.right);
    }

    /** A function to insert elements into a binary search tree
  * @param node represents the root passed in, from which we will traverse to find insertion point
  * @param data represent the integer value we want to insert into the tree
  * @return the node representing the new root after performing the insertion
  */
    Node insert(Node node, int data) {
 
        //regular BST insertion
        if (node == null)
            return (new Node(data));
 
        if (data < node.data)
            node.left = insert(node.left, data);
        else if (data > node.data)
            node.right = insert(node.right, data);
        else 
            return node;
 
        //2. update height
        node.height = 1 + max(height(node.left),
                              height(node.right));
 
        //3. find balance of ancestor node to determine if it's unbalanced. 
        int balance = getBalance(node);
 
        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && data < node.left.data)
            return rightRotate(node);
 
        // Right Right Case
        if (balance < -1 && data > node.right.data)
            return leftRotate(node);
 
        // Left Right Case
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
 
        // Right Left Case
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        } 
 
        //return unchanged node
        return node; 
    }

    //A constant function that returns true if given search tree is a BST that satisfies the search tree order property
   boolean isBST()  {
        return isBSTRecur(root, Integer.MIN_VALUE, Integer.MAX_VALUE); 
        //finds the max and min data values in the whole tree which we will use in our recursive function below
    }
 
    /** A recursive function that tests if a given tree is a binary search tree
  * @param node represents the root passed in
  * @param min represent the minimum integer value found in the tree
  * @param max represents the maximum integer value found in the tree
  * @return boolean true if the tree is BST, false otherwise
  */
    boolean isBSTRecur(Node node, int min, int max)
    {
        //case 1: an empty tree, root is null
        if (node == null)
            return true; //is a BST, returns true
 
        // false if this node violates the min/max constraints 
        //as we know the root's data should be greater than the min and smaller than the max
        if (node.data < min || node.data > max)
            return false;
 
       //otherwise recursively check subtrees, narrowing the min/max constraints. 
        return (isBSTRecur(node.left, min, node.data-1) && 
            isBSTRecur(node.right, node.data+1, max));
    }
 
    // A utility function to print preorder traversal
    // of the tree.
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
 
    public static void main(String[] args) {
        AVLTreeA3Q2 tree = new AVLTreeA3Q2();
 
        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);
 
        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        System.out.println("Preorder traversal of tree is : ");
        tree.preOrder(tree.root);

        System.out.println("\nroot is " + tree.root.data);

        System.out.println( "is tree a BST? " + tree.isBST());

        System.out.println("rotating right at root ");
        //Node hold = tree.root.left;
        tree.root = tree.rightRotate(tree.root);

        //tree.root.left = rightRotate(tree.root.left);

        tree.preOrder(tree.root);

        System.out.println("\nroot is " + tree.root.data);

        System.out.println("\nroot.right is " + tree.root.right.data);
        System.out.println("root.left is " + tree.root.left.data);

        System.out.println("root.right.left is " + tree.root.right.left.data);
        System.out.println( "is tree a BST? " + tree.isBST());

       // System.out.println("rotating left at root.right ");

       // tree.root.right = tree.leftRotate(tree.root.right);
        //tree.preOrder(tree.root);
    }
}