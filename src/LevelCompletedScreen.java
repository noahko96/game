import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that creates the Level Completed screen.
 * It is called by Level1.
 * It can be used by calling the constructor (e.g., LevelCompletedScreen(s, 400)).
 * 
 * @author Noah Over
 *
 */
public class LevelCompletedScreen {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final String BUTTON_TEXT = "Next Level";
	private static final int WIDTH_OFFSET = 45;
	private static final int HEIGHT_OFFSET = 20;
	private static final String MESSAGE = "Level Completed!";
	private static final int MESSAGE_X_VALUE = 70;
	private static final Paint FONT_COLOR = Color.RED;
	private static final String FONT = "Chiller";
	private static final int FONT_SIZE = 50;
	
	private int mySize;
	private Stage myStage;
	private Scene myScene;
	private Button myButton;
	private Text myHeader;
	private EventHandler myHandler;
	
	/**
	 * Constructor for LevelCompletedScreen which initializes the stage and the size, calls init to create the scene,
	 * shows the scene, and connects the button to its handler
	 * 
	 * @param s the stage
	 * @param size the size
	 */
	public LevelCompletedScreen(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myButton.setOnAction(myHandler);
	}
	
	/**
	 * Establishes that the button will take you to Splash Screen 3 when pressed.
	 */
	private void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new SplashScreen3(myStage, mySize);
			}
		};
	}
	
	/**
	 * Creates the scene by adding the background color, placing the header, and placing the button.
	 */
	private void init(){
		Group root = new Group();
        myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
        myButton = new Button(BUTTON_TEXT);
        myButton.relocate(mySize/2 - WIDTH_OFFSET, mySize/2 + HEIGHT_OFFSET);
        myHeader = new Text(MESSAGE_X_VALUE, mySize/2 - 2*HEIGHT_OFFSET, MESSAGE);
        myHeader.setFill(FONT_COLOR);
        myHeader.setFont(new Font(FONT, FONT_SIZE));
        root.getChildren().add(myButton);
        root.getChildren().add(myHeader);
	}
}
