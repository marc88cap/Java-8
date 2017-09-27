package customgenericdatastructures;

public class StackCustom<T extends Comparable<T>>
{
   private List<T> stackList;
   private int size = 0;
   // constructor
   public StackCustom() 
   { 
      stackList = new List<T>("stack"); 
   } 
   
   public int size(){
       return size;
   }
   // add object to stack
   public void push(T object)
   { 
      size++;
      stackList.insertAtFront(object); 
      
   } 
   
   public T peek()
   {
       return stackList.getFirst();
   }
   
   // remove object from stack
   public T pop() throws EmptyListException
   { 
      size--;
      return stackList.removeFromFront(); 
   } 

   // determine if stack is empty
   public boolean isEmpty() 
   { 
      return stackList.isEmpty(); 
   } 

   // output stack contents
   public void print() 
   { 
      stackList.print(); 
   } 
} // end class StackComposition