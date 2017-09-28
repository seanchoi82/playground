package kr.ko.nexmain.server.MissingU.msgbox.model;

import java.io.Serializable;

public class MsgBoxItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer senderId;

	private String gender;

	private String nick;

	private String sendDate;

	private String message;
	
	private Integer unreadCnt;
	
	private String photo;

	/**
	 * @return the senderId
	 */
	public Integer getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the sendDate
	 */
	public String getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the unreadCnt
	 */
	public Integer getUnreadCnt() {
		return unreadCnt;
	}

	/**
	 * @param unreadCnt the unreadCnt to set
	 */
	public void setUnreadCnt(Integer unreadCnt) {
		this.unreadCnt = unreadCnt;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}


	
}
