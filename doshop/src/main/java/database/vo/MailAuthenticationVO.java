package database.vo;

import java.sql.Timestamp;

public class MailAuthenticationVO {
	private String email;
	private String token;
	private Timestamp occurrenceTime;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(Timestamp occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
	
}
