package kr.ko.nexmain.server.MissingU.msgbox.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.LnkMsgboxMsgVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgListReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgVO;

public interface MsgBoxDao {
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(시작)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/** 쪽지 리스트 조회 */
	public List<Map<String, Object>> selectMessageBoxListByMemberId(Integer memberId);
	
	/** 쪽지 대화 리스트 조회 */
	public List<Map<String, Object>> selectMessageBoxConversationByMemberId(MsgBoxConversVO msgBoxConverVO);
	
	/** 쪽지 읽음 처리 */
	public int updateMessageBoxReadAll(MsgBoxConversVO msgBoxConverVO);
	
	/** 쪽지 발송 */
	public long insertMessageBoxMsg(MsgBoxConversSendVO msgBoxConverSendVO);
	
	/** 쪽지 발송 : 동일한 내용이지만 수신자 발신자를 복사해서 반대로 넣어둔다.(운영상의 이점이 있음) */
	public long insertMessageBoxMsgEcho(MsgBoxConversSendVO msgBoxConverSendVO);
	
	/** 쪽지 삭제 */
	public int deleteConversMsg(MsgBoxConversVO msgBoxConverVO);
	
	/** 쪽지 보기 */
	public Map<String, Object> selectMsgConvers(MsgBoxConversSendVO msgBoxConversSendVO);
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(끝)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	
	
	/** 쪽지함 리스트 조회 */
	public List<Map<String,Object>> selectMsgBoxListByMemberId(Integer memberId);
	
	/** 쪽지함 조회 */
	public Map<String,Object> selectMsgboxByMemberIdAndSenderId(MsgBoxVO inputVO);
	
	/** 쪽지 리스트 조회 */
	public List<Map<String,Object>> selectMsgListByMsgboxId(MsgListReqVO inputVO);
	
	/** 쪽지함 Insert */
	public Long insertIntoMsgbox(MsgBoxVO inputVO);
	/** 쪽지 Insert*/
	public Long insertIntoMsg(MsgVO inputVO);
	/** 링크_쪽지함_쪽지 Insert*/
	public int insertIntoLnkMsgboxMsg(LnkMsgboxMsgVO inputVO);
	/** 링크_쪽지함_쪽지 Delete*/
	public int deleteLnkMsgboxMsgByMsgId(LnkMsgboxMsgVO inputVO);
	/** 쪽지함 Status 업데이트*/
	public int updateMsgboxStatus(MsgBoxVO inputVO);
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsRead(Long msgboxId);
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsReadByMsgId(Long msgId);
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsReadYNToogleByMsgId(Long msgId);
	
	/** 미확인 쪽지 수 */
	Integer selectUnreadMsgCntByMemberId(Integer memberId);
	/** 내 쪽지 리스트 조회 */
	public List<Map<String,Object>> selectMyMsgList(CommReqVO inputVO);
	/** 쪽지 조회 */
	public Map<String,Object> selectMsgByMsgId(Long msgId);
	/** 쪽지확인 여부 조회 */
	Integer selectUnreadMsgCntByMsgId(Long msgId);
	
}