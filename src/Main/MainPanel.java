package Main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{
     private JLabel label;
     private JButton offline;
     private JButton online;
     private JButton setting;
     private Style style;
     
     MainPanel()
     {
    	 this.setLayout(new FlowLayout(FlowLayout.CENTER,900,100));
    	 label = new JLabel("Snakes and Ladders",JLabel.CENTER);
    	 offline = new JButton("Offline");
    	 online = new JButton("Online");
    	 setting = new JButton("Setting");
    	 style = new Style();
    	 
    	 style.panels(this, Color.decode("#00023F"),900, 700);
    	 
    	 style.labels(label, 30, Color.decode("#FFB756"));
   	     
   	     style.buttons(offline, Color.decode("#FF4E33"), 10, 50, 10, 50);
   	     offline.addActionListener(e -> offlineButtonAction());
   	     
   	     style.buttons(online, Color.decode("#FF4E33"), 10, 50, 10, 50);
   	     
   	     style.buttons(setting, Color.decode("#FF4E33"), 10, 50, 10, 50);
   	     setting.addActionListener(e -> settingButtonAction());
    	 
    	 add(label);
    	 add(offline);
    	 add(online);
    	 add(setting);
    	
     }
     
     public JPanel getPanel()
     {
    	 return this;
     }
     
     private void offlineButtonAction()
     {
    	 main.frame.setContentPane(new OfflinePanel());
     }
     
     private void settingButtonAction()
     {
    	 new Settngs();
     }
}
