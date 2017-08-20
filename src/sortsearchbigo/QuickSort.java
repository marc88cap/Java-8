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
public class QuickSort {
    static private int[] quickSort(int[] data, int first, int last){
        //store rang
        int low = first;
        int high = last;
        //partitioning step
        int e = data[first]; //store first element from list to find its sorted position
            //loop while last counter and first counter dont cross
            while(last-first>0){
                //compare element from tail to head
                for(;last>=first;last--){
                    if(e>data[last]){
                        data[first] = data[last];
                        data[last] = e;
                        break;
                    }
                }
                //compare element from head to tail
                for(;last>first;first++){
                    if(e<data[first]){
                        data[last] = data[first];
                        data[first] = e;
                        break;
                    }
                }              
            }
            //recursive step
            if(low<last)
            quickSort(data,low,last);
            if(first<high)
            quickSort(data,first+1,high);
         
        return data;
    }
    
    static public void main(String[] args){
        int[] data = {55,1500,58,66,1,69,42,2,131,1568,41,12,15,34,871,102,2,35,431,3};
        System.out.printf("Unsorted: %s%n",Arrays.toString(data));
        System.out.printf("Sorted with quicksort: %s%n",Arrays.toString(quickSort(data,0,data.length-1)));
    }
}
