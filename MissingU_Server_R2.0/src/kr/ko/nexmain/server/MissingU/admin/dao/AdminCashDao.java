package kr.ko.nexmain.server.MissingU.admin.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.admin.model.CashItemHistoryVO;

public interface AdminCashDao {

	int deleteCashItemHistory(Integer id);
	
	List<Map<String, CashItemHistoryVO>> selectCashItemList(Map<String, Object> params);
	
	int selectCashItemListCnt(Map<String, Object> params);
	
	List<Map<String, Object>> selectPointList(Map<String, Object> params);
	
	int selectPointListCnt(Map<String, Object> params);
}
