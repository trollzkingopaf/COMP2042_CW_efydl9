package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Main Menu Controller class is the assigned controller for the Main Menu of the game.
 * It handles the initialisation of the Main Menu and the actions performed in the Main Menu.
 * These include a game mode selector, changing of the background colour for the entire game application and a login to store the player's username.
 *
 * @author Desmond Jun Hong, Lau
 */
public class MainMenuController implements Initializable {
    /**
     * The FXML label for displaying the login status.
     */
    @FXML
    private Label validLogin;
    /**
     * The FXML anchor pane for the Main Menu stage.
     */
    @FXML
    private AnchorPane startStage;
    /**
     * The FXML text field for the player's username.
     */
    @FXML
    private TextField username;
    /**
     * The FXML label for the game mode.
     */
    @FXML
    private Label gameMode;
    /**
     * The FXML choice box for selecting the game mode.
     */
    @FXML
    private ChoiceBox<String> gameSelector;
    /**
     * The FXML colour picker for the selection of the background colour for the entire game application.
     */
    @FXML
    private ColorPicker bgColour;

    /**
     * An array of strings representing the different game modes available (4 x 4, 5 x 5, 6 x 6).
     */
    private final String[] gameBoard = {"4 x 4", "5 x 5", "6 x 6"};

    /**
     * The maximum length of the player's username.
     */
    int maxLength = 6;
    /**
     * Initialises the width of the Main Menu stage and window container to 900.
     */
    static final int WIDTH = 900;
    /**
     * Initialises the height of the Main Menu stage and window container to 900.
     */
    static final int HEIGHT = 900;
    /**
     * The root node of the Main Menu scene.
     */
    private Group gameRoot = new Group();
    /**
     * The Main Menu scene.
     */
    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, setBgColour);

    /**
     * Sets the Main Menu scene
     *
     * @param gameScene the Main Menu scene to set
     */
    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Sets the root node of the Main Menu scene.
     *
     * @param gameRoot the root node of the Main Menu scene to set
     */
    public void setGameRoot(Group gameRoot) {
        this.gameRoot = gameRoot;
    }

    /**
     * Initialises the Main Menu. This includes the removal of the focus of the player's username text field.
     * A choice box defaulted to 4 x 4 game grid in which it contains a choice box for the player to select from (4 x 4, 5 x 5, 6 x 6)
     *
     * @param url the URL for the Main Menu FXML file
     * @param rb the resource bundle for the Main Menu's resources in which it contains non java files.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setFocusTraversable(false);

        if(gameSelector != null) {
            gameSelector.setValue("4 x 4");
            gameSelector.getItems().addAll(gameBoard);
            gameSelector.setOnAction(this::getGameBoard);
        } else {
            System.out.println("Please select a game mode");
        }

    }

    /**
     *
     */
    private static Color setBgColour = Color.rgb(237, 197, 63);

    /**
     * Changes the background colour of the Main Menu and the 2048 game scene.
     * Assigns the background colour based on the player's colour picker preference.
     */
    public void changeBgColour() {
        setBgColour = bgColour.getValue();
        startStage.setBackground(new Background(new BackgroundFill(setBgColour, null, null)));
        gameScene.setFill(bgColour.getValue());
    }

    /**
     * A constructor to check for the player's login information.
     */
    public void userLogin() {
        checkLogin();
    }

    /**
     * Initialises the player's username input to an empty string.
     */
    public static String accName = "";

    /**
     * Checks the player's username and opens the 2048 game stage if the login is valid.
     * The player must fulfill a player's username, a value of game grid is selected and username's input string length is not more than 6 in order to play the game.
     * Otherwise, a label on top of the text field will display a toast based on how did the player violates the mentioned criteria.
     * Sets the default JavaFX application icon to a custom 2048 image as the icon.
     */
    public void checkLogin() {
        accName = username.getText().replaceAll("\\s", "");
        if (!accName.isEmpty() && gameSelector.getValue() != null && accName.length() <= maxLength) {
            Stage start = (Stage) startStage.getScene().getWindow();
            start.close();

            Stage primaryStage = new Stage();

            Group menuRoot = new Group();
            Group accountRoot = new Group();

            Group endgameRoot = new Group();
            Scene endGameScene = new Scene(endgameRoot, WIDTH, HEIGHT, setBgColour);



            Rectangle backgroundOfMenu = new Rectangle(240, 120, Color.rgb(120, 120, 120, 0.2));
            backgroundOfMenu.setX(WIDTH / 2 - 120);
            backgroundOfMenu.setY(180);
            menuRoot.getChildren().add(backgroundOfMenu);

            Rectangle backgroundOfMenuForPlay = new Rectangle(240, 140, Color.rgb(120, 20, 100, 0.2));
            backgroundOfMenuForPlay.setX(WIDTH / 2 - 120);
            backgroundOfMenuForPlay.setY(180);
            accountRoot.getChildren().add(backgroundOfMenuForPlay);

            Group gameRoot = new Group();
            setGameRoot(gameRoot);
            Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, setBgColour);
            setGameScene(gameScene);
            primaryStage.setScene(gameScene);
            GameScene game = new GameScene();
            game.game(gameScene, gameRoot, primaryStage, endGameScene, endgameRoot);

            primaryStage.getIcons().add(new Image("C:/Uni/DMS/CW/src/2048-Game/img/2048_logo.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } else if (accName.isEmpty()) {
            validLogin.setText("Please enter your username.");
        } else if (accName.length() > maxLength){
            validLogin.setText("Please enter <=6 characters.");
        } else if (gameSelector.getValue() == null){
            validLogin.setText("Please select a game mode.");
        } else {
            validLogin.setText("Unexpected error, please restart program.");
        }
    }

    /**
     * Exits from the Main Menu stage and closes its window container.
     */
    public void exitMainMenu() {
        Stage start = (Stage) startStage.getScene().getWindow();
        start.close();
    }

    /**
     * Gets the two-dimensional game grid size based on the selected game mode and sets it in the GameScene class.
     *
     * @param event the mouse clicked button action event that triggers this method
     */
    public void getGameBoard(ActionEvent event) {
        String myGameMode = gameSelector.getValue();
        gameMode.setText(myGameMode);
        String gameMode = gameSelector.getSelectionModel().getSelectedItem();

        switch (gameMode) {
            case "4 x 4" -> GameScene.setN(4);
            case "5 x 5" -> GameScene.setN(5);
            case "6 x 6" -> GameScene.setN(6);
        }
    }

    /**
     * Loads the Leaderboard FXML Controller Scene and replaces the Main Menu Scene for the Leaderboard Stage and window container.
     *
     * @param event the mouse clicked button action event that triggers this method
     * @throws IOException if the Leaderboard FXML Controller file cannot be found or loaded
     */
    public void switchToLeaderboardScene(ActionEvent event) throws IOException {
        Parent leaderboardParent = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        Scene leaderboardScene = new Scene(leaderboardParent);

        Stage leaderboardStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.setScene(leaderboardScene);
        leaderboardStage.show();
    }
}