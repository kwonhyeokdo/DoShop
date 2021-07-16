package database.vo;

public class AdminManagementVO {
	private String email;
	private int authority;
	private int ranking;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public void print() {
		System.out.println("email: " + email);
		System.out.println("authority: " + authority);
		System.out.println("ranking: " + ranking);
	}
}
