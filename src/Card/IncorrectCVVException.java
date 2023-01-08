package Card;

public class IncorrectCVVException extends Exception {
	
	public IncorrectCVVException() {
		super("Incorrect CVV Length");
	}
}
