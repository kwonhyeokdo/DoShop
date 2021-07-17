package member.signin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import etc.aes.AES256;
import etc.cookie.SimpleCookie;

@Service
public class SigninService {
	@Autowired
	private AES256 aes256;
	@Autowired
	private HttpServletResponse httpResponse;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private SimpleCookie simpleCookie;
	
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
		deleteAutoSigninCookie();
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
	
	public boolean signinWithCrypt(String cryptEmail, String cryptPassword) {
		try {
			System.out.println("email: " + aes256.decrypt(cryptEmail));
			MemberVO memberVO = memberDAO.selectByEmail(aes256.decrypt(cryptEmail));
			if (memberVO == null || !checkEmail(aes256.decrypt(cryptEmail), memberVO.getEmail()) ||
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

	public void registRememberEmailCookie(String inputEmail) {
		try {
			Cookie cookie = simpleCookie.getCookie("rememberEmail");
			if(cookie == null) {
				cookie = new Cookie("rememberEmail", aes256.encrypt(inputEmail));
				cookie.setMaxAge(24*60*60);
			}else {
				cookie.setValue(aes256.encrypt(inputEmail));
			}
			httpResponse.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRememberEmailCookie() {
		simpleCookie.deleteCookie("rememberEmail");
	}

	public void useAutoSigninCookie(String inputEmail, String inputPassword) {
		try {
			Cookie autoSignin = simpleCookie.getCookie("autoSignin");
			if(autoSignin == null) {
				autoSignin = new Cookie("autoSignin", "true");
				autoSignin.setMaxAge(24*60*60);
				httpResponse.addCookie(autoSignin);
				System.out.println("000000");
			}else if(autoSignin.getValue().equals("false")) {
				autoSignin.setValue("true");
				httpResponse.addCookie(autoSignin);
				System.out.println("1111111");
			}
			MemberVO memberVO = memberDAO.selectByEmail(inputEmail);
			Cookie autoSigninEmail = new Cookie("autoSigninEmail", aes256.encrypt(inputEmail));
			Cookie autoSigninPassword = new Cookie("autoSigninPassword", memberVO.getPassword());
			autoSigninEmail.setMaxAge(24*60*60);
			autoSigninPassword.setMaxAge(24*60*60);
			httpResponse.addCookie(autoSigninEmail);
			httpResponse.addCookie(autoSigninPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void notUseAutoSigninCookie() {
		Cookie autoSignin = simpleCookie.getCookie("autoSignin");
		if(autoSignin != null) {
			httpResponse.addCookie(autoSignin);
		}
	}
	
	public void deleteAutoSigninCookie() {
		String[] cookienames = {"autoSigninEmail", "autoSigninPassword"};
		for(String cookieName : cookienames) {
			simpleCookie.deleteCookie(cookieName);
		}
	}
}
