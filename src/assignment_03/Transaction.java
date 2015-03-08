/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Barry Speller
 */
public class Transaction implements Serializable {
    private Date transDate;
    private TransactionType transType;
    private String transDescription;
    private boolean transVoided;
    private double amount;

    public Transaction(Date transDate, TransactionType transType, String transDescription, double transAmount) {
        this.transDate = transDate;
        this.transType = transType;
        this.transDescription = transDescription;
        this.transVoided = false;
        this.amount = transAmount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public TransactionType getTransType() {
        return transType;
    }

    public void setTransType(TransactionType transType) {
        this.transType = transType;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public double getAmount() {
        return amount;
    }
    
    public boolean isVoided() {
        return transVoided;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.transDate);
        hash = 67 * hash + Objects.hashCode(this.transType);
        hash = 67 * hash + Objects.hashCode(this.transDescription);
        hash = 67 * hash + (this.transVoided ? 1 : 0);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.transDate, other.transDate)) {
            return false;
        }
        if (this.transType != other.transType) {
            return false;
        }
        if (!Objects.equals(this.transDescription, other.transDescription)) {
            return false;
        }
        if (this.transVoided != other.transVoided) {
            return false;
        }
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        return "Transaction{" + "transDate=" + dateFormat.format(transDate) 
                + ", transType=" + transType 
                + ", transDescription=" + transDescription 
                + ", transVoided=" + transVoided 
                + ", transAmount=" + String.format("%,.2f", amount) + '}';
    }
    
}

