package member.findPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import member.AuthenticationLevel;
import member.MailAuthenticationService;

@Service
public class FindPasswordService {
	@Autowired
	MemberDAO memberDAO;
	
	public boolean matchEmail(String inputEmail) {
		return (memberDAO.selectByEmail(inputEmail) == null) ? false : true;
	}
	
	public String getPhoneNumber(String inputEmail) {
		MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
		if(memberVO == null){
			return null;
		}else{
			return memberVO.getPhoneNumber();
		}
	}
	
	public String blindPhoneNumber(String phoneNumber) {
		char[] charPhoneNumber = phoneNumber.toCharArray();
		
		for(int i = 4; i <= 6; i++) {
			charPhoneNumber[i] = '*';
		}
		
		return String.valueOf(charPhoneNumber); 
	}
	
	public String crateDashToPhoneNumber(String phoneNumber) {
		String result = phoneNumber.substring(0, 3);
		result += '-';
		result += phoneNumber.substring(3, 7);
		result += '-';
		result += phoneNumber.substring(7);
		
		return result;
	}
	
	public String phoneNumberToNicePattern(String phoneNumber) {
		String result = blindPhoneNumber(phoneNumber);
		result = crateDashToPhoneNumber(result);
		
		return result;
	}
}
