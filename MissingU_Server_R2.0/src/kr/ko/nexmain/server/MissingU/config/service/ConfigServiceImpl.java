package kr.ko.nexmain.server.MissingU.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.config.dao.ConfigDao;
import kr.ko.nexmain.server.MissingU.config.model.GetNoticeReqVO;
import kr.ko.nexmain.server.MissingU.config.model.GetUserGuideReqVO;
import kr.ko.nexmain.server.MissingU.config.model.SaveManToManReqVO;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDao configDao;
	@Autowired
	private MembershipDao membershipDao;
	@Autowired
	private MsgUtil msgUtil;
	
	private Locale gLocale;
	
	
	/** 공지사항 리스트 조회 */
	public Map<String,Object> getNoticeList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//공지사항 리스트 조회
		List<Map<String,Object>> noticeList = configDao.selectNoticeList(inputVO);
		responseMap.put("notice", noticeList);
		
		if(noticeList != null && noticeList.size() > 0) {
			//조회 결과가 있는 경우
			
			//마지막 확인 한 공지사항 ID 업데이트
			MemberAttr attr = new MemberAttr();
			attr.setMemberId(inputVO.getgMemberId());
			attr.setAttrName(Constants.MemberAttrName.LAST_READ_NOTICE_ID);
			attr.setAttrValue(String.valueOf( noticeList.get(0).get("noticeId")) );
			membershipDao.updateMemberAttr(attr);
			
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
					msgUtil.getMsgCd("config.getNoticeList.le.noResult", gLocale),
					msgUtil.getMsgText("config.getNoticeList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 공지사항 조회 */
	public Map<String,Object> getNotice(GetNoticeReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//공지사항 조회
		configDao.updateNoticeReadCnt(inputVO);
		Map<String,Object> notice = configDao.selectNotice(inputVO);
		responseMap.put("notice", notice);
		
		if(notice != null && (Integer)notice.get("noticeId") > 0) {
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
					msgUtil.getMsgCd("config.getNotice.le.noResult", gLocale),
					msgUtil.getMsgText("config.getNotice.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 사용자가이드 리스트 조회 */
	public Map<String,Object> getUserGuideList(CommReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//사용자가이드 리스트 조회
		List<Map<String,Object>> guideList = configDao.selectUserGuideList(inputVO);
		responseMap.put("guide", guideList);
		
		if(guideList != null && guideList.size() > 0) {
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
					msgUtil.getMsgCd("config.getUserGuideList.le.noResult", gLocale),
					msgUtil.getMsgText("config.getUserGuideList.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 사용자가이드 조회 */
	public Map<String,Object> getUserGuide(GetUserGuideReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		//사용자가이드 조회
		Map<String,Object> guide = configDao.selectUserGuide(inputVO);
		responseMap.put("guide", guide);
		
		if(guide != null && !"".equals((String)guide.get("menuId"))) {
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
					msgUtil.getMsgCd("config.getUserGuide.le.noResult", gLocale),
					msgUtil.getMsgText("config.getUserGuide.le.noResult", gLocale));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	/** 
	 * 1:1 문의 저장
	 */
	public Map<String,Object> doSaveManToManQuestion(SaveManToManReqVO inputVO) {
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		//톡투미 정보 저장
		Integer mId = configDao.insertManToManQuestion(inputVO);
		
		if(mId != null && mId > 0) {
			//정상 저장
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("talktome.saveTalk.ss.success", gLocale),
					msgUtil.getMsgText("talktome.saveTalk.ss.success", gLocale));
			
			Map<String,Object> responseMap = new HashMap<String,Object>();
			responseMap.put("mId", mId);
			
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
}
