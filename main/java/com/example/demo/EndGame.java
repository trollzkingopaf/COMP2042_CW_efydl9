package com.example.demo;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class EndGame {
    private static EndGame singleInstance = null;

    private EndGame(){

    }

    public static EndGame getInstance(){
        if(singleInstance == null)
            singleInstance= new EndGame();
        return singleInstance;
    }

    public void endGameShow(Group root, Stage primaryStage, long score) throws IOException {
        Text labelText = new Text("GAME OVER");
        if(GameScene.win <= 2048) {
            labelText.setText("YOU WIN! :)");
            labelText.relocate(235,250);

        } else {
            labelText.setText("GAME OVER :(");
            labelText.relocate(220,250);
        }
        labelText.setFill(Color.WHITE);
        labelText.setFont(Font.font(80));
        root.getChildren().add(labelText);

        // Displays the score label
        Text scoreLabeltext = new Text("SCORE :");
        scoreLabeltext.setFill(Color.WHITE);
        scoreLabeltext.relocate(310, 420);
        scoreLabeltext.setFont(Font.font(80));
        root.getChildren().add(scoreLabeltext);

        // Displays the final score
        Text scoreText = new Text(score + "");
        scoreText.setFill(Color.WHITE);
        scoreText.relocate(380,600);
        scoreText.setFont(Font.font(80));
        root.getChildren().add(scoreText);

        // Displays a quit button for the player to exit the game application
        Button quitButton = new Button("QUIT");
        quitButton.setPrefSize(100,30);
        quitButton.setTextFill(Color.WHITE);
        quitButton.setStyle("-fx-background-color: #000000");
        root.getChildren().add(quitButton);
        quitButton.relocate(400,800);
        quitButton.setFocusTraversable(false);

        // Sets the mouse action for the quit button
        quitButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Exit from 2048");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                root.getChildren().clear();
                primaryStage.close();
            }
        });
    }
}
