package kr.ko.nexmain.server.MissingU.membership.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String loginId;
	@NotEmpty
	private String loginPw;
	private String gcmRegId;
	private String etcHn;
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
	 * @return the gcmRegId
	 */
	public String getGcmRegId() {
		return gcmRegId;
	}
	/**
	 * @param gcmRegId the gcmRegId to set
	 */
	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}
	/**
	 * @return the etcHn
	 */
	public String getEtcHn() {
		return etcHn;
	}
	/**
	 * @param etcHn the etcHn to set
	 */
	public void setEtcHn(String etcHn) {
		this.etcHn = etcHn;
	}
	
	

}
