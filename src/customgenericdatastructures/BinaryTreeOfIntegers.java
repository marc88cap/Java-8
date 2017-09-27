/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.security.SecureRandom;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class BinaryTreeOfIntegers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tree<Integer> i = new Tree<>();
        SecureRandom sr = new SecureRandom();
        
        for(int c=0;c<20;c++){
            int n = sr.nextInt(100);
            System.out.printf("%d ",n);
            i.insertNode(n);
        }

//        int[] integers = {15,20,18,17,9,21,14,19,4,5,3,16};
//        for(int n : integers)
//            i.insertNode(n);
        
        System.out.printf("%nDepth: %n");
        i.getDepth();
        System.out.println("Inorder traversal:");
        i.inorderTraversal();
        System.out.printf("%nPreorder traversal: %n");
        i.preorderTraversal();
        System.out.printf("%nPostorder traversal: %n");
        i.postorderTraversal();
        System.out.printf("%n%n");
    }
    
}
