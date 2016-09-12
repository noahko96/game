import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the information on the fire hydrants
 * 
 * @author Noah Over
 *
 */
public class FireHydrant {
	public static final String APPEARANCE = "Mafia Mayhem fire hydrant.png";
	public static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * constructor for FireHydrant that initializes the image and activity
	 */
	public FireHydrant(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * getter for the image of the fire hydrant
	 * 
	 * @return the image of the fire hydrant
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * getter for the activity of the fire hydrant
	 * 
	 * @return the activity of the fire hydrant
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the fire hydrant inactive
	 */
	public void notActive(){
		isActive = false;
	}

}
