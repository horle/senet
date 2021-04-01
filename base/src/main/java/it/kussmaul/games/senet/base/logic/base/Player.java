package it.kussmaul.games.senet.base.logic.base;

import it.kussmaul.games.senet.base.logic.token_field.Token;

import java.awt.*;
import java.util.ArrayList;
//import com.badlogic.gdx.graphics.Color;

/**
 * player class
 * @author horle
 */

public class Player {
		
	private String name;
	private int id;
	private Color color;
	
	private ArrayList<Token> tokens;
	
	private int goalCount;
	private int outCount;
		
	public Player(int id, String name, int tokenCount, Color blue) {
		
		this.name = name;
		this.id = id;
		this.color = blue;
		
		tokens = new ArrayList<Token>(tokenCount);
		
		for (int i = 0; i < tokenCount; i++){
			
			tokens.add(new Token(i, this));
		}
		
		goalCount = 0;
		outCount = 0;
	}

	public Color getColor() {
		return color;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public String getName() {
		return name;
	}

	public int getGoalCount() {
		return goalCount;
	}

	public void increaseGoalCount() {
		
		if (goalCount < Game.tokenCount)
			goalCount++;
	}

	public int getOutCount() {
		return outCount;
	}

	public void increaseOutCount() {
		
		if (outCount < Game.tokenCount)
			outCount++;
	}
	
	public void decreaseOutCount() {
		
		if (outCount > 0)
			outCount--;
	}


}
