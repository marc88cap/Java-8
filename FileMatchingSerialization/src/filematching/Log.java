/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematching;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.nio.file.Files;
/**
 *
 * @author markoc
 */
public class Log {
    HashMap<Integer, Integer> idList = new HashMap<>(10);
    ObjectInputStream trans;
    Formatter log;
    String tPath, lPath;
    
    /**
     * Constructor saves paths of files to local variables
     * @param logPath stores a path were to create or locate a log file
     * @param trans stores a path were to create or locate a transactions file
     */
    public Log(String logPath, String transPath){
        this.tPath = transPath;
        this.lPath = logPath;
    }
    
    /**
     *
     * @param id (master)that is going to be saved into a list and
     * compared with master account IDs (it does not save duplicates)
     */
    public void addId(int id){
        if(!this.idList.containsKey(id))
            this.idList.put(id, id);
    }
    
    /**
     * This method runs through the transactions file and compares IDs from that
     * file with IDs from IDs added to the list. If there is no match a line 
     * of text with that ID is stored to a log file.
     */
    public void compareList(){
        openTrans(); //we open an existing file, it must exist
        openLog(); //we open or create a file
        
        try{
            while(true){
                TransactionRecord record = (TransactionRecord) this.trans.readObject();
                
                if(!this.idList.containsKey(record.getAccount()))
                    log.format("%s %d%n","Unmatched transaction record for number: ", record.getAccount());
            }
        }
        catch(ClassNotFoundException e){
            System.err.println("Object error. Terminating.");
            System.exit(1);
        }
        catch(EOFException e){
            System.out.printf("%n%s%n","Done merging transactions with accounts");
        }
        catch(IOException e){
            System.err.println("Cant Open File. Terminating");
            System.exit(1);
        }
        //closing not needed files
        closeLog();
        closeTrans();
    }
    
    public void openLog(){
        try{
            log = new Formatter(this.lPath);
        }
        catch(SecurityException e){
            System.err.println("Can Not Write To File. Terminating.");
            System.exit(1);
        }
        catch(FileNotFoundException e){
            System.err.println("File Not Found. Terminating.");
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Can Not Open File. Terminating.");
            System.exit(1);
        }
    }
    
    public void openTrans(){
        try{
            this.trans = new ObjectInputStream(Files.newInputStream(Paths.get(this.tPath)));
        }
        catch(IOException e){
            System.err.println("Error Opening File. Terminating.");
            System.exit(1);
        }
    }
    
    public void closeLog(){
        if(this.log!=null)
            this.log.close();
    }
    
    public void closeTrans(){
        try{
            if(this.trans!=null)
                this.trans.close();
        }
        catch(IOException e){
            System.err.println("Cant Close What Is Not Open.");
        }
    }
}
