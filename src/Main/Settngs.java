package Main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Settngs extends JOptionPane {
	
	Style style;
	Color[] colors;
	JPanel panel;
	JTextField field1;
	JLabel labelName;
	JLabel labelColor;
	
	Settngs()
	{		
		style = new Style();
	    panel = new JPanel(new FlowLayout(FlowLayout.CENTER,1,5));
	    
	    labelName = new JLabel("Name : ");
	    style.labels(labelName, 20, Color.BLACK);
	    
	    labelColor = new JLabel("Color : ");
	    style.labels(labelColor, 20, Color.BLACK);
	    
	    field1 = new JTextField(10);
	    
	    panel.add(labelName);
	    panel.add(field1);
	    
	    showMessageDialog(null, panel);
	    getInputes();
	}
	
	void getInputes()
	{
		if(field1.getText().equals("") || field1.getText().length()>20)
		{
			showMessageDialog(null, "Didint Save");
		}
		else
		{
			main.player.setName(field1.getText());
		}
		main.savePlayer(main.player);
	}
}
