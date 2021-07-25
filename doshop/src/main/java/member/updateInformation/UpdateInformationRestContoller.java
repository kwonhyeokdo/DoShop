package member.updateInformation;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import database.vo.MemberVO;
import etc.SimpleContextUtil;
import etc.SimpleUtil;
import member.AuthenticationLevel;
import member.MemberService;
import member.PhoneAuthenticationService;
import session.SigninSession;

@RestController
@RequestMapping("/Member/UpdateInformation")
public class UpdateInformationRestContoller {
	@Autowired
	MemberService memberService;
	@Autowired
	PhoneAuthenticationService phoneAuthenticationService;
	
	@PostMapping("/CheckPassword")
	public String postCheckPassword(
		@RequestParam(value="inputPassword", required = false)String inputPassword
	) {
		SigninSession signinSession = (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
		if(signinSession == null) {
			return "false";
		}
		String email = signinSession.getEmail();
		if(memberService.checkPassword(email, inputPassword)) {
			HashMap<String, String> authSession = (HashMap<String, String>)SimpleContextUtil.getAttributeFromSession("authenticationSession");
			String pageAuthCode = SimpleUtil.createAuthenticationCode();
			authSession.put(AuthenticationLevel.UPDATE_INFORMATION.name(), pageAuthCode);
			return pageAuthCode;
		}else {
			return "false";
		}
	}
	
	@PostMapping("/IsOldPassword")
	public boolean postIsOldPassword(
		@RequestParam(value="password")String inputPassword
	) {
		SigninSession signinSession = (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
		String email = signinSession.getEmail();
		return memberService.isOldPassword(email, inputPassword);
	}
	
	@PostMapping("/UpdateMember")
	public boolean putUpdateMember(
		MemberVO memberVO
	){
		return memberService.updateMemberVO(memberVO);
	}
	
	@PostMapping("/GetAuthenticationNumber")
	public String postGetAuthenticationNumber(
		@RequestParam(value = "phone_number", required = false)String phoneNumber
	) {
	    phoneAuthenticationService.registPhoneAuthentication(phoneNumber, AuthenticationLevel.UPDATE_INFORMATION.name());
		return "" + PhoneAuthenticationService.AUTHENTICATION_TIME_SEC;
	}
	
	@PostMapping("/CheckAuthenticationNumber")
	public boolean sendAuthenticationNumber(
		HttpServletRequest request,
		Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber,
		@RequestParam(value = "authentication_number", required = false)String authenticationNumber
	) {
		return phoneAuthenticationService.checkAuthenticationNumber(phoneNumber, authenticationNumber, AuthenticationLevel.UPDATE_INFORMATION.name());
	}
}
