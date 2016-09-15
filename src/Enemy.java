//This entire file is part of my masterpiece.
//Noah Over
//I put this superclass as part of my masterpiece because I believe that it greatly represents what I have learned so
//far in this class. When I first submitted this code on Sunday 9/11, I did not have any superclasses. It was just not 
//something I really thought of and when we discussed superclasses in class on Tuesday 9/13, I realized my code 
//could have used several superclasses so I added a few and decided to use this one as part of my code masterpiece.

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Superclass the keeps track of information on different types of enemies
 * Boss, Thug, and Enforcer are all subclasses and it is used in BossLevel and Level1.
 * It can be used by calling the constructor (e.g., Enemy("Mafia Mayhem thug.png", 4))
 * 
 * @author Noah Over
 *
 */
public class Enemy {
	private static final int WIDTH = 100;
	
	private ImageView myAppearance;
	private int myHealth;
	private boolean isAlive;
	
	/**
	 * Constructor for Enemy that initializes the image, health, and life
	 * 
	 * @param appearance - the String which represents the image of the enemy
	 * @param health - the amount of health the enemy will have at the beginning
	 */
	public Enemy(String appearance, int health){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(appearance));
		myAppearance = new ImageView(image);
		myAppearance.setFitWidth(WIDTH);
		myAppearance.setPreserveRatio(true);
		myHealth = health;
		isAlive = true;
	}
	
	/**
	 * Getter for the image of the enemy
	 * 
	 * @return the image of the enemy
	 */
	public ImageView getImage(){
		return  myAppearance;
	}
	
	/**
	 * Getter for the health of the enemy
	 * 
	 * @return the health of the enemy
	 */
	public int getHealth(){
		return myHealth;
	}
	
	/**
	 * Takes away the damage dealt to the enemy by the bullet from the enemy's health
	 * 
	 * @param damage - the damage dealt to the enemy by the bullet
	 */
	public void loseHealth(int damage){
		myHealth -= damage;
	}
	
	/**
	 * Tells whether the enemy is alive or dead
	 * 
	 * @return the isAlive boolean
	 */
	public boolean getIsAlive(){
		return isAlive;
	}
	
	/**
	 * Kills the enemy by switching isAlive to false
	 */
	public void die(){
		isAlive = false;
	}

}
