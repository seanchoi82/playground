package kr.ko.nexmain.server.MissingU.theshop.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class BuyItemReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String itemCode;

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	
	


}
