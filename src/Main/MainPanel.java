package Main;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Online.OnlinePanel;

@SuppressWarnings("serial")
//make the class as JPanel
public class MainPanel extends JPanel{
     private JLabel label;
     private JButton offline;
     private JButton online;
     private JButton setting;
     private Style style;
     
     public MainPanel()
     {
    	 /*
    	  *  set layout FlowLayout
    	  *  specify its width to the frame width to center the elements
    	  *  specify height to 100 to leave some space between butts
    	  */
    	 this.setLayout(new FlowLayout(FlowLayout.CENTER,main.screenWidth,100));
    	 
    	 //initialization of elements 
    	 label = new JLabel("Snakes and Ladders",JLabel.CENTER);
    	 offline = new JButton("Offline");
    	 online = new JButton("Online");
    	 setting = new JButton("Setting");
    	 style = new Style();
    	 
    	 style.panels(this, Color.decode("#00023F"),main.screenWidth, main.screenHeigth);
    	 
    	 style.labels(label, 30, Color.decode("#FFB756"));
   	     
    	 int buttonTop = 10 ;
    	 int buttonRight = 50;
    	 int buttonBottom = 10;
    	 int buttonLeft = 50;
    	 
   	     style.buttons(offline, Color.decode("#FF4E33"), buttonTop, buttonRight, buttonBottom, buttonLeft);
   	     offline.addActionListener(e -> offlineButtonAction());
   	     
   	     style.buttons(online, Color.decode("#FF4E33"), buttonTop, buttonRight, buttonBottom, buttonLeft);
   	     online.addActionListener(e -> onlineButtonAction());
   	     
   	     style.buttons(setting, Color.decode("#FF4E33"), buttonTop, buttonRight, buttonBottom, buttonLeft);
   	     setting.addActionListener(e -> settingButtonAction());
    	 
    	 add(label);
    	 add(offline);
    	 add(online);
    	 add(setting);
    	
     }
     
     private void offlineButtonAction()
     {
    	 main.frame.setContentPane(new TurnsAndPlayers(false,false));
     }
     
     private void settingButtonAction()
     {
    	 new Settngs();
     }
     
     private void onlineButtonAction()
     {
    	 main.frame.setContentPane(new OnlinePanel());
     }
}
