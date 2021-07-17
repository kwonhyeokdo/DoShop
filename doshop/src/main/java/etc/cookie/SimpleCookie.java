package etc.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SimpleCookie {
	@Autowired
	HttpServletResponse response;
	
	public Cookie getCookie(String cookieName) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		Cookie[] allCookie = request.getCookies();
		if(allCookie != null) {
			for(Cookie cookie : allCookie) {
				if(cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}	
		}
		
		return null;
	}
	
	public void deleteCookie(String cookieName) {
		try {
			if(getCookie(cookieName) !=null) {
				Cookie cookie = new Cookie(cookieName, null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
