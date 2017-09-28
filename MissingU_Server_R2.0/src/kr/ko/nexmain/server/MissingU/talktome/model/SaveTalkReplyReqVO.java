package kr.ko.nexmain.server.MissingU.talktome.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class SaveTalkReplyReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	private Integer replyId;
	@NotNull
	private Integer talkId;
	@NotEmpty
	private String replyContent;
	private boolean useSavePoint = true;
	private Integer replayCnt;

	/**
	 * @return the replyId
	 */
	public Integer getReplyId() {
		return replyId;
	}

	/**
	 * @param replyId
	 *            the replyId to set
	 */
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	/**
	 * @return the talkId
	 */
	public Integer getTalkId() {
		return talkId;
	}

	/**
	 * @param talkId
	 *            the talkId to set
	 */
	public void setTalkId(Integer talkId) {
		this.talkId = talkId;
	}

	/**
	 * @return the replyContent
	 */
	public String getReplyContent() {
		return replyContent;
	}

	/**
	 * @param replyContent
	 *            the replyContent to set
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public boolean isUseSavePoint() {
		return useSavePoint;
	}

	public void setUseSavePoint(boolean useSavePoint) {
		this.useSavePoint = useSavePoint;
	}

	public Integer getReplayCnt() {
		return replayCnt;
	}

	public void setReplayCnt(Integer replayCnt) {
		this.replayCnt = replayCnt;
	}

	
}
