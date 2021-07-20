package member.findPassword;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import member.AuthenticationLevel;
import member.MailAuthenticationService;
import member.PhoneAuthenticationService;

@RestController
@RequestMapping("/Member/FindPassword")
public class FindPasswordRestController {
	@Autowired
	private PhoneAuthenticationService phoneAuthenticationService;
	@Autowired
	private FindPasswordService findPasswordService;
	@Autowired
	private MailAuthenticationService mailAuthenticationService;
	
	@PostMapping("/GetPhoneNumber")
	public String postGetPhoneNumber(
		@RequestParam(value="inputEmail")String inputEmail
	) {
		String phoneNumber = findPasswordService.phoneNumberToNicePattern(findPasswordService.getPhoneNumber(inputEmail));
		return phoneNumber;
	}
	
	@PostMapping("/GetPhoneAuthorityNumber")
	public String postGetPhoneAuthorityNumber(
		@RequestParam(value="inputEmail")String inputEmail
	) {
		String phoneNumber = findPasswordService.getPhoneNumber(inputEmail);
		phoneAuthenticationService.registPhoneAuthentication(phoneNumber, AuthenticationLevel.FIND_PASSWORD.name());
		return "" + PhoneAuthenticationService.AUTHENTICATION_TIME_SEC;
	}
	
	@PostMapping("/CheckAuthorityNumber")
	public boolean postCheckAuthorityNumber(
		@RequestParam(value="inputEmail")String inputEmail,
		@RequestParam(value="authorityNumber")String authorityNumber
	) {
		String phoneNumber = findPasswordService.getPhoneNumber(inputEmail);
		
		return phoneAuthenticationService.checkAuthenticationNumber(phoneNumber, authorityNumber, AuthenticationLevel.FIND_PASSWORD.name());
	}
	
	@PostMapping("/CreateAuthenticationEmail")
	public void postCreateAuthenticationEmail(
		@RequestParam(value="inputEmail")String inputEmail
	) {
		mailAuthenticationService.registAuthentication(inputEmail, AuthenticationLevel.FIND_PASSWORD.name());
	}
	
	@PostMapping("/CheckAuthorityEmailCode")
	public boolean postCheckAuthorityEmailCode(
		@RequestParam(value="inputEmail")String inputEmail,
		@RequestParam(value="authenticationCode")String authenticationCode
	) {
		return mailAuthenticationService.checkAuthenticationToken(inputEmail, authenticationCode, AuthenticationLevel.FIND_PASSWORD.name());
	}
}
