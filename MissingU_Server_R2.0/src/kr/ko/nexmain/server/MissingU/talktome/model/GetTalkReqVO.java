package kr.ko.nexmain.server.MissingU.talktome.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class GetTalkReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	@NotNull
	private Integer talkId;
	/**
	 * @return the talkId
	 */
	public Integer getTalkId() {
		return talkId;
	}
	/**
	 * @param talkId the talkId to set
	 */
	public void setTalkId(Integer talkId) {
		this.talkId = talkId;
	}

}
