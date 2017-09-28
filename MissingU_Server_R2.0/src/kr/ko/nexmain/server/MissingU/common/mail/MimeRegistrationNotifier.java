package kr.ko.nexmain.server.MissingU.common.mail;

import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

public class MimeRegistrationNotifier implements RegistrationNotifier {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(Member member) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			message.setSubject("[Mime] 회원 가입 안내", "euc-kr");
			String htmlContent = "<strong>안녕하세요</strong>, 반갑습니다.";
			message.setText(htmlContent, "euc-kr", "html");
			message.setFrom(new InternetAddress("madvirus@wemade.com"));
			message.addRecipient(RecipientType.TO, new InternetAddress(member
					.getEmail()));
		} catch (MessagingException e) {
			// 예외 처리
			e.printStackTrace();
			return;
		}
		try {
			mailSender.send(message);
		} catch (MailException e) {
			// 예외 처리
			e.printStackTrace();
		}
	}

}
