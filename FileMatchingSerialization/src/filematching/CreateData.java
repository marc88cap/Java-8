/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematching;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class CreateData {
    private static Scanner sc = new Scanner(System.in);
    private static ObjectOutputStream oldMaster;
    private static ObjectOutputStream inTransaction;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        
        
        addAccounts();
        addTransactions();
        
        System.out.printf("%n%s%n", "Files oldmast.ser and intrans.ser have been generated.");
    }
    
    private static void addAccounts(){
        
        try{
            oldMaster = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/filematching/oldmast.ser"))); //Create/Open a new/existing file
        }
        catch(SecurityException e){
            System.err.println("Write permission denied.");
            System.exit(1);
        }
        catch(IOException e){
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
                Account account = new Account(sc.nextInt(), sc.next(), 
                        sc.next(), sc.nextDouble());
                oldMaster.writeObject(account);
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
            catch(IOException e){
                System.err.println("Can Not Write To File. Terminating.");
                System.exit(1);
            }

            System.out.print("> "); //input indicator
        }
        
        try{
            //if oldMaster is opened close it
            if(oldMaster != null)
            oldMaster.close();
        }
        catch(IOException e){
            System.err.println("Error closing file. Terminating.");
        }
    }
    
    private static void addTransactions(){
        
        try{
            inTransaction = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/filematching/intrans.ser")));
        }
        catch(SecurityException e){
            System.err.println("Write permission denied.");
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("File does not exist.");
            System.exit(1);
        }
        
        System.out.printf("%n%s%n%s%n%s%n%s%n> ",
                "TRANSACTIONS",
                "Enter account number and amount as example below:",
                "100 -20.00",
                "Enter dot (.) to end input.");
        
        while(sc.hasNext()){
            try{
                TransactionRecord record = new TransactionRecord(sc.nextInt(), sc.nextDouble());
                inTransaction.writeObject(record);
            }
            catch(NoSuchElementException e){
                if(sc.next().equals(".")) break;
                System.err.println("Invalid input. Please try again.");
                sc.nextLine();
            }
            catch(IOException e){
                System.err.println("Cant Write To File. Terminating.");
                break;
            }
            System.out.print("> ");
        }
        
        try{
            if(inTransaction!=null)
                inTransaction.close();
        }
        catch(IOException e){
            System.err.println("What is not opened can not be closed.");
            System.exit(1);
        }
    }
}