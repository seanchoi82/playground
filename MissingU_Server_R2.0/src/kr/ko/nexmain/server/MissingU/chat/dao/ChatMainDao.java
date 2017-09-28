package kr.ko.nexmain.server.MissingU.chat.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;

public interface ChatMainDao {

	
	public List<SimpleMember> getSimpleMemberInfoByMemberId(String memberId);
	
	public List<Room> getUserRegIdListByRoomId(String roomId);
	
	public List<RoomMember> getUserInfoListByRoomId(String roomId);
	
	public List<RoomMember> getUserInfoListByRoomIdExceptMe(Map paramMap);
	
	public List<ChatRoom> getChatRoomList();
	
	public int updateRoomForRoomIn(String roomId);
	
	public int insertIntoRoomMemberForRoomIn(RoomMember roomMember);
	
	public String insertIntoRoom(Room room);
	
	public int updateRoomForRoomOut(String roomId);
	
	public int updateRoomMemberMasterYn(RoomMember roomMember);
	
	public int updateRoomMemberId(RoomMember roomMember);
	
	public int deleteRoomByRoomId(String roomId);
	
	public int deleteFromRoomMemberForRoomOut(RoomMember roomMember);
	
	public List<RoomMember> getRoomMemberListByMemberId(String memberId);
	
}