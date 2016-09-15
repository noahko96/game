

/**
 * Class that keeps track of the information on the dogs
 * It is used in Level1 and BossLevel.
 * You can use it by calling one of the constructors; DogObstacle() or DogObstacle("Mafia Mayhem dog.png") which give
 * you the same result.
 * 
 * @author Noah Over
 *
 */
public class DogObstacle extends Obstacle{
	private static final String APPEARANCE = "Mafia Mayhem dog.png";
	
	/**
	 * Constructor for DogObstacle that calls the Obstacle superclass constructor using the default appearance
	 */
	public DogObstacle(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for DogObstacle that calls the Obstacle superclass constructor
	 * 
	 * @param appearance - the image used for the DogObstacle
	 */
	public DogObstacle(String appearance) {
		super(appearance);
	}

	
	
}
