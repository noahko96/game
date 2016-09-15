import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that creates the second splash screen.
 * It is called by Splash Screen 1.
 * It can be used by calling the constructor (e.g., SplashScreen2(s, 400))
 * 
 * @author Noah Over
 *
 */
public class SplashScreen2 {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final Paint FONT_COLOR = Color.RED;
	private static final String BUTTON_TEXT = "Continue";
	private static final int THUG_X_OFFSET = 75;
	private static final int TRASH_X_OFFSET = 50;
	private static final int GUN_X_OFFSET = 25;
	private static final int BUTTON_X_OFFSET = 45;
	private static final int BUTTON_Y_OFFSET = 50;
	private static final String THUG_TEXT = "Take out enemies.";
	private static final String TRASH_TEXT = "Avoid obstacles.";
	private static final String GUN_TEXT = "Pick up powerups.";
	private static final int CENTERING_OFFSET = 10;
	
	private Stage myStage;
	private int mySize;
	private Scene myScene;
	private Thug myThug;
	private TrashCanObstacle myTrash;
	private GunPowerUp myGun;
	private Text myThugText;
	private Text myTrashText;
	private Text myGunText;
	private Button myButton;
	private EventHandler myHandler;
	
	/**
	 * Constructor for SplashScreen2 which initializes the size and the stage, calls init to make the scene, shows
	 * the scene, and connects the button to its handler
	 * 
	 * @param s - the stage to show the splash screen on
	 * @param size - the length and width of the scene
	 */
	public SplashScreen2(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myButton.setOnAction(myHandler);
	}
	
	/**
	 * Establishes that the button will take you to level 1 when pushed.
	 */
	private void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new Level1(myStage, mySize);
			}
		};
	}
	
	/**
	 * Creates the scene by picking the background color, placing the thug, trash can, and gun images, placing the
	 * instructions for the thug, trash can, and gun, and placing the button.
	 */
	private void init(){
		Group root = new Group();
		myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
		myThug = new Thug();
		myThug.getImage().setX(mySize/4 - THUG_X_OFFSET);
		myThug.getImage().setY(mySize/2 - myThug.getImage().getBoundsInLocal().getHeight()/2);
		myTrash = new TrashCanObstacle();
		myTrash.getImage().setX(mySize/2 - TRASH_X_OFFSET);
		myTrash.getImage().setY(mySize/2 - myTrash.getImage().getBoundsInLocal().getHeight()/2);
		myGun = new GunPowerUp();
		myGun.getImage().setX(3*mySize/4 - GUN_X_OFFSET);
		myGun.getImage().setY(mySize/2 - myGun.getImage().getBoundsInLocal().getHeight()/2);
		myThugText = new Text(myThug.getImage().getX(), myThug.getImage().getY() + myThug.getImage().getBoundsInLocal().getHeight() + CENTERING_OFFSET, THUG_TEXT);
		myThugText.setFill(FONT_COLOR);
		myTrashText = new Text(myTrash.getImage().getX(), myTrash.getImage().getY() + myTrash.getImage().getBoundsInLocal().getHeight() + CENTERING_OFFSET, TRASH_TEXT);
		myTrashText.setFill(FONT_COLOR);
		myGunText = new Text(myGun.getImage().getX(), myGun.getImage().getY() + myGun.getImage().getBoundsInLocal().getHeight() + CENTERING_OFFSET, GUN_TEXT);
		myGunText.setFill(FONT_COLOR);
		myButton = new Button(BUTTON_TEXT);
		myButton.relocate(mySize/2 - BUTTON_X_OFFSET, mySize - BUTTON_Y_OFFSET);
		root.getChildren().add(myThug.getImage());
		root.getChildren().add(myTrash.getImage());
		root.getChildren().add(myGun.getImage());
		root.getChildren().add(myThugText);
		root.getChildren().add(myTrashText);
		root.getChildren().add(myGunText);
		root.getChildren().add(myButton);
	}

}
