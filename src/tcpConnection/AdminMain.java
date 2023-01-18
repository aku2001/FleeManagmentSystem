package tcpConnection;

import java.io.IOException;
import java.util.ArrayList;

import Server.Scooter;

public class AdminMain {
	
	
	
	public static void main(String[] args) throws IOException {
		Admin admin = null;
		AdminClient adminClient = new AdminClient();
		Boolean loginMenu,adminMenu,boolVal;
		String res;
		Integer intVal;
		ArrayList<Scooter> scooterList;
		UI ui = new UI();
		
		// Admin Menu
		
		loginMenu = true; adminMenu = false;
		
		while(true) {
			
			while(loginMenu) {			
				admin = ui.showAdminLoginMenu();
				if(adminClient.authAdmin(admin.getUsername(), admin.getPassword())) {
					loginMenu = false;
					adminMenu = true;
				}
				else {
					System.out.println("Authentication Failed");
				}
			}
			
			while(adminMenu) {
				res = ui.showAdminMenu();
				
				if(res.equals(Messages.UI_ADMIN_GET_SCOOTERS)) {
					scooterList = adminClient.getScooterList(admin.getUsername(), admin.getPassword());
					ui.showScooterList(scooterList);
				}
				
				else if(res.equals(Messages.UI_ADMIN_ADD_SCOOTER)) {
					Scooter scooter  = ui.addScooterMenu();
					intVal = adminClient.addScooter(scooter,admin.getUsername(),admin.getPassword());
					if( intVal != null) {
						System.out.println("Scooter Successfully Added With The UID "+intVal);
					}
					else {
						System.out.println("The Server Declined The Request");
						System.out.println("If You Aren't Sure That The UID Is Unique Choose The Automatic Generated Version");
					}
				}
				
				else if(res.equals(Messages.UI_ADMIN_REMOVE_SCOOTER)) {
					
					intVal = ui.removeScooterMenu();
					if(adminClient.removeScooter(intVal, admin.getUsername(), admin.getPassword())) {
						System.out.println("Scooter Successfully Removed");
					}
					else {
						System.out.println("The Server Declined The Request");
					}
				}
				
				else if(res.equals(Messages.UI_ADMIN_UPDATE)) {
					ui.showAdminUpdateMenu(adminClient, admin);
				}
			}
		}
	}
	
	
}
