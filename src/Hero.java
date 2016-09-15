import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that keeps track of the information on the hero.
 * It is called by Level1 and BossLevel.
 * It can be used by calling the constructor (e.g., Hero()).
 * 
 * @author Noah Over
 *
 */
public class Hero {
	private static final int INITIAL_LIVES = 3;
	private static final int HERO_WIDTH = 100;
	private static final int INITIAL_DAMAGE = 1;
	private static final String APPEARANCE = "Mafia Mayhem hero.png";
	private static final int DOUBLER = 2;
	
	
	private int myLives;
	private ImageView myAppearance;
	private boolean isInvincible;
	private int myDamagePerShot;
	
	/**
	 * Constructor for Hero that initializes the image, the non-invincibility, the lives, and the damage per shot
	 */
	public Hero (){
		myLives = INITIAL_LIVES;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
        myAppearance = new ImageView(image);
        myAppearance.setFitWidth(HERO_WIDTH);
        myAppearance.setPreserveRatio(true);
        isInvincible = false;
        myDamagePerShot = INITIAL_DAMAGE; 
	}
	
	/**
	 * Getter for the image of the hero
	 * 
	 * @return the image of the hero
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * Getter for the number of lives left with the hero
	 * 
	 * @return the number of lives left with the hero
	 */
	public int getLives(){
		return myLives;
	}
	
	/**
	 * Takes a life away from the hero's total lives remaining
	 */
	public void loseLife(){
		myLives--;
	}
	
	/**
	 * Gives a life to the hero by adding it to his total lives
	 */
	public void gainLife(){
		myLives++;
	}
	
	/**
	 * Getter for the invincibility of the hero
	 * 
	 * @return the invincibility of the hero
	 */
	public boolean checkInvincibility(){
		return isInvincible;
	}
	
	/**
	 * Gives the hero invincibility by changing the isInvincible boolean to true
	 */
	public void gainInvincibility(){
		isInvincible = true;
	}
	
	/**
	 * Takes away the hero's invincibility by switching the isInvincible boolean to false
	 */
	public void loseInvincibility(){
		isInvincible = false;
	}
	
	/**
	 * Getter for the damage the hero does with each of his bullets
	 * 
	 * @return the damage the hero does with each of his bullets
	 */
	public int checkDamagePerShot(){
		return myDamagePerShot;
	}
	
	/**
	 * Doubles the amount of damage done by each of the hero's bullets
	 */
	public void doubleDamagePerShot(){
		myDamagePerShot = myDamagePerShot*DOUBLER;
	}
	
	/**
	 * Resets the damage the hero does with each bullet to its original value
	 */
	public void resetDamagePerShot(){
		myDamagePerShot = INITIAL_DAMAGE;
	}

}