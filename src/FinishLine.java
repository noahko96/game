import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the information of the finish line flags
 * 
 * @author Noah Over
 *
 */
public class FinishLine {
	private static final String APPEARANCE = "Mafia Mayhem flag.png";
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	
	/**
	 * constructor for FinishLine that initializes the image
	 */
	public FinishLine(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
	}
	
	/**
	 * getter for the image of the finish line flag
	 * 
	 * @return the image of the finish line flag
	 */
	public ImageView getImage(){
		return myAppearance;
	}

}
