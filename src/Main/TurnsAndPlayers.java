package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TurnsAndPlayers extends OfflinePanel{

	JPanel panel;
	JLabel playersLabel;
	JLabel colorssLabel;
	JComboBox playersNumbers;
	JComboBox colorBox;
	final String[] numbers = new String[] {"2","3","4","5","6"};
	final String[] colorsComboBox = new String[] {"white","Red","Blue","Green","Black","Yellow"};
    List<Color> colors = new LinkedList<Color>();
    List<Player> players = new LinkedList<Player>();
    List<Player> activePlayers = new LinkedList<Player>();
    Player[] computers = new Player[] {new Player("Pc1",true),new Player("Pc2",true),new Player("Pc3",true),new Player("Pc4",true),new Player("Pc5",true)};

    
	TurnsAndPlayers()
	{
		panel = new JPanel();
		playersLabel = new JLabel("Players");
		playersNumbers = new JComboBox(numbers);
		colorssLabel = new JLabel("Colors");
		colorBox = new JComboBox(colorsComboBox);
		//colors
		colors.add(Color.WHITE);
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.BLACK);
		colors.add(Color.YELLOW);
		
		panel.add(playersLabel);
		panel.add(playersNumbers);
		panel.add(colorssLabel);
		panel.add(colorBox);

		JOptionPane.showMessageDialog(null, panel);
		assignColorsAndPlayers();
   	    setActivePlayers(activePlayers);

   	    board = new BoardAndXY(activePlayers);
   	    add(board,BorderLayout.WEST);
   	    autoTurns();
  	}
	
	void assignColorsAndPlayers()
	{
		int numbers = playersNumbers.getSelectedIndex();
		activePlayers.add(main.player);
		main.player.setColor(colors.get(colorBox.getSelectedIndex()));
		colors.remove(colorBox.getSelectedIndex());
		
		int[] position = new int[] {-50,-50};
		for(int i = 0;i<= numbers;i++)
		{
			computers[i].setColor(colors.get(0));
			colors.remove(colors.get(0));
			computers[i].setPosition(position);
			System.out.println(computers[i].getPosition()[0]);
			activePlayers.add(computers[i]);
		}
		Collections.shuffle(activePlayers);
	}
	
}
