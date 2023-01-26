package Main;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Player {

	private int id;
	private String name;
	@JsonProperty("position")
	private int[] position;
	private int currentPosition;
	private int previousPosition;
	private String color;
	private boolean active;
	private boolean isPc;
	private boolean isWinner;
	private String party;
	public int animation;
	
	Player()
	{
		setPosition(new int[] {-50,-50});
		setCurrentPosition(0);
		setPreviousPosition(0);
		setActive(false);
		setName("Player");
		setColor("White");
		setPc(false);
		setWinner(false);
	}
	
	Player(String name,Color color)
	{
		setPosition(new int[] {-50,-50});
		setCurrentPosition(0);
		setPreviousPosition(0);
		setActive(false);
		setName(name);
		setColor("White");
		setPc(false);
		setWinner(false);
		animation = 0;
	}

	
	Player(String name,boolean isPc)
	{
		setPosition(new int[] {-50,-50});
		setCurrentPosition(0);
		setPreviousPosition(0);
		setName(name);
		setActive(false);
		setColor("White");
		setPc(isPc);
		setWinner(false);
		animation = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPc() {
		return isPc;
	}

	public void setPc(boolean isPc) {
		this.isPc = isPc;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

}
