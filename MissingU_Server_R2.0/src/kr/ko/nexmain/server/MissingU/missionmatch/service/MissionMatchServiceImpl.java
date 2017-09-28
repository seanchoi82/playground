package kr.ko.nexmain.server.MissingU.missionmatch.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.harmony.model.TotalInfoReqVO;
import kr.ko.nexmain.server.MissingU.harmony.service.HarmonyService;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.missionmatch.dao.MissionMatchDao;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchRankReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReportReqVO;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class MissionMatchServiceImpl implements MissionMatchService {

	@Autowired
	MembershipDao membershipDao;
	@Autowired
	MissionMatchDao missionMatchDao;
	@Autowired
	private HarmonyService harmonyService;
	@Autowired
	private MsgUtil msgUtil;

	/** 최신 미션 정보 */
	@Override
	public Map<String, Object> getLastedMissionMatch(MissionMatchReqVO inputVO) {
		
		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 미션 정보 추출
		Map<String, Object> event = missionMatchDao.getLastedMissionMatch(inputVO);
		if(event != null && event.size() > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			responseMap.put("event", event);
			returnMap.put("response", responseMap);
			returnMap.put("result", result);
		}else{
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
		}
		return returnMap;
	}
	
	/** 최신 미션 정보 */
	@Override
	public Map<String, Object> getMissionMatchInfo(MissionMatchReqVO inputVO) {
		
		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 미션 정보 추출
		Map<String, Object> event = missionMatchDao.getMissionMatch(inputVO);
		if(event != null && event.size() > 0) {
			
			// 참가자 목록 추가(랭킹)
			MissionMatchRankReqVO vo = new MissionMatchRankReqVO();
			vo.setType(inputVO.getType());
			vo.setSearchType("A");
			vo.setmId(inputVO.getmId());
			
			List<Map<String, Object>> rank = this.missionMatchDao.selectJoinRankMissionMatch(vo);
			
			MissionMatchJoinReqVO vo2 = new MissionMatchJoinReqVO();
			vo2.setmId(inputVO.getmId());
			
			Map<String, Object> totalCnt = this.missionMatchDao.countJoinMissionMatch(vo2);
			responseMap.put("totalJoinCnt", totalCnt.get("cnt"));
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			responseMap.put("event", event);
			responseMap.put("rank", rank);
			
			returnMap.put("response", responseMap);
			returnMap.put("result", result);
		}else{
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
		}
		return returnMap;
	}

	/** 미션 후보 목록 반환 */
	@Override
	public Map<String, Object> getMissionMatchDatas(MissionMatchReqVO inputVO) {
		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 미션 정보 추출
		Map<String, Object> event = missionMatchDao.getMissionMatch(inputVO);
		if(event != null && event.size() > 0) {
			
			// 미션정보를 가져온 경우
			inputVO.setmId(Integer.parseInt(event.get("mId") + ""));
			
			// 미션매치 승자 목록을 가져와서 데이터를 바인딩 해둔다.
			List<String> winners = missionMatchDao.getMissionMatchWinners(inputVO);
			inputVO.setWinners(winners);
			
			List<Map<String, Object>> candidateList = missionMatchDao.getMissionMatchDatas(inputVO);
			
			if(candidateList != null && candidateList.size() > 0) {
				
				if(candidateList.size() < inputVO.getNeedRound()) {
					//후보가 준비되지 않음
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("facematch.getCandidates.le.lessDataResult", gLocale),
							msgUtil.getMsgText("facematch.getCandidates.le.lessDataResult", new  Object[] { candidateList.size(), inputVO.getNeedRound() }, gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
				
//				// 지정한 count보다 낮은 경우 2의 배수로 맞춘다.  
//				if(candidateList.size()<inputVO.getNeedRound()) {
//					// 2, 4, 8, 16, 32, 64...512까지만 제공
//					int needRemoveSize = 0;
//					for(int i=1;i<10;i++) {
//						// 만약 2의 배수보다 후보자 사이즈가 작은 경우 종료
//						if(Math.pow(2, i) > candidateList.size()) {
//							needRemoveSize = (int)(candidateList.size() - Math.pow(2, i-1));
//							break;
//						}
//					}
//					
//					// 필요한만큼 자른다.
//					for(int i=0;i<needRemoveSize;i++)
//						candidateList.remove(candidateList.size()-1);
//				}
				
				// 미참가자 강제 참가처리(페이스매치에만 해당)
				if(inputVO.getType() == 0) {
					
					// 미션참여되지 않은 회원은 강제로 등록하고 참여수를 올려줘야 한다.
					for(Map<String, Object> map : candidateList) {
						if(!map.containsKey("isJoined") || map.get("isJoined").toString().equals("0")) {
							MissionMatchReportReqVO vo = new MissionMatchReportReqVO();
							vo.setmId(Integer.parseInt(event.get("mId") + ""));
							vo.setType(0);
							vo.setTargetMemberId(Integer.parseInt(map.get("memberId") + ""));
							this.missionMatchDao.joinMissionMatchByFaceMatchDirect(vo);							
						}
					}
				}
				
				
				List<String> participants = new ArrayList<String>();
				for(Map<String, Object> map : candidateList) {
					String memberId = map.get("memberId").toString();
					if(!StringUtils.isNullOrEmpty(memberId) && memberId.trim().length() > 0)
						participants.add(memberId);
				}
				
				inputVO.setParticipant(participants);
				
				
				/// 업데잉트 
				missionMatchDao.updateMissionMatchBattle(inputVO);
				missionMatchDao.updateMissionMatchJoinBattle(inputVO);
				
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("comm.success.search", gLocale),
						msgUtil.getMsgText("comm.success.search", gLocale));
				
				
				responseMap.put("event", event);
				responseMap.put("candidate", candidateList);
				returnMap.put("response", responseMap);
				returnMap.put("result", result);
			}else{
				//후보가 준비되지 않음
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getCandidates.le.noResult", gLocale),
						msgUtil.getMsgText("facematch.getCandidates.le.noResult", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
		} else {
			//기타에러
			Result result = new Result(
					Constants.ReturnCode.OTHER_ERROR,
					msgUtil.getMsgCd("comm.otherError", gLocale),
					msgUtil.getMsgText("comm.otherError", gLocale));
			returnMap.put("result", result);
			return returnMap;
		}

		return returnMap;
	}
	
	

	/** 미션 후보 승점 등록 */
	@Override
	public Map<String, Object> reportMissionMatch(MissionMatchReportReqVO inputVO) {
		
		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 현재는 참여했던 투표로그를 보지는 않지만 원하는 경우 추가할 수 있도록 로그를 기록한다.
		MissionMatchReqVO missionVO = new MissionMatchReqVO();
		missionVO.setmId(inputVO.getmId());
		missionVO.setType(inputVO.getType());
		missionVO.setgLang(inputVO.getgLang());
		
		Map<String, Object> mission = this.missionMatchDao.getMissionMatch(missionVO);
		
		if(mission == null || mission.size() == 0) {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
			return returnMap;
		}
		
		int useMultiVote = UTL.convertToInt(mission.get("useMultiVote"), 0);
		int multiVoteInterval = UTL.convertToInt(mission.get("multiVoteInterval"), 0);
		
		// 미션참여 여부 검사
		Integer mJId = 0;
		Map<String, Object> existsJoin = this.missionMatchDao.existsMissionMatchJoin(inputVO);
		if(existsJoin != null && existsJoin.containsKey("mj_id")) {
			mJId = Integer.parseInt(existsJoin.get("mj_id") + "");
		}
		
		// 최근 투표 정보 추출
		inputVO.setmJId(mJId);
		Map<String, Object> recentVote = this.missionMatchDao.recentMissionMatchVote(inputVO);
		
		int updateCnt = 0;
		
		// 미션매치가 페이스매치 인 경우
		if(inputVO.getType() == 0) {
			// 참여여부를 보고 갱신 또는 추가 로직 처리
			if(mJId != null && mJId > 0) {
				
				// 페이스매치는 회원 정보만 있기 때문에 추출해서 역으로 넣어줘야 한다.
				inputVO.setmJId(mJId);
				
				// 최근 참여 내역이 없는 경우 바로 투표
				if(recentVote == null || recentVote.size() == 0) {
					updateCnt = this.missionMatchDao.updateJoinMissionMatch(inputVO).intValue();
				}else if(useMultiVote >0) {
					// 최근 참여내역이 있는데 다중참여 허용인 경우 마지막 참여일 체크
					Date voteCreatedDate = UTL.convertToDate(recentVote.get("createdDate") + "");
					Calendar target = Calendar.getInstance();
					target.setTime(voteCreatedDate);
					
					// 최소 간격보다 크다면 
					if(UTL.leftHour(Calendar.getInstance(), target) > multiVoteInterval) {
						updateCnt = this.missionMatchDao.updateJoinMissionMatch(inputVO).intValue();
					}else{
						// 잠시후 다시 참여하세요.
						Result result = new Result(
								Constants.ReturnCode.LOGIC_ERROR,
								msgUtil.getMsgCd("facematch.getCandidates.le.hasVoteWaitMoment", gLocale),
								msgUtil.getMsgText("facematch.getCandidates.le.hasVoteWaitMoment", gLocale));
						returnMap.put("result", result);
						return returnMap;
					}
				}else{
					// 이미 참여
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("facematch.getCandidates.le.hasVote", gLocale),
							msgUtil.getMsgText("facematch.getCandidates.le.hasVote", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
			}else{
				// 페이스매치 처음 선택된 회원이면 자동으로 등록
				Integer insertMJId = this.missionMatchDao.joinMissionMatchByFaceMatch(inputVO).intValue();
				if(insertMJId != null && insertMJId.intValue() > 0) {
					responseMap.put("mJId", mJId);
					updateCnt = 1;
				}
			}
		// 미션매치가 일반인 경우
		}else{
			// 있는 경우 갱신 처리
			if(mJId != null && mJId > 0) {
				
				if(recentVote == null || recentVote.size() == 0) {
					updateCnt = this.missionMatchDao.updateJoinMissionMatch(inputVO).intValue();					
				}else if(useMultiVote >0) {
					// 최근 참여내역이 있는데 다중참여 허용인 경우 마지막 참여일 체크
					Date voteCreatedDate = UTL.convertToDate(recentVote.get("createdDate") + "");
					Calendar target = Calendar.getInstance();
					target.setTime(voteCreatedDate);
					
					// 최소 간격보다 크다면 
					if(UTL.leftHour(Calendar.getInstance(), target) > multiVoteInterval) {
						updateCnt = this.missionMatchDao.updateJoinMissionMatch(inputVO).intValue();
					}else{
						// 잠시후 다시 참여하세요.
						Result result = new Result(
								Constants.ReturnCode.LOGIC_ERROR,
								msgUtil.getMsgCd("facematch.getCandidates.le.hasVoteWaitMoment", gLocale),
								msgUtil.getMsgText("facematch.getCandidates.le.hasVoteWaitMoment", gLocale));
						returnMap.put("result", result);
						return returnMap;
					}
				}else{
					// 이미 참여
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("facematch.getCandidates.le.hasVote", gLocale),
							msgUtil.getMsgText("facematch.getCandidates.le.hasVote", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
				
			}else{
				// 참여여부가 없는 경우 오류
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getCandidates.le.noJoinHistory", gLocale),
						msgUtil.getMsgText("acematch.getCandidates.le.noJoinHistory", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
		}
		
		if(updateCnt > 0) {
			
			// 투표 참여 결과 로그 남기기
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("mId", inputVO.getmId());
			params.put("mJId", inputVO.getmJId());
			params.put("gMemberId", inputVO.getgMemberId());
			this.missionMatchDao.insertVote(params);
			
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("facematch.updateResult.ss.success", gLocale),
					msgUtil.getMsgText("facematch.updateResult.ss.success", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}else{
			//결과정보 불일치
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.updateResult.le.wrongResult", gLocale),
					msgUtil.getMsgText("facematch.updateResult.le.wrongResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 미션매치 참여 */
	public Map<String, Object> joinMissionMatch(MissionMatchJoinReqVO inputVO) {

		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		// 현재는 참여했던 투표로그를 보지는 않지만 원하는 경우 추가할 수 있도록 로그를 기록한다.
		MissionMatchReqVO missionVO = new MissionMatchReqVO();
		missionVO.setmId(inputVO.getmId());
		missionVO.setgLang(inputVO.getgLang());
		missionVO.setType(inputVO.getType());
		
		Map<String, Object> mission = this.missionMatchDao.getMissionMatch(missionVO);
		
		if(mission == null || mission.size() == 0) {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
			return returnMap;
		}
		
		
		int useMultiVote = UTL.convertToInt(mission.get("useMultiVote"), 0);
		int multiVoteInterval = UTL.convertToInt(mission.get("multiVoteInterval"), 0);
		
		MissionMatchReportReqVO vo = new MissionMatchReportReqVO();
		vo.setTargetMemberId(inputVO.getgMemberId());
		vo.setmId(inputVO.getmId());
		
		Integer mJId = 0;
		Map<String, Object> existsJoin = this.missionMatchDao.existsMissionMatchJoin(vo);
		if(existsJoin != null && existsJoin.containsKey("mj_id")) {
			mJId = Integer.parseInt(existsJoin.get("mj_id") + "");
		}
		
		if(mJId > 0) {
			
			// 참여한 내역이 있지만 다중참여 제한인 경우
			if(useMultiVote == 0) {
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getCandidates.le.hasJoin", gLocale),
						msgUtil.getMsgText("facematch.getCandidates.le.hasJoin", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
			
			int maxCount = UTL.convertToInt(mission.get("maxCount"), 0);
			if(maxCount > 0) {
				// 최대 입력 수 제한
				Map<String, Object> cntMap = this.missionMatchDao.countJoinMissionMatch(inputVO);
				int cnt = cntMap != null && cntMap.containsKey("cnt") ? UTL.convertToInt(cntMap.get("cnt"), 0) : 0;
				if(maxCount <= cnt) {
					//이벤트 정보가 없음
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("facematch.getCandidates.le.fullJoin", gLocale),
							msgUtil.getMsgText("facematch.getCandidates.le.fullJoin", gLocale));
					returnMap.put("result", result);	
					return returnMap;	
				}
			}
				
			if(useMultiVote > 0) {
				
				// 최근 참여내역이 있는데 다중참여 허용인 경우 마지막 참여일 체크
				Date voteCreatedDate = UTL.convertToDate(existsJoin.get("created_date") + "");
				Calendar target = Calendar.getInstance();
				target.setTime(voteCreatedDate);
				
				// 최소 간격보다 크다면 
				if(UTL.leftHour(Calendar.getInstance(), target) > multiVoteInterval) {
					mJId = this.missionMatchDao.joinMissionMatch(inputVO);
				}else{
					// 잠시후 다시 참여하세요.
					Result result = new Result(
							Constants.ReturnCode.LOGIC_ERROR,
							msgUtil.getMsgCd("facematch.getCandidates.le.hasJoinWaitMoment", gLocale),
							msgUtil.getMsgText("facematch.getCandidates.le.hasJoinWaitMoment", gLocale));
					returnMap.put("result", result);
					return returnMap;
				}
				
			}else{
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("facematch.getCandidates.le.hasJoin", gLocale),
						msgUtil.getMsgText("facematch.getCandidates.le.hasJoin", gLocale));
				returnMap.put("result", result);
				return returnMap;
			}
		}else{
			mJId = this.missionMatchDao.joinMissionMatch(inputVO);	
		}
				
		if(mJId > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("mJId", mJId);
			returnMap.put("result", result);
		}else{
			//기타에러
			Result result = new Result(
					Constants.ReturnCode.OTHER_ERROR,
					msgUtil.getMsgCd("comm.otherError", gLocale),
					msgUtil.getMsgText("comm.otherError", gLocale));
			returnMap.put("result", result);
		}		
		
		return returnMap;
	}
	
	/** 미션매치 참여 랭킹 */
	public Map<String, Object> joinRankMissionMatch(MissionMatchRankReqVO inputVO) {

		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		MissionMatchReqVO missionVO = new MissionMatchReqVO();
		missionVO.setmId(inputVO.getmId());
		missionVO.setType(inputVO.getType());
		missionVO.setgLang(inputVO.getgLang());
		
		Map<String, Object> mission = this.missionMatchDao.getMissionMatch(missionVO);
		
		if(mission == null || mission.size() == 0) {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
			return returnMap;
		}
		
		inputVO.setSearchType("M");
		Map<String, Object> monthRank = this.missionMatchDao.selectMyJoinRankMissionMatch(inputVO);
		inputVO.setSearchType("A");
		Map<String, Object> totalRank = this.missionMatchDao.selectMyJoinRankMissionMatch(inputVO);
		
//		List<Map<String, Object>> rank = this.missionMatchDao.selectJoinRankMissionMatch(inputVO);
		
		responseMap.put("event", mission);
		responseMap.put("monthRank", monthRank);
		responseMap.put("totalRank", totalRank);
//		responseMap.put("rank", rank);
		
		//승자 상세정보 조회
		CommReqVO vo2 = new CommReqVO();
		vo2.setgMemberId(inputVO.getTargetMemberId());
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
			
			// 궁합정보
			TotalInfoReqVO harmonyReqVO = new TotalInfoReqVO();
			harmonyReqVO.setgMemberId(inputVO.getgMemberId());
			harmonyReqVO.setFriendId(inputVO.getTargetMemberId());
			harmonyReqVO.setgLang(inputVO.getgLang());
			Map<String,Object> harmonyMap = harmonyService.getHarmonyResultMap(harmonyReqVO);
			responseMap.put("harmony", harmonyMap);
		}
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.search", gLocale),
				msgUtil.getMsgText("comm.success.search", gLocale));
		returnMap.put("response", responseMap);
		returnMap.put("result", result);
		
		return returnMap;
		
	}
	
	/** 미션매치 전체 랭킹 */
	public Map<String, Object> totalRankMissionMatch(MissionMatchRankReqVO inputVO) {

		Locale gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		MissionMatchReqVO missionVO = new MissionMatchReqVO();
		missionVO.setmId(inputVO.getmId());
		missionVO.setType(inputVO.getType());
		missionVO.setgLang(inputVO.getgLang());
		
		Map<String, Object> mission = this.missionMatchDao.getMissionMatch(missionVO);
		
		if(mission == null || mission.size() == 0) {
			//이벤트 정보가 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("facematch.getCandidates.le.noEvent", gLocale),
					msgUtil.getMsgText("facematch.getCandidates.le.noEvent", gLocale));
			returnMap.put("result", result);	
			return returnMap;
		}
		
		inputVO.setSearchType("A");
		List<Map<String, Object>> rank = this.missionMatchDao.selectJoinRankMissionMatch(inputVO);
		
		responseMap.put("event", mission);
		responseMap.put("rank", rank);
		
		Result result = new Result(
				Constants.ReturnCode.SUCCESS,
				msgUtil.getMsgCd("comm.success.search", gLocale),
				msgUtil.getMsgText("comm.success.search", gLocale));
		returnMap.put("response", responseMap);
		returnMap.put("result", result);
		
		return returnMap;
		
	}
}
