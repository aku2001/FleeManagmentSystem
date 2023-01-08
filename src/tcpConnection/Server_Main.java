package tcpConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Main {
	
	private static final Integer SERVER_PORT = 2001;
	
	
	public static void main(String[] args) throws IOException {
		

		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		Socket socket;
		
		while(true){

			socket = serverSocket.accept();
			System.out.println("1 New Client Has Connected");
		}
		
	
	}
}
