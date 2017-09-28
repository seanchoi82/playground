package kr.ko.nexmain.server.MissingU.common.mail;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.MailSendParam;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MuMailNotifier implements MailNotifier {
	protected static Logger log = LoggerFactory.getLogger(MuMailNotifier.class);
	
	private JavaMailSender mailSender;
	private MsgUtil msgUtil;
	
	@Value("#{config['missingu.server.baseUrl']}")
	private String MISSINGU_SERVER_BASEURL;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setMsgUtil(MsgUtil msgUtil) {
		this.msgUtil = msgUtil;
	}

	@Override
	public void sendEmail(MailSendParam param) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,
					true, "utf-8");
			String emailtype = param.getEmailType();
			Locale gLocale = new Locale(param.getLang());
			
			//메일 제목설정
			if(Constants.emailType.MEMBERSHIP_JOIN_CONFIRM.equals(emailtype)) {
				messageHelper.setSubject(msgUtil.getPropMsg("mail.membership.title", gLocale));
			} else if(Constants.emailType.TEMP_PASSWORD.equals(emailtype)) {
				messageHelper.setSubject(msgUtil.getPropMsg("mail.pass.title", gLocale));
			} else {
				log.info("Unknown emailType");
				return;
			}
			
			//메일 내용설정
			String htmlContent = "";
			if(emailtype == null || StringUtils.isEmpty(emailtype)) {
				log.info("EmailType is null!");
				return;
			} else {
				htmlContent = getMailContent(param);
			}
			
			
			
			messageHelper.setText(htmlContent, true);
			messageHelper.setFrom("applus2013@gmail.com", "MissingU");
			messageHelper.setTo(new InternetAddress(param.getLoginId(), param.getName(), "utf-8"));
			//messageHelper.addInline("missinguTitleImg", new FileDataSource("/opt/lampp/tomcat60/files/img/missing_u.png"));
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
	
	/**
	 * 요청 emailType 에 따라 emailContent 리턴
	 * @param param
	 * @return
	 */
	public String getMailContent(MailSendParam param) {
		StringBuilder sbHtmlContent = new StringBuilder();
		String emailtype = param.getEmailType();
		Locale gLocale = new Locale(param.getLang());
		
		String OPENING_PART1			= msgUtil.getPropMsg("mail.membership.opening.01", gLocale);
		String OPENING_PART2			= msgUtil.getPropMsg("mail.membership.opening.02", gLocale);
		String CLOSING_PART1			= msgUtil.getPropMsg("mail.membership.closing.01", gLocale);
		String CLOSING_PART2			= msgUtil.getPropMsg("mail.membership.closing.02", gLocale);
		String REVIEW_BTN_TITLE			= msgUtil.getPropMsg("mail.membership.review.btn.title", gLocale);
		String REVIEW_BTN_TEXT			= msgUtil.getPropMsg("mail.membership.review.btn.text", gLocale);
		String FOOTER_TEXT_01			= msgUtil.getPropMsg("mail.footer.01", gLocale);
		String FOOTER_TEXT_02			= msgUtil.getPropMsg("mail.footer.02", gLocale);
		
		String REPLY_MAIL_ADDR			= "appplus2013@gmail.com"; //회신 주소
		
		
		if(Constants.emailType.MEMBERSHIP_JOIN_CONFIRM.equals(emailtype)) {
			String MEMBERSHIP_GREETING		= msgUtil.getPropMsg("mail.membership.greeting", gLocale);
			String MEMBERSHIP_TEXT1_01	= msgUtil.getPropMsg("mail.membership.text1.01", gLocale);
			String MEMBERSHIP_TEXT1_02	= msgUtil.getPropMsg("mail.membership.text1.02", gLocale);
			String MEMBERSHIP_TEXT1_03	= msgUtil.getPropMsg("mail.membership.text1.03", gLocale);
			String MEMBERSHIP_TEXT2_01 = null;
			if("ko".equalsIgnoreCase(param.getLang())) {
				MEMBERSHIP_TEXT2_01	= msgUtil.getPropMsg("mail.membership.text2.01", gLocale);
			}
			
			StringBuilder sbURI = new StringBuilder();
			sbURI.append(MISSINGU_SERVER_BASEURL);
			sbURI.append("/missingu/common/memberJoinCert.html");
			sbURI.append("?gMemberId=").append(param.getMemberId());
			sbURI.append("&gLang=").append(param.getLang());
			String memberJoinCertReqURI = sbURI.toString();
			
			/** 회원가입 완료 메일 */
			sbHtmlContent.append("<img src='http://missingu.co.kr/img/mail/missing_u.png' alt='MissingU' style='background:#f0f0f0; width:112px; height:32px'/>");
			sbHtmlContent.append("<hr /> \n");
			sbHtmlContent.append("<table border='0' cellpadding='0' cellspacing='0' width='600' align='center'> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 120px;'><img src='http://missingu.co.kr/img/mail/missing_u_logo.png' alt='MissingU'  style='background:#f0f0f0; width:76px; height:76px'/></td> \n");
			sbHtmlContent.append("		<td> \n");
			sbHtmlContent.append("			<h3 style='font-size: 18px; margin: 0; padding: 0;'> \n");
			sbHtmlContent.append("				").append(OPENING_PART1).append(" <br />").append(OPENING_PART2).append(" \n");
			sbHtmlContent.append("			</h3> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 120px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td> \n");
			sbHtmlContent.append("			<h1 style='font-size: 50px; margin: 0; padding: 0;'>Thank You!</h1> \n");
			sbHtmlContent.append("			<h3 style='font-size: 18px; margin: 0; padding: 0;'>").append(MEMBERSHIP_GREETING).append("</h3> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("</table> \n");
			sbHtmlContent.append(" \n");
			sbHtmlContent.append("<table border='0' cellpadding='0' cellspacing='0' width='600' align='center'> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='height: 50px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td style='width: 190px; height: 50px;'>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td colspan='2'>").append(MEMBERSHIP_TEXT1_01).append(" <span style='color: #5297d8;'><strong>").append(param.getLoginId()).append("</strong></span> \n");
			sbHtmlContent.append("			").append(MEMBERSHIP_TEXT1_02).append(" \n");
			sbHtmlContent.append("			<a href='").append(memberJoinCertReqURI).append("' style='color:blue; text-decoration: underline;'><strong>&lt;Click Here!&gt;</strong></a> <span style='color:red'>").append(MEMBERSHIP_TEXT1_03).append("</span> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px; height: 10px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td style='width: 190px; '>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			
			if("ko".equalsIgnoreCase(param.getLang())) {
				//한국어인 경우만 출력하는 부분
				sbHtmlContent.append("	<tr> \n");
				sbHtmlContent.append("		<td style='width: 30px;'>&nbsp;</td> \n");
				sbHtmlContent.append("		<td colspan='2'><h4>").append(MEMBERSHIP_TEXT2_01).append("</h4></td> \n");
				sbHtmlContent.append("	</tr> \n");
			}
			
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px; height: 10px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td style='width: 190px; '>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>").append(CLOSING_PART1).append("<br />").append(CLOSING_PART2).append("</td> \n");
			sbHtmlContent.append("		<td style='width: 190px;'> \n");
			sbHtmlContent.append("		<button style='border-radius:5px; text-align:left; padding:8px 12px; color:#293029; border:solid 1px #9c9694; background-image: linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -o-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -moz-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -webkit-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -ms-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(156,166,165)), color-stop(1, rgb(206,199,198)));'> \n");
			sbHtmlContent.append("		<p style='margin:0; padding:0;'><img src='http://missingu.co.kr/img/mail/phone_icon.png' align='left' style='padding:0 10px 0 0; width:18px; height:29px'/> ").append(REVIEW_BTN_TITLE).append(" &nbsp;&nbsp;<br /><span style='font-size:11px;color:#555;'>").append(REVIEW_BTN_TEXT).append("</span></p> \n");
			sbHtmlContent.append("		</button></td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("</table> \n");
			sbHtmlContent.append(" \n");
			sbHtmlContent.append("<hr /> \n");
			sbHtmlContent.append("<p>").append(FOOTER_TEXT_01).append(" <a href='mailto:").append(REPLY_MAIL_ADDR).append("'>").append(REPLY_MAIL_ADDR).append("</a> ").append(FOOTER_TEXT_02).append(" ⓒ2013 App+</p> \n");
			
		} else if(Constants.emailType.TEMP_PASSWORD.equals(emailtype)) {
			String PASS_GREETING			= msgUtil.getPropMsg("mail.pass.greeting", gLocale);
			String PASS_TEXT1_01			= msgUtil.getPropMsg("mail.pass.text1.01", gLocale);
			String PASS_TEXT1_02			= msgUtil.getPropMsg("mail.pass.text1.02", gLocale);
			String PASS_TEXT1_03			= msgUtil.getPropMsg("mail.pass.text1.03", gLocale);
			String PASS_TEXT2_01			= msgUtil.getPropMsg("mail.pass.text2.01", gLocale);
			String PASS_TEXT2_02			= msgUtil.getPropMsg("mail.pass.text2.02", gLocale);
			
			/** 임시 패스워드 발급 메일 */
			sbHtmlContent.append("<img src='http://missingu.co.kr/img/mail/missing_u.png' alt='MissingU' style='background:#f0f0f0; width:112px; height:32px'/>");
			sbHtmlContent.append("<hr /> \n");
			sbHtmlContent.append("<table border='0' cellpadding='0' cellspacing='0' width='600' align='center'> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 120px;'><img src='http://missingu.co.kr/img/mail/missing_u_logo.png' alt='MissingU'  style='background:#f0f0f0; width:76px; height:76px'/></td> \n");
			sbHtmlContent.append("		<td> \n");
			sbHtmlContent.append("			<h3 style='font-size: 18px; margin: 0; padding: 0;'> \n");
			sbHtmlContent.append("				").append(OPENING_PART1).append(" <br />").append(OPENING_PART2).append(" \n");
			sbHtmlContent.append("			</h3> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 120px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td> \n");
			sbHtmlContent.append("			<h3 style='font-size: 18px; margin: 0; padding: 0;'>").append(PASS_GREETING).append("</h3> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("</table> \n");
			sbHtmlContent.append(" \n");
			sbHtmlContent.append("<table border='0' cellpadding='0' cellspacing='0' width='600' align='center'> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='height: 20px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td style='width: 190px; height: 20px;'>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td colspan='2'> \n");
			sbHtmlContent.append("			<p>  <span style='color: #5297d8;'><strong>").append(param.getName()).append("</strong></span>").append(PASS_TEXT1_01).append("<br />").append(PASS_TEXT1_02).append("  <span style='color: #5297d8;'><strong>").append(param.getLoginPw()).append("</strong></span>").append(PASS_TEXT1_03).append("</p> \n");
			sbHtmlContent.append(" \n");
			sbHtmlContent.append("			<p> \n");
			sbHtmlContent.append("			").append(PASS_TEXT2_01).append("<br /> \n");
			sbHtmlContent.append("			").append(PASS_TEXT2_02).append(" \n");
			sbHtmlContent.append("			</p> \n");
			sbHtmlContent.append("		</td> \n");
			sbHtmlContent.append("	</tr> \n");
			
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px; height: 10px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>&nbsp;</td> \n");
			sbHtmlContent.append("		<td style='width: 190px; '>&nbsp;</td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("	<tr> \n");
			sbHtmlContent.append("		<td style='width: 30px;'>&nbsp;</td> \n");
			sbHtmlContent.append("		<td>").append(CLOSING_PART1).append("<br />").append(CLOSING_PART2).append("</td> \n");
			sbHtmlContent.append("		<td style='width: 190px;'> \n");
			sbHtmlContent.append("		<button style='border-radius:5px; text-align:left; padding:8px 12px; color:#293029; border:solid 1px #9c9694; background-image: linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -o-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -moz-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -webkit-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -ms-linear-gradient(bottom, rgb(156,166,165) 0%, rgb(206,199,198) 100%); background-image: -webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(156,166,165)), color-stop(1, rgb(206,199,198)));'> \n");
			sbHtmlContent.append("		<p style='margin:0; padding:0;'><img src='http://missingu.co.kr/img/mail/phone_icon.png' align='left' style='padding:0 10px 0 0; width:18px; height:29px'/> ").append(REVIEW_BTN_TITLE).append(" &nbsp;&nbsp;<br /><span style='font-size:11px;color:#555;'>").append(REVIEW_BTN_TEXT).append("</span></p> \n");
			sbHtmlContent.append("		</button></td> \n");
			sbHtmlContent.append("	</tr> \n");
			sbHtmlContent.append("</table> \n");
			sbHtmlContent.append(" \n");
			sbHtmlContent.append("<hr /> \n");
			sbHtmlContent.append("<p>").append(FOOTER_TEXT_01).append(" <a href='mailto:").append(REPLY_MAIL_ADDR).append("'>").append(REPLY_MAIL_ADDR).append("</a> ").append(FOOTER_TEXT_02).append(" ⓒ2013 App+</p> \n");
		} else {
			log.info("Unknown emailType!");
		}
		
		return sbHtmlContent.toString();
	}

}




