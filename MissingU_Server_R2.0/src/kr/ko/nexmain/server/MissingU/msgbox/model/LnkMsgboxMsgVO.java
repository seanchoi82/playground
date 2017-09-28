package kr.ko.nexmain.server.MissingU.msgbox.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class LnkMsgboxMsgVO extends CommReqVO {
	private static final long serialVersionUID = -2626487624767829993L;
	
	private Long msgId;
	private Long msgboxId;
	
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
	
}
