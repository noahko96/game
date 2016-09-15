

/**
 * Class for the information on the vest powerup.
 * Depends on the superclass Obstacle and is called by Level1 and BossLevel.
 * It can be used by calling one of the constructors; VestPowerUp() or VestPowerUp("Mafia Mayhem bullet proof 
 * vest.png") which both give you the same result.
 * 
 * @author Noah Over
 *
 */
public class VestPowerUp extends Obstacle{
	private static final String APPEARANCE = "Mafia Mayhem bullet proof vest.png";
	
	/**
	 * Constructor for VestPowerUp that calls the superclass Obstacle constructor using the default appearance.
	 */
	public VestPowerUp(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for VestPowerUp that calls the superclass Obstacle constructor
	 * 
	 * @param appearance - the String that represents the appearance of the bullet proof vest
	 */
	public VestPowerUp(String appearance){
		super(appearance);
	}
}
