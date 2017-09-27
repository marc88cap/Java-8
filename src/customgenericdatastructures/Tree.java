// Fig. 21.17: Tree.java
// TreeNode and Tree class declarations for a binary search tree.
package customgenericdatastructures;

// class TreeNode definition
class TreeNode<T extends Comparable<T>> 
{
   // package access members
   TreeNode<T> leftNode; 
   T data; // node value
   TreeNode<T> rightNode; 

   // constructor initializes data and makes this a leaf node
   public TreeNode(T nodeData)
   { 
      data = nodeData;              
      leftNode = rightNode = null; // node has no children
   } 

   // locate insertion point and insert new node; ignore duplicate values
   public void insert(T insertValue)
   {
      // insert in left subtree
      if (insertValue.compareTo(data) < 0) 
      {
         // insert new TreeNode
         if (leftNode == null)
            leftNode = new TreeNode<T>(insertValue);
         else // continue traversing left subtree recursively
            leftNode.insert(insertValue); 
      }
      // insert in right subtree
      else if (insertValue.compareTo(data) > 0) 
      {
         // insert new TreeNode
         if (rightNode == null)
            rightNode = new TreeNode<T>(insertValue);
         else // continue traversing right subtree recursively
            rightNode.insert(insertValue); 
      } 
   } 
} // end class TreeNode

// class Tree definition
public class Tree<T extends Comparable<T>>
{
   private TreeNode<T> root;

   // constructor initializes an empty Tree
   public Tree() 
   { 
      root = null; 
   } 

   // insert a new node in the binary search tree
   public void insertNode(T insertValue)
   {
      if (root == null)
         root = new TreeNode<T>(insertValue); // create root node
      else
         root.insert(insertValue); // call the insert method
   } 
    public boolean deleteHelper(T deleteValue, TreeNode<T> parent, TreeNode<T> object){
        //node matched: delete focus on lesser subtrees larger value
        if(deleteValue.compareTo(object.data) == 0){
            //if node is a leaf
            if(object.rightNode==null && object.leftNode==null){
                if(object == root) // if object refers to root
                    root = null;
                else if(object.data.compareTo(parent.data) < 0)// if turned left
                    parent.leftNode = null;
                else
                    parent.rightNode = null;
            }
            //if node has two children
            else if(object.rightNode!=null && object.leftNode!=null){
                // find the node with the largest value in a lesser subtree
                TreeNode<T> replacement = object.leftNode;
                TreeNode<T> replacementsParent = object;
                while(replacement.rightNode != null){
                    replacementsParent = replacement;
                    replacement = replacement.rightNode;
                }
                // if object to be deleted is not equal to root (is not the same object)
                if(object != root)
                    // set objects parent reference to reference the new value (replacement)
                    if(parent.leftNode == object)
                        parent.leftNode = replacement;
                    else
                        parent.rightNode = replacement;
                // if object equals to root, set root to 
                // reference replacement object, because root has no parent
                else 
                    root = replacement;
                // if replacements parent doesnt reference object to be deleted
                // set replacements parent larger node reference to reference 
                // the larger subtree or null
                if(replacementsParent != object)
                        replacementsParent.rightNode = 
                                (replacement.leftNode == null) ? null : replacement.leftNode;
                // if replacement object is not same as node less than object to delete
                // set replacements less node to reference objects less node
                if(replacement != object.leftNode)
                    replacement.leftNode = object.leftNode;
                // set larger node to reference objects to delete larger node
                replacement.rightNode = object.rightNode;
            }
            // if node has one child
            else {
                // replace deletion object to its next lesser or larger value
                object = (object.leftNode != null) ? object.leftNode : object.rightNode;
                if(deleteValue.compareTo(parent.data) == 0)
                    root = object; // if deletion object is root object set new object
                else if(object.data.compareTo(parent.data) < 0) 
                    parent.leftNode = object; // set lesser object
                else
                    parent.rightNode = object; // set larger object
            }
            return true;
        }
        // move to next node
        else {
            if(object.leftNode == null && object.rightNode == null){
                System.out.printf("%nValue not found.%n");
                return false;
            }
            // if value to be deleted is greater or same as current value, recursive search
            else if(object.data.compareTo(deleteValue) > 0)
                return deleteHelper(deleteValue, object, object.leftNode);
            else
                return deleteHelper(deleteValue, object, object.rightNode);
        }
    }
   // delete node in binary tree
   public boolean deleteNode(T deleteValue){
       //find value to be deleted
        if(root == null)
            throw new EmptyListException();
        else if (deleteValue.compareTo(root.data)==0)
            return deleteHelper(deleteValue, root, root);
        else if(deleteValue.compareTo(root.data) < 0)
            return deleteHelper(deleteValue, root, root.leftNode);
        else
            return deleteHelper(deleteValue, root, root.rightNode);
        
   }
   // recursive method to search for a value
   private TreeNode<T> containsHelper(TreeNode<T> object, T value){
       if(value.compareTo(object.data) == 0)
           return object;
       else if(value.compareTo(object.data) < 0)
           return containsHelper(object.leftNode, value);
       else if(value.compareTo(object.data) > 0)
           return containsHelper(object.rightNode, value);
       else
           return null;
   }
   // call to recursive method 
   public TreeNode<T> contains(T value){
       return containsHelper(root, value);
   }
   // begin levelorder traversal
   public void levelOrderTraversal(){
       Queue<TreeNode<T>> queue = new Queue<>();
       queue.enqueue(root);
       while(!queue.isEmpty()){
           TreeNode<T> node = queue.dequeue();
           System.out.print(node.data+" ");
           if(node.leftNode != null)
               queue.enqueue(node.leftNode);
           if(node.rightNode != null)
               queue.enqueue(node.rightNode);
       }
   }
   
   public void outputTree(int spaces){
       outputTree(root, spaces);
   }
   // begin output tree
   public void outputTree(TreeNode<T> node, int spaces){
       if(node == null)
           return;
       outputTree(node.rightNode, spaces+5);
       for(int o = 0;o<spaces;o++)
           System.out.print(" ");
       System.out.printf("%s%n",node.data);
       outputTree(node.leftNode, spaces+5);
   }
   // begin preorder traversal
   public void preorderTraversal()
   { 
      preorderHelper(root); 
   } 

   // recursive method to perform preorder traversal
   private void preorderHelper(TreeNode<T> node)
   {
      if (node == null)
         return;

      System.out.printf("%s ", node.data); // output node data
      preorderHelper(node.leftNode); // traverse left subtree
      preorderHelper(node.rightNode); // traverse right subtree
   } 

   // begin inorder traversal
   public void inorderTraversal()
   { 
      inorderHelper(root); 
   } 

   // recursive method to perform inorder traversal
   private void inorderHelper(TreeNode<T> node)
   {
      if (node == null)
         return;

      inorderHelper(node.leftNode); // traverse left subtree
      System.out.printf("%s ", node.data); // output node data
      inorderHelper(node.rightNode); // traverse right subtree
   } 

   // begin postorder traversal
   public void postorderTraversal()
   { 
      postorderHelper(root); 
   } 

   // recursive method to perform postorder traversal
   private void postorderHelper(TreeNode<T> node)
   {
      if (node == null)
         return;
  
      postorderHelper(node.leftNode); // traverse left subtree
      postorderHelper(node.rightNode); // traverse right subtree
      System.out.printf("%s ", node.data); // output node data
   }   
   
   public void getDepth(){
       System.out.println(getDepthHelper(root));
   }
   
   private int getDepthHelper(TreeNode<T> node){
       if(node==null)
           return 0;
       int left = getDepthHelper(node.leftNode);
       int right = getDepthHelper(node.rightNode);
       
       if(left<=right)
           return right+1;
       else
           return left+1;
   
   }
   class Queue<Object>{
       public List<Object> queueList;
       
       public Queue(){
           this("queue");
       }
       
       public Queue(String name){
           this.queueList = new List<Object>(name);
       }
       
       public void enqueue(Object object){
           this.queueList.insertAtBack(object);
       }
       
       public Object dequeue(){
           return this.queueList.removeFromFront();
       }
       
       public boolean isEmpty(){
           return this.queueList.isEmpty();
       }
   }
   
    class List<Object>{
        String name;
        ListNode<Object> firstObject;
        ListNode<Object> lastObject;
        
        public List(){
            this("list");
        }
        
        public List(String name){
            this.name = name;
        }
        
        public void insertAtBack(Object object){
            if(isEmpty())
                firstObject = lastObject = new ListNode<Object>(object);
            else
                lastObject = lastObject.nextObject = new ListNode<Object>(object);
        }
        
        public Object removeFromFront(){
            if(isEmpty())
                throw new EmptyListException();
            else {
                ListNode<Object> node = firstObject;
                if(firstObject == lastObject)
                    firstObject = lastObject = null;
                else
                    firstObject = firstObject.nextObject;
                return node.object;
            }
        }
        
        public boolean isEmpty(){
            return firstObject == null;
        }
        
    }
   
   class ListNode<Object>{
       Object object;
       ListNode<Object> nextObject, previousObject;
       
       public ListNode(Object node){
           this(node, null, null);
       }
       
       public ListNode(Object object, ListNode<Object> nextObject,ListNode<Object> previousObject){
           this.object = object;
           this.nextObject = nextObject;
           this.previousObject = previousObject;
       }
   }
} 

/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
