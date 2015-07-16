package senet.logic.token_field;

import senet.logic.base.Game;
import senet.logic.base.Player;
import senet.logic.exception.InvalidInputException;

/**
 * abstract class for static operations on tokens 
 * @author horle
 *
 */
public abstract class TokenOperations {
	
	public static final byte FIELD_ERROR = -1;
	public static final byte FIELD_NOT_OCCUPIED = 0;
	public static final byte FIELD_OCCUPIED_BY_OWN = 1;
	public static final byte FIELD_OCCUPIED_BY_OTHER = 2;
	public static final byte FIELD_SAFE = 3;
	public static final byte FIELD_WATER = 4;
	
	private static Game game = Game.getInstance();
	
	/**
	 * updates token and field attributes, does not consider occupance.
	 * sets token onto target, old field is set empty.
	 * do not use instead of init!
	 * 
	 * @param t the token to be moved
	 * @param target the target field the token is moved to
	 */
	public static void updateReference(Field target, Token t){
		
		Field old = t.getField();
		
		if (target != null)
			target.setToken(t);
		t.setField(target);
		
		if(old != null)
			old.setToken(null);
	}
	
	/**
	 * moves token to new field, if correct turn. notifies game observers.
	 * 
	 * @param target targeted field
	 * @param t token to be moved
	 * @return true, if token could be moved and turn is to be changed. False, if target is occupied by own token or error.
	 * @throws InvalidInputException 
	 */
	public static synchronized boolean changeTokenPosition(Field target, Token t) throws InvalidInputException{
		
		if (hasTurn(t)){
		
			switch (estimateOccupance(target, t)){
				
				case FIELD_ERROR:						//error
					throw new InvalidInputException("Some sort of error!");
					
				case FIELD_NOT_OCCUPIED:				//empty and no special
					updateReference(target, t);
					game.toggleTurn();
					return true;
					
				case FIELD_OCCUPIED_BY_OWN:				// own token
					throw new InvalidInputException("Field is occupied by your own token. Token can't be moved. Choose another field!");
					
				case FIELD_OCCUPIED_BY_OTHER:			//token of other player
					target.getToken().getOwner().increaseOutCount();
					target.getToken().toggleOut();
					
					String kickedPlayer = target.getToken().getOwner().getName();
					
					updateReference(null, target.getToken());		//old token to out field (null)
					updateReference(target, t);						//new token to target field
					
					game.notifyGameObservers("You kicked Player " + kickedPlayer + "'s token!");
					game.toggleTurn();
					
					return true;
					
				case FIELD_WATER:						//water trap
					
					String trap_prefix = "You fell into the water trap! ";
					
					if (!Game.getInstance().getFields().get(14).isOccupied()){
						
						game.notifyGameObservers(trap_prefix+"Your token is moved to the House of Rebirth.");
						changeTokenPosition(Game.getInstance().getFields().get(14), t);
					}
					else{
						
						game.notifyGameObservers(trap_prefix+"Your token is kicked out, because the House of Rebirth is occupied.");
						changeTokenPosition(null, t);		//out if rebirth is occupied
						t.getOwner().increaseOutCount();
						t.toggleOut();
					}
					
					return true;
					
				case FIELD_SAFE:
					throw new InvalidInputException("Target field is a safe field. Token can't be kicked. Choose another token (field)!");
					
			}
		}
		else
			throw new InvalidInputException("It's not your token!");
		
		return false;	//else
			
	}
	
	/**
	 * considers occupance of a field
	 * 
	 * @param target
	 * @param t
	 * @return 0 if free,<br>
	 * 1 if occupied and same player,<br>
	 * 2 if token belongs to other player and is to be kicked,<br>
	 * -1 else
	 */
	private static byte estimateOccupance(Field target, Token t){
		
		if (t != null){
			
			if (target == null){	//out
				
				return FIELD_NOT_OCCUPIED;
			}
			
			if (!target.isOccupied()){
				
				switch (target.getId()){
				
				case 26:
					return FIELD_WATER;
					
				default:
					return FIELD_NOT_OCCUPIED;
				}
			}
			
			else{	//occupied
				
				if (target.getToken().getOwner().equals(t.getOwner())){		//target-token belongs to same player
					
					return FIELD_OCCUPIED_BY_OWN;
				}
				else if (!target.getToken().getOwner().equals(t.getOwner())){	//target-token belongs to other player -> kick
					
					switch (target.getId()){
					
					case 14: case 25: case 27: case 28: case 29:
						return FIELD_SAFE;
					
					default:
						return FIELD_OCCUPIED_BY_OTHER;
					}
				}
			}
		}
		
		return FIELD_ERROR;		//error?
	}
	
	private static boolean hasTurn(Token t){
		
		return (t.getOwner().equals(Game.getInstance().getTurn().getPlayer()));
	}

	public static boolean reclaimToken(Player player, Field target) throws InvalidInputException {
		
		Token token = null;
		
		for (Token t : player.getTokens())
			if (t.isOut()){
				
				token = t;
				break;
			}
				
		if (token != null && changeTokenPosition(target, token)){
			
			token.toggleOut();
			player.decreaseOutCount();
			game.notifyGameObservers(token.getOwner().getName()+", your token made it back to the game!");
			return true;
		}
		
		return false;
	}
}
