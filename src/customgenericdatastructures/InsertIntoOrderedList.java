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
public class InsertIntoOrderedList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Integer> ol = new List<>();
        ol.print();
        ol.insertAscending(5);
        ol.print();
        ol.insertAscending(3);
        ol.print();
        ol.insertAscending(8);
        ol.print();
        ol.insertAscending(1);
        ol.print();
        ol.insertAscending(7);
        ol.print();
        ol.insertAscending(2);
        ol.print();
        ol.insertAscending(6);
        ol.print();
        ol.insertAscending(4);
        ol.print();
        ol.insertAscending(9);
        ol.print();
        ol.insertAscending(2);
        ol.print();
        ol.insertAscending(5);
        ol.print();
        System.out.printf("%n%nNew list:");
        List<Integer> ol1 = new List<>();
        ol1.insertAscending(3);
        ol1.print();
        ol1.insertAscending(5);
        ol1.print();
        ol1.insertAscending(6);
        ol1.print();
        ol1.insertAscending(7);
        ol1.print();
        System.out.printf("%n%nMerge: ");
        ol.merge(ol1);
        System.out.printf("%n%nPrint List Backward: %n");
        ol.printListBackward();
        System.out.println();
    }
    
    
}
    