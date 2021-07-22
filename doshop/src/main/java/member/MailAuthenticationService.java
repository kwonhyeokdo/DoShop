package member;

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
import etc.SimpleFile;
import etc.SimpleUtil;
import mail.MailService;
import member.MemberService;

@Service
public class MailAuthenticationService {
	//public final static int AUTHENTICATION_TIME_SEC = 3600;
	public final static int AUTHENTICATION_TIME_SEC = 180;
	@Autowired
	private MailAuthenticationDAO mailAuthenticationDAO;
	@Autowired
	private TemporaryMemberDAO temporaryMemberDAO;
	@Autowired
	private MemberService memberService;
	@Autowired
	SimpleFile simpleFile;
	@Autowired
	MailService mailService;


	
	private Timestamp createAuthenticationTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public void registAuthentication(String email, String authenticationLevel) {
		String token = SimpleUtil.createAuthenticationCode();
		LocalDateTime clostingTime = LocalDateTime.now().plusHours(AUTHENTICATION_TIME_SEC / 3600);
		String title = "";
		String url = "";
		String content = "";
		
		if(authenticationLevel.equals(AuthenticationLevel.SIGNUP.name())) {
			title = "DoShop 회원가입 인증 메일";
			url = "http://localhost:8090/DoShop/Member/Signup/AuthenticationMail";
			content += "<html>";
			content += "<body>";
			content += "<a href='" + url + "?email="+email+"&token=" +token + "'>인증하기</a>";
			content += "<hr>";
			content += "<h3>"+clostingTime+"시간까지 위의 인증 하기를 클릭하시면 이메일 인증이 완료됩니다.</h3>";
			content += "</body>";
			content += "</html>";
		}else if(authenticationLevel.equals(AuthenticationLevel.FIND_PASSWORD.name())) {
			title = "DoShop 비밀번호 분실 본인인증 메일";
			content = simpleFile.getStringInFile("/inner/mail/FindPasswordEmailAuthentication.inner");
			content = content.replace("${token}", token);
		}
		mailAuthenticationDAO.insert(email, token, createAuthenticationTime(), authenticationLevel);
		mailService.sendHtmlMail(email, title, content);
	}
	
	@Transactional
	public boolean checkAuthenticationToken(String email, String token, String authenticationLevel) {
		List<MailAuthenticationVO> mailAuthenticationList = mailAuthenticationDAO.selectByToken(token, authenticationLevel);
		if(mailAuthenticationList == null || mailAuthenticationList.isEmpty()) {
			return false;
		}
		MailAuthenticationVO mailAuthenticationVO = mailAuthenticationList.get(0); 
		if(!mailAuthenticationVO.getEmail().equals(email)) {
			return false;
		}else {
			mailAuthenticationDAO.deleteByToken(token);
			return true;
		}
	}
}