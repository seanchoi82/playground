package kr.ko.nexmain.server.MissingU.chat.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class SendChatMsgReqVO extends CommReqVO {
	private static final long serialVersionUID = -8827020553952648844L;
	
	@NotNull
	private Integer roomId;
	@NotEmpty
	private String chatMsg;

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
	 * @return the chatMsg
	 */
	public String getChatMsg() {
		return chatMsg;
	}

	/**
	 * @param chatMsg the chatMsg to set
	 */
	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}
	
}
