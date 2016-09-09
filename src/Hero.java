import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hero {
	public static final int INITIAL_LIVES = 5;
	public static final int HERO_WIDTH = 100;
	public static final int INITIAL_DAMAGE = 1;
	public static final String APPEARANCE = "Mafia Mayhem hero.jpg";
	
	
	private int myLives;
	private ImageView myAppearance;
	private boolean isInvincible;
	private int myDamagePerShot;
	
	public Hero (){
		myLives = INITIAL_LIVES;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(APPEARANCE));
        myAppearance = new ImageView(image);
        myAppearance.setFitWidth(HERO_WIDTH);
        myAppearance.setPreserveRatio(true);
        isInvincible = false;
        myDamagePerShot = INITIAL_DAMAGE; 
	}
	
	public ImageView getImage(){
		return myAppearance;
	}
	
	public int getLives(){
		return myLives;
	}
	
	public void loseLife(){
		myLives--;
	}
	
	public void gainLife(){
		myLives++;
	}
	
	public boolean checkInvincibility(){
		return isInvincible;
	}
	
	public void gainInvincibility(){
		isInvincible = true;
	}
	
	public void loseInvincibility(){
		isInvincible = false;
	}
	
	public int checkDamagePerShot(){
		return myDamagePerShot;
	}
	
	public void doubleDamagePerShot(){
		myDamagePerShot = 2;
	}
	
	public void resetDamagePerShot(){
		myDamagePerShot = 1;
	}

}
