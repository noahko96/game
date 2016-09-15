import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main program, it just calls Main Menu to start the game.
 * It can be used by hitting the run button.
 * 
 * @author Noah Over
 */
public class Main extends Application {
    public static final int SIZE = 400;


    /**
     * Call MainMenu.
     */
    @Override
    public void start (Stage s) {
    	new MainMenu(s, SIZE);
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
