package kr.ko.nexmain.server.MissingU.common.dao;

import java.util.Map;

public interface CommonDao {
	/** 서비스 접근 이력 생성 */
	Integer insertServiceAccessLog(Map<String,Object> inputMap);
}