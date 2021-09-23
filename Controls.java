package Othello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This is the controls class and it not only sets up all the buttons, but also
 * creates a referee and all the players. This class has multiple eventHandlers
 * and radio buttons because it need to listen to input from the user
 */
public class Controls {
	// Declare various instance variables
	private OthelloSquare[][] _board;
	private Piece[][] _piece;
	private Pane _pane;
	private Board _gameBoard;
	private ToggleGroup _player1;
	private ToggleGroup _player2;
	private Referee _ref;
	// _setting makes sure you don't get error message when you click on apply
	// setting when in middle of game
	private boolean _setting;
	private RadioButton _human1;
	private RadioButton _computerOne1;
	private RadioButton _computerOne2;
	private RadioButton _computerOne3;
	private RadioButton _human2;
	private RadioButton _computerTwo1;
	private RadioButton _computerTwo2;
	private RadioButton _computerTwo3;
	private Game _othello;
	private Color _color;
	private Player _p1;
	private Player _p2;

	/*
	 * The constructor for the controls class takes in a variety of arguments which
	 * it assigns to instance variables that I previously created
	 */
	public Controls(Pane pane, Board gameBoard, Referee ref, Game othello, Color color) {
		_gameBoard = gameBoard;
		_piece = _gameBoard.getPiece();
		_board = _gameBoard.getBoard();
		_color = color;
		_pane = pane;
		_ref = ref;
		_othello = othello;
		_setting = false;
		// creates the labels and buttons
		this.createLabelsAndButtons();

	}

	/*
	 * This method creates all the buttons and adds them to the pane so we can
	 * graphically see it
	 */
	public void createLabelsAndButtons() {
		// create toggle groups
		_player1 = new ToggleGroup();
		_player2 = new ToggleGroup();
		// create holder boxes
		HBox playerHolder = new HBox();
		HBox applySettings = new HBox();
		VBox holder1 = new VBox();
		VBox holder2 = new VBox();
		// create new labels
		Label _p1 = new Label("(Black) Player 1");
		Label _p2 = new Label("(white) Player 2");
		Button settings = new Button("Apply Settings");
		// create new action listeners
		settings.setOnAction(new NewPlayers());
		Button reset = new Button("Reset");
		reset.setOnAction(new Reset());
		// set the location and and it to the pane
		applySettings.setLayoutX(Constants.BUTTON_X);
		applySettings.setLayoutY(Constants.BUTTON_Y);
		applySettings.setSpacing(Constants.BUTTON_SPACE);
		applySettings.getChildren().addAll(settings, reset);
		// buttons for player1
		_human1 = new RadioButton("Human");
		_computerOne1 = new RadioButton("Computer 1");
		_computerOne2 = new RadioButton("Computer 2");
		_computerOne3 = new RadioButton("Computer 3");
		_human1.setToggleGroup(_player1);
		_computerOne1.setToggleGroup(_player1);
		_computerOne2.setToggleGroup(_player1);
		_computerOne3.setToggleGroup(_player1);
		holder1.getChildren().addAll(_p1, _human1, _computerOne1, _computerOne2, _computerOne3);
		// buttons for player2
		_human2 = new RadioButton("Human");
		_computerTwo1 = new RadioButton("Computer 1");
		_computerTwo2 = new RadioButton("Computer 2");
		_computerTwo3 = new RadioButton("Computer 3");
		_human2.setToggleGroup(_player2);
		_computerTwo1.setToggleGroup(_player2);
		_computerTwo2.setToggleGroup(_player2);
		_computerTwo3.setToggleGroup(_player2);
		holder2.getChildren().addAll(_p2, _human2, _computerTwo1, _computerTwo2, _computerTwo3);
		// add both VBoxs to a HBox
		playerHolder.getChildren().addAll(holder1, holder2);
		// setting for player buttons
		playerHolder.setSpacing(Constants.PLAYER_SPACE);
		playerHolder.setLayoutX(Constants.PLAYER_X);
		playerHolder.setLayoutY(Constants.PLAYER_Y);
		holder1.setSpacing(Constants.PLAYER_BOX_SPACE);
		holder2.setSpacing(Constants.PLAYER_BOX_SPACE);
		_pane.getChildren().addAll(playerHolder, applySettings);
		// present the toggle to human1 and human2
		_player1.selectToggle(_human1);
		_player2.selectToggle(_human2);
		_pane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());// creates the keyhandler
		_pane.setFocusTraversable(true);
	}

	/*
	 * This private class sets up the players based on what the user toggles
	 */
	private class NewPlayers implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// tells program that user has clicked on the setting icon
			_setting = true;
			Controls.this.setPlayer();
		}
	}

	/*
	 * This method sets the background color of the stage when it turns gray into
	 * the random color
	 */
	public void setColor(Color color) {
		_color = color;
		if (_p1 != null && _p2 != null) {
			_p1.setColor(color);
			_p2.setColor(color);
		}
	}

	/*
	 * This class is called upon by the NewPlayers class to set up the players
	 */
	public void setPlayer() {
		boolean go = true;
		// initialize dummy players 1 and 2 so the players can be assigned to this
		// variable without
		// worrying if it was a computer or a player
		_p1 = new Player(_pane, null, _ref, _gameBoard);
		_p2 = new Player(_pane, null, _ref, _gameBoard);
		if (_ref != null) {
			// if there is already a game going on and if it not over, don't add new players
			if (_ref.gameOver()) {
				go = false;
			}
		} // only add new players if the previous game is over
		else if (go = true) {
			_ref = new Referee(_gameBoard.getPiece(), _gameBoard.getBoard(), _pane, _gameBoard, this);
			if (_player1.getSelectedToggle() == _human1)
				_p1 = new HumanPlayer(_piece, _board, _pane, Color.BLACK, _ref, _gameBoard, _color);
			else if (_player1.getSelectedToggle() == _computerOne1)
				_p1 = new ComputerPlayer(_pane, Color.BLACK, _ref, _gameBoard, 1);
			else if (_player1.getSelectedToggle() == _computerOne2)
				_p1 = new ComputerPlayer(_pane, Color.BLACK, _ref, _gameBoard, 2);
			else if (_player1.getSelectedToggle() == _computerOne3)
				_p1 = new ComputerPlayer(_pane, Color.BLACK, _ref, _gameBoard, 3);

			if (_player2.getSelectedToggle() == _human2)
				_p2 = new HumanPlayer(_piece, _board, _pane, Color.WHITE, _ref, _gameBoard, _color);
			else if (_player2.getSelectedToggle() == _computerTwo1)
				_p2 = new ComputerPlayer(_pane, Color.WHITE, _ref, _gameBoard, 1);
			else if (_player2.getSelectedToggle() == _computerTwo2)
				_p2 = new ComputerPlayer(_pane, Color.WHITE, _ref, _gameBoard, 2);
			else if (_player2.getSelectedToggle() == _computerTwo3)
				_p2 = new ComputerPlayer(_pane, Color.WHITE, _ref, _gameBoard, 3);
			// sets the players in the referee class
			_ref.setPlayer1(_p1);
			_ref.setPlayer2(_p2);
			_ref.getPlayer().makeMove();
//			_setting = false;
		}
	}

	/*
	 * The reset private class resets the entire game by assigning _othello to a new
	 * game
	 */
	private class Reset implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// calls on the restBoard method to reset the board
			Controls.this.restBoard();
		}
	}

	public void restBoard() {
		// if the setting button as been pressed, then you we can do the following code
		if (_ref != null) {
			// cases that need to be met so that the user can reset the game
			if ((_ref.getPlayer().getIfComputer() && _ref.getOpp().getIfComputer() && _ref.gameOver())
					|| (_ref.getPlayer().getIfComputer() == false && _ref.getOpp().getIfComputer() && _setting == true)
					|| (_ref.getPlayer().getIfComputer() && _ref.getOpp().getIfComputer() == false
							&& _setting == true)) {
				_othello = new Game(_pane, _othello);
				_ref.getLabel().setText("");
				_ref.getLabel1().setText("");
				_setting = false;
			}
//			if (_ref.getPlayer().getIfComputer() == false && _ref.getOpp().getIfComputer() == false&&_setting == true) {
//					_othello = new Game(_pane, _othello);
//					_ref.getLabel().setText("");
//					_ref.getLabel1().setText("");
//					_setting = false;
//				}
			// set the setting to false so that we can't click on this button until we
			// finish the game
		}
	}

	/*
	 * This private class allows the user to have key inputs to quit and change the
	 * board color
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {
		/*
		 * The handle method is overridden and contains instructions when specific keys
		 * are hit
		 */
		@Override
		public void handle(KeyEvent e) {
			// Receives the code from the event
			KeyCode key = e.getCode();
			switch (key) {
			case A:
				_player1.selectToggle(_human1);
				break;
			case S:
				_player1.selectToggle(_computerOne1);
				break;
			case D:
				_player1.selectToggle(_computerOne2);
				break;
			case F:
				_player1.selectToggle(_computerOne3);
				break;
			case Z:
				_player2.selectToggle(_human2);
				break;
			case X:
				_player2.selectToggle(_computerTwo1);
				break;
			case C:
				_player2.selectToggle(_computerTwo2);
				break;
			case V:
				_player2.selectToggle(_computerTwo3);
				break;
			case ENTER:
				_setting = true;
				Controls.this.setPlayer();
				break;
			case R:
				Controls.this.restBoard();
				break;
			default:
				System.out.print("Invalid key\n");
				break;
			}
			// consumes the event so it doesn't repeat
			e.consume();
		}
	}
}
