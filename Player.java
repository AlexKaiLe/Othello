package Othello;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Player class is the super class of the ComputerPlayer and Human Player.
 * It contains methods that both classes can use and implement. The player class
 * mainly checks for valid moves, but it also has a bunch of helper methods that
 * are useful for its subclasses
 *
 */
public class Player {
	// declare instance variables
	private Piece[][] _piece;
	private OthelloSquare[][] _board;
	private Pane _pane;
	private Color _color;
	private int _check;
	private Referee _ref;
	private ArrayList<Move> _validMoves;
	private boolean _vis;
	private boolean _valid;
	private Board _gameBoard;
	private Label _label;

	/*
	 * Constructor of the Player class takes in various arguments that they assign
	 * to the instance variables
	 */
	public Player(Pane pane, Color color, Referee ref, Board gameBoard) {
		_gameBoard = gameBoard;
		_piece = _gameBoard.getPiece();
		_board = _gameBoard.getBoard();
		_pane = pane;
		_color = color;
		_ref = ref;
		_label = new Label();
		_label.setLayoutX(Constants.BUTTON_X);
		_label.setLayoutY(Constants.BUTTON_Y);
		// sets up font of the text
		_label.setFont(Font.font("Hoofler Text", 20));
		_label.setTextFill(Color.RED);
		_pane.getChildren().add(_label);
		_validMoves = new ArrayList<Move>();
	}
	/*
	 * This method tells other classes that if the player is a computer or not.
	 * The computer player overrides this method and sets it to true
	 */
	public boolean getIfComputer()
	{
		return false;
	}
	/*
	 * This method sets the visibility of the board
	 */
	public void setVis(boolean vis) {
		if (vis)
			_vis = true;
		else
			_vis = false;
		_gameBoard.setVis(_vis);
	}

	/*
	 * This getter method returns true if the board is visable and false if it is
	 * not
	 */
	public Boolean getVis() {
		return _vis;
	}

	/*
	 * This getter method returns all the valid moves
	 */
	public ArrayList<Move> getMoves() {
		return _validMoves;
	}

	/*
	 * This method returns the color of the player
	 */
	public Color getColor() {
		return _color;
	}

	/*
	 * This method returns true of there are valid moves for the player and false if
	 * not
	 */
	public boolean getValid() {
		return _valid;
	}

	/*
	 * This method returns the label
	 */
	public Label getLabel() {
		return _label;
	}

	/*
	 * Sub classes of the player classes use this method to find valid moves and
	 * sandwich
	 */
	public void checkPieces() {
		// if the list of valid moves has stuff, clear the list
		if (_validMoves != null)
			_validMoves.removeAll(_validMoves);
		// runs through the board and find a piece that equals the same color as the
		// player
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				if (_piece[i][j] != null) {
					if (_piece[i][j].getColor() == _color) {
						// if there is a piece with the same color, then check the sandwich
						this.checkSandwich(i, j);
					}
				}
			}
		// if there are no valid moves for the player, switch turns
		if (_check == 0) {
			_label.setText("No more valid moves...\nswitching players");
			_valid = false;
			_ref.changeTurns();
		}
		_check = 0;
	}

	/*
	 * This method is called by the checkPeices class and looks in all 8 directions
	 * to find a valid move
	 */
	public void checkSandwich(int row, int col) {
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if (i == -1 || i == 1 || j == -1 || j == 1) {
					// passes in the coordinate of the surrounding pieces to the method gray()
					this.gray(row, col, i, j);
				}
			}
	}

	/*
	 * This method finds a valid move and sets it to gray
	 */
	public void gray(int row, int col, int i, int j) {
		int xCorr = row + i;
		int yCorr = col + j;
		// if the piece is not equal to the boarder of the board
		if (this.andNotEqualBoard(xCorr, yCorr))
			// if the piece location is not null
			if (_piece[xCorr][yCorr] != null) {
				// keep on going until you reach an edge case
				while (_piece[xCorr][yCorr].getColor() != _color) {
					xCorr += i;
					yCorr += j;
					// if coordinates are in the on the edge of the board
					if (this.orEqualsBoard(xCorr, yCorr))
						break;
					if (_piece[xCorr][yCorr] == null) {
						// if the board is visible
						if (_vis)
							_board[xCorr][yCorr].setColor(Color.GRAY);
						_valid = true;// there are valid moves
						// create new valid move and add it to arrayList of validMoves
						Move temp = new Move(xCorr, yCorr, 0);
						if (_validMoves != null)
							_validMoves.add(temp);
						_check++;// add to the amount of available moves
						break;
					}
					// if the end piece color is the same, there is no sandwich
					if (_piece[xCorr][yCorr].getColor() == _color)
						break;
				}
			}
	}

	/*
	 * This method returns true if the coordinates are not in the border
	 */
	public boolean andNotEqualBoard(int xCorr, int yCorr) {
		if (xCorr != Constants.BOARDSIZE && yCorr != Constants.BOARDSIZE && xCorr != -1 && yCorr != -1)
			return true;
		else
			return false;
	}

	/*
	 * This method returns true if the coordinates are in the border row
	 */
	public boolean orEqualsBoard(int xCorr, int yCorr) {
		if (xCorr == Constants.BOARDSIZE || yCorr == Constants.BOARDSIZE || xCorr == -1 || yCorr == -1)
			return true;
		else
			return false;
	}

	/*
	 * The make move method needs to be overridden by the subclasses
	 */
	public void makeMove() {
	}
	/*
	 * The make move method needs to be overridden by the subclasses
	 */
	public void setColor(Color color)
	{
	}
}
