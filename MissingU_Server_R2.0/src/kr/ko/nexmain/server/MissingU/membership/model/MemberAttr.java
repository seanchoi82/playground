package kr.ko.nexmain.server.MissingU.membership.model;

import java.io.Serializable;

public class MemberAttr implements Serializable {

	private static final long serialVersionUID = 5119565290611513512L;
	
	private Integer memberId;
	private String attrName;
	private String attrValue;
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
	 * @return the attrName
	 */
	public String getAttrName() {
		return attrName;
	}
	/**
	 * @param attrName the attrName to set
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	/**
	 * @return the attrValue
	 */
	public String getAttrValue() {
		return attrValue;
	}
	/**
	 * @param attrValue the attrValue to set
	 */
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	
	

}
