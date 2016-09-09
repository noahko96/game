import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrashCanObstacle {
	public static final String APPEARANCE = "Mafia Mayhem trashcan";
	
	private ImageView myAppearance;
	
	public TrashCanObstacle(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
	}
	
	public ImageView getImage(){
		return myAppearance;
	}
}
