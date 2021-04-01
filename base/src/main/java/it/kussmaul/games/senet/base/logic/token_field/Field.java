package it.kussmaul.games.senet.base.logic.token_field;

import it.kussmaul.games.senet.base.logic.base.Game;
import it.kussmaul.games.senet.base.logic.base.Player;

/**
 * field class
 * @author horle
 *
 */
public class Field {
	
	protected int id;
	protected String description;
	protected Token token;
	protected boolean isSafe;
	
	/**
	 * field of the board
	 * 
	 * @author horle
	 * @param id id of the field
	 */
	public Field(int id) {

		this.id = id;
		this.description = Game.getInstance().getMessages().getString("fieldStandardDescription");
		this.isSafe = false;
	}

	/**
	 * get description of the field
	 * @return field description
	 */
	public String getDescription() {
		
		return description;
	}

	/**
	 * set description of the field
	 */
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	/**
	 * get reference to the token
	 * @return token reference which occupies the field
	 */
	public Token getToken() {
		
		return this.token;
	}
	
	public boolean isOccupiedBy(Player p){
		
		return (this.token != null && this.token.getOwner().equals(p) );
	}
	
	public void setToken(Token t){
		
		this.token = t;
	}
	
	/**
	 * determines if field is occupied
	 * @return true, if occupied
	 */
	public boolean isOccupied(){
		
		return token != null;
	}

	/**
	 * get id of field
	 * 
	 */
	public int getId() {
		return id;
	}
	
	public int getNum(){
		return id+1;
	}
	
	public String toString(){
		
		if (isOccupied())
			return this.description + ", " + token.toString();
			
		else
			return this.description + ", empty";
	}

	public boolean isSafe() {

		return isSafe;
	}

}
