package member.signin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.vo.MemberVO;

@Service
public class SigninService {
	private MemberDAO memberDAO;
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	private boolean checkEmail(String inputEmail, String registeredEmail) {
		return inputEmail.equals(registeredEmail);
	}
	
	private boolean checkPassword(String inputPassword, String registeredPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder.matches(inputPassword, registeredPassword);
	}
	
	private void createSigninSession(MemberVO memberVO){
		SigninSession signinSession = new SigninSession(memberVO);
		httpSession.setAttribute("signinSession", signinSession);
	}
	
	public void signout() {
		httpSession.invalidate();
	}
	
	public boolean signin(String inputEmail, String inputPassword) {
		MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
		if (memberVO == null || !checkEmail(inputEmail, memberVO.getEmail()) ||
			!checkPassword(inputPassword, memberVO.getPassword())) {
			return false;
		}
		signout();
		createSigninSession(memberVO);
		return true;
	}
}
