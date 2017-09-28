package kr.ko.nexmain.server.MissingU.chat.model;

import java.io.Serializable;

public class ChatRoom implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private String roomPw;

	private String roomTitle;

	private String roomDesc;

	private String roomMasterId;
	
	private Integer curUserCnt;
	
	private Integer maxUserCnt;
	
	private String createdDate;

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
	 * @return the roomPw
	 */
	public String getRoomPw() {
		return roomPw;
	}

	/**
	 * @param roomPw the roomPw to set
	 */
	public void setRoomPw(String roomPw) {
		this.roomPw = roomPw;
	}

	/**
	 * @return the roomTitle
	 */
	public String getRoomTitle() {
		return roomTitle;
	}

	/**
	 * @param roomTitle the roomTitle to set
	 */
	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	/**
	 * @return the roomDesc
	 */
	public String getRoomDesc() {
		return roomDesc;
	}

	/**
	 * @param roomDesc the roomDesc to set
	 */
	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	/**
	 * @return the roomMasterId
	 */
	public String getRoomMasterId() {
		return roomMasterId;
	}

	/**
	 * @param roomMasterId the roomMasterId to set
	 */
	public void setRoomMasterId(String roomMasterId) {
		this.roomMasterId = roomMasterId;
	}

	/**
	 * @return the curUserCnt
	 */
	public Integer getCurUserCnt() {
		return curUserCnt;
	}

	/**
	 * @param curUserCnt the curUserCnt to set
	 */
	public void setCurUserCnt(Integer curUserCnt) {
		this.curUserCnt = curUserCnt;
	}

	/**
	 * @return the maxUserCnt
	 */
	public Integer getMaxUserCnt() {
		return maxUserCnt;
	}

	/**
	 * @param maxUserCnt the maxUserCnt to set
	 */
	public void setMaxUserCnt(Integer maxUserCnt) {
		this.maxUserCnt = maxUserCnt;
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
