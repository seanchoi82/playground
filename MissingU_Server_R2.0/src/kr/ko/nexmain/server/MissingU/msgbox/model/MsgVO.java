package kr.ko.nexmain.server.MissingU.msgbox.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MsgVO extends CommReqVO {
	private static final long serialVersionUID = -887744213999666782L;
	
	private Long msgId;
	private Integer senderId;
	private Integer receiverId;
	private String receiverReadYn;
	private String msgText;
	
	/**
	 * @return the msgId
	 */
	public Long getMsgId() {
		return msgId;
	}
	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
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
	 * @return the receiverId
	 */
	public Integer getReceiverId() {
		return receiverId;
	}
	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * @return the receiverReadYn
	 */
	public String getReceiverReadYn() {
		return receiverReadYn;
	}
	/**
	 * @param receiverReadYn the receiverReadYn to set
	 */
	public void setReceiverReadYn(String receiverReadYn) {
		this.receiverReadYn = receiverReadYn;
	}
	/**
	 * @return the msgText
	 */
	public String getMsgText() {
		return msgText;
	}
	/**
	 * @param msgText the msgText to set
	 */
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
}
