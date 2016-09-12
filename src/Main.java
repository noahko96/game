import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create
 * an animated scene.
 * 
 * @author Noah Over
 */
public class Main extends Application {
    public static final int SIZE = 400;


    /**
     * Set things up at the beginning.
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
