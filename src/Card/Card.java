package Card;

import java.io.Serializable;

public class Card implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long cardNumber;
	private short cardCVV;
	private ExpDate cardExpDate;
	
//	About Card Validity
	private static final byte MAX_LENGTH = 16;
	private static final byte MIN_LENGTH = 12;
	
	
//	Constructors
	public Card() {
	}
	
	
	public Card(long cardNumber, short cardCVV, ExpDate cardExpDate) {
		this.cardNumber = cardNumber;
		this.cardCVV = cardCVV;
		this.cardExpDate = cardExpDate;
	}


	public static boolean checkCardNumberValidity(long cardNumber) {
		int cardNumberLength;

		//	Convert long to string
		String strCardNumber = cardNumber + "";
		
		//	Check the length of the card
		cardNumberLength = strCardNumber.length();
		
		if(cardNumberLength < MIN_LENGTH || cardNumberLength > MAX_LENGTH) {
			return false;
		}
		
		//	Apply Luhn Algorithm	
		return luhnAlgorithm(strCardNumber);
	}
	
	public static boolean checkCardNumberValidity(String strCardNumber) {
		long cardNumber;
		
		// Convert String to Long and check card validity
		try {
			cardNumber = Long.parseLong(strCardNumber);
			return checkCardNumberValidity(cardNumber);
			
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean luhnAlgorithm(String strCardNumber) {
		String reversedCardNumber;
		StringBuilder strBuilder = new StringBuilder();
		
		//	Card length can't be bigger than 16 so use byte		
		byte cardNumberLength = (byte) strCardNumber.length();
		byte i;
		byte value;
		short total = 0;
		String[] splitNumber;
		//	Reverse Card Order
		strBuilder.append(strCardNumber);
		strBuilder.reverse();
		reversedCardNumber = strBuilder.toString();
		splitNumber = reversedCardNumber.split("") ;
		
		// Apply Luhn Algorithm
		for(i=0;i<cardNumberLength;i++) {
			
			value = Byte.parseByte(splitNumber[i]);
			
			// For every odd digit double it and make sure it is 1 digit long 			
			if(i%2 != 0) {
				value *= 2;
				
				if(value > 9) {
					value -= 9;
				}
			}
			
			total += value;
		}
		
		//	If total can be divided by 0, the algorithm is true.		
		if(total % 10 == 0) {
			return true;
		}
		else {
			return false;
		}
	}


	
//	Getters and Setters

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) throws IncorrectCardNumberException {
		
		if(checkCardNumberValidity(cardNumber)) {			
			this.cardNumber = cardNumber;
		}
		else {
			throw new IncorrectCardNumberException();
		}
	}
	
	public void setCardNumber(String cardNumber) throws IncorrectCardNumberException {
		
		if(checkCardNumberValidity(cardNumber)) {			
			this.cardNumber = Long.parseLong(cardNumber);
		}
		else {
			throw new IncorrectCardNumberException();
		}
	}

	public short getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(short cardCVV) throws IncorrectCVVException {

		//	Check if CVV has 3-4 digit 		
		if(cardCVV < 10000) {			
			this.cardCVV = cardCVV;
		}
		else {
			throw new IncorrectCVVException();
		}
	}
	
	public void setCardCVV(String strCardCVV)throws IncorrectCVVException{
		
		try {			
			short cardCVV = Short.parseShort(strCardCVV);
			setCardCVV(cardCVV);
		}catch (Exception e){
			throw new IncorrectCVVException();
		}
		
	}
	
	public ExpDate getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(ExpDate cardExpDate) {
		this.cardExpDate = cardExpDate;
	}
	


	
	
}
	
	
	