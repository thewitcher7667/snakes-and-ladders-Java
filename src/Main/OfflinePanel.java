package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import Online.OnlinePanel;

@SuppressWarnings("serial")
public class OfflinePanel extends JPanel{
	
	 private JPanel notBoardPanel; 
     private Style style;
     TurnsAndPlayers turns ;
     BoardAndXY board;
     JTextArea area;
     JButton play;
     JScrollPane s;
     Dice dice;
     JLabel playerLabel;
	 Logic l ;
	 int oneHundredPorition =0 ;
	 int currenPlayerNumber =0;
	 public static List<Player> activePlayers;
	 boolean played;
	 int autoTurnsDelay; 
	 //back button
	 JButton backButt;
	 boolean anyWinner;
	 boolean online;
	    public static final Map<String,Color> ColorsHashMap;
	    static{
	        Hashtable<String,Color> tmp = 
	            new Hashtable<String,Color>();
	        //ladders
	        tmp.put("Orange",Color.ORANGE);
	        tmp.put("Red",Color.RED);
	        tmp.put("Blue",Color.BLUE);
	        tmp.put("Green",Color.GREEN);
	        tmp.put("Black",Color.BLACK);
	        tmp.put("Yellow",Color.YELLOW);
	        tmp.put("White",Color.WHITE);
	        ColorsHashMap = Collections.unmodifiableMap(tmp);
	    }
	    //ladders
	    public static final Map<Integer,Integer> laddersAndSnakes;
	    static{
	        Hashtable<Integer,Integer> tmp = 
	            new Hashtable<Integer,Integer>();
	        //ladders
	        tmp.put(1,38);
	        tmp.put(4,14);
	        tmp.put(8,30);
	        tmp.put(21,42);
	        tmp.put(28,76);
	        tmp.put(50,67);
	        tmp.put(71,92);
	        tmp.put(80,99);
	        //snakes
	        tmp.put(32,10);
	        tmp.put(36,6);
	        tmp.put(48,26);
	        tmp.put(62,18);
	        tmp.put(88,24);
	        tmp.put(95,56);
	        tmp.put(97,78);
	        laddersAndSnakes = Collections.unmodifiableMap(tmp);
	    }
	    
     
     OfflinePanel(boolean online)
     {
    	 this.online = online;
    	 activePlayers = new LinkedList<Player>();
    	 l = new Logic();
    	 played = false;
    	 anyWinner = false;
    	 autoTurnsDelay = 2000;
    	 
    	 this.setLayout(new BorderLayout());
    	 
    	 style = new Style();
    	 notBoardPanel = new JPanel();
    	 dice = new Dice();
    	 playerLabel = new JLabel();
    	 
    	 backButt = new JButton("Back");
         style.buttons(backButt, Color.decode("#FF4E33"), 10, 50, 10, 50);
         backButt.addActionListener(e -> backButton());
    	 
         style.labels(playerLabel, 15, Color.decode("#FFB756"));
    	 
    	 area = new JTextArea(); 
    	 area.setPreferredSize(new Dimension(250,30));
    	 
    	 s = new JScrollPane(area);
         s.setPreferredSize(new Dimension(270,400));
         
         area.setEditable(false);
    	 area.append("Welcome to Snake And Laders Game\nRoll The Dice To Start Playing\n");
         area.setBackground(Color.decode("#1A1C53"));
         area.setForeground(Color.white);
    	  
    	 
    	 style.panels(this, Color.decode("#00023F"), 900, 700);
    	 style.panels(notBoardPanel, Color.decode("#00023F"), 280, 700);
    	 
    	 play = new JButton("Play");
    	 style.buttons(play, Color.decode("#FF4E33"), 10, 50, 10, 50);
    	 play.addActionListener(e -> playActionPerformed());

    	 notBoardPanel.add(playerLabel);
    	 notBoardPanel.add(s);
    	 notBoardPanel.add(dice);
    	 notBoardPanel.add(play);
    	 notBoardPanel.add(backButt);

    	 add(notBoardPanel,BorderLayout.EAST);

     }
     
	public void playActionPerformed()
	{	
	//test case for all positions	
   	 /*for(int i =0;i<=100;i++)
   	 {
       	 main.player.setCurrentPosition(i+1);
       	 l.setPosition(main.player.getCurrentPosition());
       	 main.player.setPosition(l.getFinalPosition());
       	 board.paint(board.getGraphics());
       	 
       	 try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
   	 }*/
		
		played = true;
		autoTurns();
	}
	
	void turns(Player player)
	{
		int rolledDice = 0;
		
		rolledDice = dice.getFinalRandom(1,6);
		
		//condition if the current position is 100
		
	   player.setPreviousPosition(player.getCurrentPosition());
	   player.setCurrentPosition(player.getCurrentPosition() + rolledDice);
	   
	   addText(player.getName() + " Played "+rolledDice);
	   
	   animation(player); 
		   
	   System.out.println(player.getName()+" : "+ Arrays.toString(player.getPosition()) + " " + player.getCurrentPosition() );

	   
  	   repaint();
	}
	
	void animation(Player player)
	{
      int delay = 300; //milliseconds
       autoTurnsDelay = delay * (player.getCurrentPosition() - player.getPreviousPosition()) + 2000;
   	 ActionListener taskPerformer = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
     		if(player.getPreviousPosition() >= player.getCurrentPosition()+1)
     		{
     			if(laddersAndSnakes.get(player.getCurrentPosition()) != null)
     			{
     				 player.setCurrentPosition(laddersAndSnakes.get(player.getCurrentPosition())) ;
     	         	 l.setPosition(player.getCurrentPosition());
     	        	 player.setPosition(l.getFinalPosition());
     	           	 player.setPreviousPosition(player.getCurrentPosition());
     	           	 repaint();
     			}
     			if(online)
     			{
               	    updateOnline();
     			}
     			if(player.isWinner())
     			{
     				isWinnerOptionPane(player.getName());
     				resetGame();
     			}
     			return;
     		}
     		else if(checkWinner(player))
     		{
           	   l.setPosition(player.getPreviousPosition());
         	   player.setPosition(l.getFinalPosition());
               player.setPreviousPosition(player.getPreviousPosition()+1);
               repaint();
               animation(player);	
               return;
     		}
 			if(online)
 			{
           	    updateOnline();
 			}
         }
     };
     //the timer
     Timer timer =  new Timer(delay, taskPerformer);
     timer.setRepeats(false);
     timer.start();	
	}
	
	void updateOnline()
	{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
			String json = ow.writeValueAsString(activePlayers);
			json  = "[\n"+json+","+currenPlayerNumber+"\n]";
			OnlinePanel.s.emit("ActivePlayers", json);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
	}
	
	boolean checkWinner(Player player)
	{
		if(player.getCurrentPosition() == 100) {
            player.setWinner(true);
			anyWinner = true;
            return true;
        }
        else if(player.getCurrentPosition() > 100) {
            player.setCurrentPosition(player.getPreviousPosition());
            return false;
        }
	   return true;
	}
	
	void isWinnerOptionPane(String name)
	{
        Object[] options1 = { "Play Again", "Back"};
        int result = JOptionPane.showOptionDialog(null, name + " Is The Winner", "Winner",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        
        if (result == JOptionPane.YES_OPTION)
        {
        	resetGame();
        	main.frame.setContentPane(new TurnsAndPlayers(true,false));
        }
        else if(result  == JOptionPane.NO_OPTION)
        {
        	//back function 
        	resetGame();
        	main.frame.setContentPane(new MainPanel());
        	
        }
        disconnectOnline();
	}
	
	void autoTurns()
	{
		//assign current player
		Player currentPlayer = activePlayers.get(currenPlayerNumber);
		//set the player label text that has the turn
		playerLabel.setText(currentPlayer.getName() + " Turn");
		//make the current player active
		currentPlayer.setActive(true);
		//make the play button disable
		play.setEnabled(false);
		//if current player is pc and active
        if(currentPlayer.isPc() && currentPlayer.isActive() && !anyWinner)
        {
        	//delay for the animation
            int delay = autoTurnsDelay; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	//get the dice and turns of the current pc 
                	turns(currentPlayer);
                	//increase the currentPlayerNumber to get it at line 156
       		        increaseCurrentPlayerNumber();
       		        //set the current player activity to false
    				currentPlayer.setActive(false);
    				//call the method again
    				autoTurns();
                }
            };
            //the timer
           Timer timer =  new Timer(delay, taskPerformer);
           timer.setRepeats(false);
           timer.start();
          }
        else if(!currentPlayer.isPc() && currentPlayer.isActive() && !anyWinner)
        {
    		//waiting the user to play make true at line 123 if the user playes
			if(main.player.getId() == currentPlayer.getId())
			{
	        	//if is the user set the button to true
	    		play.setEnabled(true);
			   if(played == true)
			    {
			        increaseCurrentPlayerNumber();
					//set the buuton to disable again after playing
		    		play.setEnabled(false);
		    		turns(currentPlayer);
		            played = false;
					currentPlayer.setActive(false);	
		            autoTurns();	
				}
			}

        }	 
	}
	
	void resetGame()
	{
		for(int i = 0;i<activePlayers.size();i++)
		{
			Player current = activePlayers.get(i);
			current.setCurrentPosition(0);
			current.setPreviousPosition(0);
			current.setWinner(false);
			current.setActive(false);
			current.setPosition(new int[] {-50,-50});
		}
	}
	
	void backButton()
	{
		resetGame();
		disconnectOnline();
		main.frame.setContentPane(new MainPanel());
	}
	
	void increaseCurrentPlayerNumber()
	{
        if(currenPlayerNumber == activePlayers.size()-1)
        {
        	currenPlayerNumber = 0;
        }
        else
        {
            currenPlayerNumber++;
        }
	}
	
	void setActivePlayers(List<Player> activePlayers)
	{
		OfflinePanel.activePlayers = activePlayers;
	}
	
	void addText(String text)
	{
		if(online)
		{
			OnlinePanel.s.emit("Messges",text);	
		}
		else
		{
			area.append(text);
		}
		   area.setPreferredSize(new Dimension(250,area.getPreferredSize().height+30));
	}
	
	void disconnectOnline()
	{
		if(online)
		{
			OnlinePanel.s.disconnect();
			OnlinePanel.s.close();
		}
	}
	
}
