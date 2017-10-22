/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.instructiontypes;

import compilersimulation.simpletronhardware.Memory;
import compilersimulation.simpletronhardware.Processor;

/**
 *
 * @author markoc
 */
public class InstructionTypes extends Processor { 
    final int code;
        
    public InstructionTypes(int code){
	this.code = code;
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
    
    public void setFilePath(String fp){
	super.filePath = fp;
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
