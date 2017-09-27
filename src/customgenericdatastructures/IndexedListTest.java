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
public class IndexedListTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IndexedList<String> list = new IndexedList<>();
        list.insertInIndexedList("Gremo");
        list.insertInIndexedList("gor");
        list.insertInIndexedList("če");
        list.insertInIndexedList("ne");
        list.insertInIndexedList("potem");
        list.insertInIndexedList("ne");
        list.insertInIndexedList("vem");
        list.insertInIndexedList("kaj");
        list.insertInIndexedList("se");
        list.insertInIndexedList("bo");
        list.insertInIndexedList("zgodilo");
        list.insertInIndexedList("jutri");
        list.insertInIndexedList("ampak");
        list.insertInIndexedList("Nekega");
        list.insertInIndexedList("lepega");
        list.insertInIndexedList("dne");
        list.insertInIndexedList("se");
        list.insertInIndexedList("testiram");
        list.insertInIndexedList("še");
        list.insertInIndexedList("enkrat");
        list.insertInIndexedList("toliko");
        list.insertInIndexedList("različnih");
        list.insertInIndexedList("črk");
        list.insertInIndexedList("ne");
        list.insertInIndexedList("zmorem");
        list.insertInIndexedList("več");
        list.insertInIndexedList("moj");
        list.insertInIndexedList("diuš");
        list.insertInIndexedList("še");
        list.insertInIndexedList("malo");
        list.insertInIndexedList("dodam");
        list.insertInIndexedList("da");
        list.insertInIndexedList("bo");
        list.insertInIndexedList("bolj");
        list.insertInIndexedList("polno");
        list.print();
        
        list.searchIndexedList("dius");
        System.out.println();
        list.searchIndexedList("diuš");
        System.out.println();
        list.deleteFromIndexedList("dioš");
        System.out.println();
        list.deleteFromIndexedList("diuš");
    }
    
}
