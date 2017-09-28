package kr.ko.nexmain.server.MissingU.membership.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.GcmUpdateReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.IssueTempPassReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.LoginReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.membership.model.MemberRegisterReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateFmJoinYnReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateMemberReqVO;

public interface MembershipDao {
	
	Integer getMemberCntByEmailId(CommReqVO reqParams);
	
	Integer getMemberCntByNickName(CommReqVO inputVO);
	
	Integer getMemberCntByLoginAccount(LoginReqVO loginReqVO);
	
	Map<String,Object> getMemberByLoginAccount(LoginReqVO loginReqVO);
	
	//MemberId로 회원정보 조회
	Map<String,Object> selectMemberByMemberId(CommReqVO inputVO);
	
	Integer insertIntoMember(MemberRegisterReqVO memberRegisterReqVO);
	
	Integer insertIntoMemberInventory(HashMap<String, Object> params);
	
	int updateMemberByMemberId(UpdateMemberReqVO inputVO);
	
	//MemberId로 회원속성정보 조회
	List<Map<String,Object>> selectMemberAttrByMemberId(CommReqVO inputVO);
	
	//MemberId & AttrName 으로 회원속성정보 조회
	Map<String,Object> selectMemberAttrByMemberIdAndName(MemberAttr inputVO);
	
	// 회원속성 업데이트
	int updateMemberAttr(MemberAttr input);
	// 회원속성 삭제
	int deleteMemberAttr(MemberAttr input);
	
	//회원 MainPhotoUrl 업데이트
	int updateMemberMainPhotoURL(Map<String,Object> inputMap);
	
	/** 간단 회원정보 조회 */
	public Map<String,Object> selectSimpleMemberInfoByMemberId(Integer memberId);
	
	/** 회원 포인트 정보 조회 */
	public Map<String,Object> selectMemberAndPointInfoByMemberId(Integer memberId);
	
	/** (임시패스워드) 회원조회  */
	Map<String,Object> selectSimpleMemberByIDAndNickName(IssueTempPassReqVO inputVO);
	
	/** 회원 GCM 정보 업데이트 */
	int updateGcmInfo(GcmUpdateReqVO inputVO);
	
	/** 회원 인벤토리 조회 */
	List<Map<String,Object>> selectInventoryByMemberId(CommReqVO inputVO);
	
	/** 회원 아이템정보 조회 */
	Map<String,Object> selectItemInfoByMemberIdAndItemGroup(CommReqVO inputVO);
	
	/** 회원 아이템 수신 내역 조회 */
	List<Map<String,Object>> selectItemReceiveHistByItemGroup(ItemSndRcvHistReqVO inputVO);
	
	/** 회원 아이템 송신 내역 조회 */
	List<Map<String,Object>> selectItemSendHistByItemGroup(ItemSndRcvHistReqVO inputVO);
	
	/** 회원 계정 삭제 */
	int updateForMemberCancelation(Integer memberId);
	
	/** 회원 실제 계정 삭제 */
	int deleteMember(Integer memberId);
	
	/** 회원 아이템정보 조회 */
	Map<String,Object> selectFriendCntByMemberId(CommReqVO inputVO);
	
	/** 포인트 사용 내역 Insert */
	int insertIntoPointUsageHist(Map<String, Object> inputMap);
	
	/** 포인트 사용 내역 조회 */
	List<Map<String,Object>> selectPointUsageHist(CommReqVO inputVO);
	
	/** 미확인 윙크,선물 수 조회 */
	Integer selectUnreadItemCntByMemberId(Integer memberId);
	
	/** 윙크,선물 확인시 읽음으로 업데이트 */
	int updateItemSndRcvHistAsRead(ItemSndRcvHistReqVO inputVO);
	
	/** 페이스매치 참여 여부 업데이트 */
	int updateFmJoinYn(UpdateFmJoinYnReqVO inputVO);
	
	/** 페이스매치 참여 여부 조회 */
	String selectFmJoinYnByMemberId(CommReqVO inputVO);
}