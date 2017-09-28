package kr.ko.nexmain.server.MissingU.admin.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.admin.model.ApkItem;
import kr.ko.nexmain.server.MissingU.admin.model.MsgDeleteMemberPack;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;

public interface AdminDao {
	void addApkItem(ApkItem apkItem);
	
	void deleteApkItem(int apkId);
	
	List<ApkItem> getApkItem();
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	/**  쪽지 대화 목록 */
	List<Map<String,Object>> selectMsgList(Map<String,Object> params);
	/**  쪽지 대화 목록 전체 객수*/
	Integer selectMsgListCnt(Map<String,Object> params);
	/**  쪽지 대화*/
	List<Map<String,Object>> selectMsgConversation(Map<String,Object> params);
	/**  읽음여부 갱신 */
	Integer updateReadYn(long msgId);
	/**  메세지 삭제 */
	Integer deleteMsg(long msgId);
	/**  메세지 삭제(그룹) */
	Integer deleteMsgGroup(MsgDeleteMemberPack pack);
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	
	/** 페이스매치 조회 */
	List<Map<String,Object>> selectFmList(Map<String,Object> params);
	
	/** 페이스매치 조회 Count */
	Integer selectFmListCnt(Map<String,Object> params);
	
	/** 회원목록 조회 */
	List<Map<String,Object>> selectMemberListNew(Map<String,Object> params);
	
	/** 회원목록 조회 Count */
	Integer selectMemberListCntNew(Map<String,Object> params);
	
	/** 회원목록 조회 
	 * @deprecated*/
	List<Map<String,Object>> selectMemberList(Map<String,Object> params);
	
	/** 회원목록 조회 Count 
	 * * @deprecated*/
	Integer selectMemberListCnt(Map<String,Object> params);
	
	/** 톡투미 목록 조회 */
	List<Map<String,Object>> selectTalkToMeList(Map<String,Object> params);
	
	/** 톡투미 목록 조회 Count */
	Integer selectTalkToMeListCnt(Map<String,Object> params);
	
	/** 톡투미 삭제 */
	Integer deleteTalkToMe(Map<String,Object> params);
	
	/** 톡투미 삭제 */
	Integer deleteTalkToMeReply(Integer[] replyIds);
	
	/** 톡투미 삭제 */
	Integer deleteTalkToMeArr(String[] talkIds);
	 
	/** 결제내역 조회 */
	List<Map<String,Object>> selectPayHistList(Map<String,Object> params);
	
	/** 결제내역 조회 Count*/
	Integer selectPayHistListCnt(Map<String,Object> params);
	
	/** 포인트 사용 내역 조회 */
	List<Map<String,Object>> selectPointUseHistList(Map<String,Object> params);
	
	/** 포인트 사용 내역 조회 Count */
	Integer selectPointUseHistListCnt(Map<String,Object> params);
	
	/** 공지사항목록 조회 */
	List<Map<String,Object>> selectNoticeList(Map<String,Object> params);
	
	/** 공지사항목록 조회 Count*/
	Integer selectNoticeListCnt(Map<String,Object> params);
	
	/** 공지사항 조회 */
	Map<String,Object> selectNotice(Map<String,Object> params);
	
	/** 공지사항 등록 */
	Integer insertNotice(Map<String,Object> params);
	
	/** 공지사항 저장 */
	int updateNotice(Map<String,Object> params);
	
	/** 공지사항 삭제 */
	int deleteNotice(Map<String,Object> params);
	
	/** 사용자가이드목록 조회 */
	List<Map<String,Object>> selectGuideList(Map<String,Object> params);
	
	/** 사용자가이드목록 조회 Count */
	Integer selectGuideListCnt(Map<String,Object> params);
	
	/** 사용자가이드 조회 */
	Map<String,Object> selectGuide(Map<String,Object> params);
	
	/** 사용자가이드 등록 */
	void insertGuide(Map<String,Object> params);
	
	/** 사용자가이드 저장 */
	int updateGuide(Map<String,Object> params);
	
	/** 사용자가이드 삭제 */
	int deleteGuide(Map<String,Object> params);
	
	
	/** 메세지박스 목록 */
	List<Map<String, Object>> selectMessageBoxList(Map<String, Object> params);
	/** 메세지박스 전체 레코드 (페이징) */
	Integer selectMessageBoxListCnt(Map<String, Object> params);
	/** 메세지박스 삭제 */
	Integer deleteMsgArr(String[] talkIds);
	
	/** 메세지박스 목록 */
	List<Map<String, Object>> selectMessageBoxConversationFriends(Map<String, Object> params);
	/** 메세지박스 전체 레코드 (페이징) */
	Integer selectMessageBoxConversationFriendsCnt(Map<String, Object> params);
	
	/** 메세지박스 대화 목록 */
	List<Map<String, Object>> getMsgBoxConversationByFriendsId(Map<String, Object> params);
	
	
	/** 1:1 문의 목록 */
	List<Map<String, Object>> selectManToManQuestionsList(Map<String, Object> params);
	/** 1:1 문의 전체 레코드 (페이징) */
	Integer selectManToManQuestionsListCnt(Map<String, Object> params);
	/** 1:1 문의 삭제 */
	Integer deleteManToManArr(String[] mIds);
	/** 1:1 문의 관리자 저장 */
	Integer updateManToMan(Map<String, Object> params);

	
	/** 미션매치 목록 조회 */
	List<Map<String,Object>> selectMissionMatchList(Map<String,Object> params);
	
	/** 미션매치 목록 조회 Count*/
	Integer selectMissionMatchListCnt(Map<String,Object> params);
	
	/** 미션매치 조회 */
	Map<String,Object> selectMissionMatch(Map<String,Object> params);
	
	/** 미션매치 등록 */
	Integer insertMissionMatch(Map<String,Object> params);
	
	/** 미션매치 저장 */
	int updateMissionMatch(Map<String,Object> params);
	
	/** 미션매치 삭제 */
	int deleteMissionMatch(Map<String,Object> params);
	
	/** 미션매치 목록 통계 */
	List<Map<String,Object>> selectMissionMatchStatus(Map<String,Object> params);
	
	/** 미션매치 목록 통계 Count*/
	Integer selectMissionMatchStatusCnt(Map<String,Object> params);
	
	
	/** 미션매치 참여 목록 조회 */
	List<Map<String,Object>> selectJoinMissionMatchList(Map<String,Object> params);
	
	/** 미션매치 참여 목록 조회 Count*/
	Integer selectJoinMissionMatchListCnt(Map<String,Object> params);
	
	/** 미션매치 참여 조회 */
	Map<String,Object> selectJoinMissionMatch(Map<String,Object> params);
	
	/** 미션매치 참여 등록 */
	Integer insertJoinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	/** 미션매치 참여 저장 */
	int updateJoinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	/** 미션매치 참여 삭제 */
	int deleteJoinMissionMatch(String[] mJIds);
	
	/** 미션매치 참여 투표 */
	int voteJoinMissionMatch(Map<String,Object> params);
/*
	List<ApkItem> findAll();

	ApkItem findByPrimaryKey(Integer ApkItemId);

	List<ApkItem> findByApkItemName(String ApkItemName);

	void create(ApkItem ApkItem);

	void udpate(ApkItem ApkItem);

	void delete(ApkItem ApkItem);
	
	InputStream getPicture(Integer ApkItemId);
*/
}