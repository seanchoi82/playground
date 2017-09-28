package kr.ko.nexmain.server.MissingU.config.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public class SaveManToManReqVO extends CommReqVO {

	/** */
	private static final long serialVersionUID = 1L;
	
	private Integer mId;
	private String code = "";
	private String contract = "";
	@NotEmpty
	private String content = "";
	private String file = "";
	
	
	private MultipartFile uploadFile;
	/**
	 * @return the mId
	 */
	public Integer getmId() {
		return mId;
	}
	/**
	 * @param mId the mId to set
	 */
	public void setmId(Integer mId) {
		this.mId = mId;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the contract
	 */
	public String getContract() {
		return contract;
	}
	/**
	 * @param contract the contract to set
	 */
	public void setContract(String contract) {
		this.contract = contract;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	

}
