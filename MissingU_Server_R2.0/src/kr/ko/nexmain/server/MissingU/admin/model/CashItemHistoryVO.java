package kr.ko.nexmain.server.MissingU.admin.model;

public class CashItemHistoryVO {
	
	private String id = "";
	private String senderMemberId = ""; 
	private String senderSex = ""; 
	private String senderMainPhotoUrl = ""; 
	private String senderNickName = ""; 
	private String senderCountry = ""; 
	private String senderLang = ""; 

	private String receiverMemberId = ""; 
	private String receiverSex = ""; 
	private String receiverMainPhotoUrl = ""; 
	private String receiverNickName = ""; 
	private String receiverCountry = ""; 
	private String receiverLlang = "";

	private String itemAmount = "";
	private String createdDate = ""; 
	private String receiverReadYn = ""; 
	private String receiverReadDate = "";
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSenderMemberId() {
		return senderMemberId;
	}
	public void setSenderMemberId(String senderMemberId) {
		this.senderMemberId = senderMemberId;
	}
	public String getSenderSex() {
		return senderSex;
	}
	public void setSenderSex(String senderSex) {
		this.senderSex = senderSex;
	}
	public String getSenderMainPhotoUrl() {
		return senderMainPhotoUrl;
	}
	public void setSenderMainPhotoUrl(String senderMainPhotoUrl) {
		this.senderMainPhotoUrl = senderMainPhotoUrl;
	}
	public String getSenderNickName() {
		return senderNickName;
	}
	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}
	public String getSenderCountry() {
		return senderCountry;
	}
	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry;
	}
	public String getSenderLang() {
		return senderLang;
	}
	public void setSenderLang(String senderLang) {
		this.senderLang = senderLang;
	}
	public String getReceiverMemberId() {
		return receiverMemberId;
	}
	public void setReceiverMemberId(String receiverMemberId) {
		this.receiverMemberId = receiverMemberId;
	}
	public String getReceiverSex() {
		return receiverSex;
	}
	public void setReceiverSex(String receiverSex) {
		this.receiverSex = receiverSex;
	}
	public String getReceiverMainPhotoUrl() {
		return receiverMainPhotoUrl;
	}
	public void setReceiverMainPhotoUrl(String receiverMainPhotoUrl) {
		this.receiverMainPhotoUrl = receiverMainPhotoUrl;
	}
	public String getReceiverNickName() {
		return receiverNickName;
	}
	public void setReceiverNickName(String receiverNickName) {
		this.receiverNickName = receiverNickName;
	}
	public String getReceiverCountry() {
		return receiverCountry;
	}
	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}
	public String getReceiverLlang() {
		return receiverLlang;
	}
	public void setReceiverLlang(String receiverLlang) {
		this.receiverLlang = receiverLlang;
	}
	public String getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(String itemAmount) {
		this.itemAmount = itemAmount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getReceiverReadYn() {
		return receiverReadYn;
	}
	public void setReceiverReadYn(String receiverReadYn) {
		this.receiverReadYn = receiverReadYn;
	}
	public String getReceiverReadDate() {
		return receiverReadDate;
	}
	public void setReceiverReadDate(String receiverReadDate) {
		this.receiverReadDate = receiverReadDate;
	}
}
