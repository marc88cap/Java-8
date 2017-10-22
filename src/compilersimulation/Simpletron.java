/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compilersimulation;

import compilersimulation.compiler.EntryTable;
import compilersimulation.compiler.Compiler;
import compilersimulation.errorcheck.SimpleErrorCheck;
import compilersimulation.exceptions.OutOfMemoryException;
import compilersimulation.simpletronhardware.Processor;
import java.io.IOException;


/**
 *
 * @author markoc
 */  
public class Simpletron {
    public static void main(String args[]){
        Processor p = new Processor();
	
	
	Compiler app = new Compiler();
	SimpleErrorCheck<String> s = new SimpleErrorCheck<>();
	try{ 
	    String filePath = s.openFile("src/compilersimulation/simplefiles/counterLoopAvg.simple");
	    System.out.println("File is ready for compilation....");
	    System.out.println("Starting compilation process....");
	    
	    EntryTable[] symbolTable = app.compile(filePath);
	    
	    p.setFilePathToExecute(app.getFilePathSML());
	    p.run(symbolTable);
	}
	catch(IOException e){
	    e.printStackTrace();
	}
	catch(OutOfMemoryException e){
	    e.printStackTrace();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
}
