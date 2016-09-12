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
 * class that runs the first level of the game
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
    private ArrayList<Enforcer> myEnforcers;
    private ArrayList<TrashCanObstacle> myTrash;
    private ArrayList<Dog> myDogs;
    private ArrayList<FireHydrant> myHydrants;
    private ArrayList<GunPowerUp> myGuns;
    private ArrayList<VestPowerUp> myVests;
    private ArrayList<ImageView> myImages;
    private ArrayList<ImageView> myObstacles;
    private int myGunTracker;
    private int myVestTracker;
    private int myTimer;
    
    /**
     * constructor for Level1 which initializes the stage, the size, the powerup tracker, and the timer; calls init
     * to make the scene, shows the scene, and runs the game loop
     * 
     * @param s the stage
     * @param halfSize half the size
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
     * Create the game's scene
     */
    private void init () {
        myRoot = new Group();
        myScene = new Scene(myRoot, mySize, mySize, BACKGROUND_COLOR);
        myHero = new Hero();
        myBullets = new ArrayList<Bullet>();
        myEnemyBullets = new ArrayList<Bullet>();
        myImages = new ArrayList<ImageView>();
        myObstacles = new ArrayList<ImageView>();
        placeFinishLine();
        placeThugs();
        placeEnforcers();
        placeDogs();
        placeTrash();
        placeHydrants();
        placeGuns();
        placeVests();
        myHero.getImage().setX(0);
        myHero.getImage().setY(mySize / 2  - myHero.getImage().getBoundsInLocal().getHeight() / 2);
        myRoot.getChildren().add(myHero.getImage());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    private void step (double elapsedTime) {
    	myTimer++;
    	moveImages(elapsedTime);
    	moveBulletsAndCheckIntersections(elapsedTime);
    	moveEnemyBulletsAndCheckIntersections(elapsedTime);
    	checkFinishLineIntersections();
    	checkDogIntersections();
    	checkTrashIntersections();
    	checkFireHydrantIntersections();
    	checkGunIntersections();
    	checkVestIntersections();
    	checkThugIntersectionsDeathAndFireWeapon();
    	checkEnforcerDeath();
    	setHeroRestrictions();
    	checkPowerUpTrackers();
    }


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


	private void checkEnforcerDeath() {
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


	private void checkThugIntersectionsDeathAndFireWeapon() {
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


	private void checkFireHydrantIntersections() {
		for (FireHydrant hydrant: myHydrants){
    		if (hydrant.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			hydrant.getIsActive()){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
    			}
    			else {
    				myVestTracker++;
    			}
    			hydrant.notActive();
    			myRoot.getChildren().remove(hydrant.getImage());
    		}
    	}
	}


	private void checkTrashIntersections() {
		for (TrashCanObstacle can: myTrash){
    		if (can.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent()) &&
    			can.getIsActive()){
    			if (!myHero.checkInvincibility()){
    				myHero.loseLife();
   			}
    			else {
    				myVestTracker++;
    			}
    			can.notActive();
   			myRoot.getChildren().remove(can.getImage());
    		}
    	}
	}


	private void checkDogIntersections() {
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


	private void checkFinishLineIntersections() {
		for (FinishLine flag: myFinish){
    		if (flag.getImage().getBoundsInParent().intersects(myHero.getImage().getBoundsInParent())){
    			new LevelCompletedScreen(myStage, mySize/2);
    			myAnimation.stop();
    		}
    	}
	}


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
    		for (ImageView obstacle: myObstacles){
    			if (bullet.getShape().getBoundsInParent().intersects(obstacle.getBoundsInParent()) &&
    				bullet.getIsActive()){
    				stopBullet(bullet);
    			}
    		}
    	}
	}


	private void moveBulletsAndCheckIntersections(double elapsedTime) {
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
			if (bullet.getShape().getCenterX() > myStage.getWidth()){
				stopBullet(bullet);
			}
    	}
	}


	private void moveImages(double elapsedTime) {
		for (ImageView image: myImages){
    		image.setX(image.getX() - OBJECT_SPEED*elapsedTime);
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
    
    private void placeThugs(){
    	myThugs = new ArrayList<Thug>();
    	placeAThug(1, 7);
    	placeAThug(1, 13);
    	placeAThug(2, 13);
    	placeAThug(3, 17);
    	placeAThug(0, 20);
    	placeAThug(1, 21);
    	placeAThug(3, 24);
    	placeAThug(2, 28);
    	placeAThug(1, 32);
    	placeAThug(2, 39);
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
    	placeAnEnforcer(3, 7);
    	placeAnEnforcer(0, 10);
    	placeAnEnforcer(2, 20);
    	placeAnEnforcer(0, 28);
    	placeAnEnforcer(2, 32);
    	placeAnEnforcer(1, 36);
    	placeAnEnforcer(2, 36);
    	placeAnEnforcer(3, 36);
    	placeAnEnforcer(0, 42);
    	placeAnEnforcer(2, 44);
    	placeAnEnforcer(3, 44);
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
    	placeADog(1, 24);
    	placeADog(3, 28);
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
    
    private void placeTrash(){
    	myTrash = new ArrayList<TrashCanObstacle>();
    	placeACan(2, 24);
    	placeACan(1, 42);
    }
    
    private void placeACan(int row, int column){
    	TrashCanObstacle can = new TrashCanObstacle();
    	can.getImage().setX(column*COLUMN_WIDTH);
    	can.getImage().setY(row*ROW_HEIGHT);
    	myTrash.add(can);
    	myObstacles.add(can.getImage());
    	myImages.add(can.getImage());
    	myRoot.getChildren().add(can.getImage());
    }
    
    private void placeHydrants(){
    	myHydrants = new ArrayList<FireHydrant>();
    	placeAHydrant(1, 17);
    	placeAHydrant(0, 24);
    }
    
    private void placeAHydrant(int row, int column){
    	FireHydrant hydrant = new FireHydrant();
    	hydrant.getImage().setX(column*COLUMN_WIDTH);
    	hydrant.getImage().setY(row*ROW_HEIGHT);
    	myHydrants.add(hydrant);
    	myObstacles.add(hydrant.getImage());
    	myImages.add(hydrant.getImage());
    	myRoot.getChildren().add(hydrant.getImage());
    }
    
    private void placeGuns(){
    	myGuns = new ArrayList<GunPowerUp>();
    	placeAGun(1, 18);
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
    	placeAVest(1, 14);
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