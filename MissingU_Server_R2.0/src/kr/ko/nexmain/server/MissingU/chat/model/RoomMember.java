package kr.ko.nexmain.server.MissingU.chat.model;

import java.io.Serializable;

public class RoomMember implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roomId;

	private String memberId;
	
	private String memberRegId;
	
	private String masterYn;
	
	private String createdDate;

	/**
	 * @return the roomId
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	/**
	 * @return the memberRegId
	 */
	public String getMemberRegId() {
		return memberRegId;
	}

	/**
	 * @param memberRegId the memberRegId to set
	 */
	public void setMemberRegId(String memberRegId) {
		this.memberRegId = memberRegId;
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

	



	
}
