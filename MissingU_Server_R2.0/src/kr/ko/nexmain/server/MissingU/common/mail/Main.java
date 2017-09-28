package kr.ko.nexmain.server.MissingU.common.mail;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Member member = new Member("madvirus", "�ֹ��", "madvirus@madvirus.net");
		SimpleRegistrationNotifier simpleNotifier = context.getBean(SimpleRegistrationNotifier.class);
		simpleNotifier.sendEmail(member);
		
		MimeRegistrationNotifier mimeNotifier = context.getBean(MimeRegistrationNotifier.class);
		mimeNotifier.sendEmail(member);
		
		MimeHelperRegistrationNotifier helperNotifier = context.getBean(MimeHelperRegistrationNotifier.class);
		helperNotifier.sendEmail(member);
		
		MimeInlineRegistrationNotifier inlineNotifier = context.getBean(MimeInlineRegistrationNotifier.class);
		inlineNotifier.sendEmail(member);
		context.close();
	}
}
