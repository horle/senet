package senet.ui.console;

import java.util.regex.Pattern;

import senet.logic.base.Game;
import senet.logic.exception.InvalidInputException;
import senet.logic.token_field.SenetQuery;
import senet.logic.token_field.Token;

/**
 * abstract class for input verification functions (console)
 * @author horle
 *
 */
public abstract class InputVerifier {
	
	public static boolean moveOperation(String input) throws InvalidInputException{
		
		try {
			
			Game g = Game.getInstance();
			int source = Integer.parseInt(input);	//source
			int target = source + g.getTurn().getStickScore();		//target
			
			if (g.verifyMove(source, target)){
				
				Token token = g.getFields().get(source-1).getToken();
				
				SenetQuery q = new SenetQuery(
						SenetQuery.MOVE_OPERATION,
						g.getFields().get(target-1),
						token,
						g.getTurn().getStickScore());
			
				return g.updateGame(q);
			}
			else
				return false;

		}catch (NumberFormatException e1) {
			
			throw new InvalidInputException("Invalid number input.");
		}
	}
	
	public static boolean reclaimToken() throws InvalidInputException {
		
		Game g = Game.getInstance();
		
		if (Game.getInstance().verifyReclaim()){
			
			SenetQuery q = new SenetQuery(
					SenetQuery.RECLAIM_OPERATION,
					g.getTurn().getStickScore());
		
			return g.updateGame(q);
		}
		else
			return false;
	}
	
	public static boolean checkLanguageInput(String in){
		
		return Pattern.matches("de|en", in);
	}
	
	public static boolean checkNameInput(String in){
		
		return Pattern.matches("\\w*, \\w*", in);
	}


	public static int checkTokenInput(String input) {
		
		int re = -1;
		
		if (Pattern.matches("\\d*", input))
			re = Integer.parseInt(input);
		
		if (re > 5)
			re = 5;
		
		if (re == 0)
			re = 1;
		
		return re;
	}
}
