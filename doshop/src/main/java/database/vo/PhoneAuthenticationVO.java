package database.vo;

import java.sql.Timestamp;

public class PhoneAuthenticationVO {
	private String phoneNumber;
	private String authenticationNumber;
	private Timestamp occurrenceTime;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAuthenticationNumber() {
		return authenticationNumber;
	}
	public void setAuthenticationNumber(String authenticationNumber) {
		this.authenticationNumber = authenticationNumber;
	}
	public Timestamp getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(Timestamp occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
}