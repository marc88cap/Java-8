package lambdaduplicatewordremoval;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class DuplicateWordRemoval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("%s %n> ", "Input a sentance");
        
        List<String> input = Arrays.asList(sc.nextLine().split("\\s"));
        
        input.stream()
                .map(word -> word.replaceAll("\\p{P}",""))
                .distinct()
                .sorted(Comparator.comparing(s -> s.toString(),String.CASE_INSENSITIVE_ORDER))
                .forEach(s-> System.out.printf("%s%n",s.toString()));
    }
    
}
