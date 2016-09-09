import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Bullet {
	public static final int USUAL_RADIUS = 3;
	public static final Paint BULLET_COLOR = Color.ORANGERED;
	
	private Circle myShape;
	
	public Bullet(){
		myShape = new Circle(USUAL_RADIUS, BULLET_COLOR);
	}
	
	public Bullet(int radius){
		myShape = new Circle(radius, BULLET_COLOR);
	}
	
	public Circle getShape(){
		return myShape;
	}

}
