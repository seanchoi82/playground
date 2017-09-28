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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class MembershipDaoImpl extends SqlMapClientDaoSupport implements MembershipDao {
	public MembershipDaoImpl(){
		super();
	}
	
	@Autowired
	public MembershipDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	public Integer getMemberCntByEmailId(CommReqVO reqParams) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Membership.selectMemberCntByEmailId", reqParams);
	}
	
	public Integer getMemberCntByNickName(CommReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Membership.selectMemberCntByNickName", inputVO);
	}
	
	public Integer getMemberCntByLoginAccount(LoginReqVO loginReqVO) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Membership.selectMemberCntByLoginAccount", loginReqVO);
	}
	
	public Map<String,Object> getMemberByLoginAccount(LoginReqVO loginReqVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectMemberByLoginAccount", loginReqVO);
	}
	
	//MemberId로 회원정보 조회
	public Map<String,Object> selectMemberByMemberId(CommReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectMemberByMemberId", inputVO);
	}
	
	public Integer insertIntoMemberInventory(HashMap<String ,Object> params) {
		return (Integer)getSqlMapClientTemplate().insert("Membership.defaultInventoryInsert", params);
	}
	
	public Integer insertIntoMember(MemberRegisterReqVO memberRegisterReqVO) {
		
		
//		getSqlMapClientTemplate().insert("Membership.defaultInventoryInsert", memberRegisterReqVO);
		
		return (Integer) getSqlMapClientTemplate().insert("Membership.insertIntoMember", memberRegisterReqVO);
	}
	
	public int updateMemberByMemberId(UpdateMemberReqVO inputVO) {
		return getSqlMapClientTemplate().update("Membership.updateMemberByMemberId", inputVO);
	}
	
	//MemberId로 회원속성정보 조회
	public List<Map<String,Object>> selectMemberAttrByMemberId(CommReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Membership.selectMemberAttrByMemberId", inputVO);
	}
	
	//MemberId로 회원속성정보 조회
	public Map<String,Object> selectMemberAttrByMemberIdAndName(MemberAttr inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectMemberAttrByMemberIdAndName", inputVO);
	}
	
	//회원속성 업데이트
	public int updateMemberAttr(MemberAttr input) {
		return getSqlMapClientTemplate().update("Membership.updateMemberAttr", input);
	}
	
	//회원속성 삭제
	public int deleteMemberAttr(MemberAttr input) {
		return getSqlMapClientTemplate().delete("Membership.deleteMemberAttr", input);
	}
	
	//회원 MainPhotoUrl 업데이트
	public int updateMemberMainPhotoURL(Map<String,Object> inputMap) {
		return getSqlMapClientTemplate().update("Membership.updateMemberMainPhotoUrl", inputMap);
	}
	
	/** 간단 회원정보 조회 */
	public Map<String,Object> selectSimpleMemberInfoByMemberId(Integer memberId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectSimpleMemberInfoByMemberId", memberId);
	}
	
	/** 회원 포인트 정보 조회 */
	public Map<String,Object> selectMemberAndPointInfoByMemberId(Integer memberId) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectMemberAndPointInfoByMemberId", memberId);
	}
	
	/** (임시패스워드) 회원조회  */
	public Map<String,Object> selectSimpleMemberByIDAndNickName(IssueTempPassReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectSimpleMemberByIDAndNickName", inputVO);
	}
	
	/** 회원 GCM 정보 업데이트 */
	public int updateGcmInfo(GcmUpdateReqVO inputVO) {
		return getSqlMapClientTemplate().update("Membership.updateGcmInfo", inputVO);
	}
	
	/** 회원 인벤토리 조회 */
	public List<Map<String,Object>> selectInventoryByMemberId(CommReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Membership.selectInventoryByMemberId", inputVO);
	}
	
	/** 회원 아이템정보 조회 */
	public Map<String,Object> selectItemInfoByMemberIdAndItemGroup(CommReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectItemInfoByMemberIdAndItemGroup", inputVO);
	}
	
	/** 회원 아이템 수신 내역 조회 */
	public List<Map<String,Object>> selectItemReceiveHistByItemGroup(ItemSndRcvHistReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Membership.selectItemReceiveHistByItemGroup", inputVO);
	}
	
	/** 회원 아이템 송신 내역 조회 */
	public List<Map<String,Object>> selectItemSendHistByItemGroup(ItemSndRcvHistReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Membership.selectItemSendHistByItemGroup", inputVO);
	}
	
	/** 회원 계정 삭제 */
	public int updateForMemberCancelation(Integer memberId) {
		return (Integer) getSqlMapClientTemplate().update("Membership.updateForMemberCancelation", memberId);
	}
	
	/** 회원 실제 계정 삭제 */
	public int deleteMember(Integer memberId) {
		return (Integer) getSqlMapClientTemplate().delete("Membership.deleteMember", memberId);
	}
	
	/** 회원 아이템정보 조회 */
	public Map<String,Object> selectFriendCntByMemberId(CommReqVO inputVO) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Membership.selectFriendCntByMemberId", inputVO);
	}
	
	/** 포인트 사용내역 Insert */
	public int insertIntoPointUsageHist(Map<String, Object> inputMap) {
		return getSqlMapClientTemplate().update("Membership.insertIntoPointUsageHist", inputMap);
	}
	
	/** 포인트 사용내역 조회 */
	public List<Map<String,Object>> selectPointUsageHist(CommReqVO inputVO) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Membership.selectPointUsageHist", inputVO);
	}
	
	/** 미확인 윙크,선물 수 조회 */
	public Integer selectUnreadItemCntByMemberId(Integer memberId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Membership.selectUnreadItemCntByMemberId", memberId);
	}
	
	/** 윙크,선물 확인시 읽음으로 업데이트 */
	public int updateItemSndRcvHistAsRead(ItemSndRcvHistReqVO inputVO) {
		return getSqlMapClientTemplate().update("Membership.updateItemSndRcvHistAsRead", inputVO);
	}
	
	/** 페이스매치 참여 여부 업데이트 */
	public int updateFmJoinYn(UpdateFmJoinYnReqVO inputVO) {
		return getSqlMapClientTemplate().update("Membership.updateFmJoinYn", inputVO);
	}
	
	public String selectFmJoinYnByMemberId(CommReqVO inputVO) {
		return (String) getSqlMapClientTemplate().queryForObject("Membership.selectFmJoinYnByMemberId", inputVO);
	}
}