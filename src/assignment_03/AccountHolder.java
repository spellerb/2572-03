/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Barry Speller
 */
public class AccountHolder implements Serializable {
    private String accountHolderName;
    private String accountHolderCompany;
    private Address accountHolderAddress;

    /**
     * Constructs an Account Holder
     * @param accountHolderName
     * @param companyName 
     * @param accountHolderAddress
     */
    public AccountHolder(String accountHolderName, String accountHolderCompany, Address accountHolderAddress) {
        this.accountHolderName = accountHolderName;
        this.accountHolderCompany = accountHolderCompany;
        this.accountHolderAddress = accountHolderAddress;
    }

    /**
     * Gets the account holder's name
     * @return accountHolderName
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Sets the account holder name
     * @param accountHolderName 
     */
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     * Gets the account holder's company name
     * @return 
     */
    public String getAccountHolderCompany() {
        return accountHolderCompany;
    }

    /**
     * Set account holder's company
     * @param accountHolderCompany 
     */
    public void setAccountHolderCompany(String accountHolderCompany) {
        this.accountHolderCompany = accountHolderCompany;
    }

    public Address getAccountHolderAddress() {
        return accountHolderAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountHolder other = (AccountHolder) obj;
        if (!Objects.equals(this.accountHolderName, other.accountHolderName)) {
            return false;
        }
        return Objects.equals(this.accountHolderCompany, other.accountHolderCompany);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.accountHolderName);
        hash = 67 * hash + Objects.hashCode(this.accountHolderCompany);
        return hash;
    }

    @Override
    public String toString() {
        return "AccountHolder{" + "accountHolderName=" + accountHolderName 
                + ", accountHolderCompany=" + accountHolderCompany 
                + ", accountHolderAddress=" + accountHolderAddress.toString() 
                + '}';
    }
    
    
}
