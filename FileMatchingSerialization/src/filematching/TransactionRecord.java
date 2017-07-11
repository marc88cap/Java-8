/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filematching;

import java.io.Serializable;

/**
 *
 * @author markoc
 */
public class TransactionRecord implements Serializable{
    private int account;
    private double amount;

    /**
     * Constructor takes in two values
     * @param accountNumber represents account number
     * @param amount represent an amount of transaction
     */
    public TransactionRecord(int accountNumber, double amount) {
        this.account = accountNumber;
        this.amount = amount;
    }
    
    public void setAccount(int account){
        this.account = account;
    }
    
    public int getAccount(){
        return this.account;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    
}
