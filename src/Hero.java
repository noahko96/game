import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that keeps track of the information on the hero
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
	 * constructor for Hero that initializes the image, the non-invincibility, the lives, and the damage per shot
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
	 * getter for the image of the hero
	 * 
	 * @return the image of the hero
	 */
	public ImageView getImage(){
		return myAppearance;
	}
	
	/**
	 * getter for the number of lives left with the hero
	 * 
	 * @return the number of lives left with the hero
	 */
	public int getLives(){
		return myLives;
	}
	
	/**
	 * takes a life away
	 */
	public void loseLife(){
		myLives--;
	}
	
	/**
	 * gives a life to the hero
	 */
	public void gainLife(){
		myLives++;
	}
	
	/**
	 * getter for the invincibility of the hero
	 * 
	 * @return the invincibility of the hero
	 */
	public boolean checkInvincibility(){
		return isInvincible;
	}
	
	/**
	 * gives the hero invincibility
	 */
	public void gainInvincibility(){
		isInvincible = true;
	}
	
	/**
	 * takes away the hero's invincibility
	 */
	public void loseInvincibility(){
		isInvincible = false;
	}
	
	/**
	 * getter for the damage the hero does with each of his bullets
	 * 
	 * @return the damage the hero does with each of his bullets
	 */
	public int checkDamagePerShot(){
		return myDamagePerShot;
	}
	
	/**
	 * doubles the amount of damage done by each of the hero's bullets
	 */
	public void doubleDamagePerShot(){
		myDamagePerShot = myDamagePerShot*DOUBLER;
	}
	
	/**
	 * resets the damage the hero does with each bullet to its original value
	 */
	public void resetDamagePerShot(){
		myDamagePerShot = INITIAL_DAMAGE;
	}

}