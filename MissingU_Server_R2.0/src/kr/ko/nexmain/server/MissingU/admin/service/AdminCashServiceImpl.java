package kr.ko.nexmain.server.MissingU.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.admin.dao.AdminCashDao;
import kr.ko.nexmain.server.MissingU.admin.model.CashItemHistoryVO;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.Constants.RequestURI;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.BaseService;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.friends.model.FriendsEditReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SendGiftReqVO;
import kr.ko.nexmain.server.MissingU.friends.service.FriendsService;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminCashServiceImpl extends BaseService implements AdminCashService {
	
	@Autowired
	private MsgUtil msgUtil;
	
	@Autowired
	private AdminCashDao adminCashDao;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private FriendsDao friendsDao; 
	
	/******************************************************************
	 * 윙크
	 ******************************************************************/
	@Override
	public Map<String, Object> sendWink(Map<String, Object> params) {
		
		FriendsEditReqVO inputVo = new FriendsEditReqVO();
		inputVo.setFriendId(UTL.convertToInt(params.get("targetMemberId"), 0));
		inputVo.setGcmPass("Y".equals(params.get("gcmPass") + "") ? true : false);
		inputVo.setgCountry(params.get("gCountry") + "");
		inputVo.setgLang(params.get("gLang") + "");
		inputVo.setgMemberId(UTL.convertToInt(params.get("memberId"), 0));
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Result result = null;
		Locale gLocale = Locale.KOREAN;
		
		// 포인트 차감 로직 패스 하는 경우 
		if(params.containsKey("pointPass") && "Y".equals(params.get("pointPass"))) {
			
			params.put("requestURI", RequestURI.SEND_WINK);
			Map<String,Object> needPoint = commonService.getMemberPointAndNeedPoint(params, Locale.KOREAN.toString());
			Result needPointResult = (Result)needPoint.get("result");
			if(needPointResult != null && Constants.ReturnCode.SUCCESS.equals(needPointResult.getRsltCd())) {
				// 윙크 발송
				inputVo.setPointPass(false);
				returnMap = friendsService.sendWink(inputVo);
			}else{
				// 포인트 부족
				result = new Result(
						Constants.ReturnCode.AUTH_ERROR,
						msgUtil.getMsgCd("comm.authError.lackOfPoint", gLocale),
						msgUtil.getMsgText("comm.authError.lackOfPoint", gLocale));
				returnMap.put("result", result);
			}
		}else{
			// 윙크 발송
			inputVo.setPointPass(true);
			returnMap = friendsService.sendWink(inputVo);
		}
		
		return returnMap;
	}

	@Override
	public Map<String,Object> deleteWink(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		try {
			int id = UTL.convertToInt(params.get("id"), 0);
			
			// 1. 발송 이력 삭제
			int count = adminCashDao.deleteCashItemHistory(id);
			
			Map<String,Object> winkItem = new HashMap<String,Object>();
			winkItem.put("senderId", params.get("senderMemberId"));
			winkItem.put("receiverId", params.get("receiverMemberId"));
			winkItem.put("itemCd", Constants.ItemCode.WINK);
			winkItem.put("itemAmount", 1);
			friendsDao.updateInventoryToDecreaseItemAmount(winkItem);
			
			// 2. 포인트 롤백 (불가, 여성 및 관리자 무료발송 기능 있고 해당 이력이 별도로 존재하지 않음)
		
			if(count > 0) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search"),
						msgUtil.getMsgText("comm.success.search"));
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.update"),
						msgUtil.getMsgText("comm.fail.update"));
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
			}
			
		}catch(Exception e) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"윙크 발송이력 롤백에 실패 했습니다.\n\n" + e.getMessage());
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> getWinkList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
		
		List<Map<String,CashItemHistoryVO>> dataList = adminCashDao.selectCashItemList(params);
		Integer totalCnt = adminCashDao.selectCashItemListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	/******************************************************************
	 * // 윙크
	 ******************************************************************/
	
	/******************************************************************
	 * 선물
	 ******************************************************************/
	@Override
	public Map<String, Object> sendGift(Map<String, Object> params) {
		
		SendGiftReqVO inputVo = new SendGiftReqVO();
		inputVo.setFriendId(UTL.convertToInt(params.get("targetMemberId"), 0));
		inputVo.setGcmPass("Y".equals(params.get("gcmPass") + "") ? true : false);
		inputVo.setgCountry(params.get("gCountry") + "");
		inputVo.setgLang(params.get("gLang") + "");
		inputVo.setgMemberId(UTL.convertToInt(params.get("memberId"), 0));
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Result result = null;
		Locale gLocale = Locale.KOREAN;
		
		// 포인트 차감 로직 패스 하는 경우 
		if(params.containsKey("pointPass") && "Y".equals(params.get("pointPass"))) {
			
			params.put("requestURI", RequestURI.SEND_GIFT);
			Map<String,Object> needPoint = commonService.getMemberPointAndNeedPoint(params, Locale.KOREAN.toString());
			Result needPointResult = (Result)needPoint.get("result");
			if(needPointResult != null && Constants.ReturnCode.SUCCESS.equals(needPointResult.getRsltCd())) {
				// 윙크 발송
				inputVo.setPointPass(false);
				returnMap = friendsService.sendGift(inputVo);
			}else{
				// 포인트 부족
				result = new Result(
						Constants.ReturnCode.AUTH_ERROR,
						msgUtil.getMsgCd("comm.authError.lackOfPoint", gLocale),
						msgUtil.getMsgText("comm.authError.lackOfPoint", gLocale));
				returnMap.put("result", result);
			}
		}else{
			// 윙크 발송
			inputVo.setPointPass(true);
			returnMap = friendsService.sendGift(inputVo);
		}
		
		return returnMap;
	}

	@Override
	public Map<String,Object> deleteGift(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		try {
			int id = UTL.convertToInt(params.get("id"), 0);
			
			// 1. 발송 이력 삭제
			int count = adminCashDao.deleteCashItemHistory(id);
			
			Map<String,Object> winkItem = new HashMap<String,Object>();
			winkItem.put("senderId", params.get("senderMemberId"));
			winkItem.put("receiverId", params.get("receiverMemberId"));
			winkItem.put("itemCd", Constants.ItemCode.GIFT_FLOWER);
			winkItem.put("itemAmount", 1);
			friendsDao.updateInventoryToDecreaseItemAmount(winkItem);
			
			// 2. 포인트 롤백 (불가, 여성 및 관리자 무료발송 기능 있고 해당 이력이 별도로 존재하지 않음)
		
			if(count > 0) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search"),
						msgUtil.getMsgText("comm.success.search"));
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("comm.fail.update"),
						msgUtil.getMsgText("comm.fail.update"));
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
			}
			
		}catch(Exception e) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					"LE",
					"선물(꽃다발) 발송이력 롤백에 실패 했습니다.\n\n" + e.getMessage());
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> getGiftList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
		
		List<Map<String,CashItemHistoryVO>> dataList = adminCashDao.selectCashItemList(params);
		Integer totalCnt = adminCashDao.selectCashItemListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}

	/******************************************************************
	 * // 선물
	 ******************************************************************/
	
	
	@Override
	public Map<String, Object> getPointList(Map<String, Object> params) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이징 변수 int로 변환
		makePaging(params);
		
		List<Map<String,Object>> dataList = adminCashDao.selectPointList(params);
		Integer totalCnt = adminCashDao.selectPointListCnt(params);
		
		responseMap.put("totalCnt", totalCnt); 
		responseMap.put("dataList", dataList);
		
		if(dataList != null && dataList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search"),
					msgUtil.getMsgText("comm.success.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}
		
		return returnMap;
	}
}
