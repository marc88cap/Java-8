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
public class Store extends LoadAndStore{
    
    public Store(int code){
	super(code);
    }
    
    @Override
    public void executeInstruction(){
	memory.setAtIndex(operand, accumulator);
    }
}
