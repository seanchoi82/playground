package kr.ko.nexmain.server.MissingU.config.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.config.model.SaveManToManReqVO;

public interface ConfigService {
	/** 공지사항 리스트 조회 */
	Map<String,Object> getNoticeList(CommReqVO inputVO);
	/** 공지사항 조회 */
	Map<String,Object> getNotice(GetNoticeReqVO inputVO);
	
	/** 사용자가이드 리스트 조회 */
	Map<String,Object> getUserGuideList(CommReqVO inputVO);
	/** 사용자가이드 조회 */
	Map<String,Object> getUserGuide(GetUserGuideReqVO inputVO);

	/** 1:1문의 저장 */
	Map<String,Object> doSaveManToManQuestion(SaveManToManReqVO inputVO);
	
	
}
