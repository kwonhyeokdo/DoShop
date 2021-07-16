package database.vo;

import java.sql.Timestamp;

public class TemporaryMemberVO {
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
	private Timestamp registrationTime;
	
	public TemporaryMemberVO() {
		registrationTime = new Timestamp(System.currentTimeMillis());
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
	public void setPassword(String password) {
		this.password = password;
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
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public Timestamp getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Timestamp registrationTime) {
		this.registrationTime = registrationTime;
	}
	
	public void print() {
		System.out.println("email: " + email);
		System.out.println("password: " + password);
		System.out.println("name: " + name);
		System.out.println("sex: " + sex);
		System.out.println("birthday: " + birthday);
		System.out.println("phoneNumber: " + phoneNumber);
		System.out.println("postcode: " + postcode);
		System.out.println("address: " + address);
		System.out.println("detailAddress: " + detailAddress);
		System.out.println("extraAddress: " + extraAddress);
		System.out.println("registration: " + registrationTime);
	}
}
