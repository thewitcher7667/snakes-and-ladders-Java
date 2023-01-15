package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardAndXY extends JPanel{

	Style style;
    Image board ;
    private Player player1;
    private Player player2;
    
	BoardAndXY(Player player1,Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
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
   	 
   	 d.drawOval(player1.getPosition()[0], player1.getPosition()[1], 40, 40);
   	 d.setColor(player1.getColor());
   	 d.fillOval(player1.getPosition()[0], player1.getPosition()[1], 40, 40);
   	 
   	 d.drawOval(player2.getPosition()[0], player2.getPosition()[1], 40, 40);
   	 d.setColor(player2.getColor());
   	 d.fillOval(player2.getPosition()[0], player2.getPosition()[1], 40, 40);
    }
}
