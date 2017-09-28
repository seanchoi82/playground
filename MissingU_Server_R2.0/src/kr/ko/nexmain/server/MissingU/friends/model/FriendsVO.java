package kr.ko.nexmain.server.MissingU.friends.model;

import java.io.Serializable;

public class FriendsVO implements Serializable {
	private static final long serialVersionUID = -6599350965341029847L;
	
	private Integer memberId;
	private Integer friendId;
	private String friendType;
	private String status;
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
	 * @return the friendId
	 */
	public Integer getFriendId() {
		return friendId;
	}
	/**
	 * @param friendId the friendId to set
	 */
	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}
	/**
	 * @return the friendType
	 */
	public String getFriendType() {
		return friendType;
	}
	/**
	 * @param friendType the friendType to set
	 */
	public void setFriendType(String friendType) {
		this.friendType = friendType;
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
