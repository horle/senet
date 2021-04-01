package it.kussmaul.games.senet.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * test class for console game
 * @author horle
 *
 */
public class ConsoleSenet {

	public static void main(String[] args) {
		
		String input = "";
		ConsoleOperations op = new ConsoleOperations();
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Lokales Spiel (a) oder Netzwerk (b)?");
		
		try {
			input = buff.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (Pattern.matches("[ab]", input)){
			
			if (input.contains("a"))
				op.initConsoleGame();

		}		
	}
}
