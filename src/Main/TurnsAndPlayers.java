package Main;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Online.OnlinePanel;
import io.socket.emitter.Emitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter;

public class TurnsAndPlayers extends OfflinePanel{

	JPanel panel1;
	JPanel panel2;
	JLabel playersLabel;
	JLabel colorssLabel;
	JComboBox playersNumbers;
	JComboBox colorBox;
	int numbersPlayers;
	final String[] numbers = new String[] {"2","3","4","5","6"};
    List<String> colors = new LinkedList<String>();
    List<Player> players = new LinkedList<Player>();
    List<Player> activePlayers = new LinkedList<Player>();
    Player[] computers = new Player[] {new Player("Pc1",true),new Player("Pc2",true),new Player("Pc3",true),new Player("Pc4",true),new Player("Pc5",true)};
	private boolean firstTime = true;

	public TurnsAndPlayers(boolean online,boolean partyAdmin)
	{	
		super(online);
		//colors
		colors.add("Orange");
		colors.add("Red");
		colors.add("Blue");
		colors.add("Green");
		colors.add("Black");
		colors.add("Yellow");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		playersLabel = new JLabel("Players");
		playersNumbers = new JComboBox(numbers);
		colorssLabel = new JLabel("Colors");
		colorBox = new JComboBox(colors.toArray());
		
		panel1.add(playersLabel);
		panel1.add(playersNumbers);
		panel2.add(colorssLabel);
		panel2.add(colorBox);
		
		
		if(!online)
		{
			JOptionPane.showMessageDialog(null, panel1);
			JOptionPane.showMessageDialog(null, panel2);
			assignColorsAndPlayers();
	   	    setActivePlayers(activePlayers);

	   	    board = new BoardAndXY();
	   	    add(board,BorderLayout.WEST);
	   	    autoTurns();	
		}
		else
		{
			play.setEnabled(false);
			if(partyAdmin)
			{
				JOptionPane.showMessageDialog(null, panel1);
				JOptionPane.showMessageDialog(null, panel2);
				online(partyAdmin);
			}
			else
			{
				JOptionPane.showMessageDialog(null, panel2);
				online(partyAdmin);

			}
		}
  	}
	
	void assignColorsAndPlayers()
	{
		numbersPlayers = playersNumbers.getSelectedIndex();
		activePlayers.add(main.player);
		main.player.setColor(colors.get(colorBox.getSelectedIndex()));
		colors.remove(colorBox.getSelectedIndex());
		
		int[] position = new int[] {-50,-50};
		for(int i = 0;i<= numbersPlayers;i++)
		{
			computers[i].setColor(colors.get(0));
			colors.remove(colors.get(0));
			computers[i].setPosition(position);
			System.out.println(computers[i].getPosition()[0]);
			activePlayers.add(computers[i]);
		}
		Collections.shuffle(activePlayers);
	}
	
	void online(boolean admin)
	{
		numbersPlayers = playersNumbers.getSelectedIndex()+2;
		main.player.setColor(colors.get(colorBox.getSelectedIndex()));

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonPlayer = "";
		try {
			jsonPlayer = ow.writeValueAsString(main.player);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		OnlinePanel.s.emit("Party", jsonPlayer);
		if(admin)
		{
			OnlinePanel.s.emit("Size", numbersPlayers);	
		}
		OnlinePanel.s.on("Messges", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				area.append(arg0[0].toString()+"\n");
			}
		});	
		sizes();
   	    board = new BoardAndXY();
   	    add(board,BorderLayout.WEST);	
	}
	
	void sizes()
	{
		OnlinePanel.s.emit("Setting", "");
		OnlinePanel.s.on("Setting", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				int gameSize = (int) arg0[0]; 
				int currentPlayersSize  = (int) arg0[1];
				compareSize(gameSize,currentPlayersSize);
			}
		});	
	}
	
	void compareSize(int gameSize,int currentPlayersSize)
	{
		if(gameSize == currentPlayersSize)
		{
			setFirstTime(true);
			OnlinePanel.s.emit("ActivePlayers", "\"\"");
			OnlinePanel.s.on("ActivePlayers", new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					ObjectMapper mapper = new ObjectMapper();
					try {
						List<Player> players = mapper.readValue(
								arg0[0].toString(), 
						        new TypeReference<List<Player>>(){});
				   	    setActivePlayers(players);
				   	    if(isFirstTime())
				   	    {
					   	    area.append("Game Started\n");
				   	    }
				   	  repaint();
				   	 setFirstTime(false);
				   	checkWinnerOnline();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
			});	
			OnlinePanel.s.on("CurrentPlayer", new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					currenPlayerNumber =(int) arg0[0];
				   	 autoTurns();
				}
			});	
		}
		else
		{
			addText("Waiting Players");
		}
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	private void checkWinnerOnline()
	{
		for(int i = 0;i<OfflinePanel.activePlayers.size();i++)
		{
			if(OfflinePanel.activePlayers.get(i).isWinner())
			{
				isWinnerOptionPane(OfflinePanel.activePlayers.get(i).getName());
				break;
			}
		}
	}
}
