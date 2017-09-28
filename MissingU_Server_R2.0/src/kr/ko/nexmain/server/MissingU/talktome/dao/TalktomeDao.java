package kr.ko.nexmain.server.MissingU.talktome.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;

public interface TalktomeDao {
	/** 톡투미 리스트 조회 */
	List<Map<String,Object>> selectTalktomeList(CommReqVO inputVO);
	
	/** 톡투미 저장 */
	Integer insertTalktome(SaveTalkReqVO inputVO);
	
	/** 톡투미 수정 */
	Integer updateTalktome(SaveTalkReqVO inputVO);
	
	/** 톡투미 단건 조회 */
	Map<String,Object> selectTalktomeByTalkId(GetTalkReqVO inputVO);
	
	/** 톡투미 댓글 목록조회 */
	List<Map<String,Object>> selectTalktomeReplyByTalkId(GetTalkReqVO inputVO);
	
	/** 톡투미 조회수 업데이트 */
	Integer updateTalktomeReadCnt(GetTalkReqVO inputVO);
	
	/** 톡투미 댓글 저장 */
	Integer insertTalktomeReply(SaveTalkReplyReqVO inputVO);
	
	/** 톡투미 댓글수 업데이트 */
	Integer updateTalktomeReplyCnt(SaveTalkReplyReqVO inputVO);
	
	/** 톡투미 댓글수 업데이트 */
	Integer updateTalktomeReplyCntSync(SaveTalkReplyReqVO inputVO);
	
	/** 톡투미 작성자 회원ID 조회 */
	Integer selectTalkWriterId(Integer talkId);
	/** 톡투미 삭제 */
	Integer deleteTalk(GetTalkReqVO inputVO);
}
