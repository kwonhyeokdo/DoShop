package member.findPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import member.AuthenticationLevel;
import member.MailAuthenticationService;
import member.MemberService;

@Controller
@RequestMapping("/Member/FindPassword")
public class FindPasswordController {
	String viewPath = "/Member/FindPassword";
	
	@Autowired
	private FindPasswordService findPasswordService;
	@Autowired
	private MailAuthenticationService mailAuthenticationService;
	
	@GetMapping("/")
	public String getFindPassword(String errorMessage) {
		return viewPath + "/FindPasswordStep1";
	}
	
	@PostMapping("/")
	public String postFindPassword(
		@RequestParam(value="inputEmail")String inputEmail,
		RedirectAttributes redirectAttributes,
		Model model
	) {
		if(findPasswordService.matchEmail(inputEmail)) {
			model.addAttribute("inputEmail", inputEmail);
			return viewPath + "/FindPasswordStep2";
		}else {
			redirectAttributes.addFlashAttribute("errorMessage", "* 존재하지 않는 이메일입니다.");
			return "redirect:" + viewPath + "/";
		}
	}
	
	@PostMapping("/GoToChangePassword")
	public String postGoToChangePassword(
		@RequestParam(value="inputEmail")String inputEmail,
		Model model
	) {
		model.addAttribute("inputEmail", inputEmail);
		return viewPath + "/ChangePassword";
	}
}
