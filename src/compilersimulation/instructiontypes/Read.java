/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes;

import compilersimulation.simpletron.Memory;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class Read extends InputAndOutput{
    
    public Read(int code){
	super(code);
    }
    
    @Override
    public void executeInstruction(){
        Scanner sc = new Scanner(System.in);
        int input = 0;
        do{
            System.out.printf("> ", "input");
            input = sc.nextInt();
            if(errorInput(input)){
                System.err.printf("%s%n","!!! Input should be between -9999 and 9999 inclusive !!!");
            }
            else{
                memory.setAtIndex(operand, input);
            }
        }while(errorInput(input));
    }
}
