package Othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the  main class where your Othello game will start.
  * The main method of this application calls the App constructor.
  * This class allows the entire program to run by first creating a 
  * PaneOrganizer and making it show up graphically
  *
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
    	// Create top-level object, set up the scene, and show the stage here.
    	PaneOrganizer organizer = new PaneOrganizer();
    	Scene scene = new Scene(organizer.getroot());
    	stage.setScene(scene);
    	stage.setTitle("Othello");
    	stage.show();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
