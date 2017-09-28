package kr.ko.nexmain.server.MissingU.facematch.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class UpdateResultReqVO extends CommReqVO {
	private static final long serialVersionUID = -8684067688345298503L;
	
	@NotNull
	private Long eventId;
	@NotEmpty
	private String candidates;
	@NotEmpty
	private String votedCounts;
	private Integer winnerMemberId;
	
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
	 * @return the candidates
	 */
	public String getCandidates() {
		return candidates;
	}
	/**
	 * @param candidates the candidates to set
	 */
	public void setCandidates(String candidates) {
		this.candidates = candidates;
	}
	/**
	 * @return the votedCounts
	 */
	public String getVotedCounts() {
		return votedCounts;
	}
	/**
	 * @param votedCounts the votedCounts to set
	 */
	public void setVotedCounts(String votedCounts) {
		this.votedCounts = votedCounts;
	}
	/**
	 * @return the winnerMemberId
	 */
	public Integer getWinnerMemberId() {
		return winnerMemberId;
	}
	/**
	 * @param winnerMemberId the winnerMemberId to set
	 */
	public void setWinnerMemberId(Integer winnerMemberId) {
		this.winnerMemberId = winnerMemberId;
	}




}
