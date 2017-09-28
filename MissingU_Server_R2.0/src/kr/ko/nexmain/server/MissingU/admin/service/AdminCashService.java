package kr.ko.nexmain.server.MissingU.admin.service;

import java.util.Map;


public interface AdminCashService {

	/**
	 * 윙크보내기
	 * @param params
	 * @return
	 */
	Map<String, Object> sendWink(Map<String, Object> params);
	
	/**
	 * 윙크발송 복원(포인트 원복 및 이력생성)
	 * @param params
	 * @return
	 */
	Map<String,Object> deleteWink(Map<String, Object> params);
	
	/**
	 * 윙크 목록
	 * @param params
	 * @return
	 */
	Map<String, Object> getWinkList(Map<String, Object> params);
	
	/**
	 * 선물내기
	 * @param params
	 * @return
	 */
	Map<String, Object> sendGift(Map<String, Object> params);
	
	/**
	 * 선물발송 복원(포인트 원복 및 이력생성)
	 * @param params
	 * @return
	 */
	Map<String,Object> deleteGift(Map<String, Object> params);
	
	/**
	 * 선물목록
	 * @param params
	 * @return
	 */
	Map<String, Object> getGiftList(Map<String, Object> params);
	
	/**
	 * 포인트 사용 목록
	 * @param params
	 * @return
	 */
	Map<String, Object> getPointList(Map<String, Object> params);
}
