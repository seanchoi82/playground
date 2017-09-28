package kr.ko.nexmain.server.MissingU.friends.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchFriendsReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	private Integer memberId;
	private String nickName;
	@NotEmpty
	private String sex;
	private String minAge;
	private String maxAge;
	private String appearanceTypeCd;
	private String bodyTypeCd;
	private String areaCd;
	private String areaNm;
	private String purposeCd;
	private String hobbyCd;
	private String drinkingHabitCd;
	private String smokingHabitCd;
	private Integer distance;
	private String countryExclusive;
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
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	 * @return the minAge
	 */
	public String getMinAge() {
		return minAge;
	}
	/**
	 * @param minAge the minAge to set
	 */
	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}
	/**
	 * @return the maxAge
	 */
	public String getMaxAge() {
		return maxAge;
	}
	/**
	 * @param maxAge the maxAge to set
	 */
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}
	/**
	 * @return the appearanceTypeCd
	 */
	public String getAppearanceTypeCd() {
		return appearanceTypeCd;
	}
	/**
	 * @param appearanceTypeCd the appearanceTypeCd to set
	 */
	public void setAppearanceTypeCd(String appearanceTypeCd) {
		this.appearanceTypeCd = appearanceTypeCd;
	}
	/**
	 * @return the bodyTypeCd
	 */
	public String getBodyTypeCd() {
		return bodyTypeCd;
	}
	/**
	 * @param bodyTypeCd the bodyTypeCd to set
	 */
	public void setBodyTypeCd(String bodyTypeCd) {
		this.bodyTypeCd = bodyTypeCd;
	}
	/**
	 * @return the areaCd
	 */
	public String getAreaCd() {
		return areaCd;
	}
	/**
	 * @param areaCd the areaCd to set
	 */
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	
	public String getAreaNm() {
		return areaNm;
	}
	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}
	/**
	 * @return the purposeCd
	 */
	public String getPurposeCd() {
		return purposeCd;
	}
	/**
	 * @param purposeCd the purposeCd to set
	 */
	public void setPurposeCd(String purposeCd) {
		this.purposeCd = purposeCd;
	}
	/**
	 * @return the hobbyCd
	 */
	public String getHobbyCd() {
		return hobbyCd;
	}
	/**
	 * @param hobbyCd the hobbyCd to set
	 */
	public void setHobbyCd(String hobbyCd) {
		this.hobbyCd = hobbyCd;
	}
	/**
	 * @return the drinkingHabitCd
	 */
	public String getDrinkingHabitCd() {
		return drinkingHabitCd;
	}
	/**
	 * @param drinkingHabitCd the drinkingHabitCd to set
	 */
	public void setDrinkingHabitCd(String drinkingHabitCd) {
		this.drinkingHabitCd = drinkingHabitCd;
	}
	/**
	 * @return the smokingHabitCd
	 */
	public String getSmokingHabitCd() {
		return smokingHabitCd;
	}
	/**
	 * @param smokingHabitCd the smokingHabitCd to set
	 */
	public void setSmokingHabitCd(String smokingHabitCd) {
		this.smokingHabitCd = smokingHabitCd;
	}
	/**
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public String getCountryExclusive() {
		return countryExclusive;
	}
	public void setCountryExclusive(String countryExclusive) {
		this.countryExclusive = countryExclusive;
	}
}
