package kr.ko.nexmain.server.MissingU.admin.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class ApkItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer apkId;

	@NotEmpty
	private String apkDesc;
	
	@NotEmpty
	private String apkVersion;
	
	@NotEmpty
	private String registerName;
	
	private String regDate;
	
	private String apkFileName;
	
	private MultipartFile apkFile;

	/**
	 * @return the apkId
	 */
	public Integer getApkId() {
		return apkId;
	}

	/**
	 * @param apkId the apkId to set
	 */
	public void setApkId(Integer apkId) {
		this.apkId = apkId;
	}

	/**
	 * @return the apkDesc
	 */
	public String getApkDesc() {
		return apkDesc;
	}

	/**
	 * @param apkDesc the apkDesc to set
	 */
	public void setApkDesc(String apkDesc) {
		this.apkDesc = apkDesc;
	}

	/**
	 * @return the apkVersion
	 */
	public String getApkVersion() {
		return apkVersion;
	}

	/**
	 * @param apkVersion the apkVersion to set
	 */
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the apkFileName
	 */
	public String getApkFileName() {
		return apkFileName;
	}

	/**
	 * @param apkFileName the apkFileName to set
	 */
	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}
	
	/**
	 * @return the apkFile
	 */
	public MultipartFile getApkFile() {
		return apkFile;
	}

	/**
	 * @param apkFile the apkFile to set
	 */
	public void setApkFile(MultipartFile apkFile) {
		this.apkFile = apkFile;
	}



}
