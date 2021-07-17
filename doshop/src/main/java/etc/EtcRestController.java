package etc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import admin.AdminService;
import database.vo.CategoryVO;
import member.signin.SigninSession;

@RestController
public class EtcRestController {
	@Autowired
	EtcService pageService;
	
	@PostMapping("/Etc/GetServerCategory")
	public List<CategoryVO> postGetServerCateoryList(
		@RequestParam(value="categoryParentId")int parentId
	){
		return pageService.getCategoryListWithParentUrl(parentId);
	}
}
