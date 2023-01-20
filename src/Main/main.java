package Main;

import java.util.Random;

import javax.swing.JFrame;

public class main {
	static public JFrame frame;
	static public Player player;
	
	public static void main(String[] args) {
		frame = new JFrame();
		player = new Player();
        //assign id to the user
        main.player.setId( new Random().nextInt(100000));
				
		//frame.getContentPane().add(new TurnsAndPlayers());

		frame.getContentPane().add(new MainPanel());
		
		frame.setSize(900,700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.pack();
	}    
	
}
