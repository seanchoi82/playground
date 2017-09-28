package kr.ko.nexmain.server.MissingU.chat.service;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.CreateRoomReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.KickOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomInOutReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.SendChatMsgReqVO;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;


public interface ChatService {
	/** 채팅방 리스트 조회*/
	public Map<String,Object> getRoomList(CommReqVO inputVO);
	
	/** 채팅방 생성 */
	public Map<String,Object> createRoom(CreateRoomReqVO inputVO);
	
	/** 채팅방 입장처리 */
	public Map<String,Object> doRoomInProcess(RoomInOutReqVO inputVO);
	
	/** 채팅방 퇴장처리 */
	public Map<String,Object> doRoomOutProcess(RoomInOutReqVO inputVO);
	
	/** 채팅 메세지 전송 */
	public Map<String,Object> sendChatMsg(SendChatMsgReqVO inputVO);
	
	/** 채팅방 퇴장처리 */
	public Map<String,Object> doKickOutProcess(KickOutReqVO inputVO);
	/** 채팅방 지우기 */
	public int cleanOldChatroom();
}