package member.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import database.vo.MemberVO;
import etc.SimpleContextUtil;
import member.MemberService;
import session.SigninSession;

@Controller
@RequestMapping("/Member/Information")
public class InformationController {
	String viewPath = "/Member/Information";
	
	@Autowired MemberService memberService;
	
	@PostMapping("/")
	public String postCheckMember(
		@RequestParam(value="inputPassword")String inputPassword,
 		Model model,
 		RedirectAttributes redirectAttr
	) {
		String email = null;
		SigninSession signinSession = (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
		if(signinSession == null) {
			return "redirect:" + "/";
		}
		email = signinSession.getEmail();
		if(memberService.checkPassword(email, inputPassword)) {
			MemberVO memberVO = memberService.getMemberVO(signinSession.getMemberNumber());
			model.addAttribute("memberVO", memberVO);
			return viewPath + "/Information";
		}else {
			redirectAttr.addFlashAttribute("errorMessage", "일치하지 않는 비밀번호입니다.");
			return "redirect:" + viewPath+ "/CheckMember";
		}
	}
	
	@GetMapping("/CheckMember")
	public String getCheckMember(
		RedirectAttributes redirectAttr
	) {
		return viewPath + "/CheckMember";
	}
}