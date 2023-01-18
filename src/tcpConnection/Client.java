package tcpConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

import Card.Card;
import Card.ExpDate;
import Card.IncorrectCardNumberException;
import Server.Scooter;
import Server.ServerMain;


public class Client {
	
	private Socket socket;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	
	public Client() {
		
		boolean connected = false;
		
		while(! connected) {			
			try {
				socket = new Socket("127.0.0.1",ServerMain.SERVER_PORT);
				dataOut = new DataOutputStream(socket.getOutputStream());
				dataIn = new DataInputStream(socket.getInputStream());
				connected = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Conenction Failed, Trying Again");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
	}
	
	
	public Boolean authUser(String username, String password) throws IOException {
		Byte res;
		String msg = username+";"+password;
		dataOut.writeByte(Messages.CMD_AUTH);
		dataOut.writeUTF(msg);
		res = dataIn.readByte();
		
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		
		return false;
	}
	
	public User getUser(String username, String password ) throws IOException {
		User user = new User();
		String msg = username+";"+password;
		String[] splitMsg;
		Byte res;
		Integer year,month,day;
		LocalDate date;
		Card card = new Card();
		ExpDate expDate = new ExpDate();
		
		//Msg: type,username,password
		dataOut.write(Messages.CMD_GET_USER);
		dataOut.writeUTF(msg);
		
		//	Received Message: 
		// total rides, has payment info, is riding, card, date of birth, 
		// Card: number(long),cvv(shor),month(byte),year(int)
		// Date of birth: day,month,year 
		// name;surname;phoneNumber;email
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			user.setUsername(username);
			user.setPassword(password);
			user.setTotalRides(dataIn.readInt());
			user.setHasPaymentInfo(dataIn.readBoolean());
			user.setIsRiding(dataIn.readBoolean());
			
			if(user.getIsRiding()) {
				user.setScooterUID(dataIn.readInt());
			}
			
			// Card Info
			if(user.getHasPaymentInfo()) {
				try {
					card.setCardNumber(dataIn.readLong());
					card.setCardCVV(dataIn.readShort());
					expDate.setMonth(dataIn.readByte());
					expDate.setYear(dataIn.readInt());
					card.setCardExpDate(expDate);
					
					user.setCard(card);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//Birth Date
			day = dataIn.readInt();
			month = dataIn.readInt();
			year = dataIn.readInt();
			date = LocalDate.of(year, month, day);
			user.setDateOfBirth(date);
			
			//Other String Messages: name;surname;phoneNumber;email
			msg = dataIn.readUTF();
			splitMsg = msg.split(";");
			
			if(splitMsg.length == 4) {
				user.setName(splitMsg[0]);
				user.setSurname(splitMsg[1]);
				user.setPhoneNumber(splitMsg[2]);
				user.setEmail(splitMsg[3]);
				return user;
			}
			else {
				return null;
			}
		}
		
		return null;
	}
	
	public Boolean registerUser(User user) throws IOException {
		Byte res;
		String msg = user.getUsername() + ";"+ user.getPassword();
		dataOut.writeByte(Messages.CMD_REGISTER);
		dataOut.writeUTF(msg);
		res = dataIn.readByte();
		
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Adding The Variables");
			updateName(user.getName(),user.getUsername(), user.getPassword());
			updateSurname(user.getSurname(),user.getUsername(), user.getPassword());
			updatePhoneNumber(user.getPhoneNumber(),user.getUsername(), user.getPassword());
			updateEmail(user.getEmail(),user.getUsername(), user.getPassword());
			updateTotalRides(user.getTotalRides(),user.getUsername(), user.getPassword());
			updateHasPaymentInfo(user.getHasPaymentInfo(),user.getUsername(), user.getPassword());
			updateIsRiding(user.getIsRiding(), user.getUsername(), user.getPassword());
			updateBirthDate(user.getDateOfBirth(),user.getUsername(), user.getPassword());
			
			return true;
		}
		
		return false;
		
	}
	
	
//	SPECIFIC UPDATE TYPES
	
	public Boolean updateUsername(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_USERNAME);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		return false;
	}
	
	public Boolean updateName(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_NAME);
		
		msg = newValue + ";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Name Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Name");
		return false;
	}
	
	public Boolean updateSurname(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_SURNAME);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Surname Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Surname");
		return false;
	}
	
	public Boolean updatePassword(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_PASSWORD);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Password Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Password");
		return false;
	}
	
	public Boolean updatePhoneNumber(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_PHONE_NUMBER);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("PhoneNumber Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Password");
		return false;
	}
	
	public Boolean updateEmail(String newValue, String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_EMAIL);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Email Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Email");
		return false;
	}
	
	public Boolean updateTotalRides(Integer newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_TOTAL_RIDES);
		dataOut.writeInt(newValue);
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("TotalRides Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Email");
		return false;
	}
	
	public Boolean updateHasPaymentInfo(Boolean newValue, String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_HAS_PAYMENT_INFO);
		dataOut.writeBoolean(newValue);
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Has Payment Info Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Has Payment Info");
		return false;
	}
	
	public Boolean updateIsRiding(Boolean newValue, String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_IS_RIDING);
		dataOut.writeBoolean(newValue);
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Is Riding Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Is Riding");
		return false;
	}
	
	public Boolean updateCard(Card newCard, String username,String password) throws IOException {
		if(newCard == null) {
			return false;
		}
		
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_CARD);

//		Message: number,cvv,month,year
		ExpDate expDate = newCard.getCardExpDate();		
		dataOut.writeLong(newCard.getCardNumber());
		dataOut.writeShort(newCard.getCardCVV());
		dataOut.writeByte(expDate.getMonth());
		dataOut.writeInt(expDate.getYear());
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("Card Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update Card");
		return false;
	}
	
	public Boolean updateBirthDate(LocalDate newDate, String username,String password) throws IOException {
		
		if(newDate == null) {
			return false;
		}
		
		Byte res;
		String msg;
		dataOut.writeByte(Messages.CMD_UPDATE);
		dataOut.writeByte(User.TYPE_BIRTHDATE);
		
//		Msg: Day,Month,Year
		dataOut.writeInt(newDate.getDayOfMonth());
		dataOut.writeInt(newDate.getMonthValue());
		dataOut.writeInt(newDate.getYear());
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
		
		
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			System.out.println("BirthDate Successfully Updated");
			return true;
		}
		System.out.println("Failed To Update BirthDate");
		return false;
	}
	
	// Get ScooterList
	public ArrayList<Scooter> getScooterlist(String username,String password) throws IOException{
		//	Msg: Success,number of scooters, scooterInfo
		// Scooter info: uid,x location, y location, isReserved
		String msg;
		Integer numberOfScooters;
		ArrayList<Scooter> scooterList = new ArrayList<Scooter>();		
		ArrayList<Integer> location;

		
		Scooter scooter;
		
		dataOut.writeByte(Messages.CMD_GET_SCOOTERS);
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
		
		if(dataIn.readByte() == Messages.CMD_SUCCESS) {
			numberOfScooters = dataIn.readInt();
			
			for(int i=0;i<numberOfScooters;i++) {
				location = new ArrayList<Integer>();
				scooter = new Scooter();
				
				//Get the info
				scooter.setLocalUID(dataIn.readInt());
				location.add(dataIn.readInt());
				location.add(dataIn.readInt());
				scooter.setLocation(location);
				scooter.setReserved(dataIn.readBoolean());
				
				scooterList.add(scooter);
			}
			
			return scooterList;
		}
		return null;
	}
	
	// Reserve Scooter
	public Boolean reserve(Integer UID, String username, String password) throws IOException {
		String msg;
		
		dataOut.writeByte(Messages.CMD_RESERVE);
		dataOut.writeInt(UID);
		msg = username + ";" + password;
		dataOut.writeUTF(msg);
		
		System.out.println("Message Sent For Reserve");
		
		if(dataIn.readByte() == Messages.CMD_SUCCESS) {
			System.out.println("Scooter Is Successfully Reserved");
			return true;
		}
		System.out.println("Failed To Reserve");
		return false;
	}
	
	// Park Scooter
	
	
	public User park(User user) throws IOException {
		String msg;
		Double price,reward;
		msg = user.getUsername() + ";" + user.getPassword();
		System.out.println("Park Message Is Sending");
		if(user.getScooterUID() != null) {			
			dataOut.writeByte(Messages.CMD_PARK);
			dataOut.writeInt(user.getScooterUID());
			dataOut.writeInt(user.getLocation().get(0));
			dataOut.writeInt(user.getLocation().get(1));
			
			dataOut.writeUTF(msg);
			
			
			if(dataIn.readByte() == Messages.CMD_SUCCESS) {
				price = dataIn.readDouble();
				reward = dataIn.readDouble();
				
				System.out.println("Total Cost Of The Trip: "+price);
				System.out.println("Reward Received: "+reward);
				
				user.setBalance(user.getBalance() - price);
				user.setRewards(user.getRewards() + reward);
				return user;
				
			}
		}
		
		return null;
	}


	public ArrayList<ArrayList<Integer>> getRewards(String username, String password) throws IOException {
		ArrayList<ArrayList<Integer>> rewards = new ArrayList<>();
		ArrayList<Integer> sectionReward;
		String msg;
		boolean boolVal;
		Integer x,y;
		msg = username+ ";" + password;
		
		dataOut.writeByte(Messages.CMD_GET_REWARDS);
		dataOut.writeUTF(msg);
		
		if(dataIn.readByte() == Messages.CMD_SUCCESS) {
			x = dataIn.readInt();
			y = dataIn.readInt();
			
			for(int i=0;i<x;i++) {
				sectionReward = new ArrayList<>();
				for(int j=0;j<y;j++) {
					sectionReward.add(dataIn.readInt());
				}
				
				rewards.add(sectionReward);
			}
			
			System.out.println("Rewards Are Acquired");
			return rewards;
		}
		else {
			System.out.println("Ther Server Declined The Request");
			return null;
		}
		
		
	}
	
	//
	
	
	
	
	
	
	
	
	
	
}
