package kr.ko.nexmain.server.MissingU.common.model;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rsltCd;
	
	private String rsltMsg;
	
	private String rsltMsgCd;
	
	public Result(String rsltCd, String rsltMsgCd, String rsltMsg) {
		this.rsltCd = rsltCd;
		this.rsltMsg = rsltMsg;
		this.rsltMsgCd = rsltMsgCd;
	}

	/**
	 * @return the rsltCd
	 */
	public String getRsltCd() {
		return rsltCd;
	}

	/**
	 * @param rsltCd the rsltCd to set
	 */
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	/**
	 * @return the rsltMsg
	 */
	public String getRsltMsg() {
		return rsltMsg;
	}

	/**
	 * @param rsltMsg the rsltMsg to set
	 */
	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	/**
	 * @return the rsltMsgCd
	 */
	public String getRsltMsgCd() {
		return rsltMsgCd;
	}

	/**
	 * @param rsltMsgCd the rsltMsgCd to set
	 */
	public void setRsltMsgCd(String rsltMsgCd) {
		this.rsltMsgCd = rsltMsgCd;
	}

}
