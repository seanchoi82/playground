package kr.ko.nexmain.server.MissingU.common.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SimpleRegistrationNotifier implements RegistrationNotifier {

	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(Member member) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("[Simple] ȸ�� ���� �ȳ�");
		message.setText("ȸ�� ������ ȯ���մϴ�.");
		message.setFrom("madvirus@wemade.com");
		message.setTo(member.getEmail());
		try {
			mailSender.send(message);
		} catch (MailException ex) {
			// ���� ó��
			ex.printStackTrace();
		}
	}

}
