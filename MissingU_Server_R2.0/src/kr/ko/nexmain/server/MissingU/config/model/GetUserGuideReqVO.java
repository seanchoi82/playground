package kr.ko.nexmain.server.MissingU.config.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class GetUserGuideReqVO extends CommReqVO {
	private static final long serialVersionUID = 5798349415479950422L;
	
	@NotNull
	private String menuId;

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


}
