package kr.ko.nexmain.server.MissingU.vscheck.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.CommReqVO;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.config.dao.ConfigDao;
import kr.ko.nexmain.server.MissingU.vscheck.dao.VersionCheckDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout=15)
public class VersionCheckServiceImpl implements VersionCheckService {
	
	@Autowired
	private ConfigDao configDao;
	@Autowired
	private VersionCheckDao versionCheckDao;
	@Autowired
	private MsgUtil msgUtil;
	
	private Locale gLocale;
	
	public Map<String, Object> updateEmrNotice(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		// 반드시 버전정보와, emr 기초 레코드가 있어야 함..
		int updatedCnt = versionCheckDao.updateEmrNotice(params);
		if(updatedCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
		}else{
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	public Map<String, Object> updateUpgradeVersionInfo(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		// 반드시 버전정보와, emr 기초 레코드가 있어야 함..
		int updatedCnt = versionCheckDao.updateUpgradeVersionInfo(params);
		if(updatedCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
		}else{
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getUpgradeVersionInfoAndEMRNotification(CommReqVO inputVO, boolean useShowYn) {
		
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		
		// 버전 정보 조회 
		Map<String,Object> upgradeInfo = versionCheckDao.selectLastVersionInfo(inputVO);
		responseMap.put("upgradeInfo", upgradeInfo);
		
		//공지사항 조회
		Map<String,Object> emrNotice = configDao.selectEMRNofitice(inputVO, useShowYn);
		responseMap.put("emrNotice", emrNotice);
		
		if((emrNotice != null && emrNotice.size() > 0) || (upgradeInfo != null && upgradeInfo.size() > 0))
		{
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}  else {
			//조회 결과 없음 (긴급 공지나 업그레이드 정보가 없다고 에러를 반환 시키면 안됨)
			Result result = new Result(
					Constants.ReturnCode.SUCCESS, 
					"", 
					""
					);
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	public Map<String, Object> updateEmrNoticeRandomChat(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		// 반드시 버전정보와, emr 기초 레코드가 있어야 함..
		int updatedCnt = versionCheckDao.updateEmrNoticeRandomChat(params);
		if(updatedCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
		}else{
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}

	public Map<String, Object> updateUpgradeVersionInfoRandomChat(Map<String, Object> params) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		// 반드시 버전정보와, emr 기초 레코드가 있어야 함..
		int updatedCnt = versionCheckDao.updateUpgradeVersionInfoRandomChat(params);
		if(updatedCnt > 0) {
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
		}else{
			Result result = new Result(
					Constants.ReturnCode.LOGIC_ERROR,
					msgUtil.getMsgCd("comm.fail.search"),
					msgUtil.getMsgText("comm.fail.search"));
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> getUpgradeVersionInfoAndEMRNotificationRandomChat(CommReqVO inputVO, boolean useShowYn) {
		
		gLocale = new Locale(inputVO.getgLang());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> responseMap = new HashMap<String,Object>();
		
		
		// 버전 정보 조회 
		Map<String,Object> upgradeInfo = versionCheckDao.selectLastVersionInfoRandomChat(inputVO);
		responseMap.put("upgradeInfo", upgradeInfo);
		
		//공지사항 조회
		Map<String,Object> emrNotice = configDao.selectEMRNofiticeRandomChat(inputVO, useShowYn);
		responseMap.put("emrNotice", emrNotice);
		
		if((emrNotice != null && emrNotice.size() > 0) || (upgradeInfo != null && upgradeInfo.size() > 0))
		{
			Result result = new Result(
					Constants.ReturnCode.SUCCESS,
					msgUtil.getMsgCd("comm.success.search", gLocale),
					msgUtil.getMsgText("comm.success.search", gLocale));
			returnMap.put("result", result);
			returnMap.put("response", responseMap);
		}  else {
			//조회 결과 없음 (긴급 공지나 업그레이드 정보가 없다고 에러를 반환 시키면 안됨)
			Result result = new Result(
					Constants.ReturnCode.SUCCESS, 
					"", 
					""
					);
			returnMap.put("result", result);
		}
		
		return returnMap;
	}
}
