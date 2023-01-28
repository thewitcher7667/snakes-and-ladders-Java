package Main;

import java.util.Random;

import javax.swing.JFrame;

public class main {
	/*
	 * make frame and player statics to be available for all classes
	 * to avoid calling the main class again to access the frame and players
	 */
	static public JFrame frame;
	//make new player 
	static public Player player;
	static final int screenWidth = 900;
	static final int screenHeigth = 700;

	
	public static void main(String[] args) {
		frame = new JFrame();
		player = new Player();
        //assign id to the user
        main.player.setId( new Random().nextInt(100000));
	     /*
	      * at this code frame.getContentPane().add() no  between it an frame.add()
	      * but it is good practice
	      * 
	      * adding the Panel >>MainPanel>> the first panel that contain {offline,online,setting,options}
	      * 
	      */
		frame.getContentPane().add(new MainPanel());
		//frame functions
		frame.setSize(screenWidth,screenHeigth);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.pack();
	}    
	
}
