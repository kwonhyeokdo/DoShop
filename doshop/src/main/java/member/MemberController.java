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

import database.vo.MemberVO;
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
	String viewPath = "/Member";

	@Autowired
	private SigninService signinService;
	@Autowired
	private TemporaryMemberService temporaryMemberService;
	@Autowired
	private MailAuthenticationService mailAuthenticationService;
	@Autowired
	private MemberService memberService;

	@GetMapping("/Signin")
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
	
	@PostMapping("/Signin")
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
			return "redirect:" + viewPath + "/Signin";
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
		return viewPath + "/Signup";
	}

	@GetMapping("/NoticeEmail")
	public String noticeEmail() {
		return viewPath + "/NoticeEmail";
	}

	@GetMapping("/Congratulations")
	public String congratulations() {
		return viewPath + "/Congratulations";
	}
	
	@PostMapping("/send_member_information")
	public String submitMemberInformation(RedirectAttributes redirectAttributes, TemporaryMemberVO temporaryMemberVO) {
		ArrayList<String> errorList = temporaryMemberService.registTemporaryMember(temporaryMemberVO);
		if(!errorList.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorList", errorList);
			redirectAttributes.addFlashAttribute("temporaryMemberVO", temporaryMemberVO);
			
			return "redirect:" + viewPath + "/signup";
		}else {
			mailAuthenticationService.registEmailAuthentication(temporaryMemberVO.getEmail());
			return "redirect:" + viewPath + "/NoticeEmail";
		}
		
	}
	
	@GetMapping("/authentication_mail")
	public String sendEmailAuthentication(@RequestParam(value = "token", required = false)String token) {
		try {
			boolean isTrueMail = mailAuthenticationService.checkAuthenticationToken(token);
			if(isTrueMail) {
				return "redirect:" + viewPath + "/Congratulations";
			}else {
				return "redirect:" + viewPath + "/Signup";
			}
		}catch(RuntimeException e) {
			System.out.println("mailAuthenticationService 오류");
			return "redirect:" + viewPath + "/Signup";
		}
	}
	
	@GetMapping("/FindPasswordStep")
	public String getFindPassword(String errorMessage) {
		return viewPath + "/FindPasswordStep1";
	}
	
	@PostMapping("/FindPasswordStep")
	public String postFindPassword(
		@RequestParam(value="inputEmail")String inputEmail,
		RedirectAttributes redirectAttributes
	) {
		if(memberService.matchEmail(inputEmail)) {
			return viewPath + "/FindPasswordStep2";
		}else {
			redirectAttributes.addFlashAttribute("errorMessage", "* 존재하지 않는 이메일입니다.");
			return "redirect:" + viewPath + "/FindPasswordStep";
		}
	}
}
