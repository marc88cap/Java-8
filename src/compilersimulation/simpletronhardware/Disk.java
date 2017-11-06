/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.simpletronhardware;

import java.io.FileNotFoundException;
import java.util.Formatter;

/**
 *
 * @author markoc
 */
public class Disk {
    private Formatter fileFormatter;
    
    public void openFile(String filepath){
	try{
	    fileFormatter = new Formatter(filepath);
	}
	catch(FileNotFoundException e){
	    e.printStackTrace();
	}
    }
    
    public void printToFile(String string){
	fileFormatter.format("%s%n", string);
    }
    
    public void closeFile(){
	fileFormatter.close();
    }
}
