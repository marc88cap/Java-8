package sortsearchbigo;


import java.util.Arrays;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author markoc
 */
public class RecursiveBinarySearch {
    static private int search(int[] data, int first, int middle, int last, int key){
        if(data[middle]==key){
            return middle;
        }
        if(first==last){
            return -1;
        }
        else {
            if(data[middle]>=key)
                last = middle;            
            else
                first = middle+1;
            
            middle = (first+last)/2;
            return search(data,first, middle,last,key);
        }
    }
    
    static public void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int[] data = {55,13,58,66,1,69,42,131,1568,1500,41,23,65,7,12,15,34};
        Arrays.sort(data);
        System.out.println("List to be searched: "+Arrays.toString(data));
        System.out.printf("%s:%n> ","Input a value to be searched for from the list above.");
        int searchKey = sc.nextInt();
        int result = search(data, 0, data.length/2, data.length-1, searchKey);
        if(result>=0){
            System.out.printf("Value %d can be found at index %d.%n", searchKey, result);
        }
        else{
            System.out.println("List does not contain the searched key");
        }
    }
}
