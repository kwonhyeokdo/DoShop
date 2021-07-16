package admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import database.vo.AdminManagementVO;
import database.vo.CategoryVO;
import member.signin.SigninService;
import member.signin.SigninSession;

@Controller
@RequestMapping("/Admin")
public class AdminController {
	String viewPath = "/Admin";
	@Autowired
	AdminService adminService;
	@Autowired
	HttpSession session;
	
	@GetMapping("/")
	public String getAdminHome(HttpSession httpSession) {
		return viewPath + "/Home";
	}

	@GetMapping("/Signin")
	public String getAdminSignin() {
		return viewPath + "/Signin";
	}
	
	@PostMapping("/Signin")
	public String postAdminSignin(
		@RequestParam(value="inputEmail", required=false)String inputEmail,
		@RequestParam(value="inputPassword", required=false)String inputPassword,
		Model model,
		RedirectAttributes redirectAttributes
	){
		if(adminService.signin(inputEmail, inputPassword)) {
			return "redirect:" + viewPath + "/";
		}else {
			String errorMessage = "관리자에 가입하지 않은 아이디이거나, 잘못된 비밀번호입니다";
			model.addAttribute("errorMessage", errorMessage);
			return viewPath + "/Signin";
		}
	}
	
	@GetMapping("/Signout")
	public String getAdminSignout() {
		adminService.signout();
		return "redirect:/";
	}
	
	@GetMapping("/AdminManagement/")
	public String getAdminManagement(
		@RequestParam(value = "requestPage", defaultValue = "1")int requestPage,
		@RequestParam(value = "requestSearch", defaultValue = "")String requestSearch,
		@RequestParam(value = "requestTag", defaultValue = "email")String requestTag,
		Model model
	) {
		List<AdminManagementVO> adminList = adminService.getAdminListOfPageSection(requestPage, requestSearch, requestTag);
		int pageSection = adminService.getPageSection(requestPage);
		int lastPage = adminService.getLastPage(requestSearch, requestTag);
		
		model.addAttribute("requestPage", requestPage);
		model.addAttribute("requestSearch", requestSearch);
		model.addAttribute("requestTag", requestTag);
		model.addAttribute("adminList", adminList);
		model.addAttribute("pageSection", pageSection);
		model.addAttribute("lastPage", lastPage);
		return viewPath + "/AdminManagement";
	}

	@GetMapping("/AdminManagement/PopUpMemberSearch")
	public String getPopUpMemberSearch(
	) {
		return viewPath + "/PopUpMemberSearch";
	}

	@GetMapping("/CategoryManagement/")
	public String getCategoryManagement(
		Model model
	) {
		
		return viewPath + "/CategoryManagement";
	}
}