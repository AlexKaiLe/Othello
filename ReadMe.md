# ReadMe Othello

This is my final project Othello where the player can select different combinations of player 1 and player 2. 

Extra credit: 
-Change color of board when press B 
-Change the players by pressing keys
	- Player one changes with
		-A: human
		-S: computer level 1
		-D: computer level 2
		-F: computer level 3
	- Player two changes with
		-Z: human
		-X: computer level 1
		-C: computer level 2
		-V: computer level 3
-You can reset the game by pressing R and hit enter to apply setting
-Display score of score as game progresses 

Overview: The Othello project has 13 classes that run the program. 
	-The App class is the top most class and it creates the a game class
	-The Game class creates a control class and sets up majority of the instance variable used throughout 
	the paneOrganizer. It also contains the board class and a quit key.
	-The paneOrganizer creates the pane on which the entire game lives in. It also creates a new game which allows
	the rest of the program to run.  
	-The board class creates 2 2D arrays and has methods that can add pieces to the board, set boards to visible, 
	flipps the pieces, creates the array of pieces and another array of othelloSquares
	-The piece class creates all the circle pieces and the board class can set/get the location and color of the pieces
	-Similar to the piece class, the Othello square is also used by the board class. The board class uses the othelloSquare
	class to create the green grid that you see. We can set the color of the square and we can set the locations as well.
	-The control class contains all the buttons and creates the referee class. It also contains two 
	private classes that handle inputs from the user to set up the players or reset the board. The controls class
	also creates all the players.
	- The player class is a super class that contains methods that both the human and computer player use. The human
	and computer player classes both extends the player class. Therefore the Controls class can just create a player 
	without stating it being a human or a computer.
	- The HumanPlayer class is able to highlight valid moves, and take mouse inputs to place a valid move 
	- The ComputerPlayer class uses a minimax to find the best move to make and then places that move. It also sets up
	the score board so the ComputerPlayer can figure out what move is the best 
	- The referee class handles the game logic like the turns between the two players, states when the game is over
	and gets the score from the players. 
	- The Move class is used by the minimax to figure out what types of moves it can make. The move class has three 
	properties: x coordinate, y coordinate and a value associated with that move. This class also has getter and setters 
	 that allow the minimax to manipulate the moves
	- The constants class just contains all the variables that don't change value throughout the program and it also
	creates the score board for the minimax

Design Choices;
	-For my checksandwich, I did not return a boolean, but rather just turn the squares right away
	-I also created a superclass called player that has methods that the human and the computer can utilize.
	-The referee and the Controls class need know know about eachother. The referee needs to know about the controls 
	after it has been created, but the controls also need to pass in a referee into the players, which is why the referee
	is created inside the Controls class.
