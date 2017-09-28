package kr.ko.nexmain.server.MissingU.membership.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class GcmUpdateReqVO extends CommReqVO {
	private static final long serialVersionUID = -6872111446301642141L;
	
	private String gcmUseYn;
	private String gcmRegId;
	
	/**
	 * @return the gcmUseYn
	 */
	public String getGcmUseYn() {
		return gcmUseYn;
	}
	/**
	 * @param gcmUseYn the gcmUseYn to set
	 */
	public void setGcmUseYn(String gcmUseYn) {
		this.gcmUseYn = gcmUseYn;
	}
	/**
	 * @return the gcmRegId
	 */
	public String getGcmRegId() {
		return gcmRegId;
	}
	/**
	 * @param gcmRegId the gcmRegId to set
	 */
	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}
	
	

}
