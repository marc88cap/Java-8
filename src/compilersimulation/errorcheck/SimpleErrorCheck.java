/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.errorcheck;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 *
 * @author markoc
 */
public class SimpleErrorCheck{
    private Path filePath;
    private Scanner fileSimpleCode;
    
    // method checks if the loaded program is written as expected
    public boolean checkErrors() throws Exception{
        int endCounter = 0;
	int gosubCounter = 0, returnCounter = 0; 
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
            // check if commad provided is allowed / recognized
            if(checkCommand(part[1]))
                throw new IllegalArgumentException("Command '"+part[1]+"' at line "+part[0]+" not found.");
            // check if more than one 'end' command is found: only one allowed
            if(part[1].matches("(?i)\\b(end)\\b") && endCounter < 1)
                endCounter++;
            else if (part[1].matches("(?i)\\b(end)\\b"))
                throw new Exception("Command \'end\' only once allowed.");
	    // run when command let is found
            if(part[1].matches("(?i)\\b(let)\\b"))
	    {
		String line = simpleLine.replaceAll("(?i)^[0-9]+\\slet\\s", "");
		
		// check if operators are allowed
                if(checkOperator(line))
                    throw new ArithmeticException("Operator at line "+part[0]+" not allowed.");
                // check for correct arithmetic order
                if(checkArithmeticOrder(line))
//		    if(checkArrayDeclaration(line))
                    throw new ArithmeticException("Wrong arithmetic syntax.");
	    }
	    // check branching command
	    if(part[1].matches("(?i)\\b(goto|gosub)\\b"))
	    {
		if(part[1].matches("(?i)\\b(gosub)\\b"))
		    gosubCounter++;
		if(checkBranching(simpleLine.replaceFirst("(?i)^[0-9]+\\s(goto|gosub)\\s", "")))
		    throw new IllegalArgumentException("Must be a line number.");
	    }
	    // return counter
	    if(part[1].matches("(?i)\\b(return)\\b"))
		returnCounter++;
            // for loop
            if(part[1].matches("(?i)\\b(for)\\b"))
                if(checkForLoop(simpleLine.replaceFirst("(?i)^[0-9]+\\s","")))
                    throw new Exception("Wrong syntax at line " + part[0] + ".");
	    // validate input, can only be followed by one character variable
	    if(part[1].matches("(?i)\\b(input|print)\\b") && checkInput(simpleLine.replaceFirst("(?i)^[0-9]+\\s(input|print)\\s", "")))
		throw new IllegalArgumentException("Variable at line "+part[0]+" should be one lowercase letter.");
	    // validate if line syntax
	    if(part[1].matches("(?i)\\b(if)\\b") && checkIf(simpleLine.replaceFirst("^[0-9]+\\sif\\s", "")))
		throw new Exception("Wrong syntax at line "+part[0]);
        }
	
	if(returnCounter > gosubCounter)
	    throw new Exception("\'return\' count and \'gosub\' count do not match.");
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
    // chech if for loop has correct syntax
    private boolean checkForLoop(String line){
        return !line.matches("(?i)^(for\\s[a-z]\\s=\\s[0-9]+\\sto\\s[0-9]+)(\\sstep\\s[0-9]+)?$");
    }
    // check for correct arithmetic order
    private boolean checkArithmeticOrder(String line){
        // rules
        // Only one character per variable allowed, paranthesis allowed 
        // next to variable or digit, in between one whitespace e.g. y = i * (a - 3)
        // first charcter is a variable to be set, followed by symbol equals
        // followed by variable with one character or digit till end of the line
	// 
        return !line.matches("(?i)^(([a-z])|([a-z])\\s=\\s(\\()?([a-z]|[0-9]+)((\\s[\\-+*%\\/]\\s(\\(|\\))?([a-z]|[0-9]+)(\\(|\\))?)+)?)$");
    }
    
//    private boolean checkArrayDeclaration(String line){
//	return !line.matches("(?i)^([a-z])\\s[=]\\s([{][0-9]+([,]\\s[0-9]+)+[}])$");
//    }
    
    private boolean checkBranching(String line){
	return !line.matches("(?i)^([0-9]+){1}$");
    }
    // check if operator is valid
    private boolean checkOperator(String line){
	line = line.replaceAll("([\\w\\d\\s=()])", "");
	boolean result = (line.length() == 0) ? true : line.matches("(.*[+\\-*\\/%^])");
	// operators allowed: +, -, *, /, %, ^
        return !result;
    }
    
    // check if command is valid
    private boolean checkCommand(String line){
        return !line.matches("\\b(?i)(rem|input|let|fill|end|print|goto|if|gosub|return|for|next)\\b");
    }
    
    // check for correct input variable
    private boolean  checkInput(String line){
	return !line.matches("^(?i)([a-z])((,\\s[a-z])+)?");
    }
    
    // check for syntax errors
    private boolean checkIf(String line){
	return !line.matches("^(?i)([a-z]|[-?0-9]+)\\s(==|<=|>=|>|<|=)\\s([a-z]|[-?0-9]+)\\s\\bgoto\\b\\s[0-9]+$");
    }
}
