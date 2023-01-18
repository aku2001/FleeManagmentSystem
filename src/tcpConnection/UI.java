package tcpConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Card.Card;
import Card.ExpDate;
import Card.IncorrectCVVException;
import Card.IncorrectCardNumberException;
import Card.IncorrectDateException;
import Server.RewardSystem;
import Server.Scooter;

public class UI {
	
	private static final String EXIT_KEY = "E";
	
	private Scanner sc;
	
	public UI() {
		sc = new Scanner(System.in);
	}
	
	
	public Card getCardInfo() {
		
		
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
			res = sc.nextLine();
			
			
			if(res.equalsIgnoreCase(escapeKey)) {
				;
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
			res = sc.nextLine();
			
			
			//	Exit			
			if(res.equalsIgnoreCase(escapeKey)) {
				;
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
			
			res = sc.nextLine();
			
			
			//	Exit			
			if(res.equalsIgnoreCase(escapeKey)) {
				;
				return null;
			}
			
			try {
				card.setCardCVV(res);
				isValueCorrect = true;
			}catch (IncorrectCVVException e){
				System.out.println("Incorrect CVV");
			}
		}
		
		;
		return card;
	}
	
	public void printSeperation() {
		System.out.println("\n------------------------------------------------------");
	}
	
	public User registerUser() {
		User user = new User();
		String res;
		LocalDate date;

		
//		Intro for the module
		printSeperation();
		System.out.println("Welcome to The Registration Module ");
		System.out.println("To Exit Press E At Any Point \n\n");
		
		
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
		
		
//		Get Email
		if((res = getUserEmail()) == null) {
			return null;
		}
		user.setEmail(res);
		
		
//		Get Phone Number
		if((res = getUserPhoneNumber()) == null) {
			return null;
		}
		user.setPhoneNumber(res);
		
//		Get Date Of Birth
		if((date = getUserBirthDate()) == null) {
			return null;
		}
		user.setDateOfBirth(date);
		
		
		
		
		return user;
	}
	
	public String getUserName() {
		
		
		String res = null;
		
		printSeperation();
		System.out.print("Enter Your Name: ");
		
		
		res = sc.nextLine();
	

		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}
		;
	
		
		
		return res;
		
	}
	
	public String getUserSurname() {
		
		
		String res;
		
		printSeperation();
		System.out.print("Enter Your Surname: ");
		res = sc.nextLine();
		
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}
		;
		return res;
	}
	
	public LocalDate getUserBirthDate() {
		
		
		String res;
		String[] splitString;
		LocalDate date = null;
		Boolean isValueTrue = false;
		ArrayList<Integer> intDate = new ArrayList<Integer>();
		
		while(! isValueTrue) {
			
			printSeperation();
			System.out.print("Enter Your BirthDate [DD/MM/YYYY]: ");
			res = sc.nextLine();
			
			
			if(res.equalsIgnoreCase(EXIT_KEY)) {
				return null;
			}
			
			splitString = res.split("/");
			
			if(splitString.length != 3) {
				System.out.print("Incorrect Date Format. Please Use [DD/MM/YYYY] ");
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
		
		return date;
		
	}

	public String getUserEmail() {
		
		String res;
		
		printSeperation();
		System.out.print("Enter Your E-Mail: ");
		res = sc.nextLine();
		
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}
		;
		return res;
	}

	public String getUserPhoneNumber() {
		
		String res;
		
		printSeperation();
		System.out.print("Enter Your Phone Number: ");
		res = sc.nextLine();
		
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}
		
		;
		return res;
		
	}
	
	public String getUserUsername() {
		
		String res;
		
		printSeperation();
		System.out.print("Enter A UserName: ");
		res = sc.nextLine();
		
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}

		;
		return res;
	}
	
	public String getUserPassword() {
		
		String res;
		
		printSeperation();
		System.out.print("Enter Your Password: ");
		res = sc.nextLine();
		
		
		if(res.equalsIgnoreCase(EXIT_KEY)) {
			;
			return null;
		}
		;
		return res;
	}

	
	public String showMainMenu() {
		// TODO Auto-generated method stub
		
		String res = null;
		
		printSeperation();
		System.out.println("Main Menu\n");
		System.out.println("To Use the App Please Login Or Register");
		System.out.println("Press 1 To Login");
		System.out.println("Press 2 To Register");
		System.out.print("\nYour Choice: ");
		
					
		res = sc.nextLine();	
		
		
		;
		return res;
		
		
	}

	public User showLoginMenu() {
		// TODO Auto-generated method stub
		
		User user = new User();
		
		printSeperation();
		System.out.println("User Login System\n");
		System.out.print("Enter Username: ");
		user.setUsername(sc.nextLine());
		
		
		System.out.print("Enter Password: ");
		user.setPassword(sc.nextLine());
		
		
		;
		return user;
		
	}

	public String showModules() {
		// TODO Auto-generated method stub
		
		String res;
		
		printSeperation();
		System.out.println("Modules Menu\n");
		System.out.println("Press 1 To Update User Information");
		System.out.println("Press 2 To List Scooters");
		System.out.println("Press 3 To Reserve A Scooter");
		System.out.println("Press 4 To Park The Reserved Scooter");
		System.out.println("Press 5 To See The Rewards");
		System.out.println("Press 6 To Move Client");
		System.out.println("Press 7 To Show User");
		System.out.println("Press 8 To Go Back To Main Menu");
		System.out.print("\nEnter Your Choice: ");
		
		res = sc.nextLine();
		
		;
		return res;
		
	}

	public User showUpdateMenu(Client client,User user) throws IOException {

		
		String res;
		
		printSeperation();
		System.out.println("Update Menu\n");
		System.out.println("Press 1 To Update Name");
		System.out.println("Press 2 To Update Surname");
		System.out.println("Press 3 To Update Password");
		System.out.println("Press 4 To Update Card");
		System.out.println("Press 5 To Update E-mail");
		System.out.println("Press 6 To Update Phone Number");
		System.out.println("Press 7 To Update Birth Day");
		System.out.println("Press Any Other Key To Go Back To Menu");
		System.out.print("\nEnter Your Choice: ");
		res = sc.nextLine();
		
		;
		
		
		if(res.equals(Messages.UI_UPDATE_NAME)) {
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((res = getUserName()) != null){				
				if(client.updateName(res, user.getUsername(), user.getPassword())) {
					user.setName(res);	
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
			}
		}
		
		else if(res.equals(Messages.UI_UPDATE_SURNAME)) {
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((res = getUserSurname()) != null){	
				if(client.updateSurname(res, user.getUsername(), user.getPassword())){					
					user.setSurname(res);
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
	
		else if(res.equals(Messages.UI_UPDATE_PASSWORD)) {
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((res = getUserPassword()) != null){		
				if(client.updatePassword(res, user.getUsername(), user.getPassword())) {
					user.setPassword(res);		
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
		
		else if(res.equals( Messages.UI_UPDATE_CARD)) {
			Card card;
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((card = getCardInfo()) != null){		
				if(client.updateCard(card, user.getUsername(), user.getPassword())) {
					user.setCard(card);		
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
		
		else if(res.equals(Messages.UI_UPDATE_EMAIL)) {
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((res = getUserEmail()) != null){		
				if(client.updateEmail(res, user.getUsername(), user.getPassword())) {
					user.setEmail(res);		
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
		
		else if(res.equals(Messages.UI_UPDATE_PHONE_NUMBER)){
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((res = getUserPhoneNumber()) != null){		
				if(client.updatePhoneNumber(res, user.getUsername(), user.getPassword())) {
					user.setPhoneNumber(res);		
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
		
		else if(res.equals(Messages.UI_UPDATE_BIRTHDAY)) {
			LocalDate date;
			printSeperation();
			System.out.println("\nTo Exit The Module Press "+ EXIT_KEY);
			if((date = getUserBirthDate()) != null){		
				if(client.updateBirthDate(date, user.getUsername(), user.getPassword())) {
					user.setDateOfBirth(date);		
					System.out.println("Update Succesfull");
				}
				else {
					System.out.println("The Server Declined The Request");
				}
				
			}
		}
		
		return user;

	}

	public void showScooterList(ArrayList<Scooter> scooterList, User user) {
		// TODO Auto-generated method stub
		printSeperation();
		System.out.println("Scooter List Menu\n");
		Integer dist;
		
		if(scooterList != null) {			
			for(Scooter scooter: scooterList) {
				if(!scooter.getReserved()) {					
					dist = Math.abs(scooter.getLocation().get(0) - user.getLocation().get(0));
					dist += Math.abs(scooter.getLocation().get(1) - user.getLocation().get(1));
					
					System.out.println("Scooter Info: "+"UID: "+scooter.getLocalUID()+" Distance: "+dist + " Location: "+scooter.getLocation().get(0)+" : "+scooter.getLocation().get(1));
				}
			}
		}
		else {
			System.out.println("No Scooter To Show");
		}
		
	}
	
	public void showScooterList(ArrayList<Scooter> scooterList) {
		// TODO Auto-generated method stub
		printSeperation();
		System.out.println("Scooter List Menu\n");
		
		if(scooterList != null) {			
			for(Scooter scooter: scooterList) {	
							
				System.out.println("Scooter Info: "+"UID: "+scooter.getLocalUID()+" Location: "+scooter.getLocation().get(0)+" : "+scooter.getLocation().get(1) + " Reserved: "+scooter.getReserved());
			}
		}
		else {
			System.out.println("No Scooter To Show");
		}
		
	}

	public User reserve(Client client, User user) {
		
		String res;
		Boolean boolVal;
		Integer uid;
		
		printSeperation();
		System.out.println("Reserve Scooter Menu\n");
		
		if(user.getHasPaymentInfo()) {	
			if(! user.getIsRiding()) {
				System.out.print("Enter The UID Of The Scooter: ");
				
				res = sc.nextLine();
				
				try {
					uid = Integer.parseInt(res);
					boolVal = client.reserve(uid, user.getUsername(), user.getPassword());
					if(boolVal) {
						user.setIsRiding(true);
						user.setScooterUID(uid);
						return user;
					}
					else {
						System.out.println("The Server Declined The Request");
						return user;
					}
				}catch (Exception e) {
					System.out.println("Wrong UID Number");
					return user;
				}
			}
			else {
				System.out.println("User Can Only Reserve 1 Scooter ");
				return user;
			}
			
		}
		else {
			System.out.println("Please First Add Card Information");
			return user;
		}
	}

	public User park(Client client, User user) throws IOException {
		
		User newUser;
		
		printSeperation();
		System.out.println("Park Scooter Menu\n");
		
		
		newUser = client.park(user);
		
		if(newUser != null) {
			user = newUser;
			System.out.println("Total Debt Is: "+user.getBalance());
			user.setIsRiding(false);
			user.setScooterUID(null);
			return user;
		}
		
		System.out.println("The Server Declined Request");
		return user;
	}

	public Admin showAdminLoginMenu() {
	
		
		Admin admin = new Admin();
		
		printSeperation();
		System.out.println("Admin Login Menu\n");
		System.out.print("Enter Username: ");
		admin.setUsername(sc.nextLine());
		
		
		System.out.print("Enter Password: ");
		admin.setPassword(sc.nextLine());
		
		;
		
		return admin;
	}

	public String showAdminMenu() {
		// TODO Auto-generated method stub
		
		String res;
		printSeperation();
		System.out.println("Admin Menu \n");
		
		System.out.println("Press 1 To List Scooter");
		System.out.println("Press 2 To Add Scooter");
		System.out.println("Press 3 To Remove Scooter");
		System.out.println("Press 4 To Update Information");
		System.out.print("Enter Your Choice: ");
		
		res = sc.nextLine();
		
		;
		return res;
		
		
		
	}

	public Scooter addScooterMenu() {
		// TODO Auto-generated method stub
		Scooter scooter = new Scooter();
		
		String res;
		Integer intVal;
		ArrayList<Integer> location = new ArrayList<>();
		printSeperation();
		System.out.println("Scooter Menu \n");
		System.out.print("Enter The UID Of The Scooter (Enter 0 For Auto Generated UID): ");
		
		res = sc.nextLine();
		
		try {
			intVal = Integer.parseInt(res);
			scooter.setLocalUID(intVal);
		}catch (Exception e) {
			System.out.println("UID Must Be A Number");
			;
			return null;
		}
		
		System.out.print("Enter The X Coordinate Of The Scooter: ");
		res = sc.nextLine();
		
		
		try {
			intVal = Integer.parseInt(res);
			location.add(intVal);
		}catch (Exception e) {
			System.out.println("X Coordinate Must Be A Number");
			;
			return null;
		}
		
		System.out.print("Enter The Y Coordinate Of The Scooter: ");
		res = sc.nextLine();
		
		
		try {
			intVal = Integer.parseInt(res);
			location.add(intVal);
		}catch (Exception e) {
			System.out.println("X Coordinate Must Be A Number");
			;
			return null;
		}
		
		scooter.setLocation(location);
		;
		return scooter;
		
	}

	public Integer removeScooterMenu() {
		// TODO Auto-generated method stub
		
		Integer intVal;
		
		printSeperation();
		System.out.println("Remove Scooter Menu\n");
		System.out.print("Enter The UID Of The Scooter: ");
		
		try {
			;
			intVal = Integer.parseInt(sc.nextLine());
			
			return intVal ;
		}catch(Exception e) {
			System.out.println("UID Must Be A Number");
			;
			return null;
		}
		
		
	}

	public Boolean showAdminUpdateMenu(AdminClient adminClient, Admin admin) throws IOException {
		// TODO Auto-generated method stub
		
		String res;
		
		printSeperation();
		System.out.println("Admin Update Menu\n");
		System.out.println("Press 1 To Update Name");
		System.out.println("Press 2 To Update Surname");
		System.out.println("Press 3 To Update Password");
		System.out.print("Enter Your Choice: ");
		
		res = sc.nextLine();
		
		
		if(res.equals(Messages.UI_UPDATE_NAME)) {
			System.out.print("Enter The New Name: ");
			res = sc.nextLine();
			
			
			if(adminClient.updateName(res, admin.getUsername(), admin.getPassword())) {
				admin.setName(res);
				System.out.println("Name Successfully Updated");
				
				;
				return true;
			}
			else {
				System.out.println("The Server Declined The Request");
			}
		}
		else if(res.equals(Messages.UI_UPDATE_SURNAME)) {
			System.out.print("Enter The New Surname: ");
			res = sc.nextLine();
			
			
			if(adminClient.updateName(res, admin.getUsername(), admin.getPassword())) {
				admin.setSurname(res);
				System.out.println("Surname Successfully Updated");

				;
				return true;
			}
			else {
				System.out.println("The Server Declined The Request");
			}
		}
		
		else if(res.equals(Messages.UI_UPDATE_PASSWORD)) {
			System.out.print("Enter The New Password: ");
			res = sc.nextLine();
			
			
			if(adminClient.updatePassword(res, admin.getUsername(), admin.getPassword())) {
				admin.setPassword(res);
				System.out.println("Password Successfully Updated");

				;
				return true;
			}
			else {
				System.out.println("The Server Declined The Request");
			}
		}
		
		
		;
		return false;
	}

	public User moveClient(User user) {
		// TODO Auto-generated method stub
		
		String res;
		ArrayList<Integer> location = new ArrayList<>();
		
		printSeperation();
		System.out.println("Movement Menu\n");
		
		try {			
			System.out.print("Enter The New Value Of X Coordinate: ");
			res = sc.nextLine();
			
			
			location.add(Integer.parseInt(res));
			System.out.print("Enter The New Value Of Y Coordinate: ");
			res = sc.nextLine();
			
			
			location.add(Integer.parseInt(res));
			
			user.setLocation(location);
			System.out.println("User Moved to X: "+ location.get(0) + " Y: "+location.get(1)) ;
			;
			return user;
			
		}catch (Exception e) {
			System.out.println("The Coordinate Values Must Be Number");
			;
			return user;
		}
		
		
	}


	public void showRewards(ArrayList<ArrayList<Integer>> rewards) {
		// TODO Auto-generated method stub
		Integer minX,maxX,minY,maxY;
		
		
		printSeperation();
		System.out.println("Reward Menu\n");
		System.out.println("The Rewards Are Calculated According to The Differeces Between Scooter Numbers");
		System.out.println("To Find the Wanted Reward Multiply The Difference With "+RewardSystem.REWARDS_PER_UNIT);
		
		//Write the rewards for every section
		for(int i=0;i<rewards.size();i++) {
			minX = i*RewardSystem.SECTION_SIZE;
			maxX = minX + RewardSystem.SECTION_SIZE;
			
			for(int j=0;j<rewards.size();j++) {
				
				minY = j * RewardSystem.SECTION_SIZE;
				maxY = minY + RewardSystem.SECTION_SIZE;
				System.out.println("Seciton Info X: "+minX+"-"+maxX+ " Y: "+minY+"-"+maxY+ " Number Of Scooters: "+rewards.get(i).get(j));
			}
		}
		
		
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
