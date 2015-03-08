/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

/**
 * Negative Amount Exception occurs if the user tries to enter a 
 * negative deposit or withdrawal
 * @author Barry Speller
 */
public class NegativeAmountException extends Exception {
    private final double amount;

    /**
     * Constructs a Negative Amount exception
     * @param amount 
     */
    public NegativeAmountException(double amount) {
        super("Negative amount, " + String.format("%.2f", amount) + ", may not be deposited or withdrawn");
        this.amount = amount;
    }

    /**
     * Returns the amount that was attempted to be withdrawn or 
     * deposited
     * @return amount
     */
    public double getAmount() {
        return amount;
    }
    
}
