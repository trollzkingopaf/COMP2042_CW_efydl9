package com.example.demo;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Cell properties on the 2048 game board.
 *
 * @author Desmond Jun Hong, Lau-modified
 */
public class Cell {
    /**
     * The visual cell properties on the game board.
     */
    private Rectangle rectangle;
    /**
     * The root node of the JavaFX scene.
     */
    private Group root;
    /**
     * Integer values of cells.
     */
    private Text textClass;
    /**
     * Initialises a flag indicating whether the cell's value has been modified.
     */
    private boolean modify = false;

    /**
     * Sets the value of the modify flag.
     * @param modify the value of the modify flag
     */
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    /**
     * A getter that returns the value of the modify flag.
     * @return the value of the modify flag.
     */
    public boolean getModify() {
        return modify;
    }

    /**
     * Constructs a new cell with specific cells properties containing position, scale and root node.
     *
     * @param x the x-coordinate (horizontal) of the cell
     * @param y the y-coordnate (vertical) of the cell
     * @param scale the size of the cell to scale
     * @param root the root node of the JavaFX scene
     */
    Cell(double x, double y, double scale, Group root) {
        rectangle = new Rectangle();
        rectangle.setX(x + 100);
        rectangle.setY(y + 150);
        rectangle.setHeight(scale);
        rectangle.setWidth(scale);

        rectangle.setArcWidth(50.0);
        rectangle.setArcHeight(50.0);

        this.root = root;
        rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
        this.textClass = TextMaker.getSingleInstance().madeText("0", x + 100, y + 150);
        root.getChildren().add(rectangle);
    }

    /**
     * Sets the integer value of the cell.
     * @param textClass the integer value of cell.
     */
    public void setTextClass(Text textClass) {
        this.textClass = textClass;
    }

    /**
     * Changes the value of the cell to the specified cell.
     * @param cell value of the cell to be updated
     */
    public void changeCell(Cell cell) {
        TextMaker.changeTwoText(textClass, cell.getTextClass());
        root.getChildren().remove(cell.getTextClass());
        root.getChildren().remove(textClass);

        if (!cell.getTextClass().getText().equals("0")) {
            root.getChildren().add(cell.getTextClass());
        }
        if (!textClass.getText().equals("0")) {
            root.getChildren().add(textClass);
        }
        setColorByNumber(getNumber());
        cell.setColorByNumber(cell.getNumber());
    }

    /**
     * Adds the neighbouring cell to this cell and sets the text of updated cell to 0.
     *
     * @param cell the neighbouring cell to be added to this cell
     */
    public void adder(Cell cell) {
        cell.getTextClass().setText((cell.getNumber() + this.getNumber()) + "");
        textClass.setText("0");
        root.getChildren().remove(textClass);
        cell.setColorByNumber(cell.getNumber());
        setColorByNumber(getNumber());
    }

    /**
     * Sets the colour of the cell based on a given number.
     * Non-specified number of the cells are set to the default number instead.
     *
     * @param number sets the block colour of the cell by a given number
     */
    public void setColorByNumber(int number) {
        switch (number) {
            case 0:
                rectangle.setFill(Color.rgb(224, 226, 226, 0.5));
                break;
            case 2:
                rectangle.setFill(Color.rgb(232, 255, 100, 0.7));
                break;
            case 4:
                rectangle.setFill(Color.rgb(232, 220, 50, 0.7));
                break;
            case 8:
                rectangle.setFill(Color.rgb(232, 200, 44, 0.7));
                break;
            case 16:
                rectangle.setFill(Color.rgb(232, 170, 44, 0.7));
                break;
            case 32:
                rectangle.setFill(Color.rgb(180, 120, 44, 0.7));
                break;
            case 64:
                rectangle.setFill(Color.rgb(180, 100, 44, 0.8));
                break;
            case 128:
                rectangle.setFill(Color.rgb(180, 80, 44, 0.8));
                break;
            case 256:
                rectangle.setFill(Color.rgb(180, 60, 44, 0.8));
                break;
            case 512:
                rectangle.setFill(Color.rgb(180, 30, 44, 0.9));
                break;
            case 1024:
                rectangle.setFill(Color.rgb(250, 0, 44, 0.9));
                break;
            case 2048:
                rectangle.setFill(Color.rgb(250,0,0,0.9));
                break;
            default:
                rectangle.setFill(Color.rgb(0,0,0, 1));
                break;
        }
    }

    /**
     * Returns the x-coordinate (horizontal) of the cell.
     *
     * @return the x-coordinate (horizontal) of the cell
     */
    double getX() {
        return rectangle.getX();
    }

    /**
     * Returns the y-coordinate (vertical) of the cell.
     *
     * @return the y-coordinate (vertical) of the cell
     */
    double getY() {
        return rectangle.getY();
    }

    /**
     * Returns the given number displayed in the cell.
     *
     * @return the given number displayed in the cell
     */
    int getNumber() {
        return Integer.parseInt(textClass.getText());
    }

    /**
     * Returns the integer value of the cell.
     *
     * @return the integer value of the cell
     */
    private Text getTextClass() {
        return textClass;
    }
}
