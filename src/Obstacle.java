import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Superclass that keeps the information on obstacles.
 * Its subclasses are DogObstacle, FireHydrantObstacle, TrashCanObstacle, GunPowerUp, VestPowerUp, and FinishLine.
 * It is called by Level1 and BossLevel.
 * It can be used by calling the constructor (e.g., Obstacle("Mafia Mayhem dog.png")).
 * 
 * @author Noah Over
 *
 */
public class Obstacle {
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * Constructor for Obstacle which initializes the image and activity
	 * 
	 * @param appearance - the String which represents the image of the obstacle
	 */
	public Obstacle(String appearance){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(appearance));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * Getter for the image of the obstacle
	 * 
	 * @return the image of the obstacle
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * Getter for the activity of the obstacle
	 * 
	 * @return the isActive boolean
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * Makes the obstacle inactive by switching the isActive boolean to false
	 */
	public void notActive(){
		isActive = false;
	}
}
