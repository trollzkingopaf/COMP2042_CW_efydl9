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


/**
 * This class contains information for Leaderboard FXML Controller Scene.
 * It displays the top three high-scores in a table or a podium depending on the button toggled by the user.
 * It also provides a back button to return to the Main Menu FXML Controller screen.
 *
 * @author Desmond Jun Hong, Lau
 */
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

    /**
     * Initialises the Leaderboard FXML Controller Scene by reading player's scores from an external leaderboard text file and display a table view with all the high scores while hiding the podium by default.
     * The default button to display is set to contain "Podium"
     * @param url the URL of the FXML file
     * @param rb the resource bundle for the localisation of resources
     */
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

    /**
     * Switches to the Main Menu FXML Controller Screen when the "Back" button is clicked.
     *
     * @param event the action event triggered by the mouse button click
     * @throws IOException An error exception if the FXML file for the Main Menu FXML Controller Scene cannot be found
     */
    public void switchToMainMenuScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Scene menuScene = new Scene(root);

        Stage menuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * Toggles to display between a Podium containing the Top 3 highest-scores and a Table View containing all the records of past to present player's username and scores when the "Podium" button and "Table" mouse button is clicked back and forth.
     *
     * @param event the action event triggered by the mouse button click
     */
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