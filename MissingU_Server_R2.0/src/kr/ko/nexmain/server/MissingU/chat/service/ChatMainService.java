package kr.ko.nexmain.server.MissingU.chat.service;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.Room;

import org.springframework.context.support.MessageSourceAccessor;


public interface ChatMainService {

	public MessageSourceAccessor getMessageSourceAccessor();
	
	//public String sendC2dmMsgByChatRoomId(String roomId, String msg, String memberId);
	
	public String sendC2dmMsgByChatRoomId(String roomId, String msg, String senderId, String action);
	
	public String doRoomInProcess(String roomId, String guestId);

	public String doRoomOutProcess(String roomId, String guestId);
	
	public Map<String,Object> doMakeRoomProcess(Room room);
	
	public List<ChatRoom> getChatRoomList();
	

}