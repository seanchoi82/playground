package kr.ko.nexmain.server.MissingU.msgbox.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.LnkMsgboxMsgVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgListReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class MsgBoxDaoImpl extends SqlMapClientDaoSupport implements MsgBoxDao{

	public MsgBoxDaoImpl(){
		super();
	}
	
	@Autowired
	public MsgBoxDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}

	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(시작)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/** 쪽지 리스트 조회 */
	public List<Map<String,Object>> selectMessageBoxListByMemberId(Integer memberId) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MsgBox.selectMessageBoxListByMemberId", memberId);
	}
	
	/** 쪽지 대화 리스트 조회 */
	public List<Map<String,Object>> selectMessageBoxConversationByMemberId(MsgBoxConversVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MsgBox.selectMessageBoxConversationByMemberId", inputVO);
	}
	
	/** 쪽지 모두 읽음 처리 */
	public int updateMessageBoxReadAll(MsgBoxConversVO inputVO) {
		return getSqlMapClientTemplate().update("MsgBox.updateMessageBoxReadAll", inputVO);
	}
	
	/** 쪽지 발송 */
	public long insertMessageBoxMsg(MsgBoxConversSendVO inputVO) {
		return (Long)getSqlMapClientTemplate().insert("MsgBox.insertMessageBoxMsg", inputVO);
	}
	
	/** 쪽지 발송 */
	public long insertMessageBoxMsgEcho(MsgBoxConversSendVO inputVO) {
		return (Long)getSqlMapClientTemplate().insert("MsgBox.insertMessageBoxMsgEcho", inputVO);
	}
	
	/** 쪽지 삭제 */
	public int deleteConversMsg(MsgBoxConversVO inputVO) {
		return getSqlMapClientTemplate().update("MsgBox.updateAfterMessageBoxMsgDelete", inputVO);
	}
	
	public Map<String, Object> selectMsgConvers(MsgBoxConversSendVO msgBoxConversSendVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("MsgBox.selectMessageBoxConversByMsgId", msgBoxConversSendVO);
	}
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(끝)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	
	
	/** 쪽지함 리스트 조회 */
	public List<Map<String,Object>> selectMsgBoxListByMemberId(Integer memberId) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MsgBox.selectMsgBoxListByMemberId", memberId);
	}
	
	/** 쪽지함 조회 */
	public Map<String,Object> selectMsgboxByMemberIdAndSenderId(MsgBoxVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("MsgBox.selectMsgboxByMemberIdAndSenderId", inputVO);
	}
	
	/** 쪽지 리스트 조회 */
	public List<Map<String,Object>> selectMsgListByMsgboxId(MsgListReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MsgBox.selectMsgListByMsgboxId", inputVO);
	}
	
	/** 채팅방 생성 */
	public Long insertIntoMsgbox(MsgBoxVO inputVO) {
		return (Long)getSqlMapClientTemplate().insert("MsgBox.insertIntoMsgbox", inputVO);
	}
	/** 메세지 Insert */
	public Long insertIntoMsg(MsgVO inputVO) {
		return (Long)getSqlMapClientTemplate().insert("MsgBox.insertIntoMsg", inputVO);
	}
	
	/** 링크_쪽지함_쪽지 Insert*/
	public int insertIntoLnkMsgboxMsg(LnkMsgboxMsgVO inputVO) {
		return getSqlMapClientTemplate().update("MsgBox.insertIntoLnkMsgboxMsg", inputVO);
	}
	
	/** 링크_쪽지함_쪽지 Delete*/
	public int deleteLnkMsgboxMsgByMsgId(LnkMsgboxMsgVO inputVO) {
		return getSqlMapClientTemplate().update("MsgBox.deleteLnkMsgboxMsgByMsgId", inputVO);
	}
	
	/** 쪽지함 Status 업데이트*/
	public int updateMsgboxStatus(MsgBoxVO inputVO) {
		return getSqlMapClientTemplate().update("MsgBox.updateMsgboxStatus", inputVO);
	}
	
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsRead(Long msgboxId) {
		return getSqlMapClientTemplate().update("MsgBox.updateMsgAsRead", msgboxId);
	}
	
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsReadByMsgId(Long msgId) {
		return getSqlMapClientTemplate().update("MsgBox.updateMsgAsReadByMsgId", msgId);
	}
	
	/** 쪽지 읽기여부 업데이트*/
	public int updateMsgAsReadYNToogleByMsgId(Long msgId) {
		return getSqlMapClientTemplate().update("MsgBox.updateMsgAsReadYNToogleByMsgId", msgId);
	}
	
	/** 미확인 쪽지 수 조회*/
	public Integer selectUnreadMsgCntByMemberId(Integer memberId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("MsgBox.selectUnreadMsgCntByMemberId", memberId);
	}
	
	/** 내 쪽지 리스트 조회 */
	public List<Map<String,Object>> selectMyMsgList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MsgBox.selectMyMsgList", inputVO);
	}
	
	/** 쪽지 조회 */
	public Map<String,Object> selectMsgByMsgId(Long msgId) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("MsgBox.selectMsgByMsgId", msgId);
	}
	
	/** 쪽지확인 여부 조회 */
	public Integer selectUnreadMsgCntByMsgId(Long msgId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("MsgBox.selectUnreadMsgCntByMsgId", msgId);
	}
	
}