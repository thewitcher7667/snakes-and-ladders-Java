package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class OfflinePanel extends JPanel{
	
	 private JPanel notBoardPanel; 
     private Style style;
     BoardAndXY board;
     JTextArea area;
     JButton play;
     JScrollPane s;
     Dice dice;
     Player pc;
     JLabel playerLabel;
     boolean firstTime;
	 Logic l ;
	 
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
	        tmp.put(73,92);
	        tmp.put(86,99);
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
     
     OfflinePanel()
     {
    	 firstTime =true;
    	 l = new Logic();
    	 pc = new Player("Pc",Color.BLACK);
    	 
    	 this.setLayout(new BorderLayout());
    	 
    	 style = new Style();
    	 board = new BoardAndXY(main.player,pc);
    	 notBoardPanel = new JPanel();
    	 dice = new Dice();
    	 playerLabel = new JLabel();
    	 
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

    	 add(board,BorderLayout.WEST);
    	 add(notBoardPanel,BorderLayout.EAST);

 		//initiate truns and player and pc
    	 startTurns();
     }
     
	public void playActionPerformed()
	{	
		//test case for all positions
		/*
   	 for(int i =0;i<=100;i++)
   	 {
       	 main.player.setCurrentPosition(i+1);
       	 l.setPosition(main.player.getCurrentPosition());
       	 main.player.setPosition(l.getFinalPosition());
       	 board.paint(board.getGraphics());
       	 
       	 try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
   	 }*/
		
		turns(main.player);	
		
		play.setEnabled(false);
        int delay = 2000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
        		turns(pc);
        		play.setEnabled(true);
            }
        };
        
       Timer timer =  new Timer(delay, taskPerformer);
       timer.setRepeats(false);
       timer.start();
		
	}
	
 
	void startTurns()
	{
		String start =  " Will start The Game\n";
		int rolledDice = dice.getRandomNoAnimation(1,2);
		System.out.println(rolledDice);
		if(rolledDice == 1)
		{
	   	   area.append(main.player.getName() + start);
	   	   playerLabel.setText(main.player.getName() + " Turn");
	   	   firstTime = false;
		}
		else
		{
	   	    area.append(pc.getName() + start);
	   	    turns(pc);
		}
	}
	
	void turns(Player player)
	{
		int rolledDice = 0;
		
		if(firstTime)
			rolledDice = dice.getRandomNoAnimation(1,6);
		else
			 rolledDice = dice.getFinalRandom(1,6);
		
		//condition if the current position is 100
		
	   player.setPreviousPosition(player.getCurrentPosition());
	   player.setCurrentPosition(player.getCurrentPosition() + rolledDice);
	   
		if(laddersAndSnakes.get(player.getCurrentPosition()) != null)
		{
			 player.setCurrentPosition(laddersAndSnakes.get(player.getCurrentPosition())) ;
		}
	   
	   l.setPosition(player.getCurrentPosition());
	   player.setPosition(l.getFinalPosition());
	   System.out.println(player.getName()+" : "+ Arrays.toString(player.getPosition()) + " " + player.getCurrentPosition());
	   
	   playerLabel.setText(player.getName() + " Turn");
	   area.append(player.getName() + " Played "+rolledDice+"\n");
  	   area.setPreferredSize(new Dimension(250,area.getPreferredSize().height+20));
  	   
  	   repaint();
	   firstTime = false;
	}
}
