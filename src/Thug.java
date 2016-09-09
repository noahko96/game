import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Thug {
	public static final String APPEARANCE = "Mafia Mayhem thug.jpg";
	public static final int INITIAL_HEALTH = 1;
	
	private ImageView myAppearance;
	private int myHealth;
	
	public Thug(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myHealth = INITIAL_HEALTH;
	}
	
	public ImageView getImage(){
		return  myAppearance;
	}
	
	public int getHealth(){
		return myHealth;
	}
	
	public void loseHealth(){
		myHealth--;
	}

}
