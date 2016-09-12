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
 * class that creates the third splash screen
 * 
 * @author Noah Over
 *
 */
public class SplashScreen3 {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final Paint FONT_COLOR = Color.RED;
	private static final String BUTTON_TEXT = "Continue";
	private static final int WIDTH = 100;
	private static final int CENTERING_OFFSET = 10;
	private static final int BUTTON_X_OFFSET = 45;
	private static final int BUTTON_Y_OFFSET = 50;
	private static final String BOSS_TEXT = "Take out boss before\nthe finish line to win.";
	
	private Stage myStage;
	private int mySize;
	private Scene myScene;
	private Boss myBoss;
	private Text myBossText;
	private Button myButton;
	private EventHandler myHandler;
	
	/**
	 * constructor for SplashScreen3 which initializes the size and the stage, calls init to make the scene, shows 
	 * the scene, and connects the button with its handler.
	 * 
	 * @param s the stage
	 * @param size the size
	 */
	public SplashScreen3(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myButton.setOnAction(myHandler);
	}
	
	private void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new BossLevel(myStage, mySize);
			}
		};
	}
	
	private void init(){
		Group root = new Group();
		myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
		myBoss = new Boss();
		myBoss.getImage().setX(mySize/2 - WIDTH/2);
		myBoss.getImage().setY(mySize/2 - myBoss.getImage().getBoundsInLocal().getHeight()/2);
		myBossText = new Text(myBoss.getImage().getX(), myBoss.getImage().getY() + myBoss.getImage().getBoundsInLocal().getHeight() + CENTERING_OFFSET, BOSS_TEXT);
		myBossText.setFill(FONT_COLOR);
		myButton = new Button(BUTTON_TEXT);
		myButton.relocate(mySize/2 - BUTTON_X_OFFSET, mySize - BUTTON_Y_OFFSET);
		root.getChildren().add(myBoss.getImage());
		root.getChildren().add(myBossText);
		root.getChildren().add(myButton);
	}

}
