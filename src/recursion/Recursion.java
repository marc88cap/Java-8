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
public class Recursion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.printf("%n%s%n","POWER METHOD:");
        new Power(3,4);
        new Power(4,2);
        
        System.out.printf("%n%s%n","PALINDROMES:");
        new Palindrome("able was i. ere i saw elba.");
        new Palindrome("able was i. ere i saw.");
        new Palindrome("Čirič");
        new Palindrome("Marko");
        
        System.out.printf("%n%s:%n","EIGHT QUEENS - BACKTRACKING");
        new EightQueens();
        
        System.out.printf("%n%s:%n", "PRINT AN ARRAY");
        PrintAnArray print = new PrintAnArray();
        int[] intArray = {5,7,3,9,2,6,1,8,4};
        String string = "My name is Marko Čirič";
        print.printArray(intArray,0);
        System.out.println("Minimal value in array: "
                +print.recursiveMinimum(intArray,0,0));
        System.out.printf("%n\"%s\"%nstring printed backwards:%n%s%n",
                string,print.stringReverse(string.toCharArray(), string.length()-1));
        
    }
    
}
