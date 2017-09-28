package kr.ko.nexmain.server.MissingU.membership.model;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.springframework.web.multipart.MultipartFile;

public class SaveMemberPhotoReqVO extends CommReqVO {

	private static final long serialVersionUID = 1L;

	private String actionType;
	private String fileUsageType;
	private String deleteFlag;
	private MultipartFile uploadFile;
	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the fileUsageType
	 */
	public String getFileUsageType() {
		return fileUsageType;
	}
	/**
	 * @param fileUsageType the fileUsageType to set
	 */
	public void setFileUsageType(String fileUsageType) {
		this.fileUsageType = fileUsageType;
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
	/**
	 * 
	 * @return
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	/**
	 * 
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	

}
