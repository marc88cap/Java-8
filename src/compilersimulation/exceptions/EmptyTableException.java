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
public class EmptyTableException extends RuntimeException {

    public EmptyTableException() {
	this("Table");
    }
    
    public EmptyTableException(String name){
	super(name + " is empty.");
    }
    
}
