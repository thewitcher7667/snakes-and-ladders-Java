package Main;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import javax.swing.JFrame;

public class main {
	static JFrame frame;
	static Player player;
	static final String  fileName = "palyer.dat" ;
	
	public static void main(String[] args) {
		frame = new JFrame();
        readPlayer();
				
		//frame.getContentPane().add(new TurnsAndPlayers());

		frame.getContentPane().add(new MainPanel());
		
		frame.setSize(900,700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.pack();
	}    
	
	static void readPlayer()
	{  
       try 
       {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
		player = (Player) input.readObject();
		input.close();
	   }  
	   catch (FileNotFoundException e) 
       {
			try 
			{
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
				output.writeObject(new Player());
				output.close();
				readPlayer();
			} 
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
	   }
       catch (IOException e)
       {
			e.printStackTrace();
	   } 
	   catch (ClassNotFoundException e)
       {
			e.printStackTrace();
	   }
	}
	
	static void savePlayer(Player player)
	{
		try 
		{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
			output.writeObject(player);
			output.close();
			readPlayer();
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
}
