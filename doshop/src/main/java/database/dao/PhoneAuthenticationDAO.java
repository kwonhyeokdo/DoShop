package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import database.vo.PhoneAuthenticationVO;

@Repository
public class PhoneAuthenticationDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(String phoneNumber, String authenticationNumber, Timestamp occurrenceTime, String authenticationLevel) {
		String sql = "insert into phone_authentication (phone_number, authentication_number, occurrence_time, authentication_level) values (?,?,?,?)";
		jdbcTemplate.update(sql, phoneNumber, authenticationNumber, occurrenceTime, authenticationLevel);
	}
	
	public PhoneAuthenticationVO selectPhoneNumberForLastDate(String phoneNumber, String authenticationLevel) {
		String sql = "select * from phone_authentication where phone_number=? and occurrence_time=(select max(occurrence_time) from phone_authentication where phone_number=?) and authentication_level=?";
		
		PhoneAuthenticationVO phoneAuthenticationVO = jdbcTemplate.queryForObject(sql, new RowMapper<PhoneAuthenticationVO>() {
			@Override
			public PhoneAuthenticationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PhoneAuthenticationVO phoneAuthenticationVO = new PhoneAuthenticationVO();
				phoneAuthenticationVO.setPhoneNumber(rs.getString("phone_number"));
				phoneAuthenticationVO.setAuthenticationNumber(rs.getString("authentication_number"));
				phoneAuthenticationVO.setOccurrenceTime(rs.getTimestamp("occurrence_time"));
				return phoneAuthenticationVO;
			}
		}, phoneNumber, phoneNumber, authenticationLevel);
		
		return phoneAuthenticationVO;
	}
}
