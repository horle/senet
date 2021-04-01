package it.kussmaul.games.senet.base.logic.base;

import it.kussmaul.games.senet.base.logic.exception.GameException;
import it.kussmaul.games.senet.base.logic.exception.InvalidInputException;
import it.kussmaul.games.senet.base.logic.exception.WinException;
import it.kussmaul.games.senet.base.logic.token_field.*;

import java.util.*;

	/**
	 * singleton game class
	 * 
	 * @author horle
	 */
public class Game extends Observable{

	public static final int CONSOLE_MODE = 0;
	public static final int GRAPHIC_MODE = 1;
	public static final int NW_CONSOLE_MODE = 2;
	public static final int NW_GRAPHIC_MODE = 3;
	
	public static int tokenCount;
	public static boolean fin = false;
	
	private static Game instance;
	private static int gameMode;
	private ArrayList<Observer> uiObservers = new ArrayList<Observer>();
	private ArrayList<Observer> gameObservers = new ArrayList<Observer>();
	
	private static ArrayList<Player> players;
	private static ArrayList<Field> fields;
	private static Turn turn;
	private int round;
	
	private Player winner = null;
	
	
	private Locale locale;
	private ResourceBundle messages;
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}
	
	/**
	 * adds new UI observer to list
	 */
	public void addUIObserver(Observer o){
		
		uiObservers.add(o);
	}
	
	/**
	 * adds new game observer to list
	 */
	public void addGameObserver(Observer o){
		
		gameObservers.add(o);
	}
	
	/**
	 * notifies observers for updating UI 
	 */
	public void notifyUIObservers(){
		
		for(Observer ob : uiObservers)
			ob.update(this, this);
	}
	
	/**
	 * notifies observes for game messages
	 */
	public void notifyGameObservers(String msg) {
		
		for(Observer ob : gameObservers)
			ob.update(this, msg);
	}
	
	/**
	 * @return singleton object of game instance
	 */
	public static Game getInstance(){
		
		if(instance == null)
			instance = new Game();
		return instance;
	}
	
	public Turn getTurn(){
		
		return turn;
	}
	
	/**
	 * toggles turn of players, increases round counter each two turns 
	 * returns stick score
	 */
	public int toggleTurn(){
		
		turn.toggle();
		
		if (turn.getPlayer().getId() == 0)
			round ++;
		
		return turn.getStickScore();
	}
	
	/**
	 * checks, if stick score = 0. if so, toggles turn.
	 * @return false, if stick score = 0
	 */
	public int checkSticks(){
		
		int possibleMoves = tokenCount;
		int fieldsToGoal = -1;
		
		int stickScore = turn.getStickScore();
		Player p = turn.getPlayer();
		
		boolean canGetFromOut = true;
		
		if (stickScore == 0){
			
			toggleTurn();
			return 0;
		}
		
		else{
			
			for (Token t : p.getTokens()){
				
				Field target = null;
				
				if (t.getField() != null){
				
					if (t.getField().getId() + stickScore <= 30)
						target = fields.get(t.getField().getId() + stickScore);
					
					else{
						
						possibleMoves --;	//does not get to goal, diced too big; or already in goal
						continue;
					}
				}
				
				if ( t.isOut() && fields.get( stickScore -1 ).isOccupiedBy(p) ){	//"getting-from-out"-field is occupied by own token
					
					canGetFromOut = false;
					possibleMoves --;
					continue;
				}
				
				if ( (t.getField() != null &&
						target.isOccupiedBy(p)) || 
						(t.getField() != null &&
						target.isSafe() &&
						target.isOccupied()) ){	//target field is occupied by own or an occupied safe field
					
					possibleMoves --;
					continue;
				}
				
				if ( !t.isOut() && t.getField().getId() + stickScore > 30 ){		//target field out of range
					
					fieldsToGoal = 30 - t.getField().getId();
					possibleMoves --;
					continue;
				}
			}
			
			if (canGetFromOut && p.getOutCount() > 1)						// just one option to reclaim a token
				possibleMoves -= (p.getOutCount() -1);
			
//			System.out.println("possible moves: " + possibleMoves);
			
			if (possibleMoves == 0 && fieldsToGoal == -1){
				
				toggleTurn();
				return -1;
			}
			else if (possibleMoves == 0 && fieldsToGoal != -1)
				return fieldsToGoal;

			return 10;				
		}
	}
	
	public int getRound() {
		return round;
	}

	/**
	 * sets game mode, i.e. graphic, console or network
	 * @param mode int value of game mode
	 * @return game instance
	 */
	public static Game setGameMode(int mode){
		
		if (mode > -1) 
			gameMode = mode;
		else	
			gameMode = -1;
		
		return getInstance();
	}	
	
	public static int getGameMode() {
		return gameMode;
	}
	
	public boolean initLang(String in){
		
		boolean loc = buildLocale(in);
		try {
			messages = ResourceBundle.getBundle("lang", locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loc;
	}
	
	/**
	 * creates players, sets turn to first player
	 * @param p1	first player
	 * @param p2	second player
	 * @return		true, if both players != null
	 */
	public boolean initPlayers(Player p1, Player p2) throws GameException{
		
		if (p1 != null && p2 != null){
			
			players = new ArrayList<Player>();
			
			players.add(p1);
			players.add(p2);
			
			turn = new Turn(players);
			
			return true;
		}
		else
			throw new GameException("Creating players failed.");
	}
	
	/**
	 * creates fields
	 */
	public void initBoard(){
		
		fields = new ArrayList<Field>(30);

		for (int i = 0; i < 30; i++){
			
			switch (i){
			
			case 14: case 25: case 26: case 27: case 28: case 29:
				fields.add(new SpecialField(i));
				continue;
				
			default:
				fields.add(new Field(i));
			}
		}
		fields.add(new GoalField(30));
	}
	
	/**
	 * spreads tokens across the board
	 */
	public void initTokens(){
		
		for (Token t : players.get(0).getTokens()){
			
			Field field = fields.get(t.getId()*2);
			
			TokenOperations.updateReference(field, t);
		}
		
		for (Token t : players.get(1).getTokens()){
			
			Field field = fields.get(t.getId()*2+1);
			
			TokenOperations.updateReference(field, t);
		}
		
		tokenCount = players.get(0).getTokens().size();
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Field> getFields(){
		
		return fields;
	}
	
	/**
	 * checks, if reclaim action is valid.
	 * @return true, if reclaim is valid
	 * @throws InvalidInputException
	 */
	public boolean verifyReclaim() throws InvalidInputException{
		
		if (turn.getPlayer().getOutCount() < 1)
			throw new InvalidInputException("None of your tokens is out.");
		
		return true;
	}
	
	/**
	 * checks, if move is valid.
	 * @param source source field number
	 * @param target target field number
	 * @return true, if move is valid.
	 * @throws InvalidInputException
	 */
	public boolean verifyMove(int source, int target) throws InvalidInputException {
		
		if (!fin){
			if (source < 1 || source > 30)
				throw new InvalidInputException("Number out of range. Only 1 - 30 is allowed.");
			
			if (target > 31)
				throw new InvalidInputException("You cannot move this token. Take another one or reclaim one from out!");

			if (getFields().get(source-1).getToken() == null)
				throw new InvalidInputException("Field is not occupied.");
				
			return true;
		}
		return false;
	}
	
	/**
	 * checks, if there is a winner.
	 * @return true, if game has a winner.
	 */
	public boolean checkWin() {
		
		Player p1 = getPlayers().get(0);
		Player p2 = getPlayers().get(1);
		
		if (p1.getGoalCount() == tokenCount){
			
			fin = true;
			winner = p1;
			return true;
		}
		if (p2.getGoalCount() == tokenCount){
			
			fin = true;
			winner = p2;
			return true;
		}
		return false;
	}
	
	/**
	 * ONLY FOR DEBUG PURPOSES
	 * sets p as winner
	 * @param p player that is about to win
	 */
	public void DEBUGsetWinner(Player p){
		winner = p;
	}
	
	public Player getWinner() throws GameException{
		
		if(winner != null)
			return winner;
		else
			throw new GameException("winner is NULL!!!");
	}
	
//	private static void checkSticks() {
//		
//		String p = getTurn().getPlayer().getName();
//		int diced = game.getTurn().getStickScore();
//		int num = game.checkSticks();
//		
//		if (num == -1){
//			
//			System.out.println("\n" + p + ", there are no possible moves for any of your tokens! Diced: " + diced);
//		}
//		else if (num == 0){
//			
//			System.out.println("\n" + p + ", you diced 0 and have to sit out! What a shame.\n");
//			checkSticks();
//		}
//		else if (num != 10){	// last token cant be moved because stick score doesnt match remaining field count to goal
//				
//			System.out.println("\n" + p + ", your token can't be moved. You need a " + num + " to reach the goal.");
//		}
//	}
	
	/**
	 * executes query
	 * @param q SenetQuery
	 * @return true, if success
	 * @throws InvalidInputException if query is invalid or impossible
	 */
	public boolean updateGame(SenetQuery q) throws InvalidInputException {
		
		switch(q.getCode()){
		
		case SenetQuery.MOVE_OPERATION:
			
			if (q.getDiced() == 0){
			
				toggleTurn();
			}
			
			return TokenOperations.changeTokenPosition(
					q.getTarget(),
					q.getToken());
			
		case SenetQuery.RECLAIM_OPERATION:
			
			return TokenOperations.reclaimToken(
					turn.getPlayer(),
					getFields().get(turn.getStickScore()-1));
			
		default:
			return false;
		}
	}
	
	/**
	 * the main round function. notifies observers of game events for updating UI
	 * @throws WinException if game has ended
	 */
	public void game() throws WinException {
		
		winner = null;
		toggleTurn();
		
		while(!fin){
			
			if(checkWin())
				throw new WinException();
			
			String p = turn.getPlayer().getName();
			int diced = turn.getStickScore();
			int num = checkSticks();
			
			if (num == -1){
				
				notifyGameObservers("\n" + p + ", there are no possible moves for any of your tokens! Diced: " + diced);
			}
			else if (num == 0){
				
				notifyGameObservers("\n" + p + ", you diced 0 and have to sit out! What a shame.\n");
				continue;
			}
			else if (num != 10){	// last token can't be moved because stick score doesn't match remaining field count to goal
					
				notifyGameObservers("\n" + p + ", your token can't be moved. You need a " + num + " to reach the goal.");
			}
			
			this.notifyUIObservers();
		}
	}

	public boolean buildLocale(String input) {
		
		switch(input){
		case "en":
			locale = new Locale(input, "UK");
			notifyGameObservers("Language set to English (UK).");
			return true;
		case "de":
			locale = new Locale(input, "DE");
			notifyGameObservers("Sprache auf Deutsch (DE) gesetzt.");
			return true;
		default:
			locale = new Locale("en", "US");
			return false;
		}
	}

}
