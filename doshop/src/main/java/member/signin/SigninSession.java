package member.signin;

import database.vo.MemberVO;

public class SigninSession {
	private int memberNumber;
	private String email;
	private String name;
	private int authority;
	
	public SigninSession(MemberVO memberVO) {
		this.memberNumber = memberVO.getMemberNumber();
		this.email = memberVO.getEmail();
		this.name = memberVO.getName();
		this.authority = memberVO.getAuthority();
	}
	
	public SigninSession(int memberNumber, String email, String name, int authority) {
		this.memberNumber = memberNumber;
		this.email = email;
		this.name = name;
		this.authority = authority;
	}
	
	public void updateSigninSession(MemberVO memberVO) {
		this.memberNumber = memberVO.getMemberNumber();
		this.email = memberVO.getEmail();
		this.name = memberVO.getName();
		this.authority = memberVO.getAuthority();
	}
	
	public int getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
}
