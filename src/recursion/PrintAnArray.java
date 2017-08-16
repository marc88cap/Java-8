/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursion;

import java.util.Arrays;

/**
 *
 * @author markoc
 */
public class PrintAnArray {
    public void printArray(int[] array, int x){
        if(x<array.length){
            System.out.printf("%d ", array[x]);
            printArray(array,x+1);
        }
        else
            System.out.println();
    }
    
    public String stringReverse(char[] array, int x){
        if(x<array.length && x>=0){
            return String.format("%c%s",array[x], stringReverse(array,x-1));
        }
        else
            return "";
    }
    
    public int recursiveMinimum(int[] array, int i, int x){
        if(array.length>i){
            return recursiveMinimum(array,i+1,
                    (array[x]<=array[i]) ? x : i);
        }
        else
            return array[x];
           
    }
}
