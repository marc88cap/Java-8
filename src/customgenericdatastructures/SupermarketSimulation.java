/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.security.SecureRandom;

/**
 *
 * @author markoc
 */
public class SupermarketSimulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        simulation(720);
    }
    
    private static void simulation(int minutes){
        SecureRandom sr = new SecureRandom();
        int customer = 1;
        int timeline = 0;
        int firstArrival = sr.nextInt(4)+1;
        int nextArrival = 0;
        int size = 0, longestWait = 0;
        Queue<String> q = new Queue<>();
        
        for(int serviceTime=-2;timeline<=minutes;timeline++,serviceTime--){
            if(size<q.size()) size=q.size();
            if(firstArrival==timeline){
                serviceTime = sr.nextInt(4)+1;
                nextArrival = sr.nextInt(4)+1+timeline;
            }
            if(nextArrival==timeline){
                q.enqueue("C"+customer,nextArrival);
                System.out.printf("C%d arrived.%n",customer++);
                nextArrival = sr.nextInt(4)+1+timeline;
            }
            
            if(serviceTime==0){
                System.out.printf("Service completed for the last customer.%n");

            }
            if(serviceTime<1 && !q.isEmpty()){
                serviceTime = sr.nextInt(4)+1;
                int wait = timeline+serviceTime-q.peekFirst().getArrivalTime();
                if(wait>longestWait)
                    longestWait = wait;
                System.out.printf("Serving %s customer.%n", q.dequeue());
                
            }
        }
        
        System.out.printf("%n%s %d%n%s %d %s%n",
                                        "Max size of queue at any time is", size,
                                        "Longest wait is", longestWait, "minutes");
        
    }
    
}

class Node<T>
{
    T data;
    Node<T> nextNode;
    Node<T> prevNode;
    int arrivalTime;
    
    public Node(T object){
        this(object,null,null,0);
    }
    
    public Node(T object,int arrivalTime){
        this(object,null,null,arrivalTime);
    }
    
    public Node(T object, Node<T> nextNode, Node<T> prevNode, int arrivalTime){
        this.data = object;
        this.nextNode = nextNode;
        this.prevNode = prevNode;
        this.arrivalTime = arrivalTime;
    }
    
    public Node<T> getNext(){
        return this.nextNode;
    }
    
    public void setArrivalTime(int time){
        this.arrivalTime = time;
    }
    
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    
    public T getData(){
        return this.data;
    }
}

class Line<T>
{
    Node<T> firstNode;
    Node<T> lastNode;
    String name;
    int size;
    
    public Line(){
        this("list");
    }
    
    public Line(String name){
        this.name = name;
        this.firstNode = this.lastNode = null;
    }
    
    public Node<T> getFirstNode(){
        return this.firstNode;
    }
    
    public Node<T> getLastNode(){
        return this.lastNode;
    }
    
    public void insertAtFront(T insertItem, int time){
        if(this.isEmpty())
            this.firstNode = this.lastNode = new Node(insertItem, time);
        else
            this.firstNode = this.firstNode.prevNode = new Node(insertItem, time);
        
        this.size++;
    }
    
    public void insertAtBack(T insertItem, int time){
        if(this.isEmpty())
            this.firstNode = this.lastNode = new Node(insertItem, time);
        else
            this.lastNode = this.lastNode.nextNode = new Node(insertItem, time);
        
        size++;
    }
    
    public T removeFromFront(){
        if(this.isEmpty())
            throw new EmptyListException(name);
        
        T removedItem = this.firstNode.data;
        
        if(this.firstNode == this.lastNode)
            this.firstNode = this.lastNode = null;
        else
            this.firstNode = this.firstNode.nextNode;
        
        size--;
        
        return removedItem;
    }
    
    public T removeFromBack(){
        if(this.isEmpty())
            throw new EmptyListException(name);
        
        T removedItem = this.lastNode.data;
        
        if(this.firstNode == this.lastNode)
            this.firstNode = this.lastNode = null;
        else
            this.lastNode = this.lastNode.prevNode;
        
        size--;
        
        return removedItem;
    }
    
    
    public boolean isEmpty(){
        if(this.firstNode==null)
            return true;
        return false;
    }
    
    public void print(){
        if(this.isEmpty()){
            System.out.printf("%s is empty.",this.name);
            return;
        }
        else{
            Node<T> current = firstNode;
            System.out.printf("%s contains:",this.name);
            while(current!=null){
                System.out.print(current.data+" ");
                current = current.nextNode;
            }
            System.out.println();
        }
    }
    
    public int size(){
        return this.size;
    }
}

class Queue<T>{
    private Line<T> queueList;
    
    public Queue(){
        queueList = new Line<T>("queue");
    }
    
    public void enqueue(T object,int time){
        queueList.insertAtBack(object,time);
    }
    
    public T dequeue() throws EmptyListException {
        return queueList.removeFromFront();
    }
    
    public Node<T> peekFirst(){
        return queueList.getFirstNode();
    }
    
    public Node<T> peekLast(){
        return queueList.getLastNode();
    }
    
    public int size(){
        return queueList.size();
    }
    
    public boolean isEmpty(){
        return queueList.isEmpty();
    }
    
    public void print(){
        queueList.print();
    }
}