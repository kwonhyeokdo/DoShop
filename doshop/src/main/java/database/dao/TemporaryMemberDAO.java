package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import database.vo.TemporaryMemberVO;
import member.MailAuthenticationService;

@Repository
public class TemporaryMemberDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(TemporaryMemberVO temporaryMemberVO) {
		String sql = "insert into temporary_member (email, password, name, sex, birthday, phone_number, postcode, address, detail_address, extra_address, registration_time) values (?,?,?,?,?,?,?,?,?,?,?)";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encoderPassword = encoder.encode(temporaryMemberVO.getPassword());
		jdbcTemplate.update(
			sql,
			temporaryMemberVO.getEmail(), encoderPassword, temporaryMemberVO.getName(),
			temporaryMemberVO.getSex(),	temporaryMemberVO.getBirthday(), temporaryMemberVO.getPhoneNumber(),
			temporaryMemberVO.getPostcode(), temporaryMemberVO.getAddress(), temporaryMemberVO.getDetailAddress(),
			temporaryMemberVO.getExtraAddress(), temporaryMemberVO.getRegistrationTime()
		);
	}
	
	public List<TemporaryMemberVO> selectByEmailInExpiration(String email) {
		String sql = "select * from temporary_member where email = ? AND registration_time >= DATE_SUB(NOW(), INTERVAL ? SECOND)";
		List<TemporaryMemberVO> temporaryMemberList = jdbcTemplate.query(sql, 
			new RowMapper<TemporaryMemberVO>() {
				@Override
				public TemporaryMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TemporaryMemberVO temporaryMemberVO = new TemporaryMemberVO();
					temporaryMemberVO.setEmail(rs.getString("email"));
					temporaryMemberVO.setPassword(rs.getString("password"));
					temporaryMemberVO.setName(rs.getString("name"));
					temporaryMemberVO.setSex(rs.getString("sex"));
					temporaryMemberVO.setBirthday(rs.getString("birthday"));
					temporaryMemberVO.setPhoneNumber(rs.getString("phone_number"));
					temporaryMemberVO.setPostcode(rs.getString("postcode"));
					temporaryMemberVO.setAddress(rs.getString("address"));
					temporaryMemberVO.setDetailAddress(rs.getString("detail_address"));
					temporaryMemberVO.setExtraAddress(rs.getString("extra_address"));
					temporaryMemberVO.setRegistrationTime(rs.getTimestamp("registration_time"));
					return temporaryMemberVO;
				}
			}, email, MailAuthenticationService.AUTHENTICATION_TIME_SEC
		);
		
		return temporaryMemberList;
	}
	
	public void deleteByEmailInExpiration(String email) {
		String sql = "DELETE FROM temporary_member WHERE email=? AND registration_time >= DATE_SUB(NOW(), INTERVAL ? SECOND)";
		jdbcTemplate.update(sql, email, MailAuthenticationService.AUTHENTICATION_TIME_SEC);
	}
}
