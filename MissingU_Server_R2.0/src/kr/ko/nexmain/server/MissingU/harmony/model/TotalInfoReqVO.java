package kr.ko.nexmain.server.MissingU.harmony.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class TotalInfoReqVO extends CommReqVO {
	
	private static final long serialVersionUID = 1795392071746647492L;
	
	private Integer friendId;
	
	private String nickName;
	private String bloodTypeCd;
	private String birthDate;
	private String birthTime;
	private String lunarSolarCd;


	/**
	 * @return the friendId
	 */
	public Integer getFriendId() {
		return friendId;
	}

	/**
	 * @param friendId the friendId to set
	 */
	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the bloodTypeCd
	 */
	public String getBloodTypeCd() {
		return bloodTypeCd;
	}

	/**
	 * @param bloodTypeCd the bloodTypeCd to set
	 */
	public void setBloodTypeCd(String bloodTypeCd) {
		this.bloodTypeCd = bloodTypeCd;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the birthTime
	 */
	public String getBirthTime() {
		return birthTime;
	}

	/**
	 * @param birthTime the birthTime to set
	 */
	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}

	/**
	 * @return the lunarSolarCd
	 */
	public String getLunarSolarCd() {
		return lunarSolarCd;
	}

	/**
	 * @param lunarSolarCd the lunarSolarCd to set
	 */
	public void setLunarSolarCd(String lunarSolarCd) {
		this.lunarSolarCd = lunarSolarCd;
	}
	
	
}
