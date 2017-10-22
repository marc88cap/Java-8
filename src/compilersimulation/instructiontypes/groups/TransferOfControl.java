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
public class TransferOfControl extends InstructionTypes{
    
    public TransferOfControl(int code) {
	super(code);
    }
    
    public void set(Memory mem, double ac, int o,  int ic, String fp){
	super.memory = mem;
	super.accumulator = ac;
	super.operand = o;
	super.instructionCounter = ic;
	super.filePath = fp;
    }
}
