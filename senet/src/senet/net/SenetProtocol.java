package senet.net;

import senet.logic.base.Game;
import senet.logic.exception.InvalidInputException;
import senet.logic.token_field.SenetQuery;

public class SenetProtocol {

	//private static final int 
	public void processInput(String input) throws InvalidInputException{
		
//		if (Pattern.matches("SYN", input.substring(0, 3))){
//			
//			String syn = input.substring(3);
//			
//			ArrayList<Player> players = Game.getInstance().getPlayers();
//			ArrayList<Field> fields = Game.getInstance().getFields();
//			
//			if (Pattern.matches("[[0-2]\\d|of|gf]{" + Game.tokenCount*2 + "}[01][01235]", syn)){
//				
//				for (int i = 0; i < Game.tokenCount*2; i++){		//fields
//					
//					if (i < 5){
//						
//						if (players.get(0).getTokens().get(i).getField().getId() ==
//					}
//				}
//			}
//			
//			
//			
//			return "";
//		}
		
		if (input.startsWith("MOV")){
			
			String mov = input.substring(3);
			
			int pID = mov.charAt(0);
			int tID = mov.charAt(1);
			
			int fID = -1;	//out
			
			if (!mov.substring(2, 4).equals("of"))
				fID = Integer.parseInt(mov.substring(2, 4));
			
			int dice = Integer.parseInt(mov.substring(4));
			
			SenetQuery query = new SenetQuery(fID >= 0 ? Game.getInstance().getFields().get(fID) : null,
					Game.getInstance().getPlayers().get(pID).getTokens().get(tID),
					dice);
			
			Game.getInstance().updateGame(query);
		}
		
		else if (input.startsWith("CHA")) {
			
			//CHAT
		}
		
		else if (input.startsWith("SYS")) {		//system messages like win
			
			String sys = input.substring(3);
			
			if (sys.startsWith("WIN")){
				
				Integer.parseInt(sys.substring(3)); // player id
			}
		}
	}
}
