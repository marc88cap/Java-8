/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortsearchbigo;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class RecursiveLinearSearch {
    static private int search(int[] data, int index, int searchKey){
        if(data[index]==searchKey){
            return index;
        }
        else if(data.length-1==index){
            return -1;
        }
        else{
            return search(data, index+1, searchKey);
        }
    }
    
    static public void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int[] data = {55,13,58,66,1,69,42,131,1568,1500,41,23,65,7,12,15,34};
        System.out.println("List to be searched: "+Arrays.toString(data));
        System.out.printf("%s:%n> ","Input a value to be searched for from the list above.");
        int searchKey = sc.nextInt();
        int result = search(data, 0, searchKey);
        if(result>=0){
            System.out.printf("Value %d can be found at index %d.%n", searchKey, result);
        }
        else{
            System.out.println("List does not contain the searched key");
        }
    }
}
