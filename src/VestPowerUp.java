import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VestPowerUp {
	public static final String APPEARANCE = "Mafia Mayhem bullet proof vest";
	
	private ImageView myAppearance;
	
	public VestPowerUp(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
	}
	
	public ImageView getImage(){
		return myAppearance;
	}
}
