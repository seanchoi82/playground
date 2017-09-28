package kr.ko.nexmain.server.MissingU.admin.model;

public class MsgDeleteMemberPack {

	private String senderId;
	private String receiverId;
	public String getSenderId() {
		return senderId;
	}
	public MsgDeleteMemberPack setSenderId(String senderId) {
		this.senderId = senderId;
		return this;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public MsgDeleteMemberPack setReceiverId(String receiverId) {
		this.receiverId = receiverId;
		return this;
	}
	
	

}
