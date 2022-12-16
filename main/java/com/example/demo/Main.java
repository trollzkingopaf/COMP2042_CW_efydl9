package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class for the game application in which it extends the JavaFX external library classes
 *
 * @author Desmond Jun Hong, Lau-modified
 */
public class Main extends Application {
    /**
     * The primaryStage and a window container of the Main Menu FXML Controller Scene
     */
    public Stage startingStage;

    /**
     * Overrides the start method in the JavaFX external library classes
     * Loads the Main Menu FXML file and sets the stage based on the FXML stage properties.
     *
     * @param primaryStage The primaryStage and a window container of the application
     * @throws Exception if there is an error loading the Main Menu FXML file
     */
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

    /**
     * The main method of the game application, launches the 2048 game application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}