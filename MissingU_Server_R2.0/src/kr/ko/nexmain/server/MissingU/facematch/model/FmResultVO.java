package kr.ko.nexmain.server.MissingU.facematch.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class FmResultVO extends CommReqVO {
	private static final long serialVersionUID = 1555681541092417343L;
	
	private Long eventId;
	private Integer memberId;
	private String sex;
	private Integer votedCnt;
	private Integer joinCnt;
	private Integer winCnt;
	private Integer preferMemberId;
	
	/**
	 * @return the eventId
	 */
	public Long getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return the memberId
	 */
	public Integer getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
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
	/**
	 * @return the votedCnt
	 */
	public Integer getVotedCnt() {
		return votedCnt;
	}
	/**
	 * @param votedCnt the votedCnt to set
	 */
	public void setVotedCnt(Integer votedCnt) {
		this.votedCnt = votedCnt;
	}
	/**
	 * @return the preferMemberId
	 */
	public Integer getPreferMemberId() {
		return preferMemberId;
	}
	/**
	 * @param preferMemberId the preferMemberId to set
	 */
	public void setPreferMemberId(Integer preferMemberId) {
		this.preferMemberId = preferMemberId;
	}
	/**
	 * @return the joinCnt
	 */
	public Integer getJoinCnt() {
		return joinCnt;
	}
	/**
	 * @param joinCnt the joinCnt to set
	 */
	public void setJoinCnt(Integer joinCnt) {
		this.joinCnt = joinCnt;
	}
	/**
	 * @return the winCnt
	 */
	public Integer getWinCnt() {
		return winCnt;
	}
	/**
	 * @param winCnt the winCnt to set
	 */
	public void setWinCnt(Integer winCnt) {
		this.winCnt = winCnt;
	}
	
}
