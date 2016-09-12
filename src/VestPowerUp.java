import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class for the information on the vest powerup
 * 
 * @author Noah Over
 *
 */
public class VestPowerUp {
	private static final String APPEARANCE = "Mafia Mayhem bullet proof vest.png";
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * constructor for VestPowerUp which initializes the image and activity
	 */
	public VestPowerUp(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * getter for the image of the bulletproof vest
	 * 
	 * @return the image of the bulletproof vest
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * getter for the activity of the bulletproof vest
	 * 
	 * @return the isActive boolean
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the bulletproof vest inactive
	 */
	public void notActive(){
		isActive = false;
	}
}
