package kr.ko.nexmain.server.MissingU.chat.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.chat.model.ChatRoom;
import kr.ko.nexmain.server.MissingU.chat.model.ChatroomMember;
import kr.ko.nexmain.server.MissingU.chat.model.CreateRoomReqVO;
import kr.ko.nexmain.server.MissingU.chat.model.Room;
import kr.ko.nexmain.server.MissingU.chat.model.RoomMember;
import kr.ko.nexmain.server.MissingU.chat.model.SimpleMember;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;

public interface ChatDao {
	/** 채팅방 리스트 조회 */
	public List<Map<String,Object>> selectChatRoomList(CommReqVO inputVO);
	/** 채팅방 Insert */
	public Integer insertIntoRoom(CreateRoomReqVO inputVO);
	/** 채팅방 참여자 Insert*/
	public int insertIntoRoomMember(ChatroomMember inputVO);
	/** 채팅방 참여자 List 조회 */
	public List<ChatroomMember> selectRoomMemberListByRoomId(Integer roomId);
	/** 채팅방 참여자 List 조회 By MemberId*/
	public List<ChatroomMember> selectRoomMemberListByMemberId(Integer roomId);
	/** 채팅방 조회 */
	public ChatRoom selectRoomByRoomId(Integer roomId);
	/** 채팅방 인원 리스트 조회 */
	public List<Map<String,Object>> getRoomMemberListByMemberId(String memberId);
	/** 채팅방 업데이트 : cur_user_cnt 증가 */
	public int updateRoomToIncreaseCurUserCnt(Integer roomId);
	/** 채팅방 업데이트 : cur_user_cnt 감소 */
	public int updateRoomToDecreaseCurUserCnt(Integer roomId);
	/** 채팅방 메세지 발송으로 업데이트 처리 */
	public int updateUpdateDateForSendMsg(Integer roomId);
	
	/** 채팅방 인원 삭제 
	 * @deprecated */
	public int deleteFromRoomMemberByMemberId(ChatroomMember roomMember);
	/** 채팅방 회원 정보 수정*/
	public int updateFromRoomMemberByMemberId(ChatroomMember roomMember);
	/** 간단 회원정보 조회 */
	public Map<String,Object> selectSimpleMemberInfoByMemberId(Integer memberId);
	/** 방장여부 업데이트 */
	public int updateRoomMemberMasterYn(ChatroomMember roomMember);
	/** 방장 업데이트 */
	public int updateRoomMemberId(ChatroomMember roomMember);
	
	/** 방 입장 업데이트 */
	public int updateRoomMemberByRoomId(Map<String, Object> params);
	
	
	public List<Room> getUserRegIdListByRoomId(String roomId);
	
	public List<RoomMember> getUserInfoListByRoomIdExceptMe(Map paramMap);
	
	public List<ChatRoom> getChatRoomList();
	

	
	public int deleteRoomByRoomId(String roomId);
	
	
	public int cleanOldChatroom();
	
	
}