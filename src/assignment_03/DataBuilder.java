/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Barry
 */
public class DataBuilder {

    private static void saveFile(Map<String, BankAccount> map) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Bank2.data"));
            output.writeObject(map);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static Map<String, BankAccount> getFile() {
        Map<String, BankAccount> map = null;
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("Bank2.data"));
            map = (Map<String, BankAccount>) (objectInput.readObject());
            objectInput.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Address address;
        AccountHolder accountHolder;
        BankAccount bankAccount;
        String accountId;
        double newBalance;
        GregorianCalendar calendarDate;
        Date transDate;
        Transaction firstTransaction;

        /*        String[] accountHolderNames = {"Barney Rubble", "Lucinda Suarez",
         "Michael Johnson", "Alfred Penniworth", "Waylon Jennings",
         "Robert Cocktosten", "Connie Simmons", "James Synlynen",
         "William Boyington", "Elliott Shaw", "Amy Winters", "Hank Marcel",
         "Eric Shakley", "Gustav Cousteau", "Tanya Bradley"};*/
        String[] accountHolderNames = {"Tina Russell", "Minnie Leonard",
            "Chester Smythe", "Hazel Worthington", "Kendrick Witsoe",
            "Alfonso Benitez", "Simon Jenkins", "Jane Parkinson",
            "Casper Horne", "Sebastian Jones", "Mark Witherspoon", "Emma McNeal",
            "Ezekiel Rawlings", "Grover Black", "Stephanie Easley"};
        int accountHolderNameCursor = 0;

        String[] accountCompanyNames = {"Downton Mechanics", "Hometown Construction, Inc.",
            "The Remington Co.", "Haysfield Electronics", "Main St Market",
            "Jameson Confections", "Cyberdyne", "Lawrence, Smith & Markey, PC",
            "Stark Enterprises", "Jetstream Industries"};
        int accountCompanyNameCursor = 0;

        /*        Address[] accountHolderAddresses = {
         new Address("1231 Main St", "Chicago", "IL", "60619"),
         new Address("11732 S. Halsted St", "Chicago", "IL", "60619"),
         new Address("1865 W. Marquette Ave", "Chicago", "IL", "60615"),
         new Address("2140 Allemong", "Matteson", "IL", "60443"),
         new Address("4315 Hemlock Ave", "Chicago", "IL", "60615"),
         new Address("2515 E 5th St", "Austin", "TX", "78702"),
         new Address("1060 W. Addison Ave", "Chicago", "IL", "60647"),
         new Address("806 N Michigan Ave", "Chicago", "IL", "60611"),
         new Address("5816 S. Kimbark Ave", "Chicago", "IL", "60639"),
         new Address("1801 W 73rd St", "Chicago", "IL", "60638"),
         new Address("846 W 119th St", "Chicago", "IL", "60643"),
         new Address("7033 N Kedzie Ave", "Chicago", "IL", "60645"),
         new Address("6828 S East End Ave Fl 1", "Chicago", "IL", "60649"),
         new Address("8155 S Throop St", "Chicago", "IL", "60620"),
         new Address("7425 S South Shore Dr", "Chicago", "IL", "60649"),
         new Address("2 Easton Oval", "Columbus", "OH", "43219"),
         new Address("1416 Chesterfield Estates Dr", "Chesterfield", "MO", "63005"),
         new Address("47 Rutland Sq", "Boston", "MA", "02118"),
         new Address("5784 E Dusty Coyote Cir", "Scottsdale", "AZ", "85266"),
         new Address("18 Kenilworth Rd", "Worcester", "MA", "01602"),
         new Address("8503 E 63rd St", "Tulsa", "OK", "74133"),
         new Address("923 El Morro Dr SE", "Rio Rancho", "NM", "87124"),
         new Address("4350 Madison Ave", "Indianapolis", "IN", "46227"),
         new Address("713 E College St", "Toledo", "IA", "52342"),
         new Address("1039 S 11th St", "Lincoln", "NE", "68508")};*/
        Address[] accountHolderAddresses = {
            new Address("1231 Elm St", "Chicago", "IL", "60619"),
            new Address("11732 S. Western Ave", "Blue Island", "IL", "60619"),
            new Address("1865 W. Archer Ave", "Chicago", "IL", "60611"),
            new Address("5521 Allen Parkway", "Houston", "TX", "77007"),
            new Address("1060 W. Addison Ave", "Chicago", "IL", "60647"),
            new Address("1013 E 5th St", "Austin", "TX", "78702"),
            new Address("909 N Michigan Ave", "Chicago", "IL", "60611"),
            new Address("5816 S. Kimbark Ave", "Chicago", "IL", "60639"),
            new Address("1913 W 87rd St", "Chicago", "IL", "60638"),
            new Address("846 W 109th St", "Chicago", "IL", "60643"),
            new Address("4315 Hemlock Ave", "Chicago", "IL", "60615"),
            new Address("313 E 63rd St", "Tulsa", "OK", "74133"),
            new Address("923 El Morro Dr SE", "Rio Rancho", "NM", "87124"),
            new Address("1150 Bleeker Ave", "Indianapolis", "IN", "46227"),
            new Address("7033 N Kedzie Ave", "Chicago", "IL", "60645"),
            new Address("6828 S East End Ave Fl 1", "Chicago", "IL", "60649"),
            new Address("7124 S Constance St", "Chicago", "IL", "60620"),
            new Address("7425 S South Shore Dr", "Chicago", "IL", "60649"),
            new Address("2 Easton Oval", "Columbus", "OH", "43219"),
            new Address("1416 Chesterfield Estates Dr", "Chesterfield", "MO", "63005"),
            new Address("47 Rutland Sq", "Boston", "MA", "02118"),
            new Address("5784 E Dusty Coyote Cir", "Scottsdale", "AZ", "85266"),
            new Address("18 Kenilworth Rd", "Worcester", "MA", "01602"),
            new Address("713 E College St", "Toledo", "IA", "52342"),
            new Address("1039 S 11th St", "Lincoln", "NE", "68508")};
        int accountAddressCursor = 0;

        int personOrCompany;
        boolean passTaken = false;

        Map<String, BankAccount> bankAccountsMap = new LinkedHashMap();

        calendarDate = new GregorianCalendar(2014, ((int) (Math.random() * 6) + 3), (int) (Math.random() * 27) + 1);
        transDate = calendarDate.getTime();

        while (accountAddressCursor < accountHolderAddresses.length) {
            accountId = String.format("%03d", (int) (Math.random() * 1000));
            accountId = accountId + String.format("%03d", (int) (System.currentTimeMillis() % 999));
            address = accountHolderAddresses[accountAddressCursor++];
            accountHolder = new AccountHolder("", "", new Address("", "", "", ""));
            while (!passTaken) {
                personOrCompany = (int) Math.random() * 10;
                if (personOrCompany <= 4 && accountHolderNameCursor < accountHolderNames.length) {
                    accountHolder
                            = new AccountHolder(accountHolderNames[accountHolderNameCursor++], "", address);
                    passTaken = true;
                } else if (accountCompanyNameCursor < accountCompanyNames.length) {
                    accountHolder
                            = new AccountHolder("", accountCompanyNames[accountCompanyNameCursor++], address);
                    passTaken = true;
                }
            }
            newBalance = (Math.random() * 10000);
            if ((int)(Math.random()*4) > 0) {
                bankAccount = new BankAccount(accountHolder, BankAccount.AccountType.CHECKING);
            } else {
                bankAccount = new BankAccount(accountHolder, BankAccount.AccountType.SAVINGS);
            }
            firstTransaction = new Transaction(transDate, TransactionType.CREDIT, "Initial deposit", newBalance);
            try {
                bankAccount.doTransaction(firstTransaction);
            } catch (InsufficientFundsException | NegativeAmountException e) {
                System.out.println(e.getMessage());
            }
            bankAccountsMap.put(accountId, bankAccount);
            passTaken = false;
        }

        saveFile(bankAccountsMap);

        Map<String, BankAccount> newBankAccounts;
        newBankAccounts = getFile();

        newBankAccounts.forEach((String key, BankAccount value) -> {
            System.out.print("\n" + key + "\n");
            System.out.println(value);
        });

    }

}
