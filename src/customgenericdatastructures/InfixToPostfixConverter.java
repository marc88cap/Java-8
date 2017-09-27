/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customgenericdatastructures;

import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class InfixToPostfixConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Input arithmetic expression: %n> ");
        StringBuffer infix = new StringBuffer(sc.nextLine());
        StringBuffer postfix = toPostfix(infix);
        System.out.printf("INFIX: %s to POSTFIX: %s%n",infix,postfix);
        PostfixEvaluator pe = new PostfixEvaluator(postfix);
        System.out.printf("%n%s:%n%s%n","RESULT",pe.evaluate());
    }
    
    public static StringBuffer toPostfix(StringBuffer infix){
        StackCustom<String> stack = new StackCustom<>();
        StringBuffer postfix = new StringBuffer();
        stack.push("("); // push left parantheses onto the stack
        infix.append(")"); //append right paranthesis at end of infix
        while(!stack.isEmpty()){
            System.out.println(postfix);
            for(Character c : infix.toString().toCharArray()){
                System.out.print(c);
                if(Character.isDigit(c)){ // if digit, append to postfix
                    System.out.println(" is digit. Append to postfix. ");
                    postfix.append(c);
                }
                else if(c.equals('(')){ // if left parenthesis, push onto stack
                    System.out.println(" is left paranthesis. Push onto stack. ");
                    stack.push("(");
                }
                else if(isOperator(c.toString())) //if is operator
                {
                    postfix.append(" ");
                    System.out.print(" is operator. ");
                    // while precendece operators on stack are bigger or equal 
                    // to current operator pop() and append to postfix
                    while(isOperator(stack.peek()) 
                            && precedence(c.toString(), stack.peek())){
                        System.out.print("Pop("+stack.peek()+"). Append to postfix. ");
                        postfix.append(stack.pop()+" ");
                    }
                    System.out.println("Push onto stack. ");
                    stack.push(c.toString());
                }
                else if(c.equals(')'))
                {
                    System.out.print(" is right paranthesis. ");
                    while(!stack.peek().equals("(")){
                        System.out.print("Pop("+stack.peek()+"). Append to postfix. ");
                        postfix.append(" "+stack.pop());
                    }
                    System.out.println("Pop("+stack.peek()+"). Remove.  ");
                    stack.pop();
                }
            }
        }
        return postfix;
    }
    
    public static boolean isOperator(String op){
        if(op.matches("[\\+\\-\\*\\/\\^\\%]"))
            return true;
        return false;
    }
    
    public static boolean precedence(String op1, String op2){
        if(precedenceValue(op1) <= precedenceValue(op2))
            return true;
        return false;
   }
   
   private static int precedenceValue(String op){
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

class PostfixEvaluator{
    private StringBuffer postfix;
    
    public PostfixEvaluator(StringBuffer postfix){
        this.postfix = postfix;
    }
    
    public int evaluate(){
        postfix.append(')');
        
        StackCustom<Integer> stack = new StackCustom<>();
        for(Character c: postfix.toString().toCharArray()){
            if(c==')') break;
            if(Character.isDigit(c)){ // if is a digit 
                if(stack.isEmpty()){ // if its empty push new value
                    stack.push(c-'0');
                }
                else // if not empty, take value and concatenate
                    stack.push(Integer.valueOf(stack.pop().toString()+c.toString()));
            }
            else if (!c.equals(' ') && !Character.isDigit(c)){// if is an operator
                stack.pop(); // remove the pushed zero value
                int y = stack.pop();
                int x = stack.pop();
                stack.push(calculate(x,y,c));
            }
            else
                stack.push(0); // push a zero value - used for concatenation purposes
        }
        return stack.pop();
    }
    
    private int calculate(int x, int y, Character operator){
        switch(operator){
            case '+':
                return x+y;
            case '-':
                return x-y;
            case '*':
                return x*y;
            case '/':
                return x/y;
            case '%':
                return x%y;
            case '^':
                return (int)Math.pow(x,y);
            default:
                throw new IllegalArgumentException("Unknown operator: "+operator);
        }
    }
}

