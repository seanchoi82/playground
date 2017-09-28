package kr.ko.nexmain.server.MissingU.theshop.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class BeforeGooglePayReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	private String orderNum;

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
