package kr.ko.nexmain.server.MissingU.msgbox.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MsgListReqVO extends CommReqVO {
	private static final long serialVersionUID = -3968261506745833349L;
	
	@NotNull
	private Long msgboxId;

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
