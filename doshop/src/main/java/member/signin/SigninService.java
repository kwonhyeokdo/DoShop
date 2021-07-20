package member.signin;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import etc.SimpleContextUtil;
import etc.aes.AES256;

@Service
public class SigninService {
	@Autowired
	private MemberDAO memberDAO;
	
	private boolean checkEmail(String inputEmail, String registeredEmail) {
		return inputEmail.equals(registeredEmail);
	}
	
	private boolean checkPassword(String inputPassword, String registeredPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder.matches(inputPassword, registeredPassword);
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
		try {
			MemberVO memberVO = memberDAO.selectByEmail(AES256.decrypt(cryptEmail));
			if (memberVO == null || !checkEmail(AES256.decrypt(cryptEmail), memberVO.getEmail()) ||
				!cryptPassword.equals(memberVO.getPassword())) {
				return false;
			}
			signout();
			createSigninSession(memberVO);
			
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void useRememberEmailCookie(String inputEmail) {
		try {
			SimpleContextUtil.createCookie("rememberEmail", AES256.encrypt(inputEmail), 24*60*60, "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DoNotUseRememberEmailCookie() {
		SimpleContextUtil.deleteCookie("rememberEmail");
	}

	public void useAutoSigninCookie(String inputEmail, String inputPassword) {
		try {
			SimpleContextUtil.createCookie("autoSignin", "true", 24*60*60, "/");
			MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
			SimpleContextUtil.createCookie("autoSigninEmail", AES256.encrypt(inputEmail), 24*60*60, "/");
			SimpleContextUtil.createCookie("autoSigninPassword", memberVO.getPassword(), 24*60*60, "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
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