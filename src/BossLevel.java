import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class that runs the Boss Level for Mafia Mayhem
 * 
 * @author Noah Over
 *
 */
public class BossLevel {
	private static final int KEY_INPUT_SPEED = 5;
    private static final int BULLET_SPEED = 300;
    private static final Paint BACKGROUND_COLOR = Color.BLACK; 
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int OBJECT_SPEED = 100;
    private static final int COLUMN_WIDTH = 100;
    private static final int ROW_HEIGHT = 200;
    private static final int FINAL_COLUMN = 45;
    private static final int TRACKER_START = 6;
    private static final int RESET_GUN_POWER = 5;
    private static final int RESET_VEST_POWER = 1;
    private static final int RESET_TRACKER = 0;
    private static final int BULLET_PLACEMENT = 20;
    private static final int DIVISOR = 100;
    private static final int BOSS_COLUMN = 7;
    private static final int ORIGINAL_DIRECTION = 1;

    private Scene myScene;
    private Stage myStage;
    private Group myRoot;
    private Timeline myAnimation;
    private int mySize;
    private Hero myHero;
    private Boss myBoss;
    private ArrayList<Bullet> myBullets;
    private ArrayList<Bullet> myEnemyBullets;
    private ArrayList<Thug> myThugs;
    private ArrayList<Enforcer> myEnforcers;
    private ArrayList<Dog> myDogs;
    private ArrayList<GunPowerUp> myGuns;
    private ArrayList<VestPowerUp> myVests;
    private ArrayList<ImageView> myImages;
    private ArrayList<ImageView> myObstacles;
    private int myGunTracker;
    private int myVestTracker;
    private int myTimer;
    private int myBossDirection;
    
    /**
     * constructor for BossLevel; initiates myStage, mySize, myGunTracker, myVestTracker, myBossDirection, myTimer, 
     * myAnimation; calls init; sets up stage; runs game loop
     * 
     * @param s the stage to show the scene on
     * @param halfSize half the size of the stage
     */
    public BossLevel(Stage s, int halfSize){
    	myStage = s;
    	mySize = 2*halfSize;
    	myGunTracker = TRACKER_START;
    	myVestTracker = TRACKER_START;
    	myBossDirection = ORIGINAL_DIRECTION;
    	myTimer = 0;
    	init();
    	myStage.setScene(myScene);
    	myStage.show();
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> step(SECOND_DELAY));
    	myAnimation = new Timeline();
    	myAnimation.setCycleCount(Timeline.INDEFINITE);
    	myAnimation.getKeyFrames().add(frame);
    	myAnimation.play();
    }


    /**
     * Create the game's scene
     */
    private void init () {
        myRoot = new Group();
        myScene = new Scene(myRoot, mySize, mySize, BACKGROUND_COLOR);
        myHero = new Hero();
        myBoss = new Boss();
        myBullets = new ArrayList<Bullet>();
        myEnemyBullets = new ArrayList<Bullet>();
        myImages = new ArrayList<ImageView>();
        myObstacles = new ArrayList<ImageView>();
        placeThugs();
        placeEnforcers();
        placeDogs();
        placeGuns();
        placeVests();
        myHero.getImage().setX(0);
        myHero.getImage().setY(mySize / 2 - myHero.getImage().getBoundsInLocal().getHeight() / 2);
        myBoss.getImage().setX(BOSS_COLUMN*COLUMN_WIDTH);
        myBoss.getImage().setY(mySize / 2 - myBoss.getImage().getBoundsInLocal().getHeight() / 2);
        myRoot.getChildren().add(myHero.getImage());
        myRoot.getChildren().add(myBoss.getImage());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }
    
    /**
     * determines movement of objects, determines course of action in case of an intersection, checks for death, 
     * removes dead objects, keeps track of powerups, fires the guns for the thugs
     * 
     * @param elapsedTime
     */
    private void step (double elapsedTime) {
    	myTimer++;
    	makeBossMove(elapsedTime);
    	makeImagesMove(elapsedTime);
    	makeMyBulletsMoveAndIntersect(elapsedTime);
    	makeMyEnemiesBulletsMoveAndIntersect(elapsedTime);
    	checkIfDogsIntersect();
    	checkIfGunsIntersect();
    	checkIfVestsIntersect();
    	checkIfThugDiesOrFiresGun();
    	checkIfEnforcerDies();
    	checkIfHeroDies();
    	checkIfBossDies();
    	checkPowerUpTrackers();
    }


	private void checkPowerUpTrackers() {
		if (myVestTracker == RESET_VEST_POWER &&
    		myHero.checkInvincibility()){
    		myHero.loseInvincibility();
    	}
    	if (myGunTracker == RESET_GUN_POWER &&
    		myHero.checkDamagePerShot() != 1){
    		myHero.resetDamagePerShot();
    	}
	}


	private void checkIfBossDies() {
		if (myBoss.getHealth() <= 0){
    		new GameWonScreen(myStage, mySize/2);
    		myAnimation.stop();
    	}
	}


	private void checkIfHeroDies() {
		if (myHero.getLives() <= 0 ||
    		myHero.getImage().getX() < -1 * COLUMN_WIDTH ||
    		myHero.getImage().getX() > myStage.getWidth() ||
    		myHero.getImage().getY() < -1 * myHero.getImage().getBoundsInLocal().getHeight() ||
    		myHero.getImage().getY() > myStage.getHeight() ||
    		myHero.getImage().getX() > FINAL_COLUMN*COLUMN_WIDTH){
    		new GameOverScreen(myStage, mySize/2);
    		myAnimation.stop();
    	}
	}


	private void checkIfEnforcerDies() {
		for (Enforcer enforcer: myEnforcers){
    		if (enforcer.getHealth() <= 0){
    			dieEnforcer(enforcer);
    		}
    		else if (enforcer.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    				 enforcer.getIsAlive()){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    				myHero.loseLife();
    			}
    			else {
    				myVestTracker++;
    			}
    			dieEnforcer(enforcer);
    		}
    	}
	}


	private void checkIfThugDiesOrFiresGun() {
		for (Thug thug: myThugs){
    		if (thug.getHealth() <= 0){
    			dieThug(thug);
    		}
    		else if (thug.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
				thug.getIsAlive()){
				if (!myHero.checkInvincibility()){
					myHero.loseLife();
				}
				else {
					myVestTracker++;
				}
				dieThug(thug);
    		}
    		else if (thug.getImage().getX() <= myStage.getWidth() &&
    				 thug.getImage().getX() >= 0 &&
    				 thug.getIsAlive() && 
    				 myTimer%DIVISOR == 0){
    			Bullet bullet = new Bullet();
    			bullet.getShape().setCenterX(thug.getImage().getX());
    			bullet.getShape().setCenterY(thug.getImage().getY() + BULLET_PLACEMENT);
    			myEnemyBullets.add(bullet);
    			myRoot.getChildren().add(bullet.getShape());
    		}
    	}
	}


	private void checkIfVestsIntersect() {
		for (VestPowerUp vest: myVests){
    		if (vest.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			vest.getIsActive()){
    			myHero.gainInvincibility();
    			myVestTracker = RESET_TRACKER;
    			vest.notActive();
    			myRoot.getChildren().remove(vest.getImage());
    		}
    	}
	}


	private void checkIfGunsIntersect() {
		for (GunPowerUp gun: myGuns){
    		if (gun.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			gun.getIsActive()){
    			myHero.doubleDamagePerShot();
    			myGunTracker = RESET_TRACKER;
    			gun.notActive();
    			myRoot.getChildren().remove(gun.getImage());
    		}
    	}
	}


	private void checkIfDogsIntersect() {
		for (Dog dog: myDogs){
    		if (dog.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			dog.getIsActive()){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    			}
    			else {
    				myVestTracker++;
    			}
    			dog.notActive();
    			myRoot.getChildren().remove(dog.getImage());
    		}
    	}
	}


	private void makeMyEnemiesBulletsMoveAndIntersect(double elapsedTime) {
		for (Bullet bullet: myEnemyBullets){
    		bullet.getShape().setCenterX(bullet.getShape().getCenterX() - BULLET_SPEED*elapsedTime);
    		if (bullet.getShape().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			bullet.getIsActive()){
    			stopBullet(bullet);
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    			}
    			else {
    				myVestTracker++;
    			}
    		}
    		for (ImageView obstacle: myObstacles){
    			if (bullet.getShape().getBoundsInParent().intersects(obstacle.getBoundsInParent()) &&
    				bullet.getIsActive()){
    				stopBullet(bullet);
    			}
    		}
    	}
	}


	private void makeMyBulletsMoveAndIntersect(double elapsedTime) {
		for (Bullet bullet: myBullets){
    		bullet.getShape().setCenterX(bullet.getShape().getCenterX() + BULLET_SPEED * elapsedTime);
    		for (Thug thug: myThugs){
    			if (bullet.getShape().getBoundsInParent().intersects(thug.getImage().getBoundsInParent()) &&
    				bullet.getIsActive() &&
    				thug.getIsAlive()){
    				thug.loseHealth(bullet.getDamage());
    				stopBullet(bullet);
    			}
    		}
    		for (Enforcer enforcer: myEnforcers){
    			if (bullet.getShape().getBoundsInParent().intersects(enforcer.getImage().getBoundsInParent()) &&
    				bullet.getIsActive() &&
    				enforcer.getIsAlive()){
    				enforcer.loseHealth(bullet.getDamage());
   				stopBullet(bullet);
    			}
    		}
    		for (ImageView obstacle: myObstacles){
    			if (bullet.getShape().getBoundsInParent().intersects(obstacle.getBoundsInParent()) &&
    				bullet.getIsActive()){
    				stopBullet(bullet);
    			}
    		}
    		if (bullet.getShape().getBoundsInParent().intersects(myBoss.getImage().getBoundsInParent()) &&
    			bullet.getIsActive()){
    			myBoss.loseHealth(bullet.getDamage());
    			stopBullet(bullet);
    		}
			if (bullet.getShape().getCenterX() > myStage.getWidth()){
				stopBullet(bullet);
			}
    	}
	}


	private void makeImagesMove(double elapsedTime) {
		for (ImageView image: myImages){
    		image.setX(image.getX() - OBJECT_SPEED*elapsedTime);
    	}
	}


	private void makeBossMove(double elapsedTime) {
		myBoss.getImage().setY(myBoss.getImage().getY() - myBossDirection * OBJECT_SPEED * elapsedTime);
    	if (myBoss.getImage().getY() <= 0 ||
    		myBoss.getImage().getY() >= myStage.getHeight() - myBoss.getImage().getBoundsInLocal().getHeight()){
    		myBossDirection = myBossDirection * -1;
    	}
	}
    
    private void stopBullet(Bullet bullet){
    	bullet.becomeInactive();
    	myRoot.getChildren().remove(bullet.getShape());
    }
    
    private void dieThug(Thug thug){
    	thug.die();
    	myRoot.getChildren().remove(thug.getImage());
    }
    
    private void dieEnforcer(Enforcer enforcer){
    	enforcer.die();
    	myRoot.getChildren().remove(enforcer.getImage());
    }
    
    private void placeThugs(){
    	myThugs = new ArrayList<Thug>();
    	placeAThug(0, 9);
    	placeAThug(2, 14);
    	placeAThug(2, 19);
    	placeAThug(3, 19);
    	placeAThug(0, 28);
    	placeAThug(3, 28);
    	placeAThug(1, 34);
    	placeAThug(3, 41);
    }
    
    private void placeAThug(int row, int column){
    	Thug thug = new Thug();
    	thug.getImage().setX(column*COLUMN_WIDTH);
    	thug.getImage().setY(row*ROW_HEIGHT);
    	myThugs.add(thug);
    	myImages.add(thug.getImage());
    	myRoot.getChildren().add(thug.getImage());
    }
    
    private void placeEnforcers(){
    	myEnforcers = new ArrayList<Enforcer>();
    	placeAnEnforcer(3, 9);
    	placeAnEnforcer(1, 13);
    	placeAnEnforcer(0, 17);
    	placeAnEnforcer(1, 17);
    	placeAnEnforcer(2, 22);
    	placeAnEnforcer(1, 30);
    	placeAnEnforcer(2, 41);
    }
    
    private void placeAnEnforcer(int row, int column){
    	Enforcer enforcer = new Enforcer();
    	enforcer.getImage().setX(column*COLUMN_WIDTH);
    	enforcer.getImage().setY(row*ROW_HEIGHT);
    	myEnforcers.add(enforcer);
    	myImages.add(enforcer.getImage());
    	myRoot.getChildren().add(enforcer.getImage());
    }
    
    private void placeDogs(){
    	myDogs = new ArrayList<Dog>();
    	placeADog(1, 9);
    	placeADog(2, 10);
    	placeADog(3, 15);
    	placeADog(0, 22);
    	placeADog(3, 22);
    	placeADog(1, 25);
    	placeADog(2, 25);
    	placeADog(2, 32);
    	placeADog(0, 37);
    	placeADog(1, 37);
    	placeADog(2, 37);
    }
    
    private void placeADog(int row, int column){
    	Dog dog = new Dog();
    	dog.getImage().setX(column*COLUMN_WIDTH);
    	dog.getImage().setY(row*ROW_HEIGHT);
    	myDogs.add(dog);
    	myObstacles.add(dog.getImage());
    	myImages.add(dog.getImage());
    	myRoot.getChildren().add(dog.getImage());
    }
    
    private void placeGuns(){
    	myGuns = new ArrayList<GunPowerUp>();
    	placeAGun(0, 20);
    	placeAGun(0, 39);
    }
    
    private void placeAGun(int row, int column){
    	GunPowerUp gun = new GunPowerUp();
    	gun.getImage().setX(column*COLUMN_WIDTH);
    	gun.getImage().setY(row*ROW_HEIGHT);
    	myGuns.add(gun);
    	myImages.add(gun.getImage());
    	myRoot.getChildren().add(gun.getImage());
    }
    
    private void placeVests(){
    	myVests = new ArrayList<VestPowerUp>();
    	placeAVest(0, 13);
    	placeAVest(0, 38);
    }
    
    private void placeAVest(int row, int column){
    	VestPowerUp vest = new VestPowerUp();
    	vest.getImage().setX(column*COLUMN_WIDTH);
    	vest.getImage().setY(row*ROW_HEIGHT);
    	myVests.add(vest);
    	myImages.add(vest.getImage());
    	myRoot.getChildren().add(vest.getImage());
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                myHero.getImage().setX(myHero.getImage().getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                myHero.getImage().setX(myHero.getImage().getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                myHero.getImage().setY(myHero.getImage().getY() - KEY_INPUT_SPEED);
                break;
            case DOWN:
                myHero.getImage().setY(myHero.getImage().getY() + KEY_INPUT_SPEED);
                break;
            case SPACE:
            	Bullet bullet = new Bullet();
            	if (myHero.checkDamagePerShot() != 1){
            		bullet.setDamage(myHero.checkDamagePerShot());
            	}
            	bullet.getShape().setCenterX(myHero.getImage().getX() + COLUMN_WIDTH);
            	bullet.getShape().setCenterY(myHero.getImage().getY() + BULLET_PLACEMENT);
            	myRoot.getChildren().add(bullet.getShape());
            	myBullets.add(bullet);
            	myGunTracker++;
            	break;
            case A:
            	myHero.gainInvincibility();
            	break;
            case B:
            	myHero.loseInvincibility();
            	break;
            case C:
            	myHero.doubleDamagePerShot();
            	break;
            case D:
            	myHero.resetDamagePerShot();
            	break;
            case E:
            	myHero.gainLife();
            	break;
            case F:
            	myHero.loseLife();
            	break;
            case G:
            	new GameWonScreen(myStage, mySize/2);
            	myAnimation.stop();
            	break;
            default:
            	break;
        }
    }
}
