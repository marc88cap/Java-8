/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematchingserialization;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

/**
 *
 * @author markoc
 */
public class FileMatching {
    private static ObjectInputStream inOldMaster, inTransaction, inNewMaster;
    private static ObjectOutputStream outNewMaster;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        openInOldMaster(); // open a old master file
        printInOldMaster(); // read and write data inside the file on to the screen
        
        openInTransactions();  // open transaction file
        printInTransactions(); // read and write info inside the file on to the screen
        closeInOldMaster(); // close master file
        closeInTransactions(); // close transactions file
        
        openInOldMaster(); // open old master file again
        openOutNewMaster(); // open or create new master file
        mergeFilesData(); // method to merge data from old master and transactions
        
        //print results on screen
        openInNewMaster();
        printInNewMaster();
        closeInNewMaster();
    }

    private static void printInOldMaster(){
        System.out.printf("%s%n%-10s %-12s %-12s %10s%n",
                "ACCOUNTS",
                "Account", "First Name", "Last Name", "Balance");
        
        try{
            
            while(true){
                    Account account = (Account) inOldMaster.readObject();

                    System.out.printf("%-10d %-12s %-12s %10.2f%n",
                            account.getAccount(), account.getFirstName(),
                            account.getLastName(), account.getBalance());

            }
            
        }
        catch (ClassNotFoundException e){
            System.err.println("Invalid Object. Terminating.");
            System.exit(1);
        }
        catch (EOFException e){}
        catch(IOException e){
            System.err.println("Cant Read File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void printInNewMaster(){
        System.out.printf("%n%s%n%s%n%-10s %-12s %-12s %10s%n",
                "Results:",
                "ACCOUNTS",
                "Account", "First Name", "Last Name", "Balance");
        
        try{
            
            while(true){
                    Account account = (Account) inNewMaster.readObject();

                    System.out.printf("%-10d %-12s %-12s %10.2f%n",
                            account.getAccount(), account.getFirstName(),
                            account.getLastName(), account.getBalance());
            }
            
        }
        catch (ClassNotFoundException e){
            System.err.println("Invalid Object. Terminating.");
            System.exit(1);
        }
        catch (EOFException e){}
        catch(IOException e){
            System.err.println("Cant Read File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void printInTransactions(){
        System.out.printf("%n%s%n%-10s %10s%n","TRANSACTIONS","Account","Amount");
        
        try{
            
            while(true){
                TransactionRecord record = (TransactionRecord) inTransaction.readObject();
                System.out.printf("%-10d %10.2f%n", 
                        record.getAccount(),
                        record.getAmount());
            }
            
        }
        catch (ClassNotFoundException e){
            System.err.println("Invalid Object. Terminating.");
            System.exit(1);
        }
        catch (EOFException e){}
        catch(IOException e){
            System.err.println("Cant Read File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void mergeFilesData(){ 
        // new Log Object stores log file and transaction file paths
        Log log = new Log("src/filematchingserialization/log.txt","src/filematchingserialization/intrans.ser");
        
        try{
            // for each line in old master file is declared as Account Object
            while(true){
                Account mast = (Account) inOldMaster.readObject();
                
                /* for each line in old master it adds an ID of master (only once)
                *  to Log Object and compares each master ID with transaction IDs
                */
                openInTransactions();
                log.addId(mast.getAccount());
                try{
                    while(true){
                        //each transaction gets declared as TransactionRecord Object
                        TransactionRecord trans = (TransactionRecord) inTransaction.readObject();
                        if(mast.getAccount()==trans.getAccount()){
                            /* if ID of master and transaction match 
                            *  the new balance gets calculated
                            */
                            mast.combine(trans);
                        }

                    }
                }
                catch(EOFException e){}
                
                // this line stores data from Account Object to new master file
                outNewMaster.writeObject(mast);
                closeInTransactions(); // close transaction file
            }
            
        }
        catch(EOFException e){}
        catch(NoSuchElementException e){
            System.err.println("File Format Error. Terminating.");
        }
        catch(IOException e){
            System.err.println("Cant Open File. Terminating.");
        }
        catch(ClassNotFoundException e){
            System.err.println("Invalid object. Terminating.");
        }
            
        /* when old master file has no more lines to be read
        *  we close all files and finally compare IDs from stored
        *  IDs (old master IDs) and transactions. All unmatched IDs are
        *  stored to the logfile
        */
        closeOutNewMaster();  
        log.compareList();
    }
    
    private static void closeInOldMaster(){
        try{
            if(inOldMaster!=null)
                inOldMaster.close();
        }
        catch(IOException e){
            System.err.println("Cant Close File. Error.");
        }
    }
    
    private static void closeInTransactions(){
        try{
            if(inTransaction!=null)
                inTransaction.close();
        }
        catch(IOException e){
            System.err.println("Cant Close File. Error.");
        }
    }
    
    private static void closeOutNewMaster(){
        try{
            if(outNewMaster!=null)
                outNewMaster.close();
        }
        catch(IOException e){
            System.err.println("Cant Close Files. Error.");
        }
    }
    
    private static void closeInNewMaster(){
        try{
            if(inNewMaster!=null)
                inNewMaster.close();
        }
        catch(IOException e){
            System.err.println("Cant Close Files. Error.");
        }
    }
    
    private static void openInNewMaster(){
        try{
            inNewMaster = new ObjectInputStream(Files.newInputStream(Paths.get("src/filematchingserialization/newmast.ser")));
        }
        catch(IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void openInOldMaster(){
        try{
            inOldMaster = new ObjectInputStream(Files.newInputStream(Paths.get("src/filematchingserialization/oldmast.ser")));
        }
        catch(IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void openInTransactions(){
        try{
            inTransaction = new ObjectInputStream(Files.newInputStream(Paths.get("src/filematchingserialization/intrans.ser")));
        }
        catch (IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void openOutNewMaster(){
        try{
            outNewMaster = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/filematchingserialization/newmast.ser")));
        }
        catch(SecurityException e){
            System.err.println("Can Not Write To File. Terminating.");
            System.err.println(1);
        }
        catch(FileNotFoundException e){
            System.err.println("File Not Found. Terminating.");
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Cant Open File. Terminating");
            System.exit(1);
        }
    }
}