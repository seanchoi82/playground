package kr.ko.nexmain.server.MissingU.membership.model;

import javax.validation.constraints.NotNull;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateMemberReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Integer memberId;
	private String status;
	private String loginId;
	private String loginPw;
	private String gcmUseYn;
	private String gcmRegId;
	private String name;
	private String nickName;
	private String sex;
	private String bloodTypeCd;
	private String birthDate;
	private String birthTime;
	private String lunarSolarCd;
	private String appearanceTypeCd;
	private String bodyTypeCd;
	private String areaCd;
	private String purposeCd;
	private String hobbyCd;
	private String drinkingHabitCd;
	private String smokingHabitCd;
	private String mainPhoto;
	private String subPhoto01;
	private String subPhoto02;
	private String subPhoto03;
	private String subPhoto04;
	private String subPhoto05;
	private String subPhoto06;
	private String subPhoto07;
	private String subPhoto08;
	private String selfPr;
	private String hpNm;
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
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return the loginPw
	 */
	public String getLoginPw() {
		return loginPw;
	}
	/**
	 * @param loginPw the loginPw to set
	 */
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
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
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the mainPhoto
	 */
	public String getMainPhoto() {
		return mainPhoto;
	}
	/**
	 * @param mainPhoto the mainPhoto to set
	 */
	public void setMainPhoto(String mainPhoto) {
		this.mainPhoto = mainPhoto;
	}
	/**
	 * @return the subPhoto01
	 */
	public String getSubPhoto01() {
		return subPhoto01;
	}
	/**
	 * @param subPhoto01 the subPhoto01 to set
	 */
	public void setSubPhoto01(String subPhoto01) {
		this.subPhoto01 = subPhoto01;
	}
	/**
	 * @return the subPhoto02
	 */
	public String getSubPhoto02() {
		return subPhoto02;
	}
	/**
	 * @param subPhoto02 the subPhoto02 to set
	 */
	public void setSubPhoto02(String subPhoto02) {
		this.subPhoto02 = subPhoto02;
	}
	/**
	 * @return the subPhoto03
	 */
	public String getSubPhoto03() {
		return subPhoto03;
	}
	/**
	 * @param subPhoto03 the subPhoto03 to set
	 */
	public void setSubPhoto03(String subPhoto03) {
		this.subPhoto03 = subPhoto03;
	}
	/**
	 * @return the subPhoto04
	 */
	public String getSubPhoto04() {
		return subPhoto04;
	}
	/**
	 * @param subPhoto04 the subPhoto04 to set
	 */
	public void setSubPhoto04(String subPhoto04) {
		this.subPhoto04 = subPhoto04;
	}
	/**
	 * @return the subPhoto05
	 */
	public String getSubPhoto05() {
		return subPhoto05;
	}
	/**
	 * @param subPhoto05 the subPhoto05 to set
	 */
	public void setSubPhoto05(String subPhoto05) {
		this.subPhoto05 = subPhoto05;
	}
	/**
	 * @return the subPhoto06
	 */
	public String getSubPhoto06() {
		return subPhoto06;
	}
	/**
	 * @param subPhoto06 the subPhoto06 to set
	 */
	public void setSubPhoto06(String subPhoto06) {
		this.subPhoto06 = subPhoto06;
	}
	/**
	 * @return the subPhoto07
	 */
	public String getSubPhoto07() {
		return subPhoto07;
	}
	/**
	 * @param subPhoto07 the subPhoto07 to set
	 */
	public void setSubPhoto07(String subPhoto07) {
		this.subPhoto07 = subPhoto07;
	}
	/**
	 * @return the subPhoto08
	 */
	public String getSubPhoto08() {
		return subPhoto08;
	}
	/**
	 * @param subPhoto08 the subPhoto08 to set
	 */
	public void setSubPhoto08(String subPhoto08) {
		this.subPhoto08 = subPhoto08;
	}
	/**
	 * @return the selfPr
	 */
	public String getSelfPr() {
		return selfPr;
	}
	/**
	 * @param selfPr the selfPr to set
	 */
	public void setSelfPr(String selfPr) {
		this.selfPr = selfPr;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
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
	 * @return the hpNm
	 */
	public String getHpNm() {
		return hpNm;
	}
	/**
	 * @param hpNm the hpNm to set
	 */
	public void setHpNm(String hpNm) {
		this.hpNm = hpNm;
	}
	
	
	

}
