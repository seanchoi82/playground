package kr.ko.nexmain.server.MissingU.missionmatch.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

/**
 * @author 영환
 *
 */
public class MissionMatchRankReqVO extends CommReqVO {

	/** */
	private static final long serialVersionUID = 1L;

	/** 미션번호 */
	private Integer mId;
	/** 유형 (0=페이스매치, 1=미션매치) */
	@NotNull
	private Integer type;
	/** 검색 유형 (A = 전체순위, M 월간 랭킹*/
	private String searchType;
	/*** 승자회원 아이디 **/
	private Integer targetMemberId;
	
	private String sex;

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

	/** 유형 (0=페이스매치, 1=미션매치) */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/** 검색 유형 (A = 전체순위, M 월간 랭킹*/
	public String getSearchType() {
		return searchType;
	}

	/** 검색 유형 (A = 전체순위, M 월간 랭킹*/
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/*** 승자회원 아이디 **/
	public Integer getTargetMemberId() {
		return targetMemberId;
	}

	/*** 승자회원 아이디 **/
	public void setTargetMemberId(Integer targetMemberId) {
		this.targetMemberId = targetMemberId;
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
	
	
}
