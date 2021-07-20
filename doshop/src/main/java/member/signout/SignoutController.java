package member.signout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import member.signin.SigninService;

@Controller
@RequestMapping("/Member/Signout")
public class SignoutController {
	String viewPath = "/Member/Signout";
	
	@Autowired
	private SigninService signinService;
	
	@PostMapping("/")
	public String signout(
		@RequestParam(value="email", required=false)String email,
		@RequestParam(value="token", required=false)String token
	) {
		signinService.signout();
		return "redirect:" + "/";
	}
}
