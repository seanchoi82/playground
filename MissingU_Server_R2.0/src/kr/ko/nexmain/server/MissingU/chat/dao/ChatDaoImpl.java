package kr.ko.nexmain.server.MissingU.chat.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.ChatroomMember;
import kr.ko.nexmain.server.MissingU.chat.model.CreateRoomReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class ChatDaoImpl extends SqlMapClientDaoSupport implements ChatDao {
	public ChatDaoImpl(){
		super();
	}
	
	@Autowired
	public ChatDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 채팅방 리스트 조회 */
	public List<Map<String,Object>> selectChatRoomList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Chat.selectChatRoomList", inputVO);
	}
	
	/** 채팅방 생성 */
	public Integer insertIntoRoom(CreateRoomReqVO inputVO) {
		return (Integer)getSqlMapClientTemplate().insert("Chat.insertIntoRoom", inputVO);
	}
	/** 채팅방 참여자 Insert */
	public int insertIntoRoomMember(ChatroomMember inputVO) {
		return getSqlMapClientTemplate().update("Chat.insertIntoRoomMember", inputVO);
	}
	/** 채팅방 참여자 List 조회 */
	public List<ChatroomMember> selectRoomMemberListByRoomId(Integer roomId) {
		return getSqlMapClientTemplate().queryForList("Chat.selectRoomMemberListByRoomId", roomId);
	}
	/** 채팅방 참여자 List 조회 By MemberId*/
	public List<ChatroomMember> selectRoomMemberListByMemberId(Integer memberId) {
		return getSqlMapClientTemplate().queryForList("Chat.selectRoomMemberListByMemberId", memberId);
	}
	/** 채팅방 조회 */
	public ChatRoom selectRoomByRoomId(Integer roomId) {
		return (ChatRoom) getSqlMapClientTemplate().queryForObject("Chat.selectRoomByRoomId", roomId);
	}
	/** 채팅방 인원 리스트 조회 */
	public List<Map<String,Object>> getRoomMemberListByMemberId(String memberId) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomMemberListByMemberId", memberId);
	}
	/** 채팅방 업데이트 : cur_user_cnt 증가 */
	public int updateRoomToIncreaseCurUserCnt(Integer roomId) {
		return getSqlMapClientTemplate().update("Chat.updateRoomToIncreaseCurUserCnt", roomId);
	}
	/** 채팅방 업데이트 : cur_user_cnt 감소 */
	public int updateRoomToDecreaseCurUserCnt(Integer roomId) {
		return getSqlMapClientTemplate().update("Chat.updateRoomToDecreaseCurUserCnt", roomId);
	}
	/** 채팅방 메세지 발송으로 업데이트 처리 */
	public int updateUpdateDateForSendMsg(Integer roomId) {
		return getSqlMapClientTemplate().update("Chat.updateUpDTForSendMsg", roomId);
	}
	/** 채팅방 인원 삭제 */
	public int deleteFromRoomMemberByMemberId(ChatroomMember roomMember) {
		return getSqlMapClientTemplate().delete("Chat.deleteFromRoomMemberByMemberId", roomMember);
	}
	/** 채팅방 회원 정보 수정 */
	public int updateFromRoomMemberByMemberId(ChatroomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.updateFromRoomMemberByMemberId", roomMember);
	}
	
	/** 간단 회원정보 조회 */
	public Map<String,Object> selectSimpleMemberInfoByMemberId(Integer memberId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Chat.selectSimpleMemberInfoByMemberId", memberId);
	}
	/** 방장여부 업데이트 */
	public int updateRoomMemberMasterYn(ChatroomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.updateRoomMemberMasterYn", roomMember);
	}
	/** 방장 업데이트 */
	public int updateRoomMemberId(ChatroomMember roomMember) {
		return getSqlMapClientTemplate().update("Chat.updateRoomMemberId", roomMember);
	}
	
	/** 방 입장 업데이트 */
	public int updateRoomMemberByRoomId(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Chat.updateRoomMemberByRoomId", params);
	}
	
	
	public List<Room> getUserRegIdListByRoomId(String roomId) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomItemListByRoomId", roomId);
	}
	
	public List<RoomMember> getUserInfoListByRoomIdExceptMe(Map paramMap) {
		return getSqlMapClientTemplate().queryForList("Chat.getRoomMemberListByRoomIdExceptMe", paramMap);
	}
	
	public List<ChatRoom> getChatRoomList() {
		return getSqlMapClientTemplate().queryForList("Chat.getChatRoomList");
	}
	

	
	public int deleteRoomByRoomId(String roomId) {
		return getSqlMapClientTemplate().delete("Chat.deleteRoomByRoomId", roomId);
	}
	
	public int cleanOldChatroom() {
		return getSqlMapClientTemplate().delete("Chat.cleanOldChatroom");
	}
		
	
	
}