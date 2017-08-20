/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortsearchbigo;

import java.util.Arrays;

/**
 *
 * @author markoc
 */
public class BubbleSort {
    private static int[] bubbleSort(int[] data){
        
        for(int l=0;l<data.length;l++){
            int count = 0;
            for(int i=1;i<data.length-l;i++){
                if(data[i-1]>data[i]){
                    count++;
                    int temp = data[i];
                    data[i] = data[i-1];
                    data[i-1] = temp;
                }
            }
            if(count==0)break;
            System.out.printf("Pass #%d: %s%n",l+1,Arrays.toString(data));
        }
        
        return data;
    }
    
    public static void main(String[] args){
        int[] data = {55,13,58,66,1,69,42,91,41,23,65,0,12,15,34};
        System.out.printf("Unsorted list: %s%n%n",Arrays.toString(data));
        System.out.printf("%nSorted list: %s%n",Arrays.toString(bubbleSort(data)));
    }
}
