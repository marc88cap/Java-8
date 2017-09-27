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
public class IndexedList<T extends Comparable<T>>{
    String name;
    List<T> firstList;
    List<T> lastList;
    
    public IndexedList(){
        this("Indexed list");
    }
    
    public IndexedList(String name){
        name = name;
        firstList = lastList = null;
    }
    
        
    public void insertInIndexedList(T object){
        List<T> current = firstList;
        String index = object.toString().substring(0, 1).toLowerCase();
        if(current == null){
            current = firstList = lastList = new List(index);
            current.insertAtBack(object);
        }
        else {
            // find a list corresponding to index
            while(current != lastList && current.getName().compareTo(index) != 0) 
                current = current.nextList;
            // if index was found, add object to the list with that index
            if(current.getName().compareTo(index) == 0){
                current.insertAtBack(object);
            }
            else { // if index was not found create a new list with that index
                current = lastList = lastList.nextList = new List(index); // store list index
                current.insertAtBack(object); // insert the object into the
            }
        }
    }
    
    public void searchIndexedList(T object){
        String index = object.toString().substring(0,1).toLowerCase();
        List<T> current = firstList;
        
        if(current == null)
            throw new EmptyListException();
        else{
            while(current != lastList && current.getName().compareTo(index) != 0)
                current = current.nextList;
            if(current.getName().compareTo(index) == 0){
                if(current.search(object) != null){
                    System.out.printf("Object \'%s\' was found.%n",object);
                    current.print();
                }
                else
                    System.out.printf("Object \'%s\' was not found.",object);
            }
        }
    }
    
    public void deleteFromIndexedList(T object){
        String index = object.toString().substring(0,1).toLowerCase();
        List<T> current = firstList;
        
        if(current == null)
            throw new EmptyListException();
        else {
            while(current != lastList && current.getName().compareTo(index) != 0)
                current = current.nextList;
            
            if(current.getName().compareTo(index) == 0){
                if(current.remove(object) != null){
                    System.out.printf("Object \'%s\' was deleted. ", object);
                    current.print();
                    System.out.println();
                    return;
                }
            }
            
            System.out.printf("Object \'%s\' was not found. Deletion failed.", object);
        }
    }
    
    public void print(){
        List<T> current = firstList;
        while(current != lastList.nextList){
            current.print();
            System.out.println();
            current = current.nextList;
        }
    }
}
