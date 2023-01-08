package tcpConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Card.Card;
import Card.ExpDate;
import Card.IncorrectCVVException;
import Card.IncorrectCardNumberException;
import Card.IncorrectDateException;

public class UI {
	
	private static final String EXIT_KEY = "E";
	
	public static Card getCardInfo() {
		
		Scanner sc = new Scanner(System.in);
		Card card = new Card();
		ExpDate expDate = new ExpDate();
		boolean isValueCorrect = false;
		String res;
		String[] splitString;
		String escapeKey = "E";
		
		
//		Get Card Number
		isValueCorrect = false;
		while(!isValueCorrect) {
			
			printSeperation();
			System.out.println("INFO [To Exit Enter E]");
			System.out.print("\nEnter Card Number: ");

			//	Exit			
			res = sc.next();
			if(res.equalsIgnoreCase(escapeKey)) {
				sc.close();
				return null;
			}
			
			try {
				card.setCardNumber(res);
				isValueCorrect = true;
			} catch (IncorrectCardNumberException e) {
				System.out.println("Incorrect Card Number. Please Enter A Valid Card Number.");
			}
		}
		

//		Get Card Date
		isValueCorrect = false;
		while(!isValueCorrect) {
			
			printSeperation();
			System.out.println("INFO [To Exit Enter E]");
			System.out.print("\nEnter Card Expiration Date [MM/YY]: ");
			res = sc.next();
			
			//	Exit			
			res = sc.next();
			if(res.equalsIgnoreCase(escapeKey)) {
				sc.close();
				return null;
			}
			
			
			splitString = res.split("/");
			
			if(splitString.length != 2) {
				System.out.println("Incorrect Date Format. Please Use [MM/YY].");
			}
			else {
				try {
					expDate.setMonth(splitString[0]);
					expDate.setYear(splitString[1]);
					card.setCardExpDate(expDate);
					isValueCorrect = true;
				}catch (IncorrectDateException e) {
					System.out.println("Incorrect Date. Please Use [MM/YY].");
				}
			}
		}
		
//		Get Card CVV
		isValueCorrect = false;
		while(!isValueCorrect) {	
			
			printSeperation();
			System.out.println("INFO [To Exit Enter E]");
			System.out.print("\nEnter Card CVV: ");
			res = sc.next();
			
			//	Exit			
			res = sc.next();
			if(res.equalsIgnoreCase(escapeKey)) {
				sc.close();
				return null;
			}
			
			try {
				card.setCardCVV(res);
				isValueCorrect = true;
			}catch (IncorrectCVVException e){
				System.out.println("Incorrect CVV");
			}
		}
		
		sc.close();
		return card;
	}
	
	public static void printSeperation() {
		System.out.println("\n------------------------------------------------------");
	}
	
	public static User registerUser() {
		User user = new User();
		String res;
		LocalDate date;

		
//		Intro for the module
		printSeperation();
		System.out.println("Welcome to The Registration Module ");
		System.out.println("To Exit Press E At Any Point \n\n");
		
//		Get Name
		if((res = getUserName()) == null) {
			return null;
		}
		user.setName(res);
		
//		Get Surname
		if((res = getUserSurname()) == null) {
			return null;
		}
		user.setSurname(res);
		
//		Get Date Of Birth
		if((date = getUserBirthDate()) == null) {
			return null;
		}
		user.setDateOfBirth(date);
		
//		Get Email
		if((res = getUserEmail()) == null) {
			return null;
		}
		user.setEmail(res);
		
		
//		Get Phone Number
		if((res = getUserEmail()) == null) {
			return null;
		}
		user.setPhoneNumber(res);
		
		
//		Get UserName
		if((res = getUserUsername()) == null) {
			return null;
		}
		user.setUsername(res);
	
		
//		Get Password
		if((res = getUserPassword()) == null) {
			return null;
		}
		user.setPassword(res);
		
		return user;
	}
	
	public static String getUserName() {
		
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter Your Name: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}
		sc.close();
		return res;
		
	}
	
	public static String getUserSurname() {
		
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter Your Surname: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}
		sc.close();
		return res;
	}
	
	public static LocalDate getUserBirthDate() {
		
		Scanner sc = new Scanner(System.in);
		String res;
		String[] splitString;
		LocalDate date = LocalDate.of(0, 0, 0);
		Boolean isValueTrue = false;
		ArrayList<Integer> intDate = new ArrayList<Integer>();
		
		while(!isValueTrue) {
			
			printSeperation();
			System.out.print("Enter Your Day Of Birth [DD/MM/YYYY]: ");
			res = sc.next();
			
			if(res.equalsIgnoreCase(EXIT_KEY)) {
				sc.close();
				return null;
			}
			
			splitString = res.split("/");
			
			if(splitString.length != 3) {
				System.out.println("Incorrect Date Format. Please Use [DD/MM/YYYY] ");
				continue;
			}
			else {
				
				for(int i=0;i<3;i++) {
					try {
						intDate.add(Integer.parseInt(splitString[i]));
					}catch (NumberFormatException e) {
						System.out.println("Incorrect Date Format. Please Use Numbers");
						continue;
					}
				}
				
				date = LocalDate.of(intDate.get(2),intDate.get(1),intDate.get(0));
				isValueTrue = true;
				
			}
		}
		
		sc.close();
		return date;
		
	}

	public static String getUserEmail() {
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter Your E-Mail: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}
		sc.close();
		return res;
	}

	public static String getUserPhoneNumber() {
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter Your Phone Number: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}
		
		sc.close();
		return res;
		
	}
	
	public static String getUserUsername() {
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter A UserName: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}

		sc.close();
		return res;
	}
	
	public static String getUserPassword() {
		Scanner sc = new Scanner(System.in);
		String res;
		
		printSeperation();
		System.out.print("Enter Your Surname: ");
		res = sc.next();
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			sc.close();
			return null;
		}
		sc.close();
		return res;
	}
}
