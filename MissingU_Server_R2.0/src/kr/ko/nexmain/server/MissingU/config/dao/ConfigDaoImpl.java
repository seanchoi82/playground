package kr.ko.nexmain.server.MissingU.config.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.config.model.SaveManToManReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class ConfigDaoImpl extends SqlMapClientDaoSupport implements ConfigDao {
	public ConfigDaoImpl(){
		super();
	}
	
	@Autowired
	public ConfigDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 공지사항 리스트 조회 */
	public List<Map<String,Object>> selectNoticeList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Config.selectNoticeList", inputVO);
	}
	
	/** 공지사항 조회 */
	public Map<String,Object> selectNotice(GetNoticeReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Config.selectNotice", inputVO);
	}
	/** 미확인 공지사항 수 조회 */
	public Integer selectUnreadNotiCnt(Integer lastReadNoticeId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Config.selectUnreadNotiCnt", lastReadNoticeId);
	}
	/** 공지사항 조회수 증가 */
	public int updateNoticeReadCnt(GetNoticeReqVO inputVO) {
		return getSqlMapClientTemplate().update("Config.updateNoticeReadCnt", inputVO);
	}
	
	/** 사용자가이드 리스트 조회 */
	public List<Map<String,Object>> selectUserGuideList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Config.selectUserGuideList", inputVO);
	}
	/** 사용자가이드 조회 */
	public Map<String,Object> selectUserGuide(GetUserGuideReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Config.selectUserGuide", inputVO);
	}

	/** 긴급 공지 조회 */
	@Override
	public Map<String, Object> selectEMRNofitice(CommReqVO inputVO, boolean useShowYn) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("gLang", inputVO.getgLang());
		params.put("useShowYN", useShowYn ? "1" : "0");
		
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Config.selectEMRNofitice", params);
	}
	
	/** 1:1 문의 저장 */
	public Integer insertManToManQuestion(SaveManToManReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("Config.insertIntoManToManQuestion", inputVO);
	}
	
	/** 긴급 공지 조회 */
	@Override
	public Map<String, Object> selectEMRNofiticeRandomChat(CommReqVO inputVO, boolean useShowYn) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("gLang", inputVO.getgLang());
		params.put("useShowYN", useShowYn ? "1" : "0");
		
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Config.selectEMRNofiticeRandomChat", params);
	}
	
}