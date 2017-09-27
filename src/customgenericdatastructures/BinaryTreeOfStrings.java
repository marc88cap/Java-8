/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class BinaryTreeOfStrings {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tree<String> s = new Tree<>();
        
        System.out.printf("%s%n> ","Input a sentance.");
        
        String[] sentence = sc.nextLine().replaceAll("\\p{P}*", "").split("\\s");
        for(String w : sentence)
            s.insertNode(w);
        
        System.out.println("Inorder traversal:");
        s.inorderTraversal();
        System.out.printf("%nPreorder traversal: %n");
        s.preorderTraversal();
        System.out.printf("%nPostorder traversal: %n");
        s.postorderTraversal();
        System.out.printf("%n%n");
    }
    
}
