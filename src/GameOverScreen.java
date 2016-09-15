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
 * Class that creates the Game Over screen
 * It is called by Level1 and BossLevel.
 * It can be used by calling the constructor (e.g., GameOverScreen(s, 400)).
 * 
 * @author Noah Over
 *
 */
public class GameOverScreen {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final String BUTTON_TEXT = "Main Menu";
	private static final int WIDTH_OFFSET = 45;
	private static final int HEIGHT_OFFSET = 20;
	private static final String MESSAGE = "GAME OVER";
	private static final int MESSAGE_X_VALUE = 100;
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
	 * Constructor for the Game Over Scene which initializes the size and stage, calls init to create the scene, 
	 * shows the scene on the stage, and connects the button with its handler
	 * 
	 * @param s - the stage
	 * @param size - the length and the width of the scene
	 */
	public GameOverScreen(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myButton.setOnAction(myHandler);
	}
	
	/**
	 * Establishs that myButton will take you to the main menu when pressed
	 */
	private void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new MainMenu(myStage, mySize);
			}
		};
	}
	
	/**
	 * Creates the scene by making the background, placing the header, and placing the button.
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
