package kr.ko.nexmain.server.MissingU.msgbox.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class SendMsgReqVO extends CommReqVO {
	private static final long serialVersionUID = -5276660000549202167L;
	
	@NotNull
	private Integer receiverId;
	@NotEmpty
	private String msg;
	
	/**
	 * @return the receiverId
	 */
	public Integer getReceiverId() {
		return receiverId;
	}
	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
