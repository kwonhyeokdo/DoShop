package member.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber
	) {
	    phoneAuthenticationService.registPhoneAuthentication(phoneNumber, AuthenticationLevel.SIGNUP.name());
		return "" + PhoneAuthenticationService.AUTHENTICATION_TIME_SEC;
	}
	
	@PostMapping("/CheckAuthenticationNumber")
	public Boolean sendAuthenticationNumber(
		Model model,
		@RequestParam(value = "phone_number", required = false)String phoneNumber,
		@RequestParam(value = "authentication_number", required = false)String authenticationNumber
	) {
		return phoneAuthenticationService.checkAuthenticationNumber(phoneNumber, authenticationNumber, AuthenticationLevel.SIGNUP.name());
	}
	
	@PostMapping("/EmailExists")
	public boolean postEmailExists(
		@RequestParam(value="email", required=false)String email
	) {
		return (temporaryMemberService.emailExists(email) || memberService.emailExists(email));
	}
}
