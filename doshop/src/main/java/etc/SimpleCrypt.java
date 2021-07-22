package etc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SimpleCrypt {
	private static final String secretKey = "01234567890123456789012345678912"; //32bit
	private static final byte[] IV = {
		0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00
	}; // 16bit
	
	//암호화
	public static String aes256encrypt(String str){
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV));
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(encrypted));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//복호화
	public static String aes256decrypt(String str){
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV));
			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			return new String(c.doFinal(byteStr), "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String bCryptEncode(String input) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder.encode(input);
	}
	
	public static boolean bCryptMatch(String input, String encoded) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder.matches(input, encoded);
	}
}
