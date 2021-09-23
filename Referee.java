package Othello;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The Referee class handles the player logic including switching turns, getting
 * the score, knowing when game over is and storing the players. This class also
 * updates the score and displays the numbers visually. Finally, it implements a 
 * time handler so that the computer player has some delay between player's making
 * moves
 */
public class Referee {
	//Declare instance variable of different types
	private OthelloSquare[][] _board;
	private Piece[][] _piece;
	private Pane _pane;
	private Player _currentPlayer;
	private Timeline _tl;
	private Player _player1;
	private Player _player2;
	private Label _label;
	private Label _label1;
	private int _white;
	private int _black;
	private int _playerWin;
	/*
	 * The constructor of the referee class in take various argument that it assigns to the instance
	 * variables. The constructor also creates new labels to be displayed on the screen so that the
	 * players can see their score
	 */
	public Referee(Piece[][] piece, OthelloSquare[][] board, Pane pane, Board gameBoard, Controls control) {
		_piece = piece;
		_board = board;
		_pane = pane;
		_label = new Label();
		_label1 = new Label();
		_label.setFont(Font.font("Hoofler Text", 25));
		_label1.setFont(Font.font("Hoofler Text", 25));
		VBox holder = new VBox();
		holder.setLayoutX(650);
		holder.setLayoutY(400);
		holder.getChildren().addAll(_label, _label1);
		_pane.getChildren().add(holder);
		//creating the timeLine 
		this.addTime();
	}
	/*
	 * This is a getter method that allows other classes to access the current player
	 */
	public Player getPlayer() {
		return _currentPlayer;
	}
	/*
	 * This is a setter method that allows other classes to set _player1 as well as the currentPlayer
	 */
	public void setPlayer1(Player player) {
		_player1 = player;
		_currentPlayer = _player1;
	}
	/*
	 * This is a setter method that allows other classes to set _player2
	 */
	public void setPlayer2(Player player) {
		_player2 = player;
	}
	/*
	 * This is a getter method that allows other classes to access the label
	 */
	public Label getLabel() {
		return _label;
	}
	/*
	 * This is a getter method that allows other classes to access the label1
	 */
	public Label getLabel1() {
		return _label1;
	}
	/*
	 * This method tells other classes if the current player has won 
	 */
	public boolean currentPlayerWin() {
		if ((_playerWin == 0 && _currentPlayer.getColor() == Color.WHITE)
				|| (_playerWin == 1 && _currentPlayer.getColor() == Color.BLACK))
			return true;
		return false;
	}
	/*
	 * The method returns the player that wins. However, if there is a tie, this method returns null
	 */
	public Player getScore() {
		//sets up/clears the counters
		_white = 0;
		_black = 0;
		//counts the pieces on the board
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				if (_piece[i][j] != null) {
					if (_piece[i][j].getPiece().getFill() == Color.WHITE)
						_white++;
					if (_piece[i][j].getPiece().getFill() == Color.BLACK)
						_black++;
				}
			}
		//update the label to the text below
		_label.setText("Player 1 Score: " + _black + "\nPlayer 2 Score: " + _white);
		//return the winner if white wins
		if (_white > _black) {
			if (_player1.getColor() == Color.WHITE)
				return _player1;
			else
				return _player2;
			
		}//return the winner if black wins 
		else if (_black > _white)
			if (_player1.getColor() == Color.BLACK)
				return _player1;
			else
				return _player2;
		else
			return null;//returns null if there is a tie

	}
	/*
	 * If the game is over, returns true and prints out the final score.
	 * If not, returns false and the game keeps going
	 */
	public boolean gameOver() {
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++)
				if (_piece[i][j] == null) {
					return false;
				}
		//Update text to show final score
		this.gameOverLabel();
		return true;
	}
	/*
	 * This method displays the text at the end of the game. It shows the score and who wins
	 */
	public void gameOverLabel() {
		//Displays the winner player name
		if (this.getScore() == _player1)
			_label1.setText("(Black): Player 1 wins");
		else if (this.getScore() == _player2)
			_label1.setText("(White): Player 2 wins");
		else
			_label1.setText("It's a tie");
		//Displays the score of both players
		if (this.getScore() != null) {
			if (this.getScore().getColor() == Color.WHITE) {
				_label.setText("Black loses: " + _black + "\nWhite wins: " + _white);
				_playerWin = 0;
			} else if (this.getScore().getColor() == Color.BLACK) {
				_label.setText("Black wins: " + _black + "\nWhite loses: " + _white);
				_playerWin = 1;
			} else {
				_label.setText("Black ties: " + _black + "\nWhite ties: " + _white);
			}
		}
	}
	/*
	 * This method switches between the players, but before it does so, it updates the score
	 */
	public void changeTurns() {
		//updates the score
		this.getScore();
		//checks if the game is over and if not, switches players
		if (!this.gameOver()) {
			if (_currentPlayer == _player1)
				_currentPlayer = _player2;
			else
				_currentPlayer = _player1;
			//calls on the player to make a move
			_currentPlayer.makeMove();
		}
	}
	/*
	 * This method checks if there are anyValidMoves for both players and returns true if there is
	 */
	public boolean anyValidMovesTotal() {
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				if (_board[i][j].getRect().getFill() == Color.GRAY)
					return true;
			}
		return false;
	}
	/*
	 * This method allows other classes to play the game
	 */
	public void play() {
		_tl.play();
	}
	/*
	 * This method returns the player that is not the current player
	 */
	public Player getOpp() {
		if (_currentPlayer == _player1)
			return _player2;
		else
			return _player1;
	}
	/*
	 * This method sets up the timeLine and allows there to be a delay when the player makes a move 
	 */
	public void addTime() {
		KeyFrame kf = new KeyFrame(Duration.millis(300), new time());
		_tl = new Timeline(kf);
		_tl.setCycleCount(Animation.INDEFINITE);
	}
	/*
	 * The time private class create a Action event that pauses the timeLine and waits for the other 
	 * player to move. Once the play calls on the TimeLine to be played, it switches turns.
	 */
	private class time implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			_tl.pause();
			Referee.this.changeTurns();
		}

	}
}
