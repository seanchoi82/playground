package kr.ko.nexmain.server.MissingU.vscheck.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

@Service
@Transactional(timeout=15)
public interface VersionCheckService {

	/** 업그레이드 정보 및 긴급 공지 검색 */
	Map<String,Object> getUpgradeVersionInfoAndEMRNotification(CommReqVO inputVO, boolean useShowYn);

	/** 버전정보 업그레이드 */
	Map<String,Object> updateUpgradeVersionInfo(Map<String, Object> params);
	
	/** 긴급공지 적용 */
	Map<String,Object> updateEmrNotice(Map<String, Object> params);
	
	
	/** 업그레이드 정보 및 긴급 공지 검색 */
	Map<String,Object> getUpgradeVersionInfoAndEMRNotificationRandomChat(CommReqVO inputVO, boolean useShowYn);

	/** 버전정보 업그레이드 */
	Map<String,Object> updateUpgradeVersionInfoRandomChat(Map<String, Object> params);
	
	/** 긴급공지 적용 */
	Map<String,Object> updateEmrNoticeRandomChat(Map<String, Object> params);
	
	
}
