/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematching;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class FileMatching {
    private static Scanner oldMaster, inTransaction;
    private static Formatter newMaster;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        openOldMaster(); // open a old master .txt file
        readMaster(); // read and write data inside the file on the screen
        
        openInTransactions();  // open transaction file
        readTransactions(); // read and write info inside the file on the screen
        
        closeOldMaster(); // close master file
        closeInTransactions(); // close transactions file
        
        openOldMaster(); // open old master file again
        openNewMaster(); // open or create new master file
        matchFiles(); // method to merge data from old master and transactions
    }

    private static void readMaster(){
        System.out.printf("%s%n%-10s %-12s %-12s %10s%n",
                "ACCOUNTS",
                "Account", "First Name", "Last Name", "Balance");
        
        while(oldMaster.hasNext()){
            try{
                System.out.printf("%-10d %-12s %-12s %10.2f%n",
                        oldMaster.nextInt(), oldMaster.next(),
                        oldMaster.next(), oldMaster.nextDouble());
            }
            catch(NoSuchElementException e){
                System.err.println("File Format Is Not Valid. Terminating.");
                System.exit(1);
            }
        }
    }
    
    private static void readTransactions(){
        System.out.printf("%n%s%n%-10s %10s%n","TRANSACTIONS","Account","Amount");
        
        while(inTransaction.hasNext()){
            try{
                System.out.printf("%-10d %10.2f%n", 
                        inTransaction.nextInt(),
                        inTransaction.nextDouble());
            }
            catch (NoSuchElementException e){
                System.err.println("File Format Is Not Valid. Terminating.");
                System.exit(1);
            }
        }
    }
    
    private static void matchFiles(){ 
        // new Log Object stores log file and transaction file paths
        Log log = new Log("src/filematching/log.txt","src/filematching/intrans.txt");
        
        try{
            // for each line in old master file it creates an Account Object
            while(oldMaster.hasNext()){
                Account mast = new Account(oldMaster.nextInt(),oldMaster.next(),
                                            oldMaster.next(),oldMaster.nextDouble());
                /* for each line in old master it adds an ID of master (only once)
                *  to Log Object and compares each master ID with transaction IDs
                */
                openInTransactions();
                log.addId(mast.getAccount());
                while(inTransaction.hasNext()){
                    //each transaction gets saved to an TransactionRecord Object
                    TransactionRecord trans = new TransactionRecord(
                            inTransaction.nextInt(),inTransaction.nextDouble());
                    if(mast.getAccount()==trans.getAccount()){
                        /* if ID of master and transaction match 
                        *  the new balance gets calculated
                        */
                        mast.combine(trans);
                    }
                    
                }
                // this line stores data from Account Object to new master file
                newMaster.format("%d %s %s %.2f%n", mast.getAccount(),
                            mast.getFirstName(), mast.getLastName(),
                            mast.getBalance());
                closeInTransactions(); // close transaction file
            }
            /* when old master file has no more lines to be read
            *  we close all files and finally compare IDs from stored
            *  IDs (old master IDs) and transactions. All unmatched IDs are
            *  stored to the logfile
            */
            closeNewMaster(); 
            log.compareList();
        }
        catch(NoSuchElementException e){
            System.err.println("File Format Error. Terminating.");
        }
        
    }
    
    private static void closeOldMaster(){
        if(oldMaster!=null)
            oldMaster.close();
    }
    
    private static void closeInTransactions(){
        if(inTransaction!=null)
            inTransaction.close();
    }
    
    private static void closeNewMaster(){
        if(newMaster!=null)
            newMaster.close();
    }
    
    private static void openOldMaster(){
        try{
            oldMaster = new Scanner(Paths.get("src/filematching/oldmast.txt"));
        }
        catch(IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void openInTransactions(){
        try{
            inTransaction = new Scanner(Paths.get("src/filematching/intrans.txt"));
        }
        catch (IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    private static void openNewMaster(){
        try{
            newMaster = new Formatter("src/filematching/newmast.txt");
        }
        catch(SecurityException e){
            System.err.println("Can Not Write To File. Terminating.");
            System.err.println(1);
        }
        catch(FileNotFoundException e){
            System.err.println("File Not Found. Terminating.");
            System.exit(1);
        }
    }
}
