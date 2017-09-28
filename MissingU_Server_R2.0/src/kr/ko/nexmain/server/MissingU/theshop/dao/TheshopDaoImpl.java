package kr.ko.nexmain.server.MissingU.theshop.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class TheshopDaoImpl extends SqlMapClientDaoSupport implements TheshopDao {
	public TheshopDaoImpl(){
		super();
	}
	
	@Autowired
	public TheshopDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 결제내역 조회 */
	public Integer selectPayHistCntByMemberId(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Theshop.selectPayHistCntByMemberId", params);
	}
	
	/** 결제내역 조회 */
	public List<Map<String,Object>> selectPayHistByMemberId(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Theshop.selectPayHistByMemberId", params);
	}
	
	/** 결제내역 생성 */
	public int insertPayHist(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("Theshop.insertPayHist", inputMap);
	}
	/** 결제내역 업데이트 */
	public int updatePayHist(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("Theshop.updatePayHist", inputMap);
	}
	
	/** 결제내역 저장 */
	public int insertOrUpdatePayHist(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("Theshop.insertOrUpdatePayHist", inputMap);
	}
	
	/** 결제내역 저장 */
	public int insertOrUpdatePayHistTStore(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("Theshop.insertOrUpdatePayHistTStore", inputMap);
	}
}