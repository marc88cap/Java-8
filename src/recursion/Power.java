/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursion;

/**
 *
 * @author markoc
 */
public class Power {
    public Power(int base, int exponent){
        System.out.printf("%d^%d = %d%n", base,exponent, power(base, exponent));
    }
    
    private int power(int base, int exponent){
        if(exponent == 1){
            return base;
        }
        else{
            return base * power(base, exponent - 1);
        }
    }
}
