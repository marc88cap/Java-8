/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.compiler;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Formatter;

import static customgenericdatastructures.InfixToPostfixConverter.toPostfix;
import customgenericdatastructures.StackCustom;

/**
 *
 * @author markoc
 */
public class Compiler {
    private EntryTable[] symbolTable = new EntryTable[100];
    private int[] flags = new int[100];
    private int frontLocation = 00;
    private int backLocation = 99;
    private int instructionCounter = 0;
    private int[] SML = new int[100];
    
    public String compile(String filepath) throws IOException{
	// START OF FIRST PASS
	StreamTokenizer st = new StreamTokenizer(new FileReader(filepath));
	st.eolIsSignificant(true); // treat end-of-lines as tokens
	int tokenCounter = 0;
	Arrays.fill(flags,-1);
	while (st.nextToken() != StreamTokenizer.TT_EOF) {
	    tokenCounter++;
	    // ignore rem statements
	    if(st.sval != null && st.sval.equals("rem"))
		while(st.nextToken() != StreamTokenizer.TT_EOL)
		{/* ignore rem */}

	    switch (st.ttype) {
		case StreamTokenizer.TT_EOL:
		    tokenCounter = 0;
		    break;

		case StreamTokenizer.TT_NUMBER:
		    if(tokenCounter == 1)
			pushEntry((int)st.nval,'L');
		    break;

		case StreamTokenizer.TT_WORD:
		    int symbol, location;
		    switch(st.sval){
			case "input":
			    st.nextToken(); // move to variable
			    symbol = (int)st.sval.charAt(0);
			    insertIntoSML(1000 + backLocation);
			    pushEntry(symbol,'V');
			    break;
			    
			case "print":
			    st.nextToken(); // move to variable
			    symbol = (int)st.sval.charAt(0);
			    location = getMemoryLocation(symbol,'V');
			    if(location >= 0)
			    {
				insertIntoSML(1100 + location);
			    }
			    break;
			    
			case "let":
			    st.nextToken();
			    int resultLocation = backLocation; // temporary store final location
			    pushEntry((int)st.sval.charAt(0), 'V');
			    
			    st.nextToken();
			    StringBuffer sb = new StringBuffer();
			    
			    while(st.nextToken() != StreamTokenizer.TT_EOL)
				switch(st.ttype){
				    case StreamTokenizer.TT_NUMBER:
					pushEntry((int)st.nval, 'C');
					sb.append(getMemoryLocation((int)st.nval, 'C'));
					break;
				    case StreamTokenizer.TT_WORD:
					sb.append(getMemoryLocation((int)st.sval.charAt(0), 'V'));
					break;
				    default:
					if(isOperator((char)st.ttype))
					    sb.append(" ").append((char)st.ttype).append(" ");
					else
					    sb.append((char)st.ttype);
				}
			    // load temporary location to accumulator
			    insertIntoSML(2000 + convertExpressionToSML(toPostfix(sb)));
			    // store temporary location to final variable / location
			    insertIntoSML(2100 + resultLocation);
			    break;
			    
			case "if":
			    StringBuffer comparisonSymbol = new StringBuffer();
			    boolean first = true;
			    
			    while(st.nextToken() != StreamTokenizer.TT_EOL && st.sval == null || !isCommand(st.sval)){
				switch(st.ttype){
				    case StreamTokenizer.TT_NUMBER:
					pushEntry((int)st.nval,'C');
					first = outputComparisonOperaterCodes(first, getMemoryLocation((int)st.nval, 'C'));
					break;
				    case StreamTokenizer.TT_WORD:
					symbol = (int)st.sval.charAt(0);
					first = outputComparisonOperaterCodes(first, getMemoryLocation(symbol,'V'));
					break;
				    default:
					comparisonSymbol.append(String.valueOf((char)st.ttype));
				}
			    }
			    // expected goto command location number
			    st.nextToken();
			    // get branching location
			    outputBranchingCodes(comparisonSymbol, (int)st.nval);
			    break;
			    
			case "goto":
			    st.nextToken();
			    symbol = (int)st.nval;
			    location = getMemoryLocation(symbol, 'L');
			    if(location >= 0)
				insertIntoSML(4000 + location);
			    else
			    {
				flags[instructionCounter] = symbol;
				insertIntoSML(4000);
			    }
			    break;
			    
			case "end":
			    insertIntoSML(4300);
			    break;
			default:
			    break;
		    }
		    break;	
		default:
		    break;
	    }
	}
	// END OF FIRST PASS
	// START OF SECOND PASS
	for(int f=0; f<flags.length; f++){
	    if(flags[f]>=0){
		int location = getMemoryLocation(flags[f], 'L');
		if(location == -1)
		    throw new IllegalArgumentException("Line "+flags[f]+" is missing.");
		SML[f] = SML[f]+location;
	    }
	}
	try (Formatter output = new Formatter("src/compilersimulation/simpletron/SML/"+filepath.replaceFirst("^.+?(?=\\w+[.]?\\w+$)", "").replaceFirst("[.]\\w+$", ".sml"))) {
	    for (int sml : SML) {
		if(sml==0) break;
		printToFile(output, sml);
	    }
	    output.close();
	}
	// END OF SECOND PASS
	
	return "src/compilersimulation/simpletron/SML/"+filepath.replaceFirst("^.+?(?=\\w+[.]?\\w+$)", "").replaceFirst("[.]\\w+$", ".sml");
    }
    
    public void print(){
	for(EntryTable et : symbolTable){
	    if(et == null)
		return;
	    System.out.printf("%-2s %-1c %-2d%n",et.getSymbol(),et.getType(),et.getLocation());
	}

    }
    
    private boolean isCommand(String command){
	return command.equals("input") || command.equals("print") || command.equals("goto") || command.equals("end") || command.equals("let") || command.equals("if");
    }
    
    private int getMemoryLocation(int symbol, char type){
	for(EntryTable et : symbolTable){
	    if(et == null)
		return -1;
	    else if(et.symbol == symbol && et.type == type)
		return et.location;
	}
	return -1;
    }
    
    private void pushEntry(int symbol, char type){
	int searchLocation = getMemoryLocation(symbol, type);
	if(searchLocation == -1)
	{
	    symbolTable[frontLocation++] = new EntryTable(symbol,type,(type == 'L') ? instructionCounter : backLocation--);
	}
    }
    
    private boolean outputComparisonOperaterCodes(boolean first, int memoryLocation){
	if(first) // if first element: load
	{
	    insertIntoSML(2000+memoryLocation);
	    first = !first;
	}
	else // if second element: subtract
	    insertIntoSML(3100+memoryLocation);
	
	return first;
    }
    
    private void outputBranchingCodes(StringBuffer comparisonSymbol, int value){
	int branchingLocation = getMemoryLocation(value, 'L');
	
	if(branchingLocation == -1)
	{
	    flags[instructionCounter] = value;
	    branchingLocation = 0;
	}
	
	for(int i=0;i<comparisonSymbol.length();i++){
	    switch(comparisonSymbol.toString().charAt(i)){
		case '<':
		    insertIntoSML(4100 + branchingLocation);
		    break;
		case '>':
		    insertIntoSML(4000 + branchingLocation);
		    break;
		case '=':
		    insertIntoSML(4200 + branchingLocation);
		    return;
	    }
	}
    }
    
    private void printToFile(Formatter output, int code){
	output.format("%s%d%n", (code > 0) ? "+":"",code);
	
	
    }
    
    private void insertIntoSML(int code){
	if(instructionCounter == SML.length)
	    throw new OutOfMemoryException();
	SML[instructionCounter] = code;
	instructionCounter++;
    }
    
    private boolean isOperator(char operator){
	return operator == '+' || operator == '-' || operator == '*' || operator == '/';
    }
    
    private int operatorCode(String operator){
	switch(operator){
	    case "+":
		return 3000;
	    case "-":
		return 3100;
	    case "/":
		return 3200;
	    case "*":
		return 3300;
	    default:
		throw new ArithmeticException("Operator not allowed.");
	}
    }
    
    private int convertExpressionToSML(StringBuffer postfix){
	StackCustom<Integer> stack = new StackCustom<>();
	int tempLocation = backLocation--;
	System.out.println(postfix.toString());
	String[] tp = postfix.toString().split("\\s");

	for(String token : tp){
	    if(!isOperator(token.charAt(0)))
		stack.push(Integer.parseInt(token));
	    else
	    {
		int y = stack.pop();
		int x = stack.pop();
		int oCode = operatorCode(token);
		insertIntoSML(2000 + x); // load first integer
		insertIntoSML(oCode + y); // use second integer and produce result

		insertIntoSML(2100 + tempLocation); // store new value to temp location/or result
		
		stack.push(tempLocation); // push temporary location onto stack
	    }
	}
	return stack.pop(); // return temp location
    }

}

