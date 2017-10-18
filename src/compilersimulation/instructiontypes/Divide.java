/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes;

/**
 *
 * @author markoc
 */
public class Divide extends Arithmetic{
    
    public Divide(int code){
	super(code);
    }   
    
    @Override
    public void executeInstruction() throws Exception{
        if(errorInput((int)(accumulator / memory.fetchAtIndex(operand))))
	    throw new OverflowException();
        if(memory.fetchAtIndex(operand) == 0)
	    throw new DivisionByZeroException();
	
	accumulator /= memory.fetchAtIndex(operand);
    }
}
