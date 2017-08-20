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
public class BucketSort {
    static private int[] sort(int[] data){
        //sorts up to 9 digit numbers
        for(int d=1;d<=9;d++){
            int[][] result = new int[10][data.length];
            //distribution pass
            for(int i=0;i<data.length;i++){
                String value = String.valueOf(data[i]);
                int n = Integer.parseInt(String.valueOf((value.length()-d>=0)?value.charAt(value.length()-d):'0'));
                for(int x=0;x<result[0].length;x++){
                    if(result[n][x]==0){
                        result[n][x] = Integer.parseInt(value);
                        break;
                    }
                }
            }
            //gathering pass
            int id = 0;
            for(int i=0;i<result.length;i++){
                for(int x=0;x<result[i].length;x++){
                    if(result[i][x]==0)
                        break;
                    data[id] = result[i][x];
                    id++;
                    //return data when all values are stored into one bucket
                    if(x==data.length-1) return data;
                }
            }
//            System.out.println("      Pass: "+Arrays.toString(result[0]));
            System.out.printf("Pass #%d: %s%n",d, Arrays.toString(data));
        }
       
        return data;
    }
    
    static public void main(String[] args){
        int[] data = {1,55,58,66,1,69,1568,1500,1050,1301,41,23,7,12,15,34,59,88};
        System.out.printf("Unsorted: %s%n%n",Arrays.toString(data));
        System.out.printf("%nSorted: %s%n",Arrays.toString(sort(data)));
    }
}
