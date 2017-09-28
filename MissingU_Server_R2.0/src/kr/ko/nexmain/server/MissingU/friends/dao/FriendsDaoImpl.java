package kr.ko.nexmain.server.MissingU.friends.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class FriendsDaoImpl extends SqlMapClientDaoSupport implements FriendsDao {
	
	public FriendsDaoImpl(){
		super();
	}
	
	@Autowired
	public FriendsDaoImpl(SqlMapClient sqlMapClient) {
		super(); 
		setSqlMapClient(sqlMapClient);
	}
	
	/** 친구 리스트 조회 */
	public List<Map<String,Object>> selectMemberListForSearchFriends(SearchFriendsReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Friends.selectMemberListForSearchFriends", inputVO);
	}
	
	/** 내 친구 리스트 조회 */
	public List<Map<String,Object>> selectMyFriendsList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Friends.selectMyFriendsList", inputVO);
	}
	
	/** 친구 조회 */
	public FriendsVO selectFriends(FriendsVO inputVO) {
		return (FriendsVO) getSqlMapClientTemplate().queryForObject("Friends.selectFriends", inputVO);
	}
	
	/** 친구 Insert */
	public int insertIntoFriends(FriendsVO inputVO) {
		return getSqlMapClientTemplate().update("Friends.insertIntoFriends", inputVO);
	}
	
	/** 친구 Delete */
	public int deleteFromFriends(FriendsVO inputVO) {
		return getSqlMapClientTemplate().delete("Friends.deleteFromFriends", inputVO);
	}
	
	/** 아이템 송수신 내역 Insert */
	public int insertIntoItemSndRcvHist(Map<String, Object> inputMap) {
		return getSqlMapClientTemplate().update("Friends.insertIntoItemSndRcvHist", inputMap);
	}
	
	/** 인벤토리 업데이트 : item_amount 증가 */
	public int updateInventoryToIncreaseItemAmount(Map<String, Object> inputMap) {
		return getSqlMapClientTemplate().update("Friends.updateInventoryToIncreaseItemAmount", inputMap);
	}
	
	/** 인벤토리 업데이트 : item_amount 증가 */
	public int updateInventoryToDecreaseItemAmount(Map<String, Object> inputMap) {
		return getSqlMapClientTemplate().update("Friends.updateInventoryToDecreaseItemAmount", inputMap);
	}
}