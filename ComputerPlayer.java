package Othello;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * The computer player class extends the player class and therefore has access
 * to all its methods. The computer player has a method that makes a move based
 * on a minimax. Because the minimax creates many copy boards, the
 * computerPlayer sets vis to false when creating a new board. This class also
 * creates weights for different moves based on how good it is. The minimax uses
 * this to find the best move possible
 *
 */
public class ComputerPlayer extends Player {
	// Instantiates various instance variables
	private Piece[][] _piece;
	private Referee _ref;
	private int[][] _scoreBoard;
	private Board _gameBoard;
	private int _level;

	/*
	 * This is the constructor that takes in various parameters and pass them to its
	 * superclass. It also assigns these arguments to the instance variables so the
	 * other methods also have access to them. Finally, it sets up the scoreboard so
	 * the computer player knows about the weights
	 */
	public ComputerPlayer(Pane pane, Color color, Referee ref, Board gameBoard, int level) {
		super(pane, color, ref, gameBoard);
		_ref = ref;
		_gameBoard = gameBoard;
		_piece = _gameBoard.getPiece();
		_level = level;
		_scoreBoard = new int[Constants.BOARDSIZE][Constants.BOARDSIZE];
		// creating the scoreBoard
		this.setUpScoreBoard();
	}

	/*
	 * This method allows the player to make a move. First, it sets the visible to
	 * false because it's going to create many copy boards. It is also going to
	 * check the pieces to find all the valid moves. Once it does so, the minimax
	 * can chose form this list of possible moves and find the best one. Finally, it
	 * returns a best move which the computer can make.
	 */
	@Override
	public void makeMove() {
		super.setVis(false);
		super.checkPieces();
		Move move = this.getBestMove(_gameBoard, _level, _ref.getPlayer().getColor());
		super.setVis(true);
		// Adds the piece to the board graphically and logically so that it can play
		_gameBoard.addPiece(move.getX(), move.getY(), _ref.getPlayer().getColor());
		_gameBoard.flipp(move.getX(), move.getY(), _ref.getPlayer().getColor());
		_ref.getOpp().getLabel().setText("");
		// switches turns
		_ref.play();
	}

	/*
	 * This method sets up the weights on the scoreboard by using the 3d array that
	 * was created in the Constants class
	 */
	public void setUpScoreBoard() {
		for (int i = 0; i < Constants.SCORE.length; i++) {
			for (int j = 0; j < Constants.SCORE[i].length; j++)
				// setting values to the scores
				this.setScores(Constants.SCORE[i][0][0], Constants.SCORE[i][0][1], Constants.SCORE[i][0][2]);
		}
	}

	/*
	 * This method adds all the scores to the score board by taking in one
	 * coordinate, but adding the value to 4 places
	 */
	public void setScores(int x1, int y1, int score) {
		int x2 = Constants.BOARDSIZE - 1 - x1;
		int y2 = Constants.BOARDSIZE - 1 - y1;
		_scoreBoard[x1][y1] = score;
		_scoreBoard[x1][y2] = score;
		_scoreBoard[x2][y1] = score;
		_scoreBoard[x2][y2] = score;
	}

	/*
	 * The evaluateBoard method takes in a color and checks to see what the value of
	 * the move will be if you subtract the other person's score from the current
	 * one. It returns this new value based on the color that you input
	 */
	public int evaluateBoard(Color color) {
		// local variables
		int currentPlayerScore = 0;
		int otherPlayerScore = 0;
		for (int i = 0; i < Constants.BOARDSIZE - 1; i++)
			for (int j = 0; j < Constants.BOARDSIZE - 1; j++) {
				if (_gameBoard.getPiece()[i][j] != null) {
					if (_gameBoard.getPiece()[i][j].getColor() == color)
						currentPlayerScore += _scoreBoard[i][j];
					else
						otherPlayerScore += _scoreBoard[i][j];
				}
			}
		// Subtracting scores to find the true score
		int score = currentPlayerScore - otherPlayerScore;
		return score;
	}
	@Override
	public boolean getIfComputer() {
		return true;
	}
	/*
	 * This getBestMove method is the heart of this class where the player tries to
	 * fined the best move that will ensure its victory
	 */
	public Move getBestMove(Board gameBoard, int intelligence, Color currentColor) {
		Piece[][] board = gameBoard.getPiece();
		Color oppColor;
		if (currentColor == Color.WHITE)
			oppColor = Color.BLACK;
		else
			oppColor = Color.WHITE;
		// This chunk of code are edge cases
		if (gameBoard.gameOver() && gameBoard.currentPlayer(currentColor)==0)
			return new Move(0, 0, 1000);
		else if (gameBoard.gameOver() && gameBoard.currentPlayer(currentColor)==1)
			return new Move(0, 0, -1000);
		else if (gameBoard.gameOver() && gameBoard.currentPlayer(currentColor)==2)
			return new Move(0, 0, 0);
		if (super.getValid() == false) //gameBoard.currentPlayer(currentColor)
		{
			if (intelligence == 1)
				return new Move(0, 0, -1000);
			else {
				Move move = getBestMove(gameBoard, intelligence - 1, oppColor);
				move.negateValue();
				return move;
			}
		}

		// create new variables that are essential to the minimax program
		int value = 0;
		Move bestMove = new Move(0, 0, -1000);
		// creates a new arrayList of possible moves
		ArrayList<Move> moveList = new ArrayList<Move>();
		super.checkPieces(); // checks all the valid moves
		moveList = super.getMoves();// adds all the valid moves to the arrayList

		// sets the value of each move the the list of possible moves
		for (int i = 0; i < moveList.size(); i++) {
			int settingValue = _scoreBoard[moveList.get(i).getX()][moveList.get(i).getY()];
			moveList.get(i).setMove(settingValue);
		}

		// heart of copy constructor that runs through all possible moves to find the
		// best one
		for (int i = 0; i < moveList.size(); i++) {
			Board copy = new Board(board);
			copy.addPiece(moveList.get(i).getX(), moveList.get(i).getY(), currentColor);
			copy.flipp(moveList.get(i).getX(), moveList.get(i).getY(), currentColor);
			if (intelligence == 1) {
				value = this.evaluateBoard(currentColor);
			} else {
				// get the pieces 2d array and pass that into the recursion
				Move move = getBestMove(copy, intelligence - 1, oppColor);
				move.negateValue();
				value = move.getValue();
			}
			// if this move has a higher value than previous moves, than make the best move
			// to this move
			if (value >= bestMove.getValue()) {
				bestMove = moveList.get(i);
			}
		}
		return bestMove;
	}
}
