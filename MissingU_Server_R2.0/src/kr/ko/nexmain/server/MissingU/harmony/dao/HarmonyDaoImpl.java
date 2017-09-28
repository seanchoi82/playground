package kr.ko.nexmain.server.MissingU.harmony.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class HarmonyDaoImpl extends SqlMapClientDaoSupport implements HarmonyDao {
	public HarmonyDaoImpl(){
		super();
	}
	
	@Autowired
	public HarmonyDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 혈액형 궁합 데이타 조회 */
	public Map<String,Object> selectBloodHarmonyData(Map<String,Object> inputMap) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Harmony.selectBloodHarmonyData", inputMap);
	}
	
	/** 별자리 궁합 데이타 조회 */
	public Map<String,Object> selectSignHarmonyData(Map<String,Object> inputMap) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Harmony.selectSignHarmonyData", inputMap);
	}
	
	/** 속궁합 데이타 조회 */
	public Map<String,Object> selectInnerHarmonyData(Map<String,Object> inputMap) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Harmony.selectInnerHarmonyData", inputMap);
	}
	
	/** 겉궁합 데이타 조회 */
	public Map<String,Object> selectOuterHarmonyData(Map<String,Object> inputMap) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Harmony.selectOuterHarmonyData", inputMap);
	}
}