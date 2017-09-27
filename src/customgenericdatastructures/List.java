// Fig. 21.3: List.java
// ListNode and List class declarations.
package customgenericdatastructures;

// class to represent one node in a list
class ListNode<T> 
{
   // package access members; List can access these directly
   T data; // data for this node
   ListNode<T> nextNode; // reference to the next node in the list
   ListNode<T> prevNode;
   
   // constructor creates a ListNode that refers to object
   ListNode(T object) 
   { 
      this(object, null, null); 
   }  
   // constructor creates ListNode that refers to the specified
   // object and to the next ListNode
   ListNode(T object, ListNode<T> pNode, ListNode<T> nNode)
   {
      data = object;    
      nextNode = nNode;
      prevNode = pNode;
   } 

   // return reference to data in node
   T getData() 
   { 
      return data; 
   } 

   // return reference to next node in list
   ListNode<T> getNext() 
   { 
      return nextNode; 
   } 
} // end class ListNode<T>

// class List definition
public class List<T extends Comparable<T>>
{
   private ListNode<T> firstNode;
   private ListNode<T> lastNode;
   protected List<T> nextList;
   protected List<T> prevList;
   private String name; // string like "list" used in printing
   private int size;
   // constructor creates empty List with "list" as the name
   public List() 
   { 
      this("list"); 
   } 

   // constructor creates an empty List with a name
   public List(String listName)
   {
      name = listName;
      firstNode = lastNode = null;
   } 
   
   public String getName(){
       return name;
   }
   public T getLast(){
       return lastNode.data;
   }
   
   public T getFirst(){
       return firstNode.data;
   }
   
   public int size(){
       return size;
   }
   
   public T search(T data){
       return searchHelper(firstNode, data);
   }
   
   public T searchHelper(ListNode<T> node,  T data){
       if(node == lastNode && data != node.data)
           return null;
       
       if(node.data == data)
           return data;
       else
           return searchHelper(node.nextNode,data);
   }
   
   public void merge(List<T> list){
       ListNode<T> current = list.firstNode;
       while(current!=null){
           insertAscending(current.data);
           print();
           current = current.nextNode;
       }
   }
   
   public void insertAscending(T insertItem){
       System.out.printf("%nInsert: %s%n",insertItem);
       size++;
       if(isEmpty())
           firstNode = lastNode = new ListNode<T>(insertItem);
       else if(firstNode == lastNode){
           if(firstNode.data.compareTo(insertItem)>0){
               firstNode = firstNode.prevNode = new ListNode<T>(insertItem, null, firstNode);
           }
           else
               lastNode = lastNode.nextNode = new ListNode<T>(insertItem, lastNode, null);
       }
       else {
           ListNode<T> current = firstNode;
           while(current!=lastNode){
               if(current.data.compareTo(insertItem)>=0){
                   if(current==firstNode){
                       current.prevNode = firstNode = new ListNode<T>(insertItem, null, current);
                   }
                   else {
                       
                       current.prevNode = current.prevNode.nextNode = new ListNode<T>(insertItem,current.prevNode,current);

                   }
                   break;
               }
               current = current.nextNode;
           }
           if(current==lastNode)
            if(lastNode.data.compareTo(insertItem)>0){
                lastNode.prevNode = lastNode.prevNode.nextNode = new ListNode<T>(insertItem, lastNode.prevNode, lastNode);
            }
            else if(lastNode.data.compareTo(insertItem)<0)
                 lastNode = lastNode.nextNode = new ListNode<T>(insertItem, lastNode, null);
       }
       
   }
   
   // insert item at front of List
   public void insertAtFront(T insertItem)
   {
      size++;
      if (isEmpty()) // firstNode and lastNode refer to same object
         firstNode = lastNode = new ListNode<T>(insertItem);
      else // firstNode refers to new node
         firstNode = new ListNode<T>(insertItem, null, firstNode);
   } 

   // insert item at end of List
   public void insertAtBack(T insertItem)
   {
      size++;
      if (isEmpty()) // firstNode and lastNode refer to same object
         firstNode = lastNode = new ListNode<T>(insertItem);
      else // lastNode's nextNode refers to new node
         lastNode = lastNode.nextNode = new ListNode<T>(insertItem, lastNode, null);
   } 

   // remove first node from List
   public T removeFromFront() throws EmptyListException
   {
      if (isEmpty()) // throw exception if List is empty
         throw new EmptyListException(name);
      size--;
      T removedItem = firstNode.data; // retrieve data being removed

      // update references firstNode and lastNode 
      if (firstNode == lastNode)
         firstNode = lastNode = null;
      else
         firstNode = firstNode.nextNode;

      return removedItem; // return removed node data
   } // end method removeFromFront

   // remove last node from List
   public T removeFromBack() throws EmptyListException
   {
      if (isEmpty()) // throw exception if List is empty
         throw new EmptyListException(name);
      size--;
      T removedItem = lastNode.data; // retrieve data being removed

      // update references firstNode and lastNode
      if (firstNode == lastNode)
         firstNode = lastNode = null;
      else // locate new last node
      { 
         ListNode<T> current = firstNode;

         // loop while current node does not refer to lastNode
         while (current.nextNode != lastNode)
            current = current.nextNode;
   
         lastNode = current; // current is new lastNode
         current.nextNode = null;
      } 

      return removedItem; // return removed node data
   } 

   public T remove(T object){
       if(isEmpty())
           throw new EmptyListException(name);
       
       ListNode<T> current = firstNode;
       T removedItem = null;
       while(current != lastNode && current.data.compareTo(object) != 0)
           current = current.nextNode;
       
       if(current.data == object)
           if(current.prevNode != null && current.nextNode != null){
               current.prevNode.nextNode = current.nextNode;
               current.nextNode.prevNode = current.prevNode;
               removedItem = current.data;
           }
           else if (current.prevNode == null)
               removedItem = removeFromFront();
           else
               removedItem = removeFromBack();
       
       return removedItem;
   }
   
   // determine whether list is empty
   public boolean isEmpty()
   { 
      return firstNode == null; // return true if list is empty
   } 

   // output list contents
   public void print()
   {
      if (isEmpty()) 
      {
         System.out.printf("Empty %s%n", name);
         return;
      }

      System.out.printf("The %s is: ", name);
      ListNode<T> current = firstNode;

      // while not at end of list, output current node's data
      while (current != null) 
      {
         System.out.printf("%s ", current.data);
         current = current.nextNode;
      }
   } 
   
   public void printListBackward(){
       printListBackwardHelper(firstNode);
   }
   
   private void printListBackwardHelper(ListNode<T> node){
       if(node==null)
           return;
       printListBackwardHelper(node.nextNode);
       System.out.printf("%s ",node.data);
   }
} // end class List<T>
