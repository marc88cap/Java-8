package filematching;

import filematching.TransactionRecord;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marko.ch
 */
public class Account{
    private int account;
    private String firstName;
    private String lastName;
    private double balance;

    /**
     * Creating an empty object will set an empty account with call 
     * to another construction
     */
    public Account(){ 
        this(0,"","",0.0);
    } 
    
    /**
     * Creating an object with data we are going to fill the following fields:
     * @param account as account id
     * @param firstName as first name
     * @param lastName as last name
     * @param balance as current balance on account
     */
    public Account(int account, String firstName, 
            String lastName, double balance){
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    } 
    //getters with corresponding setters
    public int getAccount(){
        return this.account;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public double getBalance(){
        return this.balance;
    }    
    
    public void setBalance(double balance){
        this.balance = balance;
    }
    // subtracts the amount from current balance
    public void combine(TransactionRecord record){
        this.setBalance(this.getBalance()+record.getAmount());
    }
    /* we override the superclass method toString 
    *  to return a string of account details
    */
    @Override
    public String toString(){
        return String.format("%d %s %s %.2f",this.account, this.firstName,
                this.lastName, this.balance); 
    }
}
