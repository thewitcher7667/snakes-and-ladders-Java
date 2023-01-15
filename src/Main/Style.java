package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Style {
	
	public JPanel panels(JPanel panel,Color color,int w,int h)
   {
	  panel.setSize(w,h);
	  panel.setPreferredSize(new Dimension(w,h));
	  panel.setBackground(color);
	  
	  return panel;  
   }
   
   public JLabel labels(JLabel label,int size,Color color)
   {
	   
	  label.setHorizontalTextPosition(JLabel.CENTER);
	  label.setVerticalTextPosition(JLabel.TOP);
	  label.setForeground(color);
	  label.setFont(new Font("Dialog",Font.BOLD,size));
	 
	  return label;   
   }
   
	public JButton buttons(JButton butt,Color color,int margin1,int margin2,int margin3,int margin4) {
		butt.setForeground(Color.WHITE);
		butt.setBackground(color);
		Border line = new LineBorder(Color.WHITE);
		Border margin = new EmptyBorder(margin1,margin2,margin3,margin4);
		Border compound = new CompoundBorder(line, margin);
		butt.setBorder(compound);
		
		return butt;
	}
}
