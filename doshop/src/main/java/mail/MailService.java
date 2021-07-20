package mail;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {
	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;

	public void sendHtmlMail(List<String> recipientList, String title, String content){
		if(recipientList == null || recipientList.isEmpty()) {
			System.out.println("리스트가 비어있어요!");
			return;
		}
		
		String[] to = new String[recipientList.size()];
		for(int i = 0; i < recipientList.size(); i++) {
			to[i] = recipientList.get(i);
		}
		
		createHtmlMail(to, title, content);
	}

	public void sendHtmlMail(String recipient, String title, String content){
		if(recipient == null) {
			System.out.println("보낼 대상이 없어요!");
			return;
		}
		String[] to = new String[1];
		to[0] = recipient;
		createHtmlMail(to, title, content);
	}

	public void sendHtmlMail(String[] recipientList, String title, String content){
		if(recipientList == null || recipientList.length == 0) {
			System.out.println("리스트가 비어있어요!");
			return;
		}
		
		createHtmlMail(recipientList, title, content);
	}
	
	private void createHtmlMail(String[] to, String title, String content){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {// 보내는 사람
				final String from = "kwonhyeokdo1209@gmail.com";
				
				try {
					// 메일 내용 넣을 객체와, 이를 도와주는 Helper 객체 생성
					MimeMessage mail = javaMailSenderImpl.createMimeMessage();
					MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");

					
					// 메일 내용을 채워줌
					mailHelper.setFrom(from);	// 보내는 사람 셋팅
					mailHelper.setTo(to);		// 받는 사람 셋팅
					mailHelper.setSubject(title);	// 제목 셋팅
					mailHelper.setText(content, true);	// HTML 내용 셋팅
					//mailHelper.setText(content);	// 내용 셋팅

					// 메일 전송
					javaMailSenderImpl.send(mail);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
	}
}
