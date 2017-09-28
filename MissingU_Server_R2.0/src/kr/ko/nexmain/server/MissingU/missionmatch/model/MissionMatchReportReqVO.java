package kr.ko.nexmain.server.MissingU.missionmatch.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MissionMatchReportReqVO extends CommReqVO {
	
	private static final long serialVersionUID = 1L;

	/** 미션참여번호 */
	private Integer mJId;
	/** 미션번호 */
	private Integer mId;
	/** 유형 (0=페이스매치, 1=미션매치) */
	@NotNull
	private Integer type;
	/** 승리 대상 아이디 */
	@NotNull
	private Integer targetMemberId;
	
	
	/**
	 * @return the mJId
	 */
	public Integer getmJId() {
		return mJId;
	}
	/**
	 * @param mJId the mJId to set
	 */
	public void setmJId(Integer mJId) {
		this.mJId = mJId;
	}
	/**
	 * @return the mId
	 */
	public Integer getmId() {
		return mId;
	}
	/**
	 * @param mId the mId to set
	 */
	public void setmId(Integer mId) {
		this.mId = mId;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the targetMemberId
	 */
	public Integer getTargetMemberId() {
		return targetMemberId;
	}
	/**
	 * @param targetMemberId the targetMemberId to set
	 */
	public void setTargetMemberId(Integer targetMemberId) {
		this.targetMemberId = targetMemberId;
	}
}
