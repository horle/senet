package senet.logic.exception;

/**
 * exception for failed language recognition
 * @author horle
 *
 */
public class InvalidLanguageException extends Exception {

	public InvalidLanguageException(String string) {
		super(string);
	}
	
	public InvalidLanguageException() {
		super();
	}
}
