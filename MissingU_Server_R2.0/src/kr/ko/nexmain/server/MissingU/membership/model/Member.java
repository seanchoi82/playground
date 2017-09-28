package kr.ko.nexmain.server.MissingU.membership.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommonBean;

import org.hibernate.validator.constraints.NotEmpty;

public class Member extends CommonBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String loginId;
	private String loginPw;
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


}
