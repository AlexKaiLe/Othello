package Othello;
/** This is the Constants class and contains all the constants 
 * Other classes can call and access these constants, but they are unable to change their value
 */
public class Constants {
	//Constants of type int
	public static final int PANEX = 1000;
	public static final int PANEY = 700;
	public static final int GAMEX = 600;
	public static final int GAMEY = 600;
	public static final int BOARDSIZE = 8;
	public static final int SQUARESIZE = 75;
	public static final int PIECESIZE = 35;
	public static final int BUTTON_X = 650;
	public static final int BUTTON_Y = 300;
	public static final int BUTTON_SPACE = 50;
	public static final int PLAYER_X = 650;
	public static final int PLAYER_Y = 100;
	public static final int PLAYER_SPACE = 40;
	public static final int PLAYER_BOX_SPACE = 20;

	//board values/weights
	public static final int[] SCORE_200= {0,7,200};
	public static final int[] SCORE_100= {1,1,100};
	public static final int[] SCORE_2_1= {2,2,2};
	public static final int[] SCORE_2_2= {3,3,2};	
	public static final int[] SCORE_2_3= {2,3,2};
	public static final int[] SCORE_2_4= {3,2,2};	
	public static final int[] SCORE_NEG_70_1= {1,0,70};
	public static final int[] SCORE_NEG_70_2= {0,6,70};
	public static final int[] SCORE_30_1= {0,2,30};
	public static final int[] SCORE_30_2= {5,7,30};
	public static final int[] SCORE_25_1= {0,3,25};
	public static final int[] SCORE_25_2= {4,0,25};
	public static final int[] SCORE_NEG_10_1= {1,2,-10};
	public static final int[] SCORE_NEG_10_2= {1,3,-10};
	public static final int[] SCORE_NEG_10_3= {2,1,-10};
	public static final int[] SCORE_NEG_10_4= {3,1,-10};
	public static final int[][][] SCORE = {{SCORE_200},{SCORE_100},{SCORE_2_1},{SCORE_2_2},{SCORE_2_3},{SCORE_2_4},
			{SCORE_NEG_70_1},{SCORE_NEG_70_2},{SCORE_30_1},{SCORE_30_2}, {SCORE_25_1},{SCORE_25_2},{SCORE_NEG_10_1},
			{SCORE_NEG_10_2},{SCORE_NEG_10_3},{SCORE_NEG_10_4}};
}
