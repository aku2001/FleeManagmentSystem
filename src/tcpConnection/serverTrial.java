package tcpConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class serverTrial {
	

	public static void main(String[] args){
		
		try (ServerSocket serverSocket = new ServerSocket(2001)) {
			Socket socket;
			String res;
			Byte type;
			

			socket = serverSocket.accept();		
			System.out.println("One Client Accepted");
			
			DataInputStream dataIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
			
			while(true) {
				
				type = dataIn.readByte();
				res = dataIn.readUTF();
				
				System.out.println("Type: "+type+" msg: "+res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

		
	}
}
