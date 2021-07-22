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
import session.SigninSession;

@Component
public class SigninSessionUpdate implements HandlerInterceptor {
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession httpSession = request.getSession();
		if(httpSession != null) {
			SigninSession signinSession = (SigninSession)httpSession.getAttribute("signinSession");
			if(signinSession != null) {
				List<MemberVO> memberVOList = memberDAO.selectByMemberNumber(signinSession.getMemberNumber());
				if(!memberVOList.isEmpty()) {
					MemberVO memberVO = memberVOList.get(0);
					if(memberVO != null){
						signinSession.updateSigninSession(memberVO);
					}	
				}
			}
		}
		return true;
	}
}
