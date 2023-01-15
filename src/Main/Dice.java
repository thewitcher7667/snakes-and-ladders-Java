package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Dice extends JPanel{

	Style style;
	int random;
	int finalRandom;
	
	Dice()
	{
		style = new Style();
		style.panels(this, Color.WHITE,150, 150);
		finalRandom = 0;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Dialog",Font.BOLD,30));
		g2d.drawString(String.valueOf(finalRandom), 65, 75);
	}
	
	int random(int min,int max)
	{
		Random rand = new Random();
		//((max - min )+1)  + min
		random = rand.nextInt((max-min)+1)+min;
		return random;
	}
	
	private void animation(int min,int max)
	{
		for(int i=0;i<4;i++)
		{
			finalRandom = random(min,max);
			paint(getGraphics());
	        repaint();	
			try {
		          TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	int getFinalRandom(int min,int max)
	{
		animation(min,max);
		return finalRandom;
	}
	
	int getRandomNoAnimation(int min,int max)
	{
		return random(min,max);
	}
}