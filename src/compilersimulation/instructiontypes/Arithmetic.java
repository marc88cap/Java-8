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
public class Arithmetic extends InstructionTypes{
    
    public Arithmetic(int code) {
	super(code);
    }
    
    public void set(Memory mem, double ac, int o){
	super.set(mem,ac,o);
    }
}
