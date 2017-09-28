package kr.ko.nexmain.server.MissingU.config.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.config.model.SaveManToManReqVO;

public interface ConfigDao {
	/** 공지사항 리스트 조회 */
	List<Map<String,Object>> selectNoticeList(CommReqVO inputVO);
	/** 공지사항 조회 */
	Map<String,Object> selectNotice(GetNoticeReqVO inputVO);
	/** 공지사항 조회수 증가 */
	int updateNoticeReadCnt(GetNoticeReqVO inputVO);
	/** 미확인 공지사항 수 조회 */
	Integer selectUnreadNotiCnt(Integer lastReadNoticeId);
	
	/** 사용자가이드 리스트 조회 */
	List<Map<String,Object>> selectUserGuideList(CommReqVO inputVO);
	/** 사용자가이드 조회 */
	Map<String,Object> selectUserGuide(GetUserGuideReqVO inputVO);
	
	
	/** 긴급 공지 조회 */
	Map<String,Object> selectEMRNofitice(CommReqVO inputVO, boolean useShowYn);
	
	/** 1:1 문의 저장 */
	Integer insertManToManQuestion(SaveManToManReqVO inputVO);
	
	/** 긴급 공지 조회 */
	Map<String,Object> selectEMRNofiticeRandomChat(CommReqVO inputVO, boolean useShowYn);
	
}