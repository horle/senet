package it.kussmaul.games.senet.base.logic.exception;

import it.kussmaul.games.senet.base.logic.base.Player;

/**
 * exception for winning case
 * @author horle
 *
 */
public class WinException extends Exception {
	
	private Player winner;

	public WinException(){
	}

	public Player getPlayer() {

		return winner;
	}
}
