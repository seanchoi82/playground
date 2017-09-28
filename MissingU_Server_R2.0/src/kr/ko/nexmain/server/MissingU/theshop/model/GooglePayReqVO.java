package kr.ko.nexmain.server.MissingU.theshop.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class GooglePayReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String token;
	@NotEmpty
	private String prodCd;
	private String prodName;
	private String amount;
	@NotEmpty
	private String orderNum;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the prodCd
	 */
	public String getProdCd() {
		return prodCd;
	}
	/**
	 * @param prodCd the prodCd to set
	 */
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	/**
	 * @return the prodName
	 */
	public String getProdName() {
		return prodName;
	}
	/**
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	
	

}
