package kr.ko.nexmain.server.MissingU.chat.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.c2dm.C2dmHelper;
import kr.ko.nexmain.server.MissingU.chat.dao.ChatMainDao;
import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;
import kr.ko.nexmain.server.MissingU.common.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

public class ChatMainServiceImpl implements ChatMainService {

	private ChatMainDao chatMainDao;
	private C2dmHelper 	c2dmHelper;
	private MessageSource messageSource;
	private MessageSourceAccessor accessor;
	private PlatformTransactionManager transactionManager;
	
	private Logger log = LoggerFactory.getLogger(ChatMainServiceImpl.class);
	
	public void setChatMainDao(ChatMainDao chatMainDao) {
		this.chatMainDao = chatMainDao;
	}
	
	public void setC2dmHelper(C2dmHelper c2dmHelper) {
		this.c2dmHelper = c2dmHelper;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		accessor = new MessageSourceAccessor(messageSource);
		this.messageSource = messageSource;
	}
	
	public MessageSourceAccessor getMessageSourceAccessor() {
		return this.accessor;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @param roomId
	 * @param msg
	 * @param memberId
	 * @return
	 */
	/*
	public String sendC2dmMsgByChatRoomId(String roomId, String msg, String memberId) {
		try {
			
			List<Room> roomItem = this.chatMainDao.getUserRegIdListByRoomId(roomId);
			
			//c2dmHelper로 보낼 파라미터 작성
			List<List<String>> c2dmParamList = new ArrayList<List<String>>();
			List<String> c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"action");
			c2dmParamItem.add(1,Constants.ActionType.CHAT_MSG);
			c2dmParamList.add(c2dmParamItem);
			
			c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"sender_id");
			c2dmParamItem.add(1,memberId);
			c2dmParamList.add(c2dmParamItem);
			
			c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"sender_nick");
			c2dmParamItem.add(1,"");
			c2dmParamList.add(c2dmParamItem);
			
			for(Room room : roomItem) {
				if(StringUtils.hasLength(room.getMemberRegId())) {
					c2dmHelper.sendMsg(room.getMemberRegId(), Constants.C2DM.C2DM_AUTH_TOKEN, msg, c2dmParamList);
				}
				if(StringUtils.hasLength(room.getGuest1RegId())) {
					c2dmHelper.sendMsg(room.getGuest1RegId(), Constants.C2DM.C2DM_AUTH_TOKEN, msg, c2dmParamList);
				}
				if(StringUtils.hasLength(room.getGuest2RegId())) {
					c2dmHelper.sendMsg(room.getGuest2RegId(), Constants.C2DM.C2DM_AUTH_TOKEN, msg, c2dmParamList);
				}
				if(StringUtils.hasLength(room.getGuest3RegId())) {
					c2dmHelper.sendMsg(room.getGuest3RegId(), Constants.C2DM.C2DM_AUTH_TOKEN, msg, c2dmParamList);
				}
				
			}
			
			return Constants.ReturnCode.SUCCESS;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.ReturnCode.OTHER_ERROR;
		}
	}
	*/
	
	public String sendC2dmMsgByChatRoomId(String roomId, String msg, String senderId, String action) {
		try {
			//Map<String, Object> paramMap = new HashMap<String,Object>();
			//paramMap.put("roomId", roomId);
			//paramMap.put("memberId", senderId);
			//List<RoomMember> roomMemberList = this.chatMainDao.getUserInfoListByRoomIdExceptMe(paramMap);
			List<RoomMember> roomMemberList = this.chatMainDao.getUserInfoListByRoomId(roomId);
			
			//senderId로 해당 member 기본정보 가져오기
			String senderNick="";
			String gender="";
			List<SimpleMember> memberList = this.chatMainDao.getSimpleMemberInfoByMemberId(senderId);
			if(memberList != null && !memberList.isEmpty()) {
				senderNick = URLEncoder.encode(memberList.get(0).getNick(), "UTF-8");
				gender = URLEncoder.encode(memberList.get(0).getGender(), "UTF-8");
			}
			
			//c2dmHelper로 보낼 파라미터 작성
			List<List<String>> c2dmParamList = new ArrayList<List<String>>();
			List<String> c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"action");
			c2dmParamItem.add(1,action);
			c2dmParamList.add(c2dmParamItem);
			
			c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"sender_id");
			c2dmParamItem.add(1,senderId);
			c2dmParamList.add(c2dmParamItem);
			
			c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"sender_nick");
			c2dmParamItem.add(1,senderNick);
			c2dmParamList.add(c2dmParamItem);
			
			c2dmParamItem = new ArrayList<String>();
			c2dmParamItem.add(0,"gender");
			c2dmParamItem.add(1,gender);
			c2dmParamList.add(c2dmParamItem);
			
			for(RoomMember roomMember : roomMemberList) {
				if(StringUtils.hasLength(roomMember.getMemberRegId())) {
					c2dmHelper.sendMsg(roomMember.getMemberRegId(), Constants.C2DM.C2DM_AUTH_TOKEN, msg, c2dmParamList);
				}
			}
			
			return Constants.ReturnCode.SUCCESS;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.ReturnCode.OTHER_ERROR;
		}
	}
	
	/**
	 * 실시간 채팅방 입장 프로세스
	 * @param roomId
	 * @param guestId
	 * @return
	 */
	public String doRoomInProcess(String roomId, String guestId) {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			List<Room> roomItemList = this.chatMainDao.getUserRegIdListByRoomId(roomId);
			
			if(roomItemList == null || roomItemList.isEmpty()) { //해당 채팅방 존재X
				return Constants.ReturnCode.LOGIC_ERROR;
			} else {
				boolean sameMemberExist = false;
				//room_member 테이블에 해당 roomId, memberId 와 동일한 row를 제외하고 모두 삭제
				List<RoomMember> roomMemberList = this.chatMainDao.getRoomMemberListByMemberId(guestId);
				for(RoomMember roomMember : roomMemberList) {
					int deletedCnt = 0;
					if(Integer.parseInt(roomId) == Integer.parseInt(roomMember.getRoomId())
							&& Integer.parseInt(guestId) == Integer.parseInt(roomMember.getMemberId())) {
						sameMemberExist = true;
					} else {
						deletedCnt = this.chatMainDao.deleteFromRoomMemberForRoomOut(roomMember);
					}
					
					if(deletedCnt > 0) {
						this.chatMainDao.updateRoomForRoomOut(roomMember.getRoomId()); //room.cur_user - 1 처리
					}
				}
				
				if(sameMemberExist) {
					this.transactionManager.commit(status);
					return Constants.ReturnCode.SUCCESS;
				} else {
					Room roomObj = roomItemList.get(0);
					if(roomObj.getMaxUser().equals(roomObj.getCurUser())) {
						//채팅방 인원이 만원인 경우
						this.transactionManager.rollback(status);
						return Constants.ReturnCode.LOGIC_ERROR;
					} else {
						this.chatMainDao.updateRoomForRoomIn(roomId);
						
						int insertedCnt = 0;
						RoomMember roomMemberForInsert = new RoomMember();
						roomMemberForInsert.setRoomId(roomId);
						roomMemberForInsert.setMemberId(guestId);
						roomMemberForInsert.setMasterYn("");
						insertedCnt = this.chatMainDao.insertIntoRoomMemberForRoomIn(roomMemberForInsert);
						
						if(insertedCnt > 0) {
							StringBuilder sbMsg = new StringBuilder();
							List<SimpleMember> memberList = this.chatMainDao.getSimpleMemberInfoByMemberId(guestId);
							if(memberList != null && !memberList.isEmpty()) {
								sbMsg.append(memberList.get(0).getNick());
							}
							sbMsg.append(accessor.getMessage("chat.roomIn.roomInNotice"));
							sendC2dmMsgByChatRoomId(roomId, sbMsg.toString(), guestId, Constants.ActionType.ROOM_IN_MSG);
						}
						this.transactionManager.commit(status);
						return Constants.ReturnCode.SUCCESS;
					}
				}
			}
			
			
		} catch (RuntimeException e) {
			log.debug("----- ChatMainServiceImpl.doRoomInProcess() RuntimeException -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			return Constants.ReturnCode.OTHER_ERROR;
		} catch (Exception e) {
			log.debug("----- ChatMainServiceImpl.doRoomInProcess() Exception -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			return Constants.ReturnCode.OTHER_ERROR;
		} finally {
			if(!status.isCompleted()) {
				this.transactionManager.commit(status);
			}
		}
	}
	
	/**
	 * 실시간 채팅방 퇴장 프로세스
	 * @param roomId
	 * @param guestId
	 * @return
	 */
	public String doRoomOutProcess(String roomId, String guestId) {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		accessor = new MessageSourceAccessor(this.messageSource);
		try {
			//room_member 테이블에서 해당 User 삭제
			RoomMember roomMemberForDelete = new RoomMember();
			roomMemberForDelete.setRoomId(roomId);
			roomMemberForDelete.setMemberId(guestId);
			roomMemberForDelete.setMasterYn("");
			int deletedCnt = this.chatMainDao.deleteFromRoomMemberForRoomOut(roomMemberForDelete);
			
			if(deletedCnt > 0) {
				//room 테이블 업데이트(cur_user -1)
				this.chatMainDao.updateRoomForRoomOut(roomId);
				
				StringBuilder sbMsg = new StringBuilder();
				List<SimpleMember> memberList = this.chatMainDao.getSimpleMemberInfoByMemberId(guestId);
				if(memberList != null && !memberList.isEmpty()) {
					sbMsg.append(memberList.get(0).getNick());
				}
				sbMsg.append(accessor.getMessage("chat.roomOut.roomOutNotice"));
				//퇴장알림 C2DM메세지 전송
				sendC2dmMsgByChatRoomId(roomId, sbMsg.toString(), guestId, Constants.ActionType.ROOM_OUT_MSG);
				
				//채팅방에 사람이 남아 있고, master_yn으로 order by 하여 첫 번째 사용자가 마스터가 아니면 'Y'로 업데이트
				List<RoomMember> roomMemberList = this.chatMainDao.getUserInfoListByRoomId(roomId);
				if(roomMemberList != null && !roomMemberList.isEmpty()) {
					RoomMember roomMember = roomMemberList.get(0);
					if(!Constants.YES.equalsIgnoreCase((roomMember.getMasterYn()))) {
						roomMember.setMasterYn("Y");
						this.chatMainDao.updateRoomMemberMasterYn(roomMember);
						//room.member_id를 새로운 방장으로 업데이트
						this.chatMainDao.updateRoomMemberId(roomMember);
					}
				}
			}
			
			this.transactionManager.commit(status);
			return Constants.ReturnCode.SUCCESS;
		} catch (RuntimeException e) {
			log.debug("----- ChatMainServiceImpl.doRoomOutProcess() RuntimeException -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			return Constants.ReturnCode.OTHER_ERROR;
		} catch (Exception e) {
			log.debug("----- ChatMainServiceImpl.doRoomOutProcess() Exception -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			return Constants.ReturnCode.OTHER_ERROR;
		}
	}
	
	/**
	 * 실시간 채팅방 개설 프로세스
	 * @param roomId
	 * @param guestId
	 * @return
	 */
	public Map<String,Object> doMakeRoomProcess(Room room) {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		accessor = new MessageSourceAccessor(this.messageSource);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try {
			//해당 memberId가 room_member 테이블에 존재시 삭제
			List<RoomMember> roomMemberList = this.chatMainDao.getRoomMemberListByMemberId(room.getMemberId());
			for(RoomMember roomMember : roomMemberList) {
				int deletedCnt = this.chatMainDao.deleteFromRoomMemberForRoomOut(roomMember);
				if(deletedCnt > 0) {
					this.chatMainDao.updateRoomForRoomOut(roomMember.getRoomId()); //room.cur_user - 1 처리
				}
				
				//roomMember에 더 이상 user가 없다면 room도 삭제
				List<RoomMember> roomMemberList2 = this.chatMainDao.getUserInfoListByRoomId(roomMember.getRoomId());
				if((roomMemberList2 == null) ||
						(roomMemberList2 != null && roomMemberList2.isEmpty())) {
					this.chatMainDao.deleteRoomByRoomId(roomMember.getRoomId());
				}
			}
			
			//Room Insert
			String roomId = this.chatMainDao.insertIntoRoom(room);
			//RoomMember Insert
			RoomMember roomMember = new RoomMember();
			roomMember.setRoomId(roomId);
			roomMember.setMasterYn("Y");
			roomMember.setMemberId(room.getMemberId());
			this.chatMainDao.insertIntoRoomMemberForRoomIn(roomMember);
			
			
			returnMap.put("resultCode", Constants.ReturnCode.SUCCESS);
			returnMap.put("roomId", roomId);
			returnMap.put("maxUser", room.getMaxUser());
			this.transactionManager.commit(status);
			return returnMap;
		} catch (RuntimeException e) {
			log.debug("----- ChatMainServiceImpl.doMakeRoomProcess() RuntimeException -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			returnMap.put("resultCode", Constants.ReturnCode.OTHER_ERROR);
			return returnMap;
		} catch (Exception e) {
			log.debug("----- ChatMainServiceImpl.doMakeRoomProcess() Exception -----");
			e.printStackTrace();
			this.transactionManager.rollback(status);
			returnMap.put("resultCode", Constants.ReturnCode.OTHER_ERROR);
			return returnMap;
		}
	}
	
	/* (non-Javadoc)
	 * @see kr.ko.nexmain.server.MissingU.chat.service.ChatMainService#getChatRoomList()
	 */
	public List<ChatRoom> getChatRoomList() {
		return this.chatMainDao.getChatRoomList();
	}

}