package kr.ko.nexmain.server.MissingU.theshop.model;

public class TStorePurchaseConfirmation {
	
	/**
	 * 구매시 iap 서버에서 발급한 unique한 키값
	 */
	private String txid;
	/**
	 * 부모상품아이디
	 */
	private String appid;
	/**
	 * 전자영수증 데이트
	 */
	private String signdata;
	
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSigndata() {
		return signdata;
	}
	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}
	
	
}
