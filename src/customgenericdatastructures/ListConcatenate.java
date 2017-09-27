/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.security.SecureRandom;
import java.util.LinkedList;

/**
 *
 * @author markoc
 */
public class ListConcatenate {

    private static LinkedList<Character> concatenate(LinkedList<Character> first, LinkedList<Character> second){
        LinkedList<Character> result = first;
        for(Character obj : second)
            result.add(obj);
        return result;
    }
    public static void main(String[] args) {
        LinkedList<Character> first, second;
        first = generateChars(10);
        second = generateChars(20);
        
        System.out.printf("First: %s%nSecond: %s%n",first,second);
        System.out.printf("Concatenate: %s%n",concatenate(first,second));
    }
    
    private static LinkedList<Character> generateChars(int count){
        LinkedList<Character> result = new LinkedList<>();
        SecureRandom r = new SecureRandom();
        for(int i=0;i<count;i++){
            int c = r.nextInt(26)+'a';
            result.add((char)c);
        }
        return result;
    }
}
