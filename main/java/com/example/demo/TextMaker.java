package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A utility class that creates and manipulates Text objects.
 *
 * @author Desmond Jun Hong, Lau-modified
 */
public class TextMaker {
    /**
     * Initialises the single instance of the TextMaker class to null.
     */
    private static TextMaker singleInstance = null;

    /**
     * Private empty constructor used for passing of methods and parameters to prevent the instatntiation of TextMaker objects.
     */
    private TextMaker() {

    }

    /**
     * Returns the single instance of the TextMaker class.
     * If the instance does not exist, it is created.
     *
     * @return the single instance of the TextMaker class
     */
    static TextMaker getSingleInstance() {
        if (singleInstance == null)
            singleInstance = new TextMaker();
        return singleInstance;
    }

    /**
     * Creates a Text object with the given input string and positions it in the given cell.
     *
     * @param input the string to be displayed in the Text object
     * @param xCell the x-coordinate (x-axis) of the cell in which the Text cell object will be displayed
     * @param yCell the y-coordinate (y-axis) of the cell in which the Text cell object will be displayed
     * @return the created Text object
     */
    Text madeText(String input, double xCell, double yCell) {
        double length = GameScene.getLENGTH();
        double fontSize = (2 * length) / 8.0;
        Text text = new Text(input);
        text.setFont(Font.font(fontSize));
        text.relocate((xCell + (1.2)* length / 7.0), (yCell + 2 * length / 7.0));
        text.setFill(Color.WHITE);

        return text;
    }

    /**
     * Swaps the text and positions of the given Text objects.
     *
     * @param first the first object to be swapped into a different value
     * @param second the second object to be swapped into a different value
     */
    static void changeTwoText(Text first, Text second) {
        String temp;
        temp = first.getText();
        first.setText(second.getText());
        second.setText(temp);

        double tempNumber;
        tempNumber = first.getX();
        first.setX(second.getX());
        second.setX(tempNumber);

        tempNumber = first.getY();
        first.setY(second.getY());
        second.setY(tempNumber);
    }
}