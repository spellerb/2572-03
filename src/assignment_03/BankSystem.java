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
import java.util.Map;

/**
 *
 * @author Barry Speller
 */
public class BankSystem {
    private Map<String, BankAccount> mapBankAccount;

    public BankSystem() {
        
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
    
    public BankAccount getBankAccount(String accountId) {
        return mapBankAccount.get(accountId);
    }
    
    public boolean hasAccountId(String accountId) {
        return mapBankAccount.containsKey(accountId);
    }
}
