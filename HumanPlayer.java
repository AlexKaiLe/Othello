package Othello;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * 
 * The Human player allows the user to use mouse inputs to select square to
 * play. It also shows the user available sandwiches by highlighting the square
 * in gray
 * 
 */
public class HumanPlayer extends Player {
	// declares instance variables of various types
	private Color _color;
	private MouseHandler _handle;
	private Pane _pane;
	private boolean _clicked;
	private Referee _ref;
	private OthelloSquare[][] _board;
	private Piece[][] _piece;
	private Board _gameBoard;
	private Color _boardColor;

	/*
	 * The constructor of the humanPlayer takes in various arguments that it assigns
	 * to the instance variables. It also instantiates a new Mouse handler so that
	 * it can take read mouse inputs
	 */
	public HumanPlayer(Piece[][] piece, OthelloSquare[][] board, Pane pane, Color color, Referee ref, Board gameBoard, Color boardColor) {
		super(pane, color, ref, gameBoard);
		// allows the board to be visable
		super.setVis(true);
		_pane = pane;
		_color = color;
		_boardColor = boardColor;
		_clicked = false;
		_piece = piece;
		_ref = ref;
		_gameBoard = gameBoard;
		_board = board;
		_piece = piece;
		_handle = new MouseHandler();
	}
	public void setColor(Color color) {
		_boardColor = color;
	}
	/*
	 * The makeMove method sets visible to true, checks the pieces and allows the
	 * player to click on the board
	 */
	@Override
	public void makeMove() {
		super.setVis(true);// Visible
		this.checkPieces();// checking pieces
		_pane.setOnMouseClicked(_handle);// allows mouse to be read
	}

	/*
	 * The private class MouseHandler finds the X and Y input of the mouse and if it
	 * is valid, then it adds a piece to the board and finally it switches turns
	 */
	private class MouseHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			//gets mouse locations
			int clickX = (int) event.getSceneX() / 75;
			int clickY = (int) event.getSceneY() / 75;
			//if within the board
			if (clickX >= 0 && clickX <= Constants.BOARDSIZE - 1 && clickY >= 0 && clickY <= Constants.BOARDSIZE - 1)
				if (_board[clickX][clickY].getRect().getFill() == Color.GRAY) {
					_clicked = true;//set clicked to true
					//add the piece to the clicked location
					_gameBoard.addPiece(clickX, clickY, _ref.getPlayer().getColor());
					//makes all board turn back to green
					for (int i = 0; i < Constants.BOARDSIZE; i++)
						for (int j = 0; j < Constants.BOARDSIZE; j++)
							if (_board[i][j].getRect().getFill() == Color.GRAY)
								_board[i][j].getRect().setFill(_boardColor);
					//flip all the pieces
					_gameBoard.flipp(clickX, clickY, _ref.getPlayer().getColor());
					_pane.setOnMouseClicked(null);//make it so there is no more mouse inputs 
					_ref.getOpp().getLabel().setText("");
					_ref.play();//switch turns
				}
		}
	}
	/*
	 * Method returns true if the player has clicked
	 */
	public boolean getClicked() {
		return _clicked;
	}
}
