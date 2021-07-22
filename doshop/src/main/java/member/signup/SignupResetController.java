package member.signup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import etc.SimpleContextUtil;
import etc.SimpleUtil;
import member.AuthenticationLevel;
import member.MemberService;
import member.PhoneAuthenticationService;

@RestController
@RequestMapping("/Member/Signup")
public class SignupResetController {
	@Autowired
	PhoneAuthenticationService phoneAuthenticationService;
	@Autowired
	TemporaryMemberService temporaryMemberService;
	@Autowired
	MemberService memberService;
	
	@PostMapping("/GetAuthenticationNumber")
	public String postGetAuthenticationNumber(
		@RequestParam(value = "phone_number", required = false)String phoneNumber
	) {
	    phoneAuthenticationService.registPhoneAuthentication(phoneNumber, AuthenticationLevel.SIGNUP.name());
		return "" + PhoneAuthenticationService.AUTHENTICATION_TIME_SEC;
	}
	
	@PostMapping("/CheckAuthenticationNumber")
	public String sendAuthenticationNumber(
		HttpServletRequest request,
		Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber,
		@RequestParam(value = "authentication_number", required = false)String authenticationNumber
	) {
		String authenticationCode = "false";
		if(phoneAuthenticationService.checkAuthenticationNumber(phoneNumber, authenticationNumber, AuthenticationLevel.SIGNUP.name())) {
			authenticationCode = SimpleUtil.createAuthenticationCode();
		}
		HashMap<String, String> authSession = (HashMap<String, String>)SimpleContextUtil.getAttributeFromSession("authenticationSession");
		authSession.put(AuthenticationLevel.SIGNUP.name(), authenticationCode);
		SimpleContextUtil.setAttributeToSession("authenticationSession", authSession);
		
		return authenticationCode;
	}
	
	@PostMapping("/EmailExists")
	public boolean postEmailExists(
		@RequestParam(value="email", required=false)String email,
		HttpServletRequest request
	) {
		return (temporaryMemberService.emailExists(email) || memberService.emailExists(email));
	}
}
