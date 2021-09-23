package Othello;
/**
 * The piece class is a class used by the board class to create an array of pieces out
 * of ellipses. This class has various helper methods that allow other classes to manipulate 
 * and receive information about the peices
 *
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Piece {
	//instance of Ellipse
	private Ellipse _piece;
	/*
	 * Constructor of the Piece class just creates a circle based on the dimension of the constants class
	 */
	public Piece() {
		_piece = new Ellipse(Constants.PIECESIZE,Constants.PIECESIZE);
	}
	/*
	 * Setter method that fills the pieces with a color
	 */
	public void setColor(Color color) {
		_piece.setFill(color);
	}
	/*
	 * Getter method that gets the color of the piece
	 */
	public Color getColor() {
		return (Color) _piece.getFill();
	}
	/*
	 * Setter method that sets the x and y values
	 */
	public void setXY(double x, double y) {
		_piece.setCenterX(x*Constants.SQUARESIZE+Constants.SQUARESIZE/2);
		_piece.setCenterY(y*Constants.SQUARESIZE+Constants.SQUARESIZE/2);
		}
	/*
	 * getter method that returns the x location of the ellipse
	 */
	public int getX()
	{
		return (int)_piece.getCenterX();
	}
	/*
	 * getter method that returns the y location of the ellipse
	 */
	public int getY()
	{
		return (int)_piece.getCenterY();
	}
	/*
	 * getter method that returns the the ellipse
	 */
	public Ellipse getPiece() {
		return _piece;
	}
}
