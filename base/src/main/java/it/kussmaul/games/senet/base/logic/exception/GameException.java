package it.kussmaul.games.senet.base.logic.exception;

/**
 * exception for game messages
 * @author horle
 *
 */
public class GameException extends Exception {

	public GameException(String string) {
		super(string);
	}
	
	public GameException() {
		super();
	}

}
