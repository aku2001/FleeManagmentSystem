package Card;

import java.io.Serializable;

public class ExpDate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte month;
	private int year;
	
	
//	Constructors
	public ExpDate(byte month, byte year) {
		super();
		this.month = month;
		this.year = year;
	}
	
	public ExpDate() {}
	


//	Getters and Setters
	public byte getMonth() {
		return month;
	}
	public void setMonth(byte month) throws IncorrectDateException {
		if(month < 12) {			
			this.month = month;
		}
		else {
			throw new IncorrectDateException();
		}
	}
	
	public void setMonth(String strMonth) throws IncorrectDateException {
		
		try {
			byte month = Byte.parseByte(strMonth);
			setMonth(month);
		}catch (Exception e) {
			throw new IncorrectDateException();
		}
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	public void setYear(String strYear) throws IncorrectDateException {
		
		try {
			int year = Integer.parseInt(strYear);
			setYear(year);
		}catch (Exception e) {
			throw new IncorrectDateException();
		}
		
	}
	
	
	
}
