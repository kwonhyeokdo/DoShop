package member.signup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import database.vo.TemporaryMemberVO;
import member.AuthenticationLevel;
import member.MailAuthenticationService;
import member.MemberService;

@Controller
@RequestMapping("/Member/Signup")
public class SignupController {
	String viewPath = "/Member/Signup";
	@Autowired
	TemporaryMemberService temporaryMemberService;
	@Autowired
	MailAuthenticationService mailAuthenticationService;
	@Autowired
	MemberService memberService;
	
	@GetMapping("/")
	public String getSignup(
		@ModelAttribute TemporaryMemberVO temporaryMemberVO,
		ArrayList<String> errorList
	) {
		return viewPath + "/Signup";
	}

	@GetMapping("/NoticeEmail")
	public String getNoticeEmail() {
		return viewPath + "/NoticeEmail";
	}

	@GetMapping("/Congratulations")
	public String getCongratulations() {
		return viewPath + "/Congratulations";
	}
	
	@PostMapping("/RegistTemporaryMember")
	public String postRegistTemporaryMember(
		RedirectAttributes redirectAttributes,
		TemporaryMemberVO temporaryMemberVO)
	{
		ArrayList<String> errorList = temporaryMemberService.checkErrorTemporaryMemberVO(temporaryMemberVO);
		if(!errorList.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorList", errorList);
			redirectAttributes.addFlashAttribute("temporaryMemberVO", temporaryMemberVO);
			return "redirect:" + viewPath + "/";
		}else {
			temporaryMemberService.registTemporaryMember(temporaryMemberVO);
			mailAuthenticationService.registAuthentication(temporaryMemberVO.getEmail(), AuthenticationLevel.SIGNUP.name());
			return "redirect:" + viewPath + "/NoticeEmail";
		}
	}
	
	@GetMapping("/AuthenticationMail")
	@Transactional
	public String sendEmailAuthentication(
		@RequestParam(value = "email", required = false)String inputEmail,
		@RequestParam(value = "token", required = false)String token
	) {
		if(mailAuthenticationService.checkAuthenticationToken(inputEmail, token, AuthenticationLevel.SIGNUP.name())) {
			memberService.registMember(inputEmail);
			return "redirect:" + viewPath + "/Congratulations";
		}else {
			return "redirect:" + "/Expiration";
		}
	}
}