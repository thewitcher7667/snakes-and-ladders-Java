package Online;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Main.MainPanel;
import Main.Style;
import Main.TurnsAndPlayers;
import Main.main;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class OnlinePanel extends JPanel{
	
	Style style;
    public static Socket s;
	//buttons
	JButton creatPartyButt;
	JTextField creatPartyField;
	//joining field
	JButton joinPartyButt;
	JTextField joinPartyField;
	JButton back;

	public OnlinePanel()
	{
               
            try {
                s = IO.socket("http://localhost:3000");

                s.open();
            } catch (URISyntaxException ex) {
                Logger.getLogger(OnlinePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.setLayout(new FlowLayout(FlowLayout.CENTER,700,100));
            JPanel containCreate = new JPanel();
            JPanel containJoining = new JPanel();
            back = new JButton("Back");
            
            style = new Style();
            
            creatPartyButt = new JButton("Create Party");
            creatPartyField = new JTextField(20);
            
            joinPartyButt = new JButton("Join Party");
            joinPartyField = new JTextField(20);
            
            //styling panel
            style.panels(this, Color.decode("#00023F"),900, 700);
            style.panels(containCreate, Color.decode("#00023F"),700, 100);
            style.panels(containJoining, Color.decode("#00023F"),700, 100);
            
            style.buttons(creatPartyButt, Color.decode("#FF4E33"), 10, 50, 10, 50);
            creatPartyButt.addActionListener(e -> creatPartyButt());
            
            style.buttons(joinPartyButt, Color.decode("#FF4E33"), 10, 50, 10, 50);
            joinPartyButt.addActionListener(e -> joinPartyButt());
            
            style.buttons(back, Color.decode("#FF4E33"), 10, 50, 10, 50);
            back.addActionListener(e -> main.frame.setContentPane(new MainPanel()));
            
            //adding to panel
            containCreate.add(creatPartyButt);
            containCreate.add(creatPartyField);
            containJoining.add(joinPartyButt);
            containJoining.add(joinPartyField);
            add(containCreate);
            add(containJoining);
            add(back);
            
            s.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {				
     			@Override
    			public void call(Object... arg0) {
    				
    			}
    		});
	}
	
	private void creatPartyButt()
	{
		String partyName = creatPartyField.getText()  + new Random().nextInt(100000) ;
		main.player.setParty(partyName);
		main.frame.setContentPane(new TurnsAndPlayers(true,true));
		
 
	}
		
	private void joinPartyButt()
	{
		String partyName = joinPartyField.getText() ;
		if(partyName.equals(""))
		{	
		}
		else
		{
			main.player.setParty(partyName);
			main.frame.setContentPane(new TurnsAndPlayers(true,false));
		}
	}
}
