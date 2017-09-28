package kr.ko.nexmain.server.MissingU.facematch.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class GetRankReqVO extends CommReqVO {
	private static final long serialVersionUID = -4564352524918571843L;
	
	@NotNull
	private String sex;

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}


}
