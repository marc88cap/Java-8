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
public class OutOfMemoryException extends RuntimeException {

    public OutOfMemoryException() {
	this("Ran out of memory during compilation.");
    }
    
    public OutOfMemoryException(String message) {
	super(message);
    }
    
}
