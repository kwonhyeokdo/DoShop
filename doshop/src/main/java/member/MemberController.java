package member;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import database.vo.TemporaryMemberVO;
import etc.aes.AES256;
import member.signin.SigninService;
import member.signin.SigninSession;
import member.signup.MailAuthenticationService;
import member.signup.PhoneAuthenticationService;
import member.signup.TemporaryMemberService;

@Controller
@RequestMapping("/Member")
public class MemberController {
	String path = "/Member";

	@Autowired
	private SigninService signinService;
	@Autowired
	private TemporaryMemberService temporaryMemberService;
	@Autowired
	private MailAuthenticationService mailAuthenticationService;
	@Autowired
	HttpSession httpSession;
	@Autowired
	AES256 aes256;

	@GetMapping("/Signin")
	public String getSignin(
		@CookieValue(value="rememberEmail", required=false) Cookie rememberEmailCookie,
		@CookieValue(value="autoSignin", required=false) Cookie autoSignin,
		Model model
	) {
		if(rememberEmailCookie != null) {
			try {
			String email = rememberEmailCookie.getValue();
			model.addAttribute("rememberEmail", aes256.decrypt(email));
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
		return path + "/Signin";
	}
	
	@PostMapping("/Signin")
	public String postSignin(
		@RequestParam(value="inputEmail", required=false)String inputEmail,
		@RequestParam(value="inputPassword", required=false)String inputPassword,
		@RequestParam(value="checkRememberEmail", required=false)String checkRememberEmail,
		@RequestParam(value="checkAutoSignin", required=false)String checkAutoSignin,
		Model model
	) {
		if(signinService.signin(inputEmail, inputPassword)) {
			if(Boolean.parseBoolean(checkRememberEmail)){
				signinService.registRememberEmailCookie(inputEmail);
			}else {
				signinService.deleteRememberEmailCookie();
			}

			if(Boolean.parseBoolean(checkAutoSignin)) {
				signinService.useAutoSigninCookie(inputEmail, inputPassword);
			}else {
				signinService.notUseAutoSigninCookie();
			}
			return "redirect:/";
		}else {
			String errorMessage = "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다";
			model.addAttribute("errorMessage", errorMessage);
			return path + "/Signin";
		}
	}
	
	@PostMapping("/Signout")
	public String signout(
		@RequestParam(value="email", required=false)String email,
		@RequestParam(value="token", required=false)String token
	) {
		signinService.signout();
		return "redirect:/";
	}
	
	@GetMapping("/Signup")
	public String signup(@ModelAttribute TemporaryMemberVO temporaryMemberVO) {
		return path + "/Signup";
	}

	@GetMapping("/NoticeEmail")
	public String noticeEmail() {
		return path + "/NoticeEmail";
	}

	@GetMapping("/Congratulations")
	public String congratulations() {
		return path + "/Congratulations";
	}
	
	@PostMapping("/send_member_information")
	public String submitMemberInformation(RedirectAttributes redirectAttributes, TemporaryMemberVO temporaryMemberVO) {
		ArrayList<String> errorList = temporaryMemberService.registTemporaryMember(temporaryMemberVO);
		if(!errorList.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorList", errorList);
			redirectAttributes.addFlashAttribute("temporaryMemberVO", temporaryMemberVO);
			
			return "redirect:" + path + "/signup";
		}else {
			mailAuthenticationService.registEmailAuthentication(temporaryMemberVO.getEmail());
			return "redirect:" + path + "/NoticeEmail";
		}
		
	}
	
	@GetMapping("/authentication_mail")
	public String sendEmailAuthentication(@RequestParam(value = "token", required = false)String token) {
		try {
			boolean isTrueMail = mailAuthenticationService.checkAuthenticationToken(token);
			if(isTrueMail) {
				return "redirect:" + path + "/Congratulations";
			}else {
				return "redirect:" + path + "/Signup";
			}
		}catch(RuntimeException e) {
			System.out.println("mailAuthenticationService 오류");
			return "redirect:" + path + "/Signup";
		}
	}	
	
	@ResponseBody
	@PostMapping("/Hi")
	public String awef() {
		System.out.println("asdf");
		return "assdfa";
	}	
}
