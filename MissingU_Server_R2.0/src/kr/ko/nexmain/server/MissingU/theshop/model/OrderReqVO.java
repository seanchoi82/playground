package kr.ko.nexmain.server.MissingU.theshop.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String prodCode;

	/**
	 * @return the prodCode
	 */
	public String getProdCode() {
		return prodCode;
	}

	/**
	 * @param prodCode the prodCode to set
	 */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	
	
	


}
