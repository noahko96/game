import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the information for the gun power ups
 * 
 * @author Noah Over
 *
 */
public class GunPowerUp {
	private static final String APPEARANCE = "Mafia Mayhem gun.png";
	private static final int WIDTH = 100;
	
	
	private ImageView myAppearance;
	private boolean isActive;
	
	/**
	 * constructor for the GunPowerUp the initializes the image and activity
	 */
	public GunPowerUp(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		isActive = true;
	}
	
	/**
	 * getter for the image of the gun
	 * 
	 * @return the image of the gun
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * getter for the activity of the gun
	 * 
	 * @return the activity of the gun
	 */
	public boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * makes the gun inactive
	 */
	public void notActive(){
		isActive = false;
	}

}
