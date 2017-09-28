package kr.ko.nexmain.server.MissingU.vscheck.dao;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class VersionCheckDaoImpl extends SqlMapClientDaoSupport implements VersionCheckDao {

	public VersionCheckDaoImpl(){
		super();
	}
	
	@Autowired
	public VersionCheckDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectLastVersionInfo(CommReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("VersionInfo.selectLastVersionInfo", inputVO);
	}
	
	@Override
	public int updateUpgradeVersionInfo(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("VersionInfo.updateUpgradeVersionInfo", params);
	}
	
	@Override
	public int updateEmrNotice(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("VersionInfo.updateEmrNotice", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectLastVersionInfoRandomChat(CommReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("VersionInfo.selectLastVersionInfoRandomChat", inputVO);
	}
	
	@Override
	public int updateUpgradeVersionInfoRandomChat(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("VersionInfo.updateUpgradeVersionInfoRandomChat", params);
	}
	
	@Override
	public int updateEmrNoticeRandomChat(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("VersionInfo.updateEmrNoticeRandomChat", params);
	}

}
