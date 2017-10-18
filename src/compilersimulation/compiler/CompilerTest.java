/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.compiler;

import compilersimulation.errorcheck.SimpleErrorCheck;
import java.io.IOException;

/**
 *
 * @author markoc
 */
public class CompilerTest {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Compiler app = new Compiler();
	SimpleErrorCheck<String> s = new SimpleErrorCheck<>();
	try{
	    String filePath = s.openFile("src/compilersimulation/simplefiles/sumOfTwo.simple");
	    System.out.println("File is ready for compilation....");
	    System.out.println("Starting compilation process....");
	    app.compile(filePath);
	    app.print();
	}
	catch(IOException e){
	    e.printStackTrace();
	}
	catch(OutOfMemoryException e){
	    e.printStackTrace();
	}
//	app.insert(23,'C',07);
//	app.insert(64,'V',00);
//	app.insert(12,'C',66);
//	app.insert(867,'L',81);
//	app.insert(34,'V',54);
//	app.insert(23,'C',10);
//	app.insert(65,'L',50);
//	app.insert(03,'L',32);
//	
//	app.printSymbolTable();
    }
    
}
