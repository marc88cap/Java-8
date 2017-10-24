/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.simpletronhardware;

import compilersimulation.compiler.EntryTable;
import compilersimulation.instructiontypes.*;
import compilersimulation.instructiontypes.groups.Arithmetic;
import compilersimulation.instructiontypes.groups.InputAndOutput;
import compilersimulation.instructiontypes.groups.LoadAndStore;
import compilersimulation.instructiontypes.groups.TransferOfControl;
import compilersimulation.instructiontypes.groups.types.Add;
import compilersimulation.instructiontypes.groups.types.Branch;
import compilersimulation.instructiontypes.groups.types.BranchNeg;
import compilersimulation.instructiontypes.groups.types.BranchZero;
import compilersimulation.instructiontypes.groups.types.Divide;
import compilersimulation.instructiontypes.groups.types.Exponentiation;
import compilersimulation.instructiontypes.groups.types.Halt;
import compilersimulation.instructiontypes.groups.types.Load;
import compilersimulation.instructiontypes.groups.types.Multiply;
import compilersimulation.instructiontypes.groups.types.Read;
import compilersimulation.instructiontypes.groups.types.Reminder;
import compilersimulation.instructiontypes.groups.types.Store;
import compilersimulation.instructiontypes.groups.types.Write;
import compilersimulation.instructiontypes.groups.types.Subtract;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class Processor {
    InstructionTypes instructions[] = new InstructionTypes[14];
    //variables
    public Memory memory = new Memory(100); //to insert instructions
    public double accumulator = 0; //to save information
    public int instructionCounter = 0;
    public int operationCode = 0; //save operation code
    public int operand = 0; //save memory location
    public double instructionRegister = 0; //temp save the word (i.e. +0000)
    public String filePath = ""; // location of the file being edited
    
    public void run(EntryTable[] symbolTable) throws IOException, Exception{
	Scanner sc;
	instructions[0] = new Read(10);
	instructions[1] = new Write(11);
	instructions[2] = new Load(20);
	instructions[3] = new Store(21);
	instructions[4] = new Add(30);
	instructions[5] = new Subtract(31);
	instructions[6] = new Divide(32);
	instructions[7] = new Multiply(33);
	instructions[8] = new Reminder(34);
	instructions[9] = new Exponentiation(35);
	instructions[10] = new Branch(40);
	instructions[11] = new BranchNeg(41);
	instructions[12] = new BranchZero(42);
	instructions[13] = new Halt(43);

	try{
            sc = new Scanner(Paths.get(filePath));
	    while(sc.hasNext()){
		memory.setAtIndex(instructionCounter++, sc.nextInt());
	    }
	    System.out.printf("%s%n%s%n",
		    "*** Program loading completed ***",
		    "*** Program execution begins  ***");	
	    
	    memory.findAndStoreConstants(symbolTable);
	    
	    instructionCounter = 0;
	    while(memory.fetchAtIndex(instructionCounter) != 0){
		instructionRegister = memory.fetchAtIndex(instructionCounter++);
		operationCode = getOperationCode((int)instructionRegister);
		operand = getOperand((int)instructionRegister);
		for(InstructionTypes it : instructions){
		    
		    if(it.getOperationCode() == operationCode){
			if (it instanceof InputAndOutput)
			{
			    ((InputAndOutput) it).set(memory, operand);
			    
			    it.executeInstruction();
			    
			    memory = it.getMemory();
			}
			else if(it instanceof Arithmetic || it instanceof LoadAndStore)
			{
			    if(it instanceof Arithmetic)
			    {
				((Arithmetic) it).set(memory,accumulator,operand);
			    }
			    else
				((LoadAndStore) it).set(memory,accumulator,operand);
			    it.executeInstruction();
			    
			    if(it instanceof LoadAndStore)
				memory = it.getMemory();
			    
			    accumulator = it.getAccumulator();
			}
			else if(it instanceof TransferOfControl)
			{
			    ((TransferOfControl) it).set(memory, accumulator, operand, instructionCounter, filePath);
			    
			    it.executeInstruction();
			    
			    instructionCounter = ((TransferOfControl) it).getInstructionCounter();
			}

		    } 
		}
	    }  
	    
        }
        catch(IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    protected void dump(){
        StringBuilder s = new StringBuilder();
	Disk disk = new Disk();
		s.append(String.format("%n%s:%n"
                + "%s%15s%n" //accumulator
                + "%s%8s%n" //instructionCounter
                + "%s%7s%n" //instructionRegister
                + "%s%13s%n" //operationCode
                + "%s%19s%n%n"//operand
		+ "%s:%n%3s",
                "REGISTER", 
                "accumulator", ((accumulator<0)?String.format("%05.0f",accumulator):"+"+String.format("%04.0f",accumulator)),
                "instructionCounter", String.format("%02d",instructionCounter),
                "instructionRegister", ((instructionRegister<0)?String.format("%05.0f",instructionRegister):"+"+String.format("%04.0f",instructionRegister)),
                "operationCode", String.format("%02d",operationCode),
                "operand", String.format("%02d",operand),
		"MEMORY",""));
        for(int i=0;i<10;i++){
            s.append(String.format("%6d",i));
        }
        s.append("\n");
	
        for(int r=0;r<memory.getSize()/10;r++){
            s.append(String.format("%3d",r*10));
            for(int c=0;c<10;c++){
                instructionRegister = memory.fetchAtIndex((r*10)+c);
                s.append(String.format("%6s",((instructionRegister<0)?String.format("%05.0f",instructionRegister):"+"+String.format("%04.0f",instructionRegister))));
            }
            s.append("\n");
        }
	// print to screen
	System.out.print(s);
	// print to file
	disk.openFile(filePath.replaceFirst("[.]\\w+$", ".dump"));
	disk.printToFile(s.toString());
	disk.closeFile();
    }
    
    private int getOperationCode(int instruction){
        return instruction / 100;
    }
    
    private int getOperand(int instruction){
        return instruction % 100;
    }
    
    public boolean errorInput(int input){
	return input < (-9999) || input > 9999;
    }
    
    public void setFilePathToExecute(String filePath){
	this.filePath = filePath;
    }
}
