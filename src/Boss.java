//This entire file is part of my masterpiece.
//Noah Over
//This is part of my masterpiece because it is a subclass of the superclass Enemy that I decided to use for my code
//masterpiece.

/**
 * Class that keeps track of the Boss's information
 * Depends on the superclass Enemy and is used in SplashScreen3 and BossLevel
 * It can be used by one of the constructors; either Boss() or Boss(Mafia Mayhem boss.png, 10) will do the same thing
 * 
 * @author Noah Over
 *
 */
public class Boss extends Enemy{
	private static final String APPEARANCE = "Mafia Mayhem boss.png";
	private static final int NORMAL_HEALTH = 10;
	/**
	 * Constructor for Boss that calls the superclass Enemy constructor with default appearance and health for a boss
	 */
	public Boss(){
		super(APPEARANCE, NORMAL_HEALTH);
	}
	
	/**
	 * Constructor for Boss that calls the superclass Enemy constructor
	 * 
	 * @param appearance - the image used for the boss
	 * @param health - the health the boss will start with
	 */
	public Boss(String appearance, int health){
		super(appearance, health);
	}
}
