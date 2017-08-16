/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursion;

/**
 *
 * @author markoc
 */
public class Palindrome {
    String input;
    int length;

    /**
     *
     * @param input represents a word or string that will be trimmed of white
     *              spaces and punctuation, ignoring case, then checked if its 
     *              a palindrome
     */
    public Palindrome(String input){
        this.input = input.toLowerCase().replaceAll("\\s|\\p{P}", "");
        this.length = this.input.length()-1;
        
        System.out.printf("\"%s\" is %s%n", input, (testPalindrome(this.input, 0, this.length))? "a PALINDROME" : "NOT a palindrome");
    }
    
    private boolean testPalindrome(String input, int head, int tail){
        /*
        * Check if head and tail character in level are the same,
        * if they are not return false and break recursion
        */
        if(input.charAt(head) != input.charAt(tail)){ 
            return false;
        }
        /*
        * Check if we reached the middle of the input, if true:
        * no differences have been found, so we return TRUE and end recursion
        */
        else if(this.length%2 == 0 && head == this.length/2
                || this.length%2 != 0 && head == this.length+1/2){
            return true;
        }
        /*
        * If above conditions are false, we go one level deeper:
        * we move towards the centre of the input,
        * increasing the head and decreasing the tail by 1
        */
        else{
            return testPalindrome(input, head+1, tail-1);
        }
    }
}
