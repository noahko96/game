import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the statistics for the dogs
 * 
 * @author Noah Over
 *
 */
public class Dog {
	private static final String APPEARANCE = "Mafia Mayhem dog.png";
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * constructor for dog that initializes the appearance and activity
	 */
	public Dog(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * getter for the image of the dog
	 * 
	 * @return the image of the dog
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * get for the activity of the dog
	 * 
	 * @return the activity of the dog
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the dog inactive
	 */
	public void notActive(){
		isActive = false;
	}

}
