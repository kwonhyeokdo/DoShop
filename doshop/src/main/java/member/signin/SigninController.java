package member.signin;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import etc.aes.AES256;

@Controller
@RequestMapping("/Member/Signin")
public class SigninController {
	String viewPath = "/Member/Signin";
	
	@Autowired
	private SigninService signinService;
	
	@GetMapping("/")
	public String getSignin(
		@CookieValue(value="rememberEmail", required=false) Cookie rememberEmailCookie,
		@CookieValue(value="autoSignin", required=false) Cookie autoSignin,
		String errorMessage,
		Model model
	) {
		if(rememberEmailCookie != null) {
			try {
			String email = rememberEmailCookie.getValue();
			model.addAttribute("rememberEmail", AES256.decrypt(email));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(autoSignin != null) {
			try {
			model.addAttribute("autoSignin", autoSignin.getValue());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return viewPath + "/Signin";
	}
	
	@PostMapping("/")
	public String postSignin(
			@RequestParam(value="inputEmail", required=false)String inputEmail,
			@RequestParam(value="inputPassword", required=false)String inputPassword,
			@RequestParam(value="checkRememberEmail", required=false)String checkRememberEmail,
			@RequestParam(value="checkAutoSignin", required=false)String checkAutoSignin,
			RedirectAttributes redirectAttributes
		) {
			if(signinService.signin(inputEmail, inputPassword)) {
				if(Boolean.parseBoolean(checkRememberEmail)){
					signinService.useRememberEmailCookie(inputEmail);
				}else {
					signinService.DoNotUseRememberEmailCookie();
				}

				if(Boolean.parseBoolean(checkAutoSignin)) {
					signinService.useAutoSigninCookie(inputEmail, inputPassword);
				}else {
					signinService.DoNotUseAutoSigninCookie();
				}
				return "redirect:/";
			}else {
				String errorMessage = "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다";
				redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
				return "redirect:" + viewPath + "/";
			}
		}
}
