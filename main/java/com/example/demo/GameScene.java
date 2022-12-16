package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GameScene {

    // Initialises a flag indicating whether the game detects a 2048 cell to obtain the player's permission to choice to continue playing or switching to the EndGame scene.
    private boolean checkAlertBoxOnce = false;

    // Initialises the height of the game scene to the value of 700.
    private static final int HEIGHT = 700;

    // Initialises the grid size of the game scene to a 4 x 4 grid.
    public static int n = 4;

    // Initialises the target 2048 cell to reach to win the game.
    public static int win = 0;

    // Initialises the distance between cells in the game grid to a value of 10.
    private final static int distanceBetweenCells = 10;

    // The length of all individual cells
    private static double LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;

    // A single instance class that restricts the creation and management of text objects
    private final TextMaker textMaker = TextMaker.getSingleInstance();

    // The cells in the game grid
    private final Cell[][] cells = new Cell[n][n];

    // An array that contains the previous state of the game scene grid
    int[][] initialArray = new int[n][n];

    // An array that contains the current state of the game scene grid
    int[][] updatedArray = new int[n][n];

    //The root node of the game scene
    private Group root;

    // Initialises the player's score of the game to 0 at the start of game
    private long score = 0;

    public static void setN(int number) {
        n = number;
        LENGTH = (HEIGHT - ((n + 1) * distanceBetweenCells)) / (double) n;
    }

    static double getLENGTH() {
        return LENGTH;
    }

    private void randomFillNumber() {

        Cell[][] emptyCells = new Cell[n][n];
        int a = 0;
        int b = 0;
        int aForBound, bForBound = 0;

        // spawns an empty cell if there is an empty cell after the movement of the current state of array and spawns if the mentioned conditions were met
        outer:
        while (true) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (cells[i][j].getNumber() == 0) {
                        emptyCells[a][b] = cells[i][j];
                        if (b < n - 1) {
                            bForBound = b;
                            b++;

                        } else {
                            aForBound = a;
                            a++;
                            b = 0;
                            if (a == n)
                                break outer;
                        }
                    }
                }
            }
        }

        // Choose a random empty cell from the list
        Text text;
        Random random = new Random();
        boolean putTwo = random.nextInt() % 2 != 0;
        int xCell, yCell;
        xCell = random.nextInt(aForBound + 1);
        yCell = random.nextInt(bForBound + 1);

        // Fill a random empty cell with either a cell value of 2 or 4
        if (putTwo) {
            text = textMaker.madeText("2", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY());
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(2);
        } else {
            text = textMaker.madeText("4", emptyCells[xCell][yCell].getX(), emptyCells[xCell][yCell].getY());
            emptyCells[xCell][yCell].setTextClass(text);
            root.getChildren().add(text);
            emptyCells[xCell][yCell].setColorByNumber(4);
        }
    }

    private int haveEmptyCell() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cells[i][j].getNumber() == 0)
                    return 1;

            }
        }
        return -1;
    }

    private int passDestination(int i, int j, char direct) {
        int coordinate = 0;

        // move left
        if (direct == 'l') {
            for (int k = j - 1; k >= 0; k--) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                }
            }
            return coordinate;
        }
        coordinate = j;

        // move right
        if (direct == 'r') {
            for (int k = j + 1; k <= n - 1; k++) {
                if (cells[i][k].getNumber() != 0) {
                    coordinate = k - 1;
                    break;
                }
                else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }
        coordinate = i;

        // move down
        if (direct == 'd') {
            for (int k = i + 1; k <= n - 1; k++) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k - 1;
                    break;

                }
                else if (k == n - 1) {
                    coordinate = n - 1;
                }
            }
            return coordinate;
        }

        // move up
        if (direct == 'u') {
            for (int k = i - 1; k >= 0; k--) {
                if (cells[k][j].getNumber() != 0) {
                    coordinate = k + 1;
                    break;
                } else if (k == 0) {
                    coordinate = 0;
                }
            }
            return coordinate;
        }
        return -1;
    }

    private boolean compareArray() {
        return !Arrays.deepEquals(initialArray, updatedArray);
    }

    private void moveLeft() {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                moveHorizontally(i, j, passDestination(i, j, 'l'), -1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    private void moveRight() {
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                moveHorizontally(i, j, passDestination(i, j, 'r'), 1);
            }
            for (int j = 0; j < n; j++) {
                cells[i][j].setModify(false);
            }
        }
    }

    private void moveUp() {
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                moveVertically(i, j, passDestination(i, j, 'u'), -1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }
    }

    private void moveDown() {
        for (int j = 0; j < n; j++) {
            for (int i = n - 1; i >= 0; i--) {
                moveVertically(i, j, passDestination(i, j, 'd'), 1);
            }
            for (int i = 0; i < n; i++) {
                cells[i][j].setModify(false);
            }
        }
    }

    private boolean isValidDesH(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            return cells[i][des + sign].getNumber() == cells[i][j].getNumber() && !cells[i][des + sign].getModify()
                    && cells[i][des + sign].getNumber() != 0;
        }
        return false;
    }

    private void moveHorizontally(int i, int j, int des, int sign) {
        if (isValidDesH(i, j, des, sign)) {
            cells[i][j].adder(cells[i][des + sign]);
            cells[i][des + sign].setModify(true);
            score += cells[i][des + sign].getNumber();
            if (win < cells[i][des + sign].getNumber()) {
                win = cells[i][des + sign].getNumber();
            }
        } else if (des != j) {
            cells[i][j].changeCell(cells[i][des]);
        } else {
            GameScene.this.canNotMove();
        }
    }

    private boolean isValidDesV(int i, int j, int des, int sign) {
        if (des + sign < n && des + sign >= 0) {
            return cells[des + sign][j].getNumber() == cells[i][j].getNumber() && !cells[des + sign][j].getModify()
                    && cells[des + sign][j].getNumber() != 0;
        }
        return false;
    }

    private void moveVertically(int i, int j, int des, int sign) {
        if (isValidDesV(i, j, des, sign)) {
            cells[i][j].adder(cells[des + sign][j]);
            cells[des + sign][j].setModify(true);
            score += cells[des + sign][j].getNumber();
            if (win < cells[des + sign][j].getNumber()) {
                win = cells[des + sign][j].getNumber();
            }
        } else if (des != i) {
            cells[i][j].changeCell(cells[des][j]);
        } else {
            GameScene.this.canNotMove();
        }
    }

    private boolean haveSameNumberNearly(int i, int j) {
        if (i < n - 1 && j < n - 1) {
            if (cells[i + 1][j].getNumber() == cells[i][j].getNumber())
                return true;
            return cells[i][j + 1].getNumber() == cells[i][j].getNumber();
        }
        return false;
    }

    private boolean canNotMove() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (haveSameNumberNearly(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void game(Scene gameScene, Group root, Stage primaryStage, Scene endGameScene, Group endGameRoot) {
        this.root = root;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell((j) * LENGTH + (j + 1) * distanceBetweenCells,
                        (i) * LENGTH + (i + 1) * distanceBetweenCells, LENGTH, root);
            }

        }

        Text usernameText = new Text();
        root.getChildren().add(usernameText);
        usernameText.relocate(120, 50);
        usernameText.setFont(Font.font(30));
        usernameText.setFill(Color.WHITE);
        usernameText.setText("USERNAME: " + MainMenuController.accName);

        Text text = new Text();
        root.getChildren().add(text);
        text.relocate(650, 50);
        text.setFont(Font.font(30));
        text.setFill(Color.WHITE);
        text.setText("SCORE :");

        Text scoreText = new Text();
        root.getChildren().add(scoreText);
        scoreText.relocate(770, 50);
        scoreText.setFont(Font.font(30));
        scoreText.setFill(Color.WHITE);
        scoreText.setText("0");


        randomFillNumber();
        randomFillNumber();

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, key -> Platform.runLater(() -> {
            int haveEmptyCell;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    initialArray[i][j] = cells[i][j].getNumber();
                }
            }

            switch (key.getCode()){
                case DOWN -> GameScene.this.moveDown();
                case UP -> GameScene.this.moveUp();
                case LEFT -> GameScene.this.moveLeft();
                case RIGHT -> GameScene.this.moveRight();
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    updatedArray[i][j] = cells[i][j].getNumber();
                }
            }

            scoreText.setText(score + "");
            haveEmptyCell = GameScene.this.haveEmptyCell();

            if (win == 2048) {
                if (!checkAlertBoxOnce) {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.getButtonTypes().clear();
                    a.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                    a.setHeaderText("CONGRATULATIONS! YOU WON.");
                    a.setContentText("Do you want to enter Endless Mode?");
                    a.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.NO){
                            try {
                                EndGame.getInstance().endGameShow(endGameRoot, primaryStage, score);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            root.getChildren().clear();
                            score = 0;
                            primaryStage.setScene(endGameScene);
                            Account acc = new Account();
                            acc.writeLB(MainMenuController.accName, score);
                        } else if (response == ButtonType.YES) {
                            checkAlertBoxOnce = true;

                        }
                    });
                }
            } else if (haveEmptyCell == -1) {
                if (GameScene.this.canNotMove()) {
                    primaryStage.setScene(endGameScene);
                    Account acc = new Account();
                    acc.writeLB(MainMenuController.accName, score);
                    try {
                        EndGame.getInstance().endGameShow(endGameRoot, primaryStage, score);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    root.getChildren().clear();
                    score = 0;
                    Account.accounts.clear();
                    }
                }

            if (haveEmptyCell == 1 && key.getCode().isArrowKey() && compareArray()) {
                GameScene.this.randomFillNumber();
            }
        }));
    }
}