/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.compiler;

import customgenericdatastructures.StackCustom;

/**
 *
 * @author markoc
 */
public class InfixToPostfixConverter{
    
    public StringBuffer toPostfix(StringBuffer infix){
        StackCustom<String> stack = new StackCustom<>();
        StringBuffer postfix = new StringBuffer();
        stack.push("("); // push left parantheses onto the stack
        infix.append(")"); //append right paranthesis at end of infix
        while(!stack.isEmpty()){
            for(Character c : infix.toString().toCharArray()){
		if(Character.isAlphabetic(c)){
		    postfix.append(c);
		}
		if(Character.isDigit(c)){ // if digit, append to postfix
//                    System.out.println(" is digit. Append to postfix. ");
                    postfix.append(c);
                }
                else if(c.equals('(')){ // if left parenthesis, push onto stack
//                    System.out.println(" is left paranthesis. Push onto stack. ");
                    stack.push("(");
                }
                else if(isOperator(c.toString())) //if is operator
                {
                    postfix.append(" ");
//                    System.out.print(" is operator. ");
                    // while precendece operators on stack are bigger or equal 
                    // to current operator pop() and append to postfix
                    while(isOperator(stack.peek()) 
                            && precedence(c.toString(), stack.peek())){
//                        System.out.print("Pop("+stack.peek()+"). Append to postfix. ");
                        postfix.append(stack.pop()+" ");
                    }
//                    System.out.println("Push onto stack. ");
                    stack.push(c.toString());
                }
                else if(c.equals(')'))
                {
//                    System.out.print(" is right paranthesis. ");
                    while(!stack.peek().equals("(")){
//                        System.out.print("Pop("+stack.peek()+"). Append to postfix. ");
                        postfix.append(" "+stack.pop());
                    }
//                    System.out.println("Pop("+stack.peek()+"). Remove.  ");
                    stack.pop();
                }
            }
        }
        return postfix;
    }
    
    public boolean isOperator(String op){
        if(op.matches("[\\+\\-\\*\\/\\^\\%]"))
            return true;
        return false;
    }
    
    public boolean precedence(String op1, String op2){
        if(precedenceValue(op1) <= precedenceValue(op2))
            return true;
        return false;
   }
   
   private int precedenceValue(String op){
       switch(op){
            case "*":
            case "/":
            case "%":
            case "^":
                return 12;
            case "+":
            case "-":
                return 11;
            default: 
                throw new IllegalArgumentException("Operator not recognized: "+op);
        }
   }
}


