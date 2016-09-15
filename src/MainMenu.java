import java.util.ArrayList;

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
 * Class that creates the Main Menu.
 * It is called by Main.
 * It can be used by calling the constructor (e.g., MainMenu(s, 400)).
 * 
 * @author Noah Over
 *
 */
public class MainMenu {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final String BUTTON_TEXT = "START GAME";
	private static final int WIDTH_OFFSET = 45;
	private static final int HEIGHT_OFFSET = 20;
	private static final String TITLE = "Mafia Mayhem";
	private static final int TITLE_X_VALUE = 90;
	private static final String FONT = "Chiller";
	private static final int FONT_SIZE = 50;
	private static final Paint FONT_COLOR = Color.RED;
	
	private Scene myScene;
	private Button myStartButton;
	private EventHandler myHandler;
	private Text myHeader;
	private int mySize;
	private Stage myStage;
	
	/**
	 * Constructor for MainMenu that initializes the stage and the size, calls init to make the scene, calls getTitle
	 * to get the title, shows the scene and title, and connects the button to its handler
	 * 
	 * @param s - the stage on which to display the menu
	 * @param size - the length and the width of the scene
	 */
	public MainMenu(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setTitle(getTitle());
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myStartButton.setOnAction(myHandler);
	}
	
    /**
     * Returns name of the game.
     * 
     * @return the name of the game
     */
	private String getTitle () {
        return TITLE;
    }
	
	/**
	 * Establishes that the button will take you to Splash Screen 1 when pushed.
	 */
	private void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new SplashScreen1(myStage, mySize);
			}
		};
	}
	
	/**
	 * Creates the scene by adding the background color, placing the header, and placing the button.
	 */
	private void init () {
        Group root = new Group();
        myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
        myStartButton = new Button(BUTTON_TEXT);
        myStartButton.relocate(mySize/2 - WIDTH_OFFSET, mySize/2 + HEIGHT_OFFSET);
        myHeader = new Text(TITLE_X_VALUE, mySize/2 - 2*HEIGHT_OFFSET, TITLE);
        myHeader.setFill(FONT_COLOR);
        myHeader.setFont(new Font(FONT, FONT_SIZE));
        root.getChildren().add(myStartButton);
        root.getChildren().add(myHeader);
    }

}
