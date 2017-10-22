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
public class DivisionByZeroException extends Exception {

    public DivisionByZeroException() {
	this("!!! Division by zero !!!");
    }
    
    public DivisionByZeroException(String string){
	super("*** Simpletron execution abnormally terminated ***");
    }
    
}
