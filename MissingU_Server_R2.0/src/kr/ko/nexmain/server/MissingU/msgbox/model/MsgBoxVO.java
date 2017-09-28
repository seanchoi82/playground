package kr.ko.nexmain.server.MissingU.msgbox.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MsgBoxVO extends CommReqVO {
	private static final long serialVersionUID = -887744213999666782L;
	
	private Long msgboxId;
	private Integer memberId;
	private Integer senderId;
	private String status;
	
	/**
	 * @return the msgboxId
	 */
	public Long getMsgboxId() {
		return msgboxId;
	}
	/**
	 * @param msgboxId the msgboxId to set
	 */
	public void setMsgboxId(Long msgboxId) {
		this.msgboxId = msgboxId;
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
	 * @return the senderId
	 */
	public Integer getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
