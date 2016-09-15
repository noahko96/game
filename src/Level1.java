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
 * Class that runs the first level of the game.
 * It is called by SplashScreen2.
 * It can be used by calling the constructor (e.g., Level1(s, 400)).
 * 
 * @author Noah Over
 */
class Level1 {
    private static final int KEY_INPUT_SPEED = 5;
    private static final int BULLET_SPEED = 300;
    private static final Paint BACKGROUND_COLOR = Color.BLACK; 
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int OBJECT_SPEED = 100;
    private static final int COLUMN_WIDTH = 100;
    private static final int ROW_HEIGHT = 200;
    private static final int NUMBER_OF_ROWS = 4;
    private static final int FINAL_COLUMN = 45;
    private static final int TRACKER_START = 6;
    private static final int RESET_GUN_POWER = 5;
    private static final int RESET_VEST_POWER = 1;
    private static final int RESET_TRACKER = 0;
    private static final int BULLET_PLACEMENT = 20;
    private static final int DIVISOR = 100;
    private static final int INITIAL_DAMAGE = 1;
    

    private Scene myScene;
    private Stage myStage;
    private Group myRoot;
    private Timeline myAnimation;
    private int mySize;
    private Hero myHero;
    private ArrayList<Bullet> myBullets;
    private ArrayList<Bullet> myEnemyBullets;
    private ArrayList<FinishLine> myFinish;
    private ArrayList<Thug> myThugs;
    private ArrayList<Enemy> myEnemies;
    private ArrayList<GunPowerUp> myGuns;
    private ArrayList<VestPowerUp> myVests;
    private ArrayList<ImageView> myImages;
    private ArrayList<Obstacle> myObstacles;
    private int myGunTracker;
    private int myVestTracker;
    private int myTimer;
    
    /**
     * Constructor for Level1 which initializes the stage, the size, the powerup tracker, and the timer; calls init
     * to make the scene, shows the scene, and runs the game loop
     * 
     * @param s - the stage to show the animation on
     * @param halfSize - half the length and width of the scene
     */
    public Level1(Stage s, int halfSize){
    	myStage = s;
    	mySize = 2*halfSize;
    	myGunTracker = TRACKER_START;
    	myVestTracker = TRACKER_START;
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
     * Create the game's scene and places the Hero in it as well as calling placeFinishLine, placeEnemies, and 
     * placeObstacles
     */
    private void init () {
        myRoot = new Group();
        myScene = new Scene(myRoot, mySize, mySize, BACKGROUND_COLOR);
        myHero = new Hero();
        myBullets = new ArrayList<Bullet>();
        myEnemyBullets = new ArrayList<Bullet>();
        myImages = new ArrayList<ImageView>();
        placeFinishLine();
        placeEnemies();
        placeObstacles();
        myHero.getImage().setX(0);
        myHero.getImage().setY(mySize / 2  - myHero.getImage().getBoundsInLocal().getHeight() / 2);
        myRoot.getChildren().add(myHero.getImage());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    /**
     * Increments myTimer, moves the images and bullets, checks everything for intersections and handles what to 
     * do if there is one, checks deaths of hero and enemies, and keeps track of the power ups through the use of a
     * bunch of other methods that it calls
     */
    private void step (double elapsedTime) {
    	myTimer++;
    	moveImages(elapsedTime);
    	moveBulletsAndCheckIntersections(elapsedTime);
    	moveEnemyBulletsAndCheckIntersections(elapsedTime);
    	checkFinishLineIntersections();
    	checkObstacleIntersections();
    	checkGunIntersections();
    	checkVestIntersections();
    	thugsFireWeapons();
    	checkEnemyDeath();
    	setHeroRestrictions();
    	checkPowerUpTrackers();
    }

    /**
     * Checks to see if the power up trackers have reached their reset values and if they have it takes away the
     * respective power up
     */
	private void checkPowerUpTrackers() {
		if (myVestTracker == RESET_VEST_POWER &&
    		myHero.checkInvincibility()){
    		myHero.loseInvincibility();
    	}
    	if (myGunTracker == RESET_GUN_POWER &&
    		myHero.checkDamagePerShot() != INITIAL_DAMAGE){
    		myHero.resetDamagePerShot();
    	}
	}

	/**
	 * Checks to see if the hero has ran out of lives or went out of bounds and if he has it stops the animation 
	 * and calls the GameOverScreen
	 */
	private void setHeroRestrictions() {
		if (myHero.getLives() <= 0 ||
    		myHero.getImage().getX() < -1 * COLUMN_WIDTH ||
    		myHero.getImage().getX() > myStage.getWidth() ||
    		myHero.getImage().getY() < -1 * myHero.getImage().getBoundsInLocal().getHeight() ||
    		myHero.getImage().getY() > myStage.getHeight()){
    		new GameOverScreen(myStage, mySize/2);
    		myAnimation.stop();
    	}
	}

	/**
	 * Checks to see if an enemy ran out of lives or is intersecting with the hero. In either case, you call 
	 * dieEnemy to kill the enemy. If it is the intersection, the hero loses a life, or two if the enemy is an
	 * enforcer, if he is not invincible, otherwise it increments the vest power up tracker.
	 */
	private void checkEnemyDeath() {
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
	 * Has all the thugs that are on the screen fire when myTimer is evenly divisible by DIVISOR
	 */
	private void thugsFireWeapons() {
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
	 * Checks to see if any of the vests intersect with the hero and if they do it gives the hero invincibility
	 * until he gets hit by either a bullet, enemy, or obstacle and removes the vest from the screen.
	 */
	private void checkVestIntersections() {
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
	 * Checks to see if any of the gun power ups intersect with the hero and if they do, doubles the damage the 
	 * hero does with each of his shots until he shoots five times and removes the gun from the screen
	 */
	private void checkGunIntersections() {
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
	 * Checks to see if any of the obstacles intersect with the hero, other than the power ups or the finish line. 
	 * If any of them do, the hero will lose a life if he is not invincible or the vest power up tracker will be 
	 * incremented and no matter the invincibility the obstacle will be removed from the screen.
	 */
	private void checkObstacleIntersections() {
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
	 * Checks to see if the finish line intersects with the hero and if it does it calls the LevelCompletedScreen
	 * and stops the animation.
	 */
	private void checkFinishLineIntersections() {
		for (FinishLine flag: myFinish){
    		if (flag.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent())){
    			new LevelCompletedScreen(myStage, mySize/2);
    			myAnimation.stop();
    		}
    	}
	}

	/**
	 * Moves all of the enemy bullets towards the left side of the screen and checks to see if they intersect with
	 * the hero or one of the obstacles. If it intersects with the hero, the hero will lose a life barring 
	 * invincibility or the vest power up tracker will be incremented and it will call stopBullet to stop the 
	 * bullet. If it intersects with an obstacle, the bullet will be stopped by just calling stopBullet.
	 * 
	 * @param elapsedTime
	 */
	private void moveEnemyBulletsAndCheckIntersections(double elapsedTime) {
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
	 * Moves all of the hero's bullets towards the right side of the screen and checks if it intersects with an
	 * enemy, an obstacle, or goes out of bounds. If it intersects with an enemy, the enemy loses the amount of 
	 * health that equals the amount of damage that the bullet can do and the bullet is stopped with the method
	 * stopBullet. If it intersects with an obstacle or goes out of bounds, the bullet is stopped using 
	 * stopBullet.
	 * 
	 * @param elapsedTime
	 */
	private void moveBulletsAndCheckIntersections(double elapsedTime) {
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
	 * Moves all of the images except for that of the hero towards the left side of the screen
	 * 
	 * @param elapsedTime
	 */
	private void moveImages(double elapsedTime) {
		for (ImageView image: myImages){
    		image.setX(image.getX() - OBJECT_SPEED*elapsedTime);
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
     * Kills and enemy by calling their die method and removing them from the screen.
     * 
     * @param enemy - the enemy to be killed
     */
    private void dieEnemy(Enemy enemy){
    	enemy.die();
    	myRoot.getChildren().remove(enemy.getImage());
    }
    
    /**
     * Places the finish line all the way across the forty-fifth column and initializes myFinish.
     */
    private void placeFinishLine(){
    	myFinish = new ArrayList<FinishLine>();
    	int row = 0;
    	while (row < NUMBER_OF_ROWS){
    		FinishLine flag = new FinishLine();
    		flag.getImage().setX(FINAL_COLUMN*COLUMN_WIDTH);
    		flag.getImage().setY(row*ROW_HEIGHT);
    		myFinish.add(flag);
    		myImages.add(flag.getImage());
    		myRoot.getChildren().add(flag.getImage());
    		row++;
    	}
    }
    
    /**
     * Initializes myEnemies and myThugs and places all of the thugs and enforcers by calling placeAnEnemy a bunch 
     * of times.
     */
    private void placeEnemies(){
    	myEnemies = new ArrayList<Enemy>();
    	myThugs = new ArrayList<Thug>();
    	placeAnEnemy(1, 7, "thug");
    	placeAnEnemy(1, 13, "thug");
    	placeAnEnemy(2, 13, "thug");
    	placeAnEnemy(3, 17, "thug");
    	placeAnEnemy(0, 20, "thug");
    	placeAnEnemy(1, 21, "thug");
    	placeAnEnemy(3, 24, "thug");
    	placeAnEnemy(2, 28, "thug");
    	placeAnEnemy(1, 32, "thug");
    	placeAnEnemy(2, 39, "thug");
    	placeAnEnemy(3, 7, "enforcer");
    	placeAnEnemy(0, 10, "enforcer");
    	placeAnEnemy(2, 20, "enforcer");
    	placeAnEnemy(0, 28, "enforcer");
    	placeAnEnemy(2, 32, "enforcer");
    	placeAnEnemy(1, 36, "enforcer");
    	placeAnEnemy(2, 36, "enforcer");
    	placeAnEnemy(3, 36, "enforcer");
    	placeAnEnemy(0, 42, "enforcer");
    	placeAnEnemy(2, 44, "enforcer");
    	placeAnEnemy(3, 44, "enforcer");
    }
    
    /**
     * Places an enemy in one of the four rows and forty-five columns of the game and determines the type of enemy
     * by using type and it adds the enemy to myEnemies, the image of the enemy to myImages and the children of 
     * myRoot, and the enemy to myThugs if it is a thug.
     * 
     * @param row - the number 0-3 which represents which row the enemy should be placed in
     * @param column - the number 7-45 which represents which column the enemy should be placed in
     * @param type - the String which represents what type of enemy is being placed
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
     * Initializes myObstacles, myGuns, and myVests and places all of the obstacles except for the finish line
     * using a bunch of calls to the method placeAnObstacle.
     */
    private void placeObstacles(){
    	myObstacles = new ArrayList<Obstacle>();
    	myGuns = new ArrayList<GunPowerUp>();
    	myVests = new ArrayList<VestPowerUp>();
    	placeAnObstacle(1, 24, "dog");
    	placeAnObstacle(3, 28, "dog");
    	placeAnObstacle(2, 24, "can");
    	placeAnObstacle(1, 42, "can");
    	placeAnObstacle(1, 17, "hydrant");
    	placeAnObstacle(0, 24, "hydrant");
    	placeAnObstacle(1, 18, "gun");
    	placeAnObstacle(1, 14, "vest");
    }
    
    /**
     * Places the obstacle into one of the four rows and forty-five columns of the game and determines its type from
     * type and adds the obstacle to myObstacles, the image of the obstacle to myImages and the children of myRoot, 
     * the obstacle to myGuns if it is a gun, and the obstacle to myVests if it is a vest.
     * 
     * @param row - the number 0-3 that determines the row the obstacle will be placed in
     * @param column - the number 7-45 that determines the column the obstacle will be place in
     * @param type - the String that represents what type of obstacle is being placed
     */
    private void placeAnObstacle(int row, int column, String type){
    	Obstacle obstacle;
    	if (type.equals("dog")){
    		obstacle = new DogObstacle();
    	}
    	else if (type.equals("can")){
    		obstacle = new TrashCanObstacle();
    	}
    	else if (type.equals("hydrant")){
    		obstacle = new FireHydrantObstacle();
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
     * clicked, you beat the level and the LevelCompletedScreen pops up and the animation stops.
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
            	if (myHero.checkDamagePerShot() != INITIAL_DAMAGE){
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
            	new LevelCompletedScreen(myStage, mySize/2);
            	myAnimation.stop();
            	break;
            default:
            	break;
        }
    }
}