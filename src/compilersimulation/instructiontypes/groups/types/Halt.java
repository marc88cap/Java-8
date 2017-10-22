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
public class Halt extends TransferOfControl{
    
    public Halt(int code){
	super(code);
    }
    
    @Override
    public void executeInstruction(){
        System.out.printf("%s%n","*** Simpletron execution terminated ***");
	super.dump();
    }
}
