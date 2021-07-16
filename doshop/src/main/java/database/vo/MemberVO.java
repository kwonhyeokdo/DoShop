package database.vo;

import java.sql.Timestamp;

public class MemberVO {
	private int member_number;
	private String email;
	private String password;
	private String name;
	private String sex;
	private String birthday;
	private String phoneNumber;
	private String postcode;
	private String address;
	private String detailAddress;
	private String extraAddress;
	private int authority;
	private Timestamp registrationTime;
	
	public MemberVO() {
		registrationTime = new Timestamp(System.currentTimeMillis());
	}
	
	public int getMemberNumber() {
		return member_number;
	}
	public void setMemberNumber(int member_number) {
		this.member_number = member_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String crypt_password) {
		this.password = crypt_password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phone_number) {
		this.phoneNumber = phone_number;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getExtraAddress() {
		return extraAddress;
	}
	public void setExtraAddress(String extraAddress) {
		this.extraAddress = extraAddress;
	}
	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public Timestamp getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Timestamp registrationTime) {
		this.registrationTime = registrationTime;
	}
}
