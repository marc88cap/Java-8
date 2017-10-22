/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.simpletronhardware;

import compilersimulation.compiler.EntryTable;

/**
 *
 * @author markoc
 */
public class Memory {
    double[] memory;
    int size;
    
    public Memory(int size){
	memory = new double[size];
	this.size = size;
    }
    
    public double fetchAtIndex(int index){
	return memory[index];
    }
    
    public void setAtIndex(int index, double value){
	memory[index] = value;
    }
    
    public int getSize(){
	return this.size;
    }
    
    public void findAndStoreConstants(EntryTable[] symbolTable){
	for(EntryTable et : symbolTable){
	    if(et != null && et.type == 'C')
		memory[et.location] = Double.parseDouble(String.valueOf(et.symbol));
	}
    }
}
