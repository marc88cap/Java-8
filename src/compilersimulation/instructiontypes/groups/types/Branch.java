/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes.groups.types;

import compilersimulation.instructiontypes.groups.TransferOfControl;

/**
 *
 * @author markoc
 */
public class Branch extends TransferOfControl{
    
    public Branch(int code){
	super(code);
    }

    @Override
    public void executeInstruction(){
	System.out.println("branching"+ operand);
        instructionCounter = operand;
    }
}
