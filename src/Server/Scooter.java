package Server;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;



public class Scooter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean reserved;
	private ArrayList<Integer> location;
	private ReentrantLock lock;
	public static AtomicInteger UID = new AtomicInteger(0);
	private Integer localUID;
	
	
	// Info about the user who reserved the scooter
	private String username;
	private String password;
	private LocalTime reservationTime;
	
	
	
	
	
	
	
	public Scooter() {
		lock = new ReentrantLock();
		reserved = false;
		location = new ArrayList<>();
	}
	
	
	public static int incrementUID() {
		return UID.incrementAndGet();
	}
	
	public static AtomicInteger getUID() {
		return UID;
	}

	public Boolean getReserved() {
		return reserved;
	}
	
	private void setReserved(boolean val) {
		this.reserved = val;
	}

	public Boolean reserve(String username, String password) {
		// Get lock and check if it is reserved
		System.out.println("Here2");
		if(this.lock.tryLock()){
			if(! getReserved()) {
				
				//Reserve the scooter in the name of the user and set a timer
				setReserved(true);
				this.username = username;
				this.password = password;
				reservationTime = LocalTime.now();
				
				this.lock.unlock();
				return true;
			}
		}
		
		this.lock.unlock();
		return false;
	}
	
	public LocalTime park(ArrayList<Integer> location, String username, String password) {
		
		if(reserved) {
			if(this.username.equals(username) && this.password.equals(password)) {
				setReserved(false);
				setLocation(location);
				return reservationTime;
			}
		}
		
		return null;
	}
	
	public ArrayList<Integer> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<Integer> location) {
		this.location = location;
	}

	public Integer getLocalUID() {
		return localUID;
	}
	
	public void setLocalUID(Integer localUID) {
		this.localUID = localUID;
	}


	public LocalTime getReservationTime() {
		return reservationTime;
	}


	public void setReservationTime(LocalTime reservationTime) {
		this.reservationTime = reservationTime;
	}


	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}
	
	
	
	
	
	
}
