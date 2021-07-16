package spring.intercepteor.all;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import database.dao.MemberDAO;
import database.vo.MemberVO;
import member.signin.SigninService;
import member.signin.SigninSession;

@Component
public class SigninSessionUpdate implements HandlerInterceptor {
	@Autowired
	HttpSession httpSession;
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(httpSession != null) {
			SigninSession signinSession = (SigninSession)httpSession.getAttribute("signinSession");
			if(signinSession != null) {
				MemberVO memberVO = memberDAO.selectMemberVOByMemberNumber(signinSession.getMemberNumber());
				if(memberVO != null){
					signinSession.updateSigninSession(memberVO);
				}
			}
		}
		return true;
	}
}
