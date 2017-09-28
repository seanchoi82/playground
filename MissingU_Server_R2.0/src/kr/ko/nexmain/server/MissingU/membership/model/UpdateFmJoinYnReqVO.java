package kr.ko.nexmain.server.MissingU.membership.model;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class UpdateFmJoinYnReqVO extends CommReqVO {
	
	private static final long serialVersionUID = -5457239916212812718L;
	
	@NotEmpty
	private String fmJoinYn;

	/**
	 * @return the fmJoinYn
	 */
	public String getFmJoinYn() {
		return fmJoinYn;
	}

	/**
	 * @param fmJoinYn the fmJoinYn to set
	 */
	public void setFmJoinYn(String fmJoinYn) {
		this.fmJoinYn = fmJoinYn;
	}
	
	
}
