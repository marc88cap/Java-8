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
public class SearchList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int x=0;
        while(x<2){
        SecureRandom sr = new SecureRandom();
        List<Integer> i = new List<>();
            for(int c=0;c<20;c++){
            int n = sr.nextInt(100);
            i.insertAtBack(n);
        }
            
        i.print();
        System.out.printf("%nInsert a value to be searched for from the list above.%n> ");
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Result: "+i.search(sc.nextInt()));
        x++;
        }
    }
    
}
