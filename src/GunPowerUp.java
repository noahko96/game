

/**
 * Class that keeps track of the information on the gun power ups
 * Depends on the superclass Obstacle and is called by Level1, BossLevel, and SplashScreen2.
 * It can be used by calling one of the constructors; GunPowerUp() or GunPowerUp("Mafia Mayhem gun.png") which both
 * accomplish the same thing.
 * 
 * @author Noah Over
 *
 */
public class GunPowerUp extends Obstacle {
	private static final String APPEARANCE = "Mafia Mayhem gun.png";
	
	/**
	 * Constructor for GunPowerUp that calls the superclass Obstacle constructor with the default appearance
	 */
	public GunPowerUp(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for GunPowerUp that calls the superclass Obstacle constructor
	 * 
	 * @param appearance - the String which represents the image of the gun
	 */
	public GunPowerUp(String appearance){
		super(appearance);
	}
}
