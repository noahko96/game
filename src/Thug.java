//This entire file is part of my masterpiece.
//Noah Over
//This file is part of my masterpiece because it is a subclass of Enemy which I decided to use for my code 
//masterpiece.

/**
 * Class that keeps the information on the thugs.
 * Depends on the superclass Enemy and is called by Level1, BossLevel, and SplashScreen2.
 * It can be used by calling one of the constructors; Thug() or Thug("Mafia Mayhem thug.png", 4) both of which give
 * you the same result.
 * 
 * @author Noah Over
 *
 */
public class Thug extends Enemy {
	private static final String APPEARANCE = "Mafia Mayhem thug.png";
	private static final int INITIAL_HEALTH = 4;
	
	/**
	 * Constructor for Thug that calls the constructor for the superclass Enemy using the default appearance and 
	 * health.
	 */
	public Thug(){
		super(APPEARANCE, INITIAL_HEALTH);
	}
	
	/**
	 * Constructor for Thug that calls the constructor for the superclass Enemy
	 * 
	 * @param appearance - the String that represents the image of the thug
	 * @param health - the initial amount of health the thug will have
	 */
	public Thug(String appearance, int health){
		super(appearance, health);
	}
}
