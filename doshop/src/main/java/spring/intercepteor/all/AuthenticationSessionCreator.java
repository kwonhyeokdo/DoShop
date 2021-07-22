package spring.intercepteor.all;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthenticationSessionCreator implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(true);
		HashMap<String, String> authSession = (HashMap<String, String>)session.getAttribute("authenticationSession");
		if(authSession == null) {
			authSession = new HashMap<String, String>();
			session.setAttribute("authenticationSession", authSession);
		}
		return true;
	}
}
