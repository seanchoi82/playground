package kr.ko.nexmain.server.MissingU.config.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class GetNoticeReqVO extends CommReqVO {
	private static final long serialVersionUID = 5143059798821187727L;
	
	@NotNull
	private Integer noticeId;

	/**
	 * @return the noticeId
	 */
	public Integer getNoticeId() {
		return noticeId;
	}

	/**
	 * @param noticeId the noticeId to set
	 */
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	

}
