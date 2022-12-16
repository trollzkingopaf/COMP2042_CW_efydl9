package com.example.demo;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private Label firstPlace;
    @FXML
    private Label secondPlace;
    @FXML
    private Label thirdPlace;

    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, String> tableusername;
    @FXML
    private TableColumn<Account, Long> tablescore;

    @FXML
    private ToggleButton podiumBtn;

    @FXML
    private ToggleButton tableBtn;

    @FXML
    private ImageView podiumLB;

    ArrayList<Account> displayLB = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        podiumBtn.setVisible(false);
        podiumLB.setVisible(false);
        firstPlace.setVisible(false);
        secondPlace.setVisible(false);
        thirdPlace.setVisible(false);
        try {
            displayLB = Account.readLB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Account> accountObservableList = FXCollections.observableArrayList(displayLB);
        tableusername.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().getLeaderboardUsername()));
        tablescore.setCellValueFactory(col -> new SimpleLongProperty(col.getValue().getLeaderboardScore()).asObject());
        tableView.setItems(accountObservableList);
        Account.accounts.clear();
    }

    public void switchToMainMenuScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Scene menuScene = new Scene(root);

        Stage menuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    public void toggleView(ActionEvent event) {

        podiumBtn.setVisible(false);
        tableBtn.setVisible(true);

        if(event.getSource() == podiumBtn) {
            podiumLB.setVisible(false);
            tableView.setVisible(true);
            firstPlace.setVisible(false);
            secondPlace.setVisible(false);
            thirdPlace.setVisible(false);
            podiumBtn.setVisible(false);
            tableBtn.setVisible(true);
        } else if (event.getSource() == tableBtn) {
            Account acc = new Account();
            tableView.setVisible(false);
            podiumLB.setVisible(true);
            firstPlace.setVisible(true);
            firstPlace.setText(Account.Champion);
            secondPlace.setText(Account.RunnerUp);
            thirdPlace.setText(Account.SecondRunnerUp);
            secondPlace.setVisible(true);
            thirdPlace.setVisible(true);

            tableBtn.setVisible(false);
            podiumBtn.setVisible(true);
        }
    }
}