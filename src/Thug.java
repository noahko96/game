import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps the information on the thugs
 * 
 * @author Noah Over
 *
 */
public class Thug {
	private static final String APPEARANCE = "Mafia Mayhem thug.png";
	private static final int INITIAL_HEALTH = 4;
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private int myHealth;
	private boolean isAlive;
	
	/**
	 * constructor for Thug that initializes the image, health, and life
	 */
	public Thug(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		myHealth = INITIAL_HEALTH;
		isAlive = true;
	}
	
	/**
	 * getter for the image of the thug
	 * 
	 * @return the image of the thug
	 */
	public ImageView getImage(){
		return  myAppearance;
	}
	
	/**
	 * getter for the health of the thug
	 * 
	 * @return the health of the thug
	 */
	public int getHealth(){
		return myHealth;
	}
	
	/**
	 * takes away the damage dealt to you by the bullet from your health
	 * 
	 * @param damage the damage dealt to you by the bullet
	 */
	public void loseHealth(int damage){
		myHealth -= damage;
	}
	
	/**
	 * tells whether the thug is alive or dead
	 * 
	 * @return the isAlive boolean
	 */
	public boolean getIsAlive(){
		return isAlive;
	}
	
	/**
	 * kills the thug
	 */
	public void die(){
		isAlive = false;
	}

}
