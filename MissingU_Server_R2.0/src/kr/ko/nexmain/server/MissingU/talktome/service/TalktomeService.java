package kr.ko.nexmain.server.MissingU.talktome.service;

import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;

public interface TalktomeService {
	/** 톡 리스트 조회 */
	Map<String,Object> getTalktomeList(CommReqVO inputVO);
	/** 톡 저장 */
	Map<String,Object> doSaveTalkInfo(SaveTalkReqVO inputVO);
	/** 톡 저장 */
	Map<String,Object> doSaveTalkInfoNoUsePoint(SaveTalkReqVO inputVO);
	/** 톡 저장 */
	Map<String,Object> doEditTalkInfo(SaveTalkReqVO inputVO);
	/** 톡 단건 조회 */
	Map<String,Object> getTalktome(GetTalkReqVO inputVO);
	/** 톡 댓글 저장 */
	Map<String,Object> doSaveTalkReply(SaveTalkReplyReqVO inputVO);
	
	/** 톡 삭제 */
	Map<String,Object> doDeleteTalk(GetTalkReqVO inputVO);
}
