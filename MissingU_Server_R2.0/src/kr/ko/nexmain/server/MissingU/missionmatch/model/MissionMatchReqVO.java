package kr.ko.nexmain.server.MissingU.missionmatch.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

/**
 * @author 영환
 *
 */
public class MissionMatchReqVO extends CommReqVO {

	/** */
	private static final long serialVersionUID = 1L;

	/** 미션번호 */
	private Integer mId;
	/** 유형 (0=페이스매치, 1=미션매치) */
	@NotNull
	private Integer type;
	/** 32강, 16강 선택 */
	private int needRound = 16;
	/** 시드권자 */
	private int needSeedCount;
	/** 일반사용자 */
	private int needCount;
	/** 성별 */
	private String sex="";
	/** 승자 목록 */
	private List<String> winners;
	
	private List<String> participant;

	/** 미션번호 */
	public Integer getmId() {
		return mId;
	}

	/** 미션번호 */
	public void setmId(Integer mId) {
		this.mId = mId;
	}

	/** 유형 (0=페이스매치, 1=미션매치) */
	public int getType() {
		return type;
	}

	/** 유형 (0=페이스매치, 1=미션매치) */
	public void setType(int type) {
		this.type = type;
	}

	/** 32강, 16강 선택 */
	public int getNeedRound() {
		return needRound;
	}

	/** 32강, 16강 선택 */
	public void setNeedRound(int needRound) {
		this.needRound = needRound;
	}

	/** 성별 */
	public String getSex() {
		return sex;
	}

	/** 성별 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/** 승자 목록 */
	public List<String> getWinners() {
		return winners;
	}

	/** 승자 목록 */
	public void setWinners(List<String> winners) {
		this.winners = winners;
		
		if(this.winners != null) {
			this.needSeedCount = this.winners.size();
			this.needCount = this.needRound - this.needSeedCount;
		}
	}
	
	public int getNeedSeedCount() {
		return this.needSeedCount;
	}
	
	public int getNeedCount() {
		return this.needCount;
	}

	/**
	 * @return the participant
	 */
	public List<String> getParticipant() {
		return participant;
	}

	/**
	 * @param participant the participant to set
	 */
	public void setParticipant(List<String> participant) {
		this.participant = participant;
	}

	
	
}
