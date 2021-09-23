package Othello;
/**
 * The OthelloSquare class is a class used by the board class to create the board out
 * of squares. This class has various helper methods that allow other classes to manipulate 
 * and receive information about the square
 *
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OthelloSquare {
	//Instance variable of type Rectangle
	private Rectangle _rect;
	/*
	 * The constructor basically just creates a square of the correct size 
	 */
	public OthelloSquare() {
		_rect = new Rectangle(Constants.SQUARESIZE, Constants.SQUARESIZE);
	}
	/*
	 * Setter method that sets the fill of the rectangle
	 */
	public void setColor(Color color) {
		_rect.setFill(color);
	}
	/*
	 * Setter method that set the color and stroke of the border
	 */
	public void setBorder(Color color, int stroke) {
		_rect.setStroke(color);
		_rect.setStrokeWidth(stroke);
	}
	/*
	 * Setter method that sets the X and Y values
	 */
	public void setXY(int x, int y) {
		_rect.setX(x);
		_rect.setY(y);
	}
	/*
	 * Getter method that return the rectangle
	 */
	public Rectangle getRect() {
		return _rect;
	}
	/*
	 * Getter method that returns the X location of the rectangle
	 */
	public int getX() {
		return (int) _rect.getX();
	}
	/*
	 * Getter method that returns the Y location of the rectangle
	 */
	public int getY() {
		return (int) _rect.getY();
	}
}
