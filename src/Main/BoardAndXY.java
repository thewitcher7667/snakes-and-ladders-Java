package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardAndXY extends JPanel{

	Style style;
    Image board ;
    private List<Player> players;
    int ovalSize;
    
	BoardAndXY()
	{
   	    style = new Style();
   	    style.panels(this, Color.decode("#00023F"), 617, 610);
   	    board = new ImageIcon("game.png").getImage();
   	    ovalSize = 40;
	}
	
    @Override
    public void paint(Graphics g)
    {
   	 Graphics2D d = (Graphics2D) g;
   	 d.setColor(Color.decode("#00023F"));
   	 d.fillRect(0, 0, getWidth(), getHeight());

   	 d.drawImage(board,0,0,null);   	 
   	 
   	 players = OfflinePanel.activePlayers;
   	 
   	 for(int i =0 ;i<players.size();i++)
   	 {
   		 //System.out.print(players.get(i).getPosition()[0]+" "+players.get(i).getPosition()[1]);
   	   	 d.drawOval(players.get(i).getPosition()[0], players.get(i).getPosition()[1], ovalSize, ovalSize);
   	   	 d.setColor(OfflinePanel.ColorsHashMap.get(players.get(i).getColor()));
   	   	 d.fillOval(players.get(i).getPosition()[0], players.get(i).getPosition()[1], ovalSize, ovalSize);
   	   	 d.setColor(Color.BLACK);
   	   	 d.setFont(new Font("default", Font.BOLD, 16));
   	   	 d.drawString(players.get(i).getName(), players.get(i).getPosition()[0], players.get(i).getPosition()[1]);
   	  }
}
    
}
