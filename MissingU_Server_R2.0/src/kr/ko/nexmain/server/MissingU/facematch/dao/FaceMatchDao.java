package kr.ko.nexmain.server.MissingU.facematch.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.FmResultVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetCandidatesReqVO;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.msgbox.model.MsgBoxVO;

public interface FaceMatchDao {
	
	List<Map<String,Object>> selectCandidatesFromFmResult(FmResultVO inputVO);
	
	Map<String,Object> selectCurrentFmEvent();
	
	/** 페이스매치 결과 업데이트 */
	int updateFmResult(FmResultVO inputVO);
	/** 페이스매치 승자 업데이트 */
	int updateFmWinner(FmResultVO inputVO);
	/** 페이스매치 순위 조회 */
	List<Map<String,Object>> selectFmRank(FmResultVO inputVO);
	
	/** 페이스매치 사진 업데이트 */
	int insertOrUpdateFmPhoto(Map<String,Object> inputMap);
	/** 페이스매치 전적 조회 */
	Map<String,Object> selectFmRecord(CommReqVO inputVO);
	
	
}