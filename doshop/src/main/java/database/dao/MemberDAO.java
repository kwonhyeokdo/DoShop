package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import database.vo.AdminManagementVO;
import database.vo.MemberVO;
import database.vo.TemporaryMemberVO;

@Repository
public class MemberDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void insert(TemporaryMemberVO temporaryMemberVO) {
		String sql = "INSERT INTO member(email, password, name, sex, birthday, phone_number, postcode, address, detail_address, extra_address, authority, registration_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
		jdbcTemplate.update(
			sql,
			temporaryMemberVO.getEmail(), temporaryMemberVO.getPassword(), temporaryMemberVO.getName(),
			temporaryMemberVO.getSex(),	temporaryMemberVO.getBirthday(), temporaryMemberVO.getPhoneNumber(),
			temporaryMemberVO.getPostcode(), temporaryMemberVO.getAddress(), temporaryMemberVO.getDetailAddress(),
			temporaryMemberVO.getExtraAddress(), 0, new Timestamp(System.currentTimeMillis())
		);
	}

	public MemberVO selectByEmail(String email) {
		String sql = "select * from member where email = ?";
		List<MemberVO> memberList = jdbcTemplate.query(sql, 
			new RowMapper<MemberVO>() {
				@Override
				public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MemberVO memberVO = new MemberVO();
					memberVO.setMemberNumber(rs.getInt("member_number"));
					memberVO.setEmail(rs.getString("email"));
					memberVO.setPassword(rs.getString("password"));
					memberVO.setName(rs.getString("name"));
					memberVO.setSex(rs.getString("sex"));
					memberVO.setBirthday(rs.getString("birthday"));
					memberVO.setPhoneNumber(rs.getString("phone_number"));
					memberVO.setPostcode(rs.getString("postcode"));
					memberVO.setAddress(rs.getString("address"));
					memberVO.setDetailAddress(rs.getString("detail_address"));
					memberVO.setExtraAddress(rs.getString("extra_address"));
					memberVO.setAuthority(rs.getInt("authority"));
					memberVO.setRegistrationTime(rs.getTimestamp("registration_time"));
					return memberVO;
				}
			
			},
			email
		);
		
		return memberList.isEmpty() ? null : memberList.get(0);
	}
	
	public List<MemberVO> selectByMemberNumber(int memberNumber) {
		String sql = "select * from member where member_number = ?";
		List<MemberVO> memberList = jdbcTemplate.query(sql, 
			new RowMapper<MemberVO>() {
				@Override
				public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MemberVO memberVO = new MemberVO();
					memberVO.setMemberNumber(rs.getInt("member_number"));
					memberVO.setEmail(rs.getString("email"));
					memberVO.setPassword(rs.getString("password"));
					memberVO.setName(rs.getString("name"));
					memberVO.setSex(rs.getString("sex"));
					memberVO.setBirthday(rs.getString("birthday"));
					memberVO.setPhoneNumber(rs.getString("phone_number"));
					memberVO.setPostcode(rs.getString("postcode"));
					memberVO.setAddress(rs.getString("address"));
					memberVO.setDetailAddress(rs.getString("detail_address"));
					memberVO.setExtraAddress(rs.getString("extra_address"));
					memberVO.setAuthority(rs.getInt("authority"));
					memberVO.setRegistrationTime(rs.getTimestamp("registration_time"));
					return memberVO;
				}
			
			},
			memberNumber
		);
		
		return memberList;
	}
	
	public int selectCountByAuthority(int authority) {
		String sql = "SELECT count(*) FROM member WHERE authority=?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, authority);
		
		return count;
	}
	
	public List<AdminManagementVO> getAdminManagementVOListOfSection(int startIndex, int endIndex, String search, String tag){
		String sql = "SELECT email, authority, ranking FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY registration_time DESC) AS ranking FROM member WHERE (authority BETWEEN 1 AND 3) AND ("+tag+" LIKE '%"+search+"%')) as Ranking WHERE (ranking BETWEEN ? AND ?)";
		List<AdminManagementVO> adminManagmentList = jdbcTemplate.query(sql, new RowMapper<AdminManagementVO>() {
			@Override
			public AdminManagementVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminManagementVO adminManagementVO = new AdminManagementVO();
				adminManagementVO.setEmail(rs.getString("email"));
				adminManagementVO.setAuthority(rs.getInt("authority"));
				adminManagementVO.setRanking(rs.getInt("ranking"));
				
				return adminManagementVO;
			}
		}, startIndex, endIndex);
		return adminManagmentList;
	}
	
	public List<AdminManagementVO> selectAdminManagement(String search){
		String sql = "SELECT email, authority, ranking FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY registration_time DESC) AS ranking FROM member WHERE (email LIKE '%"+search+"%')) as Ranking";
		List<AdminManagementVO> adminManagmentList = jdbcTemplate.query(sql, new RowMapper<AdminManagementVO>() {
			@Override
			public AdminManagementVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdminManagementVO adminManagementVO = new AdminManagementVO();
				adminManagementVO.setEmail(rs.getString("email"));
				adminManagementVO.setAuthority(rs.getInt("authority"));
				adminManagementVO.setRanking(rs.getInt("ranking"));
				
				return adminManagementVO;
			}
		});
		return adminManagmentList;
	}
	
	public int getCountOfAdminManagementVOList(String search, String tag){
		String sql = "SELECT count(*) FROM member WHERE (authority between 1 and 3) and (" + tag + " LIKE CONCAT('%','" + search + "','%'))";
		try {
			int i = jdbcTemplate.queryForObject(sql, Integer.class);
			return i;
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	public void updateAuthorityByEmail(String email, int authority) {
		String sql = "UPDATE member SET authority=? WHERE email=?";
		jdbcTemplate.update(sql, authority, email);
	}
	
	public boolean updatePasswordByEmail(String email, String password) {
		String sql = "UPDATE member SET password=? WHERE email=?";
		int count = jdbcTemplate.update(sql, password, email);
		return (count == 1) ? true : false;
	}
}