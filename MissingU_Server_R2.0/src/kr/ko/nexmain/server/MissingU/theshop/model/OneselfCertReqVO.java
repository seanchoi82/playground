package kr.ko.nexmain.server.MissingU.theshop.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class OneselfCertReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;

//	@NotEmpty
	private String juminIdPrefix;
	
//	@NotEmpty
	private String juminIdSuffix;
	
	private String authType;

	/**
	 * @return the juminIdPrefix
	 */
	public String getJuminIdPrefix() {
		return juminIdPrefix;
	}

	/**
	 * @param juminIdPrefix the juminIdPrefix to set
	 */
	public void setJuminIdPrefix(String juminIdPrefix) {
		this.juminIdPrefix = juminIdPrefix;
	}

	/**
	 * @return the juminIdSuffix
	 */
	public String getJuminIdSuffix() {
		return juminIdSuffix;
	}

	/**
	 * @param juminIdSuffix the juminIdSuffix to set
	 */
	public void setJuminIdSuffix(String juminIdSuffix) {
		this.juminIdSuffix = juminIdSuffix;
	}

	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return authType;
	}

	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	


}
