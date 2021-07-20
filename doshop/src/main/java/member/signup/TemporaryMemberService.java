package member.signup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.TemporaryMemberDAO;
import database.vo.TemporaryMemberVO;

@Service
public class TemporaryMemberService {
	@Autowired
	private TemporaryMemberDAO temporaryMemberDAO;
	
	private boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile("(?i)^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{1,3}$");
		Matcher matcher = pattern.matcher(email);

		return matcher.find();
	}
	
	public boolean emailExists(String email) {
		List<TemporaryMemberVO> memberList =  temporaryMemberDAO.selectByEmailInExpiration(email);
		if(memberList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean checkPassword(String password) {
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z~!@#$%^&*()_+]{8,16}$");
		Matcher matcher = pattern.matcher(password);
		int level = 0;

		if(!matcher.find()) {
			return false;
		}
		
		pattern = Pattern.compile("[0-9]");
		matcher = pattern.matcher(password);
		if(matcher.find()) {level++;}
        
		pattern = Pattern.compile("[a-z]");
		matcher = pattern.matcher(password);
		if(matcher.find()) {level++;}
        
		pattern = Pattern.compile("[A-Z]");
		matcher = pattern.matcher(password);
		if(matcher.find()) {level++;}
        
		pattern = Pattern.compile("[~!@#$%^&*()_+]");
		matcher = pattern.matcher(password);
		if(matcher.find()) {level++;}
        
		return level < 3 ? false : true;
	}
	
	private boolean checkName(String name) {
		Pattern pattern = Pattern.compile("^[^0-9~!@#$%^&*()_+]*$");
		Matcher matcher = pattern.matcher(name);

		return matcher.find();
	}
	
	private boolean checkSex(String sex) {
		return (sex.equals("M") || sex.equals("F")) ? true : false;
	}
	
	private boolean checkBirthday(String birthday) {
        final int MIN_YEAR = 1900;
        final int MAX_YEAR = 2021;

		Pattern pattern = Pattern.compile("^[0-9]{8}$");
		Matcher matcher = pattern.matcher(birthday);

        if(!matcher.find()) {
			return false;
		}
        
		int year = Integer.parseInt(birthday.substring(0, 4));
		int month = Integer.parseInt(birthday.substring(4, 6));
		int day = Integer.parseInt(birthday.substring(6, 8));
		
        if((year >= MIN_YEAR && year <= MAX_YEAR) && 
           (month >= 1 && month <= 12) &&
           (day >= 1 && month <= 31)){
    		return  true;
        }else{
        	return  false;
        }
	}
	
	private boolean checkPhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile("^[0-9]{10,11}$");
		Matcher matcher = pattern.matcher(phoneNumber);

		return matcher.find();
	}
	
	private boolean checkPostcode(String postcode) {
		return (postcode.length() > 0) ? true : false;
	}
	
	private boolean checkAddress(String address) {
		return (address.length() > 0) ? true : false;
	}
	
	private boolean checkDetailAddress(String detailAddress) {
		return (detailAddress.length() > 0) ? true : false;
	}
	
	public ArrayList<String> checkErrorTemporaryMemberVO(TemporaryMemberVO temporaryMemberVO) {
		ArrayList<String> errorList = new ArrayList<>();
		if(!checkEmail(temporaryMemberVO.getEmail())){
			errorList.add("email");
		}
		if(!checkPassword(temporaryMemberVO.getPassword())){
			errorList.add("password");
		}
		if(!checkName(temporaryMemberVO.getName())){
			errorList.add("name");
		}
		if(!checkSex(temporaryMemberVO.getSex())){
			errorList.add("sex");
		}
		if(!checkBirthday(temporaryMemberVO.getBirthday())){
			errorList.add("birthday");
		}
		if(!checkPhoneNumber(temporaryMemberVO.getPhoneNumber())){
			errorList.add("phoneNumber");
		}
		if(!checkPostcode(temporaryMemberVO.getPostcode())){
			errorList.add("postcode");
		}
		if(!checkAddress(temporaryMemberVO.getAddress())){
			errorList.add("address");
		}
		if(!checkDetailAddress(temporaryMemberVO.getDetailAddress())){
			errorList.add("detailAddress");
		}
		
		return errorList;
	}
	
	public void registTemporaryMember(TemporaryMemberVO temporaryMemberVO) {
		temporaryMemberDAO.insert(temporaryMemberVO);
	}
	
	public void printTemporaryMemberVOList(List<TemporaryMemberVO> temporaryMemberVOList) {
		if(temporaryMemberVOList == null) {
			return;
		}
		if(temporaryMemberVOList.isEmpty()) {
			return;
		}
		
		int number = 0;
		for(TemporaryMemberVO temporaryMemberVO : temporaryMemberVOList) {
			number++;
			System.out.println("[" + number + "번]");
			temporaryMemberVO.print();
			System.out.println();
		}
	}
}
