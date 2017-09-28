package kr.ko.nexmain.server.MissingU.missionmatch.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchRankReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReportReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReqVO;

public interface MissionMatchDao {
	
	/** 최신 미션매치 정보 반환 */
	Map<String,Object> getLastedMissionMatch(MissionMatchReqVO inputVO);
	
	/** 미션매치 정보 반환 */
	Map<String, Object> getMissionMatch(MissionMatchReqVO inputVO);
	
	/** 미션매치 승자 목록 반환 */
	List<String> getMissionMatchWinners(MissionMatchReqVO inputVO);
	
	/** 미션매치 목록 반환 */
	List<Map<String, Object>> getMissionMatchDatas(MissionMatchReqVO inputVO);
	
	/** 미션매치 참여여부 검사 */
	Map<String, Object> existsMissionMatchJoin(MissionMatchReportReqVO inputVO);
	
	/** 미션매치 페이스매치 참여 */
	Integer joinMissionMatchByFaceMatch(MissionMatchReportReqVO inputVO);
	
	/**  미션매치 페이스매치 참여 */
	Integer joinMissionMatchByFaceMatchDirect(MissionMatchReportReqVO inputVO);

	/** 미션매치 결과 업데이트(승수업) */
	Integer updateJoinMissionMatch(MissionMatchReportReqVO inputVO);
	/** 미션매치 투표 로그*/
	Integer insertVote(Map<String, Object> params);
	
	/** 최근 미션 투표 참여 정보 */
	Map<String, Object> recentMissionMatchVote(MissionMatchReportReqVO inputVO);
	
	/** 미션매치 결과 등록 */
	Integer joinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	
	/** 미션매치 참여자수 반환 */
	Map<String, Object> countJoinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	/** 미션매치 내순위 가져오기 */
	Map<String, Object> selectMyJoinRankMissionMatch(MissionMatchRankReqVO inputVO);
	
	/** 미션매치 목록 반환 */
	List<Map<String, Object>> selectJoinRankMissionMatch(MissionMatchRankReqVO inputVO);

	/** 미션매치 배틀 카운트 업데이트  */
	Integer updateMissionMatchBattle(MissionMatchReqVO inputVO);

	/** 미션매치 배틀 참가자 카운트 업데이트 */
	Integer updateMissionMatchJoinBattle(MissionMatchReqVO inputVO);
}
