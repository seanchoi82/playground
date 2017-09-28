package kr.ko.nexmain.server.MissingU.missionmatch.model;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class MissionMatchJoinReqVO extends CommReqVO {
	
	private static final long serialVersionUID = 1L;

	/** 미션 참여 번호 */
	private Integer mJId;
	/** 미션번호 */
	private Integer mId;
	/** 유형 (0=페이스매치, 1=미션매치) */
	@NotNull
	private Integer type;
	
	/** 등록 코멘트 */
	private String comment;
	/** 업로드 파일 */
	private MultipartFile saveFile;
	
	/** 저장되는 파일명 */
	private String uploadfile;
	/** 저장되는 파일명 큰이미지 */
	private String uploadfileBig;
	/** 저장되는 파일명 원본파일 */
	private String uploadfileOrg;
	
	private String vote;
	private String viewCnt;
	private String voteByMonth;
	private String battleJoinCnt;
	
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
	/** 미션번호 */
	public Integer getmId() {
		return mId;
	}
	/** 미션번호 */
	public void setmId(Integer mId) {
		this.mId = mId;
	}
	/** 유형 (0=페이스매치, 1=미션매치) */
	public Integer getType() {
		return type;
	}
	/** 유형 (0=페이스매치, 1=미션매치) */
	public void setType(Integer type) {
		this.type = type;
	}
	/** 등록 코멘트 */
	public String getComment() {
		return comment;
	}
	/** 등록 코멘트 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the saveFile
	 */
	public MultipartFile getSaveFile() {
		return saveFile;
	}
	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(MultipartFile saveFile) {
		this.saveFile = saveFile;
	}
	/**
	 * @return the uploadfile
	 */
	public String getUploadfile() {
		return uploadfile;
	}
	/**
	 * @param uploadfile the uploadfile to set
	 */
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}
	/**
	 * @return the uploadfileBig
	 */
	public String getUploadfileBig() {
		return uploadfileBig;
	}
	/**
	 * @param uploadfileBig the uploadfileBig to set
	 */
	public void setUploadfileBig(String uploadfileBig) {
		this.uploadfileBig = uploadfileBig;
	}
	/**
	 * @return the uploadfileOrg
	 */
	public String getUploadfileOrg() {
		return uploadfileOrg;
	}
	/**
	 * @param uploadfileOrg the uploadfileOrg to set
	 */
	public void setUploadfileOrg(String uploadfileOrg) {
		this.uploadfileOrg = uploadfileOrg;
	}
	/**
	 * @return the vote
	 */
	public String getVote() {
		return vote;
	}
	/**
	 * @param vote the vote to set
	 */
	public void setVote(String vote) {
		this.vote = vote;
	}
	/**
	 * @return the viewCnt
	 */
	public String getViewCnt() {
		return viewCnt;
	}
	/**
	 * @param viewCnt the viewCnt to set
	 */
	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}
	/**
	 * @return the voteByMonth
	 */
	public String getVoteByMonth() {
		return voteByMonth;
	}
	/**
	 * @param voteByMonth the voteByMonth to set
	 */
	public void setVoteByMonth(String voteByMonth) {
		this.voteByMonth = voteByMonth;
	}
	/**
	 * @return the battleJoinCnt
	 */
	public String getBattleJoinCnt() {
		return battleJoinCnt;
	}
	/**
	 * @param battleJoinCnt the battleJoinCnt to set
	 */
	public void setBattleJoinCnt(String battleJoinCnt) {
		this.battleJoinCnt = battleJoinCnt;
	}
	

	
}
