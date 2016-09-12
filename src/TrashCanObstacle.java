import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * the class which keeps the information on the trash can obstacle
 * 
 * @author Noah Over
 *
 */
public class TrashCanObstacle {
	private static final String APPEARANCE = "Mafia Mayhem trashcan.png";
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * constructor for TrashCanObstacle which initializes the image and activity
	 */
	public TrashCanObstacle(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * getter for the image of the trash can
	 * 
	 * @return the image of the trash can
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * getter for the activity of the trash can
	 * 
	 * @return the isActive boolean
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the trash can inactive
	 */
	public void notActive(){
		isActive = false;
	}
}
