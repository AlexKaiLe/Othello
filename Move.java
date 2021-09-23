package Othello;

/**
 * The Move class consists of three properties, a x value, a y value
 * representing coordinate and a weight for the coordinates. This class has a
 * bunch of helper methods that allow other classes to
 *
 */
public class Move {
	// declare instance variables
	private int _x;
	private int _y;
	private int _value;

	/*
	 * The constructor of the Move class takes in 3 integers which it sets to the
	 * instance variables of _x, _y and _value
	 */
	public Move(int x, int y, int value) {
		_x = x;
		_y = y;
		_value = value;
	}
	/*
	 * Getter method that returns the x location
	 */
	public int getX() {
		return _x;
	}
	/*
	 * Getter method that returns the y location
	 */
	public int getY() {
		return _y;
	}
	/*
	 * Setter method that negates the value 
	 */
	public void negateValue() {
		_value = -_value;
	}
	/*
	 * Getter method that returns the value of the move
	 */
	public int getValue() {
		return _value;
	}
	/*
	 * Setter method that sets the value of the move
	 */
	public void setMove(int value) {
		_value = value;
	}
}
