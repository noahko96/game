import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enforcer {
	public static final String APPEARANCE = "Mafia Mayhem enforcer.jpg";
	public static final int INITIAL_HEALTH = 2;
	
	private ImageView myAppearance;
	private int myHealth;
	
	public Enforcer(){
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
	
	public void loseHealth(int healthLost){
		myHealth -= healthLost;
	}
	
	public void loseAHealth(){
		loseHealth(1);
	}
}
