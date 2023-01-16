package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardAndXY extends JPanel{

	Style style;
    Image board ;
    private List<Player> players;
    
	BoardAndXY(List<Player> players)
	{
		this.players = players;
   	    style = new Style();
   	    style.panels(this, Color.decode("#00023F"), 617, 610);
   	    board = new ImageIcon("game.png").getImage();
	}
	
    @Override
    public void paint(Graphics g)
    {
   	 Graphics2D d = (Graphics2D) g;
   	 d.setColor(Color.decode("#00023F"));
   	 d.fillRect(0, 0, getWidth(), getHeight());

   	 d.drawImage(board,0,0,null);   	 
   	 
   	 for(int i =0 ;i<players.size();i++)
   	 {
   	   	 d.drawOval(players.get(i).getPosition()[0], players.get(i).getPosition()[1], 40, 40);
   	   	 d.setColor(players.get(i).getColor());
   	   	 d.fillOval(players.get(i).getPosition()[0], players.get(i).getPosition()[1], 40, 40);
   	  }
}
    
}
