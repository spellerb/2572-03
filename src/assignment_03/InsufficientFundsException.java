/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

/**
 *
 * @author Barry Speller
 */
public class InsufficientFundsException extends Exception {
    private final double amount;
    
    /**
     * Constructs an Insufficient Funds exception
     * @param amount
     */
    public InsufficientFundsException(double amount) {
        super("There are insufficient funds to make a withdrawal of " 
                + String.format("%.2f", amount));
        this.amount = amount;
    }

    /**
     * Returns the amount of the attempted withdrawal
     * @return amount
     */
    public double getAmount() {
        return amount;
    }
    
}
