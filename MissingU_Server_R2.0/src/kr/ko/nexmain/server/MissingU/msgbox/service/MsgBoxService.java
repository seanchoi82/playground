package kr.ko.nexmain.server.MissingU.msgbox.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.DeleteMsgboxReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversSendVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxConversVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgListReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.OpenMsgReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.SendMsgReqVO;

public interface MsgBoxService {
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(시작)
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/** 쪽지 리스트 조회*/
	public Map<String, Object> getConversMsgBoxList(CommReqVO inputVO);
	
	/** 쪽지 대화 리스트 조회*/
	public Map<String, Object> getConversMsgConversationList(MsgBoxConversVO inputVO);
	
	/** 쪽지 전송*/
	public Map<String,Object> sendConversMsg(MsgBoxConversSendVO inputVO);
	
	/** 쪽지 삭제 */
	public Map<String,Object> deleteConversMsg(MsgBoxConversVO inputVO);
	
	
	///----------------------------------------------------------------------------------------------------------------------------------------///
	/// 새로 개발되는 쪽지함(끝)
	///----------------------------------------------------------------------------------------------------------------------------------------///

	/** 쪽지함 리스트 조회*/
	public Map<String,Object> getMsgBoxList(CommReqVO inputVO);
	/** 쪽지 전송*/
	public Map<String,Object> sendMsg(SendMsgReqVO inputVO);
	/** 쪽지 리스트 조회*/
	public Map<String,Object> getMsgList(MsgListReqVO inputVO);
	/** 쪽지함 삭제*/
	public Map<String,Object> deleteMsgbox(DeleteMsgboxReqVO inputVO);
	/** 쪽지함 삭제*/
	public Map<String,Object> deleteMsg(DeleteMsgReqVO inputVO);
	/** 내 친구 리스트 조회*/
	public Map<String,Object> getMyFriendsList(CommReqVO inputVO);
	/** 내 쪽지 리스트 조회*/
	public Map<String,Object> myMsgList(CommReqVO inputVO);
	/** 쪽지 개봉 */
	public Map<String,Object> openMsg(OpenMsgReqVO inputVO);

	
	

}