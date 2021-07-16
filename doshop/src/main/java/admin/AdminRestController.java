package admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import database.vo.AdminManagementVO;
import database.vo.CategoryVO;

@RestController
@RequestMapping("/Admin")
public class AdminRestController {
	@Autowired
	AdminService adminService;
	
	@PostMapping("/AdminManagement/ChangeAuthority")
	public boolean postChangeAuthority(
		@RequestParam(value="jsonData") String jsonData
	) {
		adminService.changeAuthority(jsonData);
		
		return true;
	}
	
	@PostMapping("/AdminManagement/MemberSearch")
	public List<AdminManagementVO> getMemberSearch(
		@RequestParam(value = "inputEmail", defaultValue = "")String inputEmail
	) {
		List<AdminManagementVO> adminList =  adminService.getAdminList(inputEmail);
		return adminList;
	}
	
	@PostMapping("/CategoryManagement/GetCategoryList")
	public List<CategoryVO> postGetPrimaryCategoryList(
		@RequestParam(value="parentId")int parentId	
	) {
		List<CategoryVO> categoryList = adminService.getCategoryList(parentId);
		return categoryList;
	}
	
	@PostMapping("/CategoryManagement/UpdateCategory")
	public boolean postUpdatePrimaryCateory(
		@RequestParam(value="updateCategoryList")String updateCategoryList
	) {
		System.out.println(updateCategoryList);
		return adminService.updateCategory(updateCategoryList);
	}
}
