package kr.ko.nexmain.server.MissingU.membership.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.GcmUpdateReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.IssueTempPassReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.ItemSndRcvHistReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.LoginReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;
import kr.ko.nexmain.server.MissingU.membership.model.MemberRegisterReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.SaveMemberPhotoReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateFmJoinYnReqVO;
import kr.ko.nexmain.server.MissingU.membership.model.UpdateMemberReqVO;

public interface MembershipService {
	
	boolean checkLoginEmailId(CommReqVO reqParams);
	
	boolean checkNickName(CommReqVO inputVO);
	
	Map<String,Object> doLogin(LoginReqVO inputVO);
	
	Map<String,Object> doMemberRegister(MemberRegisterReqVO inputVO);
	
	Map<String,Object> doCreateMemeber(MemberRegisterReqVO inputVO);
	
	Map<String,Object> doUpdateMemeber(UpdateMemberReqVO inputVO);
	
	int updateMemberAttr(MemberAttr attr);
	
	//마이페이지 조회
	Map<String,Object> doGetMyPageInfo(CommReqVO inputVO);
	
	/** 마이페이지 메인 */
	Map<String,Object> getMyPageMain(CommReqVO inputVO);
	
	//프로필 이미지 저장
	Map<String,Object> doSaveMemberPhoto(SaveMemberPhotoReqVO inputVO, HttpServletRequest request);
	/** GCM 알림수신 여부 업데이트*/
	Map<String,Object> gcmUpdate(GcmUpdateReqVO inputVO);
	/** 아이템 수신내역 조회 서비스 */
	Map<String,Object> itemReceiveHist(ItemSndRcvHistReqVO inputVO);
	/** 아이템 송신내역 조회 서비스 */
	Map<String,Object> itemSendHist(ItemSndRcvHistReqVO inputVO);
	/** 회원 계정 삭제 */
	Map<String,Object> deleteMember(CommReqVO inputVO);
	/** 실제 회원 계정 삭제 */
	Map<String,Object> deleteMemberReal(CommReqVO inputVO);
	/** 임시 패스워드 발급 */
	Map<String,Object> issueTempPass(IssueTempPassReqVO inputVO);
	/** 포인트 내역 조회 서비스 */
	Map<String,Object> pointUsageHist(CommReqVO inputVO);
	/** 페이스매치 참여여부 업데이트*/
	Map<String,Object> updateFmJoinYn(UpdateFmJoinYnReqVO inputVO);
	
	
}
