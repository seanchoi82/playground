package kr.ko.nexmain.server.MissingU.vscheck.dao;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public interface VersionCheckDao {

	Map<String,Object> selectLastVersionInfo(CommReqVO inputVO);
	
	int updateUpgradeVersionInfo(Map<String, Object> params);
	
	int updateEmrNotice(Map<String, Object> params);
	
	Map<String,Object> selectLastVersionInfoRandomChat(CommReqVO inputVO);
	
	int updateUpgradeVersionInfoRandomChat(Map<String, Object> params);
	
	int updateEmrNoticeRandomChat(Map<String, Object> params);
	
	
}
