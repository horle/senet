package it.kussmaul.games.senet.base.logic.token_field;

/**
 * goal field class
 * @author horle
 *
 */
public class GoalField extends Field {

	public GoalField(int id){
		
		super(id);
	}
	
	public void setToken(Token t){
		
		System.out.println(t.getOwner().getName() + "'s token reached the goal!");
		
		t.getOwner().increaseGoalCount();
		this.token = null;
	}
}
