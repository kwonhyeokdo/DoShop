package etc;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class SimpleContextUtil {
	
	/* ServletContext*/
	public static ServletContext getContext() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
	}
	
	public static Object getAttributeFromContext(String key) {
		ServletContext context = getContext();
		return context.getAttribute(key);
	}
	
	public static void setAttributeToContext(String key, Object value) {
		ServletContext context = getContext();
		if(context != null) {
			context.setAttribute(key, value);
		}
	}
	
	/* HttpServletRequest */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static Object getAttributeFromRequest(String key) {
		return getRequest().getAttribute(key);
	}
	
	public static void setAttributeToRequest(String key, Object value) {
		HttpServletRequest request = getRequest();
		request.setAttribute(key, value);
	}
	
	/* HttpServletResponse */
	
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/* HttpSession */
	
	public static HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}
	
	public static void invalidateSession() {
		HttpSession session = getSession(false);
		if(session != null) {
			session.invalidate();
		}
	}
	
	public static void deleteAttributeFromSession(String key) {
		HttpSession session = getSession(false);
		if(session != null) {
			Object obj = session.getAttribute(key);
			if(obj != null) {
				session.removeAttribute(key);
			}
		}
	}
	
	public static Object getAttributeFromSession(String key) {
		HttpSession session = getSession(false);
		if(session == null) {
			return null;
		}
		return session.getAttribute(key);
	}
	
	public static void setAttributeToSession(String key, Object value) {
		HttpSession session = getSession(true);
		session.setAttribute(key, value);
	}
	
	/* Cookie */
	public static Cookie getCookie(String cookieName) {
		HttpServletRequest request = getRequest();
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) {
				return cookie;
			}
		}
		
		return null;
	}
	
	public static void createCookie(String cookieName, String value, int maxAge, String path) {
		HttpServletResponse response = getResponse();
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(String cookieName) {
		HttpServletResponse response = getResponse();
		try {
			if(getCookie(cookieName) != null) {
				Cookie cookie = new Cookie(cookieName, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCookie(Cookie cookie) {
		HttpServletResponse response = getResponse();
		try {
			if(cookie != null) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
