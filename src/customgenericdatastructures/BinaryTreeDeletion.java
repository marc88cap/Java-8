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
public class BinaryTreeDeletion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tree<Integer> ints = new Tree<>();
//        int[] integers = {30,20,10,5,3,1,40,50,52,25,23,21,27,24,22,7,6,8,9,4,2,35,33,37,48,52};
        int[] integers = {49,28,18,11,19,40,32,44,83,70,97,69,73,92,99};
        for(int i : integers)
            ints.insertNode(i);
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n%n");
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nInsert 71%n");
        ints.insertNode(71);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nInsert 72%n");
        ints.insertNode(72);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nDelete 70%n");
        ints.deleteNode(70);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nDelete 83%n");
        ints.deleteNode(83);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nDelete 71%n");
        ints.deleteNode(71);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
        System.out.printf("%n  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%nDelete 49%n");
        ints.deleteNode(49);
        System.out.printf("%n      Depth: ");
        ints.getDepth();
        System.out.printf("  Pre order: ");
        ints.preorderTraversal();
        System.out.printf("%n Post order: ");
        ints.postorderTraversal();
        System.out.printf("%n   In order: ");
        ints.inorderTraversal();
        System.out.printf("%nLevel order: ");
        ints.levelOrderTraversal();
        System.out.println(); 
        System.out.println(); 
        ints.outputTree(0);
        
    }
    
}
