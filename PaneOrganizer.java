package Othello;
/**
 * The PaneOrganizer creates majority of the graphics and instantiates a boarderPane,
 * a pane and the othello game itself. This class also create the quit button
 *
 */

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;

public class PaneOrganizer {
	// Declare instance variables
	private BorderPane _root;
	private Game _othello;

	/*
	 * Constructor of PaneOrganizer creates a new BordrPane, pane, game and calls on
	 * a method to set up the quit button
	 */
	public PaneOrganizer() {
		_root = new BorderPane();
		_root.setStyle("-fx-background-color: lightblue");//set color of root
		_root.setPrefSize(Constants.PANEX, Constants.PANEY);//set size
		Pane pane = new Pane();//new Pane
		_othello = new Game(pane, _othello);//new game
		_root.setTop(_othello.getPane());
		//sets up quit button
		this.quitButton();
	}
	/*
	 * This method sets up the quit button on the game
	 */
	public void quitButton() {
		HBox buttons = new HBox();
		_root.setCenter(buttons);
		buttons.setAlignment(Pos.CENTER);
		Button quit = new Button("Quit");
		quit.setOnAction(new quit());
		Ellipse shape = new Ellipse(30, 50);
		quit.setShape(shape);
		quit.setTextFill(Color.RED);
		quit.setFont(Font.font("Hoefler Text", 14));
		buttons.getChildren().add(quit);
	}
	/*
	 * The private class quit uses an ActionEvent to quit the program
	 */
	private class quit implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// method the quits the program
			Platform.exit();
		}
	}
	/*
	 * The method getRoot is a helper method that returns the root
	 */
	public BorderPane getroot() {
		return _root;
	}

}
