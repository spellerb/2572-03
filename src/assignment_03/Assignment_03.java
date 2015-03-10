/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
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

    private String accountId, accountHolderName, accountHolderCompany;
    private String accountHolderStreet, accountHolderCity, accountHolderState,
            accountHolderZip;
    private double accountBalance;
    private BankAccount.AccountType accountType;

    private FileChooser fileChooser;

    private VBox searchBox;
    private BorderPane searchResults;
    private TextField tfCustomerName;
    private TextField tfAccountId;
    private Button btnSearch;
    private Button btnClear;
    private Button btnDeposit;
    private Button btnWithdraw;
    private Button btnSummary;

    private Label lblCustomerName;
    private Label lblCustomerAddress;
    private Label lblCustomerCSZ;

    private TextField tfBalance;
    private Label lblAccountType;

    private Label lblMessage;

    // New Transaction Controls
    private TextField tfNewTransDesc;
    private TextField tfNewTransAmount;
    // New Transaction data
    private TransactionType newTransactionType;
    private Date newTransactionDate;

    private void chooseFile(Stage stage) {
        String message = "";
        bankFile = fileChooser.showOpenDialog(stage);
        if (bankFile != null) {
            if (!bankSystem.getBankData(bankFile, message)) {
                System.out.println(message);
                enableAccountControls(false);
            } else {
                enableAccountControls(true);
            }
        }
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
                } else {
                    lblMessage.setText(bankSystem.getSystemMessage());
                    System.out.println(lblMessage.getText());
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

    private void displaySearchResults() {
        lblCustomerName.setText(accountHolderName + accountHolderCompany);
        lblCustomerAddress.setText(accountHolderStreet);
        lblCustomerCSZ.setText(accountHolderCity + ", "
                + accountHolderState + " "
                + accountHolderZip);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tfBalance.setText(currencyFormatter.format(accountBalance));
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
        tfBalance.clear();
    }

    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem miOpen = new MenuItem("Open File...");
        menuFile.getItems().add(miOpen);
        miOpen.setOnAction((ActionEvent event) -> {
            chooseFile(primaryStage);
        });

        // --- Menu Edit
        Menu menuEdit = new Menu("Mode");
        ToggleGroup tgMode = new ToggleGroup();
        RadioMenuItem rmiSearch = new RadioMenuItem("Search");
        rmiSearch.setSelected(true);
        RadioMenuItem rmiBrowse = new RadioMenuItem("Browse");
        rmiBrowse.setSelected(false);
        rmiSearch.setToggleGroup(tgMode);
        rmiBrowse.setToggleGroup(tgMode);
        menuEdit.getItems().add(rmiSearch);
        menuEdit.getItems().add(rmiBrowse);

        menuBar.getMenus().addAll(menuFile, menuEdit);

        fileChooser = new FileChooser();

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

        searchBox = new VBox();
        Label lblCustomer = new Label("Customer");
        lblCustomer.setStyle("-fx-font-size:24; -fx-font-weight:bold;");
        searchBox.getChildren().add(lblCustomer);
        GridPane searchControls = new GridPane();
        searchControls.setStyle("-fx-border-color: rgb(64,64,64); -fx-border-width:2; -fx-border-radius:10;");
        searchControls.setPadding(new Insets(10, 5, 10, 5));
        searchControls.setVgap(3);
        searchControls.setHgap(10);
        searchControls.add(new Label("Name: "), 0, 0);
        tfCustomerName = new TextField();
        searchControls.add(tfCustomerName, 1, 0);
        searchControls.add(new Label("Account Id: "), 2, 0);
        tfAccountId = new TextField();
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

        searchResults = new BorderPane();
        searchResults.setStyle("-fx-border-color: rgb(64,64,64); -fx-border-width:2; -fx-border-radius:10;");
        searchResults.setPadding(new Insets(20, 0, 0, 0));
        //Label lblSearchResults = new Label("Search Results");
        //lblSearchResults.setStyle("-fx-font-size:24; -fx-font-weight:bold;");
        //searchResults.setTop(lblSearchResults);
        //BorderPane.setAlignment(lblSearchResults, Pos.TOP_LEFT);
        VBox customerInfo = new VBox();
        customerInfo.setStyle("-fx-font-size:16;");
        lblCustomerName = new Label(" ");
        lblCustomerAddress = new Label(" ");
        lblCustomerCSZ = new Label(" ");
        customerInfo.getChildren().add(lblCustomerName);
        customerInfo.getChildren().add(lblCustomerAddress);
        customerInfo.getChildren().add(lblCustomerCSZ);
        VBox accountInfo = new VBox();
        accountInfo.setStyle("-fx-font-size:16;");
        lblAccountType = new Label(" ");
        tfBalance = new TextField();
        tfBalance.setEditable(false);
        accountInfo.getChildren().add(lblAccountType);
        accountInfo.getChildren().add(tfBalance);
        searchResults.setLeft(customerInfo);
        searchResults.setRight(accountInfo);

        searchBox.getChildren().add(searchControls);

        StackPane body = new StackPane();
        body.setPadding(new Insets(10, 10, 10, 10));
        VBox vboxWrapper = new VBox();
        Label lblSearchResults = new Label("Search Results");
        lblSearchResults.setStyle("-fx-font-size:24; -fx-font-weight:bold;");
        //searchResults.setTop(lblSearchResults);

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
        btnSummary = new Button("Show Summary");
        accountControls.getChildren().addAll(btnDeposit, btnWithdraw, btnSummary);

        lblMessage = new Label();

        vboxWrapper.getChildren().addAll(searchBox, lblSearchResults, searchResults, accountControls);
        body.getChildren().addAll(vboxWrapper);

        Scene scene = new Scene(new VBox(), 618, 800);
        ((VBox) scene.getRoot()).setStyle("-fx-font-family:'Segoe UI'");
        ((VBox) scene.getRoot()).getChildren().add(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(heading);
        ((VBox) scene.getRoot()).getChildren().add(body);
        ((VBox) scene.getRoot()).getChildren().add(lblMessage);

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
