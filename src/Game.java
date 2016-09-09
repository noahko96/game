import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Robert C. Duvall
 */
class Game {
    public static final String TITLE = "Mafia Mayhem";
    public static final int KEY_INPUT_SPEED = 5;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;

    private Scene myScene;
    private Hero myHero;
    private ArrayList<Bullet> myBullets;
    


    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
        Group root = new Group();
        myScene = new Scene(root, width, height, Color.WHITE);
        myHero = new Hero();
        myBullets = new ArrayList<Bullet>();
        // x and y represent the top left corner, so center it
        myHero.getImage().setX(0);
        myHero.getImage().setY(height / 2  - myHero.getImage().getBoundsInLocal().getHeight() / 2);
        root.getChildren().add(myHero.getImage());
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     * but these simple ways work too.
     */
    public void step (double elapsedTime) {
    	for (Bullet bullet: myBullets)
    		bullet.getShape().setCenterX(bullet.getShape().getCenterX() + 10 * BOUNCER_SPEED * elapsedTime);
        
        // check for collisions
        // with shapes, can check precisely
//        Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
//        if (intersect.getBoundsInLocal().getWidth() != -1) {
//            myTopBlock.setFill(Color.MAROON);
//       }
//        else {
//            myTopBlock.setFill(Color.RED);
//        }
        // with images can only check bounding box
//        if (myBottomBlock.getBoundsInParent().intersects(myHero.getImage().getBoundsInParent())) {
//            myBottomBlock.setFill(Color.BURLYWOOD);
//        }
//        else {
//            myBottomBlock.setFill(Color.BISQUE);
//        }
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                myHero.getImage().setX(myHero.getImage().getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                myHero.getImage().setX(myHero.getImage().getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                myHero.getImage().setY(myHero.getImage().getY() - 10*KEY_INPUT_SPEED);
                break;
            case DOWN:
                myHero.getImage().setY(myHero.getImage().getY() + 10*KEY_INPUT_SPEED);
                break;
            case SPACE:
            	Bullet bullet = new Bullet();
            	bullet.getShape().setCenterX(myHero.getImage().getX() + 100);
            	bullet.getShape().setCenterY(myHero.getImage().getY() + 25);
            	((Group) myScene.getRoot()).getChildren().add(bullet.getShape());
            	myBullets.add(bullet);
            	break;
            default:
                // do nothing
        }
    }

    // What to do each time a key is pressed
//    private void handleMouseInput (double x, double y) {
//        if (myBottomBlock.contains(x, y)) {
//            myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
//            myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
//        }
//    }
}
