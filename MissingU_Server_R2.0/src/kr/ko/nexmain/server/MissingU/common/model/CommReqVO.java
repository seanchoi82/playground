package kr.ko.nexmain.server.MissingU.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;;

public class CommReqVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Integer gMemberId;
	@NotEmpty
	private String gLang;
	private String gPosX;
	private String gPosY;
	private Integer lastItemId;
	private String location;
	private String searchType;
	private String gConfirm;
	private String gCountry;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**
	 * @return the gMemberId
	 */
	public Integer getgMemberId() {
		return gMemberId;
	}
	/**
	 * @param gMemberId the gMemberId to set
	 */
	public void setgMemberId(Integer gMemberId) {
		this.gMemberId = gMemberId;
	}
	/**
	 * @return the gLang
	 */
	public String getgLang() {
		return gLang;
	}
	/**
	 * @param gLang the gLang to set
	 */
	public void setgLang(String gLang) {
		this.gLang = gLang;
	}
	/**
	 * @return the gPosX
	 */
	public String getgPosX() {
		return gPosX;
	}
	/**
	 * @param gPosX the gPosX to set
	 */
	public void setgPosX(String gPosX) {
		this.gPosX = gPosX;
	}
	/**
	 * @return the gPosY
	 */
	public String getgPosY() {
		return gPosY;
	}
	/**
	 * @param gPosY the gPosY to set
	 */
	public void setgPosY(String gPosY) {
		this.gPosY = gPosY;
	}
	/**
	 * @return the lastItemId
	 */
	public Integer getLastItemId() {
		return lastItemId;
	}
	/**
	 * @param lastItemId the lastItemId to set
	 */
	public void setLastItemId(Integer lastItemId) {
		this.lastItemId = lastItemId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the gConfirm
	 */
	public String getgConfirm() {
		return gConfirm;
	}

	/**
	 * @param gConfirm the gConfirm to set
	 */
	public void setgConfirm(String gConfirm) {
		this.gConfirm = gConfirm;
	}

	/**
	 * @return the gCountry
	 */
	public String getgCountry() {
		return gCountry;
	}

	/**
	 * @param gCountry the gCountry to set
	 */
	public void setgCountry(String gCountry) {
		this.gCountry = gCountry;
	}

	
	
	
	


}
