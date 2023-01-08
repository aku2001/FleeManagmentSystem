package tcpConnection;

import java.time.LocalDate;
import Card.Card;

public class User {

//	About User General Info
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	private Integer totalRides;
	private String phoneNumber;
	private String email;
	private String username;
	
	//Will be updated to more secure version
	private String password;
	
	
//	About Payment
	Card card;
	
//	For the implementation of reservation
	private Boolean isRiding;
	private Boolean hasPaymentInfo;
	
	
	
	
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
	}
	
	public User() {}



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
	
	
	
	
}
