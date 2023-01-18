package tcpConnection;



public class Messages {
	
	//	Message Types 
	public static final Byte CMD_UPDATE = 0;
	public static final Byte CMD_AUTH = 1;
	public static final Byte CMD_REGISTER = 2;
	public static final Byte CMD_GET_REWARDS = 3;
	public static final Byte CMD_GET_SCOOTERS = 4;
	public static final Byte CMD_RESERVE = 5;
	public static final Byte CMD_PARK = 6;
	public static final Byte CMD_GET_USER = 7;
	
	//Message Responses
	public static final Byte CMD_SUCCESS = 8;
	public static final Byte CMD_FAIL = 9;
	
	//Admin Messages
	public static final Byte ADMIN_ADD_SCOOTER = 10;
	public static final Byte ADMIN_REMOVE_SCOOTER= 11;
	public static final Byte ADMIN_UPDATE = 12;
	public static final Byte ADMIN_AUTH = 13;
	public static final Byte ADMIN_GET_SCOOTERS = 14;
	
	
	
//	User Options. Number to function
	public static final String UI_LOGIN = "1";
	public static final String UI_REGISTER = "2";
	
	public static final String UI_UPDATE = "1";
	public static final String UI_GET_SCOOTERS = "2";
	public static final String UI_RESERVE = "3";
	public static final String UI_PARK = "4";
	public static final String UI_GET_REWARDS = "5";
	public static final String UI_MOVE = "6";
	public static final String UI_SHOW_USER = "7";
	public static final String UI_GO_BACK= "8";

	
	// Update Options For UI
	public static final String UI_UPDATE_NAME = "1";
	public static final String UI_UPDATE_SURNAME = "2";
	public static final String UI_UPDATE_PASSWORD = "3";
	public static final String UI_UPDATE_CARD = "4";
	public static final String UI_UPDATE_EMAIL = "5";
	public static final String UI_UPDATE_PHONE_NUMBER = "6";
	public static final String UI_UPDATE_BIRTHDAY = "7";
	
	// UI Options For Admin Functionality
	public static final String UI_ADMIN_GET_SCOOTERS = "1";
	public static final String UI_ADMIN_ADD_SCOOTER = "2";
	public static final String UI_ADMIN_REMOVE_SCOOTER = "3";
	public static final String UI_ADMIN_UPDATE= "4";
	
	

	
	
}
 