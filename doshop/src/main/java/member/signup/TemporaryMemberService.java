package member.signup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.TemporaryMemberDAO;
import database.vo.TemporaryMemberVO;
import etc.SimpleContextUtil;
import member.AuthenticationLevel;
import member.MemberService;

@Service
public class TemporaryMemberService {
	@Autowired
	private TemporaryMemberDAO temporaryMemberDAO;
	@Autowired
	private MemberService memberService;
		
	public boolean emailExists(String email) {
		List<TemporaryMemberVO> memberList =  temporaryMemberDAO.selectByEmailInExpiration(email);
		if(memberList.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean checkErrorTemporaryMemberVO(TemporaryMemberVO temporaryMemberVO) {
		if(!memberService.checkEmailReg(temporaryMemberVO.getEmail())){
			return false;
		}
		else if(!memberService.checkPasswordReg(temporaryMemberVO.getPassword())){
			return false;
		}
		else if(!memberService.checkNameReg(temporaryMemberVO.getName())){
			return false;
		}
		else if(!memberService.checkSexReg(temporaryMemberVO.getSex())){
			return false;
		}
		else if(!memberService.checkBirthdayReg(temporaryMemberVO.getBirthday())){
			return false;
		}
		else if(!memberService.checkPhoneNumberReg(temporaryMemberVO.getPhoneNumber())){
			return false;
		}
		else if(!memberService.checkPostcodeReg(temporaryMemberVO.getPostcode())){
			return false;
		}
		else if(!memberService.checkAddressReg(temporaryMemberVO.getAddress())){
			return false;
		}
		else if(!memberService.checkDetailAddressReg(temporaryMemberVO.getDetailAddress())){
			return false;
		}
		
		return true;
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
