package kr.ko.nexmain.server.MissingU.friends.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class SendGiftReqVO extends CommReqVO {
	private static final long serialVersionUID = -5430894900675190873L;
	
	@NotNull
	private Integer friendId;
	@NotEmpty
	private String	itemCd;
	
	private boolean gcmPass = false;
	private boolean pointPass = false;

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
	 * @return the itemCd
	 */
	public String getItemCd() {
		return itemCd;
	}

	/**
	 * @param itemCd the itemCd to set
	 */
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public boolean isGcmPass() {
		return gcmPass;
	}

	public void setGcmPass(boolean gcmPass) {
		this.gcmPass = gcmPass;
	}

	public boolean isPointPass() {
		return pointPass;
	}

	public void setPointPass(boolean pointPass) {
		this.pointPass = pointPass;
	}
	
	

}
