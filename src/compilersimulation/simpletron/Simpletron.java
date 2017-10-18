/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compilersimulation.simpletron;

import compilersimulation.errorcheck.SimpleErrorCheck;
import compilersimulation.compiler.OutOfMemoryException;
import java.io.IOException;


/**
 *
 * @author markoc
 */  
public class Simpletron {
    public static void main(String args[]){
        Processor p = new Processor();
	
	
	compilersimulation.compiler.Compiler app = new compilersimulation.compiler.Compiler();
	SimpleErrorCheck<String> s = new SimpleErrorCheck<>();
	try{ 
	    String filePath = s.openFile("src/compilersimulation/simplefiles/sumOfTwo.simple");
	    System.out.println("File is ready for compilation....");
	    System.out.println("Starting compilation process....");
	    p.run(app.compile(filePath));
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
