package spring.intercepteor.all;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import etc.cookie.SimpleCookie;
import member.signin.SigninService;
import member.signin.SigninSession;

@Component
public class AutoSignin implements HandlerInterceptor {
	@Autowired
	private SimpleCookie simpleCookie;
	@Autowired
	private SigninService signinService;
	@Autowired
	HttpSession httpSession;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SigninSession signinSession = (SigninSession)httpSession.getAttribute("signinSession");
		if(signinSession == null) {
			System.out.println("세션 없음");
			Cookie autoSignin = simpleCookie.getCookie("autoSignin");
			if(autoSignin != null && autoSignin.getValue().equals("true")) {
				Cookie autoSigninEmail = simpleCookie.getCookie("autoSigninEmail");
				Cookie autoSigninPassword = simpleCookie.getCookie("autoSigninPassword");
				if(autoSigninEmail != null && autoSigninPassword != null) {
					signinService.signinWithCrypt(autoSigninEmail.getValue(), autoSigninPassword.getValue());
					System.out.println("autoSignin: " + autoSignin.getValue());
					System.out.println("autoSigninEmail: " + autoSigninEmail.getValue());
					System.out.println("autoSigninPassword: " + autoSigninPassword.getValue());
				}
			}
		}else {
			System.out.println("세션 존재");
		}
		
		return true;
	}

}
