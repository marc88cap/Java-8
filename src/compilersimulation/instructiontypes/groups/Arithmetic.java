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
public class Arithmetic extends InstructionTypes{
    
    public Arithmetic(int code) {
	super(code);
    }
    
    public void set(Memory mem, double ac, int o){
	super.memory = mem;
	super.accumulator = ac;
	super.operand = o;
    }
}
