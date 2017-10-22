/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes.groups;

import compilersimulation.instructiontypes.InstructionTypes;
import compilersimulation.simpletronhardware.Memory;

/**
 *
 * @author markoc
 */
public class InputAndOutput extends InstructionTypes{
    
    public InputAndOutput(int code) {
	super(code);
    }
    
    public void set(Memory mem, int o){
	super.memory = mem;
	super.operand = o;
    }
    
}
