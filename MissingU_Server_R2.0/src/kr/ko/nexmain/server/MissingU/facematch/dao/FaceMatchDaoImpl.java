package kr.ko.nexmain.server.MissingU.facematch.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.FmResultVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetCandidatesReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class FaceMatchDaoImpl extends SqlMapClientDaoSupport implements FaceMatchDao {
	public FaceMatchDaoImpl(){
		super();
	}
	
	@Autowired
	public FaceMatchDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 페이스매치 후보 조회 */
	public List<Map<String,Object>> selectCandidatesFromFmResult(FmResultVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("FaceMatch.selectCandidatesFromFmResult", inputVO);
	}
	/** 현재 진행중인 이벤트 정보 조회 */
	public Map<String,Object> selectCurrentFmEvent() {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("FaceMatch.selectCurrentEvent");
	}
	
	/** 페이스매치 결과 업데이트 */
	public int updateFmResult(FmResultVO inputVO) {
		return getSqlMapClientTemplate().update("FaceMatch.updateFmResult", inputVO);
	}
	
	/** 페이스매치 결과 업데이트 */
	public int updateFmWinner(FmResultVO inputVO) {
		return getSqlMapClientTemplate().update("FaceMatch.updateFmWinner", inputVO);
	}
	
	/** 페이스매치 순위 조회 */
	public List<Map<String,Object>> selectFmRank(FmResultVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("FaceMatch.selectFmRank", inputVO);
	}
	
	/** 페이스매치 사진 업데이트 */
	public int insertOrUpdateFmPhoto(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("FaceMatch.insertOrUpdateFmPhoto", inputMap);
	}
	
	/** 페이스매치 전적 조회 */
	public Map<String,Object> selectFmRecord(CommReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("FaceMatch.selectFmRecord", inputVO);
	}
	
}