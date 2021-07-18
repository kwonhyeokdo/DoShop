package spring.intercepteor.all;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import etc.SimpleContextUtil;
import member.signin.SigninService;
import member.signin.SigninSession;

@Component
public class AutoSignin implements HandlerInterceptor {
	@Autowired
	private SigninService signinService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SigninSession signinSession = (SigninSession)SimpleContextUtil.getAttributeFromSession("signinSession");
		if(signinSession == null) {
			Cookie autoSignin = SimpleContextUtil.getCookie("autoSignin");
			if(autoSignin != null && autoSignin.getValue().equals("true")) {
				Cookie autoSigninEmail = SimpleContextUtil.getCookie("autoSigninEmail");
				Cookie autoSigninPassword = SimpleContextUtil.getCookie("autoSigninPassword");
				if(autoSigninEmail != null && autoSigninPassword != null) {
					String email = autoSigninEmail.getValue();
					String password = autoSigninPassword.getValue();
					if(signinService.signinWithCrypt(email, password)) {
						SimpleContextUtil.createCookie("autoSigninEmail", email, 24*60*60);
						SimpleContextUtil.createCookie("autoSigninPassword", password, 24*60*60);
					}
				}
			}
		}
		
		return true;
	}

}
