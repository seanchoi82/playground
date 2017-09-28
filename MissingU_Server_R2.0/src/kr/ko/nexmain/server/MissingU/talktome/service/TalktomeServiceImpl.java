package kr.ko.nexmain.server.MissingU.talktome.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.talktome.dao.TalktomeDao;
import kr.ko.nexmain.server.MissingU.talktome.dao.TalktomeDaoImpl;
import kr.ko.nexmain.server.MissingU.talktome.model.GetTalkReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReplyReqVO;
import kr.ko.nexmain.server.MissingU.talktome.model.SaveTalkReqVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class TalktomeServiceImpl implements TalktomeService {

	@Autowired
	private CommonService commonService;
	@Autowired
	private TalktomeDao talktomeDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private MsgUtil msgUtil;
	
	protected static Logger log = LoggerFactory.getLogger(TalktomeServiceImpl.class);
	private Locale gLocale;
	/** 
	 * 톡투미 리스트 조회
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getTalktomeList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//톡투미 리스트 조회
		List<Map<String,Object>> talktomeList = talktomeDao.selectTalktomeList(inputVO);
		responseMap.put("talkList", talktomeList);
		
		if(talktomeList != null && talktomeList.size() > 0) {
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.getTalktomeList.le.noResult", gLocale),
					msgUtil.getMsgText("talktome.getTalktomeList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 톡투미 정보 저장
	 */
	public Map<String,Object> doSaveTalkInfo(SaveTalkReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		// 수정모드로 전환
		if(inputVO.getTalkId() != null && inputVO.getTalkId() > 0) {
			return doEditTalkInfo(inputVO);
		}else{
			
			//톡투미 정보 저장
			Integer talkId = talktomeDao.insertTalktome(inputVO);
			log.info("Talk Created. ID : {}", String.valueOf(talkId));
		
		
			if(talkId != null && talkId > 0) {
				//정상 저장
				Result result = new Result(
						Constants.ReturnCode.SUCCESS,
						msgUtil.getMsgCd("talktome.saveTalk.ss.success", gLocale),
						msgUtil.getMsgText("talktome.saveTalk.ss.success", gLocale));
				
				Map<String,Object> responseMap = new HashMap<String,Object>();
				responseMap.put("talkId", talkId);
				
				returnMap.put("result", result);
				returnMap.put("response", responseMap);
				
				//포인트 정보 업데이트
				commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O207", -100, inputVO.getgLang());
			} else {
				//저장 에러
				Result result = new Result(
						Constants.ReturnCode.LOGIC_ERROR,
						msgUtil.getMsgCd("talktome.saveTalk.le.noResult", gLocale),
						msgUtil.getMsgText("talktome.saveTalk.le.noResult", gLocale));
				returnMap.put("result", result);
			}
		}
		
		return returnMap;
	}
	
	/** 
	 * 톡투미 정보 저장
	 */
	public Map<String,Object> doSaveTalkInfoNoUsePoint(SaveTalkReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//톡투미 정보 저장
		Integer talkId = talktomeDao.insertTalktome(inputVO);
		log.info("Talk Created. ID : {}", String.valueOf(talkId));
		
		if(talkId != null && talkId > 0) {
			//정상 저장
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("talktome.saveTalk.ss.success", gLocale),
					msgUtil.getMsgText("talktome.saveTalk.ss.success", gLocale));
			
			Map<String,Object> responseMap = new HashMap<String,Object>();
			responseMap.put("talkId", talkId);
			
			returnMap.put("result", result);
			returnMap.put("response", responseMap);

		} else {
			//저장 에러
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.saveTalk.le.noResult", gLocale),
					msgUtil.getMsgText("talktome.saveTalk.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 톡투미 정보 저장
	 */
	public Map<String,Object> doEditTalkInfo(SaveTalkReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Integer talkId = inputVO.getTalkId(); 
				
		//톡투미 정보 저장
		Integer updatedCount = talktomeDao.updateTalktome(inputVO);
		log.info("Talk Created. ID : {}", String.valueOf(talkId));
		
		if(updatedCount != null && updatedCount > 0) {
			//정상 저장
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("talktome.saveTalk.ss.success", gLocale),
					msgUtil.getMsgText("talktome.saveTalk.ss.success", gLocale));
			
			Map<String,Object> responseMap = new HashMap<String,Object>();
			responseMap.put("talkId", talkId);
			
			returnMap.put("result", result);
			returnMap.put("response", responseMap);

		} else {
			//저장 에러
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.saveTalk.le.noResult", gLocale),
					msgUtil.getMsgText("talktome.saveTalk.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	/** 
	 * 톡투미 단건 조회
	 */
	@Transactional
	public Map<String,Object> getTalktome(GetTalkReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//톡투미 리스트 조회
		Integer updateCnt = talktomeDao.updateTalktomeReadCnt(inputVO);
		Map<String,Object> talktome = talktomeDao.selectTalktomeByTalkId(inputVO);
		List<Map<String,Object>> talktomeReplyList = talktomeDao.selectTalktomeReplyByTalkId(inputVO);
		
		log.info("talk to me size "  + talktomeReplyList.size());
		
		if(talktome != null) {
			if(talktomeReplyList != null && talktomeReplyList.size() > 0) {
				talktome.put("reply", talktomeReplyList);
			}
			responseMap.put("talk", talktome);
			//조회 결과가 있는 경우
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		} else {
			//조회 결과 없음
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.getTalktome.le.noResult", gLocale),
					msgUtil.getMsgText("talktome.getTalktome.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 톡투미 댓글 저장
	 */
	@Transactional
	public Map<String,Object> doSaveTalkReply(SaveTalkReplyReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//톡투미 정보 저장
		Integer replyId = talktomeDao.insertTalktomeReply(inputVO);
		log.info("Reply Created. ID : {}", String.valueOf(replyId));
		Integer updateCnt = talktomeDao.updateTalktomeReplyCnt(inputVO);
		
		if(replyId != null && replyId > 0) {
			Integer receiverId = talktomeDao.selectTalkWriterId(inputVO.getTalkId());
			if(receiverId != null && receiverId > 0) {
				//댓글 알림 GCM메세지 전송
				sendGCMTalkMsgByMemberId(receiverId, inputVO.getgMemberId(), Constants.ActionType.TALK_MSG, inputVO.getTalkId());
			}
			
			//정상 저장
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("talktome.saveTalkReply.ss.success", gLocale),
					msgUtil.getMsgText("talktome.saveTalkReply.ss.success", gLocale));
			returnMap.put("result", result);
			
			//포인트 정보 업데이트
			if(inputVO.isUseSavePoint())
				commonService.updatePointInfo(inputVO.getgMemberId(), Constants.EventTypeCd.OUTCOME, "O208", -100, inputVO.getgLang());
		} else {
			//저장 에러
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.saveTalkReply.le.noResult", gLocale),
					msgUtil.getMsgText("talktome.saveTalkReply.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	public boolean sendGCMTalkMsgByMemberId(Integer receiverId, Integer senderId, String action, Integer talkId) {
		Map<String,Object> receiver = this.membershipDao.selectSimpleMemberInfoByMemberId(receiverId);
		Map<String,Object> sender = this.membershipDao.selectSimpleMemberInfoByMemberId(senderId);
		
		StringBuilder sb = new StringBuilder();
		sb.append( (sender.get("nickName")) );
		sb.append( msgUtil.getPropMsg("talktome.saveTalkReply.talkNotice", gLocale));
		
		Map<String,String> gcmParams = new HashMap<String,String>();
		gcmParams.put("msg", sb.toString());
		gcmParams.put("action", action);
		gcmParams.put("talkId", String.valueOf(talkId));
		gcmParams.put("senderId", String.valueOf(senderId));
		gcmParams.put("senderSex", UTL.nvl(((String)sender.get("sex"))));
		gcmParams.put("senderPhotoUrl", UTL.nvl((String)sender.get("mainPhotoUrl")));
		
		if(receiver.get("gcmRegId") != null && !"".equals((String)receiver.get("gcmRegId"))) {
			UTL.sendGCM((String)receiver.get("gcmRegId"), gcmParams);
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	@Transactional
	public Map<String, Object> doDeleteTalk(GetTalkReqVO inputVO) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
				
		Map<String,Object> data = this.talktomeDao.selectTalktomeByTalkId(inputVO);
		if(data != null
				&& data.get("memberId") != null
				&& data.get("memberId").toString().equals(inputVO.getgMemberId() + "")) {
			// 글 삭제(안보이게만 처리할것)
			this.talktomeDao.deleteTalk(inputVO);
			
			//정상 저장
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("talktome.delTalk.ss.success", gLocale),
					msgUtil.getMsgText("talktome.delTalk.ss.success", gLocale));
			returnMap.put("result", result);
			
		}else{
			//본인글이 아닌 경우 삭제 에러
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("talktome.delTalk.le.noSearchResult", gLocale),
					msgUtil.getMsgText("talktome.delTalk.le.noSearchResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
}
