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

public class SplashScreen1 {
	public static final Paint BACKGROUND_COLOR = Color.BLACK;
	public static final String ARROWS_APPEARANCE = "Arrow Keys.jpg";
	public static final String SPACE_BAR_APPEARANCE = "Space Bar.png";
	public static final int ARROWS_WIDTH = 100;
	public static final int SPACE_BAR_WIDTH = 200;
	public static final String BUTTON_TEXT = "Continue";
	public static final Paint FONT_COLOR = Color.RED;
	
	private Scene myScene;
	private ImageView myArrows;
	private ImageView mySpaceBar;
	private Button myButton;
	private int mySize;
	private Text mySpaceBarText;
	private Text myArrowsText;
	
	public SplashScreen1(Stage s, int size){
		mySize = size;
		init();
		s.setScene(myScene);
		s.show();
	}
	
	public void init () {
        Group root = new Group();
        myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(SPACE_BAR_APPEARANCE));
        mySpaceBar = new ImageView(image);
        mySpaceBar.setX(25);
        mySpaceBar.setY(mySize/2 - 25);
        mySpaceBar.setFitWidth(SPACE_BAR_WIDTH);
        mySpaceBar.setPreserveRatio(true);
        image = new Image(getClass().getClassLoader().getResourceAsStream(ARROWS_APPEARANCE));
        myArrows = new ImageView(image);
        myArrows.setX(mySpaceBar.getX() + SPACE_BAR_WIDTH + 25);
        myArrows.setY(mySize/2 - 65);
        myArrows.setFitWidth(ARROWS_WIDTH);
        myArrows.setPreserveRatio(true);
        myButton = new Button(BUTTON_TEXT);
        myButton.setLayoutX(mySize/2 - 45);
        myButton.setLayoutY(mySize - 50);
        mySpaceBarText = new Text(mySpaceBar.getX(), mySpaceBar.getY() + 45, "Hit SPACE to shoot.");
        mySpaceBarText.setFill(FONT_COLOR);
        myArrowsText = new Text(myArrows.getX(), myArrows.getY() + 112, "Hit UP to jump.\nHit RIGHT to move forward.\nHit LEFT to move back.");
        myArrowsText.setFill(FONT_COLOR);
        root.getChildren().add(myArrows);
        root.getChildren().add(mySpaceBar);
        root.getChildren().add(myButton);
        root.getChildren().add(mySpaceBarText);
        root.getChildren().add(myArrowsText);
    }

}
