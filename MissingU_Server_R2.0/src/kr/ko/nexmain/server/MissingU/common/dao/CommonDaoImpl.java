package kr.ko.nexmain.server.MissingU.common.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class CommonDaoImpl extends SqlMapClientDaoSupport implements CommonDao {
	public CommonDaoImpl(){
		super();
	}
	
	@Autowired
	public CommonDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 서비스 접근 이력 생성 */
	public Integer insertServiceAccessLog(Map<String,Object> inputMap) {
		return (Integer) getSqlMapClientTemplate().insert("Common.insertServiceAccessLog", inputMap);
	}
	
}