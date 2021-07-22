package member;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts.chain.commands.servlet.CreateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.dao.TemporaryMemberDAO;
import database.vo.MemberVO;
import database.vo.TemporaryMemberVO;
import etc.SimpleCrypt;

@Service
public class MemberService {
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	TemporaryMemberDAO temporaryMemberDAO;
	
	public boolean checkEmailReg(String email) {
		Pattern pattern = Pattern.compile("(?i)^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{1,3}$");
		Matcher matcher = pattern.matcher(email);

		return matcher.find();
	}
	
	public boolean checkPasswordReg(String password) {
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
	
	public boolean checkNameReg(String name) {
		Pattern pattern = Pattern.compile("^[^0-9~!@#$%^&*()_+]*$");
		Matcher matcher = pattern.matcher(name);

		return matcher.find();
	}
	
	public boolean checkSexReg(String sex) {
		return (sex.equals("M") || sex.equals("F")) ? true : false;
	}
	
	public boolean checkBirthdayReg(String birthday) {
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
	
	public boolean checkPhoneNumberReg(String phoneNumber) {
		Pattern pattern = Pattern.compile("^[0-9]{10,11}$");
		Matcher matcher = pattern.matcher(phoneNumber);

		return matcher.find();
	}
	
	public boolean checkPostcodeReg(String postcode) {
		return (postcode.length() > 0) ? true : false;
	}
	
	public boolean checkAddressReg(String address) {
		return (address.length() > 0) ? true : false;
	}
	
	public boolean checkDetailAddressReg(String detailAddress) {
		return (detailAddress.length() > 0) ? true : false;
	}

	public void registMember(String email) {
		List<TemporaryMemberVO> temporaryMemberVOList = temporaryMemberDAO.selectByEmailInExpiration(email);
		if(temporaryMemberVOList.isEmpty()) {
			return;
		}
		TemporaryMemberVO temporaryMemberVO = temporaryMemberVOList.get(0);
		memberDAO.insert(temporaryMemberVO);
		temporaryMemberDAO.deleteByEmailInExpiration(temporaryMemberVO.getEmail());
	}
	
	public boolean emailExists(String email) {
		MemberVO member =  memberDAO.selectByEmail(email);
		if(member == null) {
			return false;
		}else {
			return true;
		}
	}

	public boolean isOldPassword(String inputEmail, String inputPassword) {
		MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
		if(memberVO == null) {
			return false;
		}
		
		return SimpleCrypt.bCryptMatch(inputPassword, memberVO.getPassword());
	}
	
	public boolean chagnePassword(String email, String password) {
		if(isOldPassword(email, password)) {
			return false;
		}
		MemberVO memberVO = memberDAO.selectByEmail(email);
		if(memberVO == null || !checkPasswordReg(password)) {
			return false;
		}
		
		return memberDAO.updatePasswordByEmail(email, SimpleCrypt.bCryptEncode(password));
	}
	
	public boolean checkPassword(String email, String password) {
		MemberVO memberVO = memberDAO.selectByEmail(email);
		if(memberVO == null) {
			return false;
		}
		
		return SimpleCrypt.bCryptMatch(password, memberVO.getPassword());
	}
	
	public MemberVO getMemberVO(int memberNumber) {
		List<MemberVO> memberVOList = memberDAO.selectByMemberNumber(memberNumber);
		return memberVOList.isEmpty() ? null : memberVOList.get(0);
	}
}
