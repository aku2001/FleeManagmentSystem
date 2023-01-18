package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerMain {
	
	public static final Integer SERVER_PORT = 2001;
	
	
	public static void main(String[] args) throws IOException {
		
		Socket socket;
		ClientHandler clientHandler;
		FileManager fileManager = new FileManager();
		Lock lockOut = new ReentrantLock();
		Condition condition = lockOut.newCondition();
		
		ScooterManager scooterManager = new ScooterManager(condition,lockOut);
		RewardSystem rewardSystem = new RewardSystem(scooterManager,condition,lockOut);
		
		Thread t1;
		t1 = new Thread(rewardSystem);
		t1.start();
	
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
			
			while(true){

				//	Accept new connection		
				socket = serverSocket.accept();
				System.out.println("1 New Client Has Connected");
				
				//	Start the thread for client handler
				clientHandler = new ClientHandler(socket, fileManager, scooterManager,rewardSystem);
				t1 = new Thread(clientHandler);
				t1.start();
			
			}
		}
		
	
	}
}
