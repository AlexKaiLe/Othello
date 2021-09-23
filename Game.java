package Othello;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
 * The Game class creates the over all structure of the graphics by creating a
 * new board, a control class as well as a keyhandler that can quit the program
 *
 */
public class Game {
	// Declare instance variables
	private Pane _pane;
	private Board _board;
	private Label _label;
	private Game _othello;
	private Referee ref;
	private Color _color;
	private Controls _control;

	/*
	 * The constructor for the game class takes in a Pane and the game othello, and
	 * then it assigns to the instance variables from above.
	 */
	public Game(Pane pane, Game othello) {
		_pane = pane;
		_othello = othello;
		_pane.setStyle("-fx-background-color: cyan");// sets the background to cyan
		_pane.setPrefSize(Constants.GAMEX, Constants.GAMEY);// sets up size of pane
		_pane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());// creates the keyhandler
		_pane.setFocusTraversable(true);
		_board = new Board(_pane);// creates a new board
		_board.setUpPieces();
		_label = new Label();// creates a new label
		// creates a new control
		_color = Color.FORESTGREEN;
		_control = new Controls(_pane, _board, ref, _othello, _color);
	}

	/*
	 * This method returns a pane so that it other classes can access it
	 */
	public Pane getPane() {
		return _pane;
	}

	/*
	 * This method creates random colors so that the user can change the board color
	 */
	public void changeColor() {
		int red = randomInt(50, 200); // setting red to a random number
		int green = randomInt(50, 200); // setting green to a random number
		int blue = randomInt(50, 200); // setting blue to a random number
		// Creates a color based on the random numbers generated
		_color = Color.rgb(red, green, blue);
		for (int i = 0; i < Constants.BOARDSIZE; i++)
			for (int j = 0; j < Constants.BOARDSIZE; j++) {
				if (_board.getBoard()[i][j].getRect().getFill() != Color.GRAY)
					_board.getBoard()[i][j].setColor(_color);
			}
		//sets to random color to the back ground color so it doesn't flip back to a green square
		_control.setColor(_color);
	}

	/*
	 * This method returns a value between the to arguments that are passed in
	 */
	public int randomInt(int low, int high) {
		return low + (int) (Math.random() * (high - low + 1));
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
			// quits the program
			case Q:
				System.exit(0);
				break;
			// changes the board color
			case B:
				Game.this.changeColor();
				break;
			// if presses a key not available, then print "invalid key"
			default:
				System.out.print("Invalid key\n");
				break;
			}
			// consumes the event so it doesn't repeat
			e.consume();
		}
	}

}
