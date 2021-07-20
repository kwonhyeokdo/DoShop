package member;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.PhoneAuthenticationDAO;
import database.vo.PhoneAuthenticationVO;

@Service
public class PhoneAuthenticationService {
	public final static int AUTHENTICATION_TIME_SEC = 30;
	
	@Autowired
	private PhoneAuthenticationDAO phoneAuthenticationDAO;

	private String createAuthenticationNumber() {
		final int MAX = 999999;
		final int MIN = 100000;
		String authenticationNumber = "" + (int)((Math.random() * (MAX - MIN)) + MIN);
				
		return authenticationNumber;
	}
	
	private Timestamp createAuthenticationTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public void registPhoneAuthentication(String phoneNumber, String place) {
		Timestamp authenticationTime = createAuthenticationTime();
		
		String authenticationNumber = createAuthenticationNumber();
/*
		// 문자 발송 API //
		String api_key = "NCSAPJYPS4YQ6J62";
	    String api_secret = "PVGP4O4CFNYLIB2CMWGAUQOSKHIOPOTF";
	    Message coolsms = new Message(api_key, api_secret);
	    String message = "DoShop 인증번호: [" + authenticationNumber + "]\n" + 
	    				 AUTHENTICATION_TIME_SEC + "초 안에 입력해주세요.";

	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", "01026752337");
	    params.put("from", phoneNumber);
	    params.put("type", "SMS");
	    params.put("text", message);
	    params.put("app_version", "test app 1.2"); // application name and version

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	    // 문자 발송 API 끝 //
*/
		System.out.println("인증번호: " + authenticationNumber);
		phoneAuthenticationDAO.insert(phoneNumber, authenticationNumber, authenticationTime, place);
	}

	public Boolean checkAuthenticationNumber(String phoneNumber, String authenticationNumber, String place) {
		PhoneAuthenticationVO phoneAuthenticationVO = phoneAuthenticationDAO.selectPhoneNumberForLastDate(phoneNumber, place);
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime occurrenceTime = phoneAuthenticationVO.getOccurrenceTime().toLocalDateTime();
		Duration duration = Duration.between(currentTime, occurrenceTime);
		
		if(duration.getSeconds() <= AUTHENTICATION_TIME_SEC &&
		   authenticationNumber.equals(phoneAuthenticationVO.getAuthenticationNumber())) {
			return true;
		}else {
			return false;
		}
	}
}
