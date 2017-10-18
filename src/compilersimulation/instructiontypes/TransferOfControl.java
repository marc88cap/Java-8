/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes;

import compilersimulation.simpletron.Memory;

/**
 *
 * @author markoc
 */
public class TransferOfControl extends InstructionTypes{
    
    public TransferOfControl(int code) {
	super(code);
    }
    
    public void set(Memory mem, int o, double ac, int ic){
	super.set(mem,o,ac,ic);
    }
}
