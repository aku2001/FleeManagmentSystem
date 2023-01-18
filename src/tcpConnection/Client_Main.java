package tcpConnection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Server.Scooter;
import Server.ServerMain;

public class Client_Main {
	
	public static void main(String[] args) throws IOException {
		String strVal;
		User user = null;
		Boolean boolVal,mainMenu,moduleMenu;
		ArrayList<Scooter> scooterList;
		ArrayList<ArrayList<Integer>> rewards;
		Client client = new Client();
		UI ui = new UI();

		mainMenu = true;
		moduleMenu = false;
		//Main Menu: Login or Register
		
		while(true) {
			
			while(mainMenu) {
				strVal = ui.showMainMenu();

				if(strVal.equals(Messages.UI_LOGIN)) {
					//Login Menu
					user = ui.showLoginMenu();
					if(user != null) {						
						boolVal = client.authUser(user.getUsername(), user.getPassword());
						if(boolVal) {
							System.out.println("Successfully Logged In");
							user = client.getUser(user.getUsername(), user.getPassword());
							mainMenu = false;
							moduleMenu = true;
						}
						else {
							System.out.println("Login Failed");
						}
					}
					
				}
				else if(strVal.equals(Messages.UI_REGISTER)) {
					user = ui.registerUser();
					if(user != null) {						
						boolVal = client.registerUser(user);
						if(boolVal) {
							System.out.println("Successfully Registered");
							mainMenu = false;
							moduleMenu = true;
						}
						else {
							System.out.println("Registiration Failed");
						}
					}
					
				}
				else {
					System.out.println("Invaid Option");
				}
			}
			
			
			while(moduleMenu) {
				strVal = ui.showModules();
				
				if(strVal.equals(Messages.UI_UPDATE)) {
					user = ui.showUpdateMenu(client,user);
				}
				
				else if(strVal.equals(Messages.UI_GET_SCOOTERS)) {
					scooterList = client.getScooterlist(user.getUsername(), user.getPassword());	
					ui.showScooterList(scooterList,user);
//					ui.showScooterList(scooterList);
				}
				
				else if(strVal.equals(Messages.UI_RESERVE)) {
					user = ui.reserve(client,user);
				}
				
				else if(strVal.equals(Messages.UI_PARK)) {
					user = ui.park(client,user);
				}
				
				else if(strVal.equals(Messages.UI_GET_REWARDS)) {
					
					rewards = client.getRewards(user.getUsername(),user.getPassword());
					if(rewards != null) {						
						ui.showRewards(rewards);
					}
				}
				
				else if(strVal.equals(Messages.UI_MOVE)) {
					user = ui.moveClient(user);
				}
				
				else if(strVal.equals(Messages.UI_SHOW_USER)) {
					System.out.println(user);
				}
				
				else if(strVal.equals(Messages.UI_GO_BACK)) {
					mainMenu = true;
					moduleMenu = false;
				}
				
				
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
