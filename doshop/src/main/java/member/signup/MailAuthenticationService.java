package member.signup;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import database.dao.MailAuthenticationDAO;
import database.dao.TemporaryMemberDAO;
import database.vo.MailAuthenticationVO;
import mail.MailService;
import member.MemberService;

@Service
public class MailAuthenticationService {
	public final static int AUTHENTICATION_TIME_SEC = 3600;
	private MailService mailService;
	private MailAuthenticationDAO mailAuthenticationDAO;
	private TemporaryMemberDAO temporaryMemberDAO;
	private MemberService memberService;
	
	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Autowired
	public void setMailAuthenticationDAO(MailAuthenticationDAO mailAuthenticationDAO) {
		this.mailAuthenticationDAO = mailAuthenticationDAO;
	}
	
	@Autowired
	public void setTemporaryMemberDAO(TemporaryMemberDAO temporaryMemberDAO) {
		this.temporaryMemberDAO = temporaryMemberDAO;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	private String createAuthenticationToken() {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		//System.out.println("generatedString: " + generatedString);
		return generatedString;
	}
	
	private Timestamp createAuthenticationTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public void registEmailAuthentication(String email) {
		String token = createAuthenticationToken();
		String title = "DoShop 회원가입 인증 메일";
		String url = "http://localhost:8090/DoShop/Member/authentication_mail";
		LocalDateTime clostingTime = LocalDateTime.now().plusHours(AUTHENTICATION_TIME_SEC / 3600); 
		
		String content = "<html>";
		content += "<body>";
		content += "<a href='" + url + "?token=" +token + "'>인증하기</a>";
		content += "<hr>";
		content += "<h3>"+clostingTime+"시간까지 위의 인증 하기를 클릭하시면 이메일 인증이 완료됩니다.</h3>";
		content += "</body>";
		content += "</html>";
		
		
		mailAuthenticationDAO.insert(email, token, createAuthenticationTime());
		mailService.sendHtmlMail(email, title, content);
	}
	
	@Transactional
	public boolean checkAuthenticationToken(String token) {
		List<MailAuthenticationVO> mailAuthenticationList = mailAuthenticationDAO.selectByToken(token);
		if(mailAuthenticationList == null || mailAuthenticationList.isEmpty()) {
			return false;
		}
		MailAuthenticationVO mailAuthenticationVO = mailAuthenticationList.get(0); 
		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime occurrenceTime = mailAuthenticationVO.getOccurrenceTime().toLocalDateTime();
		Duration duration = Duration.between(currentTime, occurrenceTime);
		
		if(duration.getSeconds() <= AUTHENTICATION_TIME_SEC &&
		   mailAuthenticationVO.getToken().equals(token)) {
			mailAuthenticationDAO.deleteByToken(token);
			memberService.registMember(temporaryMemberDAO.selectByEmail(mailAuthenticationVO.getEmail()).get(0));
			return true;
		}else {
			return false;
		}
	}
}