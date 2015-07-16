package senet.logic.base;

import java.util.ArrayList;
import java.util.Random;

/**
 * turn class. also manages stick score
 * @author horle
 *
 */
public class Turn {
	
	private Player playerTurn;
	private ArrayList<Player> players;
	private int stickScore;
	
	public Turn (ArrayList<Player> p){
		
		players = p;
		playerTurn = null;
		throwSticks();
	}

	public Player getPlayer() {
		
		return playerTurn;
	}
	
	public int getStickScore(){
		
		return stickScore;
	}

	public void toggle() {
		
		if (playerTurn != null){
			
			if (playerTurn.equals(players.get(0)))
				playerTurn = players.get(1);
			else
				playerTurn = players.get(0);
		}
		else
			playerTurn = players.get(0);
		
		throwSticks();
	}
	
	private void throwSticks(){
		
		Random random = new Random();
		
		int result = random.nextInt(5);
		
		if (result == 4)
			result = 5;
		
		stickScore = result;	
	}

}
