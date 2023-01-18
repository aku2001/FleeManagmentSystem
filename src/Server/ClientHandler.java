package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

import Card.Card;
import Card.ExpDate;
import tcpConnection.Messages;
import tcpConnection.User;

public class ClientHandler implements Runnable{
	
	//	Functions:
		// Receive Message and Decide on what to do
	
	//	Decisions:
		//	Authenticate
		//	Register (Save User)
		//	Send Rewards
		//	Send Scouter Places
		//	Reserve Scouter
		//	Park Scouter and Send the Trip cost
	
	private Socket socket;
	FileManager fileManager;
	ScooterManager scooterManager;
	RewardSystem rewardSystem;
	
	public ClientHandler(Socket socket, FileManager fileManager, ScooterManager scooterManager, RewardSystem rewardSystem) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.fileManager = fileManager;
		this.scooterManager = scooterManager;
		this.rewardSystem = rewardSystem;
	}
	
	
	public void run() {
		try {
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			
			
			Byte cmd,updateType,byteVal;
			Boolean res, boolVal;
			String username,password,msg;
			String[] splitMsg;
			Integer day,month,year,intVal;
			Double doubleVal;
			LocalDate date;
			Long longVal;
			Short shortVal;
			Card card = new Card();
			User user;
			ExpDate expDate = new ExpDate();
			ArrayList<Scooter> scooterList;

			boolean closeSocket = false;
			
			while (!closeSocket) {
				
				//Read the message and store it in byte array
				cmd = dataIn.readByte();
				if(cmd == Messages.CMD_AUTH) {
					//	Authenticate User by username and password
					//	Msg: username;password
					msg = dataIn.readUTF();
					splitMsg = msg.split(";");
					
					if(splitMsg.length == 2) {						
						username = splitMsg[0];
						password = splitMsg[1];
						
						if(fileManager.authUser(username,password)) {
							//Send success message
							dataOut.writeByte(Messages.CMD_SUCCESS);
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
					}
				}
				
				else if(cmd == Messages.CMD_REGISTER) {
					//	Register User
					//	Msg: username;password
					
					msg = dataIn.readUTF();
					splitMsg = msg.split(";");
					
					if(splitMsg.length == 2) {
						//	give username,passw
						if(fileManager.register(splitMsg[0],splitMsg[1])) {
							System.out.println("User Registered Successfully");
							dataOut.write(Messages.CMD_SUCCESS);
						}
						else {
							dataOut.write(Messages.CMD_FAIL);
						}
					}
					else {
						dataOut.write(Messages.CMD_FAIL);
					}
					
				}
			
				else if(cmd == Messages.CMD_UPDATE) {
					//	Update User's Values
					//	Msg: updateType msg: username;password;newVariable
					
					System.out.println("Update Request Received");
					updateType = dataIn.readByte();
					res = false;
					
					if(updateType == User.TYPE_NAME) {
						String str = dataIn.readUTF();
						splitMsg = str.split(";");
						System.out.println(str);
						
						if(splitMsg.length == 3) {		
							res = fileManager.updateName(splitMsg[0],splitMsg[1],splitMsg[2]);
						}
					}
					else if(updateType == User.TYPE_SURNAME) {					
						
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 3) {
							res = fileManager.updateSurname(splitMsg[0],splitMsg[1],splitMsg[2]);							
						}
					}
					else if(updateType == User.TYPE_USERNAME) {
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 3) {
							res = fileManager.updateUsername(splitMsg[0],splitMsg[1],splitMsg[2]);							
						}
					}
					else if(updateType == User.TYPE_PASSWORD) {					
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 3) {
							res = fileManager.updatePassword(splitMsg[0],splitMsg[1],splitMsg[2]);							
						}
					}
					else if(updateType == User.TYPE_CARD) {						
						try {
							card = new Card();
							card.setCardNumber(dataIn.readLong()); //number
							card.setCardCVV(dataIn.readShort()); //cvv
							
							expDate.setMonth(dataIn.readByte()); // month
							expDate.setYear(dataIn.readInt()); //year
							card.setCardExpDate(expDate); 
							
							splitMsg = dataIn.readUTF().split(";");
							if(splitMsg.length == 2) {								
								res = fileManager.updateCard(card,splitMsg[0],splitMsg[1]);
							}
							
						}catch (Exception e) {
							res = false;
							e.printStackTrace();
						}

					}
					else if(updateType == User.TYPE_BIRTHDATE) {
						day = dataIn.readInt();
						month = dataIn.readInt();
						year = dataIn.readInt();
						date = LocalDate.of(year, month, month);
						
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 2) {
							res = fileManager.updateBirthDay(date,splitMsg[0],splitMsg[1]);							
						}
					}
					else if(updateType == User.TYPE_HAS_PAYMENT_INFO) {						
						boolVal = dataIn.readBoolean();
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 2) {
							res = fileManager.updateHasPayment(boolVal,splitMsg[0],splitMsg[1]);							
						}
					}	
					else if(updateType == User.TYPE_IS_RIDING) {						
						boolVal = dataIn.readBoolean();
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 2) {
							res = fileManager.updateIsRiding(boolVal,splitMsg[0],splitMsg[1]);													
						}
					}
					else if(updateType == User.TYPE_TOTAL_RIDES) {
						intVal = dataIn.readInt();
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 2) {
							res = fileManager.updateTotalRides(intVal,splitMsg[0],splitMsg[1]);												
						}
					}
							
					else if(updateType == User.TYPE_PHONE_NUMBER) {
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 3) {
							res = fileManager.updatePhoneNumber(splitMsg[0],splitMsg[1],splitMsg[2]);							
						}
					}
					else if(updateType == User.TYPE_EMAIL) {
						splitMsg = dataIn.readUTF().split(";");
						if(splitMsg.length == 3) {
							res = fileManager.updateEmail(splitMsg[0],splitMsg[1],splitMsg[2]);												
						}
					}
					else {
						System.out.println("Wrong Update Type Has Given");
					}

						
					//Send back the information
					if(res) {
						dataOut.writeByte(Messages.CMD_SUCCESS);
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
					
				}
					
				
				
				else if(cmd == Messages.CMD_GET_USER) {
					
					// Sent Message: 
					// Success
					// total rides, has payment info, is riding, card, date of birth, 
					// Card: number(long),cvv(shor),month(byte),year(int)
					// Date of birth: day,month,year 
					// name;surname;phoneNumber;email
		
					splitMsg = dataIn.readUTF().split(";");
					user = fileManager.getUser(splitMsg[0],splitMsg[1]);
					System.out.println(user);
					
					if(user != null) {
						dataOut.writeByte(Messages.CMD_SUCCESS);
						dataOut.writeInt(user.getTotalRides());
						dataOut.writeBoolean(user.getHasPaymentInfo());
						dataOut.writeBoolean(user.getIsRiding());
						
						if(user.getIsRiding()) {
							dataOut.writeInt(user.getScooterUID());
						}
						
						//Card
						if(user.getHasPaymentInfo()) {							
							card = user.getCard();
							dataOut.writeLong(card.getCardNumber());
							dataOut.writeShort(card.getCardCVV());
							dataOut.writeByte(card.getCardExpDate().getMonth());
							dataOut.writeInt(card.getCardExpDate().getYear());
						}
						
						//BirthDate
						dataOut.writeInt(user.getDateOfBirth().getDayOfMonth());
						dataOut.writeInt(user.getDateOfBirth().getMonthValue());
						dataOut.writeInt(user.getDateOfBirth().getYear());
						
						//Others
						msg = user.getName()+";"+user.getSurname()+";"+user.getPhoneNumber()+";"+user.getEmail();
						dataOut.writeUTF(msg);						
					}
				}
				
				else if(cmd == Messages.CMD_GET_SCOOTERS) {
					//	Send Scouter PLaces
					//	Msg: Success,number of scooters, scooterInfo
					// Scooter info: uid,x location, y location, isReserved
					
					splitMsg = dataIn.readUTF().split(";");
					res = fileManager.authUser(splitMsg[0], splitMsg[1]);
					if(res) {
						scooterList = scooterManager.getScooterList();
						
						if(scooterList != null) {
							dataOut.writeByte(Messages.CMD_SUCCESS);
							
							dataOut.writeInt(scooterList.size());
							
							for(Scooter scooter: scooterList) {	
								dataOut.writeInt(scooter.getLocalUID());
								dataOut.writeInt(scooter.getLocation().get(0));
								dataOut.writeInt(scooter.getLocation().get(1));
								dataOut.writeBoolean(scooter.getReserved());
								
							}
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
						
					}
					
				}
				
				else if(cmd == Messages.CMD_RESERVE) {
					//	Reserve the scouter and attend a unique identifier
					//	Msg: UID, username;password
					intVal = dataIn.readInt(); //UID
					splitMsg = dataIn.readUTF().split(";"); //username, password
					res = fileManager.authUser(splitMsg[0], splitMsg[1]);
					if(res) {						
						res = scooterManager.reserve(intVal, splitMsg[0], splitMsg[1]);
						if(res) {
							user = fileManager.getUser(splitMsg[0], splitMsg[1]);
							user.setIsRiding(true);
							user.setScooterUID(intVal);
							
							fileManager.saveUserList();
							scooterManager.saveScooterList();
							dataOut.writeByte(Messages.CMD_SUCCESS);
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
				}
				
				else if(cmd == Messages.CMD_PARK) {
					//	Park the scouter and send back the price
					//	Msg: username;password;UID
					ArrayList<Integer> newLocation = new ArrayList<>() ;
					ArrayList<Integer> oldLocation = new ArrayList<>() ;
					Double reward;
					Scooter scooter;
					
					intVal = dataIn.readInt(); //UID
					
					// Location
					newLocation.add(dataIn.readInt()); 
					newLocation.add(dataIn.readInt());
					
					splitMsg = dataIn.readUTF().split(";"); //username, password
					
					
					res = fileManager.authUser(splitMsg[0], splitMsg[1]);
					System.out.println("Parking");
					
					if(res) {		
						try {
							
							scooter = scooterManager.getScooter(intVal);
							if(scooter != null) {
								oldLocation = scooter.getLocation();
								reward = rewardSystem.calculateReward(oldLocation,newLocation);
								
								doubleVal = scooterManager.park(intVal,newLocation, splitMsg[0], splitMsg[1]);
								if(doubleVal != null) {
									
									// Update User and Scooter Lists
									user = fileManager.getUser(splitMsg[0], splitMsg[1]);
									user.setIsRiding(false);
									user.setScooterUID(null);
									
									fileManager.saveUserList();
									scooterManager.saveScooterList();
									
									dataOut.writeByte(Messages.CMD_SUCCESS);
									dataOut.writeDouble(doubleVal);
									dataOut.writeDouble(reward);
								}
								else {
									System.out.println("Hello1");
									dataOut.writeByte(Messages.CMD_FAIL);
								}
							}
							else {
								System.out.println("The Scooter Doesn't Exist");
								dataOut.writeByte(Messages.CMD_FAIL);
							}
						}catch (Exception e) {
							e.printStackTrace();
							dataOut.writeByte(Messages.CMD_FAIL);
						}
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
					
				}
				
				else if(cmd == Messages.CMD_GET_REWARDS) {
					//	Send Reward Places
					//	Msg: username;password
					
					splitMsg = dataIn.readUTF().split(";");
					res = fileManager.authUser(splitMsg[0], splitMsg[1]);
					
					if(res) {
						dataOut.writeByte(Messages.CMD_SUCCESS);
						ArrayList<ArrayList<Integer>> rewards = rewardSystem.getSections();
						
						dataOut.writeInt(rewards.size());
						dataOut.writeInt(rewards.get(0).size());
						
						for(int i= 0; i<rewards.size();i++) {
							for(int j=0; j<rewards.get(0).size();j++) {
								dataOut.writeInt(rewards.get(i).get(j));
							}
						}
						
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
					
				}
				
				else if(cmd == Messages.ADMIN_AUTH) {
					splitMsg = dataIn.readUTF().split(";");
					res = fileManager.authAdmin(splitMsg[0], splitMsg[1]);
					
					if(res) {
						dataOut.writeByte(Messages.CMD_SUCCESS);
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
				}
				
				else if(cmd == Messages.ADMIN_GET_SCOOTERS) {
//					Send Scouter PLaces
					//	Msg: Success,number of scooters, scooterInfo
					// Scooter info: uid,x location, y location, isReserved
					
					splitMsg = dataIn.readUTF().split(";");
					res = fileManager.authAdmin(splitMsg[0], splitMsg[1]);
					if(res) {
						scooterList = scooterManager.getScooterList();
						
						if(scooterList != null) {
							dataOut.writeByte(Messages.CMD_SUCCESS);
							
							dataOut.writeInt(scooterList.size());
							
							for(Scooter scooter: scooterList) {	
								dataOut.writeInt(scooter.getLocalUID());
								dataOut.writeInt(scooter.getLocation().get(0));
								dataOut.writeInt(scooter.getLocation().get(1));
								dataOut.writeBoolean(scooter.getReserved());
								
							}
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
						
					}
				}
				
				else if(cmd == Messages.ADMIN_ADD_SCOOTER) {
					
					//MSG: type, scooter UID, scooter x, scooter y, username;password
					Scooter scooter = new Scooter();
					ArrayList<Integer> location = new ArrayList<>();
					
					scooter.setLocalUID(dataIn.readInt());
					location.add(dataIn.readInt());
					location.add(dataIn.readInt());
					
					scooter.setLocation(location);
					intVal = scooterManager.addScooter(scooter);
					
					splitMsg = dataIn.readUTF().split(";");
					if(splitMsg.length == 2 && fileManager.authAdmin(splitMsg[0], splitMsg[1])) {						
						if(intVal != null) {
							dataOut.writeByte(Messages.CMD_SUCCESS);
							dataOut.writeInt(intVal);
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
					

				}
				
				else if(cmd == Messages.ADMIN_REMOVE_SCOOTER) {
					
					//MSG: type, scooter UID, scooter x, scooter y, username;password

					intVal = dataIn.readInt();
					splitMsg = dataIn.readUTF().split(";");

					if(fileManager.authAdmin(splitMsg[0], splitMsg[1])) {	
						
						if(scooterManager.removeScooter(intVal)) {
							dataOut.writeByte(Messages.CMD_SUCCESS);
						}
						else {
							dataOut.writeByte(Messages.CMD_FAIL);
						}
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
				}
				
				else if(cmd == Messages.ADMIN_UPDATE) {
					//	Update User's Values
					//	Msg: updateType msg: newVariable;username;password
					
					updateType = dataIn.readByte();
					res = false;
					
					if(updateType == User.TYPE_NAME) {
						splitMsg = dataIn.readUTF().split(";");
						res = fileManager.updateAdminName(splitMsg[0],splitMsg[1],splitMsg[2]);
					}
					else if(updateType == User.TYPE_SURNAME) {					
						
						splitMsg = dataIn.readUTF().split(";");
						res = fileManager.updateAdminSurname(splitMsg[0],splitMsg[1],splitMsg[2]);
					}
					else if(updateType == User.TYPE_PASSWORD) {
						splitMsg = dataIn.readUTF().split(";");
						res = fileManager.updateAdminPassword(splitMsg[0],splitMsg[1],splitMsg[2]);
					}
					
					if(res) {
						dataOut.writeByte(Messages.CMD_SUCCESS);
					}
					else {
						dataOut.writeByte(Messages.CMD_FAIL);
					}
				}
				
				
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket For The Client Is Closed");
		}
	}
}
