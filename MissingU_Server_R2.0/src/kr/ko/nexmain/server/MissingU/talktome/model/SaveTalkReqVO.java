package kr.ko.nexmain.server.MissingU.talktome.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class SaveTalkReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;
	private Integer talkId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String contents;
	private String talkPhotoUrl;
	private String talkPhotoBigUrl;
	private String talkPhotoOrgUrl;
	private MultipartFile uploadFile;

	/**
	 * @return the talkId
	 */
	public Integer getTalkId() {
		return talkId;
	}
	/**
	 * @param talkId the talkId to set
	 */
	public void setTalkId(Integer talkId) {
		this.talkId = talkId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the talkPhotoUrl
	 */
	public String getTalkPhotoUrl() {
		return talkPhotoUrl;
	}
	/**
	 * @param talkPhotoUrl the talkPhotoUrl to set
	 */
	public void setTalkPhotoUrl(String talkPhotoUrl) {
		this.talkPhotoUrl = talkPhotoUrl;
	}
	/**
	 * @return the talkPhotoBigUrl
	 */
	public String getTalkPhotoBigUrl() {
		return talkPhotoBigUrl;
	}
	/**
	 * @param talkPhotoBigUrl the talkPhotoBigUrl to set
	 */
	public void setTalkPhotoBigUrl(String talkPhotoBigUrl) {
		this.talkPhotoBigUrl = talkPhotoBigUrl;
	}
	/**
	 * @return the talkPhotoOrgUrl
	 */
	public String getTalkPhotoOrgUrl() {
		return talkPhotoOrgUrl;
	}
	/**
	 * @param talkPhotoOrgUrl the talkPhotoOrgUrl to set
	 */
	public void setTalkPhotoOrgUrl(String talkPhotoOrgUrl) {
		this.talkPhotoOrgUrl = talkPhotoOrgUrl;
	}
	/**
	 * @return the uploadFile
	 */
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
	

}
