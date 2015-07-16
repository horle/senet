package senet.logic.token_field;

import java.util.ResourceBundle;

import senet.logic.base.Game;

/**
 * class for fields with special function
 * @author horle
 *
 */
public class SpecialField extends Field {
	
	private String specialConsole;
	private ResourceBundle m;
	
	public SpecialField(int id){
		
		super(id);
		m = Game.getInstance().getMessages();
		initSpecialDescription();
	}
	
	/**
	 * initialises special description of the field
	 */
	private void initSpecialDescription(){
		
		switch (this.id){
		
		case 14:
			description += m.getString("field15Description");
			specialConsole = m.getString("field15DescriptionConsole");
			this.isSafe = true;
			break;
		case 25:
			description += m.getString("field26Description");
			specialConsole = m.getString("field26DescriptionConsole");
			this.isSafe = true;
			break;
		case 26:	//water
			description += m.getString("field27Description");
			specialConsole = m.getString("field27DescriptionConsole");
			break;
		case 27:
			description += m.getString("field28Description");
			specialConsole = m.getString("field28DescriptionConsole");
			this.isSafe = true;
			break;
		case 28:
			description += m.getString("field29Description");
			specialConsole = m.getString("field29DescriptionConsole");
			this.isSafe = true;
			break;
		case 29:
			description += m.getString("field30Description");
			specialConsole = m.getString("field30DescriptionConsole");
			this.isSafe = true;
			break;
		}
	}
	
	public String getSpecialDescriptionConsole(){
		
		return specialConsole;
	}
}
