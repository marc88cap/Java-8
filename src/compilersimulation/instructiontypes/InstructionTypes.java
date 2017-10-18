/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes;

import compilersimulation.simpletron.Memory;
import compilersimulation.simpletron.Processor;

/**
 *
 * @author markoc
 */
public class InstructionTypes extends Processor { 
    final int code;
        
    public InstructionTypes(int code){
	this.code = code;
    }

    public void set(Memory mem, int o){
	setMemory(mem);
	setOperand(o);
    }
    
    public void set(Memory mem, double ac, int o){
	setMemory(mem);
	setAccumulator(ac);
	setOperand(o);
    }
    
    public void set(Memory mem, int o, double ac, int ic){
	setOperand(o);
	setAccumulator(ac);
	setInstructionCounter(ic);
	setMemory(mem);
    }
    
    public void executeInstruction() throws Exception{
	System.out.println("Nothing to execute");
    }
    
    public void setInstructionCounter(int ic){
	super.instructionCounter = ic;
    }
    
    public void setMemory(Memory mem){
	super.memory = mem;
    }
    
    public void setOperand(int o){
	super.operand = o;
    }
    
    public void setAccumulator(double ac){
	super.accumulator = ac;
    }
    
    public int getOperationCode(){
	return this.code;
    }
    
    public int getInstructionCounter(){
	return super.instructionCounter;
    }
    
    public double getAccumulator(){
	return super.accumulator;
    }
    
    public Memory getMemory(){
	return super.memory;
    }
    
    public int getOperand(){
	return super.operand;
    }
}
