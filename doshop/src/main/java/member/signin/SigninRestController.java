package member.signin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import etc.SimpleContextUtil;
import session.SigninSession;

@RestController
@RequestMapping("/Member/Signin")
public class SigninRestController {
	@PostMapping("/GetSigninSession")
	public SigninSession postGetSigninSession(
	){
		return (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
	}
}
