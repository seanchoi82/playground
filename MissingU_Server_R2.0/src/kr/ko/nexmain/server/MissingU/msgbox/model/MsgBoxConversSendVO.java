package kr.ko.nexmain.server.MissingU.msgbox.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MsgBoxConversSendVO extends CommReqVO 
{
	private static final long serialVersionUID = -4856053239078539076L;

	private long msgId;
		
	/** 상대 ID */
	@NotNull
	private Integer targetMemberId;
	
	@NotEmpty
	private String msgText;
	
	
	

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public Integer getTargetMemberId() {
		return targetMemberId; 
	}

	public void setTargetMemberId(Integer targetMemberId) {
		this.targetMemberId = targetMemberId;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	
	
	
}
