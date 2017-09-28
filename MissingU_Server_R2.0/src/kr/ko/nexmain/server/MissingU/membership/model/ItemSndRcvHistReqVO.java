package kr.ko.nexmain.server.MissingU.membership.model;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class ItemSndRcvHistReqVO extends CommReqVO {
	private static final long serialVersionUID = 2911429653340142620L;
	
	@NotEmpty
	private String itemGroup;

	/**
	 * @return the itemGroup
	 */
	public String getitemGroup() {
		return itemGroup;
	}

	/**
	 * @param itemGroup the itemGroup to set
	 */
	public void setitemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	
	

}
