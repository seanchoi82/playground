package kr.ko.nexmain.server.MissingU.friends.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;

public interface FriendsDao {
	
	List<Map<String,Object>> selectMemberListForSearchFriends(SearchFriendsReqVO inputVO);
	
	List<Map<String,Object>> selectMyFriendsList(CommReqVO inputVO);
	
	/** 친구 조회 */
	public FriendsVO selectFriends(FriendsVO inputVO);
	
	/** 친구 Insert */
	public int insertIntoFriends(FriendsVO inputVO);
	
	/** 친구 Delete */
	public int deleteFromFriends(FriendsVO inputVO);
	
	/** 아이템 송수신 내역 Insert */
	int insertIntoItemSndRcvHist(Map<String, Object> inputMap);
	
	/** 인벤토리 업데이트 : item_amount 증가 */
	int updateInventoryToIncreaseItemAmount(Map<String, Object> inputMap);
	
	/** 인벤토리 업데이트 : item_amount 감소 */
	int updateInventoryToDecreaseItemAmount(Map<String, Object> inputMap);
	
}