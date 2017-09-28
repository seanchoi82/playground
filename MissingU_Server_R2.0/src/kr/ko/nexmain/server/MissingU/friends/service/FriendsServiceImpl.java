package kr.ko.nexmain.server.MissingU.friends.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.controller.FriendsController;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SendGiftReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.service.HarmonyService;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class FriendsServiceImpl implements FriendsService {
	
	protected static Logger log = LoggerFactory.getLogger(FriendsServiceImpl.class);

	@Autowired
	private CommonService commonService;
	@Autowired
	private FriendsDao friendsDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private HarmonyService harmonyService;
	@Autowired
	private MsgUtil msgUtil;
	
	private Locale gLocale;
	
	/** 
	 * 친구 리스트 조회
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getMemberListForSearchFriends(SearchFriendsReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 지역검색이 있는 경우
		if(inputVO.getAreaCd() != null && inputVO.getAreaCd().length() > 0
				&& inputVO.getAreaNm() != null && inputVO.getAreaNm().length() > 0) {
			
			// 지역검색중 한국 해외, 기타인 경우
			if("A06017".equals(inputVO.getAreaCd())) {
				inputVO.setAreaCd(null);
				inputVO.setAreaNm(null);
				inputVO.setCountryExclusive("kr");
			// 지역검색중 일본 기타지역인 경우
			}else if("AJ6048".equals(inputVO.getAreaCd())) {
				inputVO.setAreaCd(null);
				inputVO.setAreaNm(null);
				inputVO.setCountryExclusive("ja");
			// 지역검색중 중국 기타지역인 경우
			}else if("AZ6035".equals(inputVO.getAreaCd())) {
				inputVO.setAreaCd(null);
				inputVO.setAreaNm(null);
				inputVO.setCountryExclusive("zh");
			}else{
				// 빈값이라도 있어야함
				inputVO.setCountryExclusive("");
			}
			
		}else{
			inputVO.setCountryExclusive(null);

		}
		
		// 가까운 거리 검색(랜덤인 경우 전체 적용을 위함)
		if(inputVO.getDistance().compareTo(0) <= 0) {
			// inputVO.setDistance(30);
			inputVO.setDistance(null);
		}
		
		log.info("Input Param : {}", inputVO.toString());
		
		//Input 계정정보로 회원조회
		List<Map<String,Object>> memberList = friendsDao.selectMemberListForSearchFriends(inputVO);
		responseMap.put("member", memberList);
		
		if(memberList != null && memberList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("friends.searchFriends.le.noResult", gLocale),
					msgUtil.getMsgText("friends.searchFriends.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 친구 추가 */
	public Map<String,Object> addFriend(FriendsEditReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		FriendsVO vo = new FriendsVO();
		vo.setMemberId(inputVO.getgMemberId());
		vo.setFriendId(inputVO.getFriendId());
		FriendsVO friend = friendsDao.selectFriends(vo);
		
		if(friend != null) {
			//이미 등록된 친구인 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("friends.addFriend.le.alreadyExist", gLocale),
					msgUtil.getMsgText("friends.addFriend.le.alreadyExist", gLocale));
			returnMap.put("result", result);
			return returnMap;
		} else {
			vo.setStatus("A"); //Active
			friendsDao.insertIntoFriends(vo);
		}
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("friends.addFriend.ss.success", gLocale),
				msgUtil.getMsgText("friends.addFriend.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 친구 삭제 */
	public Map<String,Object> deleteFriend(FriendsEditReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		FriendsVO vo = new FriendsVO();
		vo.setMemberId(inputVO.getgMemberId());
		vo.setFriendId(inputVO.getFriendId());
		int deleteCnt = friendsDao.deleteFromFriends(vo);
		
		if(deleteCnt > 0) {
			//성공
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("friends.deleteFriend.ss.success", gLocale),
					msgUtil.getMsgText("friends.deleteFriend.ss.success", gLocale));
			returnMap.put("result", result);
		} else {
			//삭제 건이 없는 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("friends.deleteFriend.le.notDeleted", gLocale),
					msgUtil.getMsgText("friends.deleteFriend.le.notDeleted", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 친구 상세정보 조회
	 */
	public Map<String,Object> getDetailInfo(FriendsEditReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//친구 상세정보 조회
		CommReqVO vo = new CommReqVO();
		vo.setgMemberId(inputVO.getFriendId());
		Map<String,Object> friendMap = membershipDao.selectMemberByMemberId(vo);
		List<Map<String,Object>> friendAttrList = membershipDao.selectMemberAttrByMemberId(vo);
		
		//내 정보 조회
		//vo = new CommReqVO();
		//vo.setgMemberId(inputVO.getgMemberId());
		//Map<String,Object> memberMap = membershipDao.selectMemberByMemberId(inputVO);
		
		if(friendMap != null) {
			//회원 존재
			
			if(friendAttrList != null && friendAttrList.size() > 0) {
				Map<String,String> friendAttrMap = new HashMap<String,String>();
				for(Map<String, Object> attrMap : friendAttrList){
					friendAttrMap.put((String)attrMap.get("attrName"), (String)attrMap.get("attrValue"));
				}
				friendMap.put("attr", friendAttrMap);
			}
			
			responseMap.put("friend", friendMap);
			//responseMap.put("member", memberMap);
			
			//TODO : 추가개발 필요
			TotalInfoReqVO harmonyReqVO = new TotalInfoReqVO();
			harmonyReqVO.setgMemberId(inputVO.getgMemberId());
			harmonyReqVO.setFriendId(inputVO.getFriendId());
			harmonyReqVO.setgLang(inputVO.getgLang());
			Map<String,Object> harmonyMap = harmonyService.getHarmonyResultMap(harmonyReqVO);
			responseMap.put("harmony", harmonyMap);
			/*
			harmonyMap.put("myBloodTypeCd", "B01001");
			harmonyMap.put("myNickName", "내 닉네임");
			harmonyMap.put("mySignCd", "S02");
			harmonyMap.put("myElementCd", "E02");
			harmonyMap.put("yourBloodTypeCd", "B01001");
			harmonyMap.put("yourNickName", "상대 닉네임");
			harmonyMap.put("yourSignCd", "S01");
			harmonyMap.put("yourElementCd", "E01");
			harmonyMap.put("totalHarmonyScore", 90);
			*/
			
			//추가 개발 필요
			/*
			Map<String,Object> etcMap = new HashMap<String,Object>();
			etcMap.put("participateCnt", 5);
			etcMap.put("votedCnt", 3);
			etcMap.put("curRank", 3);
			etcMap.put("totalRank", 5);
			responseMap.put("etc", etcMap);
			*/

			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//회원 미존재
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("membership.getMyPage.le.fail", gLocale),
					msgUtil.getMsgText("membership.getMyPage.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 윙크 보내기 */
	public Map<String,Object> sendWink(FriendsEditReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		boolean flag = false;
		
		// 자기 자신에게 쪽지를 보낼 수 없다.
		if(inputVO.getFriendId() == inputVO.getgMemberId()) {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("friends.sendWink.le.sameFriendId", gLocale),
					msgUtil.getMsgText("friends.sendWink.le.sameFriendId", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		Map<String,Object> winkItem = new HashMap<String,Object>();
		winkItem.put("senderId", inputVO.getgMemberId());
		winkItem.put("receiverId", inputVO.getFriendId());
		winkItem.put("itemCd", Constants.ItemCode.WINK);
		winkItem.put("itemAmount", 1);
		friendsDao.insertIntoItemSndRcvHist(winkItem);				//아이템 송수신 내역 Insert
		friendsDao.updateInventoryToIncreaseItemAmount(winkItem);	//수신자 인벤토리 업데이트 : item_amount 증가
		
		// gcm 발송
		if(!inputVO.isGcmPass())
			flag = sendGCMMsgByMemberId(inputVO.getFriendId(), inputVO.getgMemberId(), Constants.ActionType.WINK_MSG);
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("friends.sendWink.ss.success", gLocale),
				msgUtil.getMsgText("friends.sendWink.ss.success", gLocale));
		returnMap.put("result", result);
		
		//포인트 차감
		if(!inputVO.isPointPass())
			commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O206", -300, inputVO.getgLang());

		return returnMap;
	}
	
	/** 선물 보내기 */
	public Map<String,Object> sendGift(SendGiftReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		boolean flag = false;
		
		// 자기 자신에게 쪽지를 보낼 수 없다.
		if(inputVO.getFriendId() == inputVO.getgMemberId()) {
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("friends.sendGift.le.sameFriendId", gLocale),
					msgUtil.getMsgText("friends.sendGift.le.sameFriendId", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		Map<String,Object> giftItem = new HashMap<String,Object>();
		giftItem.put("senderId", inputVO.getgMemberId());
		giftItem.put("receiverId", inputVO.getFriendId());
		giftItem.put("itemCd", inputVO.getItemCd());
		giftItem.put("itemAmount", 1);
		friendsDao.insertIntoItemSndRcvHist(giftItem);				//아이템 송수신 내역 Insert
		friendsDao.updateInventoryToIncreaseItemAmount(giftItem);	//수신자 인벤토리 업데이트 : item_amount 증가
		
		flag = sendGCMMsgByMemberId(inputVO.getFriendId(), inputVO.getgMemberId(), Constants.ActionType.GIFT_MSG);
		
		//성공
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("friends.sendGift.ss.success", gLocale),
				msgUtil.getPropMsg("itemCode.T03001", gLocale) + msgUtil.getMsgText("friends.sendGift.ss.success", gLocale));
		returnMap.put("result", result);
		
		String usageCd = "";
		if(Constants.ItemCode.GIFT_FLOWER.equalsIgnoreCase(inputVO.getItemCd())) {
			usageCd = "O205";
		}
		
		//포인트 정보 업데이트
		commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, usageCd, -1000, inputVO.getgLang());

		return returnMap;
	}
	
	public boolean sendGCMMsgByMemberId(Integer receiverId, Integer senderId, String action) {
		Map<String,Object> receiver = this.membershipDao.selectSimpleMemberInfoByMemberId(receiverId);
		Map<String,Object> sender = this.membershipDao.selectSimpleMemberInfoByMemberId(senderId);
		
		StringBuilder sb = new StringBuilder();
		sb.append( (sender.get("nickName")) );
		if(Constants.ActionType.WINK_MSG.equalsIgnoreCase(action)) {
			sb.append( msgUtil.getPropMsg("friends.sendWink.winkNotice", gLocale));
		} else if (Constants.ActionType.GIFT_MSG.equalsIgnoreCase(action)) {
			sb.append( msgUtil.getPropMsg("friends.sendGift.giftNotice", gLocale));
		}
		
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", sb.toString());
		gcmParams.put("action", action);
		gcmParams.put("senderId", String.valueOf(senderId));
		gcmParams.put("senderSex", UTL.nvl(((String)sender.get("sex"))));
		gcmParams.put("senderPhotoUrl", UTL.nvl((String)sender.get("mainPhotoUrl")));
		
		if(receiver.get("gcmRegId") != null && !"".equals((String)receiver.get("gcmRegId"))) {
			UTL.sendGCM((String)receiver.get("gcmRegId"), gcmParams);
		} else {
			return false;
		}
		
		return true;
	}
	
	

}
