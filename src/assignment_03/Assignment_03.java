/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Barry Speller
 */
public class Assignment_03 extends Application {

    private BankSystem bankSystem;
    private File bankFile = null;
    private boolean fileChanged;

    private String accountId, accountHolderName, accountHolderCompany;
    private String accountHolderStreet, accountHolderCity, accountHolderState,
            accountHolderZip;
    private double accountBalance;
    private BankAccount.AccountType accountType;

    private FileChooser fileChooser;
    MenuBar menuBar;
    // --- Menu File
    Menu menuFile;
    MenuItem miOpen;
    MenuItem miSave;
    MenuItem miSaveAs;
    // --- Menu Edit
    Menu menuEdit;
    ToggleGroup tgMode;
    RadioMenuItem rmiSearch;
    RadioMenuItem rmiBrowse;

    private VBox searchModeGUI;
    private VBox browseModeGUI;

    private VBox searchBox;
    private BorderPane searchResults;
    private TextField tfCustomerName;
    private TextField tfAccountId;
    private Button btnSearch;
    private Button btnClear;
    private Button btnDeposit;
    private Button btnWithdraw;
    private Button btnBrowse;

    private Label lblCustomerName;
    private Label lblCustomerAddress;
    private Label lblCustomerCSZ;

    private Label lblBalance;
    private Label lblAccountType;

    private TextArea taAccountSummary;

    private Label lblMessage;

    // New Transaction Controls
    private TextField tfNewTransDesc;
    private TextField tfNewTransAmount;
    // New Transaction data
    private TransactionType newTransactionType;
    private Date newTransactionDate;

    String[] accountIds;
    Integer accountIndex = 0;

    private void chooseFile(Stage stage) {
        String message = "";
        bankFile = fileChooser.showOpenDialog(stage);
        if (bankFile != null) {
            if (!bankSystem.getBankData(bankFile)) {
                lblMessage.setText(bankSystem.getSystemMessage());
                enableAccountControls(false);
                btnBrowse.setDisable(true);
                fileChanged = false;
            } else {
                enableAccountControls(true);
                btnBrowse.setDisable(false);
            }
        }
    }

    private void saveFile() {
        if (bankFile != null) {
            if (!bankSystem.saveBankData(bankFile)) {
                lblMessage.setText(bankSystem.getSystemMessage());
            } else {
                fileChanged = false;
                miSave.setDisable(false);
                miSaveAs.setDisable(false);
            }
        }
    }

    private void saveFileAs(File newFile) {
        if (newFile != null) {
            if (!bankSystem.saveBankData(newFile)) {
                lblMessage.setText(bankSystem.getSystemMessage());
            } else {
                fileChanged = false;
                miSave.setDisable(false);
                miSaveAs.setDisable(false);
            }
        }
    }

    private void doNextAccount() {
        accountIndex++;
        if (accountIndex >= accountIds.length) {
            accountIndex = 0;
        }
    }

    private void doPreviousAccount() {
        accountIndex--;
        if (accountIndex < 0) {
            accountIndex = accountIds.length - 1;
        }
    }

    private void browseAccounts() {
        accountIds = bankSystem.getAccounts();
        accountIndex = 0;
        Stage browseStage = new Stage();
        browseStage.setTitle("Browse Accounts");

        VBox browseContainer = new VBox();
        browseContainer.setPadding(new Insets(20, 10, 10, 5));
        browseContainer.setSpacing(8);
        browseContainer.setAlignment(Pos.TOP_LEFT);
        browseContainer.setStyle("-fx-font-family:'Segoe UI','sans-serif';");

        Label lblBrowse = new Label("Account Browse");
        lblBrowse.setStyle("-fx-font-size:22; -fx-font-weight:bold;");

        VBox browseContent = new VBox();
        browseContent.setPadding(new Insets(7, 7, 7, 7));
        browseContent.setSpacing(5);

        TextField tfBcustomer = new TextField();
        Label lblCustomer = new Label("Customer");
        lblCustomer.setStyle("-fx-font-size:11;");
        TextField tfBstreet = new TextField();
        Label lblStreet = new Label("Street");
        lblStreet.setStyle("-fx-font-size:11;");
        TextField tfBcity = new TextField();
        Label lblCity = new Label("City");
        lblCity.setStyle("-fx-font-size:11;");
        GridPane customerCSZ = new GridPane();
        customerCSZ.setVgap(5);
        customerCSZ.setHgap(5);
        customerCSZ.add(tfBcity, 0, 0);
        TextField tfBstate = new TextField();
        tfBstate.setMinWidth(50);
        customerCSZ.add(tfBstate, 1, 0);
        TextField tfBzip = new TextField();
        tfBzip.setMinWidth(75);
        customerCSZ.add(tfBzip, 2, 0);
        customerCSZ.add(lblCity, 0, 1);
        Label lblState = new Label("State");
        lblState.setStyle("-fx-font-size:11;");
        customerCSZ.add(lblState, 1, 1);
        Label lblZip = new Label("Zip");
        lblZip.setStyle("-fx-font-size:11;");
        customerCSZ.add(lblZip, 2, 1);
        Label lblBrowseId = new Label();
        lblBrowseId.setStyle("-fx-font-size:12; -fx-font-weighht:bold;");
        customerCSZ.add(lblBrowseId, 0, 3);
        Label lblBaccountType = new Label();
        lblBaccountType.setStyle("-fx-font-size:12; -fx-font-weighht:bold;");
        customerCSZ.add(lblBaccountType, 1, 3);

        HBox browseControls = new HBox();
        browseControls.setAlignment(Pos.CENTER);
        browseControls.setPadding(new Insets(5, 5, 5, 5));
        browseControls.setSpacing(5);

        Button btnPrevious = new Button("<");
        btnPrevious.setOnAction((ActionEvent event) -> {
            doPreviousAccount();
            tfBcustomer.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderName()
                    + bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderCompany());
            tfBstreet.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getStreet());
            tfBcity.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getCity());
            tfBstate.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getState());
            tfBzip.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getZip());
            lblBrowseId.setText(accountIds[accountIndex]);
            lblBaccountType.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountType().toString());
        });
        Button btnNext = new Button(">");
        btnNext.setOnAction((ActionEvent event) -> {
            doNextAccount();
            tfBcustomer.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderName()
                    + bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderCompany());
            tfBstreet.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getStreet());
            tfBcity.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getCity());
            tfBstate.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getState());
            tfBzip.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getZip());
            lblBrowseId.setText(accountIds[accountIndex]);
            lblBaccountType.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountType().toString());
        });
        Button btnSave = new Button("Save");
        btnSave.setOnAction((ActionEvent event) -> {
            if (bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderName().length() == 0) {
                bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().setAccountHolderCompany(tfBcustomer.getText());
            } else {
                bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().setAccountHolderName(tfBcustomer.getText());
            }
            bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().setStreet(tfBstreet.getText());
            bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().setCity(tfBcity.getText());
            bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().setState(tfBstate.getText());
            bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().setZip(tfBzip.getText());
        });

        browseControls.getChildren().addAll(btnPrevious, btnNext, btnSave);

        browseContent.getChildren().addAll(tfBcustomer, lblCustomer, tfBstreet,
                lblStreet, customerCSZ, browseControls);

        browseContainer.getChildren().addAll(lblBrowse, browseContent);

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().add(browseContainer);

        browseStage.setScene(scene);

        tfBcustomer.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderName()
                + bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderCompany());
        tfBstreet.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getStreet());
        tfBcity.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getCity());
        tfBstate.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getState());
        tfBzip.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountHolder().getAccountHolderAddress().getZip());
        lblBrowseId.setText(accountIds[accountIndex]);
        lblBaccountType.setText(bankSystem.getBankAccount(accountIds[accountIndex]).getAccountType().toString());
        browseStage.show();
    }

    private void createTransaction() {

        //BUILD GUI FOR TRANSACTION WINDOW
        Stage transactionStage = new Stage();
        transactionStage.setTitle(accountType.toString() + " " + accountId);

        VBox transactionContainer = new VBox();
        transactionContainer.setPadding(new Insets(30, 10, 10, 10));
        transactionContainer.setSpacing(5);
        transactionContainer.setStyle("-fx-font-family:'Segoe UI';");

        Label lblTransType = new Label(newTransactionType == TransactionType.CREDIT ? "Deposit" : "Widthdrawal");
        lblTransType.setStyle("-fx-font-size:16; -fx-font-weight:bold;");
        transactionContainer.getChildren().add(lblTransType);
        transactionContainer.setAlignment(Pos.TOP_LEFT);

        VBox transactionContents = new VBox();
        transactionContents.setPadding(new Insets(10, 10, 5, 10));
        transactionContents.setStyle("-fx-border-color:rgb(64,64,64); -fx-border-radius:7; -fx-border-width:2;");
        {
            HBox dateLine = new HBox();
            {
                dateLine.setAlignment(Pos.TOP_RIGHT);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                Label lblTransDate = new Label("Date: " + dateFormat.format(newTransactionDate));
                lblTransDate.setStyle("-fx-font-size:12;");
                dateLine.getChildren().add(lblTransDate);
            }
            GridPane transData = new GridPane();
            transData.setPadding(new Insets(5, 0, 10, 0));
            transData.setHgap(10);
            transData.setVgap(0);
            {
                tfNewTransDesc = new TextField();
                tfNewTransDesc.setMinWidth(300);
                transData.add(tfNewTransDesc, 0, 0);
                tfNewTransAmount = new TextField();
                tfNewTransAmount.setStyle("-fx-text-alignment: right;");
                transData.add(tfNewTransAmount, 1, 0);
                Label lblTransDesc = new Label("Description");
                lblTransDesc.setStyle("-fx-font-size:12");
                transData.add(lblTransDesc, 0, 1);
                Label lblTransAmount = new Label("Amount");
                lblTransAmount.setStyle("-fx-font-size:12");
                transData.add(lblTransAmount, 1, 1);
            }
            //BorderPane.setAlignment(lblTransDate, Pos.BOTTOM_RIGHT);
            transactionContents.getChildren().addAll(dateLine, transData);
        }

        HBox transactionControl = new HBox();
        transactionControl.setAlignment(Pos.TOP_RIGHT);
        transactionControl.setSpacing(15);
        Label lblTransactionMessage = new Label();
        Button btnSubmit = new Button("Submit");
        transactionControl.getChildren().addAll(lblTransactionMessage, btnSubmit);

        btnSubmit.setOnAction((ActionEvent event) -> {
            lblTransactionMessage.setStyle("-fx-fill-text: #000000;");
            lblTransactionMessage.setText("");
            try {
                double newTransAmount = Double.parseDouble(tfNewTransAmount.getText());
                boolean transactionSucceeded;
                transactionSucceeded = bankSystem.processTransaction(tfAccountId.getText(), newTransactionDate, newTransactionType, tfNewTransDesc.getText(), newTransAmount);
                if (transactionSucceeded) {
                    searchForCustomer();
                    miSave.setDisable(false);
                    fileChanged = true;
                } else {
                    lblMessage.setText(bankSystem.getSystemMessage());
                    System.out.println(lblMessage.getText());
                    fileChanged = false;
                }
                transactionStage.close();
            } catch (NumberFormatException e) {
                lblTransactionMessage.setStyle("-fx-fill-text: #ff0000;");
                lblTransactionMessage.setText("Please enter a valid amount for this transaction");
            }

        });

        transactionContainer.getChildren().addAll(transactionContents,
                transactionControl);

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().add(transactionContainer);

        transactionStage.setScene(scene);
        transactionStage.show();
    }

    private void enableAccountControls(boolean enabled) {
        tfCustomerName.setDisable(!enabled);
        tfAccountId.setDisable(!enabled);
        btnSearch.setDisable(!enabled);
        btnClear.setDisable(!enabled);
        btnDeposit.setDisable(!enabled);
        btnWithdraw.setDisable(!enabled);
    }

    private void displayAccountSummary(ArrayList<Transaction> accountRegister) {
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String accountSummary = "";
        accountSummary = String.format("%-8s %-55s %10s %10s\n", "Date", "Description", "Debit", "Credit");
        accountSummary = accountSummary + String.format("%-8s %-55s %10s %10s\n", "--------",
                "-------------------------------------------------------",
                "----------", "----------");
        for (int i = accountRegister.size() - 1; i >= 0; i--) {
            accountSummary = accountSummary + String.format("%-8s ", dateFormatter.format(accountRegister.get(i).getTransDate()));
            accountSummary = accountSummary + String.format("%-55s ", accountRegister.get(i).getTransDescription());
            if (accountRegister.get(i).getTransType() == TransactionType.CREDIT) {
                accountSummary = accountSummary + String.format("           %,10.2f\n", accountRegister.get(i).getAmount());
            } else {
                accountSummary = accountSummary + String.format("%,10.2f\n", accountRegister.get(i).getAmount());
            }
        }
        taAccountSummary.setText(accountSummary);
    }

    private void displaySearchResults() {
        lblCustomerName.setText(accountHolderName + accountHolderCompany);
        lblCustomerAddress.setText(accountHolderStreet);
        lblCustomerCSZ.setText(accountHolderCity + ", "
                + accountHolderState + " "
                + accountHolderZip);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        lblBalance.setText(currencyFormatter.format(accountBalance));
        lblAccountType.setText(accountType.toString());
    }

    private boolean searchForCustomer() {
        if (bankFile != null) {
            if (tfAccountId.getText().trim().length() > 0
                    || tfCustomerName.getText().trim().length() > 0) {
                if (bankSystem.hasAccountId(tfAccountId.getText())) {
                    BankAccount bankAccount;
                    bankAccount = bankSystem.getBankAccount(tfAccountId.getText());
                    accountId = tfAccountId.getText();
                    AccountHolder accountHolder = bankAccount.getAccountHolder();
                    accountHolderName = accountHolder.getAccountHolderName();
                    accountHolderCompany = accountHolder.getAccountHolderCompany();
                    accountHolderStreet = accountHolder.getAccountHolderAddress().getStreet();
                    accountHolderCity = accountHolder.getAccountHolderAddress().getCity();
                    accountHolderState = accountHolder.getAccountHolderAddress().getState();
                    accountHolderZip = accountHolder.getAccountHolderAddress().getZip();
                    accountBalance = bankAccount.getAccountBalance();
                    accountType = bankAccount.getAccountType();
                    if (accountHolderName.equals(tfCustomerName.getText())
                            || accountHolderCompany.equals(tfCustomerName.getText())) {
                        displaySearchResults();
                        displayAccountSummary(bankAccount.getTransactions());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void clearSearch() {
        tfAccountId.clear();
        tfCustomerName.clear();
        lblCustomerName.setText(" ");
        lblCustomerAddress.setText(" ");
        lblCustomerCSZ.setText(" ");
        lblAccountType.setText(" ");
        lblBalance.setText(" ");
        taAccountSummary.setText(" ");
        lblMessage.setText(" ");
    }

    @Override
    public void start(Stage primaryStage) {
        menuBar = new MenuBar();
        // --- Menu File
        menuFile = new Menu("File");
        miOpen = new MenuItem("Open File...");
        menuFile.getItems().add(miOpen);
        miOpen.setOnAction((ActionEvent event) -> {
            chooseFile(primaryStage);
        });
        miSave = new MenuItem("Save");
        menuFile.getItems().add(miSave);
        miSave.setOnAction((ActionEvent event) -> {
            saveFile();
        });
        miSave.setDisable(true);
        
        miSaveAs = new MenuItem("Save As");
        menuFile.getItems().add(miSaveAs);
        miSaveAs.setOnAction((ActionEvent event) -> {
            saveFileAs(fileChooser.showSaveDialog(primaryStage));
        });

        // --- Menu Edit
        menuEdit = new Menu("Mode");
        tgMode = new ToggleGroup();
        rmiSearch = new RadioMenuItem("Search");
        rmiSearch.setSelected(true);
        rmiSearch.setOnAction((ActionEvent event) -> {
        });
        rmiBrowse = new RadioMenuItem("Browse");
        rmiBrowse.setSelected(false);
        rmiBrowse.setOnAction((ActionEvent event) -> {
        });
        rmiSearch.setToggleGroup(tgMode);
        rmiBrowse.setToggleGroup(tgMode);
        menuEdit.getItems().add(rmiSearch);
        menuEdit.getItems().add(rmiBrowse);

        menuBar.getMenus().addAll(menuFile, menuEdit);

        fileChooser = new FileChooser();

        //GUI Heading 
        BorderPane heading = new BorderPane();
        heading.setPadding(new Insets(10, 0, 0, 10));
        try {
            Image bankImage = new Image("assignment_03/Bank2.png");
            ImageView ivBank = new ImageView();
            ivBank.setImage(bankImage);
            heading.setCenter(ivBank);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        searchModeGUI = new VBox();
        searchModeGUI.setPadding(new Insets(0, 0, 0, 0));

        searchBox = new VBox();
        searchBox.setPadding(new Insets(0, 0, 0, 0));
        searchBox.setSpacing(5);
        Label lblCustomer = new Label("Customer Search");
        lblCustomer.setStyle("-fx-font-size:22; -fx-font-weight:bold;");
        searchBox.getChildren().add(lblCustomer);
        GridPane searchControls = new GridPane();
        searchControls.setStyle("-fx-border-color: rgb(64,64,64); -fx-border-width:2; -fx-border-radius:10;");
        searchControls.setPadding(new Insets(10, 5, 10, 5));
        searchControls.setVgap(3);
        searchControls.setHgap(10);
        searchControls.add(new Label("Name: "), 0, 0);
        tfCustomerName = new TextField();
        tfCustomerName.setMinWidth(150);
        searchControls.add(tfCustomerName, 1, 0);
        searchControls.add(new Label("Account Id: "), 2, 0);
        tfAccountId = new TextField();
        tfAccountId.setMinWidth(50);
        searchControls.add(tfAccountId, 3, 0);
        btnSearch = new Button("Search");
        btnSearch.setOnAction((ActionEvent event) -> {
            if (searchForCustomer()) {
                lblMessage.setText("");
            } else {
                lblMessage.setText("No bank account " + tfAccountId.getText() + " found for " + tfCustomerName.getText());
            }
        });
        searchControls.add(btnSearch, 5, 0);
        btnClear = new Button("Clear");
        btnClear.setOnAction((ActionEvent event) -> {
            clearSearch();
        });
        searchControls.add(btnClear, 6, 0);

        VBox resultsBox = new VBox();
        resultsBox.setPadding(new Insets(20, 0, 0, 0));
        searchResults = new BorderPane();
        searchResults.setStyle("-fx-border-color: rgb(64,64,64); -fx-border-width:2; -fx-border-radius:10;");
        //searchResults.setPadding(new Insets(0, 0, 0, 0));
        VBox customerInfo = new VBox();
        customerInfo.setPadding(new Insets(5, 5, 5, 5));
        customerInfo.setSpacing(5);
        customerInfo.setStyle("-fx-font-size:14; -fx-font-weight:bold;");
        lblCustomerName = new Label(" ");
        lblCustomerAddress = new Label(" ");
        lblCustomerCSZ = new Label(" ");
        customerInfo.getChildren().add(lblCustomerName);
        customerInfo.getChildren().add(lblCustomerAddress);
        customerInfo.getChildren().add(lblCustomerCSZ);
        VBox accountInfo = new VBox();
        accountInfo.setPadding(new Insets(5, 5, 5, 5));
        accountInfo.setSpacing(5);
        accountInfo.setStyle("-fx-font-size:14; -fx-font-weight:bold;");
        lblAccountType = new Label(" ");
        lblBalance = new Label();
        accountInfo.getChildren().add(lblAccountType);
        accountInfo.getChildren().add(lblBalance);
        searchResults.setLeft(customerInfo);
        searchResults.setRight(accountInfo);

        searchBox.getChildren().add(searchControls);

        StackPane body = new StackPane();
        body.setPadding(new Insets(10, 10, 10, 10));
        VBox seachModeGUI = new VBox();
        Label lblSearchResults = new Label("Search Results");
        lblSearchResults.setStyle("-fx-font-size:22; -fx-font-weight:bold;");

        resultsBox.getChildren().addAll(lblSearchResults, searchResults);

        HBox accountControls = new HBox();
        accountControls.setPadding(new Insets(10, 0, 5, 0));
        accountControls.setSpacing(5);
        accountControls.setAlignment(Pos.CENTER);
        btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction((ActionEvent event) -> {
            newTransactionType = TransactionType.CREDIT;
            newTransactionDate = new Date();
            createTransaction();
        });
        btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction((ActionEvent event) -> {
            newTransactionType = TransactionType.DEBIT;
            newTransactionDate = new Date();
            createTransaction();
        });
        btnBrowse = new Button("Browse");
        btnBrowse.setDisable(true);
        btnBrowse.setOnAction((ActionEvent event) -> {
            browseAccounts();
        });
        accountControls.getChildren().addAll(btnDeposit, btnWithdraw, btnBrowse);

        VBox accountSummary = new VBox();
        accountSummary.setPadding(new Insets(20, 0, 0, 0));
        taAccountSummary = new TextArea();
        taAccountSummary.setMinHeight(300);
        taAccountSummary.setStyle("-fx-font-family:'Lucida Sans Typewriter', monospace; -fx-font-size:11;");
        accountSummary.getChildren().add(taAccountSummary);

        lblMessage = new Label();
        VBox messageContainer = new VBox();
        messageContainer.setPadding(new Insets(10, 10, 10, 10));
        messageContainer.getChildren().add(lblMessage);

        seachModeGUI.getChildren().addAll(searchBox, resultsBox, accountControls, accountSummary);
        body.getChildren().addAll(seachModeGUI);

        browseModeGUI = new VBox();
        browseModeGUI.setPadding(new Insets(10, 0, 0, 0));
        browseModeGUI.setSpacing(8);

        Label lblBrowse = new Label("Browse");
        lblBrowse.setStyle("-fx-font-size:22; -fx-font-weight:bold;");
        browseModeGUI.getChildren().add(lblBrowse);
        browseModeGUI.setVisible(false);
        body.getChildren().add(browseModeGUI);

        Scene scene = new Scene(new VBox(), 618, 800);
        ((VBox) scene.getRoot()).setStyle("-fx-font-family:'Segoe UI'");
        ((VBox) scene.getRoot()).getChildren().add(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(heading);
        ((VBox) scene.getRoot()).getChildren().add(body);
        ((VBox) scene.getRoot()).getChildren().add(messageContainer);

        primaryStage.setTitle("Assignment 03 - Bank");
        primaryStage.setScene(scene);
        primaryStage.show();

        bankSystem = new BankSystem();
        enableAccountControls(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
