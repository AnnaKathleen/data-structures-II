/** Assignment 2, Q3
* August 2 2018
* for AU COMP 272
* @author: Anna Rabatich 
* student no. 3314679
*/

// Java program that will create sequences as binary trees, run inorder traversals
// and compare the arrays created to see if they are the same
import java.util.Scanner;
import java.util.Stack;

public class compareSeqA3Q3 {
 
    // Class containing left and right child of current node and its data
    public class Node {
        int data;
        Node left, right, parent;

        //constructor
        public Node(int item) {
            data = item;
            left = right = null;
        }

  /** a function to create new nodes that will be added into the binary tree, iff they are not already in the tree
  * @param data represents the integer value associated with the node we add
  * @return a boolean true if node was successfully created, false otherwise
  */
        public boolean add(int data) {
            if (data == this.data)
                  return false;
            else if (data <this.data) {
                  if (left == null) {
                    left = new Node(data);
                    nodecount++;
                    return true;
                    } else
                        return left.add(data);
            } else if (data > this.data) {
                if (right == null) {
                    right = new Node(data);
                    nodecount++;
                    return true;
                } else
                    return right.add(data);
            }

            return false;

      }
 
    }
 
    static Node root;
    int nodecount;//int to keep track of number of nodes in each tree
 
    //constructor
    compareSeqA3Q3() { 
        root = null; 
        nodecount = 0;
    }
 

    /** a function to create and add nodes into the binary tree, by calling to add function in the node class
    * @param data represents the integer value associated with the node we add
    * @return a boolean true if node was successfully created and added, false otherwise
    */
    public boolean add(int data) {
        if (root == null) {
            root = new Node(data);
            nodecount++;
            return true;
        } else
            return root.add(data); //calls to node function add

    }
 
   /** a function to traverse the BST inorder using a stack structure, which both prints the traversal and creates an array
    * that stores this traversal for later comparison
    * @return the integer array created by the inorder traversal, each slot containing the node data value visited
    */
    public int[] inorderIT(){
        int tempArr[] = new int[20];
        int i = 0;
        if (root == null)
            return tempArr;
 
        Stack<Node> s = new Stack<Node>();
        Node curr = root;
 
        // traverse the tree
        while (curr != null || s.size() > 0)
        {
            //traverse to leftmost node
            while (curr !=  null)
            {
                s.push(curr);
                curr = curr.left;
            }
 
            curr = s.pop();
 
            System.out.print(curr.data + " ");
            tempArr[i] = curr.data;
 
            //now visit right subtree
            curr = curr.right;

            i++;
        }
        System.out.println("\n");
        return tempArr;
    }

   /** a function that will compare two given binary trees as arrays, and determine if they are the same or not 
    * @param a represents the nodecount of the first tree
    * @param b represents the nodecount of the second tree
    * @param arrA represents the inorder traversal array of the first tree
    * @param arrB represents the inorder traversal array of the second tree
    * @return boolean true if the trees are the same, false otherwise
    */
    static boolean compareSeq(int a, int b, int[] arrA, int[] arrB){
        boolean hold = false;
        if(a != b)
            return hold;
        for(int j = 0; j <= a; j++){
            if (arrA[j] == arrB[j])
                hold = true;
            else
                hold = false;
        }
        return hold;
    }

/* unused method that didn't work, see notes
    static boolean identicalTrees(Node a, Node b) 
    {
        /*1. both empty 
        if (a == null && b == null)
            return true;
             
        /* 2. both non-empty -> compare them 
        if (a != null && b != null) 
            return (a.data == b.data
                    && identicalTrees(a.left, b.left)
                    && identicalTrees(a.right, b.right));
  
        /* 3. one empty, one not -> false 
        return false;
    } 
    */
 


public static void main(String[] args) {
       
       compareSeqA3Q3 s1 = new compareSeqA3Q3();
       int arrS1[], arrS2[], arrS3[] = new int[20];
 
        /* Creating the following BST to represent S1
              5
           /     \
           3      7
         /  \    /  \
        2    4   6   8 
                   
        */
        s1.add(5);
        s1.add(3);
        s1.add(2);
        s1.add(4);
        s1.add(7);
        s1.add(6);
        s1.add(8);
        s1.add(2); //checking to see if duplicates will add
        s1.add(3);
       
        System.out.println("The first sequence s1 is: ");

        arrS1 = s1.inorderIT();

        //building the same tree, adding elements in different order
        //to check if addition function works properly
        compareSeqA3Q3 s2 = new compareSeqA3Q3();
        s2.add(8);
        s2.add(7);
        s2.add(6);
        s2.add(5);
        s2.add(4);
        s2.add(3);
        s2.add(2);


        System.out.println("The second sequence s2 is: ");

        arrS2 = s2.inorderIT();

        System.out.println("s1 nodecount is " + s1.nodecount);
        System.out.println("s2 nodecount is " + s2.nodecount);


        System.out.println( "comparison result: " + compareSeq(s1.nodecount, s2.nodecount, arrS1, arrS2));

        System.out.println("Adding the element 10 to s1, "); 
        s1.add(10);//adding a new element to see if nodecount is updated properly, and if comparison returns expected false
        System.out.println("The first sequence s1 is now: ");       

        arrS1 = s1.inorderIT();

        System.out.println("t1 nodecount is " + s1.nodecount);
        System.out.println("t2 nodecount is " + s2.nodecount);

        System.out.println( "comparison result: " + compareSeq(s1.nodecount, s2.nodecount, arrS1, arrS2));

        System.out.println("now creating a third sequence s3 for further testing ");
        compareSeqA3Q3 s3 = new compareSeqA3Q3();
        s3.add(1);
        s3.add(20);
        s3.add(99);

        arrS3 = s3.inorderIT();

        System.out.println("s3 nodecount is " + s3.nodecount);

        System.out.println( "comparing s1 and s3: " + compareSeq(s1.nodecount, s3.nodecount, arrS1, arrS3));
        System.out.println( "comparing s2 and s3: " + compareSeq(s3.nodecount, s2.nodecount, arrS3, arrS2));



        }
    }