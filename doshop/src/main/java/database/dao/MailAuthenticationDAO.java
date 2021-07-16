package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import database.vo.MailAuthenticationVO;

@Repository
public class MailAuthenticationDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(String email, String token, Timestamp occurrenceTime) {
		String sql = "insert into email_authentication (email, token, occurrence_time) values (?,?,?)";
		
		jdbcTemplate.update(sql, email, token, occurrenceTime);
	}
	
	public List<MailAuthenticationVO> selectByToken(String token) {
		String sql = "select * from email_authentication where token=?";
		
		List<MailAuthenticationVO> mailAuthenticationList = jdbcTemplate.query(sql, 
			new RowMapper<MailAuthenticationVO>() {
				@Override
				public MailAuthenticationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MailAuthenticationVO mailAuthenticationVO = new MailAuthenticationVO();
					mailAuthenticationVO.setEmail(rs.getString("email"));
					mailAuthenticationVO.setToken(rs.getString("token"));
					mailAuthenticationVO.setOccurrenceTime(rs.getTimestamp("occurrence_time"));
					return mailAuthenticationVO;
				}
			},
			token
		);
		
		return mailAuthenticationList;
	}
	
	public void deleteByToken(String token) {
		String sql = "delete from email_authentication where token = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, token);
				return pstmt;
			}
		});
	}
}
