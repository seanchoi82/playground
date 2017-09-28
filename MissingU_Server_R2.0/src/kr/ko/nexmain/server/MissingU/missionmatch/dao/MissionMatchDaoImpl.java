package kr.ko.nexmain.server.MissingU.missionmatch.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchRankReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReportReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class MissionMatchDaoImpl extends SqlMapClientDaoSupport implements MissionMatchDao {
	
	public MissionMatchDaoImpl() {
		super();
	}
	
	@Autowired
	public MissionMatchDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}

	@Override
	public Map<String, Object> getLastedMissionMatch(MissionMatchReqVO inputVO) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("MissionMatch.selectLastedMissionMatch", inputVO);
	}
	
	@Override
	public Map<String, Object> getMissionMatch(MissionMatchReqVO inputVO) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("MissionMatch.selectMissionMatch", inputVO);
	}

	@Override
	public List<String> getMissionMatchWinners(MissionMatchReqVO inputVO) {
		if(inputVO.getType() == 0) {
			return (List<String>)getSqlMapClientTemplate().queryForList("MissionMatch.selectMissionMatchDatasByFaceMatchOnWinner", inputVO);
		}else{
			return (List<String>)getSqlMapClientTemplate().queryForList("MissionMatch.selectMissionMatchDatasOnWinner", inputVO);
		}
	}

	@Override
	public List<Map<String, Object>> getMissionMatchDatas(MissionMatchReqVO inputVO) {
		if(inputVO.getType() == 0) {
			return (List<Map<String, Object>>)getSqlMapClientTemplate().queryForList("MissionMatch.selectMissionMatchDatasByFaceMatch", inputVO);
		}else{
			return (List<Map<String, Object>>)getSqlMapClientTemplate().queryForList("MissionMatch.selectMissionMatchDatas", inputVO);
		}
	}

	/** 미션매치 참여여부 */
	@Override
	public Map<String, Object> existsMissionMatchJoin(MissionMatchReportReqVO inputVO) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("MissionMatch.selectMissionMatchJoin", inputVO);
	}

	/** 미션매치 승수 업데이트 */
	@Override
	public Integer updateJoinMissionMatch(MissionMatchReportReqVO inputVO) {
		return getSqlMapClientTemplate().update("MissionMatch.updateJoinMissionMatch", inputVO);
	}
	
	/** 미션매치 투표 로그*/
	@Override
	public  Integer insertVote(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("MissionMatch.insertVote", params);
	}
	
	/**  미션매치 페이스매치 참여 */
	@Override
	public Integer joinMissionMatchByFaceMatch(MissionMatchReportReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("MissionMatch.joinMissionMatchByFaceMatch", inputVO);
	}
	
	/**  미션매치 페이스매치 참여 */
	@Override
	public Integer joinMissionMatchByFaceMatchDirect(MissionMatchReportReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("MissionMatch.joinMissionMatchByFaceMatchDirect", inputVO);
	}
	
	/** 미션매치 참여 */
	@Override
	public Integer joinMissionMatch(MissionMatchJoinReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("MissionMatch.joinMissionMatch", inputVO);
	}
	
	/** 최근 미션 투표 참여 정보 */
	@Override
	public Map<String, Object> recentMissionMatchVote(MissionMatchReportReqVO inputVO) {
		return (Map<String, Object>)getSqlMapClientTemplate().queryForObject("MissionMatch.selectRecentMissionMatchVote", inputVO);		
	}
	
	/** 미션매치 참여자수 반환 */
	@Override
	public Map<String, Object> countJoinMissionMatch(MissionMatchJoinReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("MissionMatch.countJoinMissionMatch", inputVO);
	}
	
	/** 미션매치 내순위(월간) 가져오기 */
	@Override
	public Map<String, Object> selectMyJoinRankMissionMatch(MissionMatchRankReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("MissionMatch.selectMyJoinRankMissionMatch", inputVO);
	}
	
	/** 미션매치 전체(월간) 순위 반환*/
	@Override
	public List<Map<String, Object>> selectJoinRankMissionMatch(MissionMatchRankReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("MissionMatch.selectJoinRankMissionMatch", inputVO);
	}

	@Override
	public Integer updateMissionMatchBattle(MissionMatchReqVO inputVO) {
		return getSqlMapClientTemplate().update("MissionMatch.updateMissionMatchBattle", inputVO);
	}

	@Override
	public Integer updateMissionMatchJoinBattle(MissionMatchReqVO inputVO) {
		return getSqlMapClientTemplate().update("MissionMatch.updateMissionMatchJoinBattle", inputVO);
	}
}
