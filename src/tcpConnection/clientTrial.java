package tcpConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class clientTrial {
	
	public static void main(String[] args) throws IOException {
		
		Socket socket;
		String res;
		Byte type;
		Boolean stop = false;
		Scanner sc = new Scanner(System.in);
	
		socket = new Socket("127.0.0.1", 2001);	
		System.out.println("One Client Accepted");
		
		DataInputStream dataIn = new DataInputStream(socket.getInputStream());
		DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
		
		while(!stop) {
			
			System.out.print("Enter Message: ");
			
			type = 0;
			res = sc.next(); 
			
			if(res != "E") {				
				dataOut.writeByte(type);
				dataOut.writeUTF(res);
				
				System.out.println("\nMessage Is Sent ");
			}
			else {
				stop = true;
			}
		}
		
		socket.close();
		
			

		
	}

}
