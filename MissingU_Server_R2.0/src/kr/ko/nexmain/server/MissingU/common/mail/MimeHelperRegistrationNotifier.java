package kr.ko.nexmain.server.MissingU.common.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MimeHelperRegistrationNotifier implements RegistrationNotifier {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(Member member) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					true, "euc-kr");
			messageHelper.setSubject("[Attachment] 회원 가입 안내");
			String htmlContent = "<strong>안녕하세요</strong>, 반갑습니다.";
			messageHelper.setText(htmlContent, true);
			messageHelper.setFrom("madvirus@wemade.com", "최범균");
			messageHelper.setTo(new InternetAddress(member.getEmail(), member
					.getName(), "euc-kr"));

			DataSource dataSource = new FileDataSource("c:\\안내문.docx");
			messageHelper.addAttachment(MimeUtility.encodeText("안내문.docx",
					"euc-kr", "B"), dataSource);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		try {
			mailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

}
