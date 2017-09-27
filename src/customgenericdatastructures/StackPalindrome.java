/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.util.Stack;

/**
 *
 * @author markoc
 */
public class StackPalindrome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String input;
        if(isPalindrome(input = "Marko Čirič is my name."))
            System.out.printf("Input \"%s\" is a palindrome.%n",input);
        else
            System.out.printf("Input \"%s\" is NOT a palindrome.%n", input);
        
        if(isPalindrome(input = "ČiriČ"))
            System.out.printf("Input \"%s\" is a palindrome.%n",input);
        else
            System.out.printf("Input \"%s\" is NOT a palindrome.%n", input);
    }
    //Palindrome tester using Stack<String> with spaces and punctuation ignored
    private static boolean isPalindrome(String input){
        char[] chars = input.replaceAll("\\p{P}*\\s*", "").toCharArray();
        Stack<Character> letters = new Stack<>();
        
        //Populate Stack<String>
        for(char c : chars)
            letters.push(c);
        //If characters from stack and array dont match return false
        for(char c : chars)
            if(letters.pop().compareTo(c)!=0)
                return false;
        return true;
    }
}
