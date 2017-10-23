/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes.groups.types;

import compilersimulation.exceptions.OverflowException;
import compilersimulation.instructiontypes.groups.Arithmetic;

/**
 *
 * @author markoc
 */
public class Subtract extends Arithmetic{
    
    public Subtract(int code){
	super(code);
    }
    
    @Override
    public void executeInstruction() throws Exception{
        if(errorInput((int)(accumulator - super.memory.fetchAtIndex(operand))))
	    throw new OverflowException();
        
	accumulator -= super.memory.fetchAtIndex(operand);
    }
}