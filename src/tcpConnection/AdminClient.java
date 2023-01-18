package tcpConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.ArrayList;

import Server.Scooter;
import Server.ServerMain;

public class AdminClient {
	
	
	private Socket socket;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	
	public AdminClient () {
		
		boolean connected = false;
		while(! connected) {			
			try {
				socket = new Socket("127.0.0.1",ServerMain.SERVER_PORT);
				dataOut = new DataOutputStream(socket.getOutputStream());
				dataIn = new DataInputStream(socket.getInputStream());
				connected = true;
				
			} catch (Exception e) {
				System.out.println("Connection Failed Trying Again");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public Boolean authAdmin(String username, String password) throws IOException {
		Byte res;
		String msg = username+";"+password;
		dataOut.writeByte(Messages.ADMIN_AUTH);
		dataOut.writeUTF(msg);
		res = dataIn.readByte();
		
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		
		return false;
	}
	
	// Get ScooterList
		public ArrayList<Scooter> getScooterList(String username,String password) throws IOException{
			//	Msg: Success,number of scooters, scooterInfo
			// Scooter info: uid,x location, y location, isReserved
			String msg;
			Integer numberOfScooters;
			ArrayList<Scooter> scooterList = new ArrayList<Scooter>();		
			ArrayList<Integer> location;

			
			Scooter scooter;
			
			dataOut.writeByte(Messages.ADMIN_GET_SCOOTERS );
			msg = username +";"+ password;
			dataOut.writeUTF(msg);
			
			if(dataIn.readByte() == Messages.CMD_SUCCESS) {
				numberOfScooters = dataIn.readInt();
				
				for(int i=0;i<numberOfScooters;i++) {
					location = new ArrayList<Integer>();
					location.add(0);location.add(0);
					scooter = new Scooter();
					
					//Get the info
					scooter.setLocalUID(dataIn.readInt());
					location.set(0, dataIn.readInt());
					location.set(1, dataIn.readInt());
					scooter.setLocation(location);
					scooter.setReserved(dataIn.readBoolean());
					
					scooterList.add(scooter);
				}
				
				return scooterList;
			}
			return null;
		}
	
	public Integer addScooter(Scooter scooter,String username, String password) throws IOException {
		String msg;
		
		//MSG: type, scooter UID, scooter x, scooter y, username;password
		dataOut.writeByte(Messages.ADMIN_ADD_SCOOTER);
		dataOut.writeInt(scooter.getLocalUID());
		dataOut.writeInt(scooter.getLocation().get(0));
		dataOut.writeInt(scooter.getLocation().get(1));
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
		
		if(dataIn.readByte() == Messages.CMD_SUCCESS) {
			return dataIn.readInt();
		}
		
		return null;
	}
	
	public Boolean removeScooter(Integer UID, String username,String password) throws IOException {
		String msg;
		
		//MSG: type, scooter UID, scooter x, scooter y
		dataOut.writeByte(Messages.ADMIN_REMOVE_SCOOTER);
		dataOut.writeInt(UID);
		
		msg = username +";"+ password;
		dataOut.writeUTF(msg);
		
		
		if(dataIn.readByte() == Messages.CMD_SUCCESS) {
			return true;
		}
		
		return false;
	}
	
	public Boolean updateName(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.ADMIN_UPDATE);
		dataOut.writeByte(User.TYPE_NAME);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		return false;
	}
	
	public Boolean updateSurname(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.ADMIN_UPDATE);
		dataOut.writeByte(User.TYPE_SURNAME);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		return false;
	}
	
	public Boolean updatePassword(String newValue,String username,String password) throws IOException {
		Byte res;
		String msg;
		dataOut.writeByte(Messages.ADMIN_UPDATE);
		dataOut.writeByte(User.TYPE_PASSWORD);
		
		msg = newValue +";"+ username +";"+ password;
		dataOut.writeUTF(msg);
			
		
		res = dataIn.readByte();
		if(res == Messages.CMD_SUCCESS) {
			return true;
		}
		return false;
	}
	

}
