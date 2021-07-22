package member.signin;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import etc.SimpleContextUtil;
import etc.SimpleCrypt;
import session.SigninSession;

@Service
public class SigninService {
	@Autowired
	private MemberDAO memberDAO;
	
	private boolean checkEmail(String inputEmail, String registeredEmail) {
		return inputEmail.equals(registeredEmail);
	}
	
	private boolean checkPassword(String inputPassword, String registeredPassword) {
		return SimpleCrypt.bCryptMatch(inputPassword, registeredPassword);
	}
	
	private void createSigninSession(MemberVO memberVO){
		SigninSession signinSession = new SigninSession(memberVO);
		SimpleContextUtil.setAttributeToSession("signinSession", signinSession);
	}
	
	public void signout() {
		deleteAutoEmailPasswordCookie();
		SimpleContextUtil.invalidateSession();
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
	
	public boolean signinWithCrypt(String cryptEmail, String cryptPassword) {
		MemberVO memberVO = memberDAO.selectByEmail(SimpleCrypt.aes256decrypt(cryptPassword));
		if (memberVO == null || !checkEmail(SimpleCrypt.aes256decrypt(cryptPassword), memberVO.getEmail()) ||
			!cryptPassword.equals(memberVO.getPassword())) {
			return false;
		}
		signout();
		createSigninSession(memberVO);
		
		return true;
	}

	public void useRememberEmailCookie(String inputEmail) {
		try {
			SimpleContextUtil.createCookie("rememberEmail", SimpleCrypt.aes256encrypt(inputEmail), 24*60*60, "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DoNotUseRememberEmailCookie() {
		SimpleContextUtil.deleteCookie("rememberEmail");
	}

	public void useAutoSigninCookie(String inputEmail, String inputPassword) {
		SimpleContextUtil.createCookie("autoSignin", "true", 24*60*60, "/");
		MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
		SimpleContextUtil.createCookie("autoSigninEmail", SimpleCrypt.aes256encrypt(inputPassword), 24*60*60, "/");
		SimpleContextUtil.createCookie("autoSigninPassword", memberVO.getPassword(), 24*60*60, "/");
	}
	
	public void DoNotUseAutoSigninCookie() {
		Cookie autoSignin = SimpleContextUtil.getCookie("autoSignin");
		if(autoSignin != null) {
			SimpleContextUtil.deleteCookie(autoSignin);
		}
	}
	
	public void deleteAutoEmailPasswordCookie() {
		String[] cookienames = {"autoSigninEmail", "autoSigninPassword"};
		for(String cookieName : cookienames) {
			SimpleContextUtil.deleteCookie(cookieName);
		}
	}
}