/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

/**
 *
 * @author markoc
 */
public class QueueInheritance<T extends Comparable<T>> extends List<T>{
    
    public QueueInheritance(){
        super("QueueInheritance");
    }
    
    public void enqueue(T object){
        insertAtBack(object);
    }
    
    public void dequeue(){
        removeFromFront();
    }
    
    public boolean isEmpty(){
        return isEmpty();
    }
    
    public void print(){
        this.print();
    }
}
