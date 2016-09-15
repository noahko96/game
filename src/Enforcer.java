//This entire file is part of my masterpiece.
//Noah Over
//This file is part of my masterpiece because it is a subclass of Enemy which I decided to use for my code 
//masterpiece.

/**
 * Class that keeps track of the information on the enforcers
 * Depends on the superclass Enemy and is used in Level1 and BossLevel.
 * It can be used by calling one of the constructors; Enforcer() or Enforcer("Mafia Mayhem enforcer.png", 8) which 
 * will get you the same result.
 * 
 * @author Noah Over
 *
 */
public class Enforcer extends Enemy{
	private static final String APPEARANCE = "Mafia Mayhem enforcer.png";
	private static final int INITIAL_HEALTH = 8;
	
	/**
	 * Constructor for Enforcer that calls the superclass Enemy constructor with the default appearance and health
	 */
	public Enforcer(){
		super(APPEARANCE, INITIAL_HEALTH);
	}
	
	/**
	 * Constructor for Enforcer that calls the superclass Enemy constructor
	 * 
	 * @param appearance - the String that represents the image of the enforcer
	 * @param health - the health the enforcer starts with
	 */
	public Enforcer(String appearance, int health){
		super(appearance, health);
	}
}
