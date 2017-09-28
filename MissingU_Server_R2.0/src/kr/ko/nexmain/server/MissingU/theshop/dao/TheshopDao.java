package kr.ko.nexmain.server.MissingU.theshop.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;

public interface TheshopDao {
	
	/** 결제내역 조회 전체 개수*/
	Integer selectPayHistCntByMemberId(Map<String, Object> params);
		
	/** 결제내역 조회 */
	List<Map<String,Object>> selectPayHistByMemberId(Map<String, Object> params);
	
	/** 결제내역 생성 */
	int insertPayHist(Map<String,Object> inputMap);
	
	/** 결제내역 업데이트 */
	int updatePayHist(Map<String,Object> inputMap);
	
	/** 결제내역 저장 */
	int insertOrUpdatePayHist(Map<String,Object> inputMap);
	
	/** 결제내역 저장 */
	int insertOrUpdatePayHistTStore(Map<String,Object> inputMap);
	
}