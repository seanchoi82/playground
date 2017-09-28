package kr.ko.nexmain.server.MissingU.harmony.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class DetailInfoReqVO extends CommReqVO {
	
	private static final long serialVersionUID = 1054479813232109244L;
	
	@NotEmpty
	private String harmonyType;
	@NotEmpty
	private String keyValue;
	
	/**
	 * @return the harmonyType
	 */
	public String getHarmonyType() {
		return harmonyType;
	}
	/**
	 * @param harmonyType the harmonyType to set
	 */
	public void setHarmonyType(String harmonyType) {
		this.harmonyType = harmonyType;
	}
	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}
	/**
	 * @param keyValue the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	


}
