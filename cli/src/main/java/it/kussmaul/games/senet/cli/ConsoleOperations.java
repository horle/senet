package it.kussmaul.games.senet.cli;

import it.kussmaul.games.senet.base.logic.base.Game;
import it.kussmaul.games.senet.base.logic.base.Player;
import it.kussmaul.games.senet.base.logic.base.Turn;
import it.kussmaul.games.senet.base.logic.exception.GameException;
import it.kussmaul.games.senet.base.logic.exception.InvalidInputException;
import it.kussmaul.games.senet.base.logic.exception.WinException;
import it.kussmaul.games.senet.base.logic.token_field.Field;
import it.kussmaul.games.senet.base.logic.token_field.SpecialField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

/**
 * observer class for console operations
 * @author horle
 *
 */

public class ConsoleOperations implements Observer{

	private static Game game;
	
	public void initConsoleGame(){
		
		game = Game.getInstance();
		
		System.out.println("\nEnter your language:");
		
		String inputLan = "";
		
		BufferedReader bufferedR = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			String input = bufferedR.readLine().trim().toLowerCase();
			
			if (!InputVerifier.checkLanguageInput(input)){
				System.out.println("Language unknown or input failed. The language will be English (UK).");
				game.initLang("en");
			}
			else
				game.initLang(input);
			
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		
		System.out.println("\nHow many tokens?");
		
		int inputToken = 3;
		bufferedR = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			String input = bufferedR.readLine().trim();
			inputToken = InputVerifier.checkTokenInput(input);
				
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		String[] inputArray = new String[2];
		
		bufferedR = new BufferedReader(new InputStreamReader(System.in));
		
		while (inputArray[0] == null || inputArray [1] == null){
		
			try {
				
				System.out.println("\nIhre Namen, wertes Ungeziefer:");
				
				String input = bufferedR.readLine();
				
				if (InputVerifier.checkNameInput(input))
					inputArray = input.split(", ");
				
				else
					System.out.println("\nSo und nicht anders:\nNAME1, NAME2");
					
			} catch (IOException e2) {
				
				e2.printStackTrace();
			}
		}
		
		Player p1 = new Player(0, inputArray[0], inputToken, Color.BLUE);
		Player p2 = new Player(1, inputArray[1], inputToken, Color.RED);
		
		System.out.println("\n###########\tGAME INITIALISING\t###########\n");
		game = Game.setGameMode(Game.CONSOLE_MODE);
		
		try{
			game.initPlayers(p1, p2);
			game.initBoard();
			System.out.println("Board initialized.");
			game.initTokens();
			System.out.println("Tokens initialized.");
			
		}catch (GameException e){
			
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		System.out.println("Player 1 (\"" + p1.getName() + "\") added.");
		System.out.println("Player 2 (\"" + p2.getName() + "\") added.");		
		
		System.out.println("\n###########\tGAME INITIALISED\t###########\n");
		System.out.println("Launching game.\n");
		
		try{
			startGame();
		}catch(WinException w){
			finishGame();
		}
	}
	
	private synchronized void drawBoard(){

		ArrayList<Field> fields = game.getFields();
		
		for (int i = 0; i < 13; i++){
		
			if (i%4 == 0)
				System.out.println("-----------------------------------------------------------------------");
			
			else{
				
				if (i == 1){
					
					System.out.println("|      |      |      |      |      |      |      |      |      |      |");
					
					for (int j = 1; j < 10; j++)
						System.out.print("|  0" + j + "  ");
					
					System.out.print("|  10  |\n");
					
					for (int j = 0; j < 10; j++){
						
						String token = "    ";
						
						if (fields.get(j).isOccupied())
							token = "P" + (fields.get(j).getToken().getOwner().getId()+1) + "T" + (fields.get(j).getToken().getId()+1);
						
						System.out.print("| " + token + " ");
					}
					
					System.out.print("|\n");
					
				}
				if (i == 5){
					
					for (int j = 19; j > 9; j--){

						String desc = fields.get(j) instanceof SpecialField ? ((SpecialField) fields.get(j)).getSpecialDescriptionConsole() : "      ";
						
						System.out.print("|" + desc);
					}
					
					System.out.print("|\n");
					
					for (int j = 20; j > 10; j--)
						System.out.print("|  " + j + "  ");
					
					System.out.print("|\n");
					
					for (int j = 19; j > 9; j--){
						
						String token = "    ";
						
						if (fields.get(j).isOccupied())
							token = "P" + (fields.get(j).getToken().getOwner().getId()+1) + "T" + (fields.get(j).getToken().getId()+1);
						
						System.out.print("| " + token + " ");
					}
					
					System.out.print("|\n");
					
				}
				if (i == 9){
					
					for (int j = 20; j < 30; j++){

						String desc = fields.get(j) instanceof SpecialField ? ((SpecialField) fields.get(j)).getSpecialDescriptionConsole() : "      ";
						
						System.out.print("|" + desc);
					}
					
					System.out.print("|\n");
					
					for (int j = 21; j < 31; j++)
						System.out.print("|  " + j + "  ");
					
					System.out.print("|\n");
					
					for (int j = 20; j < 30; j++){
						
						String token = "    ";
						
						if (fields.get(j).isOccupied())
							token = "P" + (fields.get(j).getToken().getOwner().getId()+1) + "T" + (fields.get(j).getToken().getId()+1);
						
						System.out.print("| " + token + " ");
					}
					
					System.out.print("|\n");
					
				}
			}
		}
		
		Player p1 = game.getPlayers().get(0);
		Player p2 = game.getPlayers().get(1);
		
		System.out.println("\nSpieler 1 (\"" + p1.getName() + "\"): " + p1.getOutCount() + " draussen, " + p1.getGoalCount() + " im Ziel.");
		System.out.println("Spieler 2 (\"" + p2.getName() + "\"): " + p2.getOutCount() + " draussen, " + p2.getGoalCount() + " im Ziel.");
	}
	
	public void finishGame(){
		
		try{
			
			Player p = game.getWinner();
			
			System.out.println("GAME ENDS!\nPlayer " + (p.getId()+1) + " WINS Senet! Well played, " + p.getName() + "!\n");
			System.out.println("Restart game? (y, n)");
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String in = "";
			
			try {
				
				in = bufferedReader.readLine();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			if (Pattern.matches("(y|n)", in)){
				
				if (in.equals("y"))
					initConsoleGame();
				
				else{
					System.out.println("Goodbye.");
					System.exit(0);
				}
			}
		}catch(GameException ge){
			
			System.err.println(ge.getMessage());
		}
	}
	//TODO
	public void render(){
		
		try{			
			drawBoard();
			
			Turn turn = game.getTurn();

			System.out.print("\nIt's " + turn.getPlayer().getName() + " (P" + (turn.getPlayer().getId()+1) + ")'s turn now. ");
			System.out.println(turn.getPlayer().getName() + " diced " + turn.getStickScore() + ".");
			
			System.out.println("\nIhre Eingabe, werter Spieler:");
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String in = bufferedReader.readLine();
			
			checkInputString(in);
			
		}catch(InvalidInputException e){
			
			System.out.println(e.getMessage() + " Nene.\n");
			
		} catch(IOException e){
			
			System.out.println("Ungueltige Eingabe.\n");
		}
	}

	private void checkInputString(String input) throws InvalidInputException{
		
		if (Pattern.matches("(quit|help|recl|wint|move \\d\\d)", input.trim().toLowerCase())){
			
			switch (input.substring(0, 4)){		//commands of 4 chars
			
			case "wint":	//TODO: KILL					
				game.checkWin();
				game.DEBUGsetWinner(game.getPlayers().get(0));
				finishGame();
			
			case "help":
				printHelp();
				break;
			
			case "move":
				System.out.println("\n");
				InputVerifier.moveOperation(input.substring(5));
				break;
				
			case "recl":
				System.out.println("\n");
				InputVerifier.reclaimToken();
				break;
				
			case "quit":
				System.err.println("\nGame interrupted.");
				System.exit(0);
			}					
		}
		else{
			System.out.println("Invalid Input.");
			printHelp();
		}
	}


	private void startGame() throws WinException{
		
		game.addUIObserver(this);
		game.addGameObserver(this);
		game.game();
	}
	
	private void printHelp(){
		
		System.err.println("\nSENET SYNTAX HELP:\n");
		System.err.println("A valid command is of the form\nOPERATION(4) [SOURCE_FIELD(2)]\n");
		System.err.println("To move your token (e.g. token on field no. 8), type: \"move 08\".");
		System.err.println("For reclaiming one of your tokens from out field, type \"recl\".");
		System.err.println("To quit this bloody game of shit, simply type \"quit\".\n");
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg.getClass().equals(String.class))
			System.out.println((String) arg);
		
		else
			render();
	}
}