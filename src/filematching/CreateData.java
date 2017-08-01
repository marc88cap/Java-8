/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematching;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class CreateData {
    private static Scanner sc = new Scanner(System.in);
    private static Formatter oldMaster;
    private static Formatter inTransaction;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        
        
        addAccounts();
        addTransactions();
        
        System.out.printf("%n%s%n", "Files oldmast.txt and intrans.txt have been generated.");
    }
    
    private static void addAccounts(){
        
        try{
            oldMaster = new Formatter("src/filematching/oldmast.txt"); //Create/Open a new/existing file
        }
        catch(SecurityException e){
            System.err.println("Write permission denied.");
            System.exit(1);
        }
        catch(FileNotFoundException e){
            System.err.println("File does not exist.");
            System.exit(1);
        }
        
        System.out.printf("%s%n%s%n%s%n%s%n> ",
        "ACCOUNTS",
        "Enter account number, first name, last name and balance as example below:",
        "100 John Doe 99.99",
        "Enter dot (.) to end input.");
        
        while(sc.hasNext()){ // while input is entered continue looping
            try{
                oldMaster.format("%d %s %s %.2f%n", sc.nextInt(), sc.next(), 
                        sc.next(), sc.nextDouble());
            }
            /* if dot (.) is entered, NSE Exception will be called
            *  because we need an Integer on the first position and we get to 
            *  break the loop without exiting/terminating the program
            */
            catch(NoSuchElementException e){
                if(sc.next().equals(".")) break; 
                System.err.println("Invalid input. Please try again.");
                sc.nextLine();
            }

            System.out.print("> "); //input indicator
        }
        
        //if oldMaster is opened close it
        if(oldMaster != null)
        oldMaster.close();
    }
    
    private static void addTransactions(){
        try{
            inTransaction = new Formatter("src/filematching/intrans.txt");
        }
        catch(SecurityException e){
            System.err.println("Write permission denied.");
            System.exit(1);
        }
        catch(FileNotFoundException e){
            System.err.println("File does not exist.");
            System.exit(1);
        }
        
        System.out.printf("%n%s%n%s%n%s%n%s%n> ",
                "TRANSACTIONS",
                "Enter account number and amount as example below:",
                "100 20.00",
                "Enter dot (.) to end input.");
        
        while(sc.hasNext()){
            try{
                inTransaction.format("%d %.2f%n", sc.nextInt(), sc.nextDouble());
            }
            catch(NoSuchElementException e){
                if(sc.next().equals(".")) break;
                System.err.println("Invalid input. Please try again.");
                sc.nextLine();
            }
            System.out.print("> ");
        }
        
        if(inTransaction!=null)
            inTransaction.close();
    }
}
