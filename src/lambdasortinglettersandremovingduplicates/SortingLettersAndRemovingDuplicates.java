/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambdasortinglettersandremovingduplicates;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author markoc
 */
public class SortingLettersAndRemovingDuplicates {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random r = new Random();
        List<Character> charList = r.ints(30,'a','a'+26)
                .boxed()
                .map(i -> (char)i.byteValue())
                .collect(Collectors.toList());
        
        System.out.printf("%s %n","Sorted in ascending order: ");
        charList.stream()
                .sorted(Comparator.comparing(i->i.charValue()))
                .forEach(i-> System.out.printf("%s", i.charValue()));
        
        System.out.printf("%n%n%s %n","Sorted in descending order: ");
        charList.stream()
                .sorted(Comparator.comparing(i->i.charValue()))
                .sorted(Comparator.reverseOrder())
                .forEach(i-> System.out.printf("%s", i.charValue()));
        
        System.out.printf("%n%n%s %n","Sorted in ascending order \nwith duplicates removed: ");
        charList.stream()
                .sorted(Comparator.comparing(i->i.charValue()))
                .distinct()
                .forEach(i-> System.out.printf("%s", i.charValue()));
       
        System.out.println();
    }
    
}
