package kr.ko.nexmain.server.MissingU.talktome.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class TalktomeDaoImpl extends SqlMapClientDaoSupport implements TalktomeDao {
	public TalktomeDaoImpl(){
		super();
	}
	
	@Autowired
	public TalktomeDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	/** 톡투미 리스트 조회 */
	public List<Map<String,Object>> selectTalktomeList(CommReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Talktome.selectTalktomeForList", inputVO);
	}
	
	/** 톡투미 저장 */
	public Integer insertTalktome(SaveTalkReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("Talktome.insertIntoTalktome", inputVO);
	}
	
	/** 톡투미 저장 */
	public Integer updateTalktome(SaveTalkReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().update("Talktome.updateIntoTalktome", inputVO);
	}
	
	/** 톡투미 단건 조회 */
	public Map<String,Object> selectTalktomeByTalkId(GetTalkReqVO inputVO) {
		return (Map<String,Object>) getSqlMapClientTemplate().queryForObject("Talktome.selectTalktome", inputVO);
	}
	
	/** 톡투미 댓글 목록조회 */
	public List<Map<String,Object>> selectTalktomeReplyByTalkId(GetTalkReqVO inputVO) {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("Talktome.selectTalktomeReplyList", inputVO);
	}
	
	/** 톡투미 조회수 업데이트 */
	public Integer updateTalktomeReadCnt(GetTalkReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().update("Talktome.updateTalktomeReadCnt", inputVO);
	}
	
	/** 톡투미 댓글 저장 */
	public Integer insertTalktomeReply(SaveTalkReplyReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("Talktome.insertIntoTalktomeReply", inputVO);
	}
	
	/** 톡투미 댓글수 업데이트 */
	public Integer updateTalktomeReplyCnt(SaveTalkReplyReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().update("Talktome.updateTalktomeReplyCnt", inputVO);
	}
	
	/** 톡투미 댓글수 업데이트 */
	public Integer updateTalktomeReplyCntSync(SaveTalkReplyReqVO inputVO) {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer replyCnt = (Integer)getSqlMapClientTemplate().queryForObject("Talktome.selectTalktomeReplyCnt", inputVO);
		
		System.out.println("sssssssize : " + replyCnt);
		
		params.put("talkId", inputVO.getTalkId());
		params.put("replyCnt", replyCnt);
		return (Integer) getSqlMapClientTemplate().update("Talktome.updateTalktomeReplyCntSync", params);
	}
	
	/** 톡투미 작성자 회원ID 조회 */
	public Integer selectTalkWriterId(Integer talkId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Talktome.selectTalkWriterId", talkId);
	}

	public Integer deleteTalk(GetTalkReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().update("Talktome.deleteTalktome", inputVO);
	}
	
}