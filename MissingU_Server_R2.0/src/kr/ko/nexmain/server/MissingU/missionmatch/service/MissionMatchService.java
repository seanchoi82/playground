package kr.ko.nexmain.server.MissingU.missionmatch.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchRankReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReportReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReqVO;

public interface MissionMatchService {

	/** 최신 미션매치 정보 반환 */
	Map<String,Object> getLastedMissionMatch(MissionMatchReqVO inputVO);
	
	/** 최신 미션매치 정보 반환 */
	Map<String,Object> getMissionMatchInfo(MissionMatchReqVO inputVO);
	
	/** 미션매치 목록 반환 */
	Map<String,Object> getMissionMatchDatas(MissionMatchReqVO inputVO);
	
	/** 미션매치 결과등록 */
	Map<String,Object> reportMissionMatch(MissionMatchReportReqVO inputVO);
	
	/** 미션매치 참여 */
	Map<String, Object> joinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	/** 미션매치 순위 반환 */
	Map<String, Object> joinRankMissionMatch(MissionMatchRankReqVO inputVO);
	
	/** 미션매치 순위 반환 */
	Map<String, Object> totalRankMissionMatch(MissionMatchRankReqVO inputVO);
	
}
