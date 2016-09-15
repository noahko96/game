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
 * It is called by SplashScreen3
 * It can be used by calling the constructor (e.g., BossLevel(s, 400))
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
    private ArrayList<Enemy> myEnemies;
    private ArrayList<GunPowerUp> myGuns;
    private ArrayList<VestPowerUp> myVests;
    private ArrayList<ImageView> myImages;
    private ArrayList<Obstacle> myObstacles;
    private int myGunTracker;
    private int myVestTracker;
    private int myTimer;
    private int myBossDirection;
    
    /**
     * constructor for BossLevel that initiates myStage, mySize, myGunTracker, myVestTracker, myBossDirection, 
     * myTimer, myAnimation; calls init; sets up stage; runs game loop
     * 
     * @param s - the stage to show the scene on
     * @param halfSize - half the width and length of the stage
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
     * Create the game's scene and places myHero and myBoss into it as well as calling placeEnemies and 
     * placeObstacles
     */
    private void init () {
        myRoot = new Group();
        myScene = new Scene(myRoot, mySize, mySize, BACKGROUND_COLOR);
        myHero = new Hero();
        myBoss = new Boss();
        myBullets = new ArrayList<Bullet>();
        myEnemyBullets = new ArrayList<Bullet>();
        myImages = new ArrayList<ImageView>();
        placeEnemies();
        placeObstacles();
        myHero.getImage().setX(0);
        myHero.getImage().setY(mySize / 2 - myHero.getImage().getBoundsInLocal().getHeight() / 2);
        myBoss.getImage().setX(BOSS_COLUMN*COLUMN_WIDTH);
        myBoss.getImage().setY(mySize / 2 - myBoss.getImage().getBoundsInLocal().getHeight() / 2);
        myEnemies.add(myBoss);
        myRoot.getChildren().add(myHero.getImage());
        myRoot.getChildren().add(myBoss.getImage());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }
    
    /**
     * determines movement of objects, determines course of action in case of an intersection, checks for death, 
     * removes dead objects, keeps track of powerups, and fires the guns for the thugs mainly by calling
     * other methods and increments myTimer
     * 
     * @param elapsedTime
     */
    private void step (double elapsedTime) {
    	myTimer++;
    	makeBossMove(elapsedTime);
    	makeImagesMove(elapsedTime);
    	makeMyBulletsMoveAndIntersect(elapsedTime);
    	makeMyEnemiesBulletsMoveAndIntersect(elapsedTime);
    	checkIfObstaclesIntersect();
    	checkIfGunsIntersect();
    	checkIfVestsIntersect();
    	thugFireGuns();
    	checkIfEnemyDies();
    	checkIfHeroDies();
    	checkIfBossDies();
    	checkPowerUpTrackers();
    }

    /**
     * Checks to see if myVestTracker and myGunTracker indicate that the powerup has been used and if it is it 
     * resets that power up
     */
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

	/**
	 * Checks to see if the boss died and if he did turns off the animation and brings up the GameWonScreen
	 */
	private void checkIfBossDies() {
		if (myBoss.getHealth() <= 0 ||
			!myBoss.getIsAlive()){
    		new GameWonScreen(myStage, mySize/2);
    		myAnimation.stop();
    	}
	}

	/**
	 * Checks to see if the hero ran out of lives or went out of bounds and if he did it stops the animation and
	 * calls the GameOverScreen
	 */
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

	/**
	 * Checks to see if any of the enemies have run out of health or are intersecting with the hero and if they are
	 * they call the dieEnemy method and if intersecting with the hero it takes away one of the hero's lives or two
	 * if the enemy is an Enforcer unless the hero has invincibility, otherwise it increments the vest power up 
	 * tracker
	 */
	private void checkIfEnemyDies() {
		for (Enemy enemy: myEnemies){
    		if (enemy.getHealth() <= 0){
    			dieEnemy(enemy);
    		}
    		else if (enemy.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    				 enemy.getIsAlive()){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    				if (enemy instanceof Enforcer){
    					myHero.loseLife();
    				}
    			}
    			else {
    				myVestTracker++;
    			}
    			dieEnemy(enemy);
    		}
    	}
	}

	/**
	 * Has all the thugs on the screen fire their guns if myTimer is divisible by DIVISOR
	 */
	private void thugFireGuns() {
		for (Thug thug: myThugs){
    		if (thug.getImage().getX() <= myStage.getWidth() &&
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

	/**
	 * Checks to see if the hero is intersecting with any of the vest power ups and if it is it gives the hero
	 * invincibility that will last until the hero gets hit by something as well as removing the vest from the 
	 * screen
	 */
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

	/**
	 * Checks to see if any of the gun power ups intersect with the hero and if they do it doubles the hero's 
	 * damage per shot which will last for five shots as well as removing the gun from the screen
	 */
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

	/**
	 * Checks to see if any of the obstacles are intersecting with the hero and if the hero does not have 
	 * invincibility it will subtract a life from the hero if they are, otherwise it increments the vest power up
	 * tracker
	 */
	private void checkIfObstaclesIntersect() {
		for (Obstacle obstacle: myObstacles){
    		if (obstacle.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			obstacle.getIsActive() &&
    			!(obstacle instanceof GunPowerUp) &&
    			!(obstacle instanceof VestPowerUp)){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    			}
    			else {
    				myVestTracker++;
    			}
    			obstacle.notActive();
    			myRoot.getChildren().remove(obstacle.getImage());
    		}
    	}
	}

	/**
	 * Causes all of the enemy bullets to fly and checks to see if they run into the hero or an obstacle. If they 
	 * run into a hero, it calls stopBullet and removes one of the hero's lives if he is not invincible, otherwise
	 * it increments the vest power up tracker. If they run into an obstacle, it just calls stopBullet.
	 * 
	 * @param elapsedTime
	 */
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
    		for (Obstacle obstacle: myObstacles){
    			if (bullet.getShape().getBoundsInParent().intersects(obstacle.getImage().getBoundsInParent()) &&
    				bullet.getIsActive()){
    				stopBullet(bullet);
    			}
    		}
    	}
	}

	/**
	 * Causes all of my bullets to fly and checks to see if they intersect with an enemy or an obstacle or goes out
	 * of bounds. If if intersects with an enemy, it takes away some of the enemy's health depending on the damage
	 * of the bullet and calls stopBullet. If it intersects with an obstacle or goes out of bounds, it just calls
	 * stopBullet
	 * 
	 * @param elapsedTime
	 */
	private void makeMyBulletsMoveAndIntersect(double elapsedTime) {
		for (Bullet bullet: myBullets){
    		bullet.getShape().setCenterX(bullet.getShape().getCenterX() + BULLET_SPEED * elapsedTime);
    		for (Enemy enemy: myEnemies){
    			if (bullet.getShape().getBoundsInParent().intersects(enemy.getImage().getBoundsInParent()) &&
    				bullet.getIsActive() &&
    				enemy.getIsAlive()){
    				enemy.loseHealth(bullet.getDamage());
    				stopBullet(bullet);
    			}
    		}
    		for (Obstacle obstacle: myObstacles){
    			if (bullet.getShape().getBoundsInParent().intersects(obstacle.getImage().getBoundsInParent()) &&
    				bullet.getIsActive()){
    				stopBullet(bullet);
    			}
    		}
			if (bullet.getShape().getCenterX() > myStage.getWidth()){
				stopBullet(bullet);
			}
    	}
	}

	/**
	 * Makes all of the images on the screen except for the hero and the boss move to the left
	 * 
	 * @param elapsedTime
	 */
	private void makeImagesMove(double elapsedTime) {
		for (ImageView image: myImages){
    		image.setX(image.getX() - OBJECT_SPEED*elapsedTime);
    	}
	}

	/**
	 * Makes the boss move up and down while bouncing off the top and the bottom of the screen
	 * 
	 * @param elapsedTime
	 */
	private void makeBossMove(double elapsedTime) {
		myBoss.getImage().setY(myBoss.getImage().getY() - myBossDirection * OBJECT_SPEED * elapsedTime);
    	if (myBoss.getImage().getY() <= 0 ||
    		myBoss.getImage().getY() >= myStage.getHeight() - myBoss.getImage().getBoundsInLocal().getHeight()){
    		myBossDirection = myBossDirection * -1;
    	}
	}
    
	/**
	 * Stops the bullet by making it inactive and removing it from the screen
	 * 
	 * @param bullet - the bullet to be stopped
	 */
    private void stopBullet(Bullet bullet){
    	bullet.becomeInactive();
    	myRoot.getChildren().remove(bullet.getShape());
    }
    
    /**
     * Kills the enemy by making them die and removing them from the screen
     * 
     * @param enemy - the enemy to be killed
     */
    private void dieEnemy(Enemy enemy){
    	enemy.die();
    	myRoot.getChildren().remove(enemy.getImage());
    }

    /**
     * Initializes myEnemies and myThugs and places all of the enemies except for the boss into their positions in
     * the game by calling placeAnEnemy a bunch of times
     */
    private void placeEnemies(){
    	myEnemies = new ArrayList<Enemy>();
    	myThugs = new ArrayList<Thug>();
    	placeAnEnemy(0, 9, "thug");
    	placeAnEnemy(2, 14, "thug");
    	placeAnEnemy(2, 19, "thug");
    	placeAnEnemy(3, 19, "thug");
    	placeAnEnemy(0, 28, "thug");
    	placeAnEnemy(3, 28, "thug");
    	placeAnEnemy(1, 34, "thug");
    	placeAnEnemy(3, 41, "thug");
    	placeAnEnemy(3, 9, "enforcer");
    	placeAnEnemy(1, 13, "enforcer");
    	placeAnEnemy(0, 17, "enforcer");
    	placeAnEnemy(1, 17, "enforcer");
    	placeAnEnemy(2, 22, "enforcer");
    	placeAnEnemy(1, 30, "enforcer");
    	placeAnEnemy(2, 41, "enforcer");
    }
    
    /**
     * Places an enemy into one of the four rows and forty-five columns of the level and determines initializes the
     * type of the enemy using type. Adds the enemy to myEnemies, the image of the enemy to myImages and the 
     * children of myRoot, and the enemy to myThugs if it is a thug.
     * 
     * @param row - the number 0-3 which determines the row the enemy will be placed in
     * @param column - the number 7-45 which determines the column the enemy will be placed in
     * @param type - String that determines what type of enemy is being placed
     */
    private void placeAnEnemy(int row, int column, String type){
    	Enemy enemy;
    	if (type.equals("thug")){
    		enemy = new Thug();
    		myThugs.add((Thug) enemy);
    	}
    	else {
    		enemy = new Enforcer();
    	}
    	enemy.getImage().setX(column*COLUMN_WIDTH);
    	enemy.getImage().setY(row*ROW_HEIGHT);
    	myEnemies.add(enemy);
    	myImages.add(enemy.getImage());
    	myRoot.getChildren().add(enemy.getImage());
    }
    
    /**
     * Initializes myObstacles, myGuns, and myVests and places all of the obstacles and power ups onto the screen
     * by calling placeAnObstacle a bunch of times.
     */
    private void placeObstacles(){
    	myObstacles = new ArrayList<Obstacle>();
    	myGuns = new ArrayList<GunPowerUp>();
    	myVests = new ArrayList<VestPowerUp>();
    	placeAnObstacle(1, 9, "dog");
    	placeAnObstacle(2, 10, "dog");
    	placeAnObstacle(3, 15, "dog");
    	placeAnObstacle(0, 22, "dog");
    	placeAnObstacle(3, 22, "dog");
    	placeAnObstacle(1, 25, "dog");
    	placeAnObstacle(2, 25, "dog");
    	placeAnObstacle(2, 32, "dog");
    	placeAnObstacle(0, 37, "dog");
    	placeAnObstacle(1, 37, "dog");
    	placeAnObstacle(2, 37, "dog");
    	placeAnObstacle(0, 20, "gun");
    	placeAnObstacle(0, 39, "gun");
    	placeAnObstacle(0, 13, "vest");
    	placeAnObstacle(0, 38, "vest");
    }
    
    /**
     * Places an obstacle into one of the four rows and forty-five columns of the game and determines the type of 
     * obstacle based off of type and adds the obstacle to myObstacles, the image of the obstacle to myImages and 
     * the children of myRoot, the obstacle to myGuns if it is a gun, and the obstacle to myVests if it is a vest.
     * 
     * @param row - the number 0-3 which determines which row the obstacle will be placed in
     * @param column - the number 7-45 which determines which column the obstacle will be placed in
     * @param type - the String which determines what type of obstacle is being placed
     */
    private void placeAnObstacle(int row, int column, String type){
    	Obstacle obstacle;
    	if (type.equals("dog")){
    		obstacle = new DogObstacle();
    	}
    	else if (type.equals("gun")){
    		obstacle = new GunPowerUp();
    		myGuns.add((GunPowerUp) obstacle);
    	}
    	else {
    		obstacle = new VestPowerUp();
    		myVests.add((VestPowerUp) obstacle);
    	}
    	obstacle.getImage().setX(column*COLUMN_WIDTH);
    	obstacle.getImage().setY(row*ROW_HEIGHT);
    	myObstacles.add(obstacle);
    	myImages.add(obstacle.getImage());
    	myRoot.getChildren().add(obstacle.getImage());
    }
    
    /**
     * Handles the input from the keyboard during the game. If the right arrow is clicked, it moves the hero right.
     * If the left arrow is clicked, it moves the hero to the left. If the up arrow is clicked, it moves the hero 
     * up. If the down arrow is clicked, it moves the hero down. If the space bar is clicked, it shoots the hero's
     * gun. If the A button is clicked, it gives the hero invincibility. If the B button is clicked, it removes the
     * hero's invincibility. If the C button is clicked, it doubles the damage the hero's bullets do. If the D
     * button is clicked, it resets the damage the hero's bullets do to 1. If the E button is clicked, it gives the
     * hero an extra life. If the F button is clicked, it takes away a life from the hero. If the G button is 
     * clicked, you beat the level and the GameWonScreen pops up and the animation stops.
     * 
     * @param code
     */
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
