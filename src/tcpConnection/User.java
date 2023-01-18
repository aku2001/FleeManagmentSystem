package tcpConnection;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import Card.Card;

public class User implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//	About User General Info
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	private Integer totalRides;
	private String phoneNumber;
	private String email;
	private String username;
	private ArrayList<Integer> location;
	private Integer scooterUID;
	private Double rewards;
	private Double balance;
	
	//Will be updated to more secure version
	private String password;
	
	
//	About Payment
	Card card;
	
//	For the implementation of reservation
	private Boolean isRiding;
	private Boolean hasPaymentInfo;
	
//	Variable Order Type
	public static Byte TYPE_NAME = 0;
	public static Byte TYPE_SURNAME = 1;
	public static Byte TYPE_PHONE_NUMBER = 2;
	public static Byte TYPE_BIRTHDATE = 3;
	public static Byte TYPE_EMAIL = 4;
	public static Byte TYPE_USERNAME = 5;
	public static Byte TYPE_PASSWORD = 6;
	public static Byte TYPE_TOTAL_RIDES = 7;
	public static Byte TYPE_CARD = 8;
	public static Byte TYPE_IS_RIDING = 9;
	public static Byte TYPE_HAS_PAYMENT_INFO = 10;
	
	
	
	
	
// Constructor	
	public User(String name, String surname, LocalDate dateOfBirth, String phoneNumber, String email, String username,
			String password) {
		
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		
		this.hasPaymentInfo = false;
		this.isRiding = false;
		this.totalRides = 0;
		this.location = new ArrayList<>();
		this.location.add(0);location.add(0);
		
		this.rewards = 0.;
		this.balance = 0.;
	}
	
	public User() {
		this.hasPaymentInfo = false;
		this.isRiding = false;
		this.totalRides = 0;
		this.rewards = 0.;
		this.balance = 0.;
		
		this.location = new ArrayList<>();
		this.location.add(0);this.location.add(0);
	}



//	Getters and Setters
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Integer getTotalRides() {
		return totalRides;
	}
	public void setTotalRides(Integer totalRides) {
		this.totalRides = totalRides;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
		this.hasPaymentInfo = true;
	}

	public Boolean getIsRiding() {
		return isRiding;
	}
	public void setIsRiding(Boolean isRiding) {
		this.isRiding = isRiding;
	}
	public Boolean getHasPaymentInfo() {
		return hasPaymentInfo;
	}
	public void setHasPaymentInfo(Boolean hasPaymentInfo) {
		this.hasPaymentInfo = hasPaymentInfo;
	}

	public ArrayList<Integer> getLocation() {
		return location;
	}

	public void setLocation(ArrayList<Integer> location) {
		this.location = location;
	}

	public Integer getScooterUID() {
		return scooterUID;
	}

	public void setScooterUID(Integer scooterUID) {
		this.scooterUID = scooterUID;
	}

	public Double getRewards() {
		return rewards;
	}

	public void setRewards(Double rewards) {
		this.rewards = rewards;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", dateOfBirth=" + dateOfBirth + ", totalRides="
				+ totalRides + ", phoneNumber=" + phoneNumber + ", email=" + email + ", username=" + username
				+ ", location=" + location + ", scooterUID=" + scooterUID + ", rewards=" + rewards + ", balance="
				+ balance + ", password=" + password + ", card=" + card + ", isRiding=" + isRiding + ", hasPaymentInfo="
				+ hasPaymentInfo + "]";
	}

	
	
	
	
	
	
	
}
