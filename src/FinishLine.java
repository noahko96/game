

/**
 * Class that keeps track of the information on the finish line flags
 * Depends on the superclass Enemy and is used in Level1.
 * It can be used by calling one of the constructors; FinishLine() or FinishLine("Mafia Mayhem flag.png") which both
 * give you the same result.
 * 
 * @author Noah Over
 *
 */
public class FinishLine extends Obstacle{
	private static final String APPEARANCE = "Mafia Mayhem flag.png";
	
	/**
	 * Constructor for FinishLine that calls the superclass Obstacle constructor with the default appearance
	 */
	public FinishLine(){
		super(APPEARANCE);
	}
	
	/**
	 * Constructor for FinishLine that calls the superclass Obstacle constructor
	 * 
	 * @param appearance - the String that represents the image of the finish line flag
	 */
	public FinishLine(String appearance){
		super(appearance);
	}
}
