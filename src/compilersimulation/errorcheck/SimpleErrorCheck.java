/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.errorcheck;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
/**
 *
 * @author markoc
 */
public class SimpleErrorCheck<E> implements Queue<E>{
    private InstructionNode<E> firstInstruction;
    private InstructionNode<E> lastInstruction;
    private int size;
    private Path filePath;
    private Scanner fileSimpleCode;
    
    public SimpleErrorCheck(){
        firstInstruction = lastInstruction = null;
    }
    
    // method checks if the loaded program is written as expected
    public boolean checkErrors() throws Exception{
        int endCounter = 0;
        int lineNum = -1;
        while(fileSimpleCode.hasNext())
        {
	    // get next line from file
            String simpleLine = fileSimpleCode.nextLine();
	    // break line to parts
            String[] part = simpleLine.split("\\s");
            // if current line is not bigger than previous 
            if(part[0].compareTo(String.valueOf(lineNum)) > 0)
                lineNum = Integer.valueOf(part[0]); // set store current line for next comparison
            else
                throw new Exception("Line number must be in ascending order.");
            // check every line but rem line if lower case letters were used: only lower case allowed
            if(part[1].matches("^(?!rem).*") && checkLowerCase(simpleLine))
                throw new Exception("Only lowercase letters allowed.");
            // check if commad provided is allowed / recognized
            if(checkCommand(part[1]))
                throw new IllegalArgumentException("Command '"+part[1]+"' at line "+part[0]+" not found.");
            // check if more than one 'end' command is found: only one allowed
            if(part[1].matches("\\b(end)\\b") && endCounter < 1)
                endCounter++;
            else if (part[1].matches("\\b(end)\\b"))
                throw new Exception("Command \'end\' only once allowed.");
	    // run when command let is found
            if(part[1].matches("\\b(let)\\b"))
		// check if operators are allowed
                if(checkOperator(simpleLine.replaceFirst("^[0-9]+\\slet\\s", "")))
                    throw new ArithmeticException("Operator at line "+part[0]+" not allowed.");
                // check for correct arithmetic order
                else if(checkArithmeticOrder(simpleLine.replaceFirst("^[0-9]+\\slet\\s", "")))
                    throw new ArithmeticException("Wrong arithmetic syntax.");
	    // validate input, can only be followed by one character variable
	    if(part[1].matches("\\b(input|print)\\b") && checkInput(simpleLine.replaceFirst("^[0-9]+\\s(input|print)\\s", "")))
		throw new IllegalArgumentException("Variable at line "+part[0]+" should be one lowercase letter.");
	    // validate if line syntax
	    if(part[1].matches("\\b(if)\\b") && checkIf(simpleLine.replaceFirst("^[0-9]+\\sif\\s", "")))
		throw new Exception("Wrong syntax at line "+part[0]);
        }
	return true;
    }
    
    // set file
    public String openFile(String filepath){
        filePath = Paths.get(filepath); // convert file to file path
        try
        { 
            fileSimpleCode = new Scanner(filePath); // read file
	    this.checkErrors();
        }
        catch (IOException e){
            e.printStackTrace();
	    System.exit(0);
        }
	catch (Exception e){
	    e.printStackTrace();
	    System.exit(0);
	}
	return filepath;
    }
    
    // check for correct arithmetic order
    private boolean checkArithmeticOrder(String line){
        // rules
        // Only one character per variable allowed, paranthesis allowed 
        // next to variable or digit, in between one whitespace e.g. y = i * (a - 3)
        // first charcter is a variable to be set, followed by symbol equals
        // followed by variable with one character or digit till end of the line
        return !line.matches("(^[a-z]{1})$|(^[a-z]{1})(\\s=)(\\s([(]?([0-9]+|[a-z])[)]?|[+\\-\\/*]))+$");
    }
    
    // check if everything except remarks (rem) is in lowercase
    private boolean checkLowerCase(String line){
        return line.matches("^(.*[A-Z].*$)");
    }
    
    // check if operator is valid
    private boolean checkOperator(String line){
	// operators allowed: +, -, * and /
        return line.replaceAll("([\\w\\d\\s=()])", "").matches("(.*[^+\\-*\\/].*)");
    }
    
    // check if command is valid
    private boolean checkCommand(String line){
        return !line.matches("\\b(rem|input|let|fill|end|print|goto|if)\\b");
    }
    
    // check for correct input variable
    private boolean  checkInput(String line){
	return !line.matches("^[a-z]{1}$");
    }
    
    // check for syntax errors
    private boolean checkIf(String line){
	return !line.matches("^([a-z]|[-?0-9]+)\\s(==|<=|>=|>|<|=)\\s([a-z]|[-?0-9]+)\\s\\bgoto\\b\\s[0-9]+$");
    }
    
    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public boolean offer(E e) {
        if(isEmpty())
            firstInstruction = lastInstruction = new InstructionNode<>(e,null,null);
        else
            lastInstruction = lastInstruction.nextInstruction = new InstructionNode<>(e,lastInstruction,null);
        
        size++;
        return true;
    }

    @Override
    public int size() {
       return size;
    }

    @Override
    public boolean isEmpty() {
        if(firstInstruction == null)
            return true;
        return false;
    }

    @Override
    public E remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E poll() {
        E item = peek();
        
        if(isEmpty())
            return null;
        else
        {
            firstInstruction = firstInstruction.nextInstruction;
        }
        size--;
        return item;
    }

    @Override
    public E element() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E peek() {
        return firstInstruction.data;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
