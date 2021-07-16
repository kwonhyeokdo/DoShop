package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import database.vo.CategoryVO;

@Repository
public class CategoryDAO {
	public static final int ROOT_CATEGORY_ID = 1; 
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<CategoryVO> selectCategoryOrderBySequence(int parentId) {
		String sql = "SELECT * FROM category WHERE parent_id = ? ORDER BY sequence";
		List<CategoryVO> categoryVOList = jdbcTemplate.query(sql,
			new RowMapper<CategoryVO>() {
			@Override
			public CategoryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setSelfId(rs.getInt("self_id"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setUrl(rs.getString("url"));
				categoryVO.setSequence(rs.getInt("sequence"));
				return categoryVO;
			}
		}, parentId);
		
		return categoryVOList;
	}
	
	public void insertCategory(List<CategoryVO> newCategory) {
		for(CategoryVO categoryVO : newCategory) {
			String sql = "INSERT INTO category(parent_id, name, url) values(?, ?, ?);";
			jdbcTemplate.update(sql, categoryVO.getParentId(), categoryVO.getName(), categoryVO.getUrl());
		}
	}
	
	public void updateCategory(List<CategoryVO> updateCategory) {
		for(CategoryVO categoryVO : updateCategory) {
			String sql = "UPDATE category SET name=?, url=? WHERE self_id=?;";
			jdbcTemplate.update(sql, categoryVO.getName(), categoryVO.getUrl(), categoryVO.getSelfId());
		}
	}
	
	public void deleteCategory(List<CategoryVO> deleteCategory) {
		for(CategoryVO categoryVO : deleteCategory) {
			String sql = "DELETE FROM category WHERE self_id=?;";
			jdbcTemplate.update(sql, categoryVO.getSelfId());
		}
	}
	
	public void updateSequenceCategory(List<CategoryVO> sequenceCategory) {
		for(CategoryVO categoryVO : sequenceCategory) {
			String sql = "UPDATE category SET sequence=? WHERE name=? and parent_id=?;";
			jdbcTemplate.update(sql, categoryVO.getSequence(), categoryVO.getName(), categoryVO.getParentId());
		}
	}
	
	public CategoryVO selectParentCategory(int parentId) {
		String sql = "SELECT * FROM category WHERE self_id = ?";
		List<CategoryVO> categoryVOList = jdbcTemplate.query(sql,
			new RowMapper<CategoryVO>() {
			@Override
			public CategoryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setSelfId(rs.getInt("self_id"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setUrl(rs.getString("url"));
				categoryVO.setSequence(rs.getInt("sequence"));
				return categoryVO;
			}
		}, parentId);
		
		return categoryVOList.isEmpty() ? null : categoryVOList.get(0);
	}
}