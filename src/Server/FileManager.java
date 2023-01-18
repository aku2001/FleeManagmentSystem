package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Card.Card;
import tcpConnection.Admin;
import tcpConnection.User;

public class FileManager {
	
	private ArrayList<User> userList;
	private Admin admin;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	public static final String FILE_NAME = "userlist.ser";
	public static final String ADMIN_FILE_NAME = "admin.ser";
	
	
	public FileManager() {
		userList = new ArrayList<User>();
		
		
		//Load users into the list
		loadUserList();
		
		//Load Admin
		admin = new Admin();
		admin.setUsername("admin");
		admin.setPassword("admin");
		loadAdmin();
		
	}

	@SuppressWarnings("unchecked")
	public void loadUserList() {
		
		try {			
			FileInputStream fIn = new FileInputStream(FILE_NAME);
			ObjectInputStream readStream = new ObjectInputStream(fIn);
			
			userList = (ArrayList<User>) readStream.readObject();
			readStream.close();
			
		}catch(Exception e) {
			System.out.println("No File For Users");
		}
		
	}
	
	public void loadAdmin() {
		try {			
			FileInputStream fIn = new FileInputStream(ADMIN_FILE_NAME);
			ObjectInputStream readStream = new ObjectInputStream(fIn);
			
			admin = (Admin) readStream.readObject();
			readStream.close();
			
		}catch(Exception e) {
			saveAdmin();
			System.out.println("Admin Saved");
		}
	}
	
	public void saveAdmin() {
		FileOutputStream fOut;
		ObjectOutputStream writeStream;
		try {
					
			fOut = new FileOutputStream(ADMIN_FILE_NAME);
			writeStream = new ObjectOutputStream(fOut);
			writeStream.writeObject(admin);
			writeStream.flush();
			writeStream.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void saveUserList() {
		FileOutputStream fOut;
		ObjectOutputStream writeStream;
		try {
			if(userList != null) {				
				fOut = new FileOutputStream(FILE_NAME);
				writeStream = new ObjectOutputStream(fOut);
				writeStream.writeObject(userList);
				writeStream.flush();
				writeStream.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Boolean authUser(String username, String password) {
		
		lock.readLock().lock();
		for(User user: userList) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				lock.readLock().unlock();
				return true;
			}
		}
		
		lock.readLock().unlock();
		return false;
		
	}

	public Boolean register(String username, String password) {
		// TODO Auto-generated method stub
		lock.writeLock().lock();
		
		for(User user: userList) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				lock.writeLock().unlock();
				return false;
			}
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		userList.add(user);
		lock.writeLock().unlock();
		
		
		//Save it to the local storage
		saveUserList();
		
		return true;
	}
	

	public User getUser(String username,String password) {
		
		lock.readLock().lock();;
		for(User user: userList) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				lock.readLock().unlock();
				System.out.println("User Found");
				return user;
			}
		}
		lock.readLock().unlock();
		System.out.println("No User Found");
		return null;
	}
	
	public Boolean updateEmail(String newValue, String username, String password) {
		// TODO Auto-generated method stub
		
		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setEmail(newValue);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateName(String newValue, String username, String password) {
		// TODO Auto-generated method stub
		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setName(newValue);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateSurname(String newValue, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setSurname(newValue);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updatePhoneNumber(String newValue, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setPhoneNumber(newValue);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateUsername(String newValue, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setUsername(username);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updatePassword(String newValue, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setPassword(newValue);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateTotalRides(Integer intVal, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setTotalRides(intVal);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateIsRiding(Boolean boolVal, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setIsRiding(boolVal);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateHasPayment(Boolean boolVal, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setHasPaymentInfo(boolVal);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateBirthDay(LocalDate date, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setDateOfBirth(date);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean updateCard(Card card, String username, String password) {

		User user = getUser(username, password);
		if(user != null) {
			lock.writeLock().lock();
			user.setCard(card);
			lock.writeLock().unlock();
			
			//save user
			saveUserList();
			
			return true;
		}
		
		return false;
	}

	public Boolean authAdmin(String username, String password) {
		// TODO Auto-generated method stub
		if(admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
			return true;
		}
		
		return false;
	}

	public Boolean updateAdminName(String newValue, String username, String password) {
		// TODO Auto-generated method stub

		if(authAdmin(username, password)) {
			admin.setName(newValue);
			saveAdmin();
			return true;
		}
		
		return false;
	}
	
	public Boolean updateAdminSurname(String newValue, String username, String password) {
		// TODO Auto-generated method stub

		if(authAdmin(username, password)) {
			admin.setSurname(newValue);
			saveAdmin();
			return true;
		}
		
		return false;
	}
	public Boolean updateAdminPassword(String newValue, String username, String password) {
		// TODO Auto-generated method stub

		if(authAdmin(username, password)) {
			admin.setPassword(newValue);
			saveAdmin();
			return true;
		}
		
		return false;
	}



}
