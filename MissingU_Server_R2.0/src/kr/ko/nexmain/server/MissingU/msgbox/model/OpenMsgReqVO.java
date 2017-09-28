package kr.ko.nexmain.server.MissingU.msgbox.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class OpenMsgReqVO extends CommReqVO {
	private static final long serialVersionUID = 5808977394753361276L;
	
	@NotNull
	private Long msgId;

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

	
}
