package Card;

public class IncorrectCardNumberException extends Exception{
	
	public IncorrectCardNumberException(){
		super("Incorrect Card Number");
	}
}
