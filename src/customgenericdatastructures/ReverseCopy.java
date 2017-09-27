/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author markoc
 */
public class ReverseCopy<T> {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> stringList = new LinkedList<>();
        stringList.add("Moje");
        stringList.add("ime");
        stringList.add("je");
        stringList.add("Marko");
        
        List<Integer> intList = new LinkedList<>();
        intList.add(4);
        intList.add(3);
        intList.add(2);
        intList.add(1);
        intList.add(0);
        //display original list content
        System.out.println("ORIGINAL:");
        System.out.printf("String List: %s%n",stringList.toString());
        System.out.printf("Integer List: %s%n",intList.toString());
        //reverse list content
        stringList = reversedCopy(stringList);
        intList = reversedCopy(intList);
        //display reversed list content
        System.out.println("\nREVERSED:");
        System.out.printf("String List: %s%n",stringList.toString());
        System.out.printf("Integer List: %s%n",intList.toString());
        
        System.out.printf("%nSTACK REVERSE:%n");
        reverseDisplay("Marko Čirič is my name.");
        System.out.println();
    }
    //Copying list in reversed order using List<T>
    private static <T> List<T> reversedCopy(List<T> list){
        int index = (list.size()); 
        List<T> result = new LinkedList<T>();
        while(index>0){
            result.add(list.get(--index));
        }
        return result;
    }
    //Reverse display using Stack<String> 
    private static void reverseDisplay(String input){
        System.out.printf("Original: %s%n",input);
        String[] words = input.split(" ");
        Stack<String> stack = new Stack<>();
        for(String w : words)
            stack.push(w);
        System.out.printf("Reverse: ");
        while(!stack.isEmpty())
            System.out.printf("%s ",stack.pop());
        System.out.printf("%n");
        
    }

}
