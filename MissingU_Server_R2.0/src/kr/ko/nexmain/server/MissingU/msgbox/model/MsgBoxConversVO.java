package kr.ko.nexmain.server.MissingU.msgbox.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MsgBoxConversVO extends CommReqVO 
{
	private static final long serialVersionUID = -4856053239078539076L;

	
	/** 상대 ID */
	private Integer targetMemberId = 0;

	public Integer getTargetMemberId() {
		return targetMemberId;
	}

	public void setTargetMemberId(Integer targetMemberId) {
		this.targetMemberId = targetMemberId;
	}
	
	
}
