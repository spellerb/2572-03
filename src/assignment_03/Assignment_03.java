/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    private FileChooser fileChooser;
    private Button btn;
    private VBox searchBox;
    private BorderPane searchResults;
    private TextField tfCustomerName;
    private TextField tfAccountId;
    private Button btnSearch;
    private Button btnClear;
    
    private Label lblCustomerName;
    private Label lblCustomerAddress;
    private Label lblCustomerCSZ;
    
    private TextField tfBalance;
    private Label lblAccountType;

    private void chooseFile(Stage stage) {
        String message = "";
        bankFile = fileChooser.showOpenDialog(stage);
        if (bankFile != null) {
            if (!bankSystem.getBankData(bankFile, message)) {
                System.out.println(message);
                enableSearch(false);
            } else {
                enableSearch(true);
            }
        }
    }
    
    private void enableSearch(boolean enabled) {
        tfCustomerName.setDisable(!enabled);
        tfAccountId.setDisable(!enabled);
        btnSearch.setDisable(!enabled);
        btnClear.setDisable(!enabled);
    }
    
    private void searchForCustomer() {
        if (bankFile != null) {
            if (tfAccountId.getText().trim().length() > 0
                    || tfCustomerName.getText().trim().length() > 0) {
                if (bankSystem.hasAccountId(tfAccountId.getText())) {
                    BankAccount bankAccount;
                    bankAccount = bankSystem.getBankAccount(tfAccountId.getText());
                    AccountHolder accountHolder = bankAccount.getAccountHolder();
                    String accountHolderName = accountHolder.getAccountHolderName();
                    String accountCompanyName = accountHolder.getAccountHolderCompany();
                    String accountHolderAddress = accountHolder.getAccountHolderAddress().getStreet();
                    String accountHolderCSZ = accountHolder.getAccountHolderAddress().getCity()
                            + ", " + accountHolder.getAccountHolderAddress().getState()
                            + " " + accountHolder.getAccountHolderAddress().getZip();
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    String accountBalance = currencyFormatter.format(bankAccount.getAccountBalance()); //String.format(Locale.getDefault(), "%,.2f", );
                    String accountType = bankAccount.getAccountType().toString();
                    if (accountHolderName.equals(tfCustomerName.getText())
                            || accountCompanyName.equals(tfCustomerName.getText())) {
                        lblCustomerName.setText(accountHolderName + accountCompanyName);
                        lblCustomerAddress.setText(accountHolderAddress);
                        lblCustomerCSZ.setText(accountHolderCSZ);
                        tfBalance.setText(accountBalance);
                        lblAccountType.setText(accountType);
                    }
                }
            }
        }
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
        miOpen.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                chooseFile(primaryStage);
            }
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
        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        fileChooser = new FileChooser();
        btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = "";
//                System.out.println("Hello World!");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    if (!bankSystem.getBankData(file, message)) {
                        System.out.println(message);
                    }
                }
            }
        });

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
            searchForCustomer();
        });
        searchControls.add(btnSearch, 5, 0);
        btnClear = new Button("Clear");
        btnClear.setOnAction((ActionEvent event) -> {
            clearSearch();
        });
        searchControls.add(btnClear, 6, 0);

        searchResults = new BorderPane();
        searchResults.setStyle("-fx-border-color: rgb(64,64,64); -fx-border-width:2; -fx-border-radius:10;");
        searchResults.setPadding(new Insets(20,0,0,0));
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
        vboxWrapper.getChildren().addAll(searchBox, lblSearchResults, searchResults);
        body.getChildren().add(vboxWrapper);

        Scene scene = new Scene(new VBox(), 618, 800);
        ((VBox) scene.getRoot()).setStyle("-fx-font-family:'Segoe UI'");
        ((VBox) scene.getRoot()).getChildren().add(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(heading);
        ((VBox) scene.getRoot()).getChildren().add(body);

        primaryStage.setTitle("Assignment 03 - Bank");
        primaryStage.setScene(scene);
        primaryStage.show();

        bankSystem = new BankSystem();
        enableSearch(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
