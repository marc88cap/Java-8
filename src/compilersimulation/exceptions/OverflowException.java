/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.exceptions;

/**
 *
 * @author markoc
 */
public class OverflowException extends Exception {

    public OverflowException() {
	this("Result got overflown");
    }
    
    public OverflowException(String string){
	super(string 
		+ "\n*** Simpletron execution abnormally terminated ***");
    }
    
}
