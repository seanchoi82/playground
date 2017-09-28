package kr.ko.nexmain.server.MissingU.friends.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SendGiftReqVO;

public interface FriendsService {
	/** 친구검색 */
	Map<String,Object> getMemberListForSearchFriends(SearchFriendsReqVO inputVO);
	
	/** 친구 추가 */
	Map<String,Object> addFriend(FriendsEditReqVO inputVO);
	
	/** 친구 삭제 */
	Map<String,Object> deleteFriend(FriendsEditReqVO inputVO);
	
	/** 친구 상세정보 조회 */
	Map<String,Object> getDetailInfo(FriendsEditReqVO inputVO);
	
	/** 윙크 보내기 */
	Map<String,Object> sendWink(FriendsEditReqVO inputVO);
	
	/** 선물 보내기 */
	Map<String,Object> sendGift(SendGiftReqVO inputVO);
	
	
}
