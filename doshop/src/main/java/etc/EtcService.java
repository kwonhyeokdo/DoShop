package etc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import database.dao.CategoryDAO;
import database.vo.CategoryVO;

@Service
public class EtcService {
	@Autowired
	CategoryDAO categoryDAO;
	
	public List<CategoryVO> getCategoryListWithParentUrl(int parentId) {
		List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
		List<CategoryVO> tmpCategoryList = categoryDAO.selectCategoryOrderBySequence(parentId);
		Stack<String> tmpParentURL = new Stack<String>();
		String parentURL = "";
		if(!tmpCategoryList.isEmpty()) {
			CategoryVO category = tmpCategoryList.get(0);
			CategoryVO parentCategory = categoryDAO.selectParentCategory(category.getParentId());
			while(parentCategory.getSelfId() != CategoryDAO.ROOT_CATEGORY_ID){
				tmpParentURL.push("/" + parentCategory.getUrl());
				category = parentCategory;
				parentCategory = categoryDAO.selectParentCategory(category.getParentId());
			}
		}
		
		while(!tmpParentURL.isEmpty()) {
			parentURL += tmpParentURL.pop();
		}
		
		for(CategoryVO categoryVO : tmpCategoryList) {
			categoryVO.setUrl(parentURL + "/" + categoryVO.getUrl());
			categoryList.add(categoryVO);
		}
		
		return categoryList;
	}
}
