package kr.ko.nexmain.server.MissingU.chat.model;

import java.io.Serializable;

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roomId;

	private String roomPass;

	private String roomName;

	private String memberId;

	private String guest1Id;
	
	private String guest2Id;
	
	private String guest3Id;
	
	private String memberRegId;
	
	private String guest1RegId;
	
	private String guest2RegId;
	
	private String guest3RegId;
	
	private String curUser;
	
	private String maxUser;
	
	private String memo;
	
	private String regDate;

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
	 * @return the roomPass
	 */
	public String getRoomPass() {
		return roomPass;
	}

	/**
	 * @param roomPass the roomPass to set
	 */
	public void setRoomPass(String roomPass) {
		this.roomPass = roomPass;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	 * @return the guest1Id
	 */
	public String getGuest1Id() {
		return guest1Id;
	}

	/**
	 * @param guest1Id the guest1Id to set
	 */
	public void setGuest1Id(String guest1Id) {
		this.guest1Id = guest1Id;
	}

	/**
	 * @return the guest2Id
	 */
	public String getGuest2Id() {
		return guest2Id;
	}

	/**
	 * @param guest2Id the guest2Id to set
	 */
	public void setGuest2Id(String guest2Id) {
		this.guest2Id = guest2Id;
	}

	/**
	 * @return the guest3Id
	 */
	public String getGuest3Id() {
		return guest3Id;
	}

	/**
	 * @param guest3Id the guest3Id to set
	 */
	public void setGuest3Id(String guest3Id) {
		this.guest3Id = guest3Id;
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
	 * @return the guest1RegId
	 */
	public String getGuest1RegId() {
		return guest1RegId;
	}

	/**
	 * @param guest1RegId the guest1RegId to set
	 */
	public void setGuest1RegId(String guest1RegId) {
		this.guest1RegId = guest1RegId;
	}

	/**
	 * @return the guest2RegId
	 */
	public String getGuest2RegId() {
		return guest2RegId;
	}

	/**
	 * @param guest2RegId the guest2RegId to set
	 */
	public void setGuest2RegId(String guest2RegId) {
		this.guest2RegId = guest2RegId;
	}

	/**
	 * @return the guest3RegId
	 */
	public String getGuest3RegId() {
		return guest3RegId;
	}

	/**
	 * @param guest3RegId the guest3RegId to set
	 */
	public void setGuest3RegId(String guest3RegId) {
		this.guest3RegId = guest3RegId;
	}

	/**
	 * @return the curUser
	 */
	public String getCurUser() {
		return curUser;
	}

	/**
	 * @param curUser the curUser to set
	 */
	public void setCurUser(String curUser) {
		this.curUser = curUser;
	}

	/**
	 * @return the maxUser
	 */
	public String getMaxUser() {
		return maxUser;
	}

	/**
	 * @param maxUser the maxUser to set
	 */
	public void setMaxUser(String maxUser) {
		this.maxUser = maxUser;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	
}
