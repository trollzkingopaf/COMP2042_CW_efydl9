package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public Stage startingStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        startingStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/demo/2048_logo.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}