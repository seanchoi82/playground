package kr.ko.nexmain.server.MissingU.chat.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.c2dm.C2dmHelper;
import kr.ko.nexmain.server.MissingU.chat.dao.ChatDao;
import kr.ko.nexmain.server.MissingU.chat.dao.ChatMainDao;
import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.ChatroomMember;
import kr.ko.nexmain.server.MissingU.chat.model.CreateRoomReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.KickOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomInOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SendChatMsgReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;
import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.friends.service.FriendsService;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

@Service
@Transactional(timeout=15)
public class ChatServiceImpl implements ChatService {

	@Autowired
	private CommonService commonService;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private MembershipDao membershipDao;
	
	@Autowired
	private MsgUtil msgUtil;
	
	private Logger log = LoggerFactory.getLogger(ChatMainServiceImpl.class);
	private Locale gLocale;
	
	/** 채팅방 리스트 조회*/
	public Map<String,Object> getRoomList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		List<Map<String,Object>> roomList = chatDao.selectChatRoomList(inputVO);
		responseMap.put("room", roomList);
		
		if(roomList != null && roomList.size() > 0) {
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
					msgUtil.getMsgCd("chat.getRoomList.le.noResult", gLocale),
					msgUtil.getMsgText("chat.getRoomList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 채팅방 생성 */
	public Map<String,Object> createRoom(CreateRoomReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		inputVO.setRoomMasterId(inputVO.getgMemberId());
		inputVO.setMemberId(inputVO.getgMemberId());
		inputVO.setMasterYn(Constants.YES); //방 생성시 방장이므로 무조건 Y로 셋팅
		Integer roomId = chatDao.insertIntoRoom(inputVO);
		
		ChatroomMember vo = new ChatroomMember();
		vo.setRoomId(inputVO.getRoomId());
		vo.setMemberId(inputVO.getgMemberId());
		vo.setMasterYn(Constants.YES);
		chatDao.insertIntoRoomMember(vo);
		
		if(roomId != null && roomId > 0) {
			//성공
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("chat.createRoom.ss.success", gLocale),
					msgUtil.getMsgText("chat.createRoom.ss.success", gLocale));
			returnMap.put("result", result);
			responseMap.put("roomId", roomId);
			returnMap.put("response", responseMap);
			
			//포인트 정보 업데이트
			commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O201", -100, inputVO.getgLang());
		} else {
			//실패
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("chat.createRoom.le.fail", gLocale),
					msgUtil.getMsgText("chat.createRoom.le.fail", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 채팅방 입장처리 */
	public Map<String,Object> doRoomInProcess(RoomInOutReqVO inputVO) {
		
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		int roomId = inputVO.getRoomId();
		int memberId = inputVO.getgMemberId();
		
		ChatRoom room = chatDao.selectRoomByRoomId(roomId);
		if(room == null) {
			//채팅방 미존재
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("chat.roomIn.le.roomNotExist", gLocale),
					msgUtil.getMsgText("chat.roomIn.le.roomNotExist", gLocale));
			returnMap.put("result", result);
			return returnMap;
		
		} else {
			
			// 기존 회원중에 재입장인지 여부
			boolean reconnect = false;
			boolean isAliave = false;
			boolean isReject = false;
			for(ChatroomMember mem : this.chatDao.selectRoomMemberListByRoomId(roomId)) {
				if(mem.getMemberId().compareTo(Integer.valueOf(memberId)) == 0) {
					reconnect = true;
					isAliave = mem.getStatus().equals("A");
					isReject = mem.getStatus().equals("R");
					break;
				}
			}
			
			// 강퇴된 경우 재입장 불가
			if(isReject) {
				// 입장 실패
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("chat.roomIn.le.roomInByReject", gLocale),
						msgUtil.getMsgText("chat.roomIn.le.roomInByReject", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
			// 튕겨서 나간 경우 바로 재입장
			else if(reconnect && isAliave) {
				
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("chat.roomIn.ss.success", gLocale),
						msgUtil.getMsgText("chat.roomIn.ss.success", gLocale));
				returnMap.put("result", result);
				return returnMap;
				
			// 방 나가기로 했던 회원이면 재입장시 포인트 소진 안함
			}else if(reconnect) {
				
				if(room.getMaxUserCnt() <= room.getCurUserCnt()) {
					//채팅방 인원이 만원인 경우
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("chat.roomIn.le.roomFull", gLocale),
							msgUtil.getMsgText("chat.roomIn.le.roomFull", gLocale));
					returnMap.put("result", result);
					return returnMap;
				} else {
					// 방 재입장
					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("roomId", roomId);
					params.put("memberId", memberId);
					int updateCnt = this.chatDao.updateRoomMemberByRoomId(params);
					
					if(updateCnt > 0) {
						// 방 입장 카운트 증가
						this.chatDao.updateRoomToIncreaseCurUserCnt(roomId);
						// 입장 성공
						Result result = new Result(
								Constants.ReturnCode.SUCCESS,
								msgUtil.getMsgCd("chat.roomIn.ss.success", gLocale),
								msgUtil.getMsgText("chat.roomIn.ss.success", gLocale));
						returnMap.put("result", result);
						return returnMap;
					}else{
						// 입장 실패
						Result result = new Result(
								Constants.ReturnCode.LOGIC_ERROR,
								msgUtil.getMsgCd("chat.roomIn.le.roomIn", gLocale),
								msgUtil.getMsgText("chat.roomIn.le.roomIn", gLocale));
						returnMap.put("result", result);
						return returnMap;
					}
				}
				
			// 일반적인 방입장
			}else{
				if(room.getMaxUserCnt() <= room.getCurUserCnt()) {
					//채팅방 인원이 만원인 경우
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("chat.roomIn.le.roomFull", gLocale),
							msgUtil.getMsgText("chat.roomIn.le.roomFull", gLocale));
					returnMap.put("result", result);
					return returnMap;
				} else {
					
					// 인원수 증가
					this.chatDao.updateRoomToIncreaseCurUserCnt(roomId);
					
					int insertedCnt = 0;
					ChatroomMember roomMemberForInsert = new ChatroomMember();
					roomMemberForInsert.setRoomId(roomId);
					roomMemberForInsert.setMemberId(memberId);
					roomMemberForInsert.setStatus("A");
					if(room.getCurUserCnt() == 0) {
						roomMemberForInsert.setMasterYn("Y");
					}
					insertedCnt = this.chatDao.insertIntoRoomMember(roomMemberForInsert);
					
					if(insertedCnt > 0) {
						sendGCMMsgByChatRoomId(roomId, null, memberId, Constants.ActionType.ROOM_IN_MSG);
					}
					
					//포인트 정보 업데이트
					commonService.updatePointInfo(memberId, Constants.EventTypeCd.OUTCOME, "O202", -100, inputVO.getgLang());
					
					Result result = new Result(
							Constants.ReturnCode.SUCCESS,
							msgUtil.getMsgCd("chat.roomIn.ss.success", gLocale),
							msgUtil.getMsgText("chat.roomIn.ss.success", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
			}
		}
		
			/*
			
			boolean sameMemberExist = false;
			//mu_chatroom_member 테이블에 해당 roomId, memberId 와 동일한 row를 제외하고 모두 삭제
			List<ChatroomMember> roomMemberList = this.chatDao.selectRoomMemberListByMemberId(memberId);
			for(ChatroomMember roomMember : roomMemberList) {
				int deletedCnt = 0;
				if(roomId == roomMember.getRoomId() && memberId == roomMember.getMemberId()) {
					sameMemberExist = true;
				} else {
					deletedCnt = this.chatDao.deleteFromRoomMemberByMemberId(roomMember);
				}
				
				if(deletedCnt > 0) {
					this.chatDao.updateRoomToDecreaseCurUserCnt(roomMember.getRoomId()); //room.cur_user_cnt - 1 처리
				}
			}
			
			if(sameMemberExist) {
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("chat.roomIn.ss.success", gLocale),
						msgUtil.getMsgText("chat.roomIn.ss.success", gLocale));
				returnMap.put("result", result);
				return returnMap;
			} else {
				if(room.getMaxUserCnt() <= room.getCurUserCnt()) {
					//채팅방 인원이 만원인 경우
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("chat.roomIn.le.roomFull", gLocale),
							msgUtil.getMsgText("chat.roomIn.le.roomFull", gLocale));
					returnMap.put("result", result);
					return returnMap;
				} else {
					this.chatDao.updateRoomToIncreaseCurUserCnt(roomId);
					
					int insertedCnt = 0;
					ChatroomMember roomMemberForInsert = new ChatroomMember();
					roomMemberForInsert.setRoomId(roomId);
					roomMemberForInsert.setMemberId(memberId);
					if(room.getCurUserCnt() == 0) {
						roomMemberForInsert.setMasterYn("Y");
					}
					insertedCnt = this.chatDao.insertIntoRoomMember(roomMemberForInsert);
					
					if(insertedCnt > 0) {
						sendGCMMsgByChatRoomId(roomId, null, memberId, Constants.ActionType.ROOM_IN_MSG);
					}
					
					//포인트 정보 업데이트
					commonService.updatePointInfo(memberId, Constants.EventTypeCd.OUTCOME, "O202", -100, inputVO.getgLang());
					
					Result result = new Result(
							Constants.ReturnCode.SUCCESS,
							msgUtil.getMsgCd("chat.roomIn.ss.success", gLocale),
							msgUtil.getMsgText("chat.roomIn.ss.success", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
			}
			
		}
		
		//returnMap.put("response", responseMap);
		//return returnMap;
		 * */
	}
	
	/** 채팅방 퇴장처리 */
	public Map<String,Object> doRoomOutProcess(RoomInOutReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		int roomId = inputVO.getRoomId();
		int memberId = inputVO.getgMemberId();
		
		//room_member 테이블에서 해당 User 삭제
		ChatroomMember roomMemberForDelete = new ChatroomMember();
		roomMemberForDelete.setRoomId(roomId);
		roomMemberForDelete.setMemberId(memberId);
		roomMemberForDelete.setMasterYn("");
		roomMemberForDelete.setStatus("E");
		int updateCnt = this.chatDao.updateFromRoomMemberByMemberId(roomMemberForDelete);
		
		if(updateCnt > 0) {
			//room 테이블 업데이트(cur_user -1)
			this.chatDao.updateRoomToDecreaseCurUserCnt(roomId);
			
			//채팅방에 사람이 남아 있고, master_yn으로 order by 하여 첫 번째 사용자가 마스터가 아니면 'Y'로 업데이트
			List<ChatroomMember> roomMemberList = this.chatDao.selectRoomMemberListByRoomId(roomId);
			if(roomMemberList != null && !roomMemberList.isEmpty()) {
				ChatroomMember roomMember = roomMemberList.get(0);
				if(!Constants.YES.equalsIgnoreCase((roomMember.getMasterYn()))) {
					roomMember.setMasterYn("Y");
					this.chatDao.updateRoomMemberMasterYn(roomMember);
					//room.member_id를 새로운 방장으로 업데이트
					this.chatDao.updateRoomMemberId(roomMember);
				}
				
				//퇴장알림 GCM메세지 전송
				sendGCMMsgByChatRoomId(roomId, null, memberId, Constants.ActionType.ROOM_OUT_MSG);
			}
			
		}
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("chat.roomOut.ss.success", gLocale),
				msgUtil.getMsgText("chat.roomOut.ss.success", gLocale));
		returnMap.put("result", result);
		
		return returnMap;
	}
	
	/** 채팅 메세지 전송 */
	public Map<String,Object> sendChatMsg(SendChatMsgReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		int roomId = inputVO.getRoomId();
		int memberId = inputVO.getgMemberId();
		String msg = inputVO.getChatMsg();
		
		// 채팅방 업데이트
		chatDao.updateUpdateDateForSendMsg(roomId);
		
		sendGCMMsgByChatRoomId(roomId, msg, memberId, Constants.ActionType.CHAT_MSG);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("chat.sendMsg.ss.success", gLocale),
				msgUtil.getMsgText("chat.sendMsg.ss.success", gLocale));
		returnMap.put("result", result);
		return returnMap;
	}
	
	/** 채팅방 강제퇴장처리 */
	public Map<String,Object> doKickOutProcess(KickOutReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		int roomId = inputVO.getRoomId();
		int memberId = inputVO.getMemberId();
		
		//강제퇴장알림 GCM메세지 전송
		sendGCMMsgByChatRoomId(roomId, null, memberId, Constants.ActionType.KICK_OUT_MSG);
		
		//room_member 테이블에서 해당 User 삭제
		ChatroomMember roomMemberForDelete = new ChatroomMember();
		roomMemberForDelete.setRoomId(roomId);
		roomMemberForDelete.setMemberId(memberId);
		roomMemberForDelete.setMasterYn("");
		roomMemberForDelete.setStatus("R");
		int updatedCnt = this.chatDao.updateFromRoomMemberByMemberId(roomMemberForDelete);
		
		if(updatedCnt > 0) {
			//room 테이블 업데이트(cur_user -1)
			this.chatDao.updateRoomToDecreaseCurUserCnt(roomId);
		}
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("chat.kickOut.ss.success", gLocale),
				msgUtil.getMsgText("chat.kickOut.ss.success", gLocale));
		returnMap.put("result", result);
		responseMap.put("memberId", memberId);
		returnMap.put("response", responseMap);
		
		return returnMap;
	}
	
	
	public boolean sendGCMMsgByChatRoomId(Integer roomId, String msg, Integer senderId, String action) {
		
		Map<String,Object> sender = this.chatDao.selectSimpleMemberInfoByMemberId(senderId);
		
		if(Constants.ActionType.ROOM_IN_MSG.equals(action)) {
			msg = (String)sender.get("nickName") +  msgUtil.getPropMsg("chat.roomIn.roomInNotice", gLocale);
		} else if(Constants.ActionType.ROOM_OUT_MSG.equals(action)) {
			msg = (String)sender.get("nickName") +  msgUtil.getPropMsg("chat.roomOut.roomOutNotice", gLocale);
		} else if(Constants.ActionType.KICK_OUT_MSG.equals(action)) {
			msg = (String)sender.get("nickName") +  msgUtil.getPropMsg("chat.kickOut.kickOutNotice", gLocale);
		}
		
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", msg);
		gcmParams.put("action", action);
		gcmParams.put("memberId", String.valueOf(sender.get("memberId")));
		gcmParams.put("roomId", String.valueOf(roomId));
		gcmParams.put("sex", UTL.nvl((String)sender.get("sex")));
		gcmParams.put("mainPhotoUrl", UTL.nvl((String)sender.get("mainPhotoUrl")));
		gcmParams.put("membership", String.valueOf(sender.get("membership")));
		gcmParams.put("certification", String.valueOf(sender.get("certification")));
		
		List<ChatroomMember> roomMemberList = this.chatDao.selectRoomMemberListByRoomId(roomId);
		if(Constants.ActionType.ROOM_OUT_MSG.equals(action)) {
			for(ChatroomMember member : roomMemberList) {
				if(Constants.YES.equalsIgnoreCase(member.getMasterYn())) {
					gcmParams.put("masterId", String.valueOf(member.getMemberId()));
					break;
				}
			}
		}
		
		for(ChatroomMember member : roomMemberList) {
			if(member.getGcmRegId() != null && !"".equals(member.getGcmRegId())) {
				UTL.sendGCM(member.getGcmRegId(), gcmParams);
			}
		}
		
		return true;
	}

	/** 채팅방 지우기 */
	@Override
	public int cleanOldChatroom() {
		return chatDao.cleanOldChatroom();
	}

	
}