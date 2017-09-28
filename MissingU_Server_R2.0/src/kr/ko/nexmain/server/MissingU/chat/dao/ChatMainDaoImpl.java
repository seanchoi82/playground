package kr.ko.nexmain.server.MissingU.chat.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxItem;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ChatMainDaoImpl extends SqlMapClientDaoSupport implements ChatMainDao {

	
	public List<SimpleMember> getSimpleMemberInfoByMemberId(String memberId) {
		return getSqlMapClientTemplate().queryForList("Chat.getSimpleMemberInfoByMemberId", memberId);
	}
	
	public List<Room> getUserRegIdListByRoomId(String roomId) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomItemListByRoomId", roomId);
	}
	
	public List<RoomMember> getUserInfoListByRoomId(String roomId) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomMemberListByRoomId", roomId);
	}
	
	public List<RoomMember> getUserInfoListByRoomIdExceptMe(Map paramMap) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomMemberListByRoomIdExceptMe", paramMap);
	}
	
	public List<ChatRoom> getChatRoomList() {
		return getSqlMapClientTemplate().queryForList("Chat.getChatRoomList");
	}
	
	public int updateRoomForRoomIn(String roomId) {
		return getSqlMapClientTemplate().update("Chat.updateRoomForRoomIn", roomId);
	}
	
	public int insertIntoRoomMemberForRoomIn(RoomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.insertIntoRoomMemberForRoomIn", roomMember);
	}
	
	public String insertIntoRoom(Room room) {
		return (String)getSqlMapClientTemplate().insert("Chat.insertIntoRoom", room);
	}
	
	public int updateRoomForRoomOut(String roomId) {
		return getSqlMapClientTemplate().update("Chat.updateRoomForRoomOut", roomId);
	}
	
	public int updateRoomMemberMasterYn(RoomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.updateRoomMemberMasterYn", roomMember);
	}
	
	public int updateRoomMemberId(RoomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.updateRoomMemberId", roomMember);
	}
	
	public int deleteRoomByRoomId(String roomId) {
		return getSqlMapClientTemplate().delete("Chat.deleteRoomByRoomId", roomId);
	}
	
	public int deleteFromRoomMemberForRoomOut(RoomMember roomMember) {
		return getSqlMapClientTemplate().delete("Chat.deleteFromRoomMemberForRoomOut", roomMember);
	}
	
	public List<RoomMember> getRoomMemberListByMemberId(String memberId) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomMemberListByMemberId", memberId);
	}
	
}