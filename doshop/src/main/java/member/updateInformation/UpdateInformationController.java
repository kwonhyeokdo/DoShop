package member.updateInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

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
import member.AuthenticationLevel;
import member.MemberService;
import session.SigninSession;

@Controller
@RequestMapping("/Member/UpdateInformation")
public class UpdateInformationController {
	String viewPath = "/Member/UpdateInformation";
	
	@Autowired MemberService memberService;
	
	@PostMapping("/")
	public String postCheckMember(
		@RequestParam(value="pageAuthCode")String inputPageAuthCode,
 		RedirectAttributes redirectAttr,
 		Model model
	) {
		HashMap<String, String> authenticationSession = (HashMap<String, String>)SimpleContextUtil.getAttributeFromSession("authenticationSession");
		String pageAuthCode = authenticationSession.get(AuthenticationLevel.UPDATE_INFORMATION.name());
		if(inputPageAuthCode.equals(pageAuthCode)) {
			SigninSession signinSession = (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
			int memberNumber = signinSession.getMemberNumber();
			MemberVO memberVO = memberService.getMemberVO(memberNumber);
			model.addAttribute(memberVO);
			model.addAttribute("pageAuthCode", pageAuthCode);
			return viewPath + "/UpdateInformation";
		}else {
			redirectAttr.addFlashAttribute("errorMessage", "인증에 문제가 생겼습니다. 다시 시도해주세요");
			return "redirect:" + viewPath+ "/CheckPassword";
		}
	}
	
	@GetMapping("/CheckPassword")
	public String getCheckMember(
		RedirectAttributes redirectAttr
	) {
		return viewPath + "/CheckPassword";
	}
}