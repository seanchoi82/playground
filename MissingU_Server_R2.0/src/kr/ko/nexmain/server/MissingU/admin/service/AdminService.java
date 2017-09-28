package kr.ko.nexmain.server.MissingU.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.admin.model.ApkItem;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;

import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
	void addApkItem(ApkItem apkitem);
	
	void deleteApkItem(int apkId);
	
	List<ApkItem> getApkItemList();
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	/** 쪽지함 목록 */
	Map<String, Object> getMsgList(Map<String,Object> params);
	
	/** 쪽지 대화 */
	Map<String, Object> getMsgConversations(Map<String,Object> params);
	
	/** 읽음 여부 갱신 */
	int updateReadYn(long msgId);
	
	/** 메세지 삭제 */
	int deleteMsg(long msgId);
	
	/** 메세지 삭제 (대화 통째로 삭제) */
	Map<String,Object> deleteMsgGroup(Map<String,Object> params);
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	
	
	/** 미션매치 목록 조회*/
	Map<String,Object> getMissionMatchList(Map<String,Object> params);
	
	/** 미션매치 저장*/
	Map<String,Object> saveMissionMatch(Map<String,Object> params);
	
	/** 미션매치 삭제*/
	Map<String,Object> deleteMissionMatch(Map<String,Object> params);
	
	/** 미션매치 통계 */
	Map<String, Object> getMissionMatchStatus(Map<String, Object> params);
	
	/** 미션매치 참여 목록 조회*/
	Map<String,Object> joinMissionMatchList(Map<String,Object> params);
	
	/** 미션매치 참여 저장 및 수정*/
	Map<String,Object> joinEditMissionMatch(Map<String,Object> params);
	
	/** 미션매치 저장*/
	Map<String,Object> saveJoinMissionMatch(MissionMatchJoinReqVO inputVO);
	
	/** 미션매치 참여 삭제*/
	Map<String,Object> joinDeleteMissionMatch(Map<String,Object> params);
	
	/** 미션매치 참여 투표*/
	Map<String,Object> joinVoteMissionMatch(Map<String,Object> params);
	
	
	/** 페이스매치 조회*/
	Map<String,Object> getFmList(Map<String,Object> params);
	
	/** 페이스매치 조회 Count*/
	Map<String,Object> getFmListCnt(Map<String,Object> params);
	
	/** 회원목록 조회*/
	Map<String,Object> getMemberListNew(Map<String,Object> params);
	
	/** 회원목록 조회
	 * @deprecated recommand using getMemberListNew*/
	Map<String,Object> getMemberList(Map<String,Object> params);
	
	/** 회원목록 조회 Count 
	 * @deprecated recommand using getMemberListNew*/
	Map<String,Object> getMemberListCnt(Map<String,Object> params);
	
	/** 톡투미 목록 조회*/
	Map<String,Object> getTalkToMeList(Map<String,Object> params);
	
	/** 톡투미 삭제*/
	Map<String,Object> deleteTalkToMe(Map<String,Object> params);
	/** 톡 댓글 삭제  */
	Map<String,Object> doDelTalkReply(Map<String, Object> params);
	/** 톡 삭제  */
	Map<String,Object> doDelTalkArr(Map<String, Object> params);
	
	/** 결제내역 조회*/
	Map<String,Object> getPayHistList(Map<String,Object> params);
	
	/** 결제내역 조회 Count */
	Map<String,Object> getPayHistListCnt(Map<String,Object> params);
	
	/** 포인트사용 내역 조회*/
	Map<String,Object> getPointUseHistList(Map<String,Object> params);
	
	/** 포인트사용 내역 조회 Count*/
	Map<String,Object> getPointUseHistListCnt(Map<String,Object> params);
	
	/** 공지사항 목록 조회*/
	Map<String,Object> getNoticeList(Map<String,Object> params);
	
	/** 공지사항 목록 조회 Count */
	Map<String,Object> getNoticeListCnt(Map<String,Object> params);
	
	/** 공지사항 저장*/
	Map<String,Object> saveNotice(Map<String,Object> params);
	
	/** 공지사항 삭제*/
	Map<String,Object> deleteNotice(Map<String,Object> params);
	
	/** 사용자가이드 목록 조회*/
	Map<String,Object> getGuideList(Map<String,Object> params);
	
	/** 사용자가이드 목록 조회 Count */
	Map<String,Object> getGuideListCnt(Map<String,Object> params);
	
	/** 사용자가이드 저장*/
	Map<String,Object> saveGuide(Map<String,Object> params);
	
	/** 사용자가이드 삭제*/
	Map<String,Object> deleteGuide(Map<String,Object> params);
	
	/** 회원 저장 */
	Map<String,Object> saveMember(Map<String,Object> params, MultipartFile uploadFile, HttpServletRequest request);
	
	/** 포인트 부여 */
	Map<String,Object> givePoint(Map<String,Object> params);
	
	/** 메세지박스 목록  */
	Map<String,Object> getMsgBoxList(Map<String,Object> params);
	/** 메세지박스 목록  */
	Map<String,Object> getMsgBoxConversationFriends(Map<String,Object> params);
	/** 톡 삭제  */
	Map<String,Object> doDelMsgArr(Map<String, Object> params);
	
	/** 메세지박스 목록  */
	Map<String,Object> getMsgBoxConversationByFriendsId(Map<String,Object> params);
	
	/** 1:1문의 목록  */
	Map<String,Object> getManToManQuestionsList(Map<String,Object> params);
	/** 1:1문의 삭제  */
	Map<String,Object> doDelManToManArr(Map<String, Object> params);
	/** 1:1문의 관리자 저장  */
	Map<String,Object> doUpdateManToMan(Map<String, Object> params);

	
/*
	List<ApkItem> getApkItemList();
	
	ApkItem getApkItemByApkItemId(Integer itemId);
	
	List<Item> getItemByItemName(String itemName);
	
	void entryItem(Item item);
	
	void updateItem(Item item);
	
	void deleteItem(Item item);
	
	InputStream getPicture(Integer itemId);
*/
}
