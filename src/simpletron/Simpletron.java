/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpletron;

/**
 *
 * @author marko.ch
 * 
 */
import java.util.Scanner;
public class Simpletron{
    // Input/output oprations:
    final int READ = 10;
    final int WRITE = 11;
    final int NEWLINE = 12;
    // Load/store operations:
    final int LOAD = 20;
    final int STORE = 21;
    // Arithmetic operations:
    final int ADD = 30;
    final int SUBTRACT = 31;
    final int DIVIDE = 32;
    final int MULTIPLY = 33;
    final int REMINDER = 34;
    final int EXPONENTATION = 35;
    // Transfere-of-control operations;
    final int BRANCH = 40;
    final int BRANCHNEG = 41;
    final int BRANCHZERO = 42;
    final int HALT = 43;
    // Sentinel value
    final int SENTINEL = -99999;
    
    //variables
    double accumulator = 0; //to save information
    double[] memory = new double[1000]; //to insert instructions
    int instructionCounter = 0;
    int operationCode = 0; //save operation code
    int operand = 0; //save memory location
    double instructionRegister = 0; //temp save the word (i.e. +0000)
    
    //messages
    String welcomeMessage = "*** Welcome to Simpletron! ***";
    String instructionMsgFstLn =  "*** Please enter your program one instruction      ***";
    String instructionMsgSndLn =  "*** (or data word) at a time. I will display       ***";
    String instructionMsgTrdLn =  "*** (the location number and a question mark (?).  ***";
    String instructionMsgFthLn =  "*** You then type the word for that location.      ***";
    String instructionMsgFithLn = "*** Type -99999 to stop entering your program.     ***";
    String loadingCompleted = "*** Program loading completed ***";
    String executionBegins = "*** Program execution begins  ***";
    String executionTerminated = "*** Simpletron execution terminated ***";
    //error messages
    String errorInputNotInRange = "!!! Input should be between -9999 and 9999 inclusive !!!";
    String errorOverflowException = "Result got overflown";
    String errorAbnormalTermination = "*** Simpletron execution abnormally terminated ***";
    String errorInvalidInstruction = "!!! Instruction number is not valid !!!";
    String errorDivisionByZero = "!!! Division by zero !!!";
    
    public void Simulator(){
        Scanner sc = new Scanner(System.in);
        
        int location = 0;
        int input = 0;
        
        System.out.printf("%s%n%s%n%s%n%s%n%s%n",instructionMsgFstLn,
                instructionMsgSndLn,instructionMsgTrdLn,
                instructionMsgFthLn,instructionMsgFithLn);
        
        do{ 
            System.out.printf("%02d ? ", location);
            input = sc.nextInt();
            if(input==SENTINEL)break;
            if(errorInput(input)){
                System.out.printf("%s%n", errorInputNotInRange);
            }else{
                memory[location] = input;
                location++;
            }
            
        }while(true);
        
        System.out.printf("%s%n%s%n",loadingCompleted,executionBegins);
        
        adding:
        for(instructionCounter=0;
                instructionCounter<location;
                instructionCounter++){
            instructionRegister = memory[instructionCounter];
            operationCode = getOperationCode((int)instructionRegister);
            operand = getOperand((int)instructionRegister);
            try{
                switch(operationCode){
                    case READ:
                        read();
                        break;
                    case WRITE:
                        write();
                        break;
                    case NEWLINE:
                        newline();
                        break;
                    case LOAD:
                        load();
                        break;
                    case STORE:
                        store();
                        break;
                    case ADD:
                        add();
                        break;
                    case SUBTRACT:
                        subtract();
                        break;
                    case DIVIDE:
                        divide(instructionCounter);
                        break;
                    case MULTIPLY:
                        multiply();
                        break;
                    case REMINDER:
                        reminder();
                        break;
                    case EXPONENTATION:
                        exponentation();
                        break;
                    case BRANCH:
                        branch();
                        break;
                    case BRANCHNEG:
                        instructionCounter = branchNeg(instructionCounter);
                        break;
                    case BRANCHZERO:
                        instructionCounter = branchZero(instructionCounter);
                    case HALT: //terminate program
                        dump();
                        halt();
                        break adding;
                    default:
                        System.out.print(errorInvalidInstruction+"\n"+errorAbnormalTermination);
                        dump();
                        break adding;
                }
            }catch(Exception e){
                System.out.println(e);
                dump();
                break adding;
            }
        }
    }
    
    private void dump(){
        System.out.printf("%n%s:%n"
                + "%s%15s%n" //accumulator
                + "%s%8s%n" //instructionCounter
                + "%s%7s%n" //instructionRegister
                + "%s%13s%n" //operationCode
                + "%s%19s%n%n",//operand
                "REGISTER", 
                "accumulator", ((accumulator<0)?String.format("%05.0f",accumulator):"+"+String.format("%04.0f",accumulator)),
                "instructionCounter", String.format("%02d",instructionCounter),
                "instructionRegister", ((instructionRegister<0)?String.format("%05.0f",instructionRegister):"+"+String.format("%04.0f",instructionRegister)),
                "operationCode", String.format("%02d",operationCode),
                "operand", String.format("%02d",operand));
        System.out.printf("%s:%n%3s","MEMORY","");
        for(int i=0;i<10;i++){
            System.out.printf("%6d",i);
        }
        System.out.println();
        for(int r=0;r<memory.length/10;r++){
            System.out.printf("%3d",r*10);
            for(int c=0;c<10;c++){
                instructionRegister = memory[(r*10)+c];
                System.out.printf("%6s",((instructionRegister<0)?String.format("%05.0f",instructionRegister):"+"+String.format("%04.0f",instructionRegister)));
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private void read(){
        Scanner sc = new Scanner(System.in);
        int input = 0;
        do{
            System.out.printf("%s: ", "input");
            input = sc.nextInt();
            if(errorInput(input)){
                System.out.printf("%s%n",errorInputNotInRange);
            }
            else{
                memory[operand] = input;
            }
        }while(errorInput(input));
    }
    
    private void write(){
        System.out.printf("%.2f%n",memory[operand]);
    }
    
    private void newline(){
        System.out.printf("%n");
    }
       
    private void load(){
        accumulator = memory[operand];
    }
    
    private void store(){
        memory[operand] = accumulator;
    }
    
    private void add() throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], ADD))
        accumulator += memory[operand];
    }
    
    private void subtract() throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], SUBTRACT))
        accumulator -= memory[operand];
    }
    
    private void divide(double number) throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], DIVIDE))
        if(memory[operand]==0) throw new Exception(errorDivisionByZero+"\n"+errorAbnormalTermination);
        accumulator /= number;

    }
    
    private void multiply() throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], MULTIPLY))
        accumulator *= memory[operand];
    }
    
    private void reminder() throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], REMINDER))
        accumulator %= memory[operand];
    }
    
    private void exponentation() throws Exception{
        if(accumulatorOverflow(accumulator, memory[operand], EXPONENTATION))
            accumulator = Math.pow(accumulator,memory[operand]);
    }
    
    private void branch(){
        if(accumulator > memory[operand]){
            accumulator = memory[operand];
        }
    }
    
    private int branchNeg(int instructionCounter){
        if(accumulator<0){
            return operand-1;
        }
        return instructionCounter;
    }
    
    private int branchZero(int instructionCounter){
        if(accumulator==0){
            return operand;
        }
        return instructionCounter;
    }
    
    private void halt(){
        System.out.printf("%s%n",executionTerminated);
    }
    
    private int getOperationCode(int instruction){
        return instruction / 100;
    }
    
    private int getOperand(int instruction){
        return instruction % 100;
    }
    //if error return true
    private boolean errorInput(int input){
        if(input<(-9999) || input>9999){return true;}
        return false;
    }
    //check if accumulator would get overflown
    private boolean accumulatorOverflow(double a, double b, int op) 
    throws Exception
    {
        double result;
        
        switch(op){
            case ADD:
                result = a+b;
                break;
            case SUBTRACT:
                result = a-b;
                break;
            case DIVIDE:
                result = a/b;
                break;
            case MULTIPLY:
                result = a*b;
                break;
            case REMINDER:
                result = a%b;
                break;
            case EXPONENTATION:
                result = Math.pow(a,b);
            default:
                return true;
        }
        if(errorInput((int)result)) 
        {
            throw new Exception(errorOverflowException+"\n"+errorAbnormalTermination);
        }
        return true;
    }
}
