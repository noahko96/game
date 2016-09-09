import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GunPowerUp {
	public static final String APPEARANCE = "Mafia Mayhem gun";
	
	private ImageView myAppearance;
	
	public GunPowerUp(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
	}
	
	public ImageView getImage(){
		return myAppearance;
	}

}
