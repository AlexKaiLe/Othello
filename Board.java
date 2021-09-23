package Othello;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This is the Board class. It has two constructors, one takes in a pane and the
 * other takes in double array of pieces (copy constructor). This class also
 * allows the boards logically be played on, but not graphically by setting the
 * vis to false. This class also has methods that allow the player to add pieces
 * and flip the pieces. It also sets up the initial 4 piece start board.
 *
 */
public class Board {
	// Instantiates 4 different private instance variables
	private OthelloSquare[][] _board;
	private Piece[][] _piece;
	private Pane _pane;
	private Boolean _vis;
	private boolean _valid;

	/*
	 * This is the Constructor that takes in a Pane. It assigns that pane to the
	 * instance variable _pane and the right after, it sets up the first 4 squares
	 * as well as create a new arrow of pieces.
	 * 
	 */
	public Board(Pane pane) {
		_pane = pane;
		_valid = false;
		_piece = new Piece[Constants.BOARDSIZE][Constants.BOARDSIZE];// create a new double array
		this.setUpSqaures();
	}

	/*
	 * This is the copy constructor that takes in an array of pieces. Because the
	 * Minimax uses this method, I set the vis to false and copy the pieces that
	 * were passed in to the instance variable _piece.
	 */
	public Board(Piece[][] pieces) {
		_vis = false;// set vis to false
		_piece = new Piece[Constants.BOARDSIZE][Constants.BOARDSIZE];// create a new double array
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				// copies the board
				if (pieces[i][j] != null) {
					_piece[i][j] = new Piece();
					_piece[i][j].setColor(pieces[i][j].getColor());
				}
			}
	}

	/*
	 * This is the setVis method that tells the program the make the boards visible
	 * or not
	 */
	public void setVis(Boolean vis) {
		if (vis)
			_vis = true;
		else
			_vis = false;
	}

	/*
	 * The add piece method adds a piece to the board and if it is a computer
	 * player, the vis is set to false so the piece doesn't get added.
	 */
	public void addPiece(int x, int y, Color color) {
		// making sure that the piece that we are filling in does not already have a
		// piece in it
		if (_piece[x][y] == null) {
			_piece[x][y] = new Piece();
			_piece[x][y].setXY(x, y);
			_piece[x][y].setColor(color);
			if (_vis)// if visible, then add piece graphically
				_pane.getChildren().add(_piece[x][y].getPiece());
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
	 * This method tells other classes if the game is over
	 */
	public boolean gameOver() {
		// checks through all the pieces and see if the entire array is full
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++)
				if (_piece[i][j] == null)
					return false;
		return true;
	}

	/*
	 * This method sees which player wins based on the color. It returns 0 for
	 * current player 1 for opponent player and 2 for a tie
	 */
	public int currentPlayer(Color color) {
		int current = 0;
		int opp = 0;
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++)
				if (_piece[i][j].getColor() == color)
					current++;
				else
					opp++;
		if (current > opp)
			return 0;
		else if (opp > current)
			return 1;
		else
			return 2; // tie
	}

	/*
	 * the minimax is slightly buggy and the following code is redundant and I
	 * couldn't get the minimax to work fully but I would have needed to transfer
	 * the checksandwich method to the board class
	 */

	/*
	 * This method takes in a coordinate and checks the surrounding area to see it
	 * it can flip a piece by passing new coordinates to the flipping method
	 */
	public void flipp(int row, int col, Color _color) {
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if (i == -1 || i == 1 || j == -1 || j == 1)
					this.flipping(row, col, i, j, _color);
			}
	}

	/*
	 * This method takes in the coordinates and checks in all eight directions to
	 * see it the player can flip the pieces
	 */
	public void flipping(int row, int col, int i, int j, Color _color) {
		int xCorr = row + i;
		int yCorr = col + j;
		if (this.andNotEqualBoard(xCorr, yCorr))
			// checks if there is a piece next to it
			if (_piece[xCorr][yCorr] != null) {
				// keep on going if the color is not the same as the player
				while (_piece[xCorr][yCorr].getColor() != _color) {
					xCorr += i;
					yCorr += j;
					if (this.orEqualsBoard(xCorr, yCorr))
						break;
					if (_piece[xCorr][yCorr] == null) {
						break;
					}
					// if there is a row that can be filled, pass it the the fillRow method
					if (_piece[xCorr][yCorr].getColor() == _color) {
						this.fillRow(row, col, i, j, _color);
						break;
					}
				}
			}
	}

	/*
	 * This method fills the row by doing almost the same thing as the flipping
	 * method, but instead, it fills the row with the color of the player
	 */
	public void fillRow(int row, int col, int i, int j, Color _color) {
		int xCorr = row + i;
		int yCorr = col + j;
		if (_piece[xCorr][yCorr] != null) {
			while (_piece[xCorr][yCorr].getColor() != _color) {
				if (_vis)
					// fills the pieces with the color of the player
					_piece[xCorr][yCorr].setColor(_color);
				xCorr += i;
				yCorr += j;
				if (_piece[xCorr][yCorr] == null) {
					break;
				}
				if (_piece[xCorr][yCorr].getColor() == _color)
					break;
			}
		}
	}

	/*
	 * This method return the double array of the board so other classes can access
	 * it
	 */
	public OthelloSquare[][] getBoard() {
		return _board;
	}

	/*
	 * This method return the double array of the pieces so other classes can access
	 * it
	 */
	public Piece[][] getPiece() {
		return _piece;
	}

	/*
	 * This method sets up the initial first 4 pieces that start the game.
	 */
	public void setUpPieces() {
		for (int i = 3; i <= 4; i++)
			for (int j = 3; j <= 4; j++) {
				Piece temp = new Piece();
				if (i % j == 0) {
					temp.setColor(Color.WHITE);
					_piece[i][j] = temp;
				} else {
					temp.setColor(Color.BLACK);
					_piece[i][j] = temp;
				}
				_piece[i][j].setXY(i, j);// sets the x and y location
				_pane.getChildren().add(temp.getPiece());// adds it graphically
			}
	}

	/*
	 * This method sets up the green Othello squares logically and graphically
	 */
	public void setUpSqaures() {
		// sets up the Othello square array
		_board = new OthelloSquare[Constants.BOARDSIZE][Constants.BOARDSIZE];
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				// creates new othello squares for every location
				OthelloSquare temp = new OthelloSquare();
				temp.setColor(Color.FORESTGREEN);
				temp.setBorder(Color.WHITE, 2);
				_board[i][j] = temp;
				_board[i][j].setXY(i * Constants.SQUARESIZE, j * Constants.SQUARESIZE);
				// adds the board pieces graphically
				_pane.getChildren().add(_board[i][j].getRect());
			}
	}

	/*
	 * Sub classes of the player classes use this method to find valid moves and
	 * sandwich
	 */
	public void checkPieces(Color _color) {
		// if the list of valid moves has stuff, clear the list
		// runs through the board and find a piece that equals the same color as the
		// player
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				if (_piece[i][j] != null) {
					if (_piece[i][j].getColor() == _color) {
						// if there is a piece with the same color, then check the sandwich
						this.checkSandwich(i, j, _color);
					}
				}
			}
	}

	/*
	 * This method is called by the checkPeices class and looks in all 8 directions
	 * to find a valid move
	 */
	public void checkSandwich(int row, int col, Color color) {
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if (i == -1 || i == 1 || j == -1 || j == 1) {
					// passes in the coordinate of the surrounding pieces to the method gray()
					this.gray(row, col, i, j, color);
				}
			}
	}

	/*
	 * This method finds a valid move and sets it to gray
	 */
	public void gray(int row, int col, int i, int j, Color _color) {
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
						_valid = true;// there are valid moves
						// create new valid move and add it to arrayList of validMoves
						Move temp = new Move(xCorr, yCorr, 0);
						break;
					}
					// if the end piece color is the same, there is no sandwich
					if (_piece[xCorr][yCorr].getColor() == _color)
						break;
				}
			}
	}

	public boolean getValid(Color color) {
		this.checkPieces(color);
		return _valid;
	}
}
