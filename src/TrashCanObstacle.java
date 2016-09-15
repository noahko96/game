

/**
 * The class which keeps the information on the trash can obstacle.
 * Depends on the superclass Obstacle and is called in Level1 and SplashScreen2.
 * It can be used by calling one of the constructors; TrashCanObstacle() or TrashCanObstacle("Mafia Mayhem 
 * trashcan.png") which both get the same result.
 * 
 * @author Noah Over
 *
 */
public class TrashCanObstacle extends Obstacle{
	private static final String APPEARANCE = "Mafia Mayhem trashcan.png";
	
	/**
	 * Constructor for TrashCanObstacle which calls the superclass Obstacle constructor using the default appearance.
	 */
	public TrashCanObstacle(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for TrashCanObstacle which calls the superclass Obstacle constructor
	 * 
	 * @param appearance - the String that represents the image of the trash can
	 */
	public TrashCanObstacle(String appearance) {
		super(appearance);
	}
	
}
