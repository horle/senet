package senet.logic.exception;

import senet.logic.base.Player;

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
