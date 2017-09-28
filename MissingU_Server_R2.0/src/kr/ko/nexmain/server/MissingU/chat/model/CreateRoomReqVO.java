package kr.ko.nexmain.server.MissingU.chat.model;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class CreateRoomReqVO extends CommReqVO {

	private static final long serialVersionUID = 7346063159276756221L;

	private Integer roomId;
	private String roomPw;
	@NotEmpty
	private String roomTitle;
	@NotEmpty
	private String roomDesc;
	private Integer memberId;
	private Integer roomMasterId;
	private Integer maxUserCnt;
	private String masterYn;
	
	
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
	 * @return the roomMasterId
	 */
	public Integer getRoomMasterId() {
		return roomMasterId;
	}
	/**
	 * @param roomMasterId the roomMasterId to set
	 */
	public void setRoomMasterId(Integer roomMasterId) {
		this.roomMasterId = roomMasterId;
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
