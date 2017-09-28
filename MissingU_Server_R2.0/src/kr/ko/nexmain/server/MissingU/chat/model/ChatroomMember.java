package kr.ko.nexmain.server.MissingU.chat.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class ChatroomMember extends CommReqVO {

	private static final long serialVersionUID = 6007920405466374732L;

	private Integer roomId;

	private Integer memberId;
	
	private String gcmRegId;
	
	private String masterYn;
	
	private String createdDate;
	
	private String status;

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

	/**
	 * @return the gcmRegId
	 */
	public String getGcmRegId() {
		return gcmRegId;
	}

	/**
	 * @param gcmRegId the gcmRegId to set
	 */
	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	/**
	 * @return the masterYn
	 */
	public String getMasterYn() {
		return masterYn;
	}

	/**
	 * @param masterYn the masterYn to set
	 */
	public void setMasterYn(String masterYn) {
		this.masterYn = masterYn;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
}
