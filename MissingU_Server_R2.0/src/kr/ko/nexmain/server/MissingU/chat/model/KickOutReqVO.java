package kr.ko.nexmain.server.MissingU.chat.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class KickOutReqVO extends CommReqVO {

	private static final long serialVersionUID = 6105797344422433423L;
	
	@NotNull
	private Integer roomId;
	@NotNull
	private Integer memberId;

	/**
	 * @return the roomId
	 */
	public Integer getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
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
	
}
