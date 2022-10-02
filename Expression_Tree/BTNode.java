// File: BTNode.java from the package edu.colorado.nodes
// Complete documentation is available from the BTNode link in:
//   http://www.cs.colorado.edu/~main/docs/
package WebCrawler;
public class BTNode<E> {
    // Invariant of the BTNode<E> class:
    //   1. Each node has one reference to an E Object, stored in the instance
    //      variable data.
    //   2. The instance variables left and right are references to the node's
    //      left and right children.
    //static final int COUNT = 10; for print method helper
    private E data;
    private BTNode<E> left, right, parent;
    private static BTNode<Integer> treeRoot;


    public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight, BTNode<E> initialParent) {
        data = initialData;
        left = initialLeft;
        right = initialRight;
        parent = initialParent;

    }

    public E getData() {
        return data;
    }

    public BTNode<E> getLeft() {
        return left;
    }

    public E getLeftmostData() {
        if (left == null)
            return data;
        else
            return left.getLeftmostData();
    }

    public BTNode<E> getRight() {
        return right;
    }

    public E getRightmostData() {
        if (left == null)
            return data;
        else
            return left.getRightmostData();
    }

    public BTNode<E> getParent() {
        return parent;
    }

    public void setParent(BTNode<E> newParent) {
        parent = newParent;
    }

    public void inorderPrint() {
        if (left != null) {
            System.out.print("(");
            left.inorderPrint();

        }

        if (data.equals("!")) {
            System.out.print("(" + data);
        } else {
            System.out.print(data);
        }


        if (right != null) {
            right.inorderPrint();
            System.out.print(")");
        } else if (data == "!") {
            System.out.print("(");
        }


    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    public void preorderPrint() {
        System.out.println(data);
        if (left != null)
            left.preorderPrint();
        if (right != null)
            right.preorderPrint();
    }

    public void postorderPrint() {
        if (left != null)
            left.postorderPrint();
        if (right != null)
            right.postorderPrint();
        System.out.println(data);
    }


    /**
     * Uses an inorder traversal to print the data from each node at or below
     * this node of the binary tree, with indentations to indicate the depth
     * of each node.
     *
     * @param depth the depth of this node (with 0 for root, 1 for the root's
     *              children, and so on)(
     *              <b>Precondition:</b>
     *              <CODE>depth</CODE> is the depth of this node.
     *              <b>Postcondition:</b>
     *              The data of this node and all its descendants have been writeen by
     *              <CODE>System.out.println( )</CODE> using an inorder traversal.
     *              The indentation of each line of data is four times its depth in the
     *              tree. A dash "--" is printed at any place where a child has no
     *              sibling.
     **/
    public void print(int depth) {
        int i;

        // Print the indentation and the data from the current node:
        for (i = 1; i <= depth; i++)
            System.out.print("    ");
        System.out.println(data);

        // Print the left subtree (or a dash if there is a right child and no left child)
        if (left != null)
            left.print(depth + 1);
        else if (right != null) {
            for (i = 1; i <= depth + 1; i++)
                System.out.print("    ");
            System.out.println("--");
        }

        // Print the right subtree (or a dash if there is a left child and no left child)
        if (right != null)
            right.print(depth + 1);
        else if (left != null) {
            for (i = 1; i <= depth + 1; i++)
                System.out.print("    ");
            System.out.println("--");
        }
    }

    public BTNode<E> removeLeftmost() {
        if (left == null)
            return right;
        else {
            left = left.removeLeftmost();
            return this;
        }
    }

    public BTNode<E> removeRightmost() {
        if (right == null)
            return left;
        else {
            right = right.removeRightmost();
            return this;
        }
    }

    public void setData(E newData) {
        data = newData;
    }

    public void setLeft(BTNode<E> newLeft) {
        left = newLeft;
    }

    public void setRight(BTNode<E> newRight) {
        right = newRight;
    }
/*
    public static <E> BTNode<E> treeCopy(BTNode<E> source) {
        BTNode<E> leftCopy, rightCopy;

        if (source == null)
            return null;
        else {
            leftCopy = treeCopy(source.left);
            rightCopy = treeCopy(source.right);
            return new BTNode<E>(source.data, leftCopy, rightCopy);
        }
    }

 */

    public static <E> long treeSize(BTNode<E> root) {
        if (root == null)
            return 0;
        else
            return 1 + treeSize(root.left) + treeSize(root.right);
    }

    public static <E> int treeHeight(BTNode<E> root) {
        if (root == null) {
            return -1;
        } else
            return Math.max(treeHeight(root.left), treeHeight(root.right)) + 1;

    }

    public static <E> int treeLeaves(BTNode<E> root) {
        if (root == null) //base case 1
            return 0;
        else if (root.left == null && root.right == null) //base case 2: root is a leaf
            return 1;
        else              //general case: root is not a leaf
            return treeLeaves(root.left) + treeLeaves(root.right);
    }

    public static boolean treeSearch(BTNode<Integer> root, int target) {
        if (root == null)
            return false;
        else if (root.data == target)
            return true;
        else
            return treeSearch(root.left, target) || treeSearch(root.right, target);

//      return root == null ? false : (root.data == target) || treeSearch(root.left,target) || treeSearch(root.right,target);
    }

    public static boolean search(BTNode<Integer> root, int element)
    {
        if (root == null) //If the root.data == the element then we have a duplicate key return false and print
        {
            return false;
        }
        else if (element == root.data)
        {
            return true;
        }
        else if (element < root.data)//if its less than root we traverse left sub tree otherwise, right
        {
            return search(root.left, element);
        }
        else
            return search(root.right, element);

    }

    public static BTNode<Integer> findMin(BTNode<Integer> root) {
        if (root == null) {
            return null;
        } else if (root.left == null) {
            return root;//we return root because there is no left subtree "BST rule"
        } else
            return findMin(root.left); //otherwise recurse left subtree

    }


    public static BTNode<Integer> findMax(BTNode<Integer> root) {
        if (root == null) {
            return null;
        } else if (root.right == null) {
            return root;
        } else
            return findMax(root.right);
    }

    public static BTNode<Integer> insert(BTNode<Integer> root, BTNode<Integer> parent, int element)
    {

        if (root == null)
        {
            return new BTNode<Integer>(element, null, null, null);
        }
        else if (element < root.data)
        {
            root.left = insert(root.left, root, element);
        }
        else if(element > root.data)
        {
            root.right = insert(root.right, root, element);
        }

        return root;

    }
    public static BTNode<Integer> findRoot(BTNode<Integer> node)
    {
        if(node == null)
        {
            return null;
        }
        else if(node.parent == null)
        {
            return node;
        }

        return findRoot(node.parent);

    }

    public static BTNode<Integer> insertSplay(BTNode<Integer> root, int element, int startSplay)
    {

        BTNode<Integer> currentNode = BTNode.treeRoot;
        BTNode<Integer> parentNode = null;


        while(currentNode != null)
        {
            parentNode = root = currentNode;
            if(currentNode.data == element)
            {
                splay(currentNode);
                System.out.println("Duplicate Keys: The Key Entered Was " + element);
                return root;
            }
            else if(currentNode.data < element)
            {
                currentNode = root = currentNode.right;
            }//else if the element == the currentnode.data then return
            else
            {
                currentNode = root = currentNode.left;
            }
        }

        currentNode = root = new BTNode<Integer>(element, null, null, parentNode);

        if(parentNode == null)
        {
            BTNode.treeRoot = currentNode;
        }
        else if(currentNode.data > parentNode.data)
        {
            parentNode.right = currentNode;
        }
        else
        {
            parentNode.left = currentNode;
        }
        //printDiagram(BTNode.treeRoot, 0 ,10);
        if(treeHeight(currentNode) >= 0 && startSplay == 1)
        {
            //System.out.println("Before Splay");
            //System.out.println("------------------------------------------");
            //printDiagram(currentNode, 0 ,10);
            //System.out.println("------------------------------------------");
            //System.out.println();
            splay(currentNode);
            //System.out.println();
            //System.out.println("After Splay");
            //System.out.println("------------------------------------------");
            //printDiagram(currentNode, 0 ,10);
            //System.out.println("------------------------------------------");

        }
        return root;

    }

    public static void deleteSplay(BTNode<Integer> root, int element)
    {
        BTNode<Integer> temp;
        BTNode<Integer> maxTreeNode = null;
        int nodeDeleted = 0;

        if(search(BTNode.treeRoot, element))
        {
            temp = searchSplay(BTNode.treeRoot, element);
            nodeDeleted = temp.data;
        }
        else//Cannot delete a node that is not there
        {
            System.out.println("The Item to be Deleted Was Not Found");
            temp = searchSplay(BTNode.treeRoot,element);
            splay(temp);
            //root.printDiagram(BTNode.treeRoot, 0, 10);
            return;
        }

        BTNode<Integer> leftSubTree = temp.left;
        BTNode<Integer> rightSubTree = temp.right;
        //System.out.println("Node Deleted Before Splay");
        //root.printDiagram(BTNode.treeRoot, 0, 10);

        //now we splay to bring item deleted to the root

        splay(temp); //no need to splay if its a 1 node tree


        temp = null; //delete the root
        //System.out.println("Node Deleted After Splay");
        //root.printDiagram(BTNode.treeRoot, 0, 10);

        if(leftSubTree != null)
        {
            leftSubTree.parent = null;
            maxTreeNode = findMax(leftSubTree);
            splay(maxTreeNode); //make the max the root of the left subtree
            BTNode.treeRoot = maxTreeNode; // now we make the new root of whole tree
        }
        if(rightSubTree != null)
        {
            if(leftSubTree != null)
            {
                maxTreeNode.right = rightSubTree;
            }
            else
            {
                BTNode.treeRoot = rightSubTree;
            }
            rightSubTree.parent = maxTreeNode; //attach to the root
        }
        System.out.print("Node Deleted is: " + nodeDeleted);
        //root = findRoot(root);
        root.printDiagram(BTNode.treeRoot, 0, 10);


    }


    public static BTNode<Integer> delete(BTNode<Integer> root, int element) {
        if (root == null) {
            return null;
        } else if (element < root.data) {
            root.left = delete(root.left, element);
        } else if (element > root.data) {
            root.right = delete(root.right, element);
        } else if (root.left == null) {
            return root.right;
        } else if (root.right == null) {
            return root.left;
        } else {
            root.data = findMax(root.left).data;
            root.left = delete(root.left, root.data);
        }
        return root;
    }


    public static void printDiagram(BTNode<Integer> root, int space, int count) {

        if(root == null)
        {
            return;
        }
        space += count;

        printDiagram(root.right, space, count);

        System.out.print("\n");
        for (int i = count; i < space; i++)
        {
            System.out.print(" ");
        }

        System.out.print(root.data + "\n");


        printDiagram(root.left, space, count);
    }

    public static <E> void reflection(BTNode<E> root) {
        if (root == null) {
            throw new NullPointerException("Tree is null");
        }
        //create a temp to make the swap of the left and right subtree
        BTNode<E> temp = root.right;
        root.right = root.left;
        root.left = temp;

        //then we need to recurse left and right keep making the swaps until we are at a leaf of left/right subtree
        if (root.left != null) {
            reflection(root.left);
        }
        if (root.right != null) {
            reflection(root.right);
        }


    }

    public static void leftRotation(BTNode<Integer> node)
    {
        //All symetric of right rotation
        BTNode<Integer> temp = node.right;

        if(temp != null)
        {
            node.right = temp.left;

            if(temp.left != null)
            {
                temp.left.parent = node;
            }
            else
            {
                //do nothing
            }

            temp.parent = node.parent;
        }

        if(node.parent == null)
        {
            BTNode.treeRoot = temp; // reference of tree root
            //problem need the root reference not the current node
        }
        else if(node == node.parent.left)
        {
            node.parent.left = temp;
        }
        else
        {
            node.parent.right = temp;
        }
        if(temp != null)
        {
            temp.left = node;
        }
        else{
            //do nothing
        }
        node.parent = temp;


    }

    public static void rightRotation(BTNode<Integer> node)
    {
       //first save the left subtree
        BTNode<Integer> temp = node.left;

        if(temp != null)
        {
            //if its not null the nodes left becomes the temps right
            node.left = temp.right;

            if(temp.right != null)
            {
                temp.right.parent = node;//then the rights parent is attached
            }
            else
            {
                //do nothing
            }
            temp.parent = node.parent;//otherwise the temps parent becomes the nodes parent
        }

        if(node.parent == null)
        {
            //the original root will now be temps node
            BTNode.treeRoot = temp;
        }
        else if(node == node.parent.left)
        {
            //the nodes parent/left will now be temp
            node.parent.left = temp;
        }
        else
        {
            //otherwise the opposite nodes parent/right will be temp
            node.parent.right = temp;
        }

        if(temp != null)
        {
            //temps right will now be the node if != null
            temp.right = node;
        }
        else
        {
            //do nothing
        }
        node.parent = temp;


    }

    public static boolean isLeftChild(BTNode<Integer> root, int key) {
        return key < root.data;
    }

    public static boolean isRightChild(BTNode<Integer> root, int key) {
        return key > root.data;
    }
    public static BTNode<Integer> splayAtNode(BTNode<Integer> root, int element)
    {
        if(root == null)
        {
            return root;
        }
        else
        {
            BTNode<Integer> node = searchSplay(root,element);

            if(node.data == element)
            {
                //Print Duplicate Keys
                System.out.println("Duplicate Keys");
                splay(node);
            }
            else if(element > findMin(node).data) //unnessesary code delete later
            {
                splay(node);
            }
            else
            {
                splay(node);
            }
        }
        return root;
    }
    public static void splay(BTNode<Integer> root)
    {
        while(root.parent != null) //while we are not at the original root node
        {
            BTNode<Integer> parent = root.parent;
            BTNode<Integer> grandparent = root.parent.parent;

            if(grandparent == null)
            {
                //single rotation
                if(root == parent.right)
                {
                    leftRotation(root.parent);
                }
                else
                {
                    rightRotation(root.parent);
                }
            }
            else// Else the grandparent is not null
            {
                //Zig-Zag Right Left
                if(root == root.parent.left && root.parent == root.parent.parent.right)
                {
                    rightRotation(root.parent);
                    leftRotation(root.parent);
                }
                //Zag-Zag rotation Left Left
                else if(root == root.parent.right && root.parent == root.parent.parent.right)
                {
                    leftRotation(root.parent.parent);
                    leftRotation(root.parent);
                }
                //Zig-Zig Rotation Right Right
                else if(root == root.parent.left && root.parent == root.parent.parent.left)
                {
                    rightRotation(root.parent.parent);
                    rightRotation(root.parent);
                }
                //Zag-Zig Left Right
                else if(root == parent.right && parent == grandparent.left)
                {
                    leftRotation(root.parent);
                    rightRotation(root.parent);

                }
                else
                {
                    //do nothing
                }
            }
        }

    }
    public static BTNode<Integer> searchSplay(BTNode<Integer> root, int key)
    {
        int newKey = key;
        if(key == root.data)
        {
            //System.out.print("Before Search Splay");
            //root.printDiagram(BTNode.treeRoot, 0, 10);
            System.out.println("Search is Successful: your root is: " + root.data);
            splay(root);
            //System.out.print("After Search Splay");
            //root.printDiagram(BTNode.treeRoot, 0, 10);

            return root;
        }

        BTNode <Integer> predecessor = null;
        BTNode<Integer> successor = null;
        while(root != null)
        {

            if(newKey < root.data)
            {
                successor = root;
                root = root.left;
            }
            else
            {
                predecessor = root;
                root = root.right;
            }


        }
        root = BTNode.treeRoot;

        if(predecessor == null)
        {
            newKey = successor.data;
            predecessor = successor;
        }
        else
        {
            newKey = predecessor.data;
        }


        //System.out.print("Before Search Splay");
       // root.printDiagram(BTNode.treeRoot, 0, 10);
        if(newKey == key)
        {
            System.out.println("Search is Successful: your key is: " + root.data);


        }
        else
        {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Search was Unsuccessful: your new key is: " + newKey + "\nYour initial input was: " + key);
        }
        if(newKey == predecessor.data)
        {
            splay(predecessor);
        }
        else
            splay(successor);

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("            ************ Tree Diagram After Splay ************");
        root.printDiagram(BTNode.treeRoot, 0, 10);
        System.out.println("----------------------------------------------------------------------------------");


        return BTNode.treeRoot;
    }
}

/*

    public static boolean prefix(BTNode<Integer> smallerTree, BTNode<Integer> biggerTree)
    {


    }

 */


        /* Creating a Tree

        4

   2         6

 1   3     5   7

BTNode root;
root = new BTNode<Integer>(4, null, null);
root.setLeft(new BTNode<Integer>(2, null, null));
root.setRight(new BTNode<Integer>(6, null, null));
root.getLeft().setLeft(new BTNode<Integer>(1, null, null));
root.getLeft().setRight(new BTNode<Integer>(3, null, null));
root.getRight().setLeft(new BTNode<Integer>(5, null, null));
root.getRight().setRight(new BTNode<Integer>(7, null, null));
         */