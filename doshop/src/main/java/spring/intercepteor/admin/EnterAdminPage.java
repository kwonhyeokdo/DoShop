package spring.intercepteor.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import member.signin.SigninSession;

public class EnterAdminPage implements HandlerInterceptor{
	ConditionForAdminEntry conditionForAdminEntry;
	
	public EnterAdminPage(ConditionForAdminEntry conditionForAdminEntry) {
		this.conditionForAdminEntry = conditionForAdminEntry;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session != null) {
			SigninSession signinSession = ((SigninSession)session.getAttribute("signinSession"));
			
			if(signinSession == null || !conditionForAdminEntry.condition(signinSession.getAuthority())) {
				response.sendRedirect(request.getContextPath());
				return false;
			}else {
				return true;
			}
		}else {
			response.sendRedirect(request.getContextPath());
			return false;
		}
	}
}