package member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import etc.SimpleContextUtil;
import member.signin.SigninSession;
import member.signup.PhoneAuthenticationService;
import member.signup.TemporaryMemberService;

@RestController
@RequestMapping("/Member")
public class MemberRestController {
	@Autowired
	private PhoneAuthenticationService phoneAuthenticationService;
	@Autowired
	private TemporaryMemberService temporaryMemberService;
	@Autowired
	private MemberService memberService;

	
	@PostMapping("/send_phone_number")
	public String sendPhomeNumber(Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber
	) {
	    phoneAuthenticationService.registPhoneAuthentication(phoneNumber);
		return "" + PhoneAuthenticationService.AUTHENTICATION_TIME_SEC;
	}
	
	@PostMapping("/send_authentication_number")
	public Boolean sendAuthenticationNumber(Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber,
		@RequestParam(value = "authentication_number", required = false)String authenticationNumber
	) {
		return phoneAuthenticationService.checkAuthenticationNumber(phoneNumber, authenticationNumber);
	}
	
	@PostMapping("/send_email")
	public boolean sendEmail(
		@RequestParam(value = "email", required = false)String email
	) {
		return temporaryMemberService.checkDuplicateEmail(email) && memberService.checkDuplicateEmail(email);
	}
	
	@PostMapping("/GetSigninSession")
	public SigninSession postGetSigninSession(
	){
		return (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
	}
}
