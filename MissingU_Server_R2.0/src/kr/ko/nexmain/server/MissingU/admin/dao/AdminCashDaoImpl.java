package kr.ko.nexmain.server.MissingU.admin.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.admin.model.CashItemHistoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class AdminCashDaoImpl extends SqlMapClientDaoSupport implements AdminCashDao {
	
	public AdminCashDaoImpl() {
		super();
	}
	@Autowired
	public AdminCashDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
		@Override
	public int deleteCashItemHistory(Integer id) {
		return getSqlMapClientTemplate().delete("AdminCash.deleteCashItemHistory", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, CashItemHistoryVO>> selectCashItemList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("AdminCash.selectCashItemList", params);
	}

	@Override
	public int selectCashItemListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("AdminCash.selectCashItemListCnt", params);
	}
	
	@Override
	public List<Map<String, Object>> selectPointList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("AdminCash.selectPointList", params);
	}

	@Override
	public int selectPointListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("AdminCash.selectPointListCnt", params);
	}
	
}
