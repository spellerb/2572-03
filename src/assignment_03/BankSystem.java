/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Barry Speller
 */
public class BankSystem {
    private Map<String, BankAccount> mapBankAccount;
    private String systemMessage;

    public BankSystem() {
        super();
    }
    
    public String getSystemMessage() {
        return this.systemMessage;
    }
    
    public boolean getBankData(File fileData, String systemMessage) {
        boolean success = true;
        this.mapBankAccount = null;
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileData));
            this.mapBankAccount = (Map<String, BankAccount>) (objectInput.readObject());
            objectInput.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            systemMessage = e.getMessage();
            success = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            systemMessage = e.getMessage();
            success = false;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            systemMessage = e.getMessage();
            success = false;
        }
        this.mapBankAccount.forEach((String key, BankAccount value) -> {
            System.out.print("\n" + key + "\n");
            System.out.println(value);
        });
        return success;
    }
    
    /**
     * Returns a BankAccount object
     * @param accountId
     * @return 
     */
    public BankAccount getBankAccount(String accountId) {
        return mapBankAccount.get(accountId);
    }
    
    /**
     * Returns true if the bank account data contains an account whose 
     * account Id matches the submitted account Id
     * @param accountId - the account Id to look up
     * @return 
     */
    public boolean hasAccountId(String accountId) { 
        return mapBankAccount.containsKey(accountId);
    }
    
    /**
     * Processes transaction for the give account and transaction data. 
     * Sets system message if process fails.
     * @param accountId
     * @param transDate
     * @param transType
     * @param transDescription
     * @param transAmount
     * @return success of process
     */
    public boolean processTransaction(String accountId, Date transDate, 
            TransactionType transType, String transDescription, double transAmount) {
        Transaction transaction = new Transaction(transDate, transType, transDescription, transAmount);
        try {
            getBankAccount(accountId).doTransaction(transaction);
        } catch (InsufficientFundsException e) {
            this.systemMessage = e.getMessage();
            return false;
        } catch (NegativeAmountException e) {
            this.systemMessage = e.getMessage();
            return false;
        }
        return true;
    }
}
