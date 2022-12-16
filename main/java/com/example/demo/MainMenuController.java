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

public class MainMenuController implements Initializable {

    @FXML
    private Label validLogin;

    @FXML
    private AnchorPane startStage;

    @FXML
    private TextField username;

    @FXML
    private Label gameMode;

    @FXML
    private ChoiceBox<String> gameSelector;

    @FXML
    private ColorPicker bgColour;

    private final String[] gameBoard = {"4 x 4", "5 x 5", "6 x 6"};

    int maxLength = 6;

    static final int WIDTH = 900;

    static final int HEIGHT = 900;

    private Group gameRoot = new Group();

    private Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT, setBgColour);

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setGameRoot(Group gameRoot) {
        this.gameRoot = gameRoot;
    }

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

    private static Color setBgColour = Color.rgb(237, 197, 63);

    public void changeBgColour() {
        setBgColour = bgColour.getValue();
        startStage.setBackground(new Background(new BackgroundFill(setBgColour, null, null)));
        gameScene.setFill(bgColour.getValue());
    }

    public void userLogin() {
        checkLogin();
    }

    public static String accName = "";

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

    public void exitMainMenu() {
        Stage start = (Stage) startStage.getScene().getWindow();
        start.close();
    }

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

    public void switchToLeaderboardScene(ActionEvent event) throws IOException {
        Parent leaderboardParent = FXMLLoader.load(getClass().getResource("/com/example/demo/Leaderboard.fxml"));
        Scene leaderboardScene = new Scene(leaderboardParent);

        Stage leaderboardStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.setScene(leaderboardScene);
        leaderboardStage.show();
    }
}