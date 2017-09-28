package kr.ko.nexmain.server.MissingU.common.model;

public class MailSendParam {

	private Integer memberId;
	private String loginId;
	private String loginPw;
	private String name;
	private String email;
	private String emailType;
	private String lang;


	public MailSendParam() {
	}

	public MailSendParam(Integer memberId, String loginId, String name) {
		this.memberId = memberId;
		this.loginId = loginId;
		this.name = name;
	}

	/**
	 * @return the memberId
	 */
	public Integer getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the loginPw
	 */
	public String getLoginPw() {
		return loginPw;
	}

	/**
	 * @param loginPw the loginPw to set
	 */
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailType
	 */
	public String getEmailType() {
		return emailType;
	}

	/**
	 * @param emailType the emailType to set
	 */
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

	

}
