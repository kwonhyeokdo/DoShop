package etc;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EtcController {
	String viewPath = "/Etc";
	
	@GetMapping("/")
	public String home(HttpSession session) {
		return "Home";
	}

	@GetMapping("/Expiration")
	public String getExpiration() {
		return viewPath + "/Expiration";
	}
}
