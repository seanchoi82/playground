package kr.ko.nexmain.server.MissingU.common.service;

import java.util.Map;

public interface CommonService {
	/** 포인트 정보 업데이트 */
	boolean updatePointInfo(Integer memberId, String eventTypeCd, String usageCd, Integer usePoint, String gLang);
	
	/** 유효한 정액권 소지여부 체크 */
	boolean hasFreePass(Integer memberId);
	
	/** 포인트 정책 조회 */
	Map<String,Object> getMemberPointAndNeedPoint(Map<String,Object> inputMap, String gLang);
	
	/** 서비스 접근 로그 저장 */
	boolean saveServiceAccessLog(Map<String,Object> inputMap);
}
