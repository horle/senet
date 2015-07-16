package senet.logic.exception;

/**
 * exception for failed input verification
 * @author horle
 *
 */
public class InvalidInputException extends Exception {

	public InvalidInputException(String string) {

		super(string);
	}
	
	public InvalidInputException() {

		super();
	}

}
