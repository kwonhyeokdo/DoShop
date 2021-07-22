package etc;

import org.apache.commons.lang.RandomStringUtils;

public class SimpleUtil {
	public static String createAuthenticationCode() {
		String generatedString = RandomStringUtils.randomAlphanumeric(10);
		return generatedString;
	}
	
	public static String createAuthenticationNumber() {
		final int MAX = 999999;
		final int MIN = 100000;
		String authenticationNumber = "" + (int)((Math.random() * (MAX - MIN)) + MIN);
				
		return authenticationNumber;
	}
}
