package kr.ko.nexmain.server.MissingU.theshop.model;

public class TStoreProduct {
	/**
	 * 구매이력 시간
	 */
	String log_time;
	/**
	 * 구매상품id(모상품id)
	 */
	String appid;
	/**
	 * 구매상품부분id
	 */
	String product_id;
	/**
	 * 구매상품금액
	 */
	int charge_amount;
	/**
	 * 구매요청시 app 개발사에서 발급한 아이디(missingu 에서 발급함)
	 */
	String tid;
	/**
	 * 상품 상세정보(구매요청시 넘겨준값)
	 */
	String detail_pname;
	/**
	 * BP사 정보(판매회원 개발 서버가 구매정보 수신시에 전달 받고자 하는 값
	 */
	String bp_info;
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getCharge_amount() {
		return charge_amount;
	}
	public void setCharge_amount(int charge_amount) {
		this.charge_amount = charge_amount;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getDetail_pname() {
		return detail_pname;
	}
	public void setDetail_pname(String detail_pname) {
		this.detail_pname = detail_pname;
	}
	public String getBp_info() {
		return bp_info;
	}
	public void setBp_info(String bp_info) {
		this.bp_info = bp_info;
	}
	
	
}
