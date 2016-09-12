import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * class that creates the first splash screen
 * 
 * @author Noah Over
 *
 */
public class SplashScreen1 {
	private static final Paint BACKGROUND_COLOR = Color.BLACK;
	private static final String ARROWS_APPEARANCE = "Arrow Keys.jpg";
	private static final String SPACE_BAR_APPEARANCE = "Space Bar.png";
	private static final int ARROWS_WIDTH = 100;
	private static final int SPACE_BAR_WIDTH = 200;
	private static final String BUTTON_TEXT = "Continue";
	private static final Paint FONT_COLOR = Color.RED;
	private static final int KEYBOARD_X_OFFSET = 25;
	private static final int SPACE_Y_OFFSET = 25;
	private static final int ARROW_Y_OFFSET = 65;
	private static final int BUTTON_X_OFFSET = 45;
	private static final int BUTTON_Y_OFFSET = 50;
	private static final int SPACE_TEXT_OFFSET = 45;
	private static final int ARROW_TEXT_OFFSET = 112;
	private static final String SPACE_TEXT = "Hit SPACE to shoot.";
	private static final String ARROW_TEXT = "Hit UP to jump up.\nHit DOWN to jump down.\nHit RIGHT to move forward.\nHit LEFT to move back.";
	
	private Scene myScene;
	private ImageView myArrows;
	private ImageView mySpaceBar;
	private Button myButton;
	private int mySize;
	private Text mySpaceBarText;
	private Text myArrowsText;
	private Stage myStage;
	private EventHandler myHandler;
	
	/**
	 * constructor for SplashScreen1 which initializes the size and the stage, calls init to create the scene, shows
	 * the scene, and connects the button to its handler
	 * 
	 * @param s the stage
	 * @param size the size
	 */
	public SplashScreen1(Stage s, int size){
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
				new SplashScreen2(myStage, mySize);
			}
		};
	}
	
	private void init () {
        Group root = new Group();
        myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(SPACE_BAR_APPEARANCE));
        mySpaceBar = new ImageView(image);
        mySpaceBar.setX(KEYBOARD_X_OFFSET);
        mySpaceBar.setY(mySize/2 - SPACE_Y_OFFSET);
        mySpaceBar.setFitWidth(SPACE_BAR_WIDTH);
        mySpaceBar.setPreserveRatio(true);
        image = new Image(getClass().getClassLoader().getResourceAsStream(ARROWS_APPEARANCE));
        myArrows = new ImageView(image);
        myArrows.setX(mySpaceBar.getX() + SPACE_BAR_WIDTH + KEYBOARD_X_OFFSET);
        myArrows.setY(mySize/2 - ARROW_Y_OFFSET);
        myArrows.setFitWidth(ARROWS_WIDTH);
        myArrows.setPreserveRatio(true);
        myButton = new Button(BUTTON_TEXT);
        myButton.relocate(mySize/2 - BUTTON_X_OFFSET, mySize - BUTTON_Y_OFFSET);
        mySpaceBarText = new Text(mySpaceBar.getX(), mySpaceBar.getY() + SPACE_TEXT_OFFSET, SPACE_TEXT);
        mySpaceBarText.setFill(FONT_COLOR);
        myArrowsText = new Text(myArrows.getX(), myArrows.getY() + ARROW_TEXT_OFFSET, ARROW_TEXT);
        myArrowsText.setFill(FONT_COLOR);
        root.getChildren().add(myArrows);
        root.getChildren().add(mySpaceBar);
        root.getChildren().add(myButton);
        root.getChildren().add(mySpaceBarText);
        root.getChildren().add(myArrowsText);
    }

}
