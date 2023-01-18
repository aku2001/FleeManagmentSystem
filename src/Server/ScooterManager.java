package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tcpConnection.User;

public class ScooterManager {
	
	private ArrayList<Scooter> scooterList;
	private ReadWriteLock lock;
	private static final Double PRICE_PER_MIN = 0.15;
	public static final String FILE_NAME = "scooterList.ser";
	private Condition condition;
	private Lock lockOut;

	
	public ScooterManager(Condition condition, Lock lockOut) {
		this.condition = condition;
		this.lockOut = lockOut;
		this.lock = new ReentrantReadWriteLock();
		scooterList = new ArrayList<Scooter>();
		
		//load scooters into the list
		loadScooterList();
	}
	
	public ScooterManager() {
		this.condition = new ReentrantLock().newCondition();
		this.lock = new ReentrantReadWriteLock();
		scooterList = new ArrayList<Scooter>();
		
		//load scooters into the list
		loadScooterList();
	}
	
	@SuppressWarnings("unchecked")
	public void loadScooterList() {
		Integer max = 0;
		
		try {			
			FileInputStream fIn = new FileInputStream(FILE_NAME);
			ObjectInputStream readStream = new ObjectInputStream(fIn);
			
			scooterList = (ArrayList<Scooter>) readStream.readObject();
			readStream.close();
			
			for(Scooter scooter: scooterList) {
				if(scooter.getLocalUID() > max){
					max = scooter.getLocalUID();
				}
			}
			
			if(Scooter.UID.get() <= max) {
				Scooter.UID.set(max+1);
			}
			
			
		}catch(Exception e) {
			System.out.println("No File For Scooters");
		}
	}
	
	public void saveScooterList() {
		FileOutputStream fOut;
		ObjectOutputStream writeStream;
		try {
			if(scooterList != null) {				
				fOut = new FileOutputStream(FILE_NAME);
				writeStream = new ObjectOutputStream(fOut);
				writeStream.writeObject(scooterList);
				writeStream.flush();
				writeStream.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Integer addScooter(Scooter newScooter) {
		
		//Get the reader lock and check if scooter is already added
		
		if(newScooter.getLocalUID().equals(0)) {
			//Take the write lock and add the scooter

			newScooter.setLocalUID(Scooter.incrementUID());

		}
		
		
		lock.writeLock().lock();
		for(Scooter scooter : scooterList) {
			if(scooter.getLocalUID().equals(newScooter.getLocalUID())){
				lock.writeLock().unlock();
				return null;
			}
		}		
		
		//Take the write lock and add the scooter
		scooterList.add(newScooter);
		lock.writeLock().unlock();
		
		//save scooter list
		saveScooterList();
		
		lockOut.lock();
		condition.signalAll();
		lockOut.unlock();
		
		return newScooter.getLocalUID();
		
		
	}
	
	public Boolean removeScooter(Integer localUID) {
		
		//Get the reader lock and check if scooter exists
		
		int i = 0;
		boolean found = false;
		
		lock.readLock().lock();
		for(Scooter scooter : scooterList) {
			if(scooter.getLocalUID().equals(localUID)){
				found = true;
				break;
			}
			i++;
		}
		lock.readLock().unlock();
		
		if(found) {
			//Take the write lock and remove the scooter
			
			lock.writeLock().lock();
			if(scooterList.get(i).reserve("scooterManager", "scooterManager")) {				
				scooterList.remove(i);
				lock.writeLock().unlock();
				
				
				lockOut.lock();
				condition.signalAll();
				lockOut.unlock();
				
				//save scooter list
				saveScooterList();
				return true;
			}
			lock.writeLock().unlock();
		}
		else {
			System.out.println("Scooter can't be found");
		}
		
		return false;
	}
	
	public Scooter getScooter(Integer UID) {
		
		lock.readLock().lock();
		for(Scooter scooter: scooterList) {
			
			System.out.println(scooter.getLocalUID() + " : " + UID);
			if(scooter.getLocalUID().equals(UID)) {
				lock.readLock().unlock();
				System.out.println("Found the Scooter");
				return scooter;
			}
		}
		
		lock.readLock().unlock();
		System.out.println("Can't Found The Scooter");
		return null;
	}
	
	public Boolean reserve(Integer UID, String username, String password) {
		// TODO Auto-generated method stub
		
		Scooter scooter = getScooter(UID);
		if(scooter != null) {	
			
			Boolean boolVal = scooter.reserve(username, password);
			
			lockOut.lock();
			condition.signalAll();
			lockOut.unlock();
			return boolVal;
		}
		else {
			return false;
		}
		
		
	}

	public Double park(Integer UID, ArrayList<Integer> location, String username, String password) {
		LocalTime currentDate = LocalTime.now();
		LocalTime reservationDate;
		Scooter scooter = getScooter(UID);
		Duration duration;
		Long minutes;
		Double price;
		
		
		
		if(scooter != null) {			
			reservationDate = scooter.park(location,username, password);
//			duration = Duration.between(currentDate,reservationDate);
			System.out.println(reservationDate);
			System.out.println(currentDate);
			minutes = ChronoUnit.MINUTES.between(reservationDate,currentDate);
			System.out.println(minutes);
//			duration = (double) reservationDate.until(currentDate, ChronoUnit.MINUTE1S);
			
			price = minutes * ScooterManager.PRICE_PER_MIN;
			
			lockOut.lock();
			condition.signalAll();
			lockOut.unlock();
			
			return price;
		}
		else {
			System.out.println("The Scooter Couldn't Be Found");
			return null;
		}
		
	}

	public ArrayList<Scooter> getScooterList() {
		// TODO Auto-generated method stub
		return scooterList;
	}
	
	
	
	
	
}
