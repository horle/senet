package it.kussmaul.games.senet.base.logic.token_field;

/**
 * class for game query system, used for abstraction of UI
 * @author horle
 *
 */
public class SenetQuery {
	
	public static final int RECLAIM_OPERATION = 2;
	public static final int MOVE_OPERATION = 1;
	
	private Field target;
	private Token token;
	private int diced;
	private int code;
	
	public SenetQuery (int c, Field f, Token t, int d){		//move
		
		code = c;
		target = f;
		token = t;
		diced = d;
	}
	
	public SenetQuery (int c, int d){	// reclaim
		
		code = c;
		diced = d;
	}

	public int getCode() {
		return code;
	}

	public Field getTarget() {
		return target;
	}

	public Token getToken() {
		return token;
	}

	public int getDiced() {
		return diced;
	}
}
