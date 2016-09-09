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

public class MainMenu {
	public static final Paint BACKGROUND_COLOR = Color.BLACK;
	public static final String BUTTON_TEXT = "START GAME";
	public static final int WIDTH_OFFSET = 45;
	public static final int HEIGHT_OFFSET = 20;
	public static final String TITLE = "Mafia Mayhem";
	public static final int TITLE_X_VALUE = 90;
	public static final String FONT = "Chiller";
	public static final int FONT_SIZE = 50;
	public static final Paint FONT_COLOR = Color.RED;
	
	private Scene myScene;
	private Button myStartButton;
	private EventHandler myHandler;
	private Text myHeader;
	private int mySize;
	private Stage myStage;
	
	public MainMenu(Stage s, int size){
		mySize = size;
		myStage = s;
		init();
		myStage.setScene(myScene);
		myStage.show();
		establishHandler();
		myStartButton.setOnAction(myHandler);
	}
	
	public void establishHandler(){
		myHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				new SplashScreen1(myStage, mySize);
			}
		};
	}
	
	
	public void init () {
        Group root = new Group();
        myScene = new Scene(root, mySize, mySize, BACKGROUND_COLOR);
        myStartButton = new Button(BUTTON_TEXT);
        myStartButton.setLayoutX(mySize/2 - WIDTH_OFFSET);
        myStartButton.setLayoutY(mySize/2 + HEIGHT_OFFSET);
        myHeader = new Text(TITLE_X_VALUE, mySize/2 - 2*HEIGHT_OFFSET, TITLE);
        myHeader.setFill(FONT_COLOR);
        myHeader.setFont(new Font(FONT, FONT_SIZE));
        root.getChildren().add(myStartButton);
        root.getChildren().add(myHeader);
    }

}
