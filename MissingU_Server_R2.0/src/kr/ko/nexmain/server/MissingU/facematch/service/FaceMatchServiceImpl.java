package kr.ko.nexmain.server.MissingU.facematch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.facematch.dao.FaceMatchDao;
import kr.ko.nexmain.server.MissingU.facematch.model.FmResultVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetCandidatesReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.GetRankReqVO;
import kr.ko.nexmain.server.MissingU.facematch.model.UpdateResultReqVO;
import kr.ko.nexmain.server.MissingU.friends.dao.FriendsDao;
import kr.ko.nexmain.server.MissingU.friends.model.SearchFriendsReqVO;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.service.HarmonyService;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.IsNullTagHandler;

@Service
@Transactional(timeout=15)
public class FaceMatchServiceImpl implements FaceMatchService {

	@Autowired
	private FaceMatchDao faceMatchDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private HarmonyService harmonyService;
	@Autowired
	private MsgUtil msgUtil;
	
	private Locale gLocale;
	
	/** 
	 * 페이스매치 후보 조회
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getCandidates(GetCandidatesReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이스매치 이벤트 조회
		Map<String,Object> event = faceMatchDao.selectCurrentFmEvent();
		
		if(event != null && event.size() > 0) {
			//페이스매치 후보 조회
			FmResultVO vo = new FmResultVO();
			vo.setEventId((Long)event.get("eventId"));
			vo.setSex(inputVO.getSex());
			List<Map<String,Object>> candidateList = faceMatchDao.selectCandidatesFromFmResult(vo);
			
			if(candidateList != null && candidateList.size() >= 32) {
				//조회 결과가 있는 경우
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				returnMap.put("result", result);
				responseMap.put("event", event);
				responseMap.put("candidate", candidateList);
				returnMap.put("response", responseMap);
			} else {
				//후보가 준비되지 않음
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getCandidates.le.noResult", gLocale),
						msgUtil.getMsgText("facematch.getCandidates.le.noResult", gLocale));
				returnMap.put("result", result);
			}
		} else {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		return returnMap;
	}
	
	/** 
	 * 페이스매치 결과 업데이트
	 */
	public Map<String,Object> updateResult(UpdateResultReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		String candidates	= inputVO.getCandidates();
		String votedCounts	= inputVO.getVotedCounts();
		String[] candidateArray		= null;
		String[] votedCountArray	= null;
		
		if(!"".equals(candidates) && !"".equals(votedCounts)) {
			candidateArray	= candidates.split("/");
			votedCountArray	= votedCounts.split("/");
		}
		
		FmResultVO vo = null;
		Integer winnerMemberId = inputVO.getWinnerMemberId();
		
		if(candidateArray.length == votedCountArray.length) {
			for(int i=0; i < candidateArray.length ; i++) {
				vo = new FmResultVO();
				vo.setEventId(inputVO.getEventId());
				vo.setMemberId(Integer.parseInt(candidateArray[i]));
				vo.setVotedCnt(Integer.parseInt(votedCountArray[i]));
				if(winnerMemberId == Integer.parseInt(candidateArray[i])) {
					//승자인 경우 winCnt 증가
					vo.setWinCnt(1);
				}
				//페이스매치 결과 업데이트
				faceMatchDao.updateFmResult(vo);
			}
			
			//최종승자 업데이트
			if(!UTL.isNullOrBlank(winnerMemberId)) {
				vo = new FmResultVO();
				vo.setEventId(inputVO.getEventId());
				vo.setMemberId(inputVO.getgMemberId());
				vo.setPreferMemberId(winnerMemberId);
				faceMatchDao.updateFmWinner(vo);
			}
			
			//승자 상세정보 조회
			CommReqVO vo2 = new CommReqVO();
			vo2.setgMemberId(winnerMemberId);
			Map<String,Object> friendMap = membershipDao.selectMemberByMemberId(vo2);
			List<Map<String,Object>> friendAttrList = membershipDao.selectMemberAttrByMemberId(vo2);
			
			if(friendMap != null) {
				//회원 존재
				if(friendAttrList != null && friendAttrList.size() > 0) {
					Map<String,String> friendAttrMap = new HashMap<String,String>();
					for(Map<String, Object> attrMap : friendAttrList){
						friendAttrMap.put((String)attrMap.get("attrName"), (String)attrMap.get("attrValue"));
					}
					friendMap.put("attr", friendAttrMap);
				}
				responseMap.put("winner", friendMap);
				
				CommReqVO vo3 = new CommReqVO();
				vo3.setgMemberId(winnerMemberId);
				Map<String,Object> facematchMap = faceMatchDao.selectFmRecord(vo3);
				responseMap.put("facematch", facematchMap);
				
				// 궁합정보
				TotalInfoReqVO harmonyReqVO = new TotalInfoReqVO();
				harmonyReqVO.setgMemberId(inputVO.getgMemberId());
				harmonyReqVO.setFriendId(winnerMemberId);
				harmonyReqVO.setgLang(inputVO.getgLang());
				Map<String,Object> harmonyMap = harmonyService.getHarmonyResultMap(harmonyReqVO);
				responseMap.put("harmony", harmonyMap);
			}
			
		} else {
			//결과정보 불일치
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.updateResult.le.wrongResult", gLocale),
					msgUtil.getMsgText("facematch.updateResult.le.wrongResult", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}

		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("facematch.updateResult.ss.success", gLocale),
				msgUtil.getMsgText("facematch.updateResult.ss.success", gLocale));
		returnMap.put("result", result);
		returnMap.put("response", responseMap);
		
		return returnMap;
	}
	
	@Transactional(readOnly=true)
	public Map<String,Object> getRank(GetRankReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//페이스매치 이벤트 조회
		Map<String,Object> event = faceMatchDao.selectCurrentFmEvent();
		
		if(event != null && event.size() > 0) {
			//페이스매치 순위 조회
			FmResultVO vo = new FmResultVO();
			vo.setEventId((Long)event.get("eventId"));
			vo.setSex(inputVO.getSex());
			List<Map<String,Object>> rankList = faceMatchDao.selectFmRank(vo);
			
			if(rankList != null && rankList.size() > 0) {
				//조회 결과가 있는 경우
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				returnMap.put("result", result);
				responseMap.put("rankInfo", rankList);
				returnMap.put("response", responseMap);
			} else {
				//순위 정보 없음
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getRank.le.noResult", gLocale),
						msgUtil.getMsgText("facematch.getRank.le.noResult", gLocale));
				returnMap.put("result", result);
			}
		} else {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getRank.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getRank.le.noEvent", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}
		
		return returnMap;
	}

}
