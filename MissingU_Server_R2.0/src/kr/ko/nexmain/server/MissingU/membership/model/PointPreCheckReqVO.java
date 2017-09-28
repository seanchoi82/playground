package kr.ko.nexmain.server.MissingU.membership.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class PointPreCheckReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String requestURI;
	private String msgId;
	private String roomId;

	/**
	 * @return the requestURI
	 */
	public String getRequestURI() {
		return requestURI;
	}

	/**
	 * @param requestURI the requestURI to set
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	

	
}
