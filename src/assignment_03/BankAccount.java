/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Barry Speller
 */
public class BankAccount implements Serializable {
    /**
     * Bank account type
     */
    public enum AccountType {

        /**
         * Checking Account
         */
        CHECKING,

        /**
         * Savings Account
         */
        SAVINGS
    }
    
    //private String accountId;
    private AccountHolder accountHolder;
    private double accountBalance;
    private AccountType accountType;
    private ArrayList<Transaction> transactionRegister;

    /**
     * Constructs a bank account
     * @param accountHolder
     * @param accountBalance
     * @param accountType 
     */
    public BankAccount(AccountHolder accountHolder, AccountType accountType) {
        this.accountHolder = accountHolder;
        this.accountBalance = 0;
        this.accountType = accountType;
        this.transactionRegister = new ArrayList<>();
    }

    /**
     * Enters valid transaction to transactionRegister
     * @param transaction
     * @throws InsufficientFundsException
     * @throws NegativeAmountException
     */
    public void doTransaction(Transaction transaction) throws InsufficientFundsException, NegativeAmountException {
        double transAmount = transaction.getAmount();
        if (transAmount < 0) {
            throw new NegativeAmountException(transAmount);
        }
        
        TransactionType transType = transaction.getTransType();
        if (transType == TransactionType.DEBIT 
                && transAmount > this.accountBalance) {
            throw new InsufficientFundsException(transAmount);
        }
        
        switch(transType) {
            case CREDIT:
                this.accountBalance += transAmount;
                break;
            case DEBIT:
                this.accountBalance -= transAmount;
                break;
        }
        this.transactionRegister.add(transaction);
    }
    
    /**
     * Gets the account holder 
     * @return accountHolder
     */
    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    /**
     * Sets the account holder
     * @param accountHolder 
     */
    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    /**
     * Gets the account balance
     * @return accountBalance
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Gets the type of account
     * @return accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "accountHolder=" + accountHolder + ", accountBalance=" + accountBalance + ", accountType=" + accountType + ", \ntransactionRegister=" + transactionRegister + '}';
    }

}
