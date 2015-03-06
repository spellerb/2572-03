/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.util.Objects;

/**
 *
 * @author Barry Speller
 */
public class BankAccount {
    /**
     * Bank account type
     */
    public enum AccountType {
        CHECKING, SAVINGS
    }
    
    //private String accountId;
    private AccountHolder accountHolder;
    private double accountBalance;
    private AccountType accountType;
    //private Transaction[] accountRegister;

    /**
     * Constructs a bank account
     * @param accountHolder
     * @param accountBalance
     * @param accountType 
     */
    public BankAccount(AccountHolder accountHolder, double accountBalance, AccountType accountType) {
        this.accountHolder = accountHolder;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
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

}
