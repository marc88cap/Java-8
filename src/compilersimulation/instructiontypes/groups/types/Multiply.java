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
public class Multiply extends Arithmetic{
    
    public Multiply(int code){
	super(code);
    }
    
    @Override
    public void executeInstruction() throws Exception{
	double result = accumulator * super.memory.fetchAtIndex(operand);
        if(checkInputError((int)result))
	    throw new OverflowException(); 
	
	accumulator = result;
    }
}
