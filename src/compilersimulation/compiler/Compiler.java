/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.compiler;

import compilersimulation.exceptions.OutOfMemoryException;
import compilersimulation.simpletronhardware.Disk;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;

import compilersimulation.compiler.InfixToPostfixConverter;
import customgenericdatastructures.StackCustom;
import java.io.FileNotFoundException;

/**
 *
 * @author markoc
 */
public class Compiler {
    private EntryTable[] symbolTable = new EntryTable[100]; // table of symbols (variables, constants, line numbers, etc) and their locations in memory
    private int[] lineFlags = new int[100];
    private int[] variableFlags = new int[100];
    private int[] SML = new int[100]; // to store SML instructions before writing to file
    private int[] goSub = new int[100]; // to store goSub (method) instructions before writing to file
    private int frontLocation = 00; // in memory array front location counter
    private int backLocation = 99; // in memory array back location counter
    private int instructionCounter = 0; // counter of instructions
    private String filePathSML; // sml file path to write in
    private int forFromLocation, forToLocation, forStepLocation;
    
    public EntryTable[] compile(String filePath) throws IOException{
	this.filePathSML = "src/compilersimulation/simpletronhardware/SML/"
		+filePath.replaceFirst("^.+?(?=\\w+[.]?\\w+$)", "").replaceFirst("[.]\\w+$", ".sml");
	
	firstPass(filePath);
	secondPass(filePath);
	// return symbolTable: constants need to be extracted
	return symbolTable;
    }
    // start with first pass: make an instruction list, but some instructions
    // may be incomplete
    private void firstPass(String filePath) throws FileNotFoundException, IOException{
	StreamTokenizer st = new StreamTokenizer(new FileReader(filePath));
	InfixToPostfixConverter itpc = new InfixToPostfixConverter();
	int forLoopLine = 0;
	boolean gosub = false;
	st.eolIsSignificant(true); // treat end-of-lines as tokens
	st.ordinaryChar('/'); // set slash (/) as ordinary character
	st.lowerCaseMode(true); // everything is lowercase
	Arrays.fill(lineFlags,-1);
	Arrays.fill(variableFlags,-1);
	// read file until the end
	while (st.nextToken() != StreamTokenizer.TT_EOF) {
	    // ignore rem statements
	    if(st.sval != null && st.sval.equals("rem"))
		while(st.nextToken() != StreamTokenizer.TT_EOL)
		{/* do nothing, skip rem lines */}
	    // any other line than rem is processed
	    switch (st.ttype) {
		// if end of line just break the switch
		case StreamTokenizer.TT_EOL:
		    break;
		// push line number into symbolTable
		case StreamTokenizer.TT_NUMBER:
			insertIntoSymbolTable((int)st.nval,'L');
		    break;
		// if its a word 
		case StreamTokenizer.TT_WORD:
		    int symbol, location;
		    // the compiler ignores/treats uppercase letters as lowercase
		    switch(st.sval.toLowerCase()){
			// on input create input instruction directly
			// and store it to the SML array
			case "input":
			    while(st.nextToken() != StreamTokenizer.TT_EOL)
			    {
				if(st.sval != null)
				{
				symbol = (int)st.sval.charAt(0);
				insertIntoSML(1000 + backLocation);

				insertIntoSymbolTable(symbol,'V');
				}
			    }
			    break;
			// on print create instruction directy and store to SML array
			case "print":
			     while(st.nextToken() != StreamTokenizer.TT_EOL)
			    {
				if(st.sval != null)
				{
				    symbol = (int)st.sval.charAt(0);
				    location = getMemoryLocation(symbol,'V');
				    if(location >= 0)
				    {
					insertIntoSML(1100 + location);
				    }
				    // if the variable was not yet stored to the symbolTable
				    // mark it in variableFlags array for the second pass
				    // and store blank (00) instruction to SML
				    else
				    {
					variableFlags[instructionCounter] = symbol;
					insertIntoSML(1100);
				    }
				}
			    }
			    break;
			case "let":
			    // move to next token which should be a variable
			    st.nextToken();
			    // store the variabl into v
			    char v = st.sval.charAt(0);
			    // push variable into the symbolTable
			    insertIntoSymbolTable((int)v, 'V');
			    // get the memory location of the variable from the symbolTable
			    location = getMemoryLocation((int)st.sval.charAt(0), 'V'); // temporary store final location
			    // if next token is not end-of-line continue reading
			    // its probably a equals symbol (=)
			    if(st.nextToken() != StreamTokenizer.TT_EOL)
			    {
//				// if next token is a left square bracket
//				// array declaration starts
//				if((char)st.ttype == '['){
//				    // move to next token: expected number
//				    st.nextToken();
//				    // store the number
//				    int num = (int)st.nval;
//				    
//				    for(int i=0;i<num;i++)
//					insertIntoSymbolTable((int)v, 'V', true);
//				    
//				    while(st.nextToken() != StreamTokenizer.TT_EOL);
//				    break;
//				}
//				// array declaration ends
				// create stringbuffer to build a equation
				StringBuffer sb = new StringBuffer();
				// continue reading until end of line
				while(st.nextToken() != StreamTokenizer.TT_EOL){
				    // append each element until end of file to
				    // to the StringBuffer
				    switch(st.ttype)
				    {
					case StreamTokenizer.TT_NUMBER:
					    insertIntoSymbolTable((int)st.nval, 'C');
					    sb.append(getMemoryLocation((int)st.nval, 'C'));
					    break;
					case StreamTokenizer.TT_WORD:
					    sb.append(getMemoryLocation((int)st.sval.charAt(0), 'V'));
					    break;
					default:
					    // append white-space before and after operator
					    if(isOperator((char)st.ttype))
						sb.append(" ").append((char)st.ttype).append(" ");
					    // if is not an operator its probably a parentheses  
					    else
						sb.append((char)st.ttype);
				    }

				}
				// if only one value(one memory location) should be stored to the
				// variable(another memory location)
				if(sb.length() == 2)
				{
				    // load the memory to the accumulator
				    insertIntoSML(2000 + Integer.parseInt(sb.toString()));
				    // store the memory to the variable decleration location
				    insertIntoSML(2100 + location);
				}
				else
				    // load temporary location to accumulator
				    convertExpressionToSML(itpc.toPostfix(sb), location);
			    }
			    // if there is nothing after he variable: 10 let a
			    // just store the accumulator to the variable declaration location
			    // accumulator should be 0
			    else {
				insertIntoSML(2100 + location);
			    }
			    break;
			    
			case "if":
			    // create a stringbuffer for comparison symbol construction
			    StringBuffer comparisonSymbol = new StringBuffer();
			    // is used to indicate if first or second number is been read
			    boolean first = true;
			    // if next token is not end-of-line, is not null and is not a command
			    // in this case goto command
			    while(st.nextToken() != StreamTokenizer.TT_EOL 
				    && st.sval == null 
				    || !isCommand(st.sval)){
				// store constants to the symbolTable
				// output comparison instruction of the two memory fields
				// comparison operands is stored to the stringbuffer
				switch(st.ttype){
				    case StreamTokenizer.TT_NUMBER:
					insertIntoSymbolTable((int)st.nval,'C');
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
			    // output branching instructions
			    outputBranchingCodes(comparisonSymbol, (int)st.nval);
			    break;
			case "gosub":
			    // after the command line number is expected
			    st.nextToken();
			    // store the line number
			    symbol = (int)st.nval;
			    // since methods need to be written after the 'end' command
			    // the line was not yet found:
			    // store the line to the lineFlags array
			    lineFlags[instructionCounter] = symbol;
			    // insert empty command to SML array
			    insertIntoSML(4900);
			    gosub = true;
			    break;
			case "return":
			    // to indicate the end of method push empty command to array
			    insertIntoSML(4900);
			    break;
			case "for":
			    // syntax:
			    // for x = 2 to 10 step 2
			    // move to next token: variable
			    st.nextToken();
			    // store variable
			    insertIntoSymbolTable((int)st.sval.charAt(0), 'V');
			    forFromLocation = getMemoryLocation((int)st.sval.charAt(0), 'V');
			    st.nextToken(); // equals symbol
			    st.nextToken(); // constant expected
			    insertIntoSymbolTable((int)st.nval, 'C');
			    int constantLocation = getMemoryLocation((int)st.nval, 'C');
			    insertIntoSML(2000+constantLocation);
			    insertIntoSML(2100+forFromLocation);
			    forLoopLine = instructionCounter;
			    st.nextToken(); // to command
			    st.nextToken(); // constant expected
			    insertIntoSymbolTable((int)st.nval, 'C');
			    forToLocation = getMemoryLocation((int)st.nval, 'C');
			    // if command step is found
			    int stepNumber;
			    if(st.nextToken() != StreamTokenizer.TT_EOL)
			    {
				st.nextToken(); // constant expected
				stepNumber = (int)st.nval;
			    }
			    // if step is not found
			    else
			    {
				stepNumber = 1;
			    }
			    insertIntoSymbolTable(stepNumber, 'C');
			    forStepLocation = getMemoryLocation(stepNumber, 'C');
				
			    break;
			case "next":
			    insertIntoSML(2000+forFromLocation);
			    insertIntoSML(3000+forStepLocation);
			    insertIntoSML(2100+forFromLocation);
			    insertIntoSML(3100+forToLocation);
			    if(gosub)
			    {
				insertIntoSML(-4100-forLoopLine);
				insertIntoSML(-4200-forLoopLine);
			    }
			    else 
			    {
				insertIntoSML(4100+forLoopLine);
				insertIntoSML(4200+forLoopLine);
			    }
			    
			    break;
			case "goto":
			    // after goto command a line number is expected
			    st.nextToken();
			    // store the line number
			    symbol = (int)st.nval;
			    // get the memory location of the line number from the symbolTable
			    location = getMemoryLocation(symbol, 'L');
			    // if the location was found insert full instruction into the SML table
			    if(location >= 0)
				insertIntoSML(4000 + location);
			    // else mark it in the lineFlags array and insert empty instruction into the SML table
			    else
			    {
				// insert a value of the not found line number
				// into the lineFlags arrays in the location
				// of the instruction line number
				lineFlags[instructionCounter] = symbol;
				insertIntoSML(4000);
			    }
			    break;
			    
			case "end":
			    // insert directly
			    insertIntoSML(9900);
			    break;
			default:
			    // do nothing
			    break;
		    } // end of reading the command loop
		    break;	
		default:
		    // do nothing
		    break;
	    } // end of reading the line loop
	} // end of reading the file loop
    } // end of first pass
    
    // start with second pass: complete the incomplete instructions
    public void secondPass(String filePath){
	// create a new disk, where the file commands are located
	Disk disk = new Disk();
	// is used to move to the next field and indicate the start of the method in the goSub array
	int goSubCount = 0;
	// since all arrays are the same length, or should be
	// any array length can be used as maximum value
	// loop thru arrays and complete each marked instruction
	for(int f=0; f<lineFlags.length; f++){
	    // when a positive value (line number) is found
	    if(lineFlags[f]>=0){
		// get the memory location of that line from symbolTable
		int location = getMemoryLocation(lineFlags[f], 'L');
		// if the location was not found throw an exception
		if(location == -1){
		    throw new IllegalArgumentException("Line "+lineFlags[f]+" is missing.");
		}
		// the location was found and it holds an incomplete instruction
		// for gosub/method
		if(SML[f] == 4900)
		{
		    // is used to move to the next field
		    int i = 0;
		    // store the goSub location number of the starting point for the method
		    SML[f] = SML[f] + goSubCount;
		    // every line until a return command is stored to goSub array
		    // and goSubCount and i are incremented on each input
		    // goSubCount 
		    while(SML[location+i] / 100 != 49)
		    {
			goSub[goSubCount++] = SML[location+i];
			i++;
		    }
		    goSub[goSubCount++] = SML[location+i];
		}
		// if its everything else but a gosub command complete the comand directly
		else
			SML[f] = SML[f] + location;
		
		    
	    }
	    // complete the incomplete variable instructions
	    if(variableFlags[f]>=0){
		// get the memory location of the variable from the symbolTable
		int location = getMemoryLocation(variableFlags[f], 'V');
		// if location is not found throw exception
		if(location == -1){
		    throw new IllegalArgumentException("Variable "+(char)variableFlags[f]+" is missing.");
		}
		// if location was found, complete the instruction and store it to the SML array
		SML[f] = SML[f]+location;
	    }
	} // end of instruction completitions except for goSub instructions
	// use the disk object to open a file
	disk.openFile(this.filePathSML);
	
	
	// for each sml instruction in the SML array
	for (int l=0;l<SML.length;l++) {
	    int sml = SML[l];
	    
	    // check if its a goSub instruction
	    if(sml / 100 == 49)
	    {
		// goSub instruction counter; needed at fixing location
		int i = 0;
		// get the starting field number for goSub array
		int num = sml % 100;
		// while goSub field value is valid
		// and goSub field value doesnt contain the return instruction
		while(goSub[num+i] > 0 && goSub[num+i] != 4900)
		{
		    // insert the instruction into the file
		    disk.printToFile((goSub[num+i] > 0) ? "+"+goSub[num+i] : String.valueOf(goSub[num+i]));
		    i++; // increment to next field
		}
		
		// fix symbol table location attribute
		for(int st=0;st<symbolTable.length;st++)
		{
		    if(symbolTable[st] == null) break;
		    // only check for line numbers and locations that are bigger as sml line numbers
		    // in other words: locations that come after the go sub instruction
		    if(symbolTable[st].type == 'L' && symbolTable[st].location > l)
		    {
			symbolTable[st].location += i-1; // store new locations
		    }
		}
		// correct the location in SML array
		fixSMLBranchingLocations(l+1, i-1);
	    }
	    // for every other instruction, insert directly
	    else
		disk.printToFile((sml > 0) ? "+"+sml : String.valueOf(sml));
	    // when the HALT instruction is found. insertion is completed
	    if(sml == 9900) break;
	}
	// close the file
	disk.closeFile();
    }
    // print the symbolTable contents to the screen
    public void printSymbolTable(){
	for(EntryTable et : symbolTable){
	    if(et == null)
		return;
	    System.out.printf("%-2s %-1c %-2d%n",et.getSymbol(),et.getType(),et.getLocation());
	}

    }
    // check if its a command
    private boolean isCommand(String command){
	return command.equals("input") 
		|| command.equals("print") 
		|| command.equals("goto") 
		|| command.equals("end") 
		|| command.equals("let") 
		|| command.equals("gosub") 
		|| command.equals("for") 
		|| command.equals("to") 
		|| command.equals("step") 
		|| command.equals("return") 
		|| command.equals("if");
    }
    // get a memory location from the symbolTable
    private int getMemoryLocation(int symbol, char type){
	for(EntryTable et : symbolTable){
	    if(et == null)
		return -1;
	    else if(et.symbol == symbol && et.type == type)
		return et.location;
	}
	return -1;
    }
    // insert a new EntryTable object into the symbolTable array
    private void insertIntoSymbolTable(int symbol, char type){
	// search the table if the value already exists
	int searchLocation = getMemoryLocation(symbol, type);
	// if it does not create new entry
	if(searchLocation == -1)
	{
	    symbolTable[frontLocation++] = new EntryTable(symbol,type,(type == 'L') ? instructionCounter : backLocation--);
	}
    }
    // used for IF/GOTO lines to insert SML commands
    private boolean outputComparisonOperaterCodes(boolean first, int memoryLocation){
	if(first) // if first element: load
	{
	    // check if the location value is already loaded into the accumulator
	    if(!isRedundantInstruction(memoryLocation))
		// if its not create a corresponding instruction
		insertIntoSML(2000+memoryLocation);
	    // switch first to false
	    first = !first;
	}
	else // if second element: subtract
	    insertIntoSML(3100+memoryLocation);
	
	return first;
    }
    // used for IF/GOTO
    private void outputBranchingCodes(StringBuffer comparisonSymbol, int value){
	// get the branching location
	int branchingLocation = getMemoryLocation(value, 'L');
	// comparison symbols are of max length 2
	// loop thru the comparator and intput instructions into the SML array
	for(int i=0;i<comparisonSymbol.length();i++){
	    // if branching location was not yet discovered, mark it in lineFlags array
	    if(branchingLocation == -1)
	    {
		lineFlags[instructionCounter] = value;
		branchingLocation = 0;
	    }
	    
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
    // insert code into the SML array and increment instructionCounter
    private void insertIntoSML(int code){
	if(instructionCounter == SML.length)
	    throw new OutOfMemoryException();
	SML[instructionCounter] = code;
	instructionCounter++;
    }
    // return true if the character is an operator
    private boolean isOperator(char operator){
	return operator == '+' || operator == '-' || operator == '/' 
		|| operator == '*' || operator == '%' || operator == '^';
    }
    // return operator codes
    private int getOperatorCode(String operator){
	switch(operator){
	    case "+":
		return 3000;
	    case "-":
		return 3100;
	    case "/":
		return 3200;
	    case "*":
		return 3300;
	    case "%":
		return 3400;
	    case "^":
		return 3500;
	    default:
		throw new ArithmeticException("Operator not allowed.");
	}
    }
    // convert postfix expression to SML instructions with help of a stack
    private void convertExpressionToSML(StringBuffer postfix, int resultLocation){
	StackCustom<Integer> stack = new StackCustom<>();
	// get a temporary location which is a next location from backLocation
	int tempLocation = backLocation;
	// break the postfix into parts
	String[] tp = postfix.toString().split("\\s");
	// for each part
	for(int i=0; i<tp.length;i++){
	    String token = tp[i];
	    // check if its not an operator, which makes it a number and push it onto the stack
	    if(!isOperator(token.charAt(0)))
		stack.push(Integer.parseInt(token));
	    // if its an operator
	    else
	    {
		// get the previous two numbers from the stack
		int y = stack.pop();
		int x = stack.pop();
		// get the operator code
		int oCode = getOperatorCode(token);
		// if the first location is not already loaded
		if(!isRedundantInstruction(x))
		    insertIntoSML(2000 + x); // insert a load integer instruction
		// operate on second integer with the accumulator integer with corresponding operator
		insertIntoSML(oCode + y); // insert the second integer instruction with corresponding operation
		// if its not a last run
		if(i<tp.length-1) // store new value to temp location until second last run
		    insertIntoSML(2100 + tempLocation); // store the value to temporary location
		
		stack.push(tempLocation); // push temporary location onto stack
	    }
	}
	// temporary location is forgoten and the results is stored
	// to final location/variable
	insertIntoSML(2100 + resultLocation);
    }
    
    public String getFilePathSML(){
	return this.filePathSML;
    }
    // check if the memoryLocation is already loaded into the accumulator 
    private boolean isRedundantInstruction(int memoryLocation){
	return !(memoryLocation != (SML[instructionCounter-1] % 100) ||  (SML[instructionCounter-1] - memoryLocation) == 1000);
    }
    // fixes location after inserting the gosub method into SML array
    public void fixSMLBranchingLocations(int startingLine, int num){
	boolean gosub = false;
	for(int s=startingLine; s<SML.length && SML[s] != 0;s++){
	    if(SML[s] / 100 == 49) gosub = true;
	    if(SML[s] / 1000 == -4)
	    {
		if(gosub)
		    SML[s] = SML[s] - num;
		else
		    SML[s] = Math.abs(SML[s] - num);
	    }
	}
    }
}
