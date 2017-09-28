package kr.ko.nexmain.server.MissingU.common.mail;

import kr.ko.nexmain.server.MissingU.common.model.MailSendParam;

public interface MailNotifier {

	public void sendEmail(MailSendParam param);
}
