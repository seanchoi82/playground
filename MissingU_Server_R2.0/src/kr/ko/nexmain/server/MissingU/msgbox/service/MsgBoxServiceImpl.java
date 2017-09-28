package kr.ko.nexmain.server.MissingU.msgbox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.service.ChatMainServiceImpl;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.msgbox.dao.MsgBoxDao;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgboxReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.LnkMsgboxMsgVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgListReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.OpenMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.SendMsgReqVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class MsgBoxServiceImpl implements MsgBoxService{
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private MsgBoxDao msgBoxDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private FriendsDao friendsDao;
	@Autowired
	private MsgUtil msgUtil;
	
	private Logger log = LoggerFactory.getLogger(ChatMainServiceImpl.class);
	private Locale gLocale;
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(시작)
	///----------------------------------------------------------------------------------------------------------------------------------------///	
	/** 쪽지 리스트 조회*/
	public Map<String,Object> getConversMsgBoxList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> msgBoxList = msgBoxDao.selectMessageBoxListByMemberId(inputVO.getgMemberId());
		responseMap.put("msgbox", msgBoxList);
		
		if(msgBoxList != null && msgBoxList.size() > 0) {
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
					msgUtil.getMsgCd("msgbox.getMsgBoxList.le.noResult", gLocale),
					msgUtil.getMsgText("msgbox.getMsgBoxList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 쪽지 대화 리스트 조회*/
	public Map<String,Object> getConversMsgConversationList(MsgBoxConversVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 모두 읽음 처리
		msgBoxDao.updateMessageBoxReadAll(inputVO);
		
		// 데이터 가져오기
		List<Map<String,Object>> msgBoxList = msgBoxDao.selectMessageBoxConversationByMemberId(inputVO);
		responseMap.put("msgbox", msgBoxList);
		
		if(msgBoxList != null && msgBoxList.size() > 0) {
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
					msgUtil.getMsgCd("msgbox.getMsgBoxList.le.noResult", gLocale),
					msgUtil.getMsgText("msgbox.getMsgBoxList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 쪽지 전송 */
	public Map<String,Object> sendConversMsg(MsgBoxConversSendVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		boolean flag = false;
		try {
			
			// 자기 자신에게 쪽지를 보낼 수 없다.
			if(inputVO.getTargetMemberId() == inputVO.getgMemberId()) {
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("msgbox.sendMsg.le.sameReceiverId", gLocale),
						msgUtil.getMsgText("msgbox.sendMsg.le.sameReceiverId", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
			
			// 상대방이 볼 내용
			long send = msgBoxDao.insertMessageBoxMsg(inputVO);
			// 내가 볼 내용
			long sendEcho = msgBoxDao.insertMessageBoxMsgEcho(inputVO);
			
			if(send > 0 && sendEcho > 0) {
				//GCM 메세지 전송
				flag = sendGCMMsgConversByMemberId(inputVO.getTargetMemberId(), inputVO.getMsgText(), inputVO.getgMemberId(), Constants.ActionType.MSG);
				
				inputVO.setMsgId(send);
				
				// 입력한 정보 회신
				Map<String,Object> msg = msgBoxDao.selectMsgConvers(inputVO);
				returnMap.put("msg", msg);
				
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("msgbox.sendMsg.le.error", gLocale),
						msgUtil.getMsgText("msgbox.sendMsg.le.error", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("msgbox.sendMsg.le.error", gLocale),
					msgUtil.getMsgText("msgbox.sendMsg.le.error", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
//		if(flag) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("msgbox.sendMsg.ss.success", gLocale),
					msgUtil.getMsgText("msgbox.sendMsg.ss.success", gLocale));
			returnMap.put("result", result);
			
			//포인트 정보 업데이트
			commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O203", -100, inputVO.getgLang());
			return returnMap;
//		} else {
//			//잘못된 수신자ID로 인한 실패
//			Result result = new Result(
//					Constants.ReturnCode.LOGIC_ERROR,
//					msgUtil.getMsgCd("msgbox.sendMsg.le.wrongReceiverId", gLocale),
//					msgUtil.getMsgText("msgbox.sendMsg.le.wrongReceiverId", gLocale));
//			returnMap.put("result", result);
//			return returnMap;
//		}
	}
	
	/** 쪽지함 삭제*/
	public Map<String,Object> deleteConversMsg(MsgBoxConversVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		msgBoxDao.deleteConversMsg(inputVO);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("msgbox.deleteMsgbox.ss.success", gLocale),
				msgUtil.getMsgText("msgbox.deleteMsgbox.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(끝)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	
	/** 쪽지함 리스트 조회*/
	public Map<String,Object> getMsgBoxList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> msgBoxList = msgBoxDao.selectMsgBoxListByMemberId(inputVO.getgMemberId());
		responseMap.put("msgbox", msgBoxList);
		
		if(msgBoxList != null && msgBoxList.size() > 0) {
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
					msgUtil.getMsgCd("msgbox.getMsgBoxList.le.noResult", gLocale),
					msgUtil.getMsgText("msgbox.getMsgBoxList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 쪽지 전송 */
	public Map<String,Object> sendMsg(SendMsgReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		boolean flag = false;
		try {
			
			// 자기 자신에게 쪽지를 보낼 수 없다.
			if(inputVO.getReceiverId() == inputVO.getgMemberId()) {
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("msgbox.sendMsg.le.sameReceiverId", gLocale),
						msgUtil.getMsgText("msgbox.sendMsg.le.sameReceiverId", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
			
			//쪽지 Insert
			MsgVO msgVO = new MsgVO();
			msgVO.setReceiverId(inputVO.getReceiverId());
			msgVO.setSenderId(inputVO.getgMemberId());
			msgVO.setMsgText(inputVO.getMsg());
			Long msgId = msgBoxDao.insertIntoMsg(msgVO);
			
			//수신자 동일 쪽지함 미존재 시 생성
			MsgBoxVO msgboxForReceiver = new MsgBoxVO();
			msgboxForReceiver.setMemberId(inputVO.getReceiverId());
			msgboxForReceiver.setSenderId(inputVO.getReceiverId());
			msgboxForReceiver.setStatus("A");
			
			Map<String,Object> msgboxReceiverMap = msgBoxDao.selectMsgboxByMemberIdAndSenderId(msgboxForReceiver);
			Long msgboxIdOfReceiver = new Long("0");
			if(msgboxReceiverMap == null || (Long)msgboxReceiverMap.get("msgboxId") < 0) {
				msgboxIdOfReceiver = msgBoxDao.insertIntoMsgbox(msgboxForReceiver);
			} else {
				msgboxIdOfReceiver = ((Long)msgboxReceiverMap.get("msgboxId"));
			}
			
			//송신자 동일 쪽지함 미존재 시 생성
			MsgBoxVO msgboxForSender = new MsgBoxVO();
			msgboxForSender.setMemberId(inputVO.getgMemberId());
			msgboxForSender.setSenderId(inputVO.getgMemberId());
			msgboxForSender.setStatus("A");
			
			Map<String,Object> msgboxSenderMap = msgBoxDao.selectMsgboxByMemberIdAndSenderId(msgboxForSender);
			Long msgboxIdOfSender = new Long("0");
			if(msgboxSenderMap == null || ((Long)msgboxSenderMap.get("msgboxId")).intValue() < 0) {
				msgboxIdOfSender = msgBoxDao.insertIntoMsgbox(msgboxForSender);
			} else {
				msgboxIdOfSender = ((Long)msgboxSenderMap.get("msgboxId"));
			}
			
			//링크_쪽지함_쪽지 Insert
			LnkMsgboxMsgVO lnkMsgboxMsgVO = new LnkMsgboxMsgVO();
			lnkMsgboxMsgVO.setMsgboxId(msgboxIdOfReceiver);
			lnkMsgboxMsgVO.setMsgId(msgId);
			msgBoxDao.insertIntoLnkMsgboxMsg(lnkMsgboxMsgVO);
			
			lnkMsgboxMsgVO = new LnkMsgboxMsgVO();
			lnkMsgboxMsgVO.setMsgboxId(msgboxIdOfSender);
			lnkMsgboxMsgVO.setMsgId(msgId);
			msgBoxDao.insertIntoLnkMsgboxMsg(lnkMsgboxMsgVO);
			
			//GCM 메세지 전송
			flag = sendGCMMsgByMemberId(inputVO.getReceiverId(), inputVO.getMsg(), inputVO.getgMemberId(), Constants.ActionType.MSG, msgboxIdOfReceiver, msgboxIdOfSender);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("msgbox.sendMsg.le.error", gLocale),
					msgUtil.getMsgText("msgbox.sendMsg.le.error", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
//		if(flag) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("msgbox.sendMsg.ss.success", gLocale),
					msgUtil.getMsgText("msgbox.sendMsg.ss.success", gLocale));
			returnMap.put("result", result);
			
			//포인트 정보 업데이트
			commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O203", -100, inputVO.getgLang());
			return returnMap;
//		} else {
//			//잘못된 수신자ID로 인한 실패
//			Result result = new Result(
//					Constants.ReturnCode.LOGIC_ERROR,
//					msgUtil.getMsgCd("msgbox.sendMsg.le.wrongReceiverId", gLocale),
//					msgUtil.getMsgText("msgbox.sendMsg.le.wrongReceiverId", gLocale));
//			returnMap.put("result", result);
//			return returnMap;
//		}
	}
	
	public boolean sendGCMMsgByMemberId(Integer receiverId, String msg, Integer senderId, String action, Long msgboxIdOfReceiver, Long msgboxIdOfSender) {
		Map<String,Object> receiver = this.membershipDao.selectSimpleMemberInfoByMemberId(receiverId);
		Map<String,Object> sender = this.membershipDao.selectSimpleMemberInfoByMemberId(senderId);
		
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", msg);
		gcmParams.put("action", action);
		gcmParams.put("senderId", String.valueOf(senderId));
		gcmParams.put("receiverId", String.valueOf(receiverId));
		gcmParams.put("senderSex", UTL.nvl(((String)sender.get("sex"))));
		gcmParams.put("senderPhotoUrl", UTL.nvl((String)sender.get("mainPhotoUrl")));
		
		if(receiver.get("gcmRegId") != null && !"".equals((String)receiver.get("gcmRegId"))) {
			gcmParams.put("msgboxId", String.valueOf(msgboxIdOfReceiver));
			UTL.sendGCM((String)receiver.get("gcmRegId"), gcmParams);
			
			gcmParams.remove("msgboxId");
			gcmParams.put("msgboxId", String.valueOf(msgboxIdOfSender));
			UTL.sendGCM((String)sender.get("gcmRegId"), gcmParams);
			
		} else {
			return false;
		}
		
		return true;
	}
	
	public boolean sendGCMMsgConversByMemberId(Integer receiverId, String msg, Integer senderId, String action) {
		Map<String,Object> receiver = this.membershipDao.selectSimpleMemberInfoByMemberId(receiverId);
		Map<String,Object> sender = this.membershipDao.selectSimpleMemberInfoByMemberId(senderId);
		
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", msg);
		gcmParams.put("action", action);
		gcmParams.put("senderId", String.valueOf(senderId));
		gcmParams.put("receiverId", String.valueOf(receiverId));
		gcmParams.put("senderSex", UTL.nvl(((String)sender.get("sex"))));
		gcmParams.put("senderPhotoUrl", UTL.nvl((String)sender.get("mainPhotoUrl")));
		
		if(receiver.get("gcmRegId") != null && !"".equals((String)receiver.get("gcmRegId"))) {
			UTL.sendGCM((String)receiver.get("gcmRegId"), gcmParams);
		} else {
			return false;
		}
		
		return true;
	}
	
	/** 쪽지 리스트 조회*/
	public Map<String,Object> getMsgList(MsgListReqVO inputVO) {
		
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> msgList = msgBoxDao.selectMsgListByMsgboxId(inputVO);
		
		if(msgList != null && msgList.size() > 0) {
			int updatedCnt = msgBoxDao.updateMsgAsRead(inputVO.getMsgboxId());
			//새로운 메세지가 있는 경우만 포인트 차감
			if(updatedCnt > 0) {
				//포인트 정보 업데이트
				commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O204", -300, inputVO.getgLang());
			}
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			responseMap.put("msg", msgList);
			returnMap.put("response", responseMap);
			
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("msgbox.getMsgList.le.noResult", gLocale),
					msgUtil.getMsgText("msgbox.getMsgList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 쪽지함 삭제*/
	public Map<String,Object> deleteMsgbox(DeleteMsgboxReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//MsgboxID 조회
		MsgBoxVO msgboxVO = new MsgBoxVO();
		msgboxVO.setMemberId(inputVO.getgMemberId());
		msgboxVO.setSenderId(inputVO.getgMemberId());
		msgboxVO.setStatus("A");
		Map<String,Object> msgbox = msgBoxDao.selectMsgboxByMemberIdAndSenderId(msgboxVO);
		
		//LNK_MSGBOX_MSG 삭제
		LnkMsgboxMsgVO vo = new LnkMsgboxMsgVO();
		vo.setMsgboxId((Long)msgbox.get("msgboxId"));
		// vo.setMsgId(inputVO.getMsgboxId());
		msgBoxDao.deleteLnkMsgboxMsgByMsgId(vo);
		/*
		MsgBoxVO vo = new MsgBoxVO();
		vo.setMsgboxId(inputVO.getMsgboxId());
		vo.setStatus("D");	//삭제
		
		msgBoxDao.updateMsgboxStatus(vo);
		*/
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("msgbox.deleteMsgbox.ss.success", gLocale),
				msgUtil.getMsgText("msgbox.deleteMsgbox.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 쪽지 삭제*/
	public Map<String,Object> deleteMsg(DeleteMsgReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//MsgboxID 조회
		MsgBoxVO msgboxVO = new MsgBoxVO();
		msgboxVO.setMemberId(inputVO.getgMemberId());
		msgboxVO.setSenderId(inputVO.getgMemberId());
		msgboxVO.setStatus("A");
		Map<String,Object> msgbox = msgBoxDao.selectMsgboxByMemberIdAndSenderId(msgboxVO);
		
		//LNK_MSGBOX_MSG 삭제
		LnkMsgboxMsgVO vo = new LnkMsgboxMsgVO();
		vo.setMsgboxId((Long)msgbox.get("msgboxId"));
		vo.setMsgId(inputVO.getMsgId());
		msgBoxDao.deleteLnkMsgboxMsgByMsgId(vo);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("msgbox.deleteMsgbox.ss.success", gLocale),
				msgUtil.getMsgText("msgbox.deleteMsgbox.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 내 친구 리스트 조회*/
	public Map<String,Object> getMyFriendsList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> myFriendsList = friendsDao.selectMyFriendsList(inputVO);
		
		if(myFriendsList != null && myFriendsList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			responseMap.put("member", myFriendsList);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("msgbox.myFriends.le.noResult", gLocale),
					msgUtil.getMsgText("msgbox.myFriends.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 내 쪽지 리스트 조회 */
	public Map<String,Object> myMsgList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> myMsgList = msgBoxDao.selectMyMsgList(inputVO);
		
		if(myMsgList != null && myMsgList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			responseMap.put("msg", myMsgList);
			returnMap.put("response", responseMap);
			
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 쪽지 개봉 */
	public Map<String,Object> openMsg(OpenMsgReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		Map<String,Object> myMsg = msgBoxDao.selectMsgByMsgId(inputVO.getMsgId());
		Integer gMemberId = inputVO.getgMemberId();
		
		//조회 결과가 있는 경우
		if(myMsg != null) {
			log.debug("gMemberId : {}", gMemberId);
			log.debug("receiverId : {}", (Integer)myMsg.get("receiverId"));
			
			//수신자인 경우만 읽음으로 업데이트
			if(gMemberId.intValue() == ((Integer)myMsg.get("receiverId")).intValue()) {
				log.info("Msg I Received");
				int updatedCnt = msgBoxDao.updateMsgAsReadByMsgId(inputVO.getMsgId());
				//새로운 메세지가 있는 경우만 포인트 차감
				if(updatedCnt > 0) {
					//포인트 정보 업데이트
					commonService.updatePointInfo(gMemberId, Constants.EventTypeCd.OUTCOME, "O204", -300, inputVO.getgLang());
				}
			} else {
				log.info("Msg I Sent");
			}
			
			//업데이트 된 결과 다시 조회
			myMsg = msgBoxDao.selectMsgByMsgId(inputVO.getMsgId());
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			responseMap.put("msg", myMsg);
			returnMap.put("response", responseMap);
			
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search", gLocale),
					msgUtil.getMsgText("comm.fail.search", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

}