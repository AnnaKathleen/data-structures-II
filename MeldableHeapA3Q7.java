 /** Assignment 3, Q7
* August 2 2018
* for AU COMP 272
* @author: Anna Rabatich 
* student no. 3314679
*/

/** a java program that implements a meldable heap with most of its standard operations as well
* as a remove(u) function that will remove the given node u from the heap
*/
	
import java.util.Scanner;
import java.util.Random;


class Node {
	Node left, right, parent;
	int data;

	public Node(Node parent, Node left, Node right, int data) {
		this.parent = parent;
		this.left = left;
		this.right = right;
		this.data = data;
	}
}


public class MeldableHeapA3Q7 {
	private Random rand;
	private int nodecount;
	static Node root;

	public MeldableHeapA3Q7() {
		root = null;
		rand = new Random();
		nodecount = 0;
	}


	public void makeEmpty() {
		root = null;
		rand = new Random();
		nodecount = 0;
	}

	public int getSize() {
		return nodecount;
	}

  /** a function to add new nodes into the heap
  * @param x represents the key or integer value associated with the node we add
 
  */
	public void add(int x) {
		Node u = new Node(null, null, null, x);
		root = merge(u, root);
		root.parent = null;
		nodecount++;
	}


/* method I didn't end up using, but ideally would see if the user entered key was
located in the heap. See notes below in main testing
	public Node heapSearch(Node r, int u) { //Morin's code to remove at the root
		Node temp = r;
		int num = u;
		int x = r.data;
   		
   		if(x == u)
   			return temp;
   		
   		if(temp == null)
   			return null;
   		
		
		heapsearch(temp.left, num);
   		heapsearch(temp.right, num);

   		return null;
    
	} */

//Morin's code to remove the root from a meldable heap
	public int removeRoot() {
		int x = root.data;
		root = merge(root.left, root.right);
		if (root != null)
			root.parent = null;
		nodecount--;
		return x;
	}
	
  /** the void remove function that will delete a node and its key from the meldable heap
  * @param u represents the node passed in that will be removed from the heap
  */
	public void remove(Node u) {
		if(u != null){
		int x2 = u.data;
		Node prev = u.parent;
		Node newu = merge(u.left, u.right);
		//System.out.println("newu is now " + newu.data);
		if (newu != null)
			newu.parent = prev;
		//System.out.println("newu parent is now " + newu.parent.data);
		if (u == u.parent.left){
			u.parent.left = newu;
		}else if(u == u.parent.right){
			u.parent.right = newu;
		}
		nodecount--;
		System.out.println("Node succesfully removed was: " + x2);
	}
	else{ 
		System.out.println("Node does not exist, cannot remove "); 
	}
}

  /** the merge function commonly implemented in heaps that uses coin tosses and recursion to merge one subtree with another
  * @param a represents one of the nodes passed as a subtree that will be merged with another
  * @param b represents the other node passed from the other subtree to be merged
  * @return is the node representing the root of the new subtree created by the merge
  */
	public Node merge(Node a, Node b) {
		if (a == null)
			return b;
		if (b == null)
			return a;

		if (b.data < a.data)
			return merge(b, a);

		if (rand.nextBoolean()) {
			a.left = merge(a.left, b);
			a.left.parent = a;
		} else {
			a.right = merge(a.right, b);
			a.right.parent = a;
		}
		return a;
	}

  /** a utility function that will print and display all items in the heap
  */
	public void displayHeap() {
		System.out.print("\nCurrent Meldable Heap : ");
		if (root == null) {
			System.out.print("Empty\n");
			return;
		}

		Node prev, w = root;
		while (w.left != null)
			w = w.left;

		while (w != null) {
			System.out.print(w.data + " ");
			prev = w;
			w = nextNode(w);
		}
		System.out.println("\n");
	}
  /** a utility function that will print and display all nodes in the heap
  * used in testing to keep track of nodes as they change positions
  */
	public void displayHeapNodes(){
		if(root != null)
			System.out.println("the root node is " + root.data);
		else
			System.out.println("the tree is empty ");
		if(root.right != null)
			System.out.println("the root.right node is " + root.right.data);
		else
			System.out.println("there is no right root child ");
		if(root.left != null)
			System.out.println("the root.left node is " + root.left.data);
		else
			System.out.println("there is no left root child ");
	}

  /** a utility function called by the heap print function, to find next node
  * @param node represents the node passed in which we want to find the successor 
  * @return is the node following the node we passed in
  */
	private Node nextNode(Node node) {
		if (node.right != null) {
			node = node.right;
			while (node.left != null)
				node = node.left;
		} else {
			while (node.parent != null && node.parent.left != node)
				node = node.parent;
			node = node.parent;
		}
		return node;
	}




	public static void main(String[] args) {
		//Scanner scan = new Scanner(System.in);
		System.out.println("Meldable Heap Test\n\n");

        Scanner scan = new Scanner(System.in);
		MeldableHeapA3Q7 mh = new MeldableHeapA3Q7();

/* This segment was used for quick testing and allowed me to check
if the removal functions worked for the root and children, results
were as expected. 

		mh.add(4);
		mh.add(5);
		mh.add(6);
		mh.add(2);
		mh.add(3);
		mh.add(10);
		mh.add(15);
		//mh.add(1);
		mh.displayHeap();

		mh.displayHeapNodes();

		System.out.println("\n----- now deleting the root ----- ");
		//System.out.println("root.left is " + root.left.data);
		//System.out.println("root.right is " + root.right.data);

		mh.removeRoot();

		mh.displayHeap();
		mh.displayHeapNodes();
		//System.out.println("root is " + root.data);

		//System.out.println("root.left is " + root.left.data);
		System.out.println("\n----- now deleting the root.left ----- ");
		mh.remove(root.left);
		mh.displayHeap();
		mh.displayHeapNodes();

		//System.out.println("root is " + root.data);
		
		//System.out.println("root.right is " + root.right.data);
		System.out.println("\n----- now deleting the root.right ----- ");
		mh.remove(root.right);

		mh.displayHeap();
		mh.displayHeapNodes(); 


/* this part of the code is an interactive segment that takes input from user
but it takes long for quick testing. It was difficult to get the user's
input of which node they'd like to delete without having to first search the heap
to see if the integer key they entered is a key matching to any node. I started developing
this with the method "heapSearch" but ran out of time, so opted to just give users a few choices.  */

		char ch;
		do {
			System.out.println("\nMeldable Heap Operations\n");
			System.out.println("1. add to heap ");
			System.out.println("2. remove from heap ");
			System.out.println("3. size of heap ");
			System.out.println("4. clear heap ");

			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter integer element to insert");
				mh.add(scan.nextInt());
				break;
			case 2:
				System.out.println("nodes are currently: ");
				mh.displayHeapNodes(); 
				System.out.println("which node would you like to remove: ");

				System.out.println("1. root");
				System.out.println("2. root left child ");
				System.out.println("3. root right child ");
				System.out.println("4. root left right child ");
				System.out.println("5. root right left child ");
				int choice2 = scan.nextInt();
				switch (choice2){
					case 1:
						System.out.println("removing the root ");
						mh.removeRoot();
						break;
					case 2:
						System.out.println("removing root.left ");
						mh.remove(root.left);
						break;
					case 3:
						System.out.println("removing root.right ");
						mh.remove(root.right);
						break;
					case 4:
						System.out.println("removing root.left.right ");
						mh.remove(root.left.right);
						break;
					case 5:
						System.out.println("removing root.right.left ");
						mh.remove(root.right.left);
						break;	
					default:
						System.out.println("Wrong Entry \n ");
						break;
					}
				break;

			case 3:
				System.out.println("Size of the Heap = " + mh.getSize());
				break;
			case 4:
				mh.makeEmpty();
				System.out.println("Heap Cleared\n");
				break;
			default:
				System.out.println("Wrong Entry \n ");
				break;
			}

			mh.displayHeap();

			System.out.println("\nDo you want to continue (Type y or n) \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y'); 
	}
}