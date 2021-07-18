package admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import database.dao.CategoryDAO;
import database.dao.MemberDAO;
import database.vo.AdminManagementVO;
import database.vo.CategoryVO;
import member.signin.SigninService;
import member.signin.SigninSession;

@Service
public class AdminService {
	@Autowired
	SigninService signinService;
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	CategoryDAO categoryDAO;
	
	protected int getPageSection(int requestPage) {
		if(requestPage <= 10) {
			return 0;
		}
		
		char[] page = (((Integer)requestPage).toString()).toCharArray();
		for(int i = 1; i < page.length; i++) {
			page[i] = '0';
		}

		return Integer.parseInt(String.valueOf(page));
	}
	
	protected int getLastPage(String requestSearch, String requestTag) {
		int countOfAllAdmin = memberDAO.getCountOfAdminManagementVOList(requestSearch, requestTag);
		int lastPage = countOfAllAdmin / 10;
		return ((countOfAllAdmin % 10) > 0) ? lastPage += 1 : lastPage;
	}
	
	public List<AdminManagementVO> getAdminListOfPageSection(int requestPage, String requestSearch, String requestTag) {
		int startIndex = requestPage * 10 - 9;
		int endIndex = requestPage * 10;
		return memberDAO.getAdminManagementVOListOfSection(startIndex, endIndex, requestSearch, requestTag);
	}
	
	@Transactional
	public void changeAuthority(String jsonData) {
		JSONParser jsonParser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray)jsonParser.parse(jsonData);
			for(Object obj : jsonArray) {
				String email = (String)((JSONObject)obj).get("email");
				int authority = Math.toIntExact((long)((JSONObject)obj).get("authority"));
				memberDAO.updateAuthorityByEmail(email, authority);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AdminManagementVO> getAdminList(String requestSearch) {
		return memberDAO.selectAdminManagement(requestSearch);
	}
	
	public List<CategoryVO> getCategoryList(int parentId){
		return categoryDAO.selectCategoryOrderBySequence(parentId);
	}
	
	@Transactional
	public boolean updateCategory(String updatePrimaryCategoryList) {
		int parentId = 0;
		List<CategoryVO> newCategory = new ArrayList<CategoryVO>();
		List<CategoryVO> updateCategory = new ArrayList<CategoryVO>();
		List<CategoryVO> deleteCategory = new ArrayList<CategoryVO>();
		List<CategoryVO> sequenceCategory = new ArrayList<CategoryVO>();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(updatePrimaryCategoryList);
			parentId = ((Long)jsonObject.get("parentId")).intValue();
			JSONArray newJsonArray = (JSONArray)jsonObject.get("new");
			JSONArray updateJsonArray = (JSONArray)jsonObject.get("update");
			JSONArray deleteJsonArray = (JSONArray)jsonObject.get("delete");
			JSONArray sequenceJsonArray = (JSONArray)jsonObject.get("sequence");
			
			for(Object obj : newJsonArray) {
				String name = (String)(((JSONObject) obj).get("name"));
				String url = (String)(((JSONObject) obj).get("url"));
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setParentId(parentId);
				categoryVO.setName(name);
				categoryVO.setUrl(url);
				newCategory.add(categoryVO);
			}

			for(Object obj : updateJsonArray) {
				int selfId = ((Long)((JSONObject)obj).get("selfId")).intValue();
				String name = (String)(((JSONObject) obj).get("name"));
				String url = (String)(((JSONObject) obj).get("url"));
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setSelfId(selfId);
				categoryVO.setName(name);
				categoryVO.setUrl(url);
				updateCategory.add(categoryVO);
			}

			for(Object obj : deleteJsonArray) {
				int selfId = ((Long)((JSONObject)obj).get("selfId")).intValue();
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setSelfId(selfId);
				deleteCategory.add(categoryVO);
			}

			for(Object obj : sequenceJsonArray) {
				String name = (String)(((JSONObject) obj).get("name"));
				int order = Math.toIntExact((long)((JSONObject)obj).get("order"));
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setName(name);
				categoryVO.setSequence(order);
				categoryVO.setParentId(parentId);
				sequenceCategory.add(categoryVO);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		try {
			if(!newCategory.isEmpty()) {
				categoryDAO.insertCategory(newCategory);	
			}
			if(!updateCategory.isEmpty()) {
				categoryDAO.updateCategory(updateCategory);	
			}
			if(!deleteCategory.isEmpty()) {
				categoryDAO.deleteCategory(deleteCategory);	
			}
			if(!sequenceCategory.isEmpty()) {
				categoryDAO.updateSequenceCategory(sequenceCategory);	
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
