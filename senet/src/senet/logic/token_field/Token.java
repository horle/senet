package senet.logic.token_field;

import senet.logic.base.Game;
import senet.logic.base.Player;

/**
 * class for game tokens (spielfiguren)
 * @author horle
 *
 */

public class Token {

	private int id;
	private Player owner;
	private Field field;
	private boolean out;
	
	public Token(int id, Player p) {

		this.id = id;
		this.owner = p;
	}
	
	public Field getField(){
		return field;
	}
	
	public void setField(Field field){
		this.field = field;
	}

	public boolean isOut() {
		return out;
	}

	public void toggleOut() {
		
		if (out)
			out = false;
		else
			out = true;
	}

	public Player getOwner() {
		return owner;
	}

	public int getId() {
		return id;
	}
	
	public String toString(){
		
		return out ? "OUT!!! " : "" + Game.getInstance().getMessages().getString("tokenNr") + " " + this.id + " owned by " + this.owner.getName();
	}

}
