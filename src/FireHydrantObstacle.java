

/**
 * Class that keeps track of the information on the fire hydrants.
 * Depends on the superclass Obstacle and is used in Level1.
 * Can be used by calling one of the constructors; FireHydrantObstacle() or FireHydrantObstacle("Mafia Mayhem fire
 * hydrant.png") which both give you the same result.
 * 
 * @author Noah Over
 *
 */
public class FireHydrantObstacle extends Obstacle {
	public static final String APPEARANCE = "Mafia Mayhem fire hydrant.png";
	
	/**
	 * Constructor for FireHydrantObstacle which calls the superclass Obstacle constructor with the default 
	 * appearance
	 */
	public FireHydrantObstacle(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for FireHydrantObstacle which calls the superclass Obstacle constructor
	 * 
	 * @param appearance - the String which represents the image of the fire hydrant
	 */
	public FireHydrantObstacle(String appearance){
		super(appearance);
	}
}
