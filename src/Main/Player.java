package Main;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable{

	private int id;
	private String name;
	private int[] position;
	private int currentPosition;
	private int previousPosition;
	private Color color;
    private static final long serialVersionUID = 6529685098267757690L;
	
	Player()
	{
		setPosition(new int[] {-50,-50});
		setCurrentPosition(0);
		setPreviousPosition(0);
		setName("Player");
		setColor(Color.WHITE);
	}
	
	Player(String name,Color color)
	{
		setPosition(new int[] {0,0});
		setCurrentPosition(0);
		setPreviousPosition(0);
		setName(name);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(int previousPosition) {
		this.previousPosition = previousPosition;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
